package be.uclouvain.aptitude.surveillance.evaluation;

import be.uclouvain.python_access.PythonAccessCapacity;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;

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
public interface Evaluation extends PythonAccessCapacity {
  public abstract void sendEvaluationRequest(final String requestID, final String predictions, final String gts);
  
  public abstract void sendEvaluationRequest(final String requestID, final String predictions, final String gts, final int maxFrame);
  
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
    
    public void sendEvaluationRequest(final String requestID, final String predictions, final String gts, final int maxFrame) {
      try {
        ensureCallerInLocalThread();
        this.capacity.sendEvaluationRequest(requestID, predictions, gts, maxFrame);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
