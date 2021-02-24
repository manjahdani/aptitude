package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.messages.BaseMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * @author samelson
 */
@FunctionalInterface
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface Observer extends Capacity {
  public abstract void update(final BaseMessage m);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends Observer> extends Capacity.ContextAwareCapacityWrapper<C> implements Observer {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void update(final BaseMessage m) {
      try {
        ensureCallerInLocalThread();
        this.capacity.update(m);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
