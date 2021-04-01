package be.uclouvain.organisation;

import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Positive answer to a request to join a context .
 * The source of this event is the holon that has been requested.
 * 
 * <h4>For an Agent</h4>
 * This event is received by the agent's requesting to join another holon context.
 * 
 * <h4>For a Behavior</h4>
 * Each Behavior instance within the agent receives this event when it is registered for the first time.
 * 
 * <h4>For a Skill</h4>
 * No skill receives this event.
 * 
 * <h4>For Agent Members</h4>
 * The member agents do not receive this event from the parent agent because they are not yet created when the agent is spawned.
 * 
 * @author $Author: manjahdani$
 * @version $0.1$
 * @date $31/03/2021$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AuthorizationToJoin extends Event {
  public final AgentContext contextID;
  
  public final OpenEventSpace defaultSpaceID;
  
  public final UUID entityID;
  
  public final Integer sensitivity;
  
  public AuthorizationToJoin(final AgentContext context, final OpenEventSpace Space, final UUID id, final Integer sensitivity) {
    this.contextID = context;
    this.defaultSpaceID = Space;
    this.entityID = id;
    this.sensitivity = sensitivity;
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
    AuthorizationToJoin other = (AuthorizationToJoin) obj;
    if (!Objects.equals(this.entityID, other.entityID))
      return false;
    if (other.sensitivity == null) {
      if (this.sensitivity != null)
        return false;
    } else if (this.sensitivity == null)
      return false;
    if (other.sensitivity != null && other.sensitivity.intValue() != this.sensitivity.intValue())
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
    result = prime * result + Objects.hashCode(this.sensitivity);
    return result;
  }
  
  /**
   * Returns a String representation of the AuthorizationToJoin event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("contextID", this.contextID);
    builder.add("defaultSpaceID", this.defaultSpaceID);
    builder.add("entityID", this.entityID);
    builder.add("sensitivity", this.sensitivity);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -4420426610L;
}
