/**
 * @Name       : PlatformRole
 * @Project    : APTITUDE
 * @Author     : Dani Manjah
 * @Version    : V.0.1
 * @Date       : 22/03/2021
 */
package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.CountingSkill;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationResult;
import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingPerception;
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
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;

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
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    this.intensityMap.put(Integer.valueOf(0), Integer.valueOf(1));
    this.intensityMap.put(Integer.valueOf(1), Integer.valueOf(0));
    this.intensityMap.put(Integer.valueOf(2), Integer.valueOf(1));
    this.intensityMap.put(Integer.valueOf(3), Integer.valueOf(0));
    CountingSkill _countingSkill = new CountingSkill();
    this.<CountingSkill>setSkill(_countingSkill, CounterObserverCapacity.class);
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$MissionSensitivity$1(final MissionSensitivity occurrence) {
    this.sensitivity = CollectionLiterals.<Integer>newLinkedList(((Integer[])Conversions.unwrapArray(occurrence.s, Integer.class)));
    final String ObserverName = this.availableObservers.get(((this.intensityMap.get(this.sensitivity.getFirst())) == null ? 0 : (this.intensityMap.get(this.sensitivity.getFirst())).intValue()));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Come here : " + ObserverName));
    this.start = System.currentTimeMillis();
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
  private void $behaviorUnit$TrackingPerception$2(final TrackingPerception occurrence) {
    this.ObjectToBeAnalyzed.clear();
    for (final BBoxes2D p : occurrence.perceptions) {
      {
        boolean _containsKey = this.ObjectPresentInframe.containsKey(Integer.valueOf(p.getGlobalID()));
        if (_containsKey) {
          this.ObjectPresentInframe.get(Integer.valueOf(p.getGlobalID())).update(p.getBBOX().getX(), p.getBBOX().getY(), p.getBBOX().getW(), p.getBBOX().getH(), p.getClasseID(), p.getFrame(), p.getConf());
        } else {
          this.ObjectPresentInframe.put(Integer.valueOf(p.getGlobalID()), p);
        }
        this.ObjectToBeAnalyzed.add(this.ObjectPresentInframe.get(Integer.valueOf(p.getGlobalID())));
      }
    }
    CounterObserverCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER();
    ArrayList<BBoxes2D> _arrayList = new ArrayList<BBoxes2D>(this.ObjectToBeAnalyzed);
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER.Signal2Perception(_arrayList);
  }
  
  private void $behaviorUnit$LastFrame$3(final LastFrame occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("That was it!");
    long _currentTimeMillis = System.currentTimeMillis();
    final long totalTime = ((_currentTimeMillis - this.start) / 1000);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _string = Long.valueOf(totalTime).toString();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((("It took " + _string) + " seconds"));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _string_1 = Long.valueOf((occurrence.frameNumber / totalTime)).toString();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Average FPS : " + _string_1));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info("I will transfer my perception to the Analyst");
    CounterObserverCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER.DisplayPerception();
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MissionSensitivity$1(occurrence));
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
  private void $guardEvaluator$TrackingPerception(final TrackingPerception occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TrackingPerception$2(occurrence));
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
    CounterRole other = (CounterRole) obj;
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
    result = prime * result + Long.hashCode(this.start);
    return result;
  }
  
  @SyntheticMember
  public CounterRole(final Agent agent) {
    super(agent);
  }
}
