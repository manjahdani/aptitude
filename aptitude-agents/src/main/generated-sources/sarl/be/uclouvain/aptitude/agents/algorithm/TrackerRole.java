package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.agents.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.agents.algorithm.PartnerTrackingFound;
import be.uclouvain.aptitude.agents.algorithm.Tracking;
import be.uclouvain.aptitude.agents.algorithm.TrackingImpl;
import be.uclouvain.organisation.OrganisationInfo;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.told.AlgorithmJoinPlatform;
import be.uclouvain.organisation.told.LeavePlatform;
import com.google.common.base.Objects;
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
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.FileReader;
import java.io.ObjectStreamException;
import java.util.Collection;
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
public class TrackerRole extends ObserverRole {
  private String partnerTrackingName;
  
  protected OpenEventSpace PlatformSpace;
  
  protected UUID PlatformID;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      TrackingImpl _trackingImpl = new TrackingImpl();
      this.<TrackingImpl>setSkill(_trackingImpl);
      JSONParser parser = new JSONParser();
      String configPathTracker = occurrence.parameters[0].toString();
      FileReader _fileReader = new FileReader(configPathTracker);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonTracker = ((JSONObject) _parse);
      Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER.requestTracker(jsonTracker);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$Destroy$1(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The behavior was stopped.");
  }
  
  private void $behaviorUnit$PartnerTrackingFound$2(final PartnerTrackingFound occurrence) {
    this.partnerTrackingName = occurrence.partnerName;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Tracking Partner found: " + this.partnerTrackingName));
  }
  
  private void $behaviorUnit$LeavePlatform$3(final LeavePlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ObserverTrackerLeaving");
  }
  
  private void $behaviorUnit$OrganisationInfo$4(final OrganisationInfo occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((((("Joining the Platform organisation: " + occurrence.spaceID) + " (") + occurrence.context) + ")."));
    this.PlatformID = occurrence.getSource().getUUID();
    this.PlatformSpace = occurrence.spaceID;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    this.PlatformSpace.register(_$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener());
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    AlgorithmJoinPlatform _algorithmJoinPlatform = new AlgorithmJoinPlatform(occurrence.context, occurrence.spaceID, "TinyYOLO", "Detector");
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.PlatformSpace, _algorithmJoinPlatform);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$5(final BBoxes2DTrackResult occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    BBoxes2DTrackResult _bBoxes2DTrackResult = new BBoxes2DTrackResult(occurrence.bboxes2DTrack);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_observerID_1;
      
      public $SerializableClosureProxy(final UUID $_observerID_1) {
        this.$_observerID_1 = $_observerID_1;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_observerID_1);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, TrackerRole.this.observerID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, TrackerRole.this.observerID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_bBoxes2DTrackResult, _function);
  }
  
  private void $behaviorUnit$BBoxes2DResult$6(final BBoxes2DResult occurrence) {
    Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER.getTrack(occurrence.bboxes2D);
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
  @ImportedCapacityFeature(Tracking.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING;
  
  @SyntheticMember
  @Pure
  private Tracking $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING = $getSkill(Tracking.class);
    }
    return $castSkill(Tracking.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING);
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
  private void $guardEvaluator$PartnerTrackingFound(final PartnerTrackingFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerTrackingFound$2(occurrence));
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$OrganisationInfo(final OrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$OrganisationInfo$4(occurrence));
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
    if (!java.util.Objects.equals(this.PlatformID, other.PlatformID))
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
    result = prime * result + java.util.Objects.hashCode(this.PlatformID);
    return result;
  }
  
  @SyntheticMember
  public TrackerRole(final Agent agent) {
    super(agent);
  }
}
