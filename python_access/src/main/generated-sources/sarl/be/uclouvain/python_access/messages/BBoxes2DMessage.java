package be.uclouvain.python_access.messages;

import be.uclouvain.python_access.messages.BaseMessage;
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
 * @author samelson
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class BBoxes2DMessage extends BaseMessage {
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int numberObjects;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double detectionTime;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int[] bboxes;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int[] classIDs;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] detConfs;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int dimWidth;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int dimHeight;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String bboxesFormat;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int frameNumber;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private boolean lastFrame;
  
  private final int FACTORY_ID = 1;
  
  private final int CLASS_ID = 21;
  
  public int getFactoryId() {
    return this.FACTORY_ID;
  }
  
  public int getId() {
    return this.CLASS_ID;
  }
  
  public void writeData(final ObjectDataOutput odo) throws IOException {
    super.writeData(odo);
    odo.writeInt(this.numberObjects);
    odo.writeDouble(this.detectionTime);
    odo.writeIntArray(this.bboxes);
    odo.writeIntArray(this.classIDs);
    odo.writeDoubleArray(this.detConfs);
    odo.writeInt(this.dimWidth);
    odo.writeInt(this.dimHeight);
    odo.writeUTF(this.bboxesFormat);
    odo.writeInt(this.frameNumber);
    odo.writeBoolean(this.lastFrame);
  }
  
  public void readData(final ObjectDataInput odi) throws IOException {
    super.readData(odi);
    this.numberObjects = odi.readInt();
    this.detectionTime = odi.readDouble();
    this.bboxes = odi.readIntArray();
    this.classIDs = odi.readIntArray();
    this.detConfs = odi.readDoubleArray();
    this.dimWidth = odi.readInt();
    this.dimHeight = odi.readInt();
    this.bboxesFormat = odi.readUTF();
    this.frameNumber = odi.readInt();
    this.lastFrame = odi.readBoolean();
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
    BBoxes2DMessage other = (BBoxes2DMessage) obj;
    if (other.numberObjects != this.numberObjects)
      return false;
    if (Double.doubleToLongBits(other.detectionTime) != Double.doubleToLongBits(this.detectionTime))
      return false;
    if (other.dimWidth != this.dimWidth)
      return false;
    if (other.dimHeight != this.dimHeight)
      return false;
    if (!Objects.equals(this.bboxesFormat, other.bboxesFormat))
      return false;
    if (other.frameNumber != this.frameNumber)
      return false;
    if (other.lastFrame != this.lastFrame)
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
    result = prime * result + Integer.hashCode(this.numberObjects);
    result = prime * result + Double.hashCode(this.detectionTime);
    result = prime * result + Integer.hashCode(this.dimWidth);
    result = prime * result + Integer.hashCode(this.dimHeight);
    result = prime * result + Objects.hashCode(this.bboxesFormat);
    result = prime * result + Integer.hashCode(this.frameNumber);
    result = prime * result + Boolean.hashCode(this.lastFrame);
    result = prime * result + Integer.hashCode(this.FACTORY_ID);
    result = prime * result + Integer.hashCode(this.CLASS_ID);
    return result;
  }
  
  @SyntheticMember
  public BBoxes2DMessage() {
    super();
  }
  
  @Pure
  public int getNumberObjects() {
    return this.numberObjects;
  }
  
  public void setNumberObjects(final int numberObjects) {
    this.numberObjects = numberObjects;
  }
  
  @Pure
  public double getDetectionTime() {
    return this.detectionTime;
  }
  
  public void setDetectionTime(final double detectionTime) {
    this.detectionTime = detectionTime;
  }
  
  @Pure
  public int[] getBboxes() {
    return this.bboxes;
  }
  
  public void setBboxes(final int[] bboxes) {
    this.bboxes = bboxes;
  }
  
  @Pure
  public int[] getClassIDs() {
    return this.classIDs;
  }
  
  public void setClassIDs(final int[] classIDs) {
    this.classIDs = classIDs;
  }
  
  @Pure
  public double[] getDetConfs() {
    return this.detConfs;
  }
  
  public void setDetConfs(final double[] detConfs) {
    this.detConfs = detConfs;
  }
  
  @Pure
  public int getDimWidth() {
    return this.dimWidth;
  }
  
  public void setDimWidth(final int dimWidth) {
    this.dimWidth = dimWidth;
  }
  
  @Pure
  public int getDimHeight() {
    return this.dimHeight;
  }
  
  public void setDimHeight(final int dimHeight) {
    this.dimHeight = dimHeight;
  }
  
  @Pure
  public String getBboxesFormat() {
    return this.bboxesFormat;
  }
  
  public void setBboxesFormat(final String bboxesFormat) {
    this.bboxesFormat = bboxesFormat;
  }
  
  @Pure
  public int getFrameNumber() {
    return this.frameNumber;
  }
  
  public void setFrameNumber(final int frameNumber) {
    this.frameNumber = frameNumber;
  }
  
  @Pure
  public boolean isLastFrame() {
    return this.lastFrame;
  }
  
  public void setLastFrame(final boolean lastFrame) {
    this.lastFrame = lastFrame;
  }
}
