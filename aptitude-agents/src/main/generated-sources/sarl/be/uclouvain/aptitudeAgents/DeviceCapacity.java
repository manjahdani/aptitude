package be.uclouvain.aptitudeAgents;

import UDPMessages.CharacterData;
import be.uclouvain.organisation.interactivity.element.ElementCapacity;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface DeviceCapacity extends ElementCapacity {
  public abstract TreeMap<UUID, CharacterData> getEntityList();
  
  public abstract void updateBehavior();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends DeviceCapacity> extends ElementCapacity.ContextAwareCapacityWrapper<C> implements DeviceCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public TreeMap<UUID, CharacterData> getEntityList() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getEntityList();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void updateBehavior() {
      try {
        ensureCallerInLocalThread();
        this.capacity.updateBehavior();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
