package be.uclouvain.organisation.interactivity.inputDevice;

import UDPMessages.UDP_Message_Base;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @TODO : comment
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
public class DataAcquisition extends Event {
  /**
   * The ID of the Element that has to be updated.
   */
  public final UUID elementID;
  
  /**
   * The information.
   * The {@code Object} type allows to store every kind of data. As it is the {@code Agent }(and not the {@code Behavior}) that has to process the
   * data, it is not necessary for the organisation to know the kind of exchanged data.
   */
  public final UDP_Message_Base data;
  
  public DataAcquisition(final UUID uid, final UDP_Message_Base o) {
    this.elementID = uid;
    this.data = o;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DataAcquisition other = (DataAcquisition) obj;
    if (!Objects.equals(this.elementID, other.elementID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.elementID);
    return result;
  }
  
  /**
   * Returns a String representation of the DataAcquisition event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("elementID", this.elementID);
    builder.add("data", this.data);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 2361058554L;
}
