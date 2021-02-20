package be.uclouvain.organisation.interactivity.element;

import be.uclouvain.organisation.interactivity.element.ElementCapacity;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class ElementRole extends Behavior {
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
  public ElementRole(final Agent agent) {
    super(agent);
  }
}
