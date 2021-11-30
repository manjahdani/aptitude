package be.uclouvain.python_access;

import be.uclouvain.python_access.messages.BBoxes2DMessage;
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
public class BBoxes2DResult extends Event {
  public final BBoxes2DMessage bboxes2D;
  
  public final String providerName;
  
  public BBoxes2DResult(final BBoxes2DMessage message) {
    this.bboxes2D = message;
    this.providerName = null;
  }
  
  public BBoxes2DResult(final BBoxes2DMessage message, final String algoName) {
    this.bboxes2D = message;
    this.providerName = algoName;
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
    BBoxes2DResult other = (BBoxes2DResult) obj;
    if (!Objects.equals(this.providerName, other.providerName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.providerName);
    return result;
  }
  
  /**
   * Returns a String representation of the BBoxes2DResult event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("bboxes2D", this.bboxes2D);
    builder.add("providerName", this.providerName);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -2069477177L;
}
