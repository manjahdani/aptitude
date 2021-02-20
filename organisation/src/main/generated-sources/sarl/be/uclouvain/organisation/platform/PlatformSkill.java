package be.uclouvain.organisation.platform;

import be.uclouvain.organisation.platform.PlatformCapacity;
import be.uclouvain.organisation.platform.util.PlatformConfig;
import io.sarl.core.InnerContextAccess;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
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
  
  public PlatformSkill(final PlatformConfig wc) {
    this.WC = wc;
  }
  
  public boolean RuleManagement(final Object info) {
    if ((info instanceof String)) {
      InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
      int _memberAgentCount = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getMemberAgentCount();
      int _eNTITY_MAX = this.getPlatformConfig().getENTITY_MAX();
      return ((_memberAgentCount - 1) <= _eNTITY_MAX);
    } else {
      throw new UnsupportedOperationException("Unknown Rule");
    }
  }
  
  @Pure
  public PlatformConfig getPlatformConfig() {
    return this.WC;
  }
  
  @Override
  public void ConditionActivation() {
  }
  
  @Extension
  @ImportedCapacityFeature(InnerContextAccess.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS;
  
  @SyntheticMember
  @Pure
  private InnerContextAccess $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS == null || this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS = $getSkill(InnerContextAccess.class);
    }
    return $castSkill(InnerContextAccess.class, this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS);
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
