package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.Paraddis;
import be.uclouvain.aptitude.surveillance.algorithm.AlgorithmEntity;
import be.uclouvain.aptitude.surveillance.algorithm.AlgorithmNeeded;
import be.uclouvain.aptitude.surveillance.algorithm.DetectorRole;
import be.uclouvain.aptitude.surveillance.algorithm.counter.CompetitiveCounterRole;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackerRole;
import be.uclouvain.aptitude.surveillance.platform.AgentAlgorithm;
import be.uclouvain.aptitude.surveillance.platform.AgentPlatform;
import be.uclouvain.organisation.AuthorizationToJoin;
import be.uclouvain.organisation.platform.AlgorithmJoinPlatform;
import be.uclouvain.organisation.told.entity.EntityRole;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import io.sarl.core.Behaviors;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.core.OpenEventSpaceSpecification;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
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
  
  private final HashMap<String, OpenEventSpace> agentPlatformSpaces = new HashMap<String, OpenEventSpace>();
  
  private HashMap<AgentContext, UUID> subHolons = new HashMap<AgentContext, UUID>();
  
  private OpenEventSpace openChanel;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.ADN = ((AlgorithmInfo) _get);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = this.getID();
    String _plus = (_iD + "-ALGORITHM-");
    String _name = this.ADN.getName();
    int _level = this.ADN.getLevel();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName((((_plus + _name) + "-") + Integer.valueOf(_level)));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    EntityRole _entityRole = new EntityRole(this);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_entityRole);
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    this.openChanel = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext().<OpenEventSpace>getOrCreateSpaceWithID(OpenEventSpaceSpecification.class, UUID.randomUUID());
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.openChanel.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.asEventListener());
    int _level_1 = this.ADN.getLevel();
    if ((_level_1 == 0)) {
      Object _get_1 = occurrence.parameters[1];
      HashMap<UUID, AlgorithmInfo> toSpawn = ((HashMap<UUID, AlgorithmInfo>) _get_1);
      Set<UUID> _keySet = toSpawn.keySet();
      for (final UUID e : _keySet) {
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
        InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(Algorithm.class, e, _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1.getInnerContext(), toSpawn.get(e).IncrementLevelAndGet());
      }
    }
    int _level_2 = this.ADN.getLevel();
    if ((_level_2 > 1)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _task = this.ADN.getTask();
      String _belief = this.ADN.getBelief();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(((("new atomic agent with intention: " + _task) + " & belief : ") + _belief));
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      AlgorithmEntity _algorithmEntity = new AlgorithmEntity(this);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2.registerBehavior(_algorithmEntity);
      String _task_1 = this.ADN.getTask();
      if (_task_1 != null) {
        switch (_task_1) {
          case "COUNTER":
            Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
            CompetitiveCounterRole _competitiveCounterRole = new CompetitiveCounterRole(this);
            _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3.registerBehavior(_competitiveCounterRole, this.ADN);
            break;
          case "TRACKER":
            Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_4 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
            TrackerRole _trackerRole = new TrackerRole(this);
            _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_4.registerBehavior(_trackerRole, this.ADN);
            break;
          case "DETECTOR":
            Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_5 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
            DetectorRole _detectorRole = new DetectorRole(this);
            _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_5.registerBehavior(_detectorRole, this.ADN);
            break;
        }
      }
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _name_1 = this.ADN.getName();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("I believe in : " + _name_1));
    }
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AgentPlatform$1(final AgentPlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("encounters : " + occurrence.name) + " agent"));
    this.agentPlatformSpaces.put(occurrence.name, occurrence.topic);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    occurrence.topic.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    String _name = this.ADN.getName();
    AgentAlgorithm _agentAlgorithm = new AgentAlgorithm(_name, this.openChanel);
    occurrence.topic.emit(this.getID(), _agentAlgorithm, null);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    AgentPlatform _agentPlatform = new AgentPlatform(occurrence.name, occurrence.id, occurrence.topic);
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
        UUID _iD = Algorithm.this.getID();
        return (_uUID != _iD);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, Algorithm.this.getID());
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.wake(_agentPlatform, _function);
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AlgorithmNeeded$2(final AlgorithmNeeded occurrence) {
    String _name = this.ADN.getName();
    if ((occurrence.name == _name)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _substring = occurrence.getSource().getUUID().toString().substring(0, 5);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(
        ((((((("clones image with intention :" + occurrence.task) + " - name - ") + occurrence.name) + "& belief : ") + 
          occurrence.belief) + "from ") + _substring));
      final UUID cloneID = UUID.randomUUID();
      this.subHolons.put(occurrence.contextID, cloneID);
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(Algorithm.class, cloneID, occurrence.contextID, this.ADN.cloneChild());
    }
  }
  
  private void $behaviorUnit$AlgorithmJoinPlatform$3(final AlgorithmJoinPlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _substring = occurrence.getSource().getUUID().toString().substring(0, 5);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("receives request to join platform from : " + _substring));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    String _task = this.ADN.getTask();
    String _belief = this.ADN.getBelief();
    AlgorithmNeeded _algorithmNeeded = new AlgorithmNeeded(occurrence.contextID, occurrence.name, _task, _belief);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_algorithmNeeded);
  }
  
  private void $behaviorUnit$AuthorizationToJoin$4(final AuthorizationToJoin occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _uUID = occurrence.getSource().getUUID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("receives authorisation to join" + _uUID));
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
  private void $guardEvaluator$AgentPlatform(final AgentPlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentPlatform$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AuthorizationToJoin(final AuthorizationToJoin occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AuthorizationToJoin$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AlgorithmJoinPlatform(final AlgorithmJoinPlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AlgorithmJoinPlatform$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AlgorithmNeeded(final AlgorithmNeeded occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AlgorithmNeeded$2(occurrence));
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
