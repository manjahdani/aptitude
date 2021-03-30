package be.uclouvain.organisation.interactivity.element;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Behavior;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class ElementRole extends Behavior {
  @SyntheticMember
  public ElementRole(final Agent agent) {
    super(agent);
  }
}
