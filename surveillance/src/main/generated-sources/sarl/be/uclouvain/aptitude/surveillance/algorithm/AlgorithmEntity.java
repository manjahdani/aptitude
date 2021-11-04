package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.LastFrame;
import be.uclouvain.aptitude.surveillance.algorithm.TrackingPerception;
import be.uclouvain.organisation.TOLDOrganisationInfo;
import be.uclouvain.organisation.told.AddEntry;
import be.uclouvain.organisation.told.DataEntry;
import be.uclouvain.organisation.told.QueryAnswer;
import be.uclouvain.organisation.told.ReadEntry;
import be.uclouvain.organisation.told.entity.EntityRole;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class AlgorithmEntity extends EntityRole {
  private File predfile = new File(("F:\\Database\\".concat(this.getOwner().getID().toString()) + ".txt"));
  
  private void $behaviorUnit$TOLDOrganisationInfo$0(final TOLDOrganisationInfo occurrence) {
    try {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Creating new file to register prediction");
      UUID _iD = this.getOwner().getID();
      FileWriter _fileWriter = new FileWriter(this.predfile);
      AddEntry _addEntry = new AddEntry(_iD, _fileWriter);
      occurrence.spaceID.emit(this.getOwner().getID(), _addEntry, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$TrackingPerception$1(final TrackingPerception occurrence) {
    UUID _iD = this.getOwner().getID();
    DataEntry _dataEntry = new DataEntry(_iD, occurrence.perceptions);
    this.TOLDSpace.emit(this.getOwner().getID(), _dataEntry, null);
  }
  
  private void $behaviorUnit$LastFrame$2(final LastFrame occurrence) {
    UUID _iD = this.getOwner().getID();
    ReadEntry _readEntry = new ReadEntry(_iD);
    this.TOLDSpace.emit(this.getOwner().getID(), _readEntry, null);
  }
  
  private void $behaviorUnit$QueryAnswer$3(final QueryAnswer occurrence) {
    try {
      ((FileWriter) occurrence.answerObject).close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$QueryAnswer(final QueryAnswer occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$QueryAnswer$3(occurrence));
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
  private void $guardEvaluator$TOLDOrganisationInfo(final TOLDOrganisationInfo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TOLDOrganisationInfo$0(occurrence));
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
  public AlgorithmEntity(final Agent agent) {
    super(agent);
  }
}
