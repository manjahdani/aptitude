package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

@FunctionalInterface
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface ObserverCapacity extends Capacity {
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
