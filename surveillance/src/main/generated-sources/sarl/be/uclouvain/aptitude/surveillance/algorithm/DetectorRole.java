package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.surveillance.algorithm.DetectorPythonTwin;
import be.uclouvain.aptitude.surveillance.algorithm.PythonObserverRole;
import be.uclouvain.aptitude.surveillance.algorithm.PythonTwinObserverAccessCapacity;
import be.uclouvain.aptitude.surveillance.algorithm.RestartDetector;
import be.uclouvain.organisation.platform.LeavePlatform;
import com.google.common.base.Objects;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.core.Schedules;
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
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
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
public class DetectorRole extends PythonObserverRole {
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      if ((this.jsonConfig != null)) {
        DetectorPythonTwin _detectorPythonTwin = new DetectorPythonTwin();
        this.<DetectorPythonTwin>setSkill(_detectorPythonTwin);
        PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
        _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.ActivateAccess(this.jsonConfig);
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3.task("waitforConfig"));
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.every(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("waitforConfig"), 500, _function);
  }
  
  private void $behaviorUnit$BBoxes2DResult$1(final BBoxes2DResult occurrence) {
    for (final UUID l : this.listeners) {
      OpenEventSpace _get = this.missionSpaceList.get(l);
      BBoxes2DResult _bBoxes2DResult = new BBoxes2DResult(occurrence.bboxes2D);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID l;
        
        public $SerializableClosureProxy(final UUID l) {
          this.l = l;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, l);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, l);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, l);
        }
      };
      _get.emit(this.getOwner().getID(), _bBoxes2DResult, _function);
    }
  }
  
  private void $behaviorUnit$RestartDetector$2(final RestartDetector occurrence) {
    String _name = this.observerADN.getName();
    boolean _equals = Objects.equal(occurrence.bel, _name);
    if (_equals) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Restarting");
      this.missionSpaceList.put(occurrence.getSource().getUUID(), this.missionSpaceList.get(((Object[])Conversions.unwrapArray(this.listeners, Object.class))[0]));
      this.listeners.clear();
      this.listeners.add(occurrence.getSource().getUUID());
      PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.UpdateStreamAccess(5);
      PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER_1 = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER_1.UpdateStreamAccess(1);
    }
  }
  
  private void $behaviorUnit$Destroy$3(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The behavior was stopped.");
  }
  
  private void $behaviorUnit$LeavePlatform$4(final LeavePlatform occurrence) {
    PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.UpdateStreamAccess(4);
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
  @ImportedCapacityFeature(PythonTwinObserverAccessCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY;
  
  @SyntheticMember
  @Pure
  private PythonTwinObserverAccessCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY = $getSkill(PythonTwinObserverAccessCapacity.class);
    }
    return $castSkill(PythonTwinObserverAccessCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$1(occurrence));
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
  private void $guardEvaluator$RestartDetector(final RestartDetector occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$RestartDetector$2(occurrence));
  }
  
  @SyntheticMember
  public DetectorRole(final Agent agent) {
    super(agent);
  }
}
