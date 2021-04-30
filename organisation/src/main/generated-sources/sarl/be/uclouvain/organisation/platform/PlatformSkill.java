package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.MembershipRule;
import be.uclouvain.organisation.platform.PlatformCapacity;
import be.uclouvain.organisation.platform.util.PlatformConfig;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Skill;
import java.util.LinkedList;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class PlatformSkill extends Skill implements PlatformCapacity {
  protected final PlatformConfig WC;
  
  private final LinkedList<String> ObserversList = new LinkedList<String>();
  
  public PlatformSkill(final PlatformConfig wc) {
    this.WC = wc;
  }
  
  public boolean RuleManagement(final Object info) {
    boolean _xifexpression = false;
    if ((info instanceof MembershipRule)) {
      boolean _xifexpression_1 = false;
      boolean _contains = this.ObserversList.contains(((MembershipRule)info).getM1().concat(((MembershipRule)info).getM2()));
      if (_contains) {
        return false;
      } else {
        _xifexpression_1 = this.ObserversList.add(((MembershipRule)info).getM1().concat(((MembershipRule)info).getM2()));
      }
      _xifexpression = _xifexpression_1;
    } else {
      throw new UnsupportedOperationException("Unknown Rule");
    }
    return _xifexpression;
  }
  
  @Pure
  public PlatformConfig getPlatformConfig() {
    return this.WC;
  }
  
  @Override
  public void ConditionActivation() {
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
