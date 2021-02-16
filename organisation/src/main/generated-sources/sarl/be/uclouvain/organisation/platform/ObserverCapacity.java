package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * @author manjah
 */
@FunctionalInterface
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface ObserverCapacity extends Capacity {
  public abstract void Signal2Perception();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends ObserverCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements ObserverCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void Signal2Perception() {
      try {
        ensureCallerInLocalThread();
        this.capacity.Signal2Perception();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
