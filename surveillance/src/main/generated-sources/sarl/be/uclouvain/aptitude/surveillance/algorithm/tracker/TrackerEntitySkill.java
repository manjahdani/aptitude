package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.organisation.told.InternalUpdate;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Skill;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class TrackerEntitySkill extends Skill implements InternalUpdate {
  public void internalUpdate(final Object info) {
  }
  
  @SyntheticMember
  public TrackerEntitySkill() {
    super();
  }
  
  @SyntheticMember
  public TrackerEntitySkill(final Agent agent) {
    super(agent);
  }
}
