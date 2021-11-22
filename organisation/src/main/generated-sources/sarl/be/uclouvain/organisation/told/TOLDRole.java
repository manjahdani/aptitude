package be.uclouvain.organisation.told;

import be.uclouvain.organisation.AddMember;
import be.uclouvain.organisation.PlatformOrganisationInfo;
import be.uclouvain.organisation.TOLDOrganisationInfo;
import be.uclouvain.organisation.told.AccessDatabaseCapacity;
import be.uclouvain.organisation.told.AddEntry;
import be.uclouvain.organisation.told.DataEntry;
import be.uclouvain.organisation.told.DeleteEntry;
import be.uclouvain.organisation.told.ReadEntry;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.ExternalContextAccess;
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
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.HashMap;
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
  protected UUID platformID;
  
  protected final HashMap<UUID, OpenEventSpace> privateSpacesListeners = new HashMap<UUID, OpenEventSpace>();
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AddEntry$0(final AddEntry occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _substring = occurrence.key.toString().substring(0, 5);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("creating entry for " + _substring));
    AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.create(occurrence.key, occurrence.data);
  }
  
  private void $behaviorUnit$DeleteEntry$1(final DeleteEntry occurrence) {
    AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.delete(occurrence.key);
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$DataEntry$2(final DataEntry occurrence) {
    AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.update(occurrence.key, occurrence.data);
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$AddMember$3(final AddMember occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nType mismatch: cannot convert from UUID to Event"
      + "\nType mismatch: cannot convert from UUID to UUID"
      + "\nType mismatch: cannot convert from AuthorizationToJoinContext to Scope<Address>");
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$ReadEntry$4(final ReadEntry occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nType mismatch: cannot convert from UUID to Event"
      + "\nType mismatch: cannot convert from UUID to UUID"
      + "\nType mismatch: cannot convert from QueryAnswer to Scope<Address>");
  }
  
  private void $behaviorUnit$MemberJoined$5(final MemberJoined occurrence) {
    UUID memberID = occurrence.agentID;
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    this.privateSpacesListeners.put(memberID, 
      _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext().<OpenEventSpace>getOrCreateSpaceWithID(OpenEventSpaceSpecification.class, UUID.randomUUID()));
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
    AgentContext _innerContext = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1.getInnerContext();
    OpenEventSpace _get = this.privateSpacesListeners.get(memberID);
    AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
    Object _read = _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.read(memberID);
    TOLDOrganisationInfo _tOLDOrganisationInfo = new TOLDOrganisationInfo(_innerContext, _get, _read);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID memberID;
      
      public $SerializableClosureProxy(final UUID memberID) {
        this.memberID = memberID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, memberID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, memberID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, memberID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tOLDOrganisationInfo, _function);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.privateSpacesListeners.get(memberID).register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
  }
  
  private void $behaviorUnit$PlatformOrganisationInfo$6(final PlatformOrganisationInfo occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("Platform organisation - " + occurrence.platformName) + " encountered "));
    this.platformID = occurrence.getSource().getUUID();
    boolean _containsKey = this.privateSpacesListeners.containsKey(this.platformID);
    if ((!_containsKey)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Platform was not encountered");
      synchronized (this) {
        final OpenEventSpace privateChannel = occurrence.privateCommunicationChannel;
        this.privateSpacesListeners.put(this.platformID, privateChannel);
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        privateChannel.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
        ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
        InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
        AgentContext _innerContext = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext();
        OpenEventSpace _get = this.privateSpacesListeners.get(this.platformID);
        AccessDatabaseCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER();
        Object _read = _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_TOLD_ACCESSDATABASECAPACITY$CALLER.read(this.platformID);
        TOLDOrganisationInfo _tOLDOrganisationInfo = new TOLDOrganisationInfo(_innerContext, _get, _read);
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final UUID $_platformID_1;
          
          public $SerializableClosureProxy(final UUID $_platformID_1) {
            this.$_platformID_1 = $_platformID_1;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, $_platformID_1);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, TOLDRole.this.platformID);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, TOLDRole.this.platformID);
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(privateChannel, _tOLDOrganisationInfo, _function);
      }
    }
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$MemberLeft$7(final MemberLeft occurrence) {
    this.privateSpacesListeners.remove(occurrence.agentID);
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
  
  @Extension
  @ImportedCapacityFeature(ExternalContextAccess.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS;
  
  @SyntheticMember
  @Pure
  private ExternalContextAccess $CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS == null || this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS = $getSkill(ExternalContextAccess.class);
    }
    return $castSkill(ExternalContextAccess.class, this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberLeft(final MemberLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$7(occurrence));
  }
  
  /**
   * Send if available the stored data about it.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AddMember(final AddMember occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AddMember$3(occurrence));
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
  private void $guardEvaluator$ReadEntry(final ReadEntry occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ReadEntry$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PlatformOrganisationInfo(final PlatformOrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PlatformOrganisationInfo$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$DataEntry(final DataEntry occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$DataEntry$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberJoined(final MemberJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberJoined$5(occurrence));
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
    TOLDRole other = (TOLDRole) obj;
    if (!java.util.Objects.equals(this.platformID, other.platformID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.platformID);
    return result;
  }
  
  @SyntheticMember
  public TOLDRole(final Agent agent) {
    super(agent);
  }
}
