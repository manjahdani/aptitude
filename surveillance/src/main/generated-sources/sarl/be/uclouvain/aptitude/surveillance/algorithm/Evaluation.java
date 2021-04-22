package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.PythonAccessCapacity;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;

@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface Evaluation extends PythonAccessCapacity {
  public abstract void sendEvaluationRequest(final String requestID, final String predictions, final String gts);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends Evaluation> extends PythonAccessCapacity.ContextAwareCapacityWrapper<C> implements Evaluation {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void sendEvaluationRequest(final String requestID, final String predictions, final String gts) {
      try {
        ensureCallerInLocalThread();
        this.capacity.sendEvaluationRequest(requestID, predictions, gts);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
