package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * Provide tools to interpret a signal
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@FunctionalInterface
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface ObserverCapacity extends Capacity {
  /**
   * Gather the signals before the pre-processing.
   * 
   * @param an Object that represents a particular signal. The object must be specified according to the problem.
   * @return: an array of perceptions
   */
  public abstract void Signal2Perception(final Object signal);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends ObserverCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements ObserverCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void Signal2Perception(final Object signal) {
      try {
        ensureCallerInLocalThread();
        this.capacity.Signal2Perception(signal);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
