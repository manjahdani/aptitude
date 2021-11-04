package be.uclouvain.organisation.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public abstract class HolonicConfig {
  private AtomicInteger level;
  
  /**
   * Constructor
   * 
   * @param	lvl	The hierarchical level of the Platform(0 is the highest level)
   */
  public HolonicConfig(final int l) {
    AtomicInteger _atomicInteger = new AtomicInteger(l);
    this.level = _atomicInteger;
  }
  
  @Pure
  public int getLevel() {
    return this.level.get();
  }
  
  public void setLevel(final int a) {
    this.level.set(a);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
}
