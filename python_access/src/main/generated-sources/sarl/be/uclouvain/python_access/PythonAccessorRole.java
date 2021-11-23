package be.uclouvain.python_access;

import be.uclouvain.python_access.PartnerDetectionFound;
import be.uclouvain.python_access.PartnerTrackingFound;
import be.uclouvain.python_access.PythonTwinAccessCapacity;
import io.sarl.core.Initialize;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
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
public class PythonAccessorRole extends Behavior {
  protected JSONParser parser = new JSONParser();
  
  protected JSONObject jsonConfig;
  
  protected String partnerName;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      class $AssertEvaluator$ {
        final boolean $$result;
        $AssertEvaluator$() {
          Object _get = occurrence.parameters[0];
          this.$$result = (_get != null);
        }
      }
      assert new $AssertEvaluator$().$$result : "Belief is null";
      class $AssertEvaluator$_1 {
        final boolean $$result;
        $AssertEvaluator$_1() {
          Object _get = occurrence.parameters[1];
          this.$$result = (_get != null);
        }
      }
      assert new $AssertEvaluator$_1().$$result : "PlatformName is null";
      Object _get = occurrence.parameters[0];
      String belief = (_get == null ? null : _get.toString());
      Object _get_1 = occurrence.parameters[1];
      String platformName = (_get_1 == null ? null : _get_1.toString());
      String configPath = belief;
      FileReader _fileReader = new FileReader(configPath);
      Object _parse = this.parser.parse(_fileReader);
      JSONObject jsonConfigtmp = ((JSONObject) _parse);
      HashMap<String, String> video = new HashMap<String, String>();
      video.put("path", "F:/data/".concat(platformName).concat("/vdo.avi"));
      HashMap<String, String> pathRoi = new HashMap<String, String>();
      pathRoi.put("path", "F:/data/".concat(platformName).concat("/roi.jpg"));
      Object _get_2 = jsonConfigtmp.get("Preproc");
      HashMap<String, HashMap<String, String>> a = ((HashMap<String, HashMap<String, String>>) _get_2);
      a.put("roi", pathRoi);
      jsonConfigtmp.put("Video", video);
      this.jsonConfig = jsonConfigtmp;
      PythonTwinAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER.activateAccess(this.jsonConfig);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
      PythonTwinAccessCapacity _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER.updateStreamAccess(1);
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
  @ImportedCapacityFeature(PythonTwinAccessCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY;
  
  @SyntheticMember
  @Pure
  private PythonTwinAccessCapacity $CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY = $getSkill(PythonTwinAccessCapacity.class);
    }
    return $castSkill(PythonTwinAccessCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_PYTHON_ACCESS_PYTHONTWINACCESSCAPACITY);
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
    PythonAccessorRole other = (PythonAccessorRole) obj;
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
  public PythonAccessorRole(final Agent agent) {
    super(agent);
  }
}
