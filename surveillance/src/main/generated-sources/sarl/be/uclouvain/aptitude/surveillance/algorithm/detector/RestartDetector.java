package be.uclouvain.aptitude.surveillance.algorithm.detector;

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
public class RestartDetector extends Event {
  public String bel;
  
  public RestartDetector(final String a) {
    this.bel = a;
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
    RestartDetector other = (RestartDetector) obj;
    if (!Objects.equals(this.bel, other.bel))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.bel);
    return result;
  }
  
  /**
   * Returns a String representation of the RestartDetector event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("bel", this.bel);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 182067150L;
}
