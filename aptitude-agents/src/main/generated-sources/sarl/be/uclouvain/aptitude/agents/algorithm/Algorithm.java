package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.AlgorithmNeeded;
import be.uclouvain.aptitude.agents.Paraddis;
import be.uclouvain.aptitude.agents.algorithm.CounterRole;
import be.uclouvain.aptitude.agents.algorithm.DetectorRole;
import be.uclouvain.aptitude.agents.algorithm.TrackerRole;
import be.uclouvain.organisation.told.entity.EntityRole;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import java.util.Collection;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class Algorithm extends Paraddis {
  private String name;
  
  private int level;
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.level = ((((Integer) _get)) == null ? 0 : (((Integer) _get)).intValue());
    Object _get_1 = occurrence.parameters[1];
    this.name = (_get_1 == null ? null : _get_1.toString());
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = this.getID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(((((("ALGORITHM-" + this.name) + "-") + Integer.valueOf(this.level)) + "-") + _iD));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    EntityRole _entityRole = new EntityRole(this);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_entityRole);
    if ((this.level > 0)) {
      Object _get_2 = occurrence.parameters[2];
      boolean _matched = false;
      if (Objects.equal(_get_2, "Counter")) {
        _matched=true;
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        CounterRole _counterRole = new CounterRole(this);
        _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_1.registerBehavior(_counterRole, occurrence.parameters[3], occurrence.parameters[4]);
      }
      if (!_matched) {
        if (Objects.equal(_get_2, "Tracker")) {
          _matched=true;
          Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
          TrackerRole _trackerRole = new TrackerRole(this);
          _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_2.registerBehavior(_trackerRole, occurrence.parameters[3], occurrence.parameters[4]);
        }
      }
      if (!_matched) {
        if (Objects.equal(_get_2, "Detector")) {
          _matched=true;
          Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
          DetectorRole _detectorRole = new DetectorRole(this);
          _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER_3.registerBehavior(_detectorRole, occurrence.parameters[3], occurrence.parameters[4]);
        }
      }
    }
  }
  
  private void $behaviorUnit$AlgorithmNeeded$1(final AlgorithmNeeded occurrence) {
    boolean _equals = Objects.equal(occurrence.name, this.name);
    if (_equals) {
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContext(Algorithm.class, occurrence.contextID, Integer.valueOf(1), occurrence.name, occurrence.task, occurrence.belief, occurrence.dest);
    }
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
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
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
  private void $guardEvaluator$AlgorithmNeeded(final AlgorithmNeeded occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AlgorithmNeeded$1(occurrence));
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
    Algorithm other = (Algorithm) obj;
    if (!java.util.Objects.equals(this.name, other.name))
      return false;
    if (other.level != this.level)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.name);
    result = prime * result + Integer.hashCode(this.level);
    return result;
  }
  
  @SyntheticMember
  public Algorithm(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Algorithm(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Algorithm(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
