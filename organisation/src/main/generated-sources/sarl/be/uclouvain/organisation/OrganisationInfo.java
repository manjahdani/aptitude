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
 * Generic event to provide
 * The source is usually the Holon that is the organisation
 * 
 * <h4>For an Agent</h4>
 * This event by each holon that joined context
 * 
 * <h4>For a Behavior</h4>
 * Multiple behaviors
 * 
 * <h4>For a Skill</h4>
 * No skill receives this event.
 * 
 * <h4>For Agent Members</h4>
 * The member agents does not receive @TODO "It's not clear, I do think that the sub-members should get it.
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
public class OrganisationInfo extends Event {
  /**
   * Joined Context
   */
  public final AgentContext context;
  
  /**
   * A private space used for the communication
   */
  public final OpenEventSpace privateCommunicationChannel;
  
  /**
   * Constructor
   * @param	ctxt	The context to join
   * @param	sid	    The space in the context to join
   */
  public OrganisationInfo(final AgentContext ctxt, final OpenEventSpace sid) {
    this.context = ctxt;
    this.privateCommunicationChannel = sid;
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
    builder.add("privateCommunicationChannel", this.privateCommunicationChannel);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 187548208L;
}
