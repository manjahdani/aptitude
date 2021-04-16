package be.uclouvain.organisation;

import be.uclouvain.organisation.OrganisationInfo;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class TOLDOrganisationInfo extends OrganisationInfo {
  public final Object StoredData;
  
  public TOLDOrganisationInfo(final AgentContext ctxt, final OpenEventSpace sid, final Object data) {
    super(ctxt, sid);
    this.StoredData = data;
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
   * Returns a String representation of the TOLDOrganisationInfo event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("StoredData", this.StoredData);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -2517915252L;
}
