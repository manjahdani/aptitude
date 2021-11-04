package be.uclouvain.python_access;

import be.uclouvain.python_access.PythonAccess;
import be.uclouvain.python_access.messages.ActionMessage;
import be.uclouvain.python_access.messages.BBoxes2DMessage;
import be.uclouvain.python_access.messages.BBoxes2DTrackMessage;
import be.uclouvain.python_access.messages.BaseMessage;
import be.uclouvain.python_access.messages.DisplayMessage;
import be.uclouvain.python_access.messages.EvaluationMessage;
import be.uclouvain.python_access.messages.RequestMessage;
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
  
  private HazelcastInstance hzInstance;
  
  private HashMap<String, ITopic<BaseMessage>> subscribtionTopic = CollectionLiterals.<String, ITopic<BaseMessage>>newHashMap();
  
  @Pure
  public static CommunicationManager getInstance() {
    synchronized (CommunicationManager.class) {
      if ((CommunicationManager.instance == null)) {
        CommunicationManager _communicationManager = new CommunicationManager();
        CommunicationManager.instance = _communicationManager;
      }
      return CommunicationManager.instance;
    }
  }
  
  private CommunicationManager() {
    this.createHzInstance();
  }
  
  private HazelcastInstance createHzInstance() {
    HazelcastInstance _xblockexpression = null;
    {
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
                if ((id == 30)) {
                  return new EvaluationMessage();
                } else {
                  if ((id == 91)) {
                    return new DisplayMessage();
                  } else {
                    return null;
                  }
                }
              }
            }
          }
        }
      };
      serialConfig.addDataSerializableFactory(
        1, _function);
      hzConfig.setSerializationConfig(serialConfig);
      hzConfig.setProperty("hazelcast.rest.enabled", "true");
      hzConfig.getManagementCenterConfig().setEnabled(true);
      hzConfig.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter/");
      _xblockexpression = this.hzInstance = Hazelcast.newHazelcastInstance(hzConfig);
    }
    return _xblockexpression;
  }
  
  public String subscribeTopic(final String topicName, final PythonAccess observer) {
    abstract class __CommunicationManager_0 implements MessageListener<BaseMessage> {
      public abstract void onMessage(final Message<BaseMessage> m);
    }
    
    final ITopic<BaseMessage> topic = this.hzInstance.<BaseMessage>getTopic(topicName);
    __CommunicationManager_0 ___CommunicationManager_0 = new __CommunicationManager_0() {
      public void onMessage(final Message<BaseMessage> m) {
        observer.update(m.getMessageObject());
      }
    };
    final String subscribtionID = topic.addMessageListener(___CommunicationManager_0);
    this.subscribtionTopic.put(subscribtionID.toString(), topic);
    return subscribtionID;
  }
  
  public boolean unsubscribeTopic(final String subscribtionID) {
    boolean _xblockexpression = false;
    {
      final ITopic<BaseMessage> topic = this.subscribtionTopic.remove(subscribtionID);
      class $AssertEvaluator$ {
        final boolean $$result;
        $AssertEvaluator$() {
          this.$$result = (topic != null);
        }
      }
      assert new $AssertEvaluator$().$$result;
      _xblockexpression = topic.removeMessageListener(subscribtionID);
    }
    return _xblockexpression;
  }
  
  public void publishMessage(final String topicSub, final BaseMessage message) {
    final ITopic<BaseMessage> topic = this.subscribtionTopic.get(topicSub);
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        this.$$result = (topic != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
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
}
