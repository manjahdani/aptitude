package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.agents.algorithm.CountingSkill;
import be.uclouvain.aptitude.agents.algorithm.ObserverCapacity;
import be.uclouvain.aptitude.agents.algorithm.util.BBOX;
import be.uclouvain.aptitude.agents.algorithm.util.BBoxes2D;
import be.uclouvain.organisation.OrganisationInfo;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.told.AlgorithmJoinPlatform;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class CounterRole extends ObserverRole {
  private final TreeMap<Integer, BBoxes2D> ObjectPresentInframe = new TreeMap<Integer, BBoxes2D>();
  
  private final ArrayList<BBoxes2D> ObjectToBeAnalyzed = new ArrayList<BBoxes2D>();
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    CountingSkill _countingSkill = new CountingSkill();
    this.<CountingSkill>setSkill(_countingSkill, ObserverCapacity.class);
  }
  
  private void $behaviorUnit$OrganisationInfo$1(final OrganisationInfo occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((((("Joining the Platform organisation: " + occurrence.spaceID) + " (") + occurrence.context) + ")."));
    this.TOLDID = occurrence.getSource().getUUID();
    this.TOLDSpace = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.TOLDSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    AlgorithmJoinPlatform _algorithmJoinPlatform = new AlgorithmJoinPlatform(occurrence.context, occurrence.spaceID, "SORT", "Tracker");
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.TOLDSpace, _algorithmJoinPlatform);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$2(final BBoxes2DTrackResult occurrence) {
    int _dimWidth = occurrence.bboxes2DTrack.getDimWidth();
    double ratio_width = (1920.0 / _dimWidth);
    int _dimHeight = occurrence.bboxes2DTrack.getDimHeight();
    double ratio_height = (1080.0 / _dimHeight);
    int frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
    this.ObjectToBeAnalyzed.clear();
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
        boolean _containsKey = this.ObjectPresentInframe.containsKey(Integer.valueOf(globalID));
        if (_containsKey) {
          this.ObjectPresentInframe.get(Integer.valueOf(globalID)).update(X, Y, W, H, classID, frameNumber, conf);
        } else {
          BBOX _bBOX = new BBOX(X, Y, W, H);
          BBoxes2D _bBoxes2D = new BBoxes2D(_bBOX, conf, globalID, classID, frameNumber);
          this.ObjectPresentInframe.put(Integer.valueOf(globalID), _bBoxes2D);
        }
        this.ObjectToBeAnalyzed.add(this.ObjectPresentInframe.get(Integer.valueOf(globalID)));
      }
    }
    ObserverCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY$CALLER();
    ArrayList<BBoxes2D> _arrayList = new ArrayList<BBoxes2D>(this.ObjectToBeAnalyzed);
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY$CALLER.Signal2Perception(_arrayList);
    boolean _isLastFrame = occurrence.bboxes2DTrack.isLastFrame();
    if (_isLastFrame) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("That was it! ");
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
  @ImportedCapacityFeature(ObserverCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY;
  
  @SyntheticMember
  @Pure
  private ObserverCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY = $getSkill(ObserverCapacity.class);
    }
    return $castSkill(ObserverCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_OBSERVERCAPACITY);
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
  private void $guardEvaluator$BBoxes2DTrackResult(final BBoxes2DTrackResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResult$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$OrganisationInfo(final OrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$OrganisationInfo$1(occurrence));
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
  public CounterRole(final Agent agent) {
    super(agent);
  }
}
