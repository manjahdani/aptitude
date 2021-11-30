package be.uclouvain.aptitude.surveillance;

import be.uclouvain.aptitude.surveillance.Paraddis;
import be.uclouvain.aptitude.surveillance.TOLDConfig;
import be.uclouvain.aptitude.surveillance.VirtualDatabaseSkill;
import be.uclouvain.aptitude.surveillance.algorithm.Algorithm;
import be.uclouvain.aptitude.surveillance.platform.AgentPlatform;
import be.uclouvain.organisation.JoinPlatform;
import be.uclouvain.organisation.told.AccessDatabaseCapacity;
import be.uclouvain.organisation.told.TOLDRole;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
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
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The agent TOLD keeps record of current and past knowledge.
 * The size of the database is a parameter and therefore we could have local TOLD
 * that communicates to a higher TOLD itself communicating to a bigger database.
 * The TOLD agent creates within its context spaces, as many agents as there are different tasks.
 * Therefore a space for Detectors, Trackers, Vehicle Counter and broadly speaking each class of algorithms.
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class TOLDAgent extends Paraddis {
  private TOLDConfig config;
  
  private final HashMap<String, OpenEventSpace> agentPlatformSpaces = new HashMap<String, OpenEventSpace>();
  
  private VirtualDatabaseSkill S;
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.config = ((TOLDConfig) _get);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = this.getID();
    String _plus = (_iD + "-TOLD");
    int _level = this.config.getLevel();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(((_plus + "-") + Integer.valueOf(_level)));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("ready to share my stored knowledge.");
    VirtualDatabaseSkill _virtualDatabaseSkill = new VirtualDatabaseSkill();
    this.S = this.<VirtualDatabaseSkill>setSkill(_virtualDatabaseSkill, AccessDatabaseCapacity.class);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    TOLDRole _tOLDRole = new TOLDRole(this);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_tOLDRole);
    final Function2<UUID, Object, Boolean> _function = (UUID p1, Object p2) -> {
      return Boolean.valueOf((p2 instanceof AlgorithmInfo));
    };
    Map<UUID, Object> _filter = MapExtensions.<UUID, Object>filter(this.S.database, _function);
    HashMap<UUID, AlgorithmInfo> registeredAlgorithms = new HashMap(_filter);
    ArrayList<String> _init_tasks = this.config.getInit_tasks();
    for (final String t : _init_tasks) {
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
      AlgorithmInfo _algorithmInfo = new AlgorithmInfo(t, "none", t);
      final Function2<UUID, AlgorithmInfo, Boolean> _function_1 = (UUID p1, AlgorithmInfo p2) -> {
        String _task = p2.getTask();
        return Boolean.valueOf(Objects.equal(_task, t));
      };
      Map<UUID, AlgorithmInfo> _filter_1 = MapExtensions.<UUID, AlgorithmInfo>filter(registeredAlgorithms, _function_1);
      HashMap<UUID, AlgorithmInfo> _hashMap = new HashMap<UUID, AlgorithmInfo>(_filter_1);
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContext(Algorithm.class, _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext(), _algorithmInfo, _hashMap);
    }
  }
  
  @SuppressWarnings({ "discouraged_occurrence_readonly_use", "potential_field_synchronization_problem" })
  private void $behaviorUnit$AgentPlatform$1(final AgentPlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("encounters " + occurrence.name) + " agent platform"));
    this.agentPlatformSpaces.put(occurrence.name, occurrence.topic);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    occurrence.topic.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
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
        UUID _iD = TOLDAgent.this.getID();
        return (_uUID != _iD);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, TOLDAgent.this.getID());
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.wake(_agentPlatform, _function);
  }
  
  @SuppressWarnings({ "discouraged_occurrence_readonly_use", "potential_field_synchronization_problem" })
  private void $behaviorUnit$JoinPlatform$2(final JoinPlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _substring = occurrence.contextID.getID().toString().substring(0, 5);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("joins organisation - " + _substring));
    final UUID randomID = UUID.randomUUID();
    int _level = this.config.getLevel();
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    AgentContext _innerContext = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext();
    ArrayList<String> _arrayList = new ArrayList<String>();
    TOLDConfig _tOLDConfig = new TOLDConfig((_level + 1), occurrence.location, _innerContext, _arrayList);
    TOLDConfig holonConfig = this.config.addSubTOLD(_tOLDConfig, randomID);
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(TOLDAgent.class, randomID, occurrence.contextID, holonConfig);
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
  private void $guardEvaluator$JoinPlatform(final JoinPlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$JoinPlatform$2(occurrence));
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
  public TOLDAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public TOLDAgent(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public TOLDAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
