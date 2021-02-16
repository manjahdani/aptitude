package be.uclouvain.organisation.interactivity.element;

import be.uclouvain.organisation.interactivity.element.ElementCapacity;
import be.uclouvain.organisation.interactivity.element.ElementInformation;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import java.util.Collection;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class ElementRole extends Behavior {
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$ElementInformation$0(final ElementInformation occurrence) {
    ElementCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY$CALLER.InformationAnalysis(occurrence.information);
  }
  
  @Extension
  @ImportedCapacityFeature(ElementCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY;
  
  @SyntheticMember
  @Pure
  private ElementCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY = $getSkill(ElementCapacity.class);
    }
    return $castSkill(ElementCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_ELEMENT_ELEMENTCAPACITY);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ElementInformation(final ElementInformation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ElementInformation$0(occurrence));
  }
  
  @SyntheticMember
  public ElementRole(final Agent agent) {
    super(agent);
  }
}
