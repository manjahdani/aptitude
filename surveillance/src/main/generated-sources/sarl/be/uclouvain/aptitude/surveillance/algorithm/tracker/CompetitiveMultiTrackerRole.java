package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.aptitude.surveillance.algorithm.Algorithm;
import be.uclouvain.aptitude.surveillance.algorithm.util.HyperParameters;
import be.uclouvain.organisation.platform.AddMission;
import be.uclouvain.organisation.platform.HyperParametersRequest;
import be.uclouvain.organisation.platform.ProcessingHyperParameters;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import be.uclouvain.python_access.BBoxes2DResult;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Lifecycle;
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
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class CompetitiveMultiTrackerRole extends Behavior {
  private final ArrayList<String> detectors = CollectionLiterals.<String>newArrayList("TinyYOLO", "YOLO");
  
  protected final Map<UUID, OpenEventSpace> sub_processes = Collections.<UUID, OpenEventSpace>synchronizedMap(new HashMap<UUID, OpenEventSpace>());
  
  protected final Map<String, UUID> parrallelProcess = Collections.<String, UUID>synchronizedMap(new HashMap<String, UUID>());
  
  protected final Map<UUID, HyperParameters> providers_HP = Collections.<UUID, HyperParameters>synchronizedMap(new HashMap<UUID, HyperParameters>());
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Competitive multi-tracker role started.");
    LinkedList<HyperParameters> hyperParametersToBeTested = null;
    AlgorithmInfo observerADN = null;
    try {
      Object _get = occurrence.parameters[0];
      hyperParametersToBeTested = ((LinkedList<HyperParameters>) _get);
    } catch (final Throwable _t) {
      if (_t instanceof ClassCastException) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Could not cast Initialise parameters 0 to a LinkedList");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    try {
      Object _get = occurrence.parameters[1];
      observerADN = ((AlgorithmInfo) _get);
    } catch (final Throwable _t) {
      if (_t instanceof ClassCastException) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Could not cast Initialise parameters 0 to a LinkedList");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    for (final HyperParameters param : hyperParametersToBeTested) {
      {
        UUID cloneID = UUID.randomUUID();
        this.providers_HP.put(cloneID, param);
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
        InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(Algorithm.class, cloneID, _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext(), observerADN.cloneChild());
        InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
        OpenEventSpace comSpace = _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER_1.getInnerContext().<OpenEventSpace>getOrCreateSpaceWithID(OpenEventSpaceSpecification.class, UUID.randomUUID());
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        comSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
        this.sub_processes.put(cloneID, comSpace);
        this.parrallelProcess.put(this.detectors.get(param.getSensitivity()), cloneID);
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        AddMission _addMission = new AddMission(comSpace);
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final UUID cloneID;
          
          public $SerializableClosureProxy(final UUID cloneID) {
            this.cloneID = cloneID;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, cloneID);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, cloneID);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, cloneID);
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.wake(_addMission, _function);
      }
    }
  }
  
  private void $behaviorUnit$HyperParametersRequest$1(final HyperParametersRequest occurrence) {
    UUID providerID = occurrence.getSource().getUUID();
    boolean _containsKey = this.sub_processes.containsKey(providerID);
    if (_containsKey) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _substring = providerID.toString().substring(0, 5);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("Request from -" + _substring) + "a the child"));
      HyperParameters param = this.providers_HP.get(providerID);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _substring_1 = providerID.toString().substring(0, 5);
      String _plus = (("sensitivity request from -" + _substring_1) + 
        " .... sending the following sensitivity :");
      int _sensitivity = param.getSensitivity();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((_plus + Integer.valueOf(_sensitivity)));
      OpenEventSpace _get = this.sub_processes.get(providerID);
      int _sensitivity_1 = param.getSensitivity();
      ProcessingHyperParameters _processingHyperParameters = new ProcessingHyperParameters(_sensitivity_1, 
        false);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID providerID;
        
        public $SerializableClosureProxy(final UUID providerID) {
          this.providerID = providerID;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, providerID);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, providerID);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, providerID);
        }
      };
      _get.emit(this.getOwner().getID(), _processingHyperParameters, _function);
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _substring_2 = providerID.toString().substring(0, 5);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info((("Request from -" + _substring_2) + "it is not in the child"));
    }
  }
  
  private void $behaviorUnit$Destroy$2(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The behavior was stopped.");
  }
  
  private void $behaviorUnit$BBoxes2DResult$3(final BBoxes2DResult occurrence) {
    int _frameNumber = occurrence.bboxes2D.getFrameNumber();
    if ((_frameNumber < 5)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      UUID _get = this.parrallelProcess.get(occurrence.providerName);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("Sending results from " + occurrence.providerName) + " to ") + _get));
    }
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    BBoxes2DResult _bBoxes2DResult = new BBoxes2DResult(occurrence.bboxes2D);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_get;
      
      public $SerializableClosureProxy(final UUID $_get) {
        this.$_get = $_get;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_get);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        UUID _get = CompetitiveMultiTrackerRole.this.parrallelProcess.get(occurrence.providerName);
        return Objects.equal(_uUID, _get);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, CompetitiveMultiTrackerRole.this.parrallelProcess.get(occurrence.providerName));
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_bBoxes2DResult, _function);
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
  private void $guardEvaluator$HyperParametersRequest(final HyperParametersRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$HyperParametersRequest$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$2(occurrence));
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
  public CompetitiveMultiTrackerRole(final Agent agent) {
    super(agent);
  }
}
