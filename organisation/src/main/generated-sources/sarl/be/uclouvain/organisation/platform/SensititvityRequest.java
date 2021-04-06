package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Request the sensitivity to accomplish the mission
 * The source is the role
 * 
 * <h4>For an Agent</h4>
 * Usually, the
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
 * @version $0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class SensititvityRequest extends Event {
  @SyntheticMember
  public SensititvityRequest() {
    super();
  }
  
  @SyntheticMember
  public SensititvityRequest(final Address source) {
    super(source);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 588368462L;
}
