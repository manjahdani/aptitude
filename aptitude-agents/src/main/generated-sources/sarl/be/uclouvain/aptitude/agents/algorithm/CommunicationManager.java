package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.Observer;
import be.uclouvain.aptitude.agents.algorithm.RequestMessage;
import be.uclouvain.aptitude.agents.algorithm.messages.ActionMessage;
import be.uclouvain.aptitude.aptitude.messages.BBoxes2DMessage;
import be.uclouvain.aptitude.aptitude.messages.BBoxes2DTrackMessage;
import be.uclouvain.aptitude.aptitude.messages.BaseMessage;
import com.hazelcast.config.Config;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.HashMap;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class CommunicationManager {
  private static CommunicationManager instance;
  
  private static HazelcastInstance hzInstance;
  
  private HashMap<String, ITopic<BaseMessage>> subscribtionTopic = CollectionLiterals.<String, ITopic<BaseMessage>>newHashMap();
  
  @Pure
  public static CommunicationManager getInstance() {
    if ((CommunicationManager.instance == null)) {
      CommunicationManager _communicationManager = new CommunicationManager();
      CommunicationManager.instance = _communicationManager;
      CommunicationManager.hzInstance = CommunicationManager.getHzInstance();
    }
    return CommunicationManager.instance;
  }
  
  @Pure
  private static HazelcastInstance getHzInstance() {
    if ((CommunicationManager.hzInstance == null)) {
      Config hzConfig = new Config();
      SerializationConfig serialConfig = new SerializationConfig();
      final DataSerializableFactory _function = (int id) -> {
        if ((id == 10)) {
          return new RequestMessage();
        } else {
          if ((id == 11)) {
            return new ActionMessage();
          } else {
            if ((id == 21)) {
              return new BBoxes2DMessage();
            } else {
              if ((id == 211)) {
                return new BBoxes2DTrackMessage();
              } else {
                return null;
              }
            }
          }
        }
      };
      serialConfig.addDataSerializableFactory(1, _function);
      hzConfig.setSerializationConfig(serialConfig);
      hzConfig.setProperty("hazelcast.rest.enabled", "true");
      hzConfig.getManagementCenterConfig().setEnabled(true);
      hzConfig.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter/");
      CommunicationManager.hzInstance = Hazelcast.newHazelcastInstance(hzConfig);
    }
    return CommunicationManager.hzInstance;
  }
  
  public String subscribeTopic(final String topicName, final Observer observer) {
    abstract class __CommunicationManager_0 implements MessageListener<BaseMessage> {
      public abstract void onMessage(final Message<BaseMessage> m);
    }
    
    final ITopic<BaseMessage> topic = CommunicationManager.getHzInstance().<BaseMessage>getTopic(topicName);
    __CommunicationManager_0 ___CommunicationManager_0 = new __CommunicationManager_0() {
      public void onMessage(final Message<BaseMessage> m) {
        observer.update(m.getMessageObject());
      }
    };
    final String subscribtionID = topic.addMessageListener(___CommunicationManager_0);
    this.subscribtionTopic.put(subscribtionID, topic);
    return subscribtionID;
  }
  
  public boolean unsubscribeTopic(final String subscribtionID) {
    boolean _xblockexpression = false;
    {
      final ITopic<BaseMessage> topic = this.subscribtionTopic.remove(subscribtionID);
      _xblockexpression = topic.removeMessageListener(subscribtionID);
    }
    return _xblockexpression;
  }
  
  public void publishMessage(final String topicSub, final BaseMessage message) {
    final ITopic<BaseMessage> topic = this.subscribtionTopic.get(topicSub);
    topic.publish(message);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  @SyntheticMember
  public CommunicationManager() {
    super();
  }
}
