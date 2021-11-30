package be.uclouvain.aptitude.surveillance;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.LinkedList;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @author manjah
 * @TODO Comment
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class RecruitTOLD extends Event {
  public final LinkedList<UUID> a;
  
  public RecruitTOLD(final LinkedList<UUID> s) {
    this.a = s;
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
   * Returns a String representation of the RecruitTOLD event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("a", this.a);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -2929759546L;
}
