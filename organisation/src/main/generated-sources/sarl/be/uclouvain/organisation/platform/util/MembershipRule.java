package be.uclouvain.organisation.platform.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class MembershipRule {
  private String Member1;
  
  private String Member2;
  
  public MembershipRule(final String m1, final String m2) {
    this.Member1 = m1;
    this.Member2 = m2;
  }
  
  @Pure
  public String getM1() {
    return this.Member1;
  }
  
  @Pure
  public String getM2() {
    return this.Member2;
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
    MembershipRule other = (MembershipRule) obj;
    if (!Objects.equals(this.Member1, other.Member1))
      return false;
    if (!Objects.equals(this.Member2, other.Member2))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.Member1);
    result = prime * result + Objects.hashCode(this.Member2);
    return result;
  }
}
