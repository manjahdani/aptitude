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
public interface PythonTwinAccessCapacity extends Capacity {
  public abstract void activateAccess(final JSONObject j);
  
  public abstract void update(final BaseMessage m);
  
  public abstract void updateStreamAccess(final int a);
  
  public abstract void updateStreamAccess(final int actionID, final int newFrameNumber);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends PythonTwinAccessCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements PythonTwinAccessCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void activateAccess(final JSONObject j) {
      try {
        ensureCallerInLocalThread();
        this.capacity.activateAccess(j);
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
    
    public void updateStreamAccess(final int a) {
      try {
        ensureCallerInLocalThread();
        this.capacity.updateStreamAccess(a);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void updateStreamAccess(final int actionID, final int newFrameNumber) {
      try {
        ensureCallerInLocalThread();
        this.capacity.updateStreamAccess(actionID, newFrameNumber);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
