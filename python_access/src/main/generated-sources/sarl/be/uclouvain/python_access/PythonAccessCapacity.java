package be.uclouvain.python_access;

import be.uclouvain.python_access.messages.BaseMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import org.json.simple.JSONObject;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface PythonAccessCapacity extends Capacity {
  public abstract void ActivateAccess(final JSONObject j);
  
  public abstract void update(final BaseMessage m);
  
  public abstract void UpdateStreamAccess(final int a);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends PythonAccessCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements PythonAccessCapacity {
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
    
    public void update(final BaseMessage m) {
      try {
        ensureCallerInLocalThread();
        this.capacity.update(m);
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
  }
}
