package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerTrackingFound;
import be.uclouvain.aptitude.surveillance.algorithm.PythonTwinObserverAccess;
import be.uclouvain.aptitude.surveillance.algorithm.TrackerPythonTwin;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBOX;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxes2D;
import be.uclouvain.organisation.platform.AddAlgorithm;
import be.uclouvain.organisation.platform.LeavePlatform;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
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
  
  private long totalTrackerTime = 0;
  
  private final List<ArrayList<BBoxes2D>> dynamicTrackingMemory = Collections.<ArrayList<BBoxes2D>>synchronizedList(new LinkedList<ArrayList<BBoxes2D>>());
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      this.intensityMap.put(Integer.valueOf(0), Integer.valueOf(1));
      this.intensityMap.put(Integer.valueOf(1), Integer.valueOf(0));
      this.intensityMap.put(Integer.valueOf(2), Integer.valueOf(0));
      this.intensityMap.put(Integer.valueOf(3), Integer.valueOf(1));
      TrackerPythonTwin _trackerPythonTwin = new TrackerPythonTwin();
      this.<TrackerPythonTwin>setSkill(_trackerPythonTwin);
      JSONParser parser = new JSONParser();
      String configPathTracker = occurrence.parameters[0].toString();
      FileReader _fileReader = new FileReader(configPathTracker);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonTracker = ((JSONObject) _parse);
      PythonTwinObserverAccess _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESS$CALLER.ActivateAccess(jsonTracker);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The behavior was stopped.");
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
  
  private void $behaviorUnit$MissionSensitivity$4(final MissionSensitivity occurrence) {
    this.sensitivity.set(occurrence.s);
    final String ObserverName = this.availableObservers.get(((this.intensityMap.get(Integer.valueOf(this.sensitivity.get()))) == null ? 0 : (this.intensityMap.get(Integer.valueOf(this.sensitivity.get()))).intValue()));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + ObserverName));
    AlgorithmInfo _algorithmInfo = new AlgorithmInfo(ObserverName, "DETECTOR");
    AddAlgorithm _addAlgorithm = new AddAlgorithm(this.MissionSpace, _algorithmInfo);
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
    this.PlatformSpace.emit(this.getOwner().getID(), _addAlgorithm, _function);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$5(final BBoxes2DTrackResult occurrence) {
    try {
      int _dimWidth = occurrence.bboxes2DTrack.getDimWidth();
      double ratio_width = (1920.0 / _dimWidth);
      int _dimHeight = occurrence.bboxes2DTrack.getDimHeight();
      double ratio_height = (1080.0 / _dimHeight);
      int frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
      ArrayList<BBoxes2D> tmp = CollectionLiterals.<BBoxes2D>newArrayList();
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
          BBOX _bBOX = new BBOX(X, Y, W, H);
          BBoxes2D _bBoxes2D = new BBoxes2D(_bBOX, conf, globalID, classID, frameNumber);
          tmp.add(_bBoxes2D);
        }
      }
      this.dynamicTrackingMemory.add(frameNumber, tmp);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SuppressWarnings("discouraged_occurrence_readonly_use")
  private void $behaviorUnit$BBoxes2DResult$6(final BBoxes2DResult occurrence) {
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MissionSensitivity$4(occurrence));
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
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$1(occurrence));
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
    if (other.totalTrackerTime != this.totalTrackerTime)
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
    result = prime * result + Long.hashCode(this.totalTrackerTime);
    return result;
  }
  
  @SyntheticMember
  public TrackerRole(final Agent agent) {
    super(agent);
  }
}
