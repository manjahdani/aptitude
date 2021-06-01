package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.ObserverCapacity;
import be.uclouvain.organisation.platform.util.countingLine;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import java.util.TreeMap;

@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface CounterObserverCapacity extends ObserverCapacity {
  /**
   * The capacity is able to display
   */
  public abstract TreeMap<String, countingLine> DisplayPerception(final int actionID);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends CounterObserverCapacity> extends ObserverCapacity.ContextAwareCapacityWrapper<C> implements CounterObserverCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public TreeMap<String, countingLine> DisplayPerception(final int actionID) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.DisplayPerception(actionID);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
