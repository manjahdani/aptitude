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
public class RequestMessage extends BaseMessage {
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String requestID;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String agentID;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String clientName;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String jsonConfig;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private boolean update;
  
  private final int FACTORY_ID = 1;
  
  private final int CLASS_ID = 10;
  
  public int getFactoryId() {
    return this.FACTORY_ID;
  }
  
  public int getId() {
    return this.CLASS_ID;
  }
  
  public void readData(final ObjectDataInput odi) throws IOException {
    super.readData(odi);
    this.requestID = odi.readUTF();
    this.agentID = odi.readUTF();
    this.clientName = odi.readUTF();
    this.jsonConfig = odi.readUTF();
    this.update = odi.readBoolean();
  }
  
  public void writeData(final ObjectDataOutput odo) throws IOException {
    super.writeData(odo);
    odo.writeUTF(this.requestID);
    odo.writeUTF(this.agentID);
    odo.writeUTF(this.clientName);
    odo.writeUTF(this.jsonConfig);
    odo.writeBoolean(this.update);
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
    RequestMessage other = (RequestMessage) obj;
    if (!Objects.equals(this.requestID, other.requestID))
      return false;
    if (!Objects.equals(this.agentID, other.agentID))
      return false;
    if (!Objects.equals(this.clientName, other.clientName))
      return false;
    if (!Objects.equals(this.jsonConfig, other.jsonConfig))
      return false;
    if (other.update != this.update)
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
    result = prime * result + Objects.hashCode(this.requestID);
    result = prime * result + Objects.hashCode(this.agentID);
    result = prime * result + Objects.hashCode(this.clientName);
    result = prime * result + Objects.hashCode(this.jsonConfig);
    result = prime * result + Boolean.hashCode(this.update);
    result = prime * result + Integer.hashCode(this.FACTORY_ID);
    result = prime * result + Integer.hashCode(this.CLASS_ID);
    return result;
  }
  
  @SyntheticMember
  public RequestMessage() {
    super();
  }
  
  @Pure
  public String getRequestID() {
    return this.requestID;
  }
  
  public void setRequestID(final String requestID) {
    this.requestID = requestID;
  }
  
  @Pure
  public String getAgentID() {
    return this.agentID;
  }
  
  public void setAgentID(final String agentID) {
    this.agentID = agentID;
  }
  
  @Pure
  public String getClientName() {
    return this.clientName;
  }
  
  public void setClientName(final String clientName) {
    this.clientName = clientName;
  }
  
  @Pure
  public String getJsonConfig() {
    return this.jsonConfig;
  }
  
  public void setJsonConfig(final String jsonConfig) {
    this.jsonConfig = jsonConfig;
  }
  
  @Pure
  public boolean isUpdate() {
    return this.update;
  }
  
  public void setUpdate(final boolean update) {
    this.update = update;
  }
}
