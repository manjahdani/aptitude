package be.uclouvain.organisation;

import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class RequestToJoin extends Event {
  /**
   * Default Space of the holon that fired the event
   */
  public final OpenEventSpace emitterSpace;
  
  /**
   * Constructor
   * @param	sid	    The space in the context to join
   * 
   * @FIXME : Develop an generic version
   */
  public RequestToJoin(final OpenEventSpace sid) {
    this.emitterSpace = sid;
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
   * Returns a String representation of the RequestToJoin event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("emitterSpace", this.emitterSpace);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -2759187011L;
}
