package be.uclouvain.aptitude.surveillance;

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

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AlgorithmNeeded extends Event {
  public final AgentContext contextID;
  
  public final OpenEventSpace DefaultSpaceID;
  
  public final String name;
  
  public final String task;
  
  public final String belief;
  
  public final UUID dest;
  
  public AlgorithmNeeded(final AgentContext context, final OpenEventSpace Space, final String n, final String t, final String bel, final UUID d) {
    this.contextID = context;
    this.DefaultSpaceID = Space;
    this.task = t;
    this.belief = bel;
    this.name = n;
    this.dest = d;
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
    if (!Objects.equals(this.dest, other.dest))
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
    result = prime * result + Objects.hashCode(this.dest);
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
    builder.add("DefaultSpaceID", this.DefaultSpaceID);
    builder.add("name", this.name);
    builder.add("task", this.task);
    builder.add("belief", this.belief);
    builder.add("dest", this.dest);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 2464926786L;
}
