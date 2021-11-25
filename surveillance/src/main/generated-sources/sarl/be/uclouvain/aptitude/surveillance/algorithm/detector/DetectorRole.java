package be.uclouvain.aptitude.surveillance.algorithm.detector;

import be.uclouvain.aptitude.surveillance.algorithm.detector.DetectorPythonTwin;
import be.uclouvain.aptitude.surveillance.algorithm.detector.RestartDetector;
import be.uclouvain.organisation.platform.LeavePlatform;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.platform.ProcessingHyperParameters;
import be.uclouvain.python_access.BBoxes2DResult;
import be.uclouvain.python_access.PythonAccessorRole;
import be.uclouvain.python_access.PythonTwinAccessCapacity;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.Destroy;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO: write a description
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class DetectorRole extends ObserverRole {
  private void $behaviorUnit$BBoxes2DResult$0(final BBoxes2DResult occurrence) {
    Collection<OpenEventSpace> _values = this.listeners.values();
    for (final OpenEventSpace listenersSpace : _values) {
      String _name = this.observerADN.getName();
      BBoxes2DResult _bBoxes2DResult = new BBoxes2DResult(occurrence.bboxes2D, _name);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID $_iD;
        
        public $SerializableClosureProxy(final UUID $_iD) {
          this.$_iD = $_iD;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return (_uUID != $_iD);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          UUID _iD = DetectorRole.this.getOwner().getID();
          return (_uUID != _iD);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, DetectorRole.this.getOwner().getID());
        }
      };
      listenersSpace.emit(this.getOwner().getID(), _bBoxes2DResult, _function);
    }
  }
  
  private void $behaviorUnit$ProcessingHyperParameters$1(final ProcessingHyperParameters occurrence) {
    DetectorPythonTwin _detectorPythonTwin = new DetectorPythonTwin();
    this.<DetectorPythonTwin>setSkill(_detectorPythonTwin);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    Agent _owner = this.getOwner();
    PythonAccessorRole _pythonAccessorRole = new PythonAccessorRole(_owner);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_pythonAccessorRole, this.observerADN.getBelief(), this.platformName);
  }
  
  private void $behaviorUnit$RestartDetector$2(final RestartDetector occurrence) {
    String _name = this.observerADN.getName();
    boolean _equals = Objects.equal(occurrence.bel, _name);
    if (_equals) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Restarting");
      OpenEventSpace spaceToSend = this.listeners.get(this.listeners.get(Integer.valueOf(0)));
      this.listeners.clear();
      this.listeners.put(occurrence.getSource().getUUID(), spaceToSend);
      PythonTwinAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER.updateStreamAccess(5);
      PythonTwinAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER_1 = this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER_1.updateStreamAccess(1);
    }
  }
  
  private void $behaviorUnit$Destroy$3(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The behavior was stopped.");
  }
  
  private void $behaviorUnit$LeavePlatform$4(final LeavePlatform occurrence) {
    PythonTwinAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER.updateStreamAccess(4);
  }
  
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
  @ImportedCapacityFeature(PythonTwinAccessCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY;
  
  @SyntheticMember
  @Pure
  private PythonTwinAccessCapacity $CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY = $getSkill(PythonTwinAccessCapacity.class);
    }
    return $castSkill(PythonTwinAccessCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LeavePlatform(final LeavePlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$RestartDetector(final RestartDetector occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$RestartDetector$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ProcessingHyperParameters(final ProcessingHyperParameters occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ProcessingHyperParameters$1(occurrence));
  }
  
  @SyntheticMember
  public DetectorRole(final Agent agent) {
    super(agent);
  }
}
