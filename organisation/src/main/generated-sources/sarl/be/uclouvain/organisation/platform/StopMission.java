package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Warn about the end of the Mission
 * 
 * Multiple sources could use the event @TODO : to precise
 * 
 * @TODO : to precise
 * <h4>For an Agent</h4>
 * This event is received by the agent's requesting to join another holon context.
 * 
 * <h4>For a Behavior</h4>
 * Each Behavior instance that needs to communicate with an organisation that is not in the base_organisation (AnalystRole,...)
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
public class StopMission extends Event {
  public final UUID expertID;
  
  public StopMission(final UUID id) {
    this.expertID = id;
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
    StopMission other = (StopMission) obj;
    if (!Objects.equals(this.expertID, other.expertID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.expertID);
    return result;
  }
  
  /**
   * Returns a String representation of the StopMission event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("expertID", this.expertID);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 767301322L;
}
