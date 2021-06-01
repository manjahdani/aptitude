package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResultToCounter;
import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerTrackingFound;
import be.uclouvain.aptitude.surveillance.algorithm.PythonTwinObserverAccess;
import be.uclouvain.aptitude.surveillance.algorithm.RestartDetector;
import be.uclouvain.aptitude.surveillance.algorithm.TrackerPythonTwin;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingPerception;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingRequest;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBOX;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxes2D;
import be.uclouvain.organisation.SignalID;
import be.uclouvain.organisation.platform.AddAlgorithm;
import be.uclouvain.organisation.platform.AddMission;
import be.uclouvain.organisation.platform.AddObserver;
import be.uclouvain.organisation.platform.LeavePlatform;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
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
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
  private String partnerTrackingName;
  
  private final TreeMap<Integer, Integer> intensityMap = new TreeMap<Integer, Integer>();
  
  private FileWriter gt;
  
  private final ArrayList<String> availableObservers = CollectionLiterals.<String>newArrayList("TinyYOLO", "YOLO");
  
  private double totalTrackerTime = 0;
  
  private double totalDetectorTime = 0;
  
  private final List<ArrayList<BBoxes2D>> dynamicTrackingMemory = Collections.<ArrayList<BBoxes2D>>synchronizedList(new LinkedList<ArrayList<BBoxes2D>>());
  
  private File predfile;
  
  private String videoName = "S02C006";
  
  private JSONParser parser = new JSONParser();
  
  private String championName = "None";
  
  private int championSensitivity = 5;
  
  private int LastFrame = 10000;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    this.intensityMap.put(Integer.valueOf(0), Integer.valueOf(1));
    this.intensityMap.put(Integer.valueOf(1), Integer.valueOf(0));
    this.intensityMap.put(Integer.valueOf(2), Integer.valueOf(0));
    this.intensityMap.put(Integer.valueOf(3), Integer.valueOf(1));
  }
  
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PartnerTrackingFound$2(final PartnerTrackingFound occurrence) {
    this.partnerTrackingName = occurrence.partnerName;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Tracking Partner found: " + this.partnerTrackingName));
  }
  
  private void $behaviorUnit$LeavePlatform$3(final LeavePlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ObserverTrackerLeaving");
    PythonTwinObserverAccess _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER.UpdateStreamAccess(4);
  }
  
  private void $behaviorUnit$LastFrame$4(final LastFrame occurrence) {
    for (final UUID l : this.Listeners) {
      OpenEventSpace _get = this.MissionSpaceList.get(l);
      LastFrame _lastFrame = new LastFrame(occurrence.frameNumber, occurrence.pred_file_Path, occurrence.total_time_detection, occurrence.total_time_tracking);
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
  
  private void $behaviorUnit$TrackingRequest$5(final TrackingRequest occurrence) {
    try {
      TrackerPythonTwin _trackerPythonTwin = new TrackerPythonTwin();
      this.<TrackerPythonTwin>setSkill(_trackerPythonTwin);
      String configPathTracker = this.ObserverADN.getBelief();
      FileReader _fileReader = new FileReader(configPathTracker);
      Object _parse = this.parser.parse(_fileReader);
      JSONObject jsonTracker = ((JSONObject) _parse);
      this.LastFrame = 100000;
      String _concat = "Final".concat(this.videoName).concat(this.getOwner().getID().toString());
      File _file = new File((("F:\\" + _concat) + ".txt"));
      this.predfile = _file;
      FileWriter _fileWriter = new FileWriter(this.predfile);
      this.gt = _fileWriter;
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ReactivatingAccess");
      PythonTwinObserverAccess _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER.ActivateAccess(jsonTracker);
      boolean _contains = occurrence.bel.contains("Tiny");
      if (_contains) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("My Champion is SORT and TinyYOLO");
        this.championSensitivity = 1;
        this.championName = "tinyYOLO";
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        RestartDetector _restartDetector = new RestartDetector("TinyYOLO");
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_restartDetector);
      } else {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("My Champion is SORT and YOLO");
        this.championSensitivity = 0;
        this.championName = "YOLO";
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        RestartDetector _restartDetector_1 = new RestartDetector("YOLO");
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_restartDetector_1);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$MissionSensitivity$6(final MissionSensitivity occurrence) {
    try {
      LinkedList<Integer> _linkedList = new LinkedList<Integer>(occurrence.s);
      this.sensitivity = _linkedList;
      int _size = this.sensitivity.size();
      if ((_size == 1)) {
        this.LastFrame = 10;
        TrackerPythonTwin _trackerPythonTwin = new TrackerPythonTwin();
        this.<TrackerPythonTwin>setSkill(_trackerPythonTwin);
        String configPathTracker = this.ObserverADN.getBelief();
        FileReader _fileReader = new FileReader(configPathTracker);
        Object _parse = this.parser.parse(_fileReader);
        JSONObject jsonTracker = ((JSONObject) _parse);
        PythonTwinObserverAccess _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER();
        _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER.ActivateAccess(jsonTracker);
        final String DetectorName = this.availableObservers.get(((this.sensitivity.get(0)) == null ? 0 : (this.sensitivity.get(0)).intValue()));
        String _concat = DetectorName.concat(this.ObserverADN.getName()).concat(this.videoName).concat(this.getOwner().getID().toString());
        File _file = new File((("F:\\" + _concat) + ".txt"));
        this.predfile = _file;
        FileWriter _fileWriter = new FileWriter(this.predfile);
        this.gt = _fileWriter;
        OpenEventSpace _get = ((OpenEventSpace[])Conversions.unwrapArray(this.MissionSpaceList.values(), OpenEventSpace.class))[0];
        AlgorithmInfo _algorithmInfo = new AlgorithmInfo(DetectorName, "DETECTOR");
        String _name = this.ObserverADN.getName();
        AlgorithmInfo _algorithmInfo_1 = new AlgorithmInfo(_name, "TRACKER");
        AddObserver _addObserver = new AddObserver(_get, _algorithmInfo, _algorithmInfo_1);
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
            UUID _iD = TrackerRole.this.PlatformContext.getID();
            return Objects.equal(_uUID, _iD);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, TrackerRole.this.PlatformContext.getID());
          }
        };
        this.PlatformSpace.emit(this.getOwner().getID(), _addObserver, _function);
      } else {
        for (final Integer s : this.sensitivity) {
          OpenEventSpace _get_1 = ((OpenEventSpace[])Conversions.unwrapArray(this.MissionSpaceList.values(), OpenEventSpace.class))[0];
          String _name_1 = this.ObserverADN.getName();
          String _task = this.ObserverADN.getTask();
          AlgorithmInfo _algorithmInfo_2 = new AlgorithmInfo(_name_1, _task);
          AddAlgorithm _addAlgorithm = new AddAlgorithm(_get_1, _algorithmInfo_2);
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
          final Scope<Address> _function_1 = new Scope<Address>() {
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              UUID _iD = TrackerRole.this.PlatformContext.getID();
              return Objects.equal(_uUID, _iD);
            }
            private Object writeReplace() throws ObjectStreamException {
              return new SerializableProxy($SerializableClosureProxy_1.class, TrackerRole.this.PlatformContext.getID());
            }
          };
          this.PlatformSpace.emit(this.getOwner().getID(), _addAlgorithm, _function_1);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$SignalID$7(final SignalID occurrence) {
    EventSpace _get = ((EventSpace[])Conversions.unwrapArray(this.MissionSpaceList.values(), EventSpace.class))[0];
    AddMission _addMission = new AddMission(_get, null);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_name;
      
      public $SerializableClosureProxy(final UUID $_name) {
        this.$_name = $_name;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_name);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, occurrence.name);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, occurrence.name);
      }
    };
    this.PlatformContext.getDefaultSpace().emit(this.getOwner().getID(), _addMission, _function);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$8(final BBoxes2DTrackResult occurrence) {
    try {
      double _talDetectorTime = this.totalDetectorTime;
      double _detectionTime = occurrence.bboxes2DTrack.getDetectionTime();
      this.totalDetectorTime = (_talDetectorTime + _detectionTime);
      double _talTrackerTime = this.totalTrackerTime;
      double _trackingTime = occurrence.bboxes2DTrack.getTrackingTime();
      this.totalTrackerTime = (_talTrackerTime + _trackingTime);
      int _dimWidth = occurrence.bboxes2DTrack.getDimWidth();
      double ratio_width = (1920.0 / _dimWidth);
      int _dimHeight = occurrence.bboxes2DTrack.getDimHeight();
      double ratio_height = (1080.0 / _dimHeight);
      int frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
      ArrayList<BBoxes2D> tmp = CollectionLiterals.<BBoxes2D>newArrayList();
      if (((frameNumber % 100) == 0)) {
      }
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
          synchronized (this) {
            try {
              String _string = Integer.valueOf((frameNumber + 1)).toString();
              String _string_1 = Integer.valueOf(globalID).toString();
              String _string_2 = Integer.valueOf(Double.valueOf(X).intValue()).toString();
              String _string_3 = Integer.valueOf(Double.valueOf(Y).intValue()).toString();
              String _string_4 = Integer.valueOf(Double.valueOf(W).intValue()).toString();
              String _string_5 = Integer.valueOf(Double.valueOf(H).intValue()).toString();
              this.gt.write(
                (((((((((((((((((((_string + ",") + _string_1) + ",") + _string_2) + ",") + _string_3) + ",") + _string_4) + ",") + _string_5) + ",") + 
                  "-1") + 
                  ",") + "-1") + ",") + "-1") + ",") + "-1") + "\n"));
            } catch (final Throwable _t) {
              if (_t instanceof IOException) {
              } else if (_t instanceof NullPointerException) {
                Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
                _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("NullPointerException");
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
          BBOX _bBOX = new BBOX(X, Y, W, H);
          BBoxes2D _bBoxes2D = new BBoxes2D(_bBOX, conf, globalID, classID, frameNumber);
          tmp.add(_bBoxes2D);
        }
      }
      for (final UUID l : this.Listeners) {
        {
          OpenEventSpace _get = this.MissionSpaceList.get(l);
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
          OpenEventSpace _get_1 = this.MissionSpaceList.get(l);
          BBoxes2DTrackResultToCounter _bBoxes2DTrackResultToCounter = new BBoxes2DTrackResultToCounter(occurrence.bboxes2DTrack, this.championName, this.championSensitivity);
          class $SerializableClosureProxy_1 implements Scope<Address> {
            
            private final UUID l;
            
            public $SerializableClosureProxy_1(final UUID l) {
              this.l = l;
            }
            
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              return Objects.equal(_uUID, l);
            }
          }
          final Scope<Address> _function_1 = new Scope<Address>() {
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              return Objects.equal(_uUID, l);
            }
            private Object writeReplace() throws ObjectStreamException {
              return new SerializableProxy($SerializableClosureProxy_1.class, l);
            }
          };
          _get_1.emit(this.getOwner().getID(), _bBoxes2DTrackResultToCounter, _function_1);
        }
      }
      if ((occurrence.bboxes2DTrack.isLastFrame() || (frameNumber > this.LastFrame))) {
        this.gt.close();
        for (final UUID l_1 : this.Listeners) {
          {
            OpenEventSpace _get = this.MissionSpaceList.get(l_1);
            String _string = this.predfile.toString();
            LastFrame _lastFrame = new LastFrame(frameNumber, _string, this.totalDetectorTime, this.totalTrackerTime);
            class $SerializableClosureProxy implements Scope<Address> {
              
              private final UUID l_1;
              
              public $SerializableClosureProxy(final UUID l_1) {
                this.l_1 = l_1;
              }
              
              @Override
              public boolean matches(final Address it) {
                UUID _uUID = it.getUUID();
                return Objects.equal(_uUID, l_1);
              }
            }
            final Scope<Address> _function = new Scope<Address>() {
              @Override
              public boolean matches(final Address it) {
                UUID _uUID = it.getUUID();
                return Objects.equal(_uUID, l_1);
              }
              private Object writeReplace() throws ObjectStreamException {
                return new SerializableProxy($SerializableClosureProxy.class, l_1);
              }
            };
            _get.emit(this.getOwner().getID(), _lastFrame, _function);
            for (final UUID p : this.Providers) {
              DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
              LeavePlatform _leavePlatform = new LeavePlatform();
              class $SerializableClosureProxy_1 implements Scope<Address> {
                
                private final UUID p;
                
                public $SerializableClosureProxy_1(final UUID p) {
                  this.p = p;
                }
                
                @Override
                public boolean matches(final Address it) {
                  UUID _uUID = it.getUUID();
                  return Objects.equal(_uUID, p);
                }
              }
              final Scope<Address> _function_1 = new Scope<Address>() {
                @Override
                public boolean matches(final Address it) {
                  UUID _uUID = it.getUUID();
                  return Objects.equal(_uUID, p);
                }
                private Object writeReplace() throws ObjectStreamException {
                  return new SerializableProxy($SerializableClosureProxy_1.class, p);
                }
              };
              _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_leavePlatform, _function_1);
            }
            Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
            _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$BBoxes2DResult$9(final BBoxes2DResult occurrence) {
    boolean _contains = this.Providers.contains(occurrence.getSource().getUUID());
    if ((!_contains)) {
      this.Providers.add(occurrence.getSource().getUUID());
    }
    PythonTwinObserverAccess _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER.Signal2Perception(occurrence.bboxes2D);
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
  @ImportedCapacityFeature(PythonTwinObserverAccess.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS;
  
  @SyntheticMember
  @Pure
  private PythonTwinObserverAccess $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS = $getSkill(PythonTwinObserverAccess.class);
    }
    return $castSkill(PythonTwinObserverAccess.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerTrackingFound(final PartnerTrackingFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerTrackingFound$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MissionSensitivity(final MissionSensitivity occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MissionSensitivity$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LeavePlatform(final LeavePlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$9(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DTrackResult(final BBoxes2DTrackResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResult$8(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SignalID(final SignalID occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SignalID$7(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TrackingRequest(final TrackingRequest occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TrackingRequest$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LastFrame(final LastFrame occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LastFrame$4(occurrence));
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
    if (!java.util.Objects.equals(this.partnerTrackingName, other.partnerTrackingName))
      return false;
    if (Double.doubleToLongBits(other.totalTrackerTime) != Double.doubleToLongBits(this.totalTrackerTime))
      return false;
    if (Double.doubleToLongBits(other.totalDetectorTime) != Double.doubleToLongBits(this.totalDetectorTime))
      return false;
    if (!java.util.Objects.equals(this.videoName, other.videoName))
      return false;
    if (!java.util.Objects.equals(this.championName, other.championName))
      return false;
    if (other.championSensitivity != this.championSensitivity)
      return false;
    if (other.LastFrame != this.LastFrame)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.partnerTrackingName);
    result = prime * result + Double.hashCode(this.totalTrackerTime);
    result = prime * result + Double.hashCode(this.totalDetectorTime);
    result = prime * result + java.util.Objects.hashCode(this.videoName);
    result = prime * result + java.util.Objects.hashCode(this.championName);
    result = prime * result + Integer.hashCode(this.championSensitivity);
    result = prime * result + Integer.hashCode(this.LastFrame);
    return result;
  }
  
  @SyntheticMember
  public TrackerRole(final Agent agent) {
    super(agent);
  }
}
