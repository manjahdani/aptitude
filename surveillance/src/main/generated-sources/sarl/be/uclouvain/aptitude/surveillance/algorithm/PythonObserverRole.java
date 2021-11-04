package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.PartnerDetectionFound;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerTrackingFound;
import be.uclouvain.aptitude.surveillance.algorithm.PythonTwinObserverAccessCapacity;
import be.uclouvain.organisation.platform.ObserverRole;
import io.sarl.core.Initialize;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class PythonObserverRole extends ObserverRole {
  protected JSONParser parser = new JSONParser();
  
  protected JSONObject jsonConfig;
  
  protected String partnerName;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      try {
        if (((this.observerADN.getBelief() != null) && (this.platformName != null))) {
          String configPath = this.observerADN.getBelief();
          FileReader _fileReader = new FileReader(configPath);
          Object _parse = this.parser.parse(_fileReader);
          JSONObject jsonConfigtmp = ((JSONObject) _parse);
          HashMap<String, String> video = new HashMap<String, String>();
          video.put("path", "F:/data/".concat(this.platformName).concat("/vdo.avi"));
          HashMap<String, String> pathRoi = new HashMap<String, String>();
          pathRoi.put("path", "F:/data/".concat(this.platformName).concat("/roi.jpg"));
          Object _get = jsonConfigtmp.get("Preproc");
          HashMap<String, HashMap<String, String>> a = ((HashMap<String, HashMap<String, String>>) _get);
          a.put("roi", pathRoi);
          jsonConfigtmp.put("Video", video);
          this.jsonConfig = jsonConfigtmp;
          Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
          Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3.task("waitforPlatform"));
        }
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.every(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("waitforPlatform"), 500, _function);
  }
  
  private void $behaviorUnit$PartnerTrackingFound$1(final PartnerTrackingFound occurrence) {
    synchronized (this) {
      this.partnerName = occurrence.partnerName;
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  private void $behaviorUnit$PartnerDetectionFound$2(final PartnerDetectionFound occurrence) {
    this.partnerName = occurrence.partnerName;
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      PythonTwinObserverAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER.UpdateStreamAccess(1);
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3.task("wait"));
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.in(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("wait"), 2000, _function);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
  }
  
  @Extension
  @ImportedCapacityFeature(PythonTwinObserverAccessCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY;
  
  @SyntheticMember
  @Pure
  private PythonTwinObserverAccessCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY = $getSkill(PythonTwinObserverAccessCapacity.class);
    }
    return $castSkill(PythonTwinObserverAccessCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_PYTHONTWINOBSERVERACCESSCAPACITY);
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
  private void $guardEvaluator$PartnerTrackingFound(final PartnerTrackingFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerTrackingFound$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerDetectionFound(final PartnerDetectionFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerDetectionFound$2(occurrence));
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
    PythonObserverRole other = (PythonObserverRole) obj;
    if (!Objects.equals(this.partnerName, other.partnerName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.partnerName);
    return result;
  }
  
  @SyntheticMember
  public PythonObserverRole(final Agent agent) {
    super(agent);
  }
}
