package be.uclouvain.aptitude.surveillance.algorithm;

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
public class LastFrame extends Event {
  public int frameNumber;
  
  public String pred_file_Path;
  
  public double total_time_detection;
  
  public double total_time_tracking;
  
  public LastFrame(final int f, final String p, final double total_time_detection, final double total_time_tracking) {
    this.frameNumber = f;
    this.pred_file_Path = p;
    this.total_time_detection = total_time_detection;
    this.total_time_tracking = total_time_tracking;
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
    LastFrame other = (LastFrame) obj;
    if (other.frameNumber != this.frameNumber)
      return false;
    if (!Objects.equals(this.pred_file_Path, other.pred_file_Path))
      return false;
    if (Double.doubleToLongBits(other.total_time_detection) != Double.doubleToLongBits(this.total_time_detection))
      return false;
    if (Double.doubleToLongBits(other.total_time_tracking) != Double.doubleToLongBits(this.total_time_tracking))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.frameNumber);
    result = prime * result + Objects.hashCode(this.pred_file_Path);
    result = prime * result + Double.hashCode(this.total_time_detection);
    result = prime * result + Double.hashCode(this.total_time_tracking);
    return result;
  }
  
  /**
   * Returns a String representation of the LastFrame event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("frameNumber", this.frameNumber);
    builder.add("pred_file_Path", this.pred_file_Path);
    builder.add("total_time_detection", this.total_time_detection);
    builder.add("total_time_tracking", this.total_time_tracking);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -2916988161L;
}
