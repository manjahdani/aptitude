package be.uclouvain.aptitude.surveillance.platform;

import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AgentPlatform extends Event {
  public final String name;
  
  public final UUID id;
  
  public final OpenEventSpace topic;
  
  public AgentPlatform(final String n, final UUID id, final OpenEventSpace o) {
    this.name = n;
    this.topic = o;
    this.id = id;
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
    AgentPlatform other = (AgentPlatform) obj;
    if (!Objects.equals(this.name, other.name))
      return false;
    if (!Objects.equals(this.id, other.id))
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
    result = prime * result + Objects.hashCode(this.id);
    return result;
  }
  
  /**
   * Returns a String representation of the AgentPlatform event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("name", this.name);
    builder.add("id", this.id);
    builder.add("topic", this.topic);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 2398475760L;
}
