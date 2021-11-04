package be.uclouvain.organisation;

import be.uclouvain.organisation.JoinOrganisation;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class JoinPlatform extends JoinOrganisation {
  public final String location;
  
  public JoinPlatform(final AgentContext context, final String platformName) {
    super(context);
    this.location = platformName;
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
    JoinPlatform other = (JoinPlatform) obj;
    if (!Objects.equals(this.location, other.location))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.location);
    return result;
  }
  
  /**
   * Returns a String representation of the JoinPlatform event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("location", this.location);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 692005685L;
}
