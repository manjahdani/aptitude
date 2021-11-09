package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.PythonObserverRole;
import be.uclouvain.aptitude.surveillance.algorithm.PythonTwinObserverAccessCapacity;
import be.uclouvain.aptitude.surveillance.algorithm.RestartDetector;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingPerception;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingRequest;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackerPythonTwin;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBOX;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxe2D;
import be.uclouvain.organisation.SignalID;
import be.uclouvain.organisation.platform.AddMission;
import be.uclouvain.organisation.platform.AddObserver;
import be.uclouvain.organisation.platform.LeavePlatform;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Lifecycle;
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
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
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
public class TrackerRole extends PythonObserverRole {
  private final ArrayList<String> availableObservers = CollectionLiterals.<String>newArrayList("TinyYOLO", "YOLO");
  
  private double totalTrackerTime = 0;
  
  private double totalDetectorTime = 0;
  
  private String championName = "None";
  
  private int championSensitivity = 5;
  
  private int LastFrame = 10000;
  
  private final double FRAME_WIDTH = 1920.0;
  
  private double FRAME_HEIGHT = 1080.0;
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$LeavePlatform$0(final LeavePlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ObserverTrackerLeaving");
    PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.UpdateStreamAccess(4);
  }
  
  private void $behaviorUnit$LastFrame$1(final LastFrame occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Receveived last frame " + this.listeners));
    for (final UUID l : this.listeners) {
      OpenEventSpace _get = this.missionSpaceList.get(l);
      String _concat = "F:\\Database\\".concat(this.getOwner().getID().toString());
      LastFrame _lastFrame = new LastFrame(occurrence.frameNumber, (_concat + ".txt"), this.totalDetectorTime, 
        this.totalTrackerTime);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID l;
        
        public $SerializableClosureProxy(final UUID l) {
          this.l = l;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, l);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, l);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, l);
        }
      };
      _get.emit(this.getOwner().getID(), _lastFrame, _function);
    }
  }
  
  private void $behaviorUnit$TrackingRequest$2(final TrackingRequest occurrence) {
    TrackerPythonTwin _trackerPythonTwin = new TrackerPythonTwin();
    this.<TrackerPythonTwin>setSkill(_trackerPythonTwin);
    PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.ActivateAccess(this.jsonConfig);
    boolean _contains = occurrence.bel.contains("Tiny");
    if (_contains) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("My Champion is SORT and TinyYOLO");
      this.championSensitivity = 1;
      this.championName = "tinyYOLO";
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      RestartDetector _restartDetector = new RestartDetector("TinyYOLO");
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_restartDetector);
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("My Champion is SORT and YOLO");
      this.championSensitivity = 0;
      this.championName = "YOLO";
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      RestartDetector _restartDetector_1 = new RestartDetector("YOLO");
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_restartDetector_1);
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$MissionSensitivity$3(final MissionSensitivity occurrence) {
    LinkedList<Integer> _linkedList = new LinkedList<Integer>(occurrence.s);
    this.sensitivity = _linkedList;
    int _size = this.sensitivity.size();
    if ((_size == 1)) {
      this.LastFrame = 50;
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      final Procedure1<Agent> _function = (Agent it) -> {
        if ((this.jsonConfig != null)) {
          TrackerPythonTwin _trackerPythonTwin = new TrackerPythonTwin();
          this.<TrackerPythonTwin>setSkill(_trackerPythonTwin);
          PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
          _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.ActivateAccess(this.jsonConfig);
          Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
          Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3.task("waitforConfig"));
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.every(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("waitforConfig"), 500, _function);
      final String DetectorName = this.availableObservers.get(((this.sensitivity.get(0)) == null ? 0 : (this.sensitivity.get(0)).intValue()));
      AlgorithmInfo _algorithmInfo = new AlgorithmInfo(DetectorName, "DETECTOR");
      String _name = this.observerADN.getName();
      AlgorithmInfo _algorithmInfo_1 = new AlgorithmInfo(_name, "TRACKER");
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
      final Scope<Address> _function_1 = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          UUID _iD = TrackerRole.this.platformContext.getID();
          return Objects.equal(_uUID, _iD);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, TrackerRole.this.platformContext.getID());
        }
      };
      this.privatePlatformSpace.emit(this.getOwner().getID(), _addObserver, _function_1);
    } else {
      for (final Integer s : this.sensitivity) {
        {
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
          String _name_1 = this.observerADN.getName();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + _name_1));
          String _concat = this.observerADN.getName().concat(this.sensitivity.toString());
          AlgorithmInfo _algorithmInfo_2 = new AlgorithmInfo(_concat, "TRACKER");
          String _name_2 = this.observerADN.getName();
          AlgorithmInfo _algorithmInfo_3 = new AlgorithmInfo(_name_2, "TRACKER");
          AddObserver _addObserver_1 = new AddObserver(_algorithmInfo_2, _algorithmInfo_3);
          class $SerializableClosureProxy_1 implements Scope<Address> {
            
            private final UUID $_iD_1;
            
            public $SerializableClosureProxy_1(final UUID $_iD_1) {
              this.$_iD_1 = $_iD_1;
            }
            
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              return Objects.equal(_uUID, $_iD_1);
            }
          }
          final Scope<Address> _function_2 = new Scope<Address>() {
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              UUID _iD = TrackerRole.this.platformContext.getID();
              return Objects.equal(_uUID, _iD);
            }
            private Object writeReplace() throws ObjectStreamException {
              return new SerializableProxy($SerializableClosureProxy_1.class, TrackerRole.this.platformContext.getID());
            }
          };
          this.privatePlatformSpace.emit(this.getOwner().getID(), _addObserver_1, _function_2);
        }
      }
    }
  }
  
  private void $behaviorUnit$SignalID$4(final SignalID occurrence) {
    EventSpace _get = ((EventSpace[])Conversions.unwrapArray(this.missionSpaceList.values(), EventSpace.class))[0];
    AddMission _addMission = new AddMission(_get, null);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_signalID;
      
      public $SerializableClosureProxy(final UUID $_signalID) {
        this.$_signalID = $_signalID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_signalID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, occurrence.signalID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, occurrence.signalID);
      }
    };
    this.platformContext.getDefaultSpace().emit(this.getOwner().getID(), _addMission, _function);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$5(final BBoxes2DTrackResult occurrence) {
    double _talDetectorTime = this.totalDetectorTime;
    double _detectionTime = occurrence.bboxes2DTrack.getDetectionTime();
    this.totalDetectorTime = (_talDetectorTime + _detectionTime);
    double _talTrackerTime = this.totalTrackerTime;
    double _trackingTime = occurrence.bboxes2DTrack.getTrackingTime();
    this.totalTrackerTime = (_talTrackerTime + _trackingTime);
    int _dimWidth = occurrence.bboxes2DTrack.getDimWidth();
    double ratio_width = (this.FRAME_WIDTH / _dimWidth);
    int _dimHeight = occurrence.bboxes2DTrack.getDimHeight();
    double ratio_height = (this.FRAME_HEIGHT / _dimHeight);
    int frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
    ArrayList<BBoxe2D> tmp = CollectionLiterals.<BBoxe2D>newArrayList();
    for (int i = 0; (i < occurrence.bboxes2DTrack.getNumberObjects()); i++) {
      {
        int _get = occurrence.bboxes2DTrack.getBboxes()[(4 * i)];
        double X = (_get * ratio_width);
        int _get_1 = occurrence.bboxes2DTrack.getBboxes()[((4 * i) + 1)];
        double Y = (_get_1 * ratio_height);
        int _get_2 = occurrence.bboxes2DTrack.getBboxes()[((4 * i) + 2)];
        double W = (_get_2 * ratio_width);
        int _get_3 = occurrence.bboxes2DTrack.getBboxes()[((4 * i) + 3)];
        double H = (_get_3 * ratio_height);
        int classID = occurrence.bboxes2DTrack.getClassIDs()[i];
        int globalID = occurrence.bboxes2DTrack.getGlobalIDs()[i];
        double conf = occurrence.bboxes2DTrack.getDetConfs()[i];
        BBOX _bBOX = new BBOX(X, Y, W, H);
        BBoxe2D _bBoxe2D = new BBoxe2D(_bBOX, conf, globalID, classID, frameNumber);
        tmp.add(_bBoxe2D);
      }
    }
    for (final UUID l : this.listeners) {
      OpenEventSpace _get = this.missionSpaceList.get(l);
      TrackingPerception _trackingPerception = new TrackingPerception(tmp);
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID l;
        
        public $SerializableClosureProxy(final UUID l) {
          this.l = l;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, l);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, l);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, l);
        }
      };
      _get.emit(this.getOwner().getID(), _trackingPerception, _function);
    }
    if ((occurrence.bboxes2DTrack.isLastFrame() || (frameNumber > this.LastFrame))) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      LastFrame _lastFrame = new LastFrame(frameNumber);
      class $SerializableClosureProxy_1 implements Scope<Address> {
        
        private final TrackerRole $_TrackerRole;
        
        public $SerializableClosureProxy_1(final TrackerRole $_TrackerRole) {
          this.$_TrackerRole = $_TrackerRole;
        }
        
        @Override
        public boolean matches(final Address it) {
          boolean _isMe = $_TrackerRole.isMe(it);
          return Objects.equal(it, Boolean.valueOf(_isMe));
        }
      }
      final Scope<Address> _function_1 = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          boolean _isMe = TrackerRole.this.isMe(it);
          return Objects.equal(it, Boolean.valueOf(_isMe));
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy_1.class, TrackerRole.this);
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_lastFrame, _function_1);
      for (final UUID p : this.providers) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
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
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_leavePlatform, _function_2);
      }
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
    }
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$BBoxes2DResult$6(final BBoxes2DResult occurrence) {
    boolean _contains = this.providers.contains(occurrence.getSource().getUUID());
    if ((!_contains)) {
      this.providers.add(occurrence.getSource().getUUID());
    }
    PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.Signal2Perception(occurrence.bboxes2D);
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
  @ImportedCapacityFeature(PythonTwinObserverAccessCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY;
  
  @SyntheticMember
  @Pure
  private PythonTwinObserverAccessCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY = $getSkill(PythonTwinObserverAccessCapacity.class);
    }
    return $castSkill(PythonTwinObserverAccessCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY);
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
  private void $guardEvaluator$MissionSensitivity(final MissionSensitivity occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MissionSensitivity$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LeavePlatform(final LeavePlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DTrackResult(final BBoxes2DTrackResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResult$5(occurrence));
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TrackingRequest$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LastFrame(final LastFrame occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LastFrame$1(occurrence));
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
    if (!java.util.Objects.equals(this.championName, other.championName))
      return false;
    if (other.championSensitivity != this.championSensitivity)
      return false;
    if (other.LastFrame != this.LastFrame)
      return false;
    if (Double.doubleToLongBits(other.FRAME_WIDTH) != Double.doubleToLongBits(this.FRAME_WIDTH))
      return false;
    if (Double.doubleToLongBits(other.FRAME_HEIGHT) != Double.doubleToLongBits(this.FRAME_HEIGHT))
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
    result = prime * result + java.util.Objects.hashCode(this.championName);
    result = prime * result + Integer.hashCode(this.championSensitivity);
    result = prime * result + Integer.hashCode(this.LastFrame);
    result = prime * result + Double.hashCode(this.FRAME_WIDTH);
    result = prime * result + Double.hashCode(this.FRAME_HEIGHT);
    return result;
  }
  
  @SyntheticMember
  public TrackerRole(final Agent agent) {
    super(agent);
  }
}
