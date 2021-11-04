package be.uclouvain.aptitude.surveillance.evaluation;

import be.uclouvain.aptitude.surveillance.algorithm.util.EvaluationResults;
import be.uclouvain.aptitude.surveillance.evaluation.AlgorithmSelection;
import be.uclouvain.python_access.messages.EvaluationMessage;
import com.google.common.base.Objects;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import io.sarl.lang.scoping.extensions.cast.PrimitiveCastExtensions;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class AlgorithmSelectionSkill extends Skill implements AlgorithmSelection {
  private final TreeMap<UUID, EvaluationResults> compResults = new TreeMap<UUID, EvaluationResults>();
  
  private String[] parameters;
  
  public AlgorithmSelectionSkill(final String... p) {
    this.parameters = p;
  }
  
  public EvaluationResults selectAlgorithm() {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Competition is over. Lets choose the Champion !");
    TreeMap<Double, String> rankedResult = new TreeMap<Double, String>();
    Collection<EvaluationResults> _values = this.compResults.values();
    for (final EvaluationResults l : _values) {
      {
        double _trackingTime = l.getTrackingTime();
        double _detectionTime = l.getDetectionTime();
        int _frame = l.getFrame();
        double sframe = ((_trackingTime + _detectionTime) / _frame);
        double effeciency = ((0.51 - (2 * sframe)) / 0.49);
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        double _hOTA = l.getHOTA();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("HOTA :" + Double.valueOf(_hOTA)));
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Effeciency :" + Double.valueOf(effeciency)));
        rankedResult.put(Double.valueOf(this.APTITUDE(l.getHOTA(), effeciency, new AtomicInteger(PrimitiveCastExtensions.intValue(this.parameters[0])).get())), l.getBelief());
      }
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _plus = (rankedResult + "\n Congratulations to ");
    Map.Entry<Double, String> _lastEntry = rankedResult.lastEntry();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((_plus + _lastEntry));
    final Function1<EvaluationResults, Boolean> _function = (EvaluationResults it) -> {
      String _belief = it.getBelief();
      String _value = rankedResult.lastEntry().getValue();
      return Boolean.valueOf(Objects.equal(_belief, _value));
    };
    EvaluationResults selectedAlgorithm = ((EvaluationResults[])Conversions.unwrapArray(IterableExtensions.<EvaluationResults>filter(this.compResults.values(), _function), EvaluationResults.class))[0];
    return selectedAlgorithm;
  }
  
  @Pure
  public double APTITUDE(final double hota, final double effeciency, final int beta) {
    return (hota + (beta * effeciency));
  }
  
  public void addResult(final UUID testID, final EvaluationResults e) {
    this.compResults.put(testID, e);
  }
  
  public boolean addResult(final UUID testID, final EvaluationMessage e) {
    double _hOTA = this.compResults.get(testID).getHOTA();
    if ((_hOTA == 0)) {
      this.compResults.get(testID).setValues(e);
      return true;
    }
    return false;
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
}
