package be.uclouvain.organisation.told;

import be.uclouvain.organisation.AddMember;
import be.uclouvain.organisation.AuthorizationToJoin;
import be.uclouvain.organisation.TOLDOrganisationInfo;
import be.uclouvain.organisation.told.AccessDatabaseCapacity;
import be.uclouvain.organisation.told.AddEntry;
import be.uclouvain.organisation.told.DeleteEntry;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Logging;
import io.sarl.core.MemberJoined;
import io.sarl.core.MemberLeft;
import io.sarl.core.OpenEventSpace;
import io.sarl.core.OpenEventSpaceSpecification;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Trusted Observations and Learning Database is a database aiming to achieve two goals.
 * 
 *  - Storing the information communicated by a platform.
 * 
 *  - Contains ground truth signals dedicated to learning.
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class TOLDRole extends Behavior {
  private final TreeMap<UUID, OpenEventSpace> Listeners = new TreeMap<UUID, OpenEventSpace>();
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AddEntry$0(final AddEntry occurrence) {
    AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.create(occurrence.key, occurrence.data);
  }
  
  private void $behaviorUnit$DeleteEntry$1(final DeleteEntry occurrence) {
    AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.delete(occurrence.key);
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AddMember$2(final AddMember occurrence) {
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    AgentContext _innerContext = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext();
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    EventSpace _defaultSpace = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1.getInnerContext().getDefaultSpace();
    UUID _uUID = occurrence.getSource().getUUID();
    AuthorizationToJoin _authorizationToJoin = new AuthorizationToJoin(_innerContext, ((OpenEventSpace) _defaultSpace), _uUID);
    occurrence.SourceEventSpace.emit(this.getOwner().getID(), _authorizationToJoin, null);
  }
  
  private void $behaviorUnit$MemberJoined$3(final MemberJoined occurrence) {
    final UUID MemberID = occurrence.agentID;
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    this.Listeners.put(MemberID, _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext().<OpenEventSpace>getOrCreateSpaceWithID(OpenEventSpaceSpecification.class, UUID.randomUUID()));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    AgentContext _innerContext = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1.getInnerContext();
    OpenEventSpace _get = this.Listeners.get(MemberID);
    AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
    Object _read = _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.read(MemberID);
    TOLDOrganisationInfo _tOLDOrganisationInfo = new TOLDOrganisationInfo(_innerContext, _get, _read);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID MemberID;
      
      public $SerializableClosureProxy(final UUID MemberID) {
        this.MemberID = MemberID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, MemberID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, MemberID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, MemberID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_tOLDOrganisationInfo, _function);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.Listeners.get(MemberID).register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.asEventListener());
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$MemberLeft$4(final MemberLeft occurrence) {
    this.Listeners.remove(occurrence.agentID);
  }
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
  }
  
  @Extension
  @ImportedCapacityFeature(AccessDatabaseCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY;
  
  @SyntheticMember
  @Pure
  private AccessDatabaseCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY = $getSkill(AccessDatabaseCapacity.class);
    }
    return $castSkill(AccessDatabaseCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY);
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
  private void $guardEvaluator$MemberLeft(final MemberLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$4(occurrence));
  }
  
  /**
   * Send if available the stored data about it.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AddMember(final AddMember occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AddMember$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$DeleteEntry(final DeleteEntry occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$DeleteEntry$1(occurrence));
  }
  
  /**
   * The platform register the context of a platform
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AddEntry(final AddEntry occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AddEntry$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberJoined(final MemberJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberJoined$3(occurrence));
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
  public TOLDRole(final Agent agent) {
    super(agent);
  }
}
