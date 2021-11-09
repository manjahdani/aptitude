package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.told.util.AlgorithmInfo;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AddObserver extends Event {
  public AlgorithmInfo signalProvider;
  
  public AlgorithmInfo signalReceiver;
  
  public AddObserver(final AlgorithmInfo s, final AlgorithmInfo r) {
    this.signalProvider = s;
    this.signalReceiver = r;
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
   * Returns a String representation of the AddObserver event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("signalProvider", this.signalProvider);
    builder.add("signalReceiver", this.signalReceiver);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 3498506445L;
}
