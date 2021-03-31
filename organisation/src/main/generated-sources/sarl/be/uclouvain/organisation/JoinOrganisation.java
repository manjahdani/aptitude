/**
 * @Name       : Events
 * @Project    : APTITUDE
 * @Author     : Dani Manjah
 * @Version    : V.0.1
 * @Date       : 22/03/2021
 */
package be.uclouvain.organisation;

import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event (-X)Join(-Organisation) is a generic event invites the receiver to join a Context
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class JoinOrganisation extends Event {
  public final AgentContext contextID;
  
  public final OpenEventSpace defaultSpaceID;
  
  public JoinOrganisation(final AgentContext context, final OpenEventSpace openSpace) {
    this.contextID = context;
    this.defaultSpaceID = openSpace;
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
   * Returns a String representation of the JoinOrganisation event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("contextID", this.contextID);
    builder.add("defaultSpaceID", this.defaultSpaceID);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -298389445L;
}
