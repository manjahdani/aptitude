/**
 * @Name       : PlatformRole
 * @Project    : APTITUDE
 * @Author     : Dani Manjah
 * @Version    : V.0.1
 * @Date       : 22/03/2021
 */
package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResultToCounter;
import be.uclouvain.aptitude.surveillance.algorithm.CountingSkill;
import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingPerception;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxes2D;
import be.uclouvain.aptitude.surveillance.ui.DisplayCapacity;
import be.uclouvain.aptitude.surveillance.ui.DisplaySkill;
import be.uclouvain.organisation.platform.CounterObserverCapacity;
import be.uclouvain.organisation.platform.util.countingLine;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
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
public class CounterRole extends Behavior {
  private final TreeMap<Integer, Integer> intensityMap = new TreeMap<Integer, Integer>();
  
  private final TreeMap<Integer, BBoxes2D> ObjectPresentInframe = new TreeMap<Integer, BBoxes2D>();
  
  private final ArrayList<BBoxes2D> ObjectToBeAnalyzed = new ArrayList<BBoxes2D>();
  
  private List<Integer> lineCoords = Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1235), Integer.valueOf(700), Integer.valueOf(309), Integer.valueOf(664), Integer.valueOf(1477), Integer.valueOf(324), Integer.valueOf(1676), Integer.valueOf(310)));
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The counting starts from now !");
    this.intensityMap.put(Integer.valueOf(0), Integer.valueOf(1));
    this.intensityMap.put(Integer.valueOf(1), Integer.valueOf(0));
    this.intensityMap.put(Integer.valueOf(2), Integer.valueOf(1));
    this.intensityMap.put(Integer.valueOf(3), Integer.valueOf(0));
    DisplaySkill _displaySkill = new DisplaySkill();
    this.<DisplaySkill>setSkill(_displaySkill);
    CountingSkill _countingSkill = new CountingSkill();
    this.<CountingSkill>setSkill(_countingSkill, CounterObserverCapacity.class);
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$TrackingPerception$1(final TrackingPerception occurrence) {
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
  
  private void $behaviorUnit$LastFrame$2(final LastFrame occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("That was it for Couting!");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("I will transfer my perception to the Analyst");
    CounterObserverCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER.DisplayPerception(1);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResultToCounter$3(final BBoxes2DTrackResultToCounter occurrence) {
    CounterObserverCapacity _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER();
    TreeMap<String, countingLine> a = _$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_COUNTEROBSERVERCAPACITY$CALLER.DisplayPerception(0);
    ArrayList<Integer> counts = new ArrayList<Integer>();
    Set<String> _keySet = a.keySet();
    for (final String l : _keySet) {
      Set<Integer> _keySet_1 = a.get(l).counts.keySet();
      for (final Integer id : _keySet_1) {
        counts.add(Integer.valueOf(a.get(l).counts.get(id).get()));
      }
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(counts);
    DisplayCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY$CALLER();
    final ArrayList<Integer> _converted_counts = (ArrayList<Integer>)counts;
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY$CALLER.sendDisplayMessage(occurrence.bboxes2DTrack, this.getOwner().getID().toString(), "F:/data/S02C006/vdo.avi", null, ((int[])Conversions.unwrapArray(this.lineCoords, int.class)), ((int[])Conversions.unwrapArray(_converted_counts, int.class)), occurrence.ObserverName, occurrence.Sensitivity);
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
  @ImportedCapacityFeature(DisplayCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY;
  
  @SyntheticMember
  @Pure
  private DisplayCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY = $getSkill(DisplayCapacity.class);
    }
    return $castSkill(DisplayCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_UI_DISPLAYCAPACITY);
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
  private void $guardEvaluator$BBoxes2DTrackResultToCounter(final BBoxes2DTrackResultToCounter occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResultToCounter$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TrackingPerception(final TrackingPerception occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TrackingPerception$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LastFrame(final LastFrame occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LastFrame$2(occurrence));
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  @SyntheticMember
  public CounterRole(final Agent agent) {
    super(agent);
  }
}
