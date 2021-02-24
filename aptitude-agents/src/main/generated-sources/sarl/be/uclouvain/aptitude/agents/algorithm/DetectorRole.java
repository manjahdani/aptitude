package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.agents.algorithm.Detection;
import be.uclouvain.aptitude.agents.algorithm.DetectionImpl;
import be.uclouvain.aptitude.agents.algorithm.PartnerDetectionFound;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.told.LeavePlatform;
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
public class DetectorRole extends ObserverRole {
  private String partnerDetectionName;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      DetectionImpl _detectionImpl = new DetectionImpl();
      this.<DetectionImpl>setSkill(_detectionImpl);
      JSONParser parser = new JSONParser();
      String configPathDetector = occurrence.parameters[0].toString();
      FileReader _fileReader = new FileReader(configPathDetector);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonDetector = ((JSONObject) _parse);
      Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER.requestDetector(jsonDetector);
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
      Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER.sendAction(1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$BBoxes2DResult$2(final BBoxes2DResult occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    BBoxes2DResult _bBoxes2DResult = new BBoxes2DResult(occurrence.bboxes2D);
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
        return Objects.equal(_uUID, DetectorRole.this.observerID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, DetectorRole.this.observerID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_bBoxes2DResult, _function);
  }
  
  private void $behaviorUnit$Destroy$3(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The behavior was stopped.");
  }
  
  private void $behaviorUnit$LeavePlatform$4(final LeavePlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Observer Leaving");
    Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER.sendAction(4);
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
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION;
  
  @SyntheticMember
  @Pure
  private Detection $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION = $getSkill(Detection.class);
    }
    return $castSkill(Detection.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION);
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$2(occurrence));
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
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$3(occurrence));
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
    DetectorRole other = (DetectorRole) obj;
    if (!java.util.Objects.equals(this.partnerDetectionName, other.partnerDetectionName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.partnerDetectionName);
    return result;
  }
  
  @SyntheticMember
  public DetectorRole(final Agent agent) {
    super(agent);
  }
}
