package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.aptitude.messages.BBoxes2DTrackMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class BBoxes2DTrackResult extends Event {
  public BBoxes2DTrackMessage bboxes2DTrack;
  
  public BBoxes2DTrackResult(final BBoxes2DTrackMessage message) {
    this.bboxes2DTrack = message;
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
   * Returns a String representation of the BBoxes2DTrackResult event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("bboxes2DTrack", this.bboxes2DTrack);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 896577671L;
}
