package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.util.MissionData;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AuthorizedMission extends Event {
  public final MissionData mission;
  
  public final OpenEventSpace platformOpenChannel;
  
  public AuthorizedMission(final MissionData data, final OpenEventSpace sid) {
    this.mission = data;
    this.platformOpenChannel = sid;
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
   * Returns a String representation of the AuthorizedMission event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("mission", this.mission);
    builder.add("platformOpenChannel", this.platformOpenChannel);
  }
}
