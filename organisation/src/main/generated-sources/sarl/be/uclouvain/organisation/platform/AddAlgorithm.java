package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.told.util.AlgorithmInfo;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @TODO : comment
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AddAlgorithm extends Event {
  public OpenEventSpace missionSpace;
  
  public AlgorithmInfo algorithmInfo;
  
  public AddAlgorithm(final OpenEventSpace openSpace, final AlgorithmInfo data) {
    this.missionSpace = openSpace;
    this.algorithmInfo = data;
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
   * Returns a String representation of the AddAlgorithm event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("missionSpace", this.missionSpace);
    builder.add("algorithmInfo", this.algorithmInfo);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 452430600L;
}
