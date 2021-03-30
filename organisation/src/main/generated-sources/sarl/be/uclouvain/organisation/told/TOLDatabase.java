/**
 * @Name       : TOLDatabase Capacity
 * @Project    : APTITUDE
 * @Author     : Dani Manjah
 * @Version    : V.0.1
 * @Date       : 22/03/2021
 */
package be.uclouvain.organisation.told;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;

/**
 * The generic capacity TOLDatabase aims to provide the necessary tools to access an (internal-external) database
 */
@SarlSpecification("0.11")
@SarlElementType(20)
@SuppressWarnings("all")
public interface TOLDatabase extends Capacity {
  public abstract void read();
  
  public abstract void create();
  
  public abstract void update();
  
  public abstract void delete();
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends TOLDatabase> extends Capacity.ContextAwareCapacityWrapper<C> implements TOLDatabase {
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
