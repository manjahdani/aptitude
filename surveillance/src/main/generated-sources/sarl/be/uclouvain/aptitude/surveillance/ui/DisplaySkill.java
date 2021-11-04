package be.uclouvain.aptitude.surveillance.ui;

import be.uclouvain.aptitude.surveillance.ui.DisplayCapacity;
import be.uclouvain.python_access.CommunicationManager;
import be.uclouvain.python_access.PythonAccess;
import be.uclouvain.python_access.messages.BBoxes2DTrackMessage;
import be.uclouvain.python_access.messages.BaseMessage;
import be.uclouvain.python_access.messages.DisplayMessage;
import io.sarl.core.AgentTask;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class DisplaySkill extends PythonAccess implements DisplayCapacity {
  private String topicDisplaySub;
  
  private String pendingRequestID;
  
  private AgentTask requestTask;
  
  @Override
  public void install() {
    this.topicDisplaySub = CommunicationManager.getInstance().subscribeTopic("display", this);
  }
  
  @Override
  public void uninstall() {
    if ((this.topicDisplaySub != null)) {
      CommunicationManager.getInstance().unsubscribeTopic(this.topicDisplaySub);
      this.topicDisplaySub = null;
    }
  }
  
  public void sendDisplayMessage(final BBoxes2DTrackMessage bboxes2dTrack, final String streamID, final String streamPath, final String roi, final int[] countingLines, final int[] counts, final String name, final String observer, final int sensitivity) {
    DisplayMessage displayMessage = new DisplayMessage(bboxes2dTrack);
    displayMessage.setStreamID(streamID);
    displayMessage.setStreamPath(streamPath);
    displayMessage.setCountingLines(countingLines);
    displayMessage.setCounts(counts);
    displayMessage.setName(name);
    displayMessage.setObserver(observer);
    displayMessage.setSensitivity(sensitivity);
    if ((roi != null)) {
      displayMessage.setRoiPath(roi);
    }
    CommunicationManager.getInstance().publishMessage(this.topicDisplaySub, displayMessage);
  }
  
  public void update(final BaseMessage m) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
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
    DisplaySkill other = (DisplaySkill) obj;
    if (!Objects.equals(this.topicDisplaySub, other.topicDisplaySub))
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
    result = prime * result + Objects.hashCode(this.topicDisplaySub);
    result = prime * result + Objects.hashCode(this.pendingRequestID);
    return result;
  }
  
  @SyntheticMember
  public DisplaySkill() {
    super();
  }
  
  @SyntheticMember
  public DisplaySkill(final Agent agent) {
    super(agent);
  }
}
