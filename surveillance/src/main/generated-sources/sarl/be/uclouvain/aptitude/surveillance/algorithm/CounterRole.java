/**
 * @Name       : PlatformRole
 * @Project    : APTITUDE
 * @Author     : Dani Manjah
 * @Version    : V.0.1
 * @Date       : 22/03/2021
 */
package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.surveillance.algorithm.CountingSkill;
import be.uclouvain.aptitude.surveillance.algorithm.Evaluation;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationImpl;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationResult;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerEvaluationFound;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBOX;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectStreamException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The CounterRole is a behaviour that an agent Algorithm endorses upon a mission to count vehicles
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
public class CounterRole extends ObserverRole {
  private long start;
  
  private final ArrayList<String> availableObservers = CollectionLiterals.<String>newArrayList("SORT", "DeepSORT");
  
  private final TreeMap<Integer, Integer> intensityMap = new TreeMap<Integer, Integer>();
  
  private final TreeMap<Integer, BBoxes2D> ObjectPresentInframe = new TreeMap<Integer, BBoxes2D>();
  
  private final ArrayList<BBoxes2D> ObjectToBeAnalyzed = new ArrayList<BBoxes2D>();
  
  private FileWriter gt = new Function0<FileWriter>() {
    @Override
    public FileWriter apply() {
      try {
        FileWriter _fileWriter = new FileWriter("F:\\TinySort.txt");
        return _fileWriter;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    }
  }.apply();
  
  private String EvaluationPartnerName;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      this.intensityMap.put(Integer.valueOf(0), Integer.valueOf(1));
      this.intensityMap.put(Integer.valueOf(1), Integer.valueOf(0));
      this.intensityMap.put(Integer.valueOf(2), Integer.valueOf(1));
      this.intensityMap.put(Integer.valueOf(3), Integer.valueOf(0));
      CountingSkill _countingSkill = new CountingSkill();
      this.<CountingSkill>setSkill(_countingSkill, CounterObserverCapacity.class);
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
  
  private void $behaviorUnit$PartnerEvaluationFound$1(final PartnerEvaluationFound occurrence) {
    this.EvaluationPartnerName = occurrence.partnerName;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("I found my partner" + this.EvaluationPartnerName));
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$MissionSensitivity$2(final MissionSensitivity occurrence) {
    this.sensitivity.set(occurrence.s);
    final String ObserverName = this.availableObservers.get(((this.intensityMap.get(Integer.valueOf(this.sensitivity.get()))) == null ? 0 : (this.intensityMap.get(Integer.valueOf(this.sensitivity.get()))).intValue()));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + ObserverName));
    this.start = System.currentTimeMillis();
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
        UUID _iD = CounterRole.this.PlatformContext.getID();
        return Objects.equal(_uUID, _iD);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, CounterRole.this.PlatformContext.getID());
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.emit(this.PlatformSpace, _addAlgorithm, _function);
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$BBoxes2DTrackResult$3(final BBoxes2DTrackResult occurrence) {
    try {
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
          String _string = Integer.valueOf((frameNumber + 1)).toString();
          String _string_1 = Integer.valueOf(globalID).toString();
          String _string_2 = Integer.valueOf(Double.valueOf(X).intValue()).toString();
          String _string_3 = Integer.valueOf(Double.valueOf(Y).intValue()).toString();
          String _string_4 = Integer.valueOf(Double.valueOf(W).intValue()).toString();
          String _string_5 = Integer.valueOf(Double.valueOf(H).intValue()).toString();
          this.gt.write((((((((((((((((((((_string + ",") + _string_1) + ",") + _string_2) + ",") + _string_3) + ",") + _string_4) + ",") + _string_5) + ",") + 
            "-1") + 
            ",") + "-1") + ",") + "-1") + ",") + "-1") + "\n"));
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
      CounterObserverCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER();
      ArrayList<BBoxes2D> _arrayList = new ArrayList<BBoxes2D>(this.ObjectToBeAnalyzed);
      _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER.Signal2Perception(_arrayList);
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
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info("I will transfer my perception to the Analyst");
        CounterObserverCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER_1 = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER();
        _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER_1.DisplayPerception();
        this.gt.close();
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4.info("Sending to evaluation");
        String predFile = "F:/TinySort.txt";
        String gtFile = "F:/gt.txt";
        byte[] _readAllBytes = Files.readAllBytes(Paths.get(predFile));
        String predictions = new String(_readAllBytes);
        byte[] _readAllBytes_1 = Files.readAllBytes(Paths.get(gtFile));
        String gts = new String(_readAllBytes_1);
        Evaluation _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER();
        _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_EVALUATION$CALLER.sendEvaluationRequest(UUID.randomUUID().toString(), predictions, gts);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$EvaluationResult$4(final EvaluationResult occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Message Evaluation Received");
    String _requestID = occurrence.result.getRequestID();
    InputOutput.<String>println(("reqID \t" + _requestID));
    double _hOTA = occurrence.result.getHOTA();
    InputOutput.<String>println(("HOTA \t" + Double.valueOf(_hOTA)));
    double _detA = occurrence.result.getDetA();
    InputOutput.<String>println(("DetA \t" + Double.valueOf(_detA)));
    double _assA = occurrence.result.getAssA();
    InputOutput.<String>println(("AssA \t" + Double.valueOf(_assA)));
    double _detRe = occurrence.result.getDetRe();
    InputOutput.<String>println(("DetRe \t" + Double.valueOf(_detRe)));
    double _detPr = occurrence.result.getDetPr();
    InputOutput.<String>println(("DetPr \t" + Double.valueOf(_detPr)));
    double _assRe = occurrence.result.getAssRe();
    InputOutput.<String>println(("AssRe \t" + Double.valueOf(_assRe)));
    double _assPr = occurrence.result.getAssPr();
    InputOutput.<String>println(("AssPr \t" + Double.valueOf(_assPr)));
    double _locA = occurrence.result.getLocA();
    InputOutput.<String>println(("LocA \t" + Double.valueOf(_locA)));
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CounterRole other = (CounterRole) obj;
    if (other.start != this.start)
      return false;
    if (!java.util.Objects.equals(this.EvaluationPartnerName, other.EvaluationPartnerName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.start);
    result = prime * result + java.util.Objects.hashCode(this.EvaluationPartnerName);
    return result;
  }
  
  @SyntheticMember
  public CounterRole(final Agent agent) {
    super(agent);
  }
}
