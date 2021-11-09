package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.AuthorizationToPerformMission;
import be.uclouvain.organisation.PlatformOrganisationInfo;
import be.uclouvain.organisation.SignalID;
import be.uclouvain.organisation.TOLDOrganisationInfo;
import be.uclouvain.organisation.platform.AddMission;
import be.uclouvain.organisation.platform.AddObserver;
import be.uclouvain.organisation.platform.HyperParametersRequest;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.platform.util.MissionData;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.ExternalContextAccess;
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
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The Analyst gathers information about a mission and collects Events of Interest or more generally
 * intelligence (e.g. statistics) for the Client. The role could recommend actions as well. The concept
 * is not necessarily holonic.
 * 
 * As an example, an Analyst received 175 events of incidents involving bikes and cars from one
 * Algorithm. After analysing the incidents, it could draw recommendations to the Client to take
 * actions such as a preventive campaign or new installations.
 * 
 * We advise Analysts specializing to one scenario; consequently, you need two Analysts to perform the counting of vehicles and detect traffic offenses.
 * However, those Analysts could use the same Observer if they rely on those perceptions.
 * Therefore, we advise an Analyst rely on one and only one Observer (itself composed of other Observers).
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
public class AnalystRole extends Behavior {
  protected AgentContext platformContext;
  
  protected OpenEventSpace platformSpace;
  
  protected AgentContext TOLDContext;
  
  protected OpenEventSpace TOLDSpace;
  
  protected MissionData missionData;
  
  protected OpenEventSpace missionSpace;
  
  private UUID providerID;
  
  /**
   * Receiving this event, the behavior has to update its fields of perception.
   */
  private final ArrayList<String> availableObservers = CollectionLiterals.<String>newArrayList("careful", "dynamic", "dynamic ", 
    "balanced");
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.missionData = ((MissionData) _get);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _get_1 = this.availableObservers.get(this.missionData.getSensitivity());
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("starts mission of vehicle counting with sensitivity:  " + _get_1));
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PlatformOrganisationInfo$1(final PlatformOrganisationInfo occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((((("Joined platform organisation: " + occurrence.spaceID) + " (") + occurrence.context) + ")."));
    UUID _iD = occurrence.context.getID();
    UUID _platformID = this.missionData.getPlatformID();
    boolean _equals = Objects.equal(_iD, _platformID);
    if (_equals) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      UUID _iD_1 = occurrence.context.getID();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("Sending add mission to - " + _iD_1));
      this.platformContext = occurrence.context;
      this.platformSpace = occurrence.spaceID;
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      this.platformSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      UUID _missionID = this.missionData.getMissionID();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("starts mission with spaceID - " + _missionID));
      ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      EventSpace _defaultSpace = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultSpace();
      AddMission _addMission = new AddMission(_defaultSpace, this.missionData);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID $_platformID_1;
        
        public $SerializableClosureProxy(final UUID $_platformID_1) {
          this.$_platformID_1 = $_platformID_1;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, $_platformID_1);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          UUID _platformID = AnalystRole.this.missionData.getPlatformID();
          return Objects.equal(_uUID, _platformID);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, AnalystRole.this.missionData.getPlatformID());
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.platformSpace, _addMission, _function);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info("asks for algorithm performing vehicle counting to platform");
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$AuthorizationToPerformMission$2(final AuthorizationToPerformMission occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = occurrence.missionSpace.getSpaceID().getID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Received authorisation to perform a mission within the space : " + _iD));
    this.missionSpace = occurrence.missionSpace;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.missionSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    AlgorithmInfo _algorithmInfo = new AlgorithmInfo("APTITUDE", "COUNTER");
    AlgorithmInfo _algorithmInfo_1 = new AlgorithmInfo("EXPERT", "ROADANALYST");
    AddObserver _addObserver = new AddObserver(_algorithmInfo, _algorithmInfo_1);
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.platformSpace, _addObserver);
  }
  
  private void $behaviorUnit$SignalID$3(final SignalID occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("received the provider ID \n sending the missionSpace");
    AddMission _addMission = new AddMission(this.missionSpace, null);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_signalID;
      
      public $SerializableClosureProxy(final UUID $_signalID) {
        this.$_signalID = $_signalID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_signalID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, occurrence.signalID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, occurrence.signalID);
      }
    };
    this.platformContext.getDefaultSpace().emit(this.getOwner().getID(), _addMission, _function);
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$TOLDOrganisationInfo$4(final TOLDOrganisationInfo occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((((("Joined TOLD organisation: " + occurrence.spaceID) + " (") + occurrence.context) + ")."));
    this.TOLDContext = occurrence.context;
    this.TOLDSpace = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.TOLDSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$HyperParametersRequest$5(final HyperParametersRequest occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _get = this.availableObservers.get(this.missionData.getSensitivity());
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("received a request for mission parameters \n will send the \r\nuser sensitivity : " + _get));
    this.providerID = occurrence.getSource().getUUID();
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    LinkedList<Integer> _newLinkedList = CollectionLiterals.<Integer>newLinkedList(Integer.valueOf(this.missionData.getSensitivity()));
    MissionSensitivity _missionSensitivity = new MissionSensitivity(_newLinkedList);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_providerID;
      
      public $SerializableClosureProxy(final UUID $_providerID) {
        this.$_providerID = $_providerID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_providerID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, AnalystRole.this.providerID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, AnalystRole.this.providerID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(
      this.platformContext.getDefaultSpace(), _missionSensitivity, _function);
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
  private void $guardEvaluator$AuthorizationToPerformMission(final AuthorizationToPerformMission occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AuthorizationToPerformMission$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$HyperParametersRequest(final HyperParametersRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$HyperParametersRequest$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SignalID(final SignalID occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SignalID$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PlatformOrganisationInfo(final PlatformOrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PlatformOrganisationInfo$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TOLDOrganisationInfo(final TOLDOrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TOLDOrganisationInfo$4(occurrence));
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
    AnalystRole other = (AnalystRole) obj;
    if (!java.util.Objects.equals(this.providerID, other.providerID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.providerID);
    return result;
  }
  
  @SyntheticMember
  public AnalystRole(final Agent agent) {
    super(agent);
  }
}
