package be.uclouvain.aptitude.detection;

import be.uclouvain.aptitude.other.Observer;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import org.json.simple.JSONObject;

/**
 * @author samelson
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface Detection extends Observer {
  public abstract void requestDetector(final JSONObject jsonConfig);
  
  public abstract void sendAction(final int actionID);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends Detection> extends Observer.ContextAwareCapacityWrapper<C> implements Detection {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void requestDetector(final JSONObject jsonConfig) {
      try {
        ensureCallerInLocalThread();
        this.capacity.requestDetector(jsonConfig);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void sendAction(final int actionID) {
      try {
        ensureCallerInLocalThread();
        this.capacity.sendAction(actionID);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
