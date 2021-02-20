package be.uclouvain.aptitudeAgents;

import be.uclouvain.aptitudeAgents.newMission;
import be.uclouvain.organisation.platform.AnalystRole;
import be.uclouvain.organisation.platform.AuthorizationToJoin;
import be.uclouvain.organisation.told.AddEntity;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class Expert extends Agent {
  private TreeMap<UUID, EventSpace> platformlistenersSpaceIDs;
  
  private ArrayList<UUID> platformList;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = this.getID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(("Expert-" + _iD));
    Object _get = occurrence.parameters[0];
    this.platformlistenersSpaceIDs = ((TreeMap<UUID, EventSpace>) _get);
    Object _get_1 = occurrence.parameters[1];
    this.platformList = ((ArrayList<UUID>) _get_1);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Expert agent was started.");
  }
  
  private void $behaviorUnit$newMission$1(final newMission occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("I have a new mission");
    final UUID PlatformID = this.platformList.get(occurrence.missionData.getLocation());
    final EventSpace PlatformSpace = this.platformlistenersSpaceIDs.get(PlatformID);
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    EventSpace _defaultSpace = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultSpace();
    int _sensitivity = occurrence.missionData.getSensitivity();
    AddEntity _addEntity = new AddEntity(_defaultSpace, _sensitivity);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID PlatformID;
      
      public $SerializableClosureProxy(final UUID PlatformID) {
        this.PlatformID = PlatformID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, PlatformID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, PlatformID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, PlatformID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(PlatformSpace, _addEntity, _function);
  }
  
  private void $behaviorUnit$AuthorizationToJoin$2(final AuthorizationToJoin occurrence) {
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    AnalystRole _analystRole = new AnalystRole(this);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_analystRole, Integer.valueOf(occurrence.sensitivity));
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.join(occurrence.contextID.getID(), occurrence.defaultSpaceID.getSpaceID().getID());
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AuthorizationToJoin$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$newMission(final newMission occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$newMission$1(occurrence));
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
  public Expert(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Expert(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Expert(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
