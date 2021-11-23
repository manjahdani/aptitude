package be.uclouvain.aptitude.surveillance.algorithm.counter;

import be.uclouvain.aptitude.surveillance.algorithm.util.Metric;
import be.uclouvain.aptitude.surveillance.evaluation.AlgorithmSelectorRole;
import be.uclouvain.organisation.SignalID;
import be.uclouvain.organisation.platform.AddMission;
import be.uclouvain.organisation.platform.AddObserver;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.platform.ProcessingHyperParameters;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.core.OpenEventSpaceSpecification;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO : To complete
 * 
 * @author  $ manjahdani$
 * @version $0.0.1$
 * @date $17/06/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class CompetitiveCounterRole extends ObserverRole {
  private final ArrayList<String> AVAILABLE_TRACKERS = CollectionLiterals.<String>newArrayList("SORT");
  
  private Integer expertSensitivity;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    this.sensitivity.add(Integer.valueOf(0));
    this.sensitivity.add(Integer.valueOf(1));
    this.isMaster = Boolean.valueOf(true);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Let the competition start !");
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$ProcessingHyperParameters$1(final ProcessingHyperParameters occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("I received sensitivity - " + occurrence.s));
    this.expertSensitivity = occurrence.s.get(0);
    ArrayList<String> parameters = new ArrayList<String>();
    parameters.add(this.expertSensitivity.toString());
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    Agent _owner = this.getOwner();
    AlgorithmSelectorRole _algorithmSelectorRole = new AlgorithmSelectorRole(_owner);
    String _concat = "F:/data/".concat(this.platformName).concat("/gt/gt.txt");
    final ArrayList<String> _converted_parameters = (ArrayList<String>)parameters;
    Metric _metric = new Metric("APTITUDE", _concat, ((String[])Conversions.unwrapArray(_converted_parameters, String.class)));
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_algorithmSelectorRole, _metric);
    for (final String tracker : this.AVAILABLE_TRACKERS) {
      {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("requests tracker: " + tracker));
        ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
        AlgorithmInfo _algorithmInfo = new AlgorithmInfo(tracker, "TRACKER");
        String _name = this.observerADN.getName();
        String _task = this.observerADN.getTask();
        AlgorithmInfo _algorithmInfo_1 = new AlgorithmInfo(_name, _task);
        AddObserver _addObserver = new AddObserver(_algorithmInfo, _algorithmInfo_1);
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final UUID $_iD_1;
          
          public $SerializableClosureProxy(final UUID $_iD_1) {
            this.$_iD_1 = $_iD_1;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, $_iD_1);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            UUID _iD = CompetitiveCounterRole.this.platformContext.getID();
            return Objects.equal(_uUID, _iD);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, CompetitiveCounterRole.this.platformContext.getID());
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.privatePlatformSpace, _addObserver, _function);
      }
    }
  }
  
  private void $behaviorUnit$SignalID$2(final SignalID occurrence) {
    final UUID dataSource = occurrence.signalID;
    final OpenEventSpace comChannel = this.platformContext.<OpenEventSpace>getOrCreateSpaceWithID(OpenEventSpaceSpecification.class, UUID.randomUUID());
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    comChannel.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    this.providers.put(dataSource, comChannel);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _substring = this.providers.get(dataSource).getSpaceID().getID().toString().substring(0, 5);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("received provider ID \n sending the missionSpace" + _substring));
    OpenEventSpace _get = this.providers.get(dataSource);
    AddMission _addMission = new AddMission(_get);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID dataSource;
      
      public $SerializableClosureProxy(final UUID dataSource) {
        this.dataSource = dataSource;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, dataSource);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, dataSource);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, dataSource);
      }
    };
    this.platformContext.getDefaultSpace().emit(this.getOwner().getID(), _addMission, _function);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SignalID(final SignalID occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SignalID$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ProcessingHyperParameters(final ProcessingHyperParameters occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ProcessingHyperParameters$1(occurrence));
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
    CompetitiveCounterRole other = (CompetitiveCounterRole) obj;
    if (other.expertSensitivity == null) {
      if (this.expertSensitivity != null)
        return false;
    } else if (this.expertSensitivity == null)
      return false;
    if (other.expertSensitivity != null && other.expertSensitivity.intValue() != this.expertSensitivity.intValue())
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.expertSensitivity);
    return result;
  }
  
  @SyntheticMember
  public CompetitiveCounterRole(final Agent agent) {
    super(agent);
  }
}
