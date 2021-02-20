package be.uclouvain.aptitude.agents;

import be.uclouvain.aptitude.detection.Detection;
import be.uclouvain.aptitude.detection.DetectionImpl;
import be.uclouvain.aptitude.other.BBoxes2DResult;
import be.uclouvain.aptitude.other.BBoxes2DTrackResult;
import be.uclouvain.aptitude.other.PartnerDetectionFound;
import be.uclouvain.aptitude.other.PartnerTrackingFound;
import be.uclouvain.aptitude.tracking.Tracking;
import be.uclouvain.aptitude.tracking.TrackingImpl;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import java.io.FileReader;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("discouraged_reference")
@SarlSpecification("0.11")
@SarlElementType(19)
public class CameraAgent extends Agent {
  private String partnerDetectionName;
  
  private String partnerTrackingName;
  
  private long start;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      DetectionImpl _detectionImpl = new DetectionImpl();
      this.<DetectionImpl>setSkill(_detectionImpl);
      TrackingImpl _trackingImpl = new TrackingImpl();
      this.<TrackingImpl>setSkill(_trackingImpl);
      JSONParser parser = new JSONParser();
      String configPathDetector = occurrence.parameters[0].toString();
      FileReader _fileReader = new FileReader(configPathDetector);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonDetector = ((JSONObject) _parse);
      Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER.requestDetector(jsonDetector);
      String configPathTracker = occurrence.parameters[1].toString();
      FileReader _fileReader_1 = new FileReader(configPathTracker);
      Object _parse_1 = parser.parse(_fileReader_1);
      JSONObject jsonTracker = ((JSONObject) _parse_1);
      Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER.requestTracker(jsonTracker);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$PartnerDetectionFound$1(final PartnerDetectionFound occurrence) {
    try {
      this.partnerDetectionName = occurrence.partnerName;
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Detection partner found: " + this.partnerDetectionName));
      Thread.sleep(2000);
      Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER.sendAction(1);
      this.start = System.currentTimeMillis();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$PartnerTrackingFound$2(final PartnerTrackingFound occurrence) {
    this.partnerTrackingName = occurrence.partnerName;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Tracking Partner found: " + this.partnerTrackingName));
  }
  
  private void $behaviorUnit$BBoxes2DResult$3(final BBoxes2DResult occurrence) {
    Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER.getTrack(occurrence.bboxes2D);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$4(final BBoxes2DTrackResult occurrence) {
    boolean _isLastFrame = occurrence.bboxes2DTrack.isLastFrame();
    if (_isLastFrame) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("That was it!");
      long _currentTimeMillis = System.currentTimeMillis();
      final long totalTime = ((_currentTimeMillis - this.start) / 1000);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _string = Long.valueOf(totalTime).toString();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((("It took " + _string) + " seconds"));
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      int _frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
      String _string_1 = Long.valueOf((_frameNumber / totalTime)).toString();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Average FPS : " + _string_1));
    }
  }
  
  private void $behaviorUnit$Destroy$5(final Destroy occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
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
  @ImportedCapacityFeature(Detection.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION;
  
  @SyntheticMember
  @Pure
  private Detection $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION = $getSkill(Detection.class);
    }
    return $castSkill(Detection.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION);
  }
  
  @Extension
  @ImportedCapacityFeature(Tracking.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING;
  
  @SyntheticMember
  @Pure
  private Tracking $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING = $getSkill(Tracking.class);
    }
    return $castSkill(Tracking.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING);
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
  private void $guardEvaluator$BBoxes2DTrackResult(final BBoxes2DTrackResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResult$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$5(occurrence));
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
  private void $guardEvaluator$PartnerDetectionFound(final PartnerDetectionFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerDetectionFound$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerTrackingFound(final PartnerTrackingFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerTrackingFound$2(occurrence));
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
    CameraAgent other = (CameraAgent) obj;
    if (!Objects.equals(this.partnerDetectionName, other.partnerDetectionName))
      return false;
    if (!Objects.equals(this.partnerTrackingName, other.partnerTrackingName))
      return false;
    if (other.start != this.start)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.partnerDetectionName);
    result = prime * result + Objects.hashCode(this.partnerTrackingName);
    result = prime * result + Long.hashCode(this.start);
    return result;
  }
  
  @SyntheticMember
  public CameraAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public CameraAgent(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public CameraAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
