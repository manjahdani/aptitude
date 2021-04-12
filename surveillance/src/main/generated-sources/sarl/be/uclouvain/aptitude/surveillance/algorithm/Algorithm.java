package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.AlgorithmNeeded;
import be.uclouvain.aptitude.surveillance.Paraddis;
import be.uclouvain.aptitude.surveillance.algorithm.CounterRole;
import be.uclouvain.aptitude.surveillance.algorithm.DetectorRole;
import be.uclouvain.aptitude.surveillance.algorithm.TrackerRole;
import be.uclouvain.organisation.told.entity.EntityRole;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Agent with a set of tasks (Goals) and a model (Belief).
 * It bases its computations on a Belief, otherwise a model defined by parameters.
 * Each agent owns the intrinsic desire to achieve its Goal and become better at it.
 * 
 * Algorithms are firstly classified according to the Goals ((Detector, Tracker, Vehicle Counter, Offense Detector…)) and secondly their belief.
 * 
 * There will exists as many internal space discussions as there is tasks.
 * 
 * Initially the algorithm, it exists in the realm of TOLD and within a Holon could exist infinite combination of models and parameters.
 * 
 * @TODO : Must be somewhere else
 * The algorithm becomes operational when joining a Platform. However, the instance does not join another context but rather provide a replicate.
 * The instance may due to local interactions, evolve to be better than to the original at this specific task and Location.
 * When that happens, the replicate may choose to adapt its belief accordingly and communicate to the original the decision.
 * If the latest follows, it universally and automatically suggests to child instances the modification.
 * They now share a new belief. In the case, it does not follow, the replicate will become a new Holon in the realm of TOLD and becomes a distinct entity
 */
@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class Algorithm extends Paraddis {
  private final AtomicReference<String> name = new AtomicReference<String>();
  
  private final AtomicInteger level = new AtomicInteger();
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.level.set(((((Integer) _get)) == null ? 0 : (((Integer) _get)).intValue()));
    this.name.set(occurrence.parameters[1].toString());
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = this.getID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(((((("ALGORITHM-" + this.name) + "-") + this.level) + "-") + _iD));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    EntityRole _entityRole = new EntityRole(this);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_entityRole);
    if ((this.level.intValue() > 0)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((" I am a copy of " + this.name));
      Object _get_1 = occurrence.parameters[2];
      boolean _matched = false;
      if (Objects.equal(_get_1, "Counter")) {
        _matched=true;
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        CounterRole _counterRole = new CounterRole(this);
        _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.registerBehavior(_counterRole, occurrence.parameters[3], occurrence.parameters[4]);
      }
      if (!_matched) {
        if (Objects.equal(_get_1, "Tracker")) {
          _matched=true;
          Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
          TrackerRole _trackerRole = new TrackerRole(this);
          _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2.registerBehavior(_trackerRole, occurrence.parameters[3], occurrence.parameters[4]);
        }
      }
      if (!_matched) {
        if (Objects.equal(_get_1, "Detector")) {
          _matched=true;
          Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
          DetectorRole _detectorRole = new DetectorRole(this);
          _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3.registerBehavior(_detectorRole, occurrence.parameters[3], occurrence.parameters[4]);
        }
      }
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("I believe in: " + this.name));
    }
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AlgorithmNeeded$1(final AlgorithmNeeded occurrence) {
    String _get = this.name.get();
    boolean _equals = Objects.equal(occurrence.name, _get);
    if (_equals) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("I receive a JoinPlatform " + occurrence.task) + occurrence.belief));
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContext(Algorithm.class, occurrence.contextID, Integer.valueOf(1), occurrence.name, occurrence.task, occurrence.belief, occurrence.dest);
    }
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
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
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
  private void $guardEvaluator$AlgorithmNeeded(final AlgorithmNeeded occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AlgorithmNeeded$1(occurrence));
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  @SyntheticMember
  public Algorithm(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Algorithm(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Algorithm(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
