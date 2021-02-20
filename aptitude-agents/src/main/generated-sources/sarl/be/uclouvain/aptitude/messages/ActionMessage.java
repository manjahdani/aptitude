package be.uclouvain.aptitude.messages;

import be.uclouvain.aptitude.messages.BaseMessage;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.io.IOException;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author samelson
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class ActionMessage extends BaseMessage {
  /**
   * PLAY = 1
   * PAUSE = 2
   * RESUME = 3
   * STOP = 4
   * RESTART = 5
   * SEEK = 6
   */
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int actionID;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int lastFrameNumber;
  
  private final int FACTORY_ID = 1;
  
  private final int CLASS_ID = 11;
  
  public int getFactoryId() {
    return this.FACTORY_ID;
  }
  
  public int getId() {
    return this.CLASS_ID;
  }
  
  public void readData(final ObjectDataInput odi) throws IOException {
    super.readData(odi);
    this.actionID = odi.readInt();
    this.lastFrameNumber = odi.readInt();
  }
  
  public void writeData(final ObjectDataOutput odo) throws IOException {
    super.writeData(odo);
    odo.writeInt(this.actionID);
    odo.writeInt(this.lastFrameNumber);
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
    ActionMessage other = (ActionMessage) obj;
    if (other.actionID != this.actionID)
      return false;
    if (other.lastFrameNumber != this.lastFrameNumber)
      return false;
    if (other.FACTORY_ID != this.FACTORY_ID)
      return false;
    if (other.CLASS_ID != this.CLASS_ID)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.actionID);
    result = prime * result + Integer.hashCode(this.lastFrameNumber);
    result = prime * result + Integer.hashCode(this.FACTORY_ID);
    result = prime * result + Integer.hashCode(this.CLASS_ID);
    return result;
  }
  
  @SyntheticMember
  public ActionMessage() {
    super();
  }
  
  @Pure
  public int getActionID() {
    return this.actionID;
  }
  
  public void setActionID(final int actionID) {
    this.actionID = actionID;
  }
  
  @Pure
  public int getLastFrameNumber() {
    return this.lastFrameNumber;
  }
  
  public void setLastFrameNumber(final int lastFrameNumber) {
    this.lastFrameNumber = lastFrameNumber;
  }
}
