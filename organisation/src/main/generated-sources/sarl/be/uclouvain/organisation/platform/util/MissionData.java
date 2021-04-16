package be.uclouvain.organisation.platform.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO : to comment
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
  private final UUID MissionID;
  
  private final UUID ExpertID;
  
  private final int Location;
  
  private final int Sensitivity;
  
  public MissionData(final UUID missionID, final UUID expertID, final int location, final int sensitivity) {
    this.MissionID = missionID;
    this.ExpertID = expertID;
    this.Location = location;
    this.Sensitivity = sensitivity;
  }
  
  @Pure
  public UUID getMissionID() {
    return this.MissionID;
  }
  
  @Pure
  public UUID getExpertID() {
    return this.ExpertID;
  }
  
  @Pure
  public int getLocation() {
    return this.Location;
  }
  
  @Pure
  public int getSensitivity() {
    return this.Sensitivity;
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
    if (!Objects.equals(this.MissionID, other.MissionID))
      return false;
    if (!Objects.equals(this.ExpertID, other.ExpertID))
      return false;
    if (other.Location != this.Location)
      return false;
    if (other.Sensitivity != this.Sensitivity)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.MissionID);
    result = prime * result + Objects.hashCode(this.ExpertID);
    result = prime * result + Integer.hashCode(this.Location);
    result = prime * result + Integer.hashCode(this.Sensitivity);
    return result;
  }
}
