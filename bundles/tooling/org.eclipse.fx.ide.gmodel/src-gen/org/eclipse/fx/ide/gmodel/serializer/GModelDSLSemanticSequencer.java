/*
 * generated by Xtext
 */
package org.eclipse.fx.ide.gmodel.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fx.ide.gmodel.gModelDSL.GDefault;
import org.eclipse.fx.ide.gmodel.gModelDSL.GDomainElement;
import org.eclipse.fx.ide.gmodel.gModelDSL.GDomainMap;
import org.eclipse.fx.ide.gmodel.gModelDSL.GDomainProperty;
import org.eclipse.fx.ide.gmodel.gModelDSL.GModel;
import org.eclipse.fx.ide.gmodel.gModelDSL.GModelDSLPackage;
import org.eclipse.fx.ide.gmodel.services.GModelDSLGrammarAccess;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;

@SuppressWarnings("all")
public class GModelDSLSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private GModelDSLGrammarAccess grammarAccess;
	
	@Override
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == GModelDSLPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case GModelDSLPackage.GDEFAULT:
				sequence_GDefault(context, (GDefault) semanticObject); 
				return; 
			case GModelDSLPackage.GDOMAIN_ELEMENT:
				sequence_GDomainElement(context, (GDomainElement) semanticObject); 
				return; 
			case GModelDSLPackage.GDOMAIN_MAP:
				sequence_GDomainMap(context, (GDomainMap) semanticObject); 
				return; 
			case GModelDSLPackage.GDOMAIN_PROPERTY:
				sequence_GDomainProperty(context, (GDomainProperty) semanticObject); 
				return; 
			case GModelDSLPackage.GMODEL:
				sequence_GModel(context, (GModel) semanticObject); 
				return; 
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (stringVal=STRING | intVal=INT)
	 */
	protected void sequence_GDefault(EObject context, GDefault semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID (superTypeList+=[GDomainElement|ID] superTypeList+=[GDomainElement|ID]*)? (map=GDomainMap | propertyList+=GDomainProperty*))
	 */
	protected void sequence_GDomainElement(EObject context, GDomainElement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (builtIn='Int' | builtIn='Double' | builtIn='String' | builtIn='Boolean' | ref=[GDomainElement|ID])
	 */
	protected void sequence_GDomainMap(EObject context, GDomainMap semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ((builtIn='Int' | builtIn='Double' | builtIn='String' | builtIn='Boolean') list?='[]'? name=ID defaultValue=GDefault?) | 
	 *         (ref=[GDomainElement|ID] list?='[]'? name=ID)
	 *     )
	 */
	protected void sequence_GDomainProperty(EObject context, GDomainProperty semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=QualifiedName typeList+=GDomainElement+)
	 */
	protected void sequence_GModel(EObject context, GModel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}