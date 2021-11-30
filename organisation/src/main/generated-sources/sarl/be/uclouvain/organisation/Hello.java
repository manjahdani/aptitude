package be.uclouvain.organisation;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * @author manjah
 * Used for debug
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class Hello extends Event {
  @SyntheticMember
  public Hello() {
    super();
  }
  
  @SyntheticMember
  public Hello(final Address source) {
    super(source);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 588368462L;
}
