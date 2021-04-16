package be.uclouvain.aptitude.surveillance;

import be.uclouvain.aptitude.surveillance.CommitSuicide;
import be.uclouvain.organisation.Hello;
import com.google.common.base.Objects;
import io.sarl.core.AgentTask;
import io.sarl.core.Destroy;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class Paraddis extends Agent {
  protected String AgentType = "Unknown";
  
  private void $behaviorUnit$Destroy$0(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("agent was stopped.");
  }
  
  private void $behaviorUnit$CommitSuicide$1(final CommitSuicide occurrence) {
    if ((this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER().hasMemberAgent() && (!this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER().getActiveTasks().contains("wait-task")))) {
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      final AgentTask waitTask = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.task("wait-task");
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      final Procedure1<Agent> _function = (Agent it) -> {
        InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
        boolean _hasMemberAgent = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.hasMemberAgent();
        if ((!_hasMemberAgent)) {
          this.CancelTasks();
          Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(waitTask);
          Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
        } else {
          InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
          CommitSuicide _commitSuicide = new CommitSuicide();
          class $SerializableClosureProxy implements Scope<Address> {
            
            private final Paraddis $_Paraddis;
            
            public $SerializableClosureProxy(final Paraddis $_Paraddis) {
              this.$_Paraddis = $_Paraddis;
            }
            
            @Override
            public boolean matches(final Address it) {
              boolean _isMe = (it != null && $_Paraddis.getID().equals(it.getUUID()));
              return (!Objects.equal(it, Boolean.valueOf(_isMe)));
            }
          }
          final Scope<Address> _function_1 = new Scope<Address>() {
            @Override
            public boolean matches(final Address it) {
              boolean _isMe = (it != null && Paraddis.this.getID().equals(it.getUUID()));
              return (!Objects.equal(it, Boolean.valueOf(_isMe)));
            }
            private Object writeReplace() throws ObjectStreamException {
              return new SerializableProxy($SerializableClosureProxy.class, Paraddis.this);
            }
          };
          _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1.getInnerContext().getDefaultSpace().emit(this.getID(), _commitSuicide, _function_1);
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.every(waitTask, 2000, _function);
    } else {
      this.CancelTasks();
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
    }
  }
  
  protected void CancelTasks() {
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    boolean _isEmpty = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.getActiveTasks().isEmpty();
    if ((!_isEmpty)) {
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      ConcurrentSkipListSet<String> _activeTasks = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.getActiveTasks();
      for (final String e : _activeTasks) {
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3.task(e));
      }
    }
  }
  
  private void $behaviorUnit$Hello$2(final Hello occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("I received Hello");
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$Hello$2(final Hello it, final Hello occurrence) {
    boolean _isFromMe = (occurrence != null && this.getID().equals(occurrence.getSource().getUUID()));
    return (!_isFromMe);
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
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
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Hello(final Hello occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$Hello$2(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Hello$2(occurrence));
    }
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$CommitSuicide(final CommitSuicide occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$CommitSuicide$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$0(occurrence));
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Paraddis other = (Paraddis) obj;
    if (!java.util.Objects.equals(this.AgentType, other.AgentType))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.AgentType);
    return result;
  }
  
  @SyntheticMember
  public Paraddis(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Paraddis(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Paraddis(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
