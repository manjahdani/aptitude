package be.uclouvain.organisation.told.entity;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface EntityCapacity extends Capacity {
  public abstract void read();
  
  public abstract void create();
  
  public abstract void update();
  
  public abstract void delete();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends EntityCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements EntityCapacity {
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
