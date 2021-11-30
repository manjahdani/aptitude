package be.uclouvain.aptitude.surveillance.ui;

import UDPMessages.UDP_Message_AuthenticateMobile;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AuthenticateUser extends Event {
  public final UUID userID;
  
  public final UDP_Message_AuthenticateMobile data;
  
  public AuthenticateUser(final UUID ID, final UDP_Message_AuthenticateMobile data) {
    this.data = data;
    this.userID = ID;
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
    AuthenticateUser other = (AuthenticateUser) obj;
    if (!Objects.equals(this.userID, other.userID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.userID);
    return result;
  }
  
  /**
   * Returns a String representation of the AuthenticateUser event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("userID", this.userID);
    builder.add("data", this.data);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 95769289L;
}
