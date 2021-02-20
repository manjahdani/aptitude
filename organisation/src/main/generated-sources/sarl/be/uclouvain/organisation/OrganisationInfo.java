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
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class OrganisationInfo extends Event {
  /**
   * The context joined
   */
  public final AgentContext context;
  
  /**
   * The space used for the communication
   */
  public final OpenEventSpace spaceID;
  
  /**
   * Constructor
   * 
   * @param	ctxt	The context to join
   * @param	sid	The space in the context to join
   */
  public OrganisationInfo(final AgentContext ctxt, final OpenEventSpace sid) {
    this.context = ctxt;
    this.spaceID = sid;
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
   * Returns a String representation of the OrganisationInfo event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("context", this.context);
    builder.add("spaceID", this.spaceID);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -944164767L;
}
