package be.uclouvain.aptitude.messages;

import be.uclouvain.aptitude.messages.BBoxes2DMessage;
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
public class BBoxes2DTrackMessage extends BBoxes2DMessage {
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double trackingTime;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int[] globalIDs;
  
  private final int FACTORY_ID = 1;
  
  private final int CLASS_ID = 211;
  
  public int getFactoryId() {
    return this.FACTORY_ID;
  }
  
  public int getId() {
    return this.CLASS_ID;
  }
  
  public void writeData(final ObjectDataOutput odo) throws IOException {
    super.writeData(odo);
    odo.writeDouble(this.trackingTime);
    odo.writeIntArray(this.globalIDs);
  }
  
  public void readData(final ObjectDataInput odi) throws IOException {
    super.readData(odi);
    this.trackingTime = odi.readDouble();
    this.globalIDs = odi.readIntArray();
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
    BBoxes2DTrackMessage other = (BBoxes2DTrackMessage) obj;
    if (Double.doubleToLongBits(other.trackingTime) != Double.doubleToLongBits(this.trackingTime))
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
    result = prime * result + Double.hashCode(this.trackingTime);
    result = prime * result + Integer.hashCode(this.FACTORY_ID);
    result = prime * result + Integer.hashCode(this.CLASS_ID);
    return result;
  }
  
  @SyntheticMember
  public BBoxes2DTrackMessage() {
    super();
  }
  
  @Pure
  public double getTrackingTime() {
    return this.trackingTime;
  }
  
  public void setTrackingTime(final double trackingTime) {
    this.trackingTime = trackingTime;
  }
  
  @Pure
  public int[] getGlobalIDs() {
    return this.globalIDs;
  }
  
  public void setGlobalIDs(final int[] globalIDs) {
    this.globalIDs = globalIDs;
  }
}
