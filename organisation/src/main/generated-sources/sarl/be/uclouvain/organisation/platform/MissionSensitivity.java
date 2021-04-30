package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.LinkedList;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Communicate the MissionSensitivity
 * 
 * The source is an Observer or Analyst role
 * 
 * <h4>For an Agent</h4>
 * Algorithm
 * 
 * <h4>For a Behavior</h4>
 * The receiver is an Observer role
 * 
 * <h4>For a Skill</h4>
 * No skill receives this event.
 * 
 * <h4>For Agent Members</h4>
 * @TODO : clarify ?
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
public class MissionSensitivity extends Event {
  public final LinkedList<Integer> s;
  
  public MissionSensitivity(final LinkedList<Integer> s) {
    this.s = s;
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
   * Returns a String representation of the MissionSensitivity event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("s", this.s);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 653283896L;
}