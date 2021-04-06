package be.uclouvain.organisation.interactivity.inputDevice;

import be.uclouvain.organisation.interactivity.element.ElementInformation;
import be.uclouvain.organisation.interactivity.inputDevice.DataAcquisition;
import be.uclouvain.organisation.interactivity.inputDevice.InputDeviceCapacity;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Interface collecting and distributing to the appropriate Element the external data fed to the system (A mobile app, a connected device).
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class InputDeviceRole extends Behavior {
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    InputDeviceCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY$CALLER.EnableInputStream();
  }
  
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
    InputDeviceCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY$CALLER.DisableInputStream();
  }
  
  private void $behaviorUnit$DataAcquisition$2(final DataAcquisition occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    ElementInformation _elementInformation = new ElementInformation(occurrence.data);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_elementID;
      
      public $SerializableClosureProxy(final UUID $_elementID) {
        this.$_elementID = $_elementID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_elementID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, occurrence.elementID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, occurrence.elementID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_elementInformation, _function);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @Extension
  @ImportedCapacityFeature(InputDeviceCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY;
  
  @SyntheticMember
  @Pure
  private InputDeviceCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY = $getSkill(InputDeviceCapacity.class);
    }
    return $castSkill(InputDeviceCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_INTERACTIVITY_INPUTDEVICE_INPUTDEVICECAPACITY);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$DataAcquisition(final DataAcquisition occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$DataAcquisition$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$1(occurrence));
  }
  
  @SyntheticMember
  public InputDeviceRole(final Agent agent) {
    super(agent);
  }
}
