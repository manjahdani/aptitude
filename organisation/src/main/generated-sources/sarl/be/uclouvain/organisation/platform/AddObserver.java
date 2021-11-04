package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.told.util.AlgorithmInfo;
import io.sarl.core.OpenEventSpace;
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
  public OpenEventSpace missionSpace;
  
  public AlgorithmInfo signalProvider;
  
  public AlgorithmInfo signalReceiver;
  
  public int flag;
  
  public AddObserver(final OpenEventSpace ospace, final AlgorithmInfo s, final AlgorithmInfo r) {
    this.missionSpace = ospace;
    this.signalProvider = s;
    this.signalReceiver = r;
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
    AddObserver other = (AddObserver) obj;
    if (other.flag != this.flag)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.flag);
    return result;
  }
  
  /**
   * Returns a String representation of the AddObserver event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("missionSpace", this.missionSpace);
    builder.add("signalProvider", this.signalProvider);
    builder.add("signalReceiver", this.signalReceiver);
    builder.add("flag", this.flag);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1803859140L;
}
