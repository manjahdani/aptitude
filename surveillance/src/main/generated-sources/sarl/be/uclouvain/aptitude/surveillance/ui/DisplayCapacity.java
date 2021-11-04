package be.uclouvain.aptitude.surveillance.ui;

import be.uclouvain.python_access.PythonAccessCapacity;
import be.uclouvain.python_access.messages.BBoxes2DTrackMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface DisplayCapacity extends PythonAccessCapacity {
  public abstract void sendDisplayMessage(final BBoxes2DTrackMessage bboxes2dTrack, final String streamID, final String streamPath, final String roi, final int[] countingLines, final int[] counts, final String name, final String observer, final int sensitivity);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends DisplayCapacity> extends PythonAccessCapacity.ContextAwareCapacityWrapper<C> implements DisplayCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void sendDisplayMessage(final BBoxes2DTrackMessage bboxes2dTrack, final String streamID, final String streamPath, final String roi, final int[] countingLines, final int[] counts, final String name, final String observer, final int sensitivity) {
      try {
        ensureCallerInLocalThread();
        this.capacity.sendDisplayMessage(bboxes2dTrack, streamID, streamPath, roi, countingLines, counts, name, observer, sensitivity);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
