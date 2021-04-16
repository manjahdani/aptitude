package be.uclouvain.aptitude.surveillance.algorithm;

import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @TODO : Comment
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
  public final AgentContext ContextID;
  
  public final OpenEventSpace SpaceID;
  
  public final String name;
  
  public final String task;
  
  public final String belief;
  
  public final UUID sourceID;
  
  public AlgorithmNeeded(final AgentContext context, final OpenEventSpace Space, final String n, final String t, final String bel, final UUID sourceID) {
    this.ContextID = context;
    this.SpaceID = Space;
    this.task = t;
    this.belief = bel;
    this.name = n;
    this.sourceID = sourceID;
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
    if (!Objects.equals(this.sourceID, other.sourceID))
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
    result = prime * result + Objects.hashCode(this.sourceID);
    return result;
  }
  
  /**
   * Returns a String representation of the AlgorithmNeeded event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("ContextID", this.ContextID);
    builder.add("SpaceID", this.SpaceID);
    builder.add("name", this.name);
    builder.add("task", this.task);
    builder.add("belief", this.belief);
    builder.add("sourceID", this.sourceID);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 6874671415L;
}
