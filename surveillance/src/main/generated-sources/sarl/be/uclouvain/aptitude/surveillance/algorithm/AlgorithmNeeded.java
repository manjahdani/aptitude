package be.uclouvain.aptitude.surveillance.algorithm;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Event;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @TODO : Comment
 * 
 * @FIXME Shouldn't we juste use AlgorithmJoinPlatform?
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AlgorithmNeeded extends Event {
  public AgentContext contextID;
  
  public String name;
  
  public String task;
  
  public String belief;
  
  public AlgorithmNeeded(final AgentContext ctxt, final String n, final String t, final String bel) {
    this.contextID = ctxt;
    this.task = t;
    this.belief = bel;
    this.name = n;
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
    AlgorithmNeeded other = (AlgorithmNeeded) obj;
    if (!Objects.equals(this.name, other.name))
      return false;
    if (!Objects.equals(this.task, other.task))
      return false;
    if (!Objects.equals(this.belief, other.belief))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.name);
    result = prime * result + Objects.hashCode(this.task);
    result = prime * result + Objects.hashCode(this.belief);
    return result;
  }
  
  /**
   * Returns a String representation of the AlgorithmNeeded event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("contextID", this.contextID);
    builder.add("name", this.name);
    builder.add("task", this.task);
    builder.add("belief", this.belief);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1902366326L;
}
