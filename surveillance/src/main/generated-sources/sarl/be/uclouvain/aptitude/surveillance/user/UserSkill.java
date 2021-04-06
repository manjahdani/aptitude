/**
 * @Name       : PlatformRole
 * @Project    : APTITUDE
 * @Author     : Dani Manjah
 * @Version    : V.0.1
 * @Date       : 22/03/2021
 */
package be.uclouvain.aptitude.surveillance.user;

import UDPMessages.ActorStats;
import UDPMessages.CharacterData;
import UDPMessages.ObjectData;
import UDPMessages.ScreenData;
import UDPMessages.SpawnAuthorizations;
import UDPMessages.SpawnCharacterAuthorization;
import UDPMessages.SpawnObjectAuthorization;
import UDPMessages.UDP_Message_AckAuthenticateMobile;
import UDPMessages.UDP_Message_Base;
import UDPMessages.UDP_Message_UpdateInventory;
import be.uclouvain.aptitude.surveillance.user.UserElementCapacity;
import be.uclouvain.organisation.interactivity.outputDevice.OutputMsg;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class UserSkill extends Skill implements UserElementCapacity {
  private final String playerID;
  
  public UserSkill(final String userID) {
    this.playerID = userID;
  }
  
  public synchronized void outputSend(final UDP_Message_Base msgOut) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    OutputMsg _outputMsg = new OutputMsg(msgOut);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_outputMsg);
  }
  
  public void updateBehavior(final CharacterData[] cd) {
    ObjectData[] _objectList = this.getObjectList();
    UDP_Message_UpdateInventory _uDP_Message_UpdateInventory = new UDP_Message_UpdateInventory(this.playerID, cd, _objectList);
    this.outputSend(_uDP_Message_UpdateInventory);
  }
  
  @Pure
  public ObjectData[] getObjectList() {
    ObjectData[] objectsList = new ObjectData[1];
    String _string = this.getOwner().getID().toString();
    ObjectData _objectData = new ObjectData(_string, 1, 0);
    objectsList[0] = _objectData;
    return objectsList;
  }
  
  public void InformationAnalysis(final UDP_Message_Base msg) {
  }
  
  public void setupPhysicalDevice(final CharacterData[] cd) {
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
    ObjectData[] _objectList = this.getObjectList();
    UDP_Message_AckAuthenticateMobile _uDP_Message_AckAuthenticateMobile = new UDP_Message_AckAuthenticateMobile(this.playerID, cd, _objectList, allStats, screens);
    this.outputSend(_uDP_Message_AckAuthenticateMobile);
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
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.playerID);
    return result;
  }
}
