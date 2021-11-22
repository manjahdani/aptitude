package be.uclouvain.organisation.platform.util;

import be.uclouvain.organisation.told.util.AlgorithmInfo;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class MembershipRule {
  private AlgorithmInfo member1;
  
  private AlgorithmInfo member2;
  
  public MembershipRule(final AlgorithmInfo m1, final AlgorithmInfo m2) {
    this.member1 = m1;
    this.member2 = m2;
  }
  
  @Pure
  public AlgorithmInfo getM1() {
    return this.member1;
  }
  
  @Pure
  public AlgorithmInfo getM2() {
    return this.member2;
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
}
