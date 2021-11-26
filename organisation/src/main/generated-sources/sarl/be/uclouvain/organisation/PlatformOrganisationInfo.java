package be.uclouvain.organisation;

import be.uclouvain.organisation.OrganisationInfo;
import io.sarl.core.OpenEventSpace;
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
public final class PlatformOrganisationInfo extends OrganisationInfo {
  public final String platformName;
  
  public PlatformOrganisationInfo(final AgentContext ctxt, final OpenEventSpace sid, final String name) {
    super(ctxt, sid);
    this.platformName = name;
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
    PlatformOrganisationInfo other = (PlatformOrganisationInfo) obj;
    if (!Objects.equals(this.platformName, other.platformName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.platformName);
    return result;
  }
  
  /**
   * Returns a String representation of the PlatformOrganisationInfo event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("platformName", this.platformName);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -1076867235L;
}
