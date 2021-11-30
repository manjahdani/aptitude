package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackerPythonTwinCapacity;
import be.uclouvain.python_access.BBoxes2DResult;
import be.uclouvain.python_access.PythonAccessorRole;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import java.util.Collection;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class SingleTrackerRole extends PythonAccessorRole {
  private void $behaviorUnit$BBoxes2DResult$0(final BBoxes2DResult occurrence) {
    TrackerPythonTwinCapacity _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER.Signal2Perception(occurrence.bboxes2D);
  }
  
  @Extension
  @ImportedCapacityFeature(TrackerPythonTwinCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY;
  
  @SyntheticMember
  @Pure
  private TrackerPythonTwinCapacity $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY = $getSkill(TrackerPythonTwinCapacity.class);
    }
    return $castSkill(TrackerPythonTwinCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_SURVEILLANCE_ALGORITHM_TRACKER_TRACKERPYTHONTWINCAPACITY);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$0(occurrence));
  }
  
  @SyntheticMember
  public SingleTrackerRole(final Agent agent) {
    super(agent);
  }
}
