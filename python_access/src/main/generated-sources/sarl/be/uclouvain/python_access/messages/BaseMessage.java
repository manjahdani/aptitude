package be.uclouvain.python_access.messages;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import com.hazelcast.util.UuidUtil;
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
public abstract class BaseMessage implements IdentifiedDataSerializable {
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PRIVATE_SETTER })
  private String uuid = UuidUtil.newSecureUuidString();
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private boolean ack;
  
  public void writeData(final ObjectDataOutput odo) throws IOException {
    odo.writeUTF(this.uuid);
    odo.writeBoolean(this.ack);
  }
  
  public void readData(final ObjectDataInput odi) throws IOException {
    this.uuid = odi.readUTF();
    this.ack = odi.readBoolean();
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
    BaseMessage other = (BaseMessage) obj;
    if (!Objects.equals(this.uuid, other.uuid))
      return false;
    if (other.ack != this.ack)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.uuid);
    result = prime * result + Boolean.hashCode(this.ack);
    return result;
  }
  
  @SyntheticMember
  public BaseMessage() {
    super();
  }
  
  @Pure
  public String getUuid() {
    return this.uuid;
  }
  
  private void setUuid(final String uuid) {
    this.uuid = uuid;
  }
  
  @Pure
  public boolean isAck() {
    return this.ack;
  }
  
  public void setAck(final boolean ack) {
    this.ack = ack;
  }
}
