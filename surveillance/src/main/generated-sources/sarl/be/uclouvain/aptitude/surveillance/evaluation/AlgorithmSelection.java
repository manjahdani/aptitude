package be.uclouvain.aptitude.surveillance.evaluation;

import be.uclouvain.aptitude.surveillance.algorithm.util.EvaluationResults;
import be.uclouvain.python_access.messages.EvaluationMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import java.util.UUID;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface AlgorithmSelection extends Capacity {
  public abstract void addResult(final UUID testID, final EvaluationResults s);
  
  public abstract boolean addResult(final UUID testID, final EvaluationMessage e);
  
  public abstract EvaluationResults selectAlgorithm();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends AlgorithmSelection> extends Capacity.ContextAwareCapacityWrapper<C> implements AlgorithmSelection {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void addResult(final UUID testID, final EvaluationResults s) {
      try {
        ensureCallerInLocalThread();
        this.capacity.addResult(testID, s);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public boolean addResult(final UUID testID, final EvaluationMessage e) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.addResult(testID, e);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public EvaluationResults selectAlgorithm() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.selectAlgorithm();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
