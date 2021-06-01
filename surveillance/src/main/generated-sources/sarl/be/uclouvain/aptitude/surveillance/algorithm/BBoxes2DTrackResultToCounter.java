package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.surveillance.algorithm.messages.BBoxes2DTrackMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class BBoxes2DTrackResultToCounter extends BBoxes2DTrackResult {
  public String ObserverName;
  
  public int Sensitivity;
  
  public BBoxes2DTrackResultToCounter(final BBoxes2DTrackMessage t, final String o, final int s) {
    super(t);
    this.ObserverName = o;
    this.Sensitivity = s;
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
    BBoxes2DTrackResultToCounter other = (BBoxes2DTrackResultToCounter) obj;
    if (!Objects.equals(this.ObserverName, other.ObserverName))
      return false;
    if (other.Sensitivity != this.Sensitivity)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.ObserverName);
    result = prime * result + Integer.hashCode(this.Sensitivity);
    return result;
  }
  
  /**
   * Returns a String representation of the BBoxes2DTrackResultToCounter event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("ObserverName", this.ObserverName);
    builder.add("Sensitivity", this.Sensitivity);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -1712854374L;
}
