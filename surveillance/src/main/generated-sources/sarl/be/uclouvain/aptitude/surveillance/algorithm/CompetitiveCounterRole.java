package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.surveillance.algorithm.Evaluation;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerEvaluationFound;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxes2D;
import be.uclouvain.organisation.platform.AddAlgorithm;
import be.uclouvain.organisation.platform.CounterObserverCapacity;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.ExternalContextAccess;
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
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class CompetitiveCounterRole extends ObserverRole {
  private long Comp_Duration;
  
  private final ArrayList<String> AVAILABLE_OBSERVERS = CollectionLiterals.<String>newArrayList("SORT", "DeepSORT");
  
  private final TreeMap<Integer, BBoxes2D> ObjectPresentInframe = new TreeMap<Integer, BBoxes2D>();
  
  private final ArrayList<BBoxes2D> ObjectToBeAnalyzed = new ArrayList<BBoxes2D>();
  
  private String EvaluationPartnerName;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field SENSITIVITY is undefined"
      + "\nThe method or field SENSITIVITY is undefined"
      + "\nadd cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PartnerEvaluationFound$1(final PartnerEvaluationFound occurrence) {
    this.EvaluationPartnerName = occurrence.partnerName;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("I found my partner" + this.EvaluationPartnerName));
    this.Comp_Duration = System.currentTimeMillis();
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$MissionSensitivity$2(final MissionSensitivity occurrence) {
    this.sensitivity.set(occurrence.s);
    for (final String ObserverName : this.AVAILABLE_OBSERVERS) {
      {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + ObserverName));
        ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
        AlgorithmInfo _algorithmInfo = new AlgorithmInfo(ObserverName, "TRACKER");
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
            UUID _iD = CompetitiveCounterRole.this.PlatformContext.getID();
            return Objects.equal(_uUID, _iD);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, CompetitiveCounterRole.this.PlatformContext.getID());
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.PlatformSpace, _addAlgorithm, _function);
      }
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$BBoxes2DTrackResult$3(final BBoxes2DTrackResult occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field globalID is undefined"
      + "\nThe method or field globalID is undefined"
      + "\nThe method or field X is undefined"
      + "\nThe method or field Y is undefined"
      + "\nThe method or field W is undefined"
      + "\nThe method or field H is undefined"
      + "\nThe method or field classID is undefined"
      + "\nThe method or field frameNumber is undefined"
      + "\nThe method or field conf is undefined"
      + "\nThe method or field globalID is undefined"
      + "\nThe method or field X is undefined"
      + "\nThe method or field Y is undefined"
      + "\nThe method or field W is undefined"
      + "\nThe method or field H is undefined"
      + "\nThe method or field conf is undefined"
      + "\nThe method or field globalID is undefined"
      + "\nThe method or field classID is undefined"
      + "\nThe method or field frameNumber is undefined"
      + "\nThe method or field globalID is undefined");
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
  @ImportedCapacityFeature(CounterObserverCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY;
  
  @SyntheticMember
  @Pure
  private CounterObserverCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY = $getSkill(CounterObserverCapacity.class);
    }
    return $castSkill(CounterObserverCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(Evaluation.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION;
  
  @SyntheticMember
  @Pure
  private Evaluation $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION = $getSkill(Evaluation.class);
    }
    return $castSkill(Evaluation.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION);
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
  private void $guardEvaluator$MissionSensitivity(final MissionSensitivity occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MissionSensitivity$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DTrackResult(final BBoxes2DTrackResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResult$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerEvaluationFound(final PartnerEvaluationFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerEvaluationFound$1(occurrence));
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'Signal2Perception\' expecting \'}\'"
      + "\nmismatched input \'Signal2Perception\' expecting \'}\'");
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    throw new Error("Unresolved compilation problems:"
      + "\nmismatched input \'Signal2Perception\' expecting \'}\'");
  }
  
  @SyntheticMember
  public CompetitiveCounterRole(final Agent agent) {
    super(agent);
  }
}
