package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.agents.algorithm.CommunicationManager;
import be.uclouvain.aptitude.agents.algorithm.Detection;
import be.uclouvain.aptitude.agents.algorithm.PartnerDetectionFound;
import be.uclouvain.aptitude.agents.algorithm.messages.ActionMessage;
import be.uclouvain.aptitude.agents.algorithm.messages.BBoxes2DMessage;
import be.uclouvain.aptitude.agents.algorithm.messages.BaseMessage;
import be.uclouvain.aptitude.agents.algorithm.messages.RequestMessage;
import com.google.common.base.Objects;
import com.hazelcast.util.UuidUtil;
import io.sarl.core.AgentTask;
import io.sarl.core.Behaviors;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;

/**
 * @author samelson
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class DetectionImpl extends Skill implements Detection {
  private String topicRequestSub;
  
  private String topicDetectionSub;
  
  private String pendingRequestID;
  
  private AgentTask requestTask;
  
  public void install() {
    this.topicRequestSub = CommunicationManager.getInstance().subscribeTopic("request", this);
  }
  
  public void uninstall() {
    if ((this.topicRequestSub != null)) {
      CommunicationManager.getInstance().unsubscribeTopic(this.topicRequestSub);
      this.topicRequestSub = null;
    }
    if ((this.topicDetectionSub != null)) {
      CommunicationManager.getInstance().unsubscribeTopic(this.topicDetectionSub);
      this.topicDetectionSub = null;
    }
  }
  
  public void requestDetector(final JSONObject jsonConfig) {
    this.pendingRequestID = UuidUtil.newSecureUuidString();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      RequestMessage requestMessage = new RequestMessage();
      requestMessage.setRequestID(this.pendingRequestID);
      requestMessage.setAgentID(this.getOwner().getID().toString());
      requestMessage.setJsonConfig(jsonConfig.toString());
      CommunicationManager.getInstance().publishMessage(this.topicRequestSub, requestMessage);
    };
    this.requestTask = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.every(1000, _function);
  }
  
  public void sendAction(final int actionID) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Send action: " + Integer.valueOf(actionID)));
    ActionMessage actionMessage = new ActionMessage();
    actionMessage.setActionID(actionID);
    CommunicationManager.getInstance().publishMessage(this.topicDetectionSub, actionMessage);
  }
  
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
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
            String _string = Integer.valueOf(((ActionMessage)m).getActionID()).toString();
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Ack action " + _string));
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
      this.topicDetectionSub = CommunicationManager.getInstance().subscribeTopic(topicName, this);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      String _clientName_1 = message.getClientName();
      PartnerDetectionFound _partnerDetectionFound = new PartnerDetectionFound(_clientName_1);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_partnerDetectionFound);
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
    DetectionImpl other = (DetectionImpl) obj;
    if (!java.util.Objects.equals(this.topicRequestSub, other.topicRequestSub))
      return false;
    if (!java.util.Objects.equals(this.topicDetectionSub, other.topicDetectionSub))
      return false;
    if (!java.util.Objects.equals(this.pendingRequestID, other.pendingRequestID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.topicRequestSub);
    result = prime * result + java.util.Objects.hashCode(this.topicDetectionSub);
    result = prime * result + java.util.Objects.hashCode(this.pendingRequestID);
    return result;
  }
  
  @SyntheticMember
  public DetectionImpl() {
    super();
  }
  
  @SyntheticMember
  public DetectionImpl(final Agent agent) {
    super(agent);
  }
}
