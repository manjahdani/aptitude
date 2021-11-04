package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.surveillance.algorithm.ObserverPythonAccess;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerDetectionFound;
import be.uclouvain.python_access.CommunicationManager;
import be.uclouvain.python_access.messages.ActionMessage;
import be.uclouvain.python_access.messages.BBoxes2DMessage;
import be.uclouvain.python_access.messages.BaseMessage;
import be.uclouvain.python_access.messages.RequestMessage;
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
public class DetectorPythonTwin extends ObserverPythonAccess implements Capacity {
  public void update(final BaseMessage m) {
    if ((m instanceof BBoxes2DMessage)) {
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      BBoxes2DResult _bBoxes2DResult = new BBoxes2DResult(((BBoxes2DMessage)m));
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_bBoxes2DResult);
    } else {
      if ((m instanceof RequestMessage)) {
        this.onRequestMessage(((RequestMessage)m));
      } else {
        if ((m instanceof ActionMessage)) {
          boolean _isAck = ((ActionMessage)m).isAck();
          if (_isAck) {
          }
        }
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
      final String topicName = ((("detection_" + _string) + "_") + _clientName);
      this.topicSignalAcquisition = CommunicationManager.getInstance().subscribeTopic(topicName, this);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      String _clientName_1 = message.getClientName();
      PartnerDetectionFound _partnerDetectionFound = new PartnerDetectionFound(_clientName_1);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_partnerDetectionFound);
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
  public DetectorPythonTwin() {
    super();
  }
  
  @SyntheticMember
  public DetectorPythonTwin(final Agent agent) {
    super(agent);
  }
}
