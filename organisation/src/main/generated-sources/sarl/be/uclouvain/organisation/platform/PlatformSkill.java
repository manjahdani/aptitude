package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.PlatformCapacity;
import be.uclouvain.organisation.platform.util.MembershipRule;
import be.uclouvain.organisation.platform.util.PlatformConfig;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import java.util.LinkedList;
import org.eclipse.xtext.xbase.lib.Extension;
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
    if ((info instanceof MembershipRule)) {
      return this.noDoubleMembership(((MembershipRule)info).getM1(), ((MembershipRule)info).getM2());
    } else {
      throw new UnsupportedOperationException("Unknown Rule");
    }
  }
  
  @Pure
  public PlatformConfig getPlatformConfig() {
    return this.WC;
  }
  
  public boolean noDoubleMembership(final AlgorithmInfo m1, final AlgorithmInfo m2) {
    boolean _contains = this.ObserversList.contains(m1.getFullName().concat(m2.getFullName()));
    if (_contains) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _concat = m1.getFullName().concat(m2.getFullName());
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("\n Partnership of - " + _concat) + " - is not legal "));
      return false;
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _concat_1 = m1.getFullName().concat(m2.getFullName());
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((("\n Partnership of " + _concat_1) + "is not legal "));
      this.ObserversList.add(m1.getName().concat(m2.getName()));
      return true;
    }
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
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
