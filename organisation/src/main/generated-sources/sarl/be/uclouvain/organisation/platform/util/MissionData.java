package be.uclouvain.organisation.platform.util;

import be.uclouvain.organisation.platform.util.MissionParameters;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO : to comment and to generalize
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class MissionData {
  private UUID missionID;
  
  private UUID expertID;
  
  private UUID platformID;
  
  private String location;
  
  private MissionParameters missionParam;
  
  public MissionData(final UUID missionID, final UUID expertID, final String location, final MissionParameters missionParameters) {
    this.missionID = missionID;
    this.expertID = expertID;
    this.location = location;
    this.missionParam = missionParameters;
  }
  
  @Pure
  public UUID getPlatformID() {
    return this.platformID;
  }
  
  public MissionData setPlatformID(final UUID id) {
    this.platformID = id;
    return this;
  }
  
  @Pure
  public UUID getMissionID() {
    return this.missionID;
  }
  
  @Pure
  public UUID getExpertID() {
    return this.expertID;
  }
  
  @Pure
  public String getLocation() {
    return this.location;
  }
  
  @Pure
  public int getSensitivity() {
    return this.missionParam.getSensitivity();
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
    MissionData other = (MissionData) obj;
    if (!Objects.equals(this.missionID, other.missionID))
      return false;
    if (!Objects.equals(this.expertID, other.expertID))
      return false;
    if (!Objects.equals(this.platformID, other.platformID))
      return false;
    if (!Objects.equals(this.location, other.location))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.missionID);
    result = prime * result + Objects.hashCode(this.expertID);
    result = prime * result + Objects.hashCode(this.platformID);
    result = prime * result + Objects.hashCode(this.location);
    return result;
  }
}
