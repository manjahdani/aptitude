package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.python_access.BBoxes2DTrackResult;
import be.uclouvain.python_access.messages.BBoxes2DTrackMessage;
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
  public String observerName;
  
  public int sensitivity;
  
  public BBoxes2DTrackResultToCounter(final BBoxes2DTrackMessage t, final String o, final int s) {
    super(t);
    this.observerName = o;
    this.sensitivity = s;
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
    if (!Objects.equals(this.observerName, other.observerName))
      return false;
    if (other.sensitivity != this.sensitivity)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.observerName);
    result = prime * result + Integer.hashCode(this.sensitivity);
    return result;
  }
  
  /**
   * Returns a String representation of the BBoxes2DTrackResultToCounter event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("observerName", this.observerName);
    builder.add("sensitivity", this.sensitivity);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -538773830L;
}
