package be.uclouvain.organisation.platform.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class MissionData {
  private final UUID entityID;
  
  private final int Location;
  
  private final int Sensitivity;
  
  public MissionData(final UUID id, final int location, final int sensitivity) {
    this.entityID = id;
    this.Location = location;
    this.Sensitivity = sensitivity;
  }
  
  @Pure
  public UUID getEntityID() {
    return this.entityID;
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
    if (!Objects.equals(this.entityID, other.entityID))
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
    result = prime * result + Objects.hashCode(this.entityID);
    result = prime * result + Integer.hashCode(this.Location);
    result = prime * result + Integer.hashCode(this.Sensitivity);
    return result;
  }
}
