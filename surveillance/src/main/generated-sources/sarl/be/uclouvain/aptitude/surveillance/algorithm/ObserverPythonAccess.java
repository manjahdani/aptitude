package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.PythonTwinObserverAccessCapacity;
import be.uclouvain.python_access.CommunicationManager;
import be.uclouvain.python_access.PythonAccess;
import be.uclouvain.python_access.messages.BaseMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public abstract class ObserverPythonAccess extends PythonAccess implements PythonTwinObserverAccessCapacity {
  @SuppressWarnings("potential_field_synchronization_problem")
  public void Signal2Perception(final Object detectionMessage) {
    CommunicationManager.getInstance().publishMessage(this.topicSignalAcquisition, ((BaseMessage) detectionMessage));
  }
  
  @SyntheticMember
  public ObserverPythonAccess() {
    super();
  }
  
  @SyntheticMember
  public ObserverPythonAccess(final Agent agent) {
    super(agent);
  }
}
