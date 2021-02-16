package be.uclouvain.organisation.interactivity.outputDevice;

import UDPMessages.UDP_Message_Base;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class OutputMsg extends Event {
  /**
   * The information.
   * The {@code Object} type allows to store every kind of data. As it is the {@code Agent }(and not the {@code Behavior}) that has to process the
   * data, it is not necessary for the organisation to know the kind of exchanged data.
   */
  public final UDP_Message_Base Information;
  
  /**
   * Constructor
   * 
   * @param	info	The information to store
   */
  public OutputMsg(final UDP_Message_Base info) {
    this.Information = info;
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
   * Returns a String representation of the OutputMsg event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("Information", this.Information);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 2824270001L;
}
