package be.uclouvain.organisation.interactivity.element;

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
public class ElementInformation extends Event {
  /**
   * The information.
   * The {@code Object} type allows to store every kind of data. As it is the {@code Agent }(and not the {@code Behavior}) that has to process the
   * data, it is not necessary for the organisation to know the kind of exchanged data.
   */
  public final UDP_Message_Base information;
  
  /**
   * Constructor
   * 
   * @param	info	The information to store
   */
  public ElementInformation(final UDP_Message_Base info) {
    this.information = info;
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
   * Returns a String representation of the ElementInformation event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("information", this.information);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 5451368657L;
}
