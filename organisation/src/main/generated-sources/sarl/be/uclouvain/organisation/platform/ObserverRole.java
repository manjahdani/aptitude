package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.PlatformOrganisationInfo;
import be.uclouvain.organisation.TOLDOrganisationInfo;
import be.uclouvain.organisation.platform.LeavePlatform;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.platform.SensititvityRequest;
import be.uclouvain.organisation.platform.StopMission;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
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
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.xtext.xbase.lib.Extension;
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
  protected OpenEventSpace TOLDSpace;
  
  protected AgentContext TOLDContext;
  
  protected OpenEventSpace PlatformSpace;
  
  protected AgentContext PlatformContext;
  
  protected UUID observerID;
  
  protected AtomicInteger sensitivity = new AtomicInteger();
  
  protected OpenEventSpace MissionSpace;
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[1];
    this.MissionSpace = ((OpenEventSpace) _get);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.MissionSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    Object _get_1 = occurrence.parameters[2];
    this.observerID = ((UUID) _get_1);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Received Mission Space");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(this.observerID);
  }
  
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The behavior was stopped.");
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PlatformOrganisationInfo$2(final PlatformOrganisationInfo occurrence) {
    this.PlatformContext = occurrence.context;
    this.PlatformSpace = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.PlatformSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    SensititvityRequest _sensititvityRequest = new SensititvityRequest();
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_observerID_1;
      
      public $SerializableClosureProxy(final UUID $_observerID_1) {
        this.$_observerID_1 = $_observerID_1;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_observerID_1);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, ObserverRole.this.observerID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, ObserverRole.this.observerID);
      }
    };
    this.MissionSpace.emit(this.getOwner().getID(), _sensititvityRequest, _function);
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$TOLDOrganisationInfo$3(final TOLDOrganisationInfo occurrence) {
    this.TOLDContext = occurrence.context;
    this.TOLDSpace = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.TOLDSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$SensititvityRequest$4(final SensititvityRequest occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    int _get = this.sensitivity.get();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Thank you for joining the mission. Please use the following sensitivity" + Integer.valueOf(_get)));
    int _get_1 = this.sensitivity.get();
    MissionSensitivity _missionSensitivity = new MissionSensitivity(_get_1);
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
    final Scope<Address> _function = new Scope<Address>() {
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
    this.MissionSpace.emit(this.getOwner().getID(), _missionSensitivity, _function);
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$StopMission$5(final StopMission occurrence) {
    UUID _uUID = occurrence.getSource().getUUID();
    boolean _equals = Objects.equal(_uUID, this.observerID);
    if (_equals) {
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
  private void $behaviorUnit$LeavePlatform$6(final LeavePlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ObserverLeaving");
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StopMission(final StopMission occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StopMission$5(occurrence));
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
  private void $guardEvaluator$SensititvityRequest(final SensititvityRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SensititvityRequest$4(occurrence));
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
    if (!java.util.Objects.equals(this.observerID, other.observerID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.observerID);
    return result;
  }
  
  @SyntheticMember
  public ObserverRole(final Agent agent) {
    super(agent);
  }
}
