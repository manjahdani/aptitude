package be.uclouvain.organisation.told;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * Provide tools to access an (internal-external) database
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface AccessDatabaseCapacity extends Capacity {
  public abstract void read();
  
  public abstract void create();
  
  public abstract void update();
  
  public abstract void delete();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends AccessDatabaseCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements AccessDatabaseCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void read() {
      try {
        ensureCallerInLocalThread();
        this.capacity.read();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void create() {
      try {
        ensureCallerInLocalThread();
        this.capacity.create();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void update() {
      try {
        ensureCallerInLocalThread();
        this.capacity.update();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void delete() {
      try {
        ensureCallerInLocalThread();
        this.capacity.delete();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
