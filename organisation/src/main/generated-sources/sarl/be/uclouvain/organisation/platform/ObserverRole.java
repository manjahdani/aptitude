package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.ObserverCapacity;
import io.sarl.core.Logging;
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
public class ObserverRole extends Behavior {
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(ObserverCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY;
  
  @SyntheticMember
  @Pure
  private ObserverCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY = $getSkill(ObserverCapacity.class);
    }
    return $castSkill(ObserverCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY);
  }
  
  @SyntheticMember
  public ObserverRole(final Agent agent) {
    super(agent);
  }
}
