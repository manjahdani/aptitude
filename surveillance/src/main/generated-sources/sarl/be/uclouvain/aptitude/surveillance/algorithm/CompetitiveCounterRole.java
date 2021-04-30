package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.CompetitionResults;
import be.uclouvain.aptitude.surveillance.algorithm.Evaluation;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationImpl;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationResult;
import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
  
  private final TreeMap<UUID, CompetitionResults> CompResults = new TreeMap<UUID, CompetitionResults>();
  
  private String EvaluationPartnerName;
  
  private String gtFile = "F:/data/S05C016/gt/gt.txt";
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      this.sensitivity.add(Integer.valueOf(1));
      this.isMaster = Boolean.valueOf(true);
      EvaluationImpl _evaluationImpl = new EvaluationImpl();
      this.<EvaluationImpl>setSkill(_evaluationImpl, Evaluation.class);
      String configPath = "F:/aptitude/surveillance/src/main/resources/config/evalconfig.json";
      JSONParser parser = new JSONParser();
      String configPathEvaluator = configPath;
      FileReader _fileReader = new FileReader(configPathEvaluator);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonEvaluator = ((JSONObject) _parse);
      Evaluation _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER.ActivateAccess(jsonEvaluator);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
    for (final String ObserverName : this.AVAILABLE_OBSERVERS) {
      {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + ObserverName));
        ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
        OpenEventSpace _get = ((OpenEventSpace[])Conversions.unwrapArray(this.MissionSpaceList.values(), OpenEventSpace.class))[0];
        AlgorithmInfo _algorithmInfo = new AlgorithmInfo(ObserverName, "TRACKER");
        AddAlgorithm _addAlgorithm = new AddAlgorithm(_get, _algorithmInfo);
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
  
  private void $behaviorUnit$LastFrame$3(final LastFrame occurrence) {
    try {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("Receveid " + occurrence.pred_file_Path) + Integer.valueOf(occurrence.frameNumber)));
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Sending to evaluation");
      byte[] _readAllBytes = Files.readAllBytes(Paths.get(occurrence.pred_file_Path));
      String predictions = new String(_readAllBytes);
      byte[] _readAllBytes_1 = Files.readAllBytes(Paths.get(this.gtFile));
      String gts = new String(_readAllBytes_1);
      UUID testID = UUID.randomUUID();
      UUID _uUID = occurrence.getSource().getUUID();
      CompetitionResults _competitionResults = new CompetitionResults(_uUID, 
        occurrence.pred_file_Path, 
        occurrence.total_time_detection, 
        occurrence.total_time_tracking);
      this.CompResults.put(testID, _competitionResults);
      Evaluation _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER.sendEvaluationRequest(testID.toString(), predictions, gts);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$EvaluationResult$4(final EvaluationResult occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Message Evaluation Received");
    this.CompResults.get(UUID.fromString(occurrence.result.getRequestID())).setValues(occurrence.result);
    InputOutput.<String>println("***********************");
    String _requestID = occurrence.result.getRequestID();
    InputOutput.<String>println(("           reqID \t" + _requestID));
    this.CompResults.get(UUID.fromString(occurrence.result.getRequestID())).EvaluationPrint();
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
  private void $guardEvaluator$EvaluationResult(final EvaluationResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$EvaluationResult$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerEvaluationFound(final PartnerEvaluationFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerEvaluationFound$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LastFrame(final LastFrame occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LastFrame$3(occurrence));
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
    CompetitiveCounterRole other = (CompetitiveCounterRole) obj;
    if (other.Comp_Duration != this.Comp_Duration)
      return false;
    if (!java.util.Objects.equals(this.EvaluationPartnerName, other.EvaluationPartnerName))
      return false;
    if (!java.util.Objects.equals(this.gtFile, other.gtFile))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.Comp_Duration);
    result = prime * result + java.util.Objects.hashCode(this.EvaluationPartnerName);
    result = prime * result + java.util.Objects.hashCode(this.gtFile);
    return result;
  }
  
  @SyntheticMember
  public CompetitiveCounterRole(final Agent agent) {
    super(agent);
  }
}
