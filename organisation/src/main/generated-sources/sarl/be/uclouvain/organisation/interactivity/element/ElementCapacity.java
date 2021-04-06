package be.uclouvain.organisation.interactivity.element;

import UDPMessages.UDP_Message_Base;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * Provide the information to decode an external message and transmit to the appropriate role to take further action
 * 
 * @author $Author: manjahdani$
 * @version $0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@FunctionalInterface
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface ElementCapacity extends Capacity {
  public abstract void InformationAnalysis(final UDP_Message_Base msg);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends ElementCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements ElementCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void InformationAnalysis(final UDP_Message_Base msg) {
      try {
        ensureCallerInLocalThread();
        this.capacity.InformationAnalysis(msg);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
