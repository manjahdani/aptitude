package be.uclouvain.organisation.told;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import java.util.UUID;

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
  public abstract Object read(final UUID key);
  
  public abstract void create(final UUID key, final Object data);
  
  public abstract void update(final UUID key, final Object data);
  
  public abstract void delete(final UUID key);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends AccessDatabaseCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements AccessDatabaseCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public Object read(final UUID key) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.read(key);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void create(final UUID key, final Object data) {
      try {
        ensureCallerInLocalThread();
        this.capacity.create(key, data);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void update(final UUID key, final Object data) {
      try {
        ensureCallerInLocalThread();
        this.capacity.update(key, data);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void delete(final UUID key) {
      try {
        ensureCallerInLocalThread();
        this.capacity.delete(key);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
