package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.CommunicationManager;
import be.uclouvain.aptitude.surveillance.algorithm.PythonAccess;
import be.uclouvain.aptitude.surveillance.algorithm.PythonTwinObserverAccess;
import be.uclouvain.aptitude.surveillance.algorithm.messages.BaseMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;

@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public abstract class PythonAccessObserver extends PythonAccess implements PythonTwinObserverAccess {
  @SuppressWarnings("potential_field_synchronization_problem")
  public void Signal2Perception(final Object detectionMessage) {
    CommunicationManager.getInstance().publishMessage(this.topicSignalAcquisition, ((BaseMessage) detectionMessage));
  }
  
  @SyntheticMember
  public PythonAccessObserver() {
    super();
  }
  
  @SyntheticMember
  public PythonAccessObserver(final Agent agent) {
    super(agent);
  }
}
