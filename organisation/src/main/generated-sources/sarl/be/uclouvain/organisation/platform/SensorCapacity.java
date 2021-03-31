package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface SensorCapacity extends Capacity {
  public abstract void enableDataAcquisition(final Object o);
  
  public abstract void disableDataAcquisition(final Object o);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends SensorCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements SensorCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void enableDataAcquisition(final Object o) {
      try {
        ensureCallerInLocalThread();
        this.capacity.enableDataAcquisition(o);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void disableDataAcquisition(final Object o) {
      try {
        ensureCallerInLocalThread();
        this.capacity.disableDataAcquisition(o);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
