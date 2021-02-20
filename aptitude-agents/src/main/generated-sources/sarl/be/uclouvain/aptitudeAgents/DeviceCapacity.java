package be.uclouvain.aptitudeAgents;

import UDPMessages.CharacterData;
import be.uclouvain.organisation.interactivity.element.ElementCapacity;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface DeviceCapacity extends ElementCapacity {
  public abstract void setupPhysicalDevice(final CharacterData[] cd);
  
  public abstract void updateBehavior(final CharacterData[] cd);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends DeviceCapacity> extends ElementCapacity.ContextAwareCapacityWrapper<C> implements DeviceCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void setupPhysicalDevice(final CharacterData[] cd) {
      try {
        ensureCallerInLocalThread();
        this.capacity.setupPhysicalDevice(cd);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void updateBehavior(final CharacterData[] cd) {
      try {
        ensureCallerInLocalThread();
        this.capacity.updateBehavior(cd);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
