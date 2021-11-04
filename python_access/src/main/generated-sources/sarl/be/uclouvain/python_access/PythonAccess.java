package be.uclouvain.python_access;

import be.uclouvain.python_access.CommunicationManager;
import be.uclouvain.python_access.PythonAccessCapacity;
import be.uclouvain.python_access.messages.ActionMessage;
import be.uclouvain.python_access.messages.RequestMessage;
import com.hazelcast.util.UuidUtil;
import io.sarl.core.AgentTask;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;

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
public abstract class PythonAccess extends Skill implements PythonAccessCapacity {
  protected String topicRequestSub;
  
  protected String topicSignalAcquisition;
  
  protected String pendingRequestID;
  
  protected AgentTask requestTask;
  
  @SuppressWarnings("potential_field_synchronization_problem")
  public void install() {
    this.topicRequestSub = CommunicationManager.getInstance().subscribeTopic("request", this);
  }
  
  public void prepareUninstallation() {
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  public void uninstall() {
    if ((this.topicRequestSub != null)) {
      CommunicationManager.getInstance().unsubscribeTopic(this.topicRequestSub);
      this.topicRequestSub = null;
    }
    if ((this.topicSignalAcquisition != null)) {
      CommunicationManager.getInstance().unsubscribeTopic(this.topicSignalAcquisition);
      this.topicSignalAcquisition = null;
    }
  }
  
  @SuppressWarnings("potential_field_synchronization_problem")
  public void ActivateAccess(final JSONObject jsonConfig) {
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
  
  /**
   * info("I am sending the action: " + actionID)
   * var actionMessage = new ActionMessage() //@TODO : Strange way of doing it
   * actionMessage.actionID = actionID
   * 
   * CommunicationManager.instance.publishMessage(topicSignalAcquisition, actionMessage)
   */
  @SuppressWarnings("potential_field_synchronization_problem")
  public void UpdateStreamAccess(final int actionID) {
    this.UpdateStreamAccess(actionID, (-1));
  }
  
  public void UpdateStreamAccess(final int actionID, final int newFrameNumber) {
    ActionMessage actionMessage = new ActionMessage();
    actionMessage.setActionID(actionID);
    if ((newFrameNumber != (-1))) {
      actionMessage.setNewFrameNumber(newFrameNumber);
    }
    CommunicationManager.getInstance().publishMessage(this.topicSignalAcquisition, actionMessage);
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
    PythonAccess other = (PythonAccess) obj;
    if (!Objects.equals(this.topicRequestSub, other.topicRequestSub))
      return false;
    if (!Objects.equals(this.topicSignalAcquisition, other.topicSignalAcquisition))
      return false;
    if (!Objects.equals(this.pendingRequestID, other.pendingRequestID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.topicRequestSub);
    result = prime * result + Objects.hashCode(this.topicSignalAcquisition);
    result = prime * result + Objects.hashCode(this.pendingRequestID);
    return result;
  }
  
  @SyntheticMember
  public PythonAccess() {
    super();
  }
  
  @SyntheticMember
  public PythonAccess(final Agent agent) {
    super(agent);
  }
}
