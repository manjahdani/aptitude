package be.uclouvain.organisation.told;

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
public interface InternalUpdate extends Capacity {
  public abstract void internalUpdate(final Object info);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends InternalUpdate> extends Capacity.ContextAwareCapacityWrapper<C> implements InternalUpdate {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void internalUpdate(final Object info) {
      try {
        ensureCallerInLocalThread();
        this.capacity.internalUpdate(info);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
