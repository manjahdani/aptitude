package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.Identification;
import be.uclouvain.organisation.PlatformOrganisationInfo;
import be.uclouvain.organisation.TOLDOrganisationInfo;
import be.uclouvain.organisation.platform.AddMission;
import be.uclouvain.organisation.platform.HyperParametersRequest;
import be.uclouvain.organisation.platform.LeavePlatform;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.platform.StopMission;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.core.OpenEventSpaceSpecification;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Receives data at a certain stage (Raw, Information, Knowledge, Insight) and process it.
 * Observers are distinct if they process a different Signal or use another Model or both .
 * 
 * It Becomes a multi-layer concept if it involves several entities observing the same target(data).
 * A possible Holonic configuration could have the following layers :
 * 
 * 			- Multiple Sensors at different location analysing the same scene
 *          - Multiple Algorithms with different beliefs analysing the same signal.
 * 
 * Example : let's take a junction equipped with three cameras (C_1  ,C_2,C_3) aiming to provide a list of the cars in the scene.
 * The cameras dispose two algorithms models (M_1 ,M_2). Let us assume, that C_1  uses M_1and M_2 to process the data while others only use M_1.
 * You have four Observers (O_1,O_2,O_3,O_4) = (C_1 M_1; C_1 M_2; C_2 M_1; C_3 M_1).
 * A stable and scalable approach would consider two supplementary Observers to gather this as a Holonic system.
 * On the one hand an Observer O_5 concatenating the perceptions of O_1, O_2 and on the other hand, a sixth Observer for the perceptions of O_5 O_3 O_4.
 * Notice that Observers O_1and O_2 became Signals for O_5, becoming itself a signal for O_6.
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class ObserverRole extends Behavior {
  protected OpenEventSpace privateTOLDSpace;
  
  protected AgentContext TOLDContext;
  
  protected OpenEventSpace privatePlatformSpace;
  
  protected AgentContext platformContext;
  
  protected OpenEventSpace selfSpace = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER().getInnerContext().<OpenEventSpace>getOrCreateSpaceWithID(OpenEventSpaceSpecification.class, 
    UUID.randomUUID());
  
  protected LinkedList<Integer> sensitivity = new LinkedList<Integer>();
  
  protected AlgorithmInfo observerADN;
  
  protected Boolean isMaster = Boolean.valueOf(false);
  
  protected Set<UUID> listeners = Collections.<UUID>synchronizedSet(new HashSet<UUID>());
  
  protected Set<UUID> providers = Collections.<UUID>synchronizedSet(new HashSet<UUID>());
  
  protected Map<UUID, OpenEventSpace> missionSpaceList = Collections.<UUID, OpenEventSpace>synchronizedMap(new HashMap<UUID, OpenEventSpace>());
  
  protected String platformName;
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.observerADN = ((AlgorithmInfo) _get);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.selfSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    this.listeners.add(this.getOwner().getID());
    this.missionSpaceList.put(this.getOwner().getID(), this.selfSpace);
  }
  
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PlatformOrganisationInfo$2(final PlatformOrganisationInfo occurrence) {
    this.platformContext = occurrence.context;
    this.privatePlatformSpace = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.privatePlatformSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    this.platformName = occurrence.platformName;
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    String _name = this.observerADN.getName();
    Identification _identification = new Identification(_name);
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.platformContext.getDefaultSpace(), _identification);
  }
  
  private void $behaviorUnit$TOLDOrganisationInfo$3(final TOLDOrganisationInfo occurrence) {
    this.TOLDContext = occurrence.context;
    this.privateTOLDSpace = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.privateTOLDSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$HyperParametersRequest$4(final HyperParametersRequest occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _substring = occurrence.getSource().getUUID().toString().substring(0, 5);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("received sensitivity request from -" + _substring) + " will send the following sensitivity") + this.sensitivity));
    final Function2<UUID, OpenEventSpace, Boolean> _function = (UUID p1, OpenEventSpace p2) -> {
      SpaceID _spaceID = p2.getSpaceID();
      SpaceID _spaceID_1 = occurrence.getSource().getSpaceID();
      return Boolean.valueOf(Objects.equal(_spaceID, _spaceID_1));
    };
    OpenEventSpace missionSpace = ((OpenEventSpace[])Conversions.unwrapArray(MapExtensions.<UUID, OpenEventSpace>filter(this.missionSpaceList, _function).values(), OpenEventSpace.class))[0];
    LinkedList<Integer> _xifexpression = null;
    if (((this.isMaster) == null ? false : (this.isMaster).booleanValue())) {
      _xifexpression = this.sensitivity;
    } else {
      _xifexpression = CollectionLiterals.<Integer>newLinkedList(this.sensitivity.pop());
    }
    LinkedList<Integer> s = _xifexpression;
    MissionSensitivity _missionSensitivity = new MissionSensitivity(s);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_uUID;
      
      public $SerializableClosureProxy(final UUID $_uUID) {
        this.$_uUID = $_uUID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_uUID);
      }
    }
    final Scope<Address> _function_1 = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        UUID _uUID_1 = occurrence.getSource().getUUID();
        return Objects.equal(_uUID, _uUID_1);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, occurrence.getSource().getUUID());
      }
    };
    missionSpace.emit(this.getOwner().getID(), _missionSensitivity, _function_1);
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AddMission$5(final AddMission occurrence) {
    UUID clientID = occurrence.getSource().getUUID();
    this.missionSpaceList.put(clientID, ((OpenEventSpace) occurrence.SourceEventSpace));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    ((OpenEventSpace) occurrence.SourceEventSpace).register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    boolean _contains = this.listeners.contains(clientID);
    if ((!_contains)) {
      this.listeners.add(clientID);
      OpenEventSpace _get = this.missionSpaceList.get(clientID);
      HyperParametersRequest _hyperParametersRequest = new HyperParametersRequest();
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID clientID;
        
        public $SerializableClosureProxy(final UUID clientID) {
          this.clientID = clientID;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, clientID);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, clientID);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, clientID);
        }
      };
      _get.emit(this.getOwner().getID(), _hyperParametersRequest, _function);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Requesting missinParameters");
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$StopMission$6(final StopMission occurrence) {
    boolean _contains = this.listeners.contains(occurrence.getSource().getUUID());
    if (_contains) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("I received the StopMission");
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      StopMission _stopMission = new StopMission(occurrence.expertID);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_stopMission);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      LeavePlatform _leavePlatform = new LeavePlatform();
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final ObserverRole $_ObserverRole;
        
        public $SerializableClosureProxy(final ObserverRole $_ObserverRole) {
          this.$_ObserverRole = $_ObserverRole;
        }
        
        @Override
        public boolean matches(final Address it) {
          boolean _isMe = $_ObserverRole.isMe(it);
          return Objects.equal(it, Boolean.valueOf(_isMe));
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          boolean _isMe = ObserverRole.this.isMe(it);
          return Objects.equal(it, Boolean.valueOf(_isMe));
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, ObserverRole.this);
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_leavePlatform, _function);
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$LeavePlatform$7(final LeavePlatform occurrence) {
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
  private void $guardEvaluator$LeavePlatform(final LeavePlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$7(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StopMission(final StopMission occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StopMission$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$HyperParametersRequest(final HyperParametersRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$HyperParametersRequest$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AddMission(final AddMission occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AddMission$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PlatformOrganisationInfo(final PlatformOrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PlatformOrganisationInfo$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TOLDOrganisationInfo(final TOLDOrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TOLDOrganisationInfo$3(occurrence));
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ObserverRole other = (ObserverRole) obj;
    if (other.isMaster == null) {
      if (this.isMaster != null)
        return false;
    } else if (this.isMaster == null)
      return false;
    if (other.isMaster != null && other.isMaster.booleanValue() != this.isMaster.booleanValue())
      return false;
    if (!java.util.Objects.equals(this.platformName, other.platformName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.isMaster);
    result = prime * result + java.util.Objects.hashCode(this.platformName);
    return result;
  }
  
  @SyntheticMember
  public ObserverRole(final Agent agent) {
    super(agent);
  }
}
