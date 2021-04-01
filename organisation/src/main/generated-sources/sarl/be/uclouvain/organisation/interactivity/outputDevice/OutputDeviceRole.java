package be.uclouvain.organisation.interactivity.outputDevice;

import be.uclouvain.organisation.interactivity.outputDevice.OutputDeviceCapacity;
import be.uclouvain.organisation.interactivity.outputDevice.OutputMsg;
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
public class OutputDeviceRole extends Behavior {
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$OutputMsg$0(final OutputMsg occurrence) {
    OutputDeviceCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY$CALLER.outputConversion(occurrence.getSource().getUUID(), occurrence.Information);
  }
  
  @Extension
  @ImportedCapacityFeature(OutputDeviceCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY;
  
  @SyntheticMember
  @Pure
  private OutputDeviceCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY = $getSkill(OutputDeviceCapacity.class);
    }
    return $castSkill(OutputDeviceCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_OUTPUTDEVICE_OUTPUTDEVICECAPACITY);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$OutputMsg(final OutputMsg occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$OutputMsg$0(occurrence));
  }
  
  @SyntheticMember
  public OutputDeviceRole(final Agent agent) {
    super(agent);
  }
}
