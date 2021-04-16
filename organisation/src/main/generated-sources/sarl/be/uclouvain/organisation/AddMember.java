package be.uclouvain.organisation;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventSpace;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Request to add a Member
 * The source of this event is the role Analyst
 * 
 * <h4>For an Agent</h4>
 * No particular agent
 * 
 * <h4>For a Behavior</h4>
 * PlatformRole
 * 
 * <h4>For a Skill</h4>
 * No skill receives this event.
 * 
 * <h4>For Agent Members</h4>
 * Not applicable
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
public class AddMember extends Event {
  public final EventSpace SourceEventSpace;
  
  public AddMember(final EventSpace sourceEventSpace) {
    this.SourceEventSpace = sourceEventSpace;
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
   * Returns a String representation of the AddMember event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("SourceEventSpace", this.SourceEventSpace);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1776955116L;
}
