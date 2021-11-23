package be.uclouvain.organisation.platform;

import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * event AddMission extends AddMember {
 * 
 * val missionData : MissionData
 * 
 * new(sourceEventSpace : OpenEventSpace, missionData : MissionData) {
 * super(sourceEventSpace)
 * this.missionData = missionData
 * }
 * 
 * new (sourceEventSpace : OpenEventSpace)
 * }
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AddMission extends Event {
  public final OpenEventSpace communicationChannel;
  
  public AddMission(final OpenEventSpace sourceEventSpace) {
    this.communicationChannel = sourceEventSpace;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  /**
   * Returns a String representation of the AddMission event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("communicationChannel", this.communicationChannel);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1124324306L;
}
