package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxe2D;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @TODO: Comment
 * 
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class TrackingPerception extends Event {
  public ArrayList<BBoxe2D> perceptions;
  
  public TrackingPerception(final ArrayList<BBoxe2D> p) {
    this.perceptions = p;
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
  
  /**
   * Returns a String representation of the TrackingPerception event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("perceptions", this.perceptions);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 3601438995L;
}
