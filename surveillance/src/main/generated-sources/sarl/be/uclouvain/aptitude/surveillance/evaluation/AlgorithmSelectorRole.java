package be.uclouvain.aptitude.surveillance.evaluation;

import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.counter.CounterRole;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackingRequest;
import be.uclouvain.aptitude.surveillance.algorithm.util.EvaluationResults;
import be.uclouvain.aptitude.surveillance.algorithm.util.Metric;
import be.uclouvain.aptitude.surveillance.evaluation.AlgorithmSelection;
import be.uclouvain.aptitude.surveillance.evaluation.AlgorithmSelectionSkill;
import be.uclouvain.aptitude.surveillance.evaluation.Evaluation;
import be.uclouvain.aptitude.surveillance.evaluation.EvaluationImpl;
import be.uclouvain.python_access.EvaluationResult;
import be.uclouvain.python_access.PartnerEvaluationFound;
import be.uclouvain.python_access.messages.EvaluationMessage;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
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
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.FileReader;
import java.io.ObjectStreamException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @TODO : To complete
 * 
 * @author  $ manjahdani$
 * @version $0.0.1$
 * @date $17/06/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class AlgorithmSelectorRole extends Behavior {
  private String evaluationPartnerName;
  
  protected long Comp_Duration;
  
  protected int numberTests = 0;
  
  protected Metric score;
  
  protected String gts;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      Object _get = occurrence.parameters[0];
      this.score = ((Metric) _get);
      byte[] _readAllBytes = Files.readAllBytes(Paths.get(this.score.getGtFilePatch()));
      String _string = new String(_readAllBytes);
      this.gts = _string;
      EvaluationImpl _evaluationImpl = new EvaluationImpl();
      this.<EvaluationImpl>setSkill(_evaluationImpl, Evaluation.class);
      String[] _params = this.score.getParams();
      AlgorithmSelectionSkill _algorithmSelectionSkill = new AlgorithmSelectionSkill(_params);
      this.<AlgorithmSelectionSkill>setSkill(_algorithmSelectionSkill, AlgorithmSelection.class);
      String configPath = "F:/aptitude/surveillance/src/main/resources/config/evalconfig.json";
      JSONParser parser = new JSONParser();
      String configPathEvaluator = configPath;
      FileReader _fileReader = new FileReader(configPathEvaluator);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonEvaluator = ((JSONObject) _parse);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Activating access");
      Evaluation _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION$CALLER.activateAccess(jsonEvaluator);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Access activated");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PartnerEvaluationFound$1(final PartnerEvaluationFound occurrence) {
    this.evaluationPartnerName = occurrence.partnerName;
    this.Comp_Duration = System.currentTimeMillis();
  }
  
  private void $behaviorUnit$LastFrame$2(final LastFrame occurrence) {
    try {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("Receveid " + occurrence.pred_file_Path) + Integer.valueOf(occurrence.frameNumber)));
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Sending to evaluation");
      byte[] _readAllBytes = Files.readAllBytes(Paths.get(occurrence.pred_file_Path));
      String predictions = new String(_readAllBytes);
      UUID testID = UUID.randomUUID();
      AlgorithmSelection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER();
      UUID _uUID = occurrence.getSource().getUUID();
      EvaluationResults _evaluationResults = new EvaluationResults(_uUID, occurrence.pred_file_Path, occurrence.total_time_detection, 
        occurrence.total_time_tracking, occurrence.frameNumber);
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER.addResult(testID, _evaluationResults);
      Evaluation _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION$CALLER.sendEvaluationRequest(testID.toString(), predictions, this.gts);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$EvaluationResult$3(final EvaluationResult occurrence) {
    synchronized (this) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Message Evaluation Received");
      AlgorithmSelection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER();
      boolean _addResult = _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER.addResult(UUID.fromString(occurrence.result.getRequestID()), occurrence.result);
      if (_addResult) {
        int _numberTests = this.numberTests;
        this.numberTests = (_numberTests + 1);
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("Recording the result of Competitor " + Integer.valueOf(this.numberTests)));
        if ((this.numberTests == 2)) {
          AlgorithmSelection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER_1 = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER();
          EvaluationResults selected = _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER_1.selectAlgorithm();
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
          String _belief = selected.getBelief();
          TrackingRequest _trackingRequest = new TrackingRequest(_belief);
          class $SerializableClosureProxy implements Scope<Address> {
            
            private final String $_belief;
            
            public $SerializableClosureProxy(final String $_belief) {
              this.$_belief = $_belief;
            }
            
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              return Objects.equal(_uUID, $_belief);
            }
          }
          final Scope<Address> _function = new Scope<Address>() {
            @Override
            public boolean matches(final Address it) {
              UUID _uUID = it.getUUID();
              String _belief = selected.getBelief();
              return Objects.equal(_uUID, _belief);
            }
            private Object writeReplace() throws ObjectStreamException {
              return new SerializableProxy($SerializableClosureProxy.class, selected.getBelief());
            }
          };
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_trackingRequest, _function);
          Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
          Agent _owner = this.getOwner();
          CounterRole _counterRole = new CounterRole(_owner);
          _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_counterRole);
        }
      }
    }
  }
  
  @SuppressWarnings("discouraged_reference")
  public String print(final EvaluationMessage result) {
    String _xblockexpression = null;
    {
      double _hOTA = result.getHOTA();
      InputOutput.<String>println(("HOTA \t" + Double.valueOf(_hOTA)));
      double _detA = result.getDetA();
      InputOutput.<String>println(("DetA \t" + Double.valueOf(_detA)));
      double _assA = result.getAssA();
      InputOutput.<String>println(("AssA \t" + Double.valueOf(_assA)));
      double _detRe = result.getDetRe();
      InputOutput.<String>println(("DetRe \t" + Double.valueOf(_detRe)));
      double _detPr = result.getDetPr();
      InputOutput.<String>println(("DetPr \t" + Double.valueOf(_detPr)));
      double _assRe = result.getAssRe();
      InputOutput.<String>println(("AssRe \t" + Double.valueOf(_assRe)));
      double _assPr = result.getAssPr();
      InputOutput.<String>println(("AssPr \t" + Double.valueOf(_assPr)));
      double _locA = result.getLocA();
      _xblockexpression = InputOutput.<String>println(("LocA \t" + Double.valueOf(_locA)));
    }
    return _xblockexpression;
  }
  
  @Extension
  @ImportedCapacityFeature(AlgorithmSelection.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION;
  
  @SyntheticMember
  @Pure
  private AlgorithmSelection $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION = $getSkill(AlgorithmSelection.class);
    }
    return $castSkill(AlgorithmSelection.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_ALGORITHMSELECTION);
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
  @ImportedCapacityFeature(Evaluation.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION;
  
  @SyntheticMember
  @Pure
  private Evaluation $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION = $getSkill(Evaluation.class);
    }
    return $castSkill(Evaluation.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_EVALUATION_EVALUATION);
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
  private void $guardEvaluator$LastFrame(final LastFrame occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LastFrame$2(occurrence));
  }
  
  /**
   * @FIXME Not general
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$EvaluationResult(final EvaluationResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$EvaluationResult$3(occurrence));
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AlgorithmSelectorRole other = (AlgorithmSelectorRole) obj;
    if (!java.util.Objects.equals(this.evaluationPartnerName, other.evaluationPartnerName))
      return false;
    if (other.Comp_Duration != this.Comp_Duration)
      return false;
    if (other.numberTests != this.numberTests)
      return false;
    if (!java.util.Objects.equals(this.gts, other.gts))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.evaluationPartnerName);
    result = prime * result + Long.hashCode(this.Comp_Duration);
    result = prime * result + Integer.hashCode(this.numberTests);
    result = prime * result + java.util.Objects.hashCode(this.gts);
    return result;
  }
  
  @SyntheticMember
  public AlgorithmSelectorRole(final Agent agent) {
    super(agent);
  }
}
