package be.uclouvain.aptitude.surveillance.ui;

import be.uclouvain.aptitude.surveillance.algorithm.messages.BBoxes2DTrackMessage;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.io.IOException;
import java.util.Objects;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class DisplayMessage extends BBoxes2DTrackMessage {
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String streamID;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String streamPath;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int[] countingLines;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int[] counts;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int sensitivity;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String observer;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String roiPath;
  
  private final int FACTORY_ID = 1;
  
  private final int CLASS_ID = 91;
  
  public DisplayMessage() {
  }
  
  public DisplayMessage(final BBoxes2DTrackMessage message) {
    this.setNumberObjects(message.getNumberObjects());
    this.setDetectionTime(message.getDetectionTime());
    this.setBboxes(message.getBboxes());
    this.setClassIDs(message.getClassIDs());
    this.setDetConfs(message.getDetConfs());
    this.setDimWidth(message.getDimWidth());
    this.setDimHeight(message.getDimHeight());
    this.setBboxesFormat(message.getBboxesFormat());
    this.setFrameNumber(message.getFrameNumber());
    this.setLastFrame(message.isLastFrame());
    this.setTrackingTime(message.getTrackingTime());
    this.setGlobalIDs(message.getGlobalIDs());
  }
  
  public int getFactoryId() {
    return this.FACTORY_ID;
  }
  
  public int getId() {
    return this.CLASS_ID;
  }
  
  public void writeData(final ObjectDataOutput odo) throws IOException {
    super.writeData(odo);
    odo.writeUTF(this.streamID);
    odo.writeUTF(this.streamPath);
    odo.writeIntArray(this.countingLines);
    odo.writeIntArray(this.counts);
    odo.writeInt(this.sensitivity);
    odo.writeUTF(this.observer);
    odo.writeUTF(this.roiPath);
  }
  
  public void readData(final ObjectDataInput odi) throws IOException {
    super.readData(odi);
    this.streamID = odi.readUTF();
    this.streamPath = odi.readUTF();
    this.countingLines = odi.readIntArray();
    this.counts = odi.readIntArray();
    this.sensitivity = odi.readInt();
    this.observer = odi.readUTF();
    this.roiPath = odi.readUTF();
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
    DisplayMessage other = (DisplayMessage) obj;
    if (!Objects.equals(this.streamID, other.streamID))
      return false;
    if (!Objects.equals(this.streamPath, other.streamPath))
      return false;
    if (other.sensitivity != this.sensitivity)
      return false;
    if (!Objects.equals(this.observer, other.observer))
      return false;
    if (!Objects.equals(this.roiPath, other.roiPath))
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
    result = prime * result + Objects.hashCode(this.streamID);
    result = prime * result + Objects.hashCode(this.streamPath);
    result = prime * result + Integer.hashCode(this.sensitivity);
    result = prime * result + Objects.hashCode(this.observer);
    result = prime * result + Objects.hashCode(this.roiPath);
    result = prime * result + Integer.hashCode(this.FACTORY_ID);
    result = prime * result + Integer.hashCode(this.CLASS_ID);
    return result;
  }
  
  @Pure
  public String getStreamID() {
    return this.streamID;
  }
  
  public void setStreamID(final String streamID) {
    this.streamID = streamID;
  }
  
  @Pure
  public String getStreamPath() {
    return this.streamPath;
  }
  
  public void setStreamPath(final String streamPath) {
    this.streamPath = streamPath;
  }
  
  @Pure
  public int[] getCountingLines() {
    return this.countingLines;
  }
  
  public void setCountingLines(final int[] countingLines) {
    this.countingLines = countingLines;
  }
  
  @Pure
  public int[] getCounts() {
    return this.counts;
  }
  
  public void setCounts(final int[] counts) {
    this.counts = counts;
  }
  
  @Pure
  public int getSensitivity() {
    return this.sensitivity;
  }
  
  public void setSensitivity(final int sensitivity) {
    this.sensitivity = sensitivity;
  }
  
  @Pure
  public String getObserver() {
    return this.observer;
  }
  
  public void setObserver(final String observer) {
    this.observer = observer;
  }
  
  @Pure
  public String getRoiPath() {
    return this.roiPath;
  }
  
  public void setRoiPath(final String roiPath) {
    this.roiPath = roiPath;
  }
}
