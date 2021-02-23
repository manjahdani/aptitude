package be.uclouvain.aptitude.agents.algorithm;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class PartnerDetectionFound extends Event {
  public String partnerName;
  
  public PartnerDetectionFound(final String partnerName) {
    this.partnerName = partnerName;
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
    PartnerDetectionFound other = (PartnerDetectionFound) obj;
    if (!Objects.equals(this.partnerName, other.partnerName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.partnerName);
    return result;
  }
  
  /**
   * Returns a String representation of the PartnerDetectionFound event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("partnerName", this.partnerName);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1156500152L;
}
