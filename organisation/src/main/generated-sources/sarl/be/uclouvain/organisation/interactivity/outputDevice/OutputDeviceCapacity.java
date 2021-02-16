package be.uclouvain.organisation.interactivity.outputDevice;

import UDPMessages.UDP_Message_Base;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import java.util.UUID;

/**
 * @author manjah
 */
@FunctionalInterface
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface OutputDeviceCapacity extends Capacity {
  public abstract void outputConversion(final UUID idSender, final UDP_Message_Base info);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends OutputDeviceCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements OutputDeviceCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void outputConversion(final UUID idSender, final UDP_Message_Base info) {
      try {
        ensureCallerInLocalThread();
        this.capacity.outputConversion(idSender, info);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
