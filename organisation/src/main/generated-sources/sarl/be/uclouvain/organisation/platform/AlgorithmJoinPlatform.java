package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.JoinOrganisation;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event (Algorithm)JoinPlatform invites the receiver (an Algorithm) to join a Context
 * 
 * @TODO : make sure it makes sens.
 * 
 * 
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AlgorithmJoinPlatform extends JoinOrganisation {
  public String name;
  
  public String task;
  
  public UUID sourceID;
  
  public AlgorithmJoinPlatform(final AgentContext context, final OpenEventSpace openSpace, final String n, final String t, final UUID sourceID) {
    super(context, openSpace);
    this.name = n;
    this.task = t;
    this.sourceID = sourceID;
  }
  
  public AlgorithmJoinPlatform(final AgentContext context, final String n, final String t, final UUID sourceID) {
    super(context, null);
    this.name = n;
    this.task = t;
    this.sourceID = sourceID;
  }
  
  public AlgorithmJoinPlatform(final AgentContext context, final String n, final String t) {
    super(context, null);
    this.name = n;
    this.task = t;
    this.sourceID = null;
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
    AlgorithmJoinPlatform other = (AlgorithmJoinPlatform) obj;
    if (!Objects.equals(this.name, other.name))
      return false;
    if (!Objects.equals(this.task, other.task))
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
    result = prime * result + Objects.hashCode(this.sourceID);
    return result;
  }
  
  /**
   * Returns a String representation of the AlgorithmJoinPlatform event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("name", this.name);
    builder.add("task", this.task);
    builder.add("sourceID", this.sourceID);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 3814772508L;
}
