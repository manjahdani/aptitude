package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.CompetitionResults;
import be.uclouvain.aptitude.surveillance.algorithm.CounterRole;
import be.uclouvain.aptitude.surveillance.algorithm.Evaluation;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationImpl;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationResult;
import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerEvaluationFound;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingRequest;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxes2D;
import be.uclouvain.organisation.platform.AddAlgorithm;
import be.uclouvain.organisation.platform.MissionSensitivity;
import be.uclouvain.organisation.platform.ObserverRole;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
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
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.MapExtensions;
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
  
  private final ArrayList<String> AVAILABLE_OBSERVERS = CollectionLiterals.<String>newArrayList("SORT");
  
  private final TreeMap<Integer, BBoxes2D> ObjectPresentInframe = new TreeMap<Integer, BBoxes2D>();
  
  private final ArrayList<BBoxes2D> ObjectToBeAnalyzed = new ArrayList<BBoxes2D>();
  
  private final TreeMap<UUID, CompetitionResults> CompResults = new TreeMap<UUID, CompetitionResults>();
  
  private String EvaluationPartnerName;
  
  private Integer ExpertSensitivity;
  
  private String gtFile = "F:/data/S02C006/gt/gt.txt";
  
  private int NumberTests = 0;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      this.sensitivity.add(Integer.valueOf(0));
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
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Let the competition start !");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PartnerEvaluationFound$1(final PartnerEvaluationFound occurrence) {
    this.EvaluationPartnerName = occurrence.partnerName;
    this.Comp_Duration = System.currentTimeMillis();
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$MissionSensitivity$2(final MissionSensitivity occurrence) {
    this.ExpertSensitivity = occurrence.s.get(0);
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
      byte[] _readAllBytes = Files.readAllBytes(Paths.get(occurrence.pred_file_Path));
      String predictions = new String(_readAllBytes);
      byte[] _readAllBytes_1 = Files.readAllBytes(Paths.get(this.gtFile));
      String gts = new String(_readAllBytes_1);
      UUID testID = UUID.randomUUID();
      UUID _uUID = occurrence.getSource().getUUID();
      CompetitionResults _competitionResults = new CompetitionResults(_uUID, 
        occurrence.pred_file_Path, 
        occurrence.total_time_detection, 
        occurrence.total_time_tracking, occurrence.frameNumber);
      this.CompResults.put(testID, _competitionResults);
      Evaluation _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER.sendEvaluationRequest(testID.toString(), predictions, gts);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$EvaluationResult$4(final EvaluationResult occurrence) {
    synchronized (this) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Message Evaluation Received");
      double _hOTA = this.CompResults.get(UUID.fromString(occurrence.result.getRequestID())).getHOTA();
      if ((_hOTA == 0)) {
        this.CompResults.get(UUID.fromString(occurrence.result.getRequestID())).setValues(occurrence.result);
        int _NumberTests = this.NumberTests;
        this.NumberTests = (_NumberTests + 1);
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("Recording the result of Competitor " + Integer.valueOf(this.NumberTests)));
        if ((this.NumberTests == 2)) {
          this.SelectChampion();
        }
      }
    }
  }
  
  public void SelectChampion() {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Competition is over. Lets choose the Champion !");
    TreeMap<Double, String> res = new TreeMap<Double, String>();
    Collection<CompetitionResults> _values = this.CompResults.values();
    for (final CompetitionResults l : _values) {
      {
        double HOTA = l.getHOTA();
        double _trackingTime = l.getTrackingTime();
        double _detectionTime = l.getDetectionTime();
        int _frame = l.getFrame();
        double sframe = ((_trackingTime + _detectionTime) / _frame);
        double effeciency = ((0.51 - (2 * sframe)) / 0.49);
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("HOTA :" + Double.valueOf(HOTA)));
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Effeciency :" + Double.valueOf(effeciency)));
        res.put(Double.valueOf((HOTA + (((this.ExpertSensitivity) == null ? 0 : (this.ExpertSensitivity).intValue()) * effeciency))), l.getBelief());
      }
    }
    Double _xifexpression = null;
    Double _firstKey = res.firstKey();
    Double _lastKey = res.lastKey();
    if ((_firstKey.doubleValue() > _lastKey.doubleValue())) {
      _xifexpression = res.firstKey();
    } else {
      _xifexpression = res.lastKey();
    }
    Double maxValue = _xifexpression;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _get = res.get(maxValue);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((("Congratulations to " + maxValue) + _get));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    Agent _owner = this.getOwner();
    CounterRole _counterRole = new CounterRole(_owner);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_counterRole);
    final Function2<UUID, CompetitionResults, Boolean> _function = (UUID p1, CompetitionResults p2) -> {
      String _belief = p2.getBelief();
      String _get_1 = res.get(maxValue);
      return Boolean.valueOf(Objects.equal(_belief, _get_1));
    };
    UUID trackingGuy = ((CompetitionResults[])Conversions.unwrapArray(MapExtensions.<UUID, CompetitionResults>filter(this.CompResults, _function).values(), CompetitionResults.class))[0].getCompetitorID();
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    String _get_1 = res.get(maxValue);
    TrackingRequest _trackingRequest = new TrackingRequest(_get_1);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID trackingGuy;
      
      public $SerializableClosureProxy(final UUID trackingGuy) {
        this.trackingGuy = trackingGuy;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, trackingGuy);
      }
    }
    final Scope<Address> _function_1 = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, trackingGuy);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, trackingGuy);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_trackingRequest, _function_1);
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
    if (other.ExpertSensitivity == null) {
      if (this.ExpertSensitivity != null)
        return false;
    } else if (this.ExpertSensitivity == null)
      return false;
    if (other.ExpertSensitivity != null && other.ExpertSensitivity.intValue() != this.ExpertSensitivity.intValue())
      return false;
    if (!java.util.Objects.equals(this.gtFile, other.gtFile))
      return false;
    if (other.NumberTests != this.NumberTests)
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
    result = prime * result + java.util.Objects.hashCode(this.ExpertSensitivity);
    result = prime * result + java.util.Objects.hashCode(this.gtFile);
    result = prime * result + Integer.hashCode(this.NumberTests);
    return result;
  }
  
  @SyntheticMember
  public CompetitiveCounterRole(final Agent agent) {
    super(agent);
  }
}
