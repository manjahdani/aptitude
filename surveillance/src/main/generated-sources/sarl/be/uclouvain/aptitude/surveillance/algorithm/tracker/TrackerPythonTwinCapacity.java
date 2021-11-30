package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxe2D;
import be.uclouvain.organisation.platform.ObserverCapacity;
import be.uclouvain.python_access.PythonTwinAccessCapacity;
import be.uclouvain.python_access.messages.BBoxes2DTrackMessage;
import be.uclouvain.python_access.messages.BaseMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import java.util.ArrayList;
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
public interface TrackerPythonTwinCapacity extends ObserverCapacity, PythonTwinAccessCapacity {
  public abstract ArrayList<BBoxe2D> formatConversion(final BBoxes2DTrackMessage a);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends TrackerPythonTwinCapacity> extends ObserverCapacity.ContextAwareCapacityWrapper<C> implements TrackerPythonTwinCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public ArrayList<BBoxe2D> formatConversion(final BBoxes2DTrackMessage a) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.formatConversion(a);
      } finally {
        resetCallerInLocalThread();
      }
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
