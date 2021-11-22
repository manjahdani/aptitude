package be.uclouvain.organisation;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class Identification extends Event {
  public final String fullName;
  
  public Identification(final String n) {
    this.fullName = n;
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
    Identification other = (Identification) obj;
    if (!Objects.equals(this.fullName, other.fullName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.fullName);
    return result;
  }
  
  /**
   * Returns a String representation of the Identification event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("fullName", this.fullName);
  }
}
