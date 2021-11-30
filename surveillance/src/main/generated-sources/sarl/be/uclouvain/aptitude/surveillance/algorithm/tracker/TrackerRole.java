package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.detector.RestartDetector;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.CompetitiveMultiTrackerRole;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.SingleTrackerRole;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackerPythonTwin;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackerPythonTwinCapacity;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackingPerception;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackingRequest;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxe2D;
import be.uclouvain.aptitude.surveillance.algorithm.util.HyperParameters;
import be.uclouvain.organisation.SignalID;
import be.uclouvain.organisation.platform.AddMission;
import be.uclouvain.organisation.platform.HyperParametersRequest;
import be.uclouvain.organisation.platform.LeavePlatform;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.platform.ProcessingHyperParameters;
import be.uclouvain.python_access.BBoxes2DTrackResult;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
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
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @FIXME : IMPROVEMENT : the Hazelcast only need to send the frame number and the ids.  Will it make sens ?
 * @TODO : Any Observer would transmit the read-only pointer to the memory space
 * that contains the information requested upon approved request from another.
 * 
 * It does make sens to have a certain window of information in memory and the
 * other one stored in a database. Therefore,
 *  the database with a certain window
 * in situ and otherwise stored in TOLD
 * We need to find a way such that the information is synchronized before potential fusion
 * 
 * 
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class TrackerRole extends ObserverRole {
  private final ArrayList<String> detectors = CollectionLiterals.<String>newArrayList("TinyYOLO", "YOLO");
  
  private double totalTrackerTime = 0;
  
  private double totalDetectorTime = 0;
  
  private int FrameLimit;
  
  private void $behaviorUnit$LastFrame$0(final LastFrame occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Received last frame " + this.listeners));
    Collection<OpenEventSpace> _values = this.listeners.values();
    for (final OpenEventSpace listenersSpace : _values) {
      String _concat = "F:\\Database\\".concat(this.getOwner().getID().toString());
      LastFrame _lastFrame = new LastFrame(
        occurrence.frameNumber, (_concat + ".txt"), 
        this.totalDetectorTime, 
        this.totalTrackerTime);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID $_iD;
        
        public $SerializableClosureProxy(final UUID $_iD) {
          this.$_iD = $_iD;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return (_uUID != $_iD);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          UUID _iD = TrackerRole.this.getOwner().getID();
          return (_uUID != _iD);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, TrackerRole.this.getOwner().getID());
        }
      };
      listenersSpace.emit(this.getOwner().getID(), _lastFrame, _function);
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$ProcessingHyperParameters$1(final ProcessingHyperParameters occurrence) {
    this.sensitivity = occurrence.s;
    if (occurrence.OptimalSearchEnabled) {
      LinkedList<HyperParameters> hyperParatermersToBeTested = new LinkedList<HyperParameters>();
      int iter = 0;
      for (final String detector : this.detectors) {
        {
          HyperParameters _hyperParameters = new HyperParameters(iter);
          hyperParatermersToBeTested.add(_hyperParameters);
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + detector));
          this.requestAlgorithm(detector, "DETECTOR");
          iter = (iter + 1);
        }
      }
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      Agent _owner = this.getOwner();
      CompetitiveMultiTrackerRole _competitiveMultiTrackerRole = new CompetitiveMultiTrackerRole(_owner);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_competitiveMultiTrackerRole, hyperParatermersToBeTested, this.observerADN);
    } else {
      TrackerPythonTwin _trackerPythonTwin = new TrackerPythonTwin();
      this.<TrackerPythonTwin>setSkill(_trackerPythonTwin);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      Agent _owner_1 = this.getOwner();
      SingleTrackerRole _singleTrackerRole = new SingleTrackerRole(_owner_1);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.registerBehavior(_singleTrackerRole, this.observerADN.getBelief(), this.observerADN.getPlatformName());
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _get = this.detectors.get(this.sensitivity);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + _get));
      if (((this.isMaster) == null ? false : (this.isMaster).booleanValue())) {
        this.requestAlgorithm(this.detectors.get(this.sensitivity), "DETECTOR");
      }
    }
  }
  
  private void $behaviorUnit$HyperParametersRequest$2(final HyperParametersRequest occurrence) {
    boolean _contains = this.providers.keySet().contains(occurrence.getSource().getUUID());
    if (_contains) {
      UUID providerID = occurrence.getSource().getUUID();
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _substring = providerID.toString().substring(0, 5);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("received sensitivity request from -" + _substring) + 
        " .... \n  sending the following sensitivity") + Integer.valueOf(this.sensitivity)));
      OpenEventSpace _get = this.providers.get(providerID);
      ProcessingHyperParameters _processingHyperParameters = new ProcessingHyperParameters(this.sensitivity, ((this.isMaster) == null ? false : (this.isMaster).booleanValue()));
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
    }
  }
  
  private void $behaviorUnit$TrackingRequest$3(final TrackingRequest occurrence) {
    TrackerPythonTwin _trackerPythonTwin = new TrackerPythonTwin();
    this.<TrackerPythonTwin>setSkill(_trackerPythonTwin);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    Agent _owner = this.getOwner();
    SingleTrackerRole _singleTrackerRole = new SingleTrackerRole(_owner);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_singleTrackerRole, this.observerADN.getBelief(), this.platformName);
    int _xifexpression = (int) 0;
    boolean _contains = occurrence.bel.contains("Tiny");
    if (_contains) {
      _xifexpression = 0;
    } else {
      _xifexpression = 1;
    }
    int s = _xifexpression;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Come here detectors.get(s)");
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    String _get = this.detectors.get(s);
    RestartDetector _restartDetector = new RestartDetector(_get);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_restartDetector);
  }
  
  private void $behaviorUnit$SignalID$4(final SignalID occurrence) {
    final UUID dataSource = occurrence.signalID;
    final OpenEventSpace comChannel = this.platformContext.<OpenEventSpace>getOrCreateSpaceWithID(OpenEventSpaceSpecification.class, UUID.randomUUID());
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    comChannel.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    this.providers.put(dataSource, comChannel);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = this.providers.get(dataSource).getSpaceID().getID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("\n received the provider ID \n sending the missionSpace" + _iD));
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
  
  private void $behaviorUnit$LeavePlatform$5(final LeavePlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ObserverTrackerLeaving");
    TrackerPythonTwinCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER.updateStreamAccess(4);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$6(final BBoxes2DTrackResult occurrence) {
    double _talDetectorTime = this.totalDetectorTime;
    double _detectionTime = occurrence.bboxes2DTrack.getDetectionTime();
    this.totalDetectorTime = (_talDetectorTime + _detectionTime);
    double _talTrackerTime = this.totalTrackerTime;
    double _trackingTime = occurrence.bboxes2DTrack.getTrackingTime();
    this.totalTrackerTime = (_talTrackerTime + _trackingTime);
    TrackerPythonTwinCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER();
    ArrayList<BBoxe2D> tmp = _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER.formatConversion(occurrence.bboxes2DTrack);
    Collection<OpenEventSpace> _values = this.listeners.values();
    for (final OpenEventSpace l : _values) {
      TrackingPerception _trackingPerception = new TrackingPerception(tmp);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final TrackerRole $_TrackerRole;
        
        public $SerializableClosureProxy(final TrackerRole $_TrackerRole) {
          this.$_TrackerRole = $_TrackerRole;
        }
        
        @Override
        public boolean matches(final Address it) {
          boolean _isMe = $_TrackerRole.isMe(it);
          return (!Objects.equal(it, Boolean.valueOf(_isMe)));
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          boolean _isMe = TrackerRole.this.isMe(it);
          return (!Objects.equal(it, Boolean.valueOf(_isMe)));
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, TrackerRole.this);
        }
      };
      l.emit(this.getOwner().getID(), _trackingPerception, _function);
    }
    int _frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
    if (((_frameNumber % 10) == 0)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Processed %d frames", Integer.valueOf(occurrence.bboxes2DTrack.getFrameNumber()));
    }
    if ((occurrence.bboxes2DTrack.isLastFrame() || (occurrence.bboxes2DTrack.getFrameNumber() > this.FrameLimit))) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      int _frameNumber_1 = occurrence.bboxes2DTrack.getFrameNumber();
      LastFrame _lastFrame = new LastFrame(_frameNumber_1);
      class $SerializableClosureProxy_1 implements Scope<Address> {
        
        private final UUID $_iD;
        
        public $SerializableClosureProxy_1(final UUID $_iD) {
          this.$_iD = $_iD;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, $_iD);
        }
      }
      final Scope<Address> _function_1 = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          UUID _iD = TrackerRole.this.getOwner().getID();
          return Objects.equal(_uUID, _iD);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy_1.class, TrackerRole.this.getOwner().getID());
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_lastFrame, _function_1);
      Set<UUID> _keySet = this.providers.keySet();
      for (final UUID p : _keySet) {
        OpenEventSpace _get = this.providers.get(p);
        LeavePlatform _leavePlatform = new LeavePlatform();
        class $SerializableClosureProxy_2 implements Scope<Address> {
          
          private final UUID p;
          
          public $SerializableClosureProxy_2(final UUID p) {
            this.p = p;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, p);
          }
        }
        final Scope<Address> _function_2 = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, p);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy_2.class, p);
          }
        };
        _get.emit(this.getOwner().getID(), _leavePlatform, _function_2);
      }
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
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
  
  @Extension
  @ImportedCapacityFeature(TrackerPythonTwinCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY;
  
  @SyntheticMember
  @Pure
  private TrackerPythonTwinCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY = $getSkill(TrackerPythonTwinCapacity.class);
    }
    return $castSkill(TrackerPythonTwinCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY);
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
  private void $guardEvaluator$LeavePlatform(final LeavePlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$HyperParametersRequest(final HyperParametersRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$HyperParametersRequest$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SignalID(final SignalID occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SignalID$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TrackingRequest(final TrackingRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TrackingRequest$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DTrackResult(final BBoxes2DTrackResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResult$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LastFrame(final LastFrame occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LastFrame$0(occurrence));
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
    TrackerRole other = (TrackerRole) obj;
    if (Double.doubleToLongBits(other.totalTrackerTime) != Double.doubleToLongBits(this.totalTrackerTime))
      return false;
    if (Double.doubleToLongBits(other.totalDetectorTime) != Double.doubleToLongBits(this.totalDetectorTime))
      return false;
    if (other.FrameLimit != this.FrameLimit)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.totalTrackerTime);
    result = prime * result + Double.hashCode(this.totalDetectorTime);
    result = prime * result + Integer.hashCode(this.FrameLimit);
    return result;
  }
  
  @SyntheticMember
  public TrackerRole(final Agent agent) {
    super(agent);
  }
}
