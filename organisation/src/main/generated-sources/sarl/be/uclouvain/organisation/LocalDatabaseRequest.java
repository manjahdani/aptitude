package be.uclouvain.organisation;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @TODO : comment
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class LocalDatabaseRequest extends Event {
  public final AgentContext context;
  
  public LocalDatabaseRequest(final AgentContext ctxt) {
    this.context = ctxt;
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
   * Returns a String representation of the LocalDatabaseRequest event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("context", this.context);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1380943668L;
}
