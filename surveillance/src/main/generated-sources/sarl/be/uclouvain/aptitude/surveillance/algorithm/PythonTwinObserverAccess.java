package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.PythonAccessCapacity;
import be.uclouvain.aptitude.surveillance.algorithm.messages.BaseMessage;
import be.uclouvain.organisation.platform.ObserverCapacity;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import org.json.simple.JSONObject;

/**
 * @TODO: write a description
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface PythonTwinObserverAccess extends ObserverCapacity, PythonAccessCapacity {
  public abstract void UpdateStreamAccess(final int actionID, final int newFrameNumber);
  
  public abstract void ActivateAccess(final JSONObject j);
  
  public abstract void update(final BaseMessage m);
  
  public abstract void UpdateStreamAccess(final int a);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends PythonTwinObserverAccess> extends ObserverCapacity.ContextAwareCapacityWrapper<C> implements PythonTwinObserverAccess {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void UpdateStreamAccess(final int actionID, final int newFrameNumber) {
      try {
        ensureCallerInLocalThread();
        this.capacity.UpdateStreamAccess(actionID, newFrameNumber);
      } finally {
        resetCallerInLocalThread();
      }
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
