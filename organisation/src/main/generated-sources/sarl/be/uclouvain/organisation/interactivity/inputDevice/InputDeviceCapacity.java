package be.uclouvain.organisation.interactivity.inputDevice;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * @TODO : comment
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
public interface InputDeviceCapacity extends Capacity {
  public abstract void EnableInputStream();
  
  public abstract void DisableInputStream();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends InputDeviceCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements InputDeviceCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void EnableInputStream() {
      try {
        ensureCallerInLocalThread();
        this.capacity.EnableInputStream();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void DisableInputStream() {
      try {
        ensureCallerInLocalThread();
        this.capacity.DisableInputStream();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
