package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.Observer;
import be.uclouvain.aptitude.agents.algorithm.messages.BBoxes2DMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import org.json.simple.JSONObject;

@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface Tracking extends Observer {
  public abstract void requestTracker(final JSONObject jsonConfig);
  
  public abstract void getTrack(final BBoxes2DMessage detection);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends Tracking> extends Observer.ContextAwareCapacityWrapper<C> implements Tracking {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void requestTracker(final JSONObject jsonConfig) {
      try {
        ensureCallerInLocalThread();
        this.capacity.requestTracker(jsonConfig);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void getTrack(final BBoxes2DMessage detection) {
      try {
        ensureCallerInLocalThread();
        this.capacity.getTrack(detection);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
