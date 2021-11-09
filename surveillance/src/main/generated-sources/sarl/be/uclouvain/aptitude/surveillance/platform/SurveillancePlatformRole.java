package be.uclouvain.aptitude.surveillance.platform;

import be.uclouvain.aptitude.surveillance.platform.AgentAlgorithm;
import be.uclouvain.organisation.AddMember;
import be.uclouvain.organisation.Identification;
import be.uclouvain.organisation.LocalDatabaseRequest;
import be.uclouvain.organisation.SignalID;
import be.uclouvain.organisation.platform.AddObserver;
import be.uclouvain.organisation.platform.AlgorithmJoinPlatform;
import be.uclouvain.organisation.platform.PlatformCapacity;
import be.uclouvain.organisation.platform.PlatformRole;
import be.uclouvain.organisation.platform.util.MembershipRule;
import com.google.common.base.Objects;
import io.sarl.core.AgentTask;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO
 * @author $Author: manjahdani$
 * @version $0.0.3$
 * @date $11/06/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class SurveillancePlatformRole extends PlatformRole {
  protected final Map<String, UUID> participants = Collections.<String, UUID>synchronizedMap(new HashMap<String, UUID>());
  
  private final List<String> request = Collections.<String>synchronizedList(new LinkedList<String>());
  
  protected final HashMap<String, OpenEventSpace> agentAlgorithm = new HashMap<String, OpenEventSpace>();
  
  @SuppressWarnings({ "discouraged_occurrence_readonly_use", "potential_field_synchronization_problem" })
  private void $behaviorUnit$LocalDatabaseRequest$0(final LocalDatabaseRequest occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _substring = occurrence.getSource().getUUID().toString().substring(0, 5);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("received a request from " + _substring) + " to connect with a database "));
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    OpenEventSpace _get = this.privateSpacesListeners.get(this.TOLDID);
    OpenEventSpace _get_1 = this.privateSpacesListeners.get(occurrence.getSource().getUUID());
    AddMember _addMember = new AddMember(_get_1);
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(_get, _addMember);
  }
  
  private void $behaviorUnit$Identification$1(final Identification occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("new participant: " + occurrence.name));
    this.participants.put(occurrence.name, occurrence.getSource().getUUID());
  }
  
  @SuppressWarnings({ "discouraged_occurrence_readonly_use", "potential_field_synchronization_problem" })
  private void $behaviorUnit$AddObserver$2(final AddObserver occurrence) {
    String signal = occurrence.signalProvider.getName();
    String receiver = occurrence.signalReceiver.getName();
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("Adding observer from -" + signal) + "- to ") + receiver));
    PlatformCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY$CALLER();
    MembershipRule _membershipRule = new MembershipRule(signal, receiver);
    boolean _RuleManagement = _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY$CALLER.RuleManagement(_membershipRule);
    if (_RuleManagement) {
      synchronized (this) {
        boolean _contains = this.participants.keySet().contains(signal);
        if ((!_contains)) {
          boolean _contains_1 = this.request.contains(signal);
          if ((!_contains_1)) {
            this.request.add(signal);
            ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
            OpenEventSpace _get = this.agentAlgorithm.get(occurrence.signalProvider.getTask());
            InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
            AgentContext _innerContext = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext();
            AlgorithmJoinPlatform _algorithmJoinPlatform = new AlgorithmJoinPlatform(_innerContext, signal);
            _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(_get, _algorithmJoinPlatform);
          }
          this.waitfor(signal, occurrence.getSource().getUUID());
        } else {
          ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
          OpenEventSpace _get_1 = this.privateSpacesListeners.get(occurrence.getSource().getUUID());
          UUID _get_2 = this.participants.get(occurrence.signalProvider.getName());
          SignalID _signalID = new SignalID(_get_2);
          _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER_1.emit(_get_1, _signalID);
        }
      }
    }
  }
  
  private void $behaviorUnit$AgentAlgorithm$3(final AgentAlgorithm occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _substring = occurrence.getSource().getUUID().toString().substring(0, 5);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("encountered : " + occurrence.name) + " - ") + _substring));
    this.agentAlgorithm.put(occurrence.name, occurrence.topic);
  }
  
  public synchronized AgentTask waitfor(final String signal, final UUID dest) {
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      boolean _contains = this.participants.keySet().contains(signal);
      if (_contains) {
        ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
        OpenEventSpace _get = this.privateSpacesListeners.get(dest);
        UUID _get_1 = this.participants.get(signal);
        SignalID _signalID = new SignalID(_get_1);
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final UUID dest;
          
          public $SerializableClosureProxy(final UUID dest) {
            this.dest = dest;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, dest);
          }
        }
        final Scope<Address> _function_1 = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, dest);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, dest);
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(_get, _signalID, _function_1);
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3.task("waitingfor".concat(signal).concat(dest.toString())));
      }
    };
    return _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.every(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("waitingfor".concat(signal).concat(dest.toString())), 500, _function);
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
  @ImportedCapacityFeature(PlatformCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY;
  
  @SyntheticMember
  @Pure
  private PlatformCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY = $getSkill(PlatformCapacity.class);
    }
    return $castSkill(PlatformCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_PLATFORMCAPACITY);
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
  private void $guardEvaluator$LocalDatabaseRequest(final LocalDatabaseRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LocalDatabaseRequest$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AddObserver(final AddObserver occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AddObserver$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentAlgorithm(final AgentAlgorithm occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentAlgorithm$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Identification(final Identification occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Identification$1(occurrence));
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
  public SurveillancePlatformRole(final Agent agent) {
    super(agent);
  }
}
