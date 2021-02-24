package be.uclouvain.organisation.told.entity;

import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.EventSpace;
import java.util.Collection;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class EntityRole extends Behavior {
  /**
   * The field of perception of the Agent that is VISIBLE.
   */
  protected AgentContext BaseContext;
  
  protected EventSpace BaseSpace;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    this.BaseContext = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext();
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    this.BaseSpace = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace();
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  /**
   * Receiving this event, the behavior has to update its fields of perception.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
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
  
  @SyntheticMember
  public EntityRole(final Agent agent) {
    super(agent);
  }
}
