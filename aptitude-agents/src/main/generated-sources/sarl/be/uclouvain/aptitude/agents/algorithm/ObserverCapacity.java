package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.util.BBoxes2D;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import java.util.ArrayList;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface ObserverCapacity extends Capacity {
  public abstract void Signal2Perception(final ArrayList<BBoxes2D> a);
  
  public abstract void displayPerception();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends ObserverCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements ObserverCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void Signal2Perception(final ArrayList<BBoxes2D> a) {
      try {
        ensureCallerInLocalThread();
        this.capacity.Signal2Perception(a);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void displayPerception() {
      try {
        ensureCallerInLocalThread();
        this.capacity.displayPerception();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
