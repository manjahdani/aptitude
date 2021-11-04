package be.uclouvain.aptitude.surveillance.user;

import UDPMessages.CharacterData;
import UDPMessages.UDP_Message_Base;
import UDPMessages.UDP_Message_RequestSpawn;
import UDPMessages.UDP_Message_RequestWithdraw;
import be.uclouvain.aptitude.surveillance.Expert;
import be.uclouvain.aptitude.surveillance.user.UserElementCapacity;
import be.uclouvain.organisation.HolonicParentInfo;
import be.uclouvain.organisation.interactivity.element.ElementInformation;
import be.uclouvain.organisation.interactivity.element.ElementRole;
import be.uclouvain.organisation.platform.AuthorizedMission;
import be.uclouvain.organisation.platform.NewMission;
import be.uclouvain.organisation.platform.StopMission;
import be.uclouvain.organisation.platform.util.MissionData;
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
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO : provide a description
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $Surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class UserRole extends ElementRole {
  private final HashMap<UUID, CharacterData> entityList = new HashMap<UUID, CharacterData>();
  
  private final int AVAILABLE_EXPERTS = 4;
  
  private final ArrayList<Integer> EXPERTISE_DEGREE = CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(0));
  
  private LinkedList<String> availablePlatforms = new LinkedList<String>();
  
  private OpenEventSpace parentPrivateChanel;
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.availablePlatforms.addAll(((LinkedList<String>) _get));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((" initial platforms " + this.availablePlatforms));
    for (int i = 0; (i < this.AVAILABLE_EXPERTS); i++) {
      {
        final UUID id = UUID.randomUUID();
        String _string = id.toString();
        Integer _get_1 = this.EXPERTISE_DEGREE.get(i);
        CharacterData _characterData = new CharacterData(_string, ((_get_1) == null ? 0 : (_get_1).intValue()), 0, (-1));
        this.entityList.put(id, _characterData);
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
        InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(Expert.class, id, _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext());
      }
    }
    UserElementCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER.setupPhysicalDevice(this.getExpertTypeList());
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$ElementInformation$1(final ElementInformation occurrence) {
    final UDP_Message_Base msg = occurrence.information;
    if ((msg instanceof UDP_Message_RequestSpawn)) {
      final UUID entityID = UUID.fromString(((UDP_Message_RequestSpawn)msg).actorUID);
      UUID _randomUUID = UUID.randomUUID();
      String _get = this.availablePlatforms.get(((UDP_Message_RequestSpawn)msg).sceneID);
      MissionData _missionData = new MissionData(_randomUUID, entityID, _get, 
        this.entityList.get(entityID).evolution);
      NewMission _newMission = new NewMission(_missionData);
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
          UUID _iD = UserRole.this.getOwner().getID();
          return (_uUID != _iD);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, UserRole.this.getOwner().getID());
        }
      };
      this.parentPrivateChanel.emit(this.getOwner().getID(), _newMission, _function);
      CharacterData _get_1 = this.entityList.get(entityID);
      _get_1.screenID = ((UDP_Message_RequestSpawn)msg).sceneID;
      UserElementCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER.updateBehavior(this.getExpertTypeList());
    } else {
      if ((msg instanceof UDP_Message_RequestWithdraw)) {
        UUID MissionID = UUID.fromString(((UDP_Message_RequestWithdraw)msg).actorUID);
        CharacterData _get_2 = this.entityList.get(MissionID);
        _get_2.screenID = (-1);
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        StopMission _stopMission = new StopMission(MissionID);
        class $SerializableClosureProxy_1 implements Scope<Address> {
          
          private final UUID MissionID;
          
          public $SerializableClosureProxy_1(final UUID MissionID) {
            this.MissionID = MissionID;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, MissionID);
          }
        }
        final Scope<Address> _function_1 = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, MissionID);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy_1.class, MissionID);
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_stopMission, _function_1);
        UserElementCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER_1 = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER();
        _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER_1.updateBehavior(this.getExpertTypeList());
      }
    }
  }
  
  private void $behaviorUnit$HolonicParentInfo$2(final HolonicParentInfo occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("I met my parent");
    this.parentPrivateChanel = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.parentPrivateChanel.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
  }
  
  private void $behaviorUnit$AuthorizedMission$3(final AuthorizedMission occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field platformOpenChanel is undefined for the type AuthorizedMission");
  }
  
  @Pure
  public CharacterData[] getExpertTypeList() {
    CharacterData[] characterList = new CharacterData[20];
    int i = 0;
    Set<UUID> _keySet = this.entityList.keySet();
    for (final UUID id : _keySet) {
      {
        CharacterData cD = this.entityList.get(id);
        characterList[i] = cD;
        i++;
      }
    }
    return characterList;
  }
  
  @Pure
  public HashMap<UUID, CharacterData> getEntityList() {
    return this.entityList;
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
  @ImportedCapacityFeature(UserElementCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY;
  
  @SyntheticMember
  @Pure
  private UserElementCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY = $getSkill(UserElementCapacity.class);
    }
    return $castSkill(UserElementCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_USER_USERELEMENTCAPACITY);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AuthorizedMission(final AuthorizedMission occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AuthorizedMission$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$HolonicParentInfo(final HolonicParentInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$HolonicParentInfo$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ElementInformation(final ElementInformation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ElementInformation$1(occurrence));
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
    UserRole other = (UserRole) obj;
    if (other.AVAILABLE_EXPERTS != this.AVAILABLE_EXPERTS)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.AVAILABLE_EXPERTS);
    return result;
  }
  
  @SyntheticMember
  public UserRole(final Agent agent) {
    super(agent);
  }
}
