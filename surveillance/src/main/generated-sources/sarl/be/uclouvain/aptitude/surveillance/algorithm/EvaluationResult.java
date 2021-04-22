package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.messages.EvaluationMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class EvaluationResult extends Event {
  public EvaluationMessage result;
  
  public EvaluationResult(final EvaluationMessage message) {
    this.result = message;
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
   * Returns a String representation of the EvaluationResult event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("result", this.result);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 3245249026L;
}
