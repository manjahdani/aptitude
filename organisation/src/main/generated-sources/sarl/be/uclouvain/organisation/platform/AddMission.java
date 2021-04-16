package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.AddMember;
import be.uclouvain.organisation.platform.util.MissionData;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.EventSpace;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Request to perform a Mission on Platform
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
public class AddMission extends AddMember {
  public final MissionData MissionData;
  
  public AddMission(final EventSpace sourceEventSpace, final MissionData missionData) {
    super(sourceEventSpace);
    this.MissionData = missionData;
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
   * Returns a String representation of the AddMission event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("MissionData", this.MissionData);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -889105359L;
}
