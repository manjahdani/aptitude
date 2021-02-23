package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.util.MissionData;
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
public class NewMission extends Event {
  public final EventSpace SourceEventSpace;
  
  public final MissionData missionData;
  
  public NewMission(final EventSpace sourceEventSpace, final MissionData data) {
    this.SourceEventSpace = sourceEventSpace;
    this.missionData = data;
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
   * Returns a String representation of the NewMission event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("SourceEventSpace", this.SourceEventSpace);
    builder.add("missionData", this.missionData);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1758196146L;
}
