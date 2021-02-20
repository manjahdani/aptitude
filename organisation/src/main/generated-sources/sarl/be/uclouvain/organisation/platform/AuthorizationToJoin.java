package be.uclouvain.organisation.platform;

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
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AuthorizationToJoin extends Event {
  public final AgentContext contextID;
  
  public final OpenEventSpace defaultSpaceID;
  
  public final UUID entityID;
  
  public final int sensitivity;
  
  public AuthorizationToJoin(final AgentContext context, final OpenEventSpace Space, final UUID id, final int sensitivity) {
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
    if (other.sensitivity != this.sensitivity)
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
    result = prime * result + Integer.hashCode(this.sensitivity);
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
  private static final long serialVersionUID = -3023440594L;
}
