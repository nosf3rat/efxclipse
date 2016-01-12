/*
 * generated by Xtext 2.9.0
 */
lexer grammar InternalCssDslLexer;

@header {
package org.eclipse.fx.ide.css.ide.contentassist.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

FontFace : '@'('F'|'f')('O'|'o')('N'|'n')('T'|'t')'-'('F'|'f')('A'|'a')('C'|'c')('E'|'e');

Keyframes : '@'('K'|'k')('E'|'e')('Y'|'y')('F'|'f')('R'|'r')('A'|'a')('M'|'m')('E'|'e')('S'|'s');

Charset : '@'('C'|'c')('H'|'h')('A'|'a')('R'|'r')('S'|'s')('E'|'e')('T'|'t');

Import : '@'('I'|'i')('M'|'m')('P'|'p')('O'|'o')('R'|'r')('T'|'t');

Media : '@'('M'|'m')('E'|'e')('D'|'d')('I'|'i')('A'|'a');

Page : '@'('P'|'p')('A'|'a')('G'|'g')('E'|'e');

Not_1 : ('N'|'n')('O'|'o')('T'|'t')'(';

Url_1 : ('U'|'u')('R'|'r')('L'|'l')'(';

Not : ('N'|'n')('O'|'o')('T'|'t');

Url : ('U'|'u')('R'|'r')('L'|'l');

DollarSignEqualsSign : '$''=';

AsteriskEqualsSign : '*''=';

ReverseSolidusSpace : '\\'' ';

ReverseSolidusQuotationMark : '\\''"';

ReverseSolidusApostrophe : '\\''\'';

ReverseSolidusLeftParenthesis : '\\''(';

ReverseSolidusRightParenthesis : '\\'')';

CircumflexAccentEqualsSign : '^''=';

No : ('N'|'n')('O'|'o');

Ur : ('U'|'u')('R'|'r');

ExclamationMark : '!';

DollarSign : '$';

Ampersand : '&';

LeftParenthesis : '(';

RightParenthesis : ')';

Asterisk : '*';

FullStop : '.';

Solidus : '/';

Semicolon : ';';

EqualsSign : '=';

GreaterThanSign : '>';

QuestionMark : '?';

CommercialAt : '@';

LeftSquareBracket : '[';

RightSquareBracket : ']';

LeftCurlyBracket : '{';

VerticalLine : '|';

RightCurlyBracket : '}';

Tilde : '~';

// Rules duplicated to allow inter-rule references

RULE_IMPORTANT_SYM : '!important';

RULE_ONE_INT : '0'..'9';

RULE_ONE_HEX_LETTER : ('a'..'f'|'A'..'F');

RULE_ONE_NON_HEX_LETTER : ('g'..'z'|'G'..'Z');

RULE_UNDERSCORE : '_';

RULE_DASH : '-';

RULE_PLUS : '+';

RULE_HASHMARK : '#';

RULE_COMMA : ',';

RULE_PERCENT : '%';

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_CSSSTRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'0'..'9'|'a'..'f'|'A'..'F'|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'0'..'9'|'a'..'f'|'A'..'F'|'\\')|~(('\\'|'\'')))* '\'');

RULE_INCLUDES : '~=';

RULE_DASHMATCH : '|=';

RULE_COLON : ':';
