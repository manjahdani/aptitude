package be.uclouvain.organisation;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @author manjah
 * @TODO Update comments
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AuthorizationToJoinContext extends Event {
  /**
   * Context of the holon that fired the event
   */
  public final UUID contextID;
  
  /**
   * Default Space of the holon that fired the event
   */
  public final UUID defaultSpaceID;
  
  /**
   * Constructor
   * @param	ctxt	The context to join
   * @param	sid	    The space in the context to join
   * 
   * @FIXME : Develop an generic version
   */
  public AuthorizationToJoinContext(final UUID ctxt, final UUID sid) {
    this.contextID = ctxt;
    this.defaultSpaceID = sid;
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
    AuthorizationToJoinContext other = (AuthorizationToJoinContext) obj;
    if (!Objects.equals(this.contextID, other.contextID))
      return false;
    if (!Objects.equals(this.defaultSpaceID, other.defaultSpaceID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.contextID);
    result = prime * result + Objects.hashCode(this.defaultSpaceID);
    return result;
  }
  
  /**
   * Returns a String representation of the AuthorizationToJoinContext event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("contextID", this.contextID);
    builder.add("defaultSpaceID", this.defaultSpaceID);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1421447363L;
}
