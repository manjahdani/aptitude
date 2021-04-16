package be.uclouvain.organisation;

import be.uclouvain.organisation.OrganisationInfo;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class InteractivittyOrganisationInfo extends OrganisationInfo {
  /**
   * Constructor
   * @param	ctxt	The context to join
   * @param	sid	    The space in the context to join
   */
  @SyntheticMember
  public InteractivittyOrganisationInfo(final AgentContext ctxt, final OpenEventSpace sid) {
    super(ctxt, sid);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -390348456L;
}
