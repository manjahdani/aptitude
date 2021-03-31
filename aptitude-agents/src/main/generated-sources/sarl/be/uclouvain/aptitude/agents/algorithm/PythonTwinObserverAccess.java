package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.messages.BaseMessage;
import be.uclouvain.organisation.platform.ObserverCapacity;
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
public interface PythonTwinObserverAccess extends ObserverCapacity {
  public abstract void ActivateAccess(final JSONObject j);
  
  public abstract void UpdateStreamAccess(final int a);
  
  public abstract void update(final BaseMessage m);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends PythonTwinObserverAccess> extends ObserverCapacity.ContextAwareCapacityWrapper<C> implements PythonTwinObserverAccess {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void ActivateAccess(final JSONObject j) {
      try {
        ensureCallerInLocalThread();
        this.capacity.ActivateAccess(j);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void UpdateStreamAccess(final int a) {
      try {
        ensureCallerInLocalThread();
        this.capacity.UpdateStreamAccess(a);
      } finally {
        resetCallerInLocalThread();
      }
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
