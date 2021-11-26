package be.uclouvain.aptitude.surveillance.algorithm.util;

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
public class HyperParameters {
  private int sensitivity;
  
  private boolean optimalSearchEnabled;
  
  public HyperParameters(final int s, final boolean o) {
    this.sensitivity = s;
    this.optimalSearchEnabled = o;
  }
  
  public HyperParameters(final int s) {
    this.sensitivity = s;
    this.optimalSearchEnabled = false;
  }
  
  @Pure
  public boolean isOptimalSearchEnabled() {
    return this.optimalSearchEnabled;
  }
  
  @Pure
  public int getSensitivity() {
    return this.sensitivity;
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
    HyperParameters other = (HyperParameters) obj;
    if (other.sensitivity != this.sensitivity)
      return false;
    if (other.optimalSearchEnabled != this.optimalSearchEnabled)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.sensitivity);
    result = prime * result + Boolean.hashCode(this.optimalSearchEnabled);
    return result;
  }
}
