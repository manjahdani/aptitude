package be.uclouvain.aptitudeAgents;

import UDPMessages.ActorStats;
import UDPMessages.CharacterData;
import UDPMessages.ObjectData;
import UDPMessages.ScreenData;
import UDPMessages.SpawnAuthorizations;
import UDPMessages.SpawnCharacterAuthorization;
import UDPMessages.SpawnObjectAuthorization;
import UDPMessages.UDP_Message_AckAuthenticateMobile;
import UDPMessages.UDP_Message_Base;
import UDPMessages.UDP_Message_RequestSpawn;
import UDPMessages.UDP_Message_RequestWithdraw;
import UDPMessages.UDP_Message_UpdateInventory;
import be.uclouvain.aptitudeAgents.DeviceCapacity;
import be.uclouvain.organisation.interactivity.outputDevice.OutputMsg;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Skill;
import java.util.ArrayList;
import java.util.Objects;
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
@SarlElementType(22)
@SuppressWarnings("all")
public class UserSkill extends Skill implements DeviceCapacity {
  private final TreeMap<UUID, CharacterData> entityList = new TreeMap<UUID, CharacterData>();
  
  private final TreeMap<UUID, EventSpace> worldlistenersSpaceIDs;
  
  private final ArrayList<UUID> worldList;
  
  private final String playerID;
  
  private final int POPPY_MAXNUMBER = 5;
  
  private final ArrayList<Integer> characterType = CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(0));
  
  public UserSkill(final TreeMap<UUID, EventSpace> map, final ArrayList<UUID> l, final String userID) {
    this.worldlistenersSpaceIDs = map;
    this.worldList = l;
    this.playerID = userID;
  }
  
  public void install() {
    for (int i = 0; (i < this.POPPY_MAXNUMBER); i++) {
      {
        final UUID id = UUID.randomUUID();
        String _string = id.toString();
        Integer _get = this.characterType.get(i);
        CharacterData _characterData = new CharacterData(_string, ((_get) == null ? 0 : (_get).intValue()), 0, (-1));
        this.entityList.put(id, _characterData);
      }
    }
    this.setupPhysicalDevice();
  }
  
  public synchronized void outputSend(final UDP_Message_Base msgOut) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    OutputMsg _outputMsg = new OutputMsg(msgOut);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_outputMsg);
  }
  
  public void setupPhysicalDevice() {
    SpawnObjectAuthorization[] objectsAuthorizations = new SpawnObjectAuthorization[4];
    SpawnObjectAuthorization _spawnObjectAuthorization = new SpawnObjectAuthorization(1, true);
    objectsAuthorizations[0] = _spawnObjectAuthorization;
    SpawnObjectAuthorization _spawnObjectAuthorization_1 = new SpawnObjectAuthorization(2, true);
    objectsAuthorizations[1] = _spawnObjectAuthorization_1;
    SpawnObjectAuthorization _spawnObjectAuthorization_2 = new SpawnObjectAuthorization(3, false);
    objectsAuthorizations[2] = _spawnObjectAuthorization_2;
    SpawnObjectAuthorization _spawnObjectAuthorization_3 = new SpawnObjectAuthorization(4, false);
    objectsAuthorizations[3] = _spawnObjectAuthorization_3;
    SpawnCharacterAuthorization[] charactersAuthorizations = new SpawnCharacterAuthorization[2];
    SpawnCharacterAuthorization _spawnCharacterAuthorization = new SpawnCharacterAuthorization(0, true);
    charactersAuthorizations[0] = _spawnCharacterAuthorization;
    SpawnCharacterAuthorization _spawnCharacterAuthorization_1 = new SpawnCharacterAuthorization(1, true);
    charactersAuthorizations[1] = _spawnCharacterAuthorization_1;
    SpawnAuthorizations spawnAuthorization = new SpawnAuthorizations(false, true, charactersAuthorizations, objectsAuthorizations);
    spawnAuthorization.areCharactersSpawnable = true;
    ScreenData screen1 = new ScreenData(0, 1, spawnAuthorization);
    ScreenData screen2 = new ScreenData(1, 0, spawnAuthorization);
    ScreenData[] screens = new ScreenData[2];
    screens[0] = screen1;
    screens[1] = screen2;
    ActorStats[] allStats = new ActorStats[1];
    String _string = this.getOwner().getID().toString();
    ActorStats _actorStats = new ActorStats(_string, "20");
    allStats[0] = _actorStats;
    CharacterData[] _characterList = this.getCharacterList();
    ObjectData[] _objectList = this.getObjectList();
    UDP_Message_AckAuthenticateMobile _uDP_Message_AckAuthenticateMobile = new UDP_Message_AckAuthenticateMobile(this.playerID, _characterList, _objectList, allStats, screens);
    this.outputSend(_uDP_Message_AckAuthenticateMobile);
  }
  
  public void updateBehavior() {
    CharacterData[] _characterList = this.getCharacterList();
    ObjectData[] _objectList = this.getObjectList();
    UDP_Message_UpdateInventory _uDP_Message_UpdateInventory = new UDP_Message_UpdateInventory(this.playerID, _characterList, _objectList);
    this.outputSend(_uDP_Message_UpdateInventory);
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
  
  public TreeMap<UUID, CharacterData> getEntityList() {
    return this.entityList;
  }
  
  @Pure
  public ObjectData[] getObjectList() {
    ObjectData[] objectsList = new ObjectData[1];
    String _string = this.getOwner().getID().toString();
    ObjectData _objectData = new ObjectData(_string, 1, 0);
    objectsList[0] = _objectData;
    return objectsList;
  }
  
  @Override
  public void InformationAnalysis(final UDP_Message_Base msg) {
    if ((msg instanceof UDP_Message_RequestSpawn)) {
      final UUID worldUUID = this.worldList.get(((UDP_Message_RequestSpawn)msg).sceneID);
      final UUID entityID = UUID.fromString(((UDP_Message_RequestSpawn)msg).actorUID);
      this.updateBehavior();
    } else {
      if ((msg instanceof UDP_Message_RequestWithdraw)) {
        UUID entityID_1 = UUID.fromString(((UDP_Message_RequestWithdraw)msg).actorUID);
      }
    }
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
    UserSkill other = (UserSkill) obj;
    if (!Objects.equals(this.playerID, other.playerID))
      return false;
    if (other.POPPY_MAXNUMBER != this.POPPY_MAXNUMBER)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.playerID);
    result = prime * result + Integer.hashCode(this.POPPY_MAXNUMBER);
    return result;
  }
}
