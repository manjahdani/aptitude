package be.uclouvain.aptitudeAgents;

import UDPMessages.CharacterData;
import UDPMessages.UDP_Message_Base;
import UDPMessages.UDP_Message_RequestSpawn;
import UDPMessages.UDP_Message_RequestWithdraw;
import be.uclouvain.aptitudeAgents.DeviceCapacity;
import be.uclouvain.aptitudeAgents.MissionData;
import be.uclouvain.aptitudeAgents.newMission;
import be.uclouvain.organisation.interactivity.element.ElementInformation;
import be.uclouvain.organisation.interactivity.element.ElementRole;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.EventSpace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class UserRole extends ElementRole {
  private final TreeMap<UUID, CharacterData> entityList = new TreeMap<UUID, CharacterData>();
  
  private final int AVAILABLE_MODELS = 4;
  
  private final ArrayList<Integer> characterType = CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(0));
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    for (int i = 0; (i < this.AVAILABLE_MODELS); i++) {
      {
        final UUID id = UUID.randomUUID();
        String _string = id.toString();
        Integer _get = this.characterType.get(i);
        CharacterData _characterData = new CharacterData(_string, ((_get) == null ? 0 : (_get).intValue()), 0, (-1));
        this.entityList.put(id, _characterData);
      }
    }
    DeviceCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY$CALLER.setupPhysicalDevice(this.getCharacterList());
  }
  
  private void $behaviorUnit$ElementInformation$1(final ElementInformation occurrence) {
    final UDP_Message_Base msg = occurrence.information;
    if ((msg instanceof UDP_Message_RequestSpawn)) {
      final UUID entityID = UUID.fromString(((UDP_Message_RequestSpawn)msg).actorUID);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      EventSpace _defaultSpace = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultSpace();
      MissionData _missionData = new MissionData(entityID, ((UDP_Message_RequestSpawn)msg).sceneID, this.entityList.get(entityID).evolution);
      newMission _newMission = new newMission(_defaultSpace, _missionData);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_newMission);
      DeviceCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY$CALLER.updateBehavior(this.getCharacterList());
    } else {
      if ((msg instanceof UDP_Message_RequestWithdraw)) {
        UUID entityID_1 = UUID.fromString(((UDP_Message_RequestWithdraw)msg).actorUID);
      }
    }
  }
  
  @Pure
  public CharacterData[] getCharacterList() {
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
  public TreeMap<UUID, CharacterData> getEntityList() {
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
  @ImportedCapacityFeature(DeviceCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY;
  
  @SyntheticMember
  @Pure
  private DeviceCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY = $getSkill(DeviceCapacity.class);
    }
    return $castSkill(DeviceCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDEAGENTS_DEVICECAPACITY);
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
    if (other.AVAILABLE_MODELS != this.AVAILABLE_MODELS)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.AVAILABLE_MODELS);
    return result;
  }
  
  @SyntheticMember
  public UserRole(final Agent agent) {
    super(agent);
  }
}
