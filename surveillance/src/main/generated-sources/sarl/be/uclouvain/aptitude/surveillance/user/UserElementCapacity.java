package be.uclouvain.aptitude.surveillance.user;

import UDPMessages.CharacterData;
import be.uclouvain.organisation.interactivity.element.ElementCapacity;
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
public interface UserElementCapacity extends ElementCapacity {
  public abstract void setupPhysicalDevice(final CharacterData[] cd);
  
  public abstract void updateBehavior(final CharacterData[] cd);
  
  /**
   * @ExcludeFromApidoc
   */
  public static class ContextAwareCapacityWrapper<C extends UserElementCapacity> extends ElementCapacity.ContextAwareCapacityWrapper<C> implements UserElementCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void setupPhysicalDevice(final CharacterData[] cd) {
      try {
        ensureCallerInLocalThread();
        this.capacity.setupPhysicalDevice(cd);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void updateBehavior(final CharacterData[] cd) {
      try {
        ensureCallerInLocalThread();
        this.capacity.updateBehavior(cd);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
