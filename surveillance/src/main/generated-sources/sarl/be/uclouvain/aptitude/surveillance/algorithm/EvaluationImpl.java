package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.CommunicationManager;
import be.uclouvain.aptitude.surveillance.algorithm.Evaluation;
import be.uclouvain.aptitude.surveillance.algorithm.EvaluationResult;
import be.uclouvain.aptitude.surveillance.algorithm.PartnerEvaluationFound;
import be.uclouvain.aptitude.surveillance.algorithm.PythonAccess;
import be.uclouvain.aptitude.surveillance.algorithm.messages.BaseMessage;
import be.uclouvain.aptitude.surveillance.algorithm.messages.EvaluationMessage;
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
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class EvaluationImpl extends PythonAccess implements Evaluation {
  public void update(final BaseMessage m) {
    if ((m instanceof EvaluationMessage)) {
      boolean _isAck = ((EvaluationMessage)m).isAck();
      if (_isAck) {
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        EvaluationResult _evaluationResult = new EvaluationResult(((EvaluationMessage)m));
        _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_evaluationResult);
      }
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
      final String topicName = ((("evaluation_" + _string) + "_") + _clientName);
      this.topicSignalAcquisition = CommunicationManager.getInstance().subscribeTopic(topicName, this);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      String _clientName_1 = message.getClientName();
      PartnerEvaluationFound _partnerEvaluationFound = new PartnerEvaluationFound(_clientName_1);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_partnerEvaluationFound);
    }
  }
  
  public void sendEvaluationRequest(final String requestID, final String predictions, final String gts) {
    EvaluationMessage evalMessage = new EvaluationMessage();
    evalMessage.setRequestID(requestID);
    evalMessage.setPredictions(predictions);
    evalMessage.setGts(gts);
    CommunicationManager.getInstance().publishMessage(this.topicSignalAcquisition, evalMessage);
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
  public EvaluationImpl() {
    super();
  }
  
  @SyntheticMember
  public EvaluationImpl(final Agent agent) {
    super(agent);
  }
}
