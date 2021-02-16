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
public interface PlatformCapacity extends Capacity {
  /**
   * Verifies if some conditions in the World have changed.
   */
  public abstract void ConditionActivation();
  
  /**
   * Verifies if the different information provided to the {@code WorldRole} respect the rules of the World.
   */
  public abstract boolean RuleManagement(final Object InfoRule);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends PlatformCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements PlatformCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void ConditionActivation() {
      try {
        ensureCallerInLocalThread();
        this.capacity.ConditionActivation();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public boolean RuleManagement(final Object InfoRule) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.RuleManagement(InfoRule);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
