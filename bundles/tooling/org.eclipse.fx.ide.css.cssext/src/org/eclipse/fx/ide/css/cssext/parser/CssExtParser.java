package org.eclipse.fx.ide.css.cssext.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fx.core.log.Log;
import org.eclipse.fx.core.log.Logger;
import org.eclipse.fx.ide.css.cssDsl.ColorTok;
import org.eclipse.fx.ide.css.cssDsl.CssDeclaration;
import org.eclipse.fx.ide.css.cssDsl.CssProperty;
import org.eclipse.fx.ide.css.cssDsl.CssTok;
import org.eclipse.fx.ide.css.cssDsl.FuncTok;
import org.eclipse.fx.ide.css.cssDsl.IdentifierTok;
import org.eclipse.fx.ide.css.cssDsl.NumberTok;
import org.eclipse.fx.ide.css.cssDsl.StringTok;
import org.eclipse.fx.ide.css.cssDsl.SymbolTok;
import org.eclipse.fx.ide.css.cssDsl.UrlTok;
import org.eclipse.fx.ide.css.cssDsl.WSTok;
import org.eclipse.fx.ide.css.cssext.api.ProposalContributionRegistry;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSPropertyRef;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRangedDoubleType;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRangedIntType;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRule;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleBracket;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleConcat;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleConcatWithoutSpace;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleDefinition;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleFunc;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleLiteral;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleOr;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRulePostfix;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleRef;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleRegex;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleSymbol;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSRuleXor;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CSSType;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CssExtDslFactory;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.CssExtDslPackage;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.Definition;
import org.eclipse.fx.ide.css.cssext.cssExtDsl.PropertyDefinition;
import org.eclipse.fx.ide.css.cssext.extapi.CssExtApi;
import org.eclipse.fx.ide.css.cssext.extapi.scope.ResourceScope;
import org.eclipse.fx.ide.css.cssext.extapi.scope.Scope;
import org.eclipse.fx.ide.css.cssext.parser.result.NodeType;
import org.eclipse.fx.ide.css.cssext.parser.result.ResultNode;
import org.eclipse.fx.ide.css.cssext.parser.result.State;
import org.eclipse.fx.ide.css.extapi.MultiProposal;
import org.eclipse.fx.ide.css.extapi.Proposal;
import org.eclipse.fx.ide.css.extapi.SimpleProposal;
import org.eclipse.fx.ide.css.extapi.ValidationResult;
import org.eclipse.fx.ide.css.util.TokUtil;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;


public class CssExtParser {

	private @Inject CssExtApi extApi;
//	private @Inject ICssExtManager manager;
	private @Inject IQualifiedNameProvider nameProvider;

	private @Inject ProposalContributionRegistry proposalContributionRegistry;


	private @Log("cssext.parser") Logger logger;

	private static enum ConsumeWS {
		MAY_CONSUME,
		MUST_CONSUME,
		NO_CONSUME;
	}

	public CSSRule getRuleForFunction(Scope context, String propertyName, String functionName) {
		System.err.println("getRuleForFunction " + functionName);
		CSSRule propertyRule = getRuleForProperty(context, propertyName);

		Stack<EObject> todo = new Stack<>();
		todo.push(propertyRule);
		Set<EObject> done = new HashSet<>();

		EObject cur;
		while (!todo.isEmpty()) {
			cur = todo.pop();
			if (cur == null) break;

			System.err.println("current count: " + todo.size());
			TreeIterator<EObject> it = cur.eAllContents();
			while (it.hasNext()) {
				EObject next = it.next();
				if (done.contains(next)) {
					continue;
				}

				//System.err.println("rule " + next);
//				if (next instanceof CSSRuleFunc) {
//					CSSRuleFunc rule = (CSSRuleFunc) next;
//					System.err.println("Its a func: " + rule + " " + rule.getName());
//					if (functionName.equals(rule.getName())) {
//						return rule.getParams();
//					}
//				}
//				else

				if (next instanceof CSSRuleRef) {
					System.err.println("ref to " + ((CSSRuleRef)next).getRef().getName());
					CSSRule refRule = ((CSSRuleRef) next).getRef().getRule();
					if (refRule != null) {
						System.err.println("adding ref " + refRule);
						todo.push(refRule);
					}
					CSSRuleFunc refFunc = (CSSRuleFunc)((CSSRuleDefinition)((CSSRuleRef) next).getRef()).getFunc();
					if (refFunc != null) {
						if (functionName.equals(refFunc.getName())) {
							return refFunc.getParams();
						}

					}
				}

				done.add(next);
			}
		}
		return null;
	}

	public CSSRule getRuleForProperty(Scope context, String propertyName) {
		CSSRule rule = null;
		PropertyDefinition def = extApi.findPropertyByName(context, propertyName);
		if (def != null) {
			rule = def.getRule();
		}
		else {
			if (variableTypes.containsKey(propertyName)) {
				rule = variableTypes.get(propertyName);
			}
		}
		return rule;
	}

	public CSSRule getRuleForProperty(Scope context, CssProperty property) {
		return getRuleForProperty(context, property.getName());
	}

	public String doParse(Scope context, CssProperty property) {
		CSSRule rule = getRuleForProperty(context, property);

		if (rule != null) {
			final ParserInput input = new ParserInput(((CssDeclaration)property.eContainer()).getValueTokens());

			final ParserInputCursor cursor = input.createCursor();

			final ResultNode res = parse(context, cursor, rule, ConsumeWS.MAY_CONSUME);

			return ResultNode.dbg2(res);
		}

		return "no rule found";
	}

	private ResultNode parse(Scope context, ParserInputCursor input, CSSRule rule, ConsumeWS consumeWS) {
		Assert.isNotNull(rule, "rule must not be null");
		Assert.isNotNull(input, "input must not be null");
		ResultNode result = null;

		switch(rule.eClass().getClassifierID()) {
		case CssExtDslPackage.CSS_RULE_BRACKET: result = parseBracket(context, input, (CSSRuleBracket)rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_REF: result = parseRef(context, input, (CSSRuleRef)rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_OR: result = parseOr(context, input, (CSSRuleOr) rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_XOR: result = parseConcatOr(context, input, (CSSRuleXor) rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_CONCAT: result = parseConcat(context, input, (CSSRuleConcat) rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_CONCAT_WITHOUT_SPACE: result = parseConcatWithoutSpace(context, input, (CSSRuleConcatWithoutSpace) rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_LITERAL: result = parseLiteral(input, (CSSRuleLiteral) rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_SYMBOL: result = parseSymbol(input, (CSSRuleSymbol) rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_POSTFIX: result = parsePostfix(context, input, (CSSRulePostfix) rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_REGEX: result = parseRegex(input, (CSSRuleRegex)rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_TYPE:
		case CssExtDslPackage.CSS_PROPERTY_REF:
		case CssExtDslPackage.CSS_RANGED_DOUBLE_TYPE:
		case CssExtDslPackage.CSS_RANGED_INT_TYPE:
			result = parseType(context, input, (CSSType)rule, consumeWS);
			break;
		case CssExtDslPackage.CSS_RULE_FUNC: result = parseFunction(context, input, (CSSRuleFunc)rule, consumeWS); break;
		default:
			logger.warning(rule + " not implemented! (classifierID="+rule.eClass().getClassifierID()+")");
		}
//		if (relevant(rule)) {
//			print(g.depth, g.node, "<- " + result);
//			g.depth--;
//		}
		return result;
	}

	private ResultNode parseOr(Scope context, ParserInputCursor input, CSSRuleOr or, ConsumeWS consumeWS) {
		ResultNode orResult = new ResultNode(NodeType.OR);
		orResult.remainingInput = input.copy();
		orResult.status = State.FORWARD;

		for (CSSRule rule : or.getOrs()) {
			ResultNode result = parse(context, input.copy(), rule, consumeWS);
			orResult.next.add(result);
		}

		return orResult;
	}

	// Concat
	private ResultNode parseConcat(Scope context, ParserInputCursor l, CSSRuleConcat r, ConsumeWS consumeWS) {
		ResultNode concatResult = new ResultNode(NodeType.CONCAT);
		concatResult.remainingInput = l.copy();
		concatResult.status = State.FORWARD;

		Iterator<CSSRule> ruleIt = r.getConc().iterator();
		while (ruleIt.hasNext()) {
			final CSSRule rule = ruleIt.next();
			final boolean isFirstRule = r.getConc().indexOf(rule) == 0;
			for (ResultNode last : concatResult.findLast()) {
				if (last.isValid()) {
					last.next.add(parse(context, last.remainingInput, rule, isFirstRule?consumeWS:ConsumeWS.MUST_CONSUME));

				}
			}


		}

		return concatResult;
	}

	// Concat without space
	private ResultNode parseConcatWithoutSpace(Scope context, ParserInputCursor l, CSSRuleConcatWithoutSpace r, ConsumeWS consumeWS) {
		ResultNode concatResult = new ResultNode(NodeType.CONCAT_WITHOUT_SPACE);
		concatResult.remainingInput = l.copy();
		concatResult.status = State.FORWARD;

		Iterator<CSSRule> ruleIt = r.getConc().iterator();
		while (ruleIt.hasNext()) {
			final CSSRule rule = ruleIt.next();
			final boolean isFirstRule = r.getConc().indexOf(rule) == 0;
			for (ResultNode last : concatResult.findLast()) {
				if (last.isValid()) {
					last.next.add(parse(context, last.remainingInput, rule, isFirstRule?consumeWS:ConsumeWS.NO_CONSUME));
				}
				else if (!isFirstRule && last.status == State.PROPOSE) {
//					// if the last was a proposal we go on with empty input to get multiple proposals in sequence
					ResultNode node = parse(context, ParserInputCursor.emptyParserInputCursor(), rule, ConsumeWS.NO_CONSUME);
					node.findByState(State.PROPOSE);
					for (ResultNode n : node.findLast()) {
						n.proposal = wrapMultiProposal(last.proposal, n.proposal);
					}
					last.next.add(node);
				}

			}
		}

		return concatResult;
	}

	private ResultNode parseBracket(Scope context, ParserInputCursor in, CSSRuleBracket rule, ConsumeWS consumeWS) {
		return parse(context, in, rule.getInner(), consumeWS);

//		ResultNode result = new ResultNode();
//		result.nodeSymbol = "[]";
//		result.nodeName = "Bracket";
//		result.remainingInput = in.copy();
//		result.status = State.FORWARD;
//
//		result.next.add(parse(in.copy(), rule.getInner(), consumeWS));
//		return result;
	}

	private ResultNode parsePostfix(Scope context, ParserInputCursor in, CSSRulePostfix r, ConsumeWS consumeWS) {
		ResultNode result = null;
		if( r.getCardinality() != null && ! r.getCardinality().isEmpty() ) {
			switch (r.getCardinality().charAt(0)) {
			case '?':
				result = parseOptional(context, in, r.getRule(), consumeWS);
				break;
			case '*':
				result = parseStar(context, in, r.getRule(), consumeWS);
				break;
			case '+':
				result = parsePlus(context, in, r.getRule(), consumeWS);
				break;
			}
		}
		return result;
	}

	private ResultNode parseOptional(Scope context, ParserInputCursor in, CSSRule rule, ConsumeWS consumeWS) {
		ResultNode result = new ResultNode(NodeType.OPTIONAL);
		result.remainingInput = in.copy();
		result.status = State.FORWARD;

		result.next.add(ResultNode.createSkipNode(result));
		result.next.add(parse(context, in.copy(), rule, consumeWS));

		return result;
	}

	private ResultNode parseStar(Scope context, ParserInputCursor in, CSSRule rule, ConsumeWS consumeWS) {
		ResultNode result = new ResultNode(NodeType.STAR);
		result.status = State.FORWARD;
		result.remainingInput = in.copy();

		Queue<ResultNode> last = new LinkedList<>();
		last.addAll(result.findLast());

		boolean first = true;

		while (!last.isEmpty()) {
			ResultNode cur = last.poll();
			if (cur.isValid()) {
				ResultNode n = parse(context, cur.remainingInput.copy(), rule, consumeWS);

				last.addAll(n.findLast());

				if (first) {
					cur.next.add(n);

					ResultNode skipStarNode = new ResultNode(NodeType.STAR);
					skipStarNode.status = State.SKIP;
					skipStarNode.remainingInput = cur.remainingInput.copy();

					cur.next.add(skipStarNode);

					first = false;
				}
				else {
					ResultNode starNode = new ResultNode(NodeType.STAR);
					starNode.status = State.FORWARD;
					starNode.remainingInput = cur.remainingInput.copy();
					starNode.next.add(n);
					cur.next.add(starNode);

					ResultNode skipStarNode = new ResultNode(NodeType.STAR);
					skipStarNode.status = State.SKIP;
					skipStarNode.remainingInput = cur.remainingInput.copy();

					starNode.next.add(skipStarNode);
				}


				//cur.next.add(ResultNode.createSkipNode(cur));
			}

		}

		return result;
	}


	private ResultNode parsePlus(Scope context, ParserInputCursor in, CSSRule rule, ConsumeWS consumeWS) {
		ResultNode result = new ResultNode(NodeType.PLUS);
		result.status = State.FORWARD;
		result.remainingInput = in.copy();

		// 1 - n
		Queue<ResultNode> last = new LinkedList<>();
		last.addAll(result.findLast());
		int iteration = 0;
		while (!last.isEmpty()) {
			ResultNode cur = last.poll();
			if (cur.isValid()) {

				ResultNode n = parse(context, cur.remainingInput.copy(), rule, consumeWS);
				cur.next.add(n);

				if (iteration >= 1) {
					cur.next.add(ResultNode.createSkipNode(cur));
				}

				iteration ++;
			}

		}

		return result;
	}


	private ResultNode parseType(Scope context, ParserInputCursor l, CSSType type, ConsumeWS consumeWS) {
		ResultNode result = null;
		if ("@NUM".equals(type.getType())) {
			result = parseNUMType(l, type, consumeWS);
		}
		else if ("@INT".equals(type.getType())) {
			result = parseINTType(l, type, consumeWS);
		}
		else if ("@URL".equals(type.getType())) {
			result = parseURLType(l, type, consumeWS);
		}
		else if ("@PROPERTY_REF".equals(type.getType())) {
			result = parsePROPERTY_REFType(context, l, (CSSPropertyRef) type, consumeWS);
		}
		else if ("@STRING".equals(type.getType())) {
			result = parseSTRINGType(l, type, consumeWS);
		}
		else {
			logger.error("type " + type + " not supported");
			throw new UnsupportedOperationException("type " + type + " not supported");
		}

		return result;
	}

	private ResultNode parseRegex(ParserInputCursor l, CSSRuleRegex r, ConsumeWS consumeWS) {
		ResultNode result = new ResultNode(NodeType.REGEX);

		String regex = r.getRegex().replaceAll("\\$", "");


		ParserInputCursor localInput = l.copy();

		CssTok tok = localInput.pollNextToken();
		int wsConsumed = 0;
		if (consumeWS == ConsumeWS.MAY_CONSUME) {
			while (tok instanceof WSTok) {
				tok = localInput.pollNextToken();
				wsConsumed++;
			}
		}

		if (consumeWS == ConsumeWS.MUST_CONSUME) {
			if (wsConsumed > 0) {
				// ok
			}
			else {
				// invalid
				result.status = State.INVALID;

				return result;
			}

		}

		logger.debug("REGEX1 -> " + regex + " / / " + tok);

		if (tok instanceof ColorTok) {
			String s = ((ColorTok)tok).getValue();
			logger.debug("REGEX2 -> " + regex + " / / " + s);
			if (s.matches(regex)) {
				result.status = State.MATCH;
				result.matched = tok;
				result.remainingInput = localInput;
			}
			else {
				result.status = State.INVALID;
				result.remainingInput = localInput;
			}
		}
		else {
			result.status = State.INVALID;
			result.message = "expected color token";
		}

		return result;
	}

	private ResultNode parseRef(Scope context, ParserInputCursor l, CSSRuleRef r, ConsumeWS consumeWS) {
		logger.debug("Entered with: " + r);
		CSSRule rule =  extApi.resolveReference(context, r);
		logger.debug("Resolved to: " + rule);
		if (rule == null) {
			logger.debug("resolving rule ref " + r.getRef().getName() + " returned null (maybe a function?) !!!!!");
			ResultNode inv = new ResultNode(NodeType.REF);
			inv.status = State.INVALID;
			return inv;
		}

		ResultNode rv = parse(context, l, rule, consumeWS);

		Definition ref = r.getRef();
		QualifiedName fqn = nameProvider.getFullyQualifiedName(ref);

		if (l.isConsumedOrOnlyWSLeft()) {
			List<Proposal> contributedProposalsForRule = proposalContributionRegistry.getContributedProposalsForRule(context, fqn.toString());
			for (Proposal c : contributedProposalsForRule) {
				ResultNode e = new ResultNode(NodeType.REF);
				e.status = State.PROPOSE;
				e.proposal = c;
				rv.next.add(e);
			}
		}

		return rv;
	}

	<S> void computePermutations(List<S> orig, List<List<S>> result) {
//		System.err.println("computePermutations " + orig);
		final S first = orig.get(0);
		final List<S> rest = orig.subList(1, orig.size());

		if (rest.size() == 1) {
			result.add(Arrays.asList(first, rest.get(0)));
			result.add(Arrays.asList(rest.get(0), first));
		}
		else {
			List<List<S>> r = new ArrayList<>();
			computePermutations(rest, r);
			for (List<S> s : r) {
				for (int idx = 0; idx <= s.size(); idx++) {
					List<S> x = new ArrayList<S>(s);
					x.add(idx, first);
					result.add(x);
				}
			}
		}
	}

	<S> List<List<S>> computePermutations(List<S> elems) {
		List<List<S>> result = new ArrayList<>();
		computePermutations(elems, result);
		return result;
	}

	/* A || B || C means
	 * A | B | A B | B A
	 *
	 * A || B || C means (do we even have cases with more than 2???)
	 * A | B | C | A B C | B A C | C A B | C B A | A C B | B C A
	 */
	private CSSRule rebuildXor(CSSRuleXor in) {
		CSSRuleOr or = CssExtDslFactory.eINSTANCE.createCSSRuleOr();

		// first add all single or
		for (CSSRule r : in.getXors()) {
			or.getOrs().add(EcoreUtil.copy(r));
		}

		// then add all permutations
		for (List<CSSRule> perm : computePermutations(in.getXors())) {
			CSSRuleConcat concat = CssExtDslFactory.eINSTANCE.createCSSRuleConcat();
			for (CSSRule r : perm) {
				concat.getConc().add(EcoreUtil.copy(r));
			}
			or.getOrs().add(concat);
		}

		return or;
	}

	// Xor
	private ResultNode parseConcatOr(Scope context, ParserInputCursor l, CSSRuleXor r, ConsumeWS consumeWS) {

		return parse(context, l, rebuildXor(r), consumeWS);

//
//		EList<CSSRule> xors = r.getXors();
//
//		CSSRuleOr
//
////		ResultNode result = new ResultNode(NodeType.CONCAT_OR);
////		result.status = State.INVALID;
////
////		// TODO impl this
////		for (CSSRule rule : r.getXors()) {
////			parse(l, rule, consumeWS);
////		}
////
////		return result;
//
//		ResultNode concatOrResult = new ResultNode(NodeType.CONCAT_OR);
//		concatOrResult.remainingInput = l.copy();
//		concatOrResult.status = State.FORWARD;
//
//		Queue<CSSRule> rulesLeft = new LinkedList<>(r.getXors());
//
//		int maxTests = rulesLeft.size();
//		int testNo = 1;
//
//		while (!rulesLeft.isEmpty() && testNo <= maxTests) {
//			// pick rule
//			final CSSRule rule = rulesLeft.poll();
//
//			boolean match = false;
//			//try on all
//			for (ResultNode last : concatOrResult.findLast()) {
//				if (last.isValid()) {
//					ResultNode path = parse(context, last.remainingInput, rule, consumeWS);
//					for (ResultNode pathEnd : path.findLast()) {
//						if (pathEnd.isValid()) {
//							match = true;
//						}
//					}
//					last.next.add(path);
//					if (!rulesLeft.isEmpty()) {
//						// special skip node
//						ResultNode skippy = new ResultNode(NodeType.CONCAT_OR);
//						skippy.status = State.SKIP;
//						skippy.remainingInput = last.remainingInput.copy();
//						last.next.add(skippy);
//					}
//				}
//			}
//
//			if (!match) {
//				// put the rule back
//				rulesLeft.offer(rule);
//				testNo++;
//			}
//			else {
//				testNo = 1;
//				maxTests = rulesLeft.size();
//			}
//		}
//
//		return concatOrResult;
	}





//	private final int n = Integer.MAX_VALUE;

//	private boolean recParsePostfix(ParsePath g, Set<Input> input, CSSRule rule, int min, int cur,  int max, Set<ParseResult> result) {
//		if (cur > max) return true;
//
//		print(g.depth, "recPostfix for " + input);
//			for (Input in : input) {
//				print(g.depth, "recPostfix " + in);
//				ParseResult localResult = parse(g, in, rule);
//
//				if (localResult.status.getCode() == IStatus.CANCEL) {
//					result.add(localResult);
//				}
//
//				if (!in.isConsumed()) {
//					// consume ws
//					print(g.depth, "consuming ws");
//					CssTok ws = in.getNextTok();
//					if (!(ws instanceof WSTok)) {
//						localResult.status = Status.CANCEL_STATUS;
////						continue;
//					}
//				}
//
//				boolean done = recParsePostfix(g, localResult.remainingInput, rule, min, cur + 1, max, result);
//
//				if (done) {
//
//				}
//		}
//
//		return false;
//	}

//	private boolean checkAllInvalid(List<ParseResult> results) {
//		boolean  invalid = true;
//
//		for (ParseResult r : results) {
//			if (r.status != Status.INVALID) {
//				invalid = false;
//				break;
//			}
//		}
//
//		return invalid;
//	}


	private Proposal createProposal(final String proposal) {
		return new Proposal() {

			@Override
			public String getProposal() {
				return proposal;
			}

			@Override
			public int getPriority() {
				return 0;
			}

			@Override
			public String getLabel() {
				return proposal;
			}

			@Override
			public String getImageUrl() {
				return null;
			}

			@Override
			public Object getAdditionalInfo() {
				return null;
			}

			@Override
			public Type getType() {
				return Type.Value;
			}

			@Override
			public String toString() {
				return proposal;
			}
		};
	}

	private Proposal wrapMultiProposal(final Proposal previous, final Proposal proposal) {
		return new MultiProposal() {

			@Override
			public Type getType() {
				return proposal.getType();
			}

			@Override
			public String getProposal() {
				return previous.getProposal() + proposal.getProposal();
			}

			@Override
			public int getPriority() {
				return proposal.getPriority();
			}

			@Override
			public String getLabel() {
				return previous.getLabel() + proposal.getLabel();
			}

			@Override
			public String getImageUrl() {
				return proposal.getImageUrl();
			}

			@Override
			public Object getAdditionalInfo() {
				return proposal.getAdditionalInfo();
			}

			@Override
			public Proposal getPrevious() {
				return previous;
			}

			@Override
			public String toString() {
				return previous.toString() + " + " + proposal.toString();
			}
		};
	}

	private ResultNode parseFunction(Scope context, ParserInputCursor in, CSSRuleFunc ruleFunc, ConsumeWS consumeWS) {
		ResultNode result = new ResultNode(NodeType.FUNCTION);

		ParserInputCursor local = in.copy();

		CssTok tok;
		try {
			tok = consumeWS(local, consumeWS);

			if (tok == null) {
				result.status = State.PROPOSE;
				result.proposal = createProposal(ruleFunc.getName() + "()");
			}
			else {
				if (tok instanceof FuncTok) {
					// ok
					FuncTok fTok = (FuncTok) tok;
					String funcName = fTok.getName().getName();

					if (funcName.equals(ruleFunc.getName())) {
						result.status = State.MATCH; // func name ok, still need to match params
						result.matched = tok;

						// TODO add parsing of function parameters
						ParserInput funcInput = new ParserInput(fTok.getParams());

						result.child = parse(context, funcInput.createCursor(), ruleFunc.getParams(), ConsumeWS.MAY_CONSUME);


//						logger.debug("paramResult= " + paramResult);

//						boolean paramsOk = false;
//
//						for (ParseResult paramR : paramResult) {
//							if (paramR.status == Status.MATCH && paramR.remainingInput.isConsumed()) {
//								paramsOk = true;
//								break;
//							}
//						}
//
//						if (paramsOk) {
//							r.remainingInput = local;
//							r.status = Status.MATCH;
//
//							g.newSegment(ParseStatus.MATCH, fTok);
//						}
//						else {
//							// function parameters wrong
//							r.message = "recheck function params";
//							r.status = Status.INVALID;
//
//							g.newSegment(ParseStatus.INVALID, fTok, "wrong parameters");
//						}
					}
					else {
						// invalid (wrong func)
						result.message = "expected " + ruleFunc.getName();
						result.status = State.INVALID;
					}



				}
				else {
					// invalid
					result.status = State.INVALID;
					result.message = "expected function";
				}
			}

		} catch (Exception e) {
			result.status = State.INVALID;
			result.message = "expected WS";
		}

		result.remainingInput = local.copy();
		return result;
	}

	private ResultNode parseSTRINGType(ParserInputCursor input, CSSType rule, ConsumeWS consumeWS) {
		final ResultNode result = new ResultNode(NodeType.TYPE_STRING);
		ParserInputCursor local = input.copy();
		try {
			CssTok tok = consumeWS(local, consumeWS);
			if (tok != null) {
				if (tok instanceof StringTok) {
					result.status = State.MATCH;
					result.matched = tok;
					result.remainingInput = local.copy();
				}
				else {
					result.status = State.INVALID;
					result.message = "expected string";
				}
			}
			else {
				result.status = State.PROPOSE;
				result.proposal = createProposal("\"someString\"");
			}
		}
		catch (Exception e) {
			result.status = State.INVALID;
			result.message = "expected WS";
		}

		return result;
	}

	private ThreadLocal<Set<CSSRule>> typeStackGuard = new ThreadLocal<>();
	private ResultNode parsePROPERTY_REFType(Scope context, ParserInputCursor input, CSSPropertyRef rule, ConsumeWS consumeWS) {
		final ResultNode result = new ResultNode(NodeType.TYPE_PROPERTY_REF);
		ParserInputCursor local = input.copy();
		try {
			CssTok tok = consumeWS(local, consumeWS);
			if (tok != null) {
				if (tok instanceof IdentifierTok) {
					result.status = State.MATCH;
					result.matched = tok;
					result.remainingInput = local.copy();
					result.rule = rule.getRule();
				}
				else {
					result.status = State.INVALID;
					result.message = "expected property reference";
				}
			}
			else {
				result.status = State.PROPOSE;
				result.proposal = createProposal("some reference to another property");

				if (typeStackGuard.get() == null) { typeStackGuard.set(new HashSet<>()); }

				if (!typeStackGuard.get().contains(rule)) {
					typeStackGuard.get().add(rule);
					ResultNode props = parse(context, local.copy(), rule.getRule(), consumeWS);
					result.next.add(props);
					typeStackGuard.get().remove(rule);
				}
			}
		}
		catch (Exception e) {
			result.status = State.INVALID;
			result.message = "expected WS";
		}

		return result;
	}

	private ResultNode parseURLType(ParserInputCursor input, CSSType rule, ConsumeWS consumeWS) {
		final ResultNode result = new ResultNode(NodeType.TYPE_URL);
		ParserInputCursor local = input.copy();
		try {
			CssTok tok = consumeWS(local, consumeWS);
			if (tok != null) {
				if (tok instanceof UrlTok) {
					result.status = State.MATCH;
					result.matched = tok;
					result.remainingInput = local.copy();
				}
				else {
					result.status = State.INVALID;
					result.message = "expected url";
				}
			}
			else {
				result.status = State.PROPOSE;
				result.proposal = createProposal("url(http://efxclipse.org/)");
			}
		}
		catch (Exception e) {
			result.status = State.INVALID;
			result.message = "expected WS";
		}

		return result;
	}

	private ResultNode parseINTType(ParserInputCursor l, CSSType rule, ConsumeWS consumeWS) {
		final ResultNode result = new ResultNode(NodeType.TYPE_INT);

		ParserInputCursor local = l.copy();
		try {
			CssTok tok = consumeWS(local, consumeWS);
			if (tok != null) {
				if (tok instanceof NumberTok) {

					NumberTok numberTok = (NumberTok) tok;
					double number = numberTok.getVal();

					if (rule instanceof CSSRangedIntType) {
						CSSRangedIntType ranged = (CSSRangedIntType) rule;
						int min = ranged.getFrom();
						int max = ranged.getTo();

						if (min <= number && number <= max) {
							// ok
							result.remainingInput = local;
							result.status = State.MATCH;
							result.matched = tok;
						}
						else {
							// invalid range
							result.status = State.INVALID;
							result.message = "invalid range";

						}
					}
					else {
						// ok
						result.remainingInput = local;
						result.status = State.MATCH;
						result.matched = tok;
					}
				}
				else {
					// invalid
					result.status = State.INVALID;

				}
			}
			else {
				result.status = State.PROPOSE;
				result.proposal = createProposal("0");
			}
		}
		catch (Exception e) {
			result.status = State.INVALID;
			result.message = e.getMessage();
		}

		return result;
	}

	private ResultNode parseNUMType(ParserInputCursor l, CSSType rule, ConsumeWS consumeWS) {
		ResultNode result = new ResultNode(NodeType.TYPE_NUM);

		ParserInputCursor local = l.copy();

		try {
			CssTok tok = consumeWS(local, consumeWS);
			if (tok != null) {
				if (tok instanceof NumberTok) {
					// ok
					NumberTok numberTok = (NumberTok) tok;
					double number = numberTok.getVal();

					if (rule instanceof CSSRangedDoubleType) {
						CSSRangedDoubleType ranged = (CSSRangedDoubleType) rule;
						double min = ranged.getFrom();
						double max = ranged.getTo();

						if (min <= number && number <= max) {
							// ok
							result.remainingInput = local;
							result.status = State.MATCH;
							result.matched = tok;
						}
						else {
							// invalid range
							result.status = State.INVALID;
							result.message = "invalid range";
						}
					}
					else {
						// ok
						result.remainingInput = local;
						result.status = State.MATCH;
						result.matched = tok;
					}
				}
				else {
					// invalid
					result.status = State.INVALID;

				}
			}
			else {
				result.status = State.PROPOSE;
				result.proposal = createProposal("0.0");
			}
		}
		catch (Exception e) {
			result.status = State.INVALID;
			result.message = e.getMessage();
		}

		return result;
	}

	private CssTok consumeWS(ParserInputCursor input, ConsumeWS consumeWS) throws Exception {

		CssTok tok = input.pollNextToken();
		int wsConsumed = 0;
		if (consumeWS == ConsumeWS.MAY_CONSUME || consumeWS == ConsumeWS.MUST_CONSUME) {
			while (tok instanceof WSTok) {
				tok = input.pollNextToken();
				wsConsumed++;
			}
		}

		if (consumeWS == ConsumeWS.MUST_CONSUME) {
			if (wsConsumed > 0) {
				// ok
			}
			else {
				// invalid
				throw new Exception("expected whitespace");
			}

		}

		return tok;

	}

	// Literal
	private ResultNode parseLiteral(ParserInputCursor input, CSSRuleLiteral r, ConsumeWS consumeWS) {
		ResultNode result = new ResultNode(NodeType.LITERAL);

		ParserInputCursor local = input.copy();

		try {
			CssTok tok = consumeWS(local, consumeWS);

			if (tok != null) {
				String literal = r.getValue();

				if (matchCssTok(tok, literal)) {
					result.status = State.MATCH;
					result.matched = tok;
					result.remainingInput = local.copy();
					result.message = literal;

				}
				else {
					result.status = State.INVALID;
					result.message = "expected " + literal;
				}
			}
			else {
				result.status = State.PROPOSE;
				result.proposal = createProposal(r.getValue());
			}

		}
		catch (Exception e) {
			result.status = State.INVALID;
			result.message = e.getMessage();
		}




		return result;

	}

	private ResultNode parseSymbol(ParserInputCursor l, CSSRuleSymbol r, ConsumeWS consumeWS) {
		final ResultNode result = new ResultNode(NodeType.SYMBOL);

		// This workaround allows symbol concatenation without whitespace
		if (consumeWS == ConsumeWS.MUST_CONSUME) {
			consumeWS = ConsumeWS.MAY_CONSUME;
		}

		final ParserInputCursor local = l.copy();

		try {
			CssTok tok = consumeWS(local, consumeWS);
			if (tok != null) {
				String symbol = r.getSymbol();
				if (matchCssTok(tok, symbol)) {
					// valid yay!
					result.status = State.MATCH;
					result.matched = tok;
					result.remainingInput = local.copy();

				}
				else {
					result.status = State.INVALID;
					result.message = "expected " + symbol;
				}
			}
			else {
				result.status = State.PROPOSE;
				result.proposal = createProposal(r.getSymbol());
			}
		}
		catch (Exception e) {
			result.status = State.INVALID;
			result.message = e.getMessage();
		}

		return result;

	}

	private boolean matchCssTok(CssTok tok, String literal) {
		if (tok instanceof IdentifierTok) {
			return literal.equals(((IdentifierTok)tok).getName());
		}
		else if (tok instanceof SymbolTok) {
			return literal.equals(((SymbolTok)tok).getSymbol());
		}
		return false;
	}

	public List<Proposal> findProposals(Scope context, String element, String propertyName, FuncTok funcTok, List<CssTok> prefixToks, String prefix) {
		CSSRule rule = getRuleForFunction(context, propertyName, funcTok.getName().getName());
		List<Proposal> result = new ArrayList<>();
		if (rule != null) {
			int indexOf = prefixToks.indexOf(funcTok);
			prefixToks = prefixToks.subList(indexOf+1, prefixToks.size());
			result.addAll(computeProposalsForRule(context, rule, prefixToks, prefix));
		}
		else {
			result.add(createProposal("> no rule for " + funcTok.getName().getName() + " found!"));
		}
		return result;
	}

	protected List<Proposal> computeProposalsForRule(Scope context, CSSRule rule, List<CssTok> prefixToks, String prefix) {
		List<Proposal> result = new ArrayList<>();

		final ParserInput input = new ParserInput(prefixToks);

		final ParserInputCursor cursor = input.createCursor();

		logger.debugf("starting with input: %s", cursor);
		final long parseBegin = System.nanoTime();
		final ResultNode res = parse(context, cursor, rule, ConsumeWS.MAY_CONSUME);
		final long parseDuration = System.nanoTime() - parseBegin;
		logger.debugf("parse needed %2.3fms returnd with %s", parseDuration*10e-6, res);

		for (ParseResultListener l : resultListener) {
			l.parseFinished(res);
		}

		//ResultNode.dbg(res);


		// add semicolon if input was successfully parsed
		// THIS IS SIMPLY WRONG!!!!
		for (ResultNode x : res.findByState(State.MATCH)) {
			if (x.remainingInput.isConsumedOrOnlyWSLeft()) {
				result.add(new SimpleProposal(";"));
				break;
			}
		}

		result.addAll(mapProposals(res));


//		result.addAll(findProposals(new LinkedList<CssTok>(prefixToks), prefix, def.getRule()));

		return result;
	}

	public List<Proposal> findProposals(Scope context, String element, String propertyName, List<CssTok> prefixToks, String prefix) {

		// TODO for now we skip the prefixstring queries
		//if (prefix.length() > 0) return Collections.emptyList();

		// debug output
		logger.debugf("findProposals( %s, %s, %s )", element, propertyName, prefix);
		logger.debugf("prefixString: '%s'", prefix);
		if (prefixToks.isEmpty()) {
			logger.debug("prefixToks: none");
		}
		else {
			logger.debugf("prefixToks:");
			Iterator<CssTok> iterator = prefixToks.iterator();
			while (iterator.hasNext()) {
				logger.debugf(" * %s", TokUtil.toString(iterator.next()));
			}
		}


		List<Proposal> result = new ArrayList<Proposal>();

		CSSRule rule = getRuleForProperty(context, propertyName);

		if (rule != null) {
			result.addAll(computeProposalsForRule(context, rule, prefixToks, prefix));
		}
		else {
			result.add(createProposal("> no rule for " + propertyName + " found!"));
		}

		logger.debugf("-> returning %s", result);

		return result;
	}

	private List<Proposal> mapProposals(ResultNode result) {
		List<Proposal> proposals = new ArrayList<Proposal>();

		List<Proposal> drop = new ArrayList<>();

		for (ResultNode r : result.findByState(State.PROPOSE)) {

			if (!r.next.isEmpty()) {
				ResultNode cur = r;
				List<ResultNode> allSubPropose = cur.findByState(State.PROPOSE);
				allSubPropose.remove(cur);
				for (ResultNode n : allSubPropose) {
					if (!(n.proposal instanceof MultiProposal)) {
						drop.add(n.proposal);
					}
				}

			}

			proposals.add(r.proposal);
		}
		logger.debugf("Dropping %s", drop);
		proposals.removeAll(drop);

		return proposals;
	}

	private Map<String, CSSRule> variableTypes = new HashMap<>();

	private boolean supportsPropertyRefs(Scope scope) {
		return scope.getState(ResourceScope.KEY_PROPERTY_REF_SUPPORT);
	}

	public Map<String, CSSRule> findAllReferences(Scope scope) {
		if (!supportsPropertyRefs(scope)) return Collections.emptyMap();
		long now = System.currentTimeMillis();
		Map<String, CSSRule> result = new HashMap<>();
		EObject stylesheet = scope.getResource().get().getContents().get(0);
//		EObject stylesheet = EcoreUtil.getRootContainer(scope.context);

		// FIRST PASS
		TreeIterator<EObject> it = stylesheet.eAllContents();
		variableTypes.clear();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof CssDeclaration) {
				result.putAll(findReferences(scope, (CssDeclaration)obj));
				it.prune();
			}
		}
		System.err.println("FIRST PASS RESULT: " + result.size() + " " + result);

		// 2ND PASS
		it = stylesheet.eAllContents();
		variableTypes.putAll(result);
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof CssDeclaration) {
				result.putAll(findReferences(scope, (CssDeclaration)obj));
				it.prune();
			}
		}
		System.err.println("2ND PASS RESULT: " + result.size() + " " + result);


		System.err.println("ctx refs in " + (System.currentTimeMillis() - now) + "ms");
		return result;
	}



	public Map<String, CSSRule> findReferences(Scope context, CssDeclaration decl) {
		Map<String, CSSRule> result = new HashMap<>();
		// TODO selector context -.-
		CSSRule rule = getRuleForProperty(context, decl.getProperty().getName());

		if (rule != null) {
			ParserInputCursor input = new ParserInputCursor(new ParserInput(decl.getValueTokens()));

			ResultNode parse = parse(context, input, rule, ConsumeWS.MUST_CONSUME);

			Stack<ResultNode> out = new Stack<>();
			out.push(parse);

			while (!out.isEmpty()) {
				ResultNode cur = out.pop();
				if (cur.next != null) {
					for (ResultNode n : cur.next) {
						if (n.findLast().stream().anyMatch(p->p.isValid())) {
							out.push(n);
							break;
						}
					}
				}
				if (cur.child != null) {
					if (cur.child.findLast().stream().anyMatch(p->p.isValid())) {
						out.push(cur.child);
					}
				}

				if (cur.nodeType == NodeType.TYPE_PROPERTY_REF && cur.status == State.MATCH) {
					CSSRule r = cur.rule;
					if (r instanceof CSSRuleRef) {
						r = ((CSSRuleRef)r).getRef().getRule();
					}
					result.put(((IdentifierTok)cur.matched).getName(), r);
				}
			}
		}

		return result;
	}

	public List<ValidationResult> validateProperty(Scope context, String element, String propertyName, List<CssTok> tokens) {

		// debug output
		logger.debugf("validateProperty( %s, %s )", element, propertyName);
		if (tokens.isEmpty()) {
			logger.debugf("tokens: none");
		}
		else {
			logger.debug("tokens:");
			Iterator<CssTok> iterator = tokens.iterator();
			while (iterator.hasNext()) {
				logger.debugf(" * %s", TokUtil.toString(iterator.next()));
			}
		}

		PropertyDefinition def = extApi.findPropertyByName(context, propertyName);
		if (def != null) {

//			ParserInput input = new ParserInput(tokens);


//			ResultNode res = parse(input.createCursor(), def.getRule(), ConsumeWS.MAY_CONSUME);

			boolean valid = false;

//			int lastInputIndex = -1;
			EObject lastToken = null;
//			String msg = "so ned!";

//			for (ParseResult p : res) {
//				if (p.status == Status.PROPOSE) continue;
//				logger.debugf(" * %s", p.toString());
//				if (p.remainingInput != null) {
//					if (p.remainingInput.isConsumed()) {
//						logger.debug("  -> der da!");
//						if (p.status == Status.MATCH || p.status == Status.SKIP) {
//							valid = true;
//							break;
//						}
//					}
//					else {
//						int newlastInputIndex = p.remainingInput.getPosition();
//						lastInputIndex = Math.max(newlastInputIndex, lastInputIndex);
//						msg = p.message;
//						lastToken = p.remainingInput.peekNextToken();
//					}
//				}
//			}


			logger.debug("valid = " + valid);
			logger.debug("lastToken = " + lastToken);

			List<ValidationResult> results = new ArrayList<ValidationResult>();

//			if (g.getOverallStatus() == ParseStatus.MATCH) {
//				return Collections.emptyList();
//			}
//			else {
//				List<ParsePathTerminal> rr = g.getMessage(g);
//				for (ParsePathTerminal s : rr) {
//					ValidationResult r = new ValidationResult(ValidationStatus.ERROR, s.message, s.inputToken, null, -1);
//					results.add(r);
//				}
//
//			}

			return results;

//			if (valid) {
//				return Collections.emptyList();
//			}
//			else {
//				List<ValidationResult> results = new ArrayList<ValidationResult>();
//				logger.debug("returning validation result");
//				if (lastToken != null) {
//
//					ValidationResult r = new ValidationResult(ValidationStatus.ERROR, msg, lastToken, null, 0);
//					results.add(r);
//				}
//				else {
//					ValidationResult r = new ValidationResult(ValidationStatus.ERROR, msg, null, null, lastInputIndex);
//					results.add(r);
//				}
//
//				return results;
//			}

		}


		return null;
	}

	public static interface ParseResultListener {
		public void parseFinished(ResultNode result);
	}

	private Set<ParseResultListener> resultListener = new HashSet<>();
	public void addParseResultListener(ParseResultListener l) {
		resultListener.add(l);
	}
}
