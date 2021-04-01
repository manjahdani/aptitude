package be.uclouvain.organisation.told;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventSpace;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AddMission extends Event {
  public final EventSpace SourceEventSpace;
  
  public final int MissionData;
  
  public AddMission(final EventSpace sourceEventSpace, final int missionData) {
    this.SourceEventSpace = sourceEventSpace;
    this.MissionData = missionData;
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
    AddMission other = (AddMission) obj;
    if (other.MissionData != this.MissionData)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.MissionData);
    return result;
  }
  
  /**
   * Returns a String representation of the AddMission event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("SourceEventSpace", this.SourceEventSpace);
    builder.add("MissionData", this.MissionData);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1960911026L;
}
