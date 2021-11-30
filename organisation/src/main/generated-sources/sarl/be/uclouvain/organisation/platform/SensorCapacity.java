package be.uclouvain.organisation.platform;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * Provide tools to acquire the signal
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface SensorCapacity extends Capacity {
  /**
   * Gather the signals before the pre-processing.
   * 
   * @return : an object Signal
   */
  public abstract void enableDataAcquisition();
  
  /**
   * shut the stream.
   */
  public abstract void disableDataAcquisition();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends SensorCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements SensorCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void enableDataAcquisition() {
      try {
        ensureCallerInLocalThread();
        this.capacity.enableDataAcquisition();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void disableDataAcquisition() {
      try {
        ensureCallerInLocalThread();
        this.capacity.disableDataAcquisition();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
