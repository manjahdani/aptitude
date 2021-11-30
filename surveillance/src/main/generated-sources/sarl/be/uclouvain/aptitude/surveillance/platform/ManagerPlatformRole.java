package be.uclouvain.aptitude.surveillance.platform;

import be.uclouvain.aptitude.surveillance.platform.AgentPlatform;
import be.uclouvain.organisation.JoinPlatform;
import be.uclouvain.organisation.LocalDatabaseRequest;
import be.uclouvain.organisation.platform.PlatformRole;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO To complete
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class ManagerPlatformRole extends PlatformRole {
  private final HashMap<String, OpenEventSpace> subPlatformsOpenSpaces = new HashMap<String, OpenEventSpace>();
  
  private final HashMap<UUID, String> subPlatformsIDs = new HashMap<UUID, String>();
  
  private void $behaviorUnit$AgentPlatform$0(final AgentPlatform occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    boolean _isInDefaultSpace = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.isInDefaultSpace(occurrence);
    if ((!_isInDefaultSpace)) {
      this.subPlatformsIDs.put(occurrence.id, occurrence.name);
      boolean _containsKey = this.subPlatformsOpenSpaces.containsKey(occurrence.name);
      if ((!_containsKey)) {
        this.subPlatformsOpenSpaces.put(occurrence.name, occurrence.topic);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        AgentPlatform _agentPlatform = new AgentPlatform(occurrence.name, occurrence.id, occurrence.topic);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_agentPlatform);
      }
    }
  }
  
  private void $behaviorUnit$LocalDatabaseRequest$1(final LocalDatabaseRequest occurrence) {
    OpenEventSpace _get = this.privateSpacesListeners.get(this.TOLDID);
    String _get_1 = this.subPlatformsIDs.get(occurrence.getSource().getUUID());
    JoinPlatform _joinPlatform = new JoinPlatform(occurrence.context, _get_1);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_TOLDID_1;
      
      public $SerializableClosureProxy(final UUID $_TOLDID_1) {
        this.$_TOLDID_1 = $_TOLDID_1;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_TOLDID_1);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, ManagerPlatformRole.this.TOLDID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, ManagerPlatformRole.this.TOLDID);
      }
    };
    _get.emit(this.getOwner().getID(), _joinPlatform, _function);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentPlatform(final AgentPlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentPlatform$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LocalDatabaseRequest(final LocalDatabaseRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LocalDatabaseRequest$1(occurrence));
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
  public ManagerPlatformRole(final Agent agent) {
    super(agent);
  }
}
