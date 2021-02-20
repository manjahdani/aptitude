package be.uclouvain.aptitudeAgents;

import be.uclouvain.aptitude.detection.Detection;
import be.uclouvain.aptitude.detection.DetectionImpl;
import be.uclouvain.aptitude.other.BBoxes2DResult;
import be.uclouvain.aptitude.other.BBoxes2DTrackResult;
import be.uclouvain.aptitude.other.PartnerDetectionFound;
import be.uclouvain.aptitude.other.PartnerTrackingFound;
import be.uclouvain.aptitude.tracking.Tracking;
import be.uclouvain.aptitude.tracking.TrackingImpl;
import be.uclouvain.organisation.platform.ObserverCapacity;
import be.uclouvain.organisation.told.LeavePlatform;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import java.io.FileReader;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class ObserverRole extends Behavior {
  protected OpenEventSpace PlatformSpace;
  
  protected UUID PlatformID;
  
  protected UUID ExpertID;
  
  private String configPathYOLO = "F:/aptitude/aptitude-agents/src/main/resources/config/test-YOLO.json";
  
  private String configPathTinyYOLO = "F:/aptitude/aptitude-agents/src/main/resources/config/test-TinyYOLO.json";
  
  private String configPathSORT = "F:/aptitude/aptitude-agents/src/main/resources/config/test-SORT.json";
  
  private String configPathDeepSORT = "F:/aptitude/aptitude-agents/src/main/resources/config/test-DeepSORT.json";
  
  private String partnerDetectionName;
  
  private String partnerTrackingName;
  
  private long start;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("My Observer Role started");
      DetectionImpl _detectionImpl = new DetectionImpl();
      this.<DetectionImpl>setSkill(_detectionImpl);
      TrackingImpl _trackingImpl = new TrackingImpl();
      this.<TrackingImpl>setSkill(_trackingImpl);
      JSONParser parser = new JSONParser();
      String configPathDetector = this.configPathYOLO;
      FileReader _fileReader = new FileReader(configPathDetector);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonDetector = ((JSONObject) _parse);
      Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_DETECTION_DETECTION$CALLER.requestDetector(jsonDetector);
      String configPathTracker = this.configPathSORT;
      FileReader _fileReader_1 = new FileReader(configPathTracker);
      Object _parse_1 = parser.parse(_fileReader_1);
      JSONObject jsonTracker = ((JSONObject) _parse_1);
      Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER.requestTracker(jsonTracker);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$LeavePlatform$1(final LeavePlatform occurrence) {
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.leave(this.PlatformID);
  }
  
  private void $behaviorUnit$PartnerDetectionFound$2(final PartnerDetectionFound occurrence) {
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
  
  private void $behaviorUnit$PartnerTrackingFound$3(final PartnerTrackingFound occurrence) {
    this.partnerTrackingName = occurrence.partnerName;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Tracking Partner found: " + this.partnerTrackingName));
  }
  
  private void $behaviorUnit$BBoxes2DResult$4(final BBoxes2DResult occurrence) {
    Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_TRACKING_TRACKING$CALLER.getTrack(occurrence.bboxes2D);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$5(final BBoxes2DTrackResult occurrence) {
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
  
  private void $behaviorUnit$Destroy$6(final Destroy occurrence) {
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
  @ImportedCapacityFeature(ObserverCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY;
  
  @SyntheticMember
  @Pure
  private ObserverCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY = $getSkill(ObserverCapacity.class);
    }
    return $castSkill(ObserverCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LeavePlatform(final LeavePlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$1(occurrence));
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerDetectionFound(final PartnerDetectionFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerDetectionFound$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerTrackingFound(final PartnerTrackingFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerTrackingFound$3(occurrence));
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
    ObserverRole other = (ObserverRole) obj;
    if (!Objects.equals(this.PlatformID, other.PlatformID))
      return false;
    if (!Objects.equals(this.ExpertID, other.ExpertID))
      return false;
    if (!Objects.equals(this.configPathYOLO, other.configPathYOLO))
      return false;
    if (!Objects.equals(this.configPathTinyYOLO, other.configPathTinyYOLO))
      return false;
    if (!Objects.equals(this.configPathSORT, other.configPathSORT))
      return false;
    if (!Objects.equals(this.configPathDeepSORT, other.configPathDeepSORT))
      return false;
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
    result = prime * result + Objects.hashCode(this.PlatformID);
    result = prime * result + Objects.hashCode(this.ExpertID);
    result = prime * result + Objects.hashCode(this.configPathYOLO);
    result = prime * result + Objects.hashCode(this.configPathTinyYOLO);
    result = prime * result + Objects.hashCode(this.configPathSORT);
    result = prime * result + Objects.hashCode(this.configPathDeepSORT);
    result = prime * result + Objects.hashCode(this.partnerDetectionName);
    result = prime * result + Objects.hashCode(this.partnerTrackingName);
    result = prime * result + Long.hashCode(this.start);
    return result;
  }
  
  @SyntheticMember
  public ObserverRole(final Agent agent) {
    super(agent);
  }
}
