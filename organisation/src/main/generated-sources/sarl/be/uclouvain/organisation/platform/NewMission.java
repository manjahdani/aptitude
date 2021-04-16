package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.util.MissionData;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Request to perform a New Mission
 * The source of this event is the holon User.
 * 
 * <h4>For an Agent</h4>
 * This event is received by the agent's requesting to join another holon context.
 * 
 * <h4>For a Behavior</h4>
 * Each Behavior derived from the AnalystRole
 * 
 * <h4>For a Skill</h4>
 * No skill receives this event.
 * 
 * <h4>For Agent Members</h4>
 * The member agents do not receive this event from the parent agent because they are not yet created when the agent is spawned.@TODO "It's not clear, I do think that the sub-members should get it.
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class newMission extends Event {
  public final MissionData Mission;
  
  public newMission(final MissionData data) {
    this.Mission = data;
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
   * Returns a String representation of the newMission event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("Mission", this.Mission);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 496002769L;
}
