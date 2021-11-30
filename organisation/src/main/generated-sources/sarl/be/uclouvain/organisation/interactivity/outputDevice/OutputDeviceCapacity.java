package be.uclouvain.organisation.interactivity.outputDevice;

import UDPMessages.UDP_Message_Base;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import java.util.UUID;

/**
 * Provide the tools to translate and transmit data for external systems.
 * 
 * @author $Author: manjahdani$
 * @version $0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
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
