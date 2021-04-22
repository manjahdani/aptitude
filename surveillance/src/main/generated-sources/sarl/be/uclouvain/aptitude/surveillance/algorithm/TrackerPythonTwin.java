package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.surveillance.algorithm.CommunicationManager;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerTrackingFound;
import be.uclouvain.aptitude.surveillance.algorithm.PythonAccessObserver;
import be.uclouvain.aptitude.surveillance.algorithm.messages.BBoxes2DTrackMessage;
import be.uclouvain.aptitude.surveillance.algorithm.messages.BaseMessage;
import be.uclouvain.aptitude.surveillance.algorithm.messages.RequestMessage;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Capacity;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO: write a description
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class TrackerPythonTwin extends PythonAccessObserver implements Capacity {
  public void update(final BaseMessage m) {
    if ((m instanceof BBoxes2DTrackMessage)) {
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      BBoxes2DTrackResult _bBoxes2DTrackResult = new BBoxes2DTrackResult(((BBoxes2DTrackMessage)m));
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_bBoxes2DTrackResult);
    } else {
      if ((m instanceof RequestMessage)) {
        this.onRequestMessage(((RequestMessage)m));
      }
    }
  }
  
  private void onRequestMessage(final RequestMessage message) {
    if ((message.isAck() && Objects.equal(message.getRequestID(), this.pendingRequestID))) {
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.cancel(this.requestTask);
      CommunicationManager.getInstance().unsubscribeTopic(this.topicRequestSub);
      this.topicRequestSub = null;
      String _string = this.getOwner().getID().toString();
      String _clientName = message.getClientName();
      final String topicName = ((("tracking_" + _string) + "_") + _clientName);
      this.topicSignalAcquisition = CommunicationManager.getInstance().subscribeTopic(topicName, this);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      String _clientName_1 = message.getClientName();
      PartnerTrackingFound _partnerTrackingFound = new PartnerTrackingFound(_clientName_1);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_partnerTrackingFound);
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
  
  @SyntheticMember
  public TrackerPythonTwin() {
    super();
  }
  
  @SyntheticMember
  public TrackerPythonTwin(final Agent agent) {
    super(agent);
  }
}
