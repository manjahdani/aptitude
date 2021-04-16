package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.Paraddis;
import be.uclouvain.aptitude.surveillance.algorithm.AlgorithmNeeded;
import be.uclouvain.aptitude.surveillance.algorithm.CounterRole;
import be.uclouvain.aptitude.surveillance.algorithm.DetectorRole;
import be.uclouvain.aptitude.surveillance.algorithm.TrackerRole;
import be.uclouvain.organisation.AuthorizationToJoin;
import be.uclouvain.organisation.platform.AlgorithmJoinPlatform;
import be.uclouvain.organisation.told.entity.EntityRole;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
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
 * 
 * 
 * @TODO : The agent may change the belief but not the task. Maybe instead of IFs one should have a more scalable approach by creating specific
 *  What I mean is we should have this algorithm extended and more specific goal-based name. Indeed, it will not be relevant to change the goal for now...
 * Second thoughts on 13/04. I am not very fond of this logic as it does not respect evolutionnary vision in addition. It does not bring
 * enough abstraction, bref while it could programming-wise convenient, it will just be a lack of effort in abstraction..)
 * 
 * @TODO : draw the visual to show the holonic structure (it is not really holonical, actually it is)
 * 
 * 
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SuppressWarnings("potential_field_synchronization_problem")
@SarlSpecification("0.11")
@SarlElementType(19)
public class Algorithm extends Paraddis {
  private AlgorithmInfo ADN = null;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.ADN = ((AlgorithmInfo) _get);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _name = this.ADN.getName();
    int _level = this.ADN.getLevel();
    UUID _iD = this.getID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(((((("ALGORITHM-" + _name) + "-") + Integer.valueOf(_level)) + "-") + _iD));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    EntityRole _entityRole = new EntityRole(this);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_entityRole);
    int _level_1 = this.ADN.getLevel();
    if ((_level_1 == 0)) {
      Object _get_1 = occurrence.parameters[2];
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      ((OpenEventSpace) _get_1).register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.asEventListener());
      Object _get_2 = occurrence.parameters[1];
      HashMap<UUID, AlgorithmInfo> toSpawn = ((HashMap<UUID, AlgorithmInfo>) _get_2);
      Set<UUID> _keySet = toSpawn.keySet();
      for (final UUID e : _keySet) {
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
        InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(Algorithm.class, e, _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext(), toSpawn.get(e).IncrementLevelAndGet());
      }
    }
    int _level_2 = this.ADN.getLevel();
    if ((_level_2 > 1)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _name_1 = this.ADN.getName();
      String _task = this.ADN.getTask();
      String _belief = this.ADN.getBelief();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((((((" My name is " + _name_1) + " I DO ") + _task) + " and my skills are : ") + _belief));
      String _task_1 = this.ADN.getTask();
      if (_task_1 != null) {
        switch (_task_1) {
          case "COUNTER":
            Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
            CounterRole _counterRole = new CounterRole(this);
            _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2.registerBehavior(_counterRole, this.ADN.getBelief(), occurrence.parameters[1], 
              occurrence.parameters[2]);
            break;
          case "TRACKER":
            Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
            TrackerRole _trackerRole = new TrackerRole(this);
            _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3.registerBehavior(_trackerRole, this.ADN.getBelief(), occurrence.parameters[1], 
              occurrence.parameters[2]);
            break;
          case "DETECTOR":
            Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_4 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
            DetectorRole _detectorRole = new DetectorRole(this);
            _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_4.registerBehavior(_detectorRole, this.ADN.getBelief(), occurrence.parameters[1], 
              occurrence.parameters[2]);
            break;
        }
      }
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _name_2 = this.ADN.getName();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("I believe in : " + _name_2));
    }
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AlgorithmNeeded$1(final AlgorithmNeeded occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContext(Algorithm.class, occurrence.ContextID, this.ADN.clone().IncrementLevelAndGet(), occurrence.SpaceID, occurrence.sourceID);
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$AlgorithmNeeded$1(final AlgorithmNeeded it, final AlgorithmNeeded occurrence) {
    String _name = this.ADN.getName();
    boolean _equals = Objects.equal(occurrence.name, _name);
    return _equals;
  }
  
  private void $behaviorUnit$AlgorithmJoinPlatform$2(final AlgorithmJoinPlatform occurrence) {
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    String _belief = this.ADN.getBelief();
    AlgorithmNeeded _algorithmNeeded = new AlgorithmNeeded(occurrence.contextID, occurrence.defaultSpaceID, occurrence.name, _belief, occurrence.task, occurrence.sourceID);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_algorithmNeeded);
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$AlgorithmJoinPlatform$2(final AlgorithmJoinPlatform it, final AlgorithmJoinPlatform occurrence) {
    String _task = this.ADN.getTask();
    boolean _equals = Objects.equal(occurrence.task, _task);
    return _equals;
  }
  
  private void $behaviorUnit$AuthorizationToJoin$3(final AuthorizationToJoin occurrence) {
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.join(occurrence.contextID.getID(), occurrence.defaultSpaceID.getSpaceID().getID());
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
  
  @Extension
  @ImportedCapacityFeature(InnerContextAccess.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS;
  
  @SyntheticMember
  @Pure
  private InnerContextAccess $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS == null || this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS = $getSkill(InnerContextAccess.class);
    }
    return $castSkill(InnerContextAccess.class, this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS);
  }
  
  @Extension
  @ImportedCapacityFeature(ExternalContextAccess.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS;
  
  @SyntheticMember
  @Pure
  private ExternalContextAccess $CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS == null || this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS = $getSkill(ExternalContextAccess.class);
    }
    return $castSkill(ExternalContextAccess.class, this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS);
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
  private void $guardEvaluator$AuthorizationToJoin(final AuthorizationToJoin occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AuthorizationToJoin$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AlgorithmJoinPlatform(final AlgorithmJoinPlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$AlgorithmJoinPlatform$2(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AlgorithmJoinPlatform$2(occurrence));
    }
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AlgorithmNeeded(final AlgorithmNeeded occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$AlgorithmNeeded$1(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AlgorithmNeeded$1(occurrence));
    }
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
