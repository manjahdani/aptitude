package be.uclouvain.aptitude.surveillance.algorithm.tracker;

import be.uclouvain.aptitude.surveillance.algorithm.ObserverPythonAccess;
import be.uclouvain.aptitude.surveillance.algorithm.tracker.TrackerPythonTwinCapacity;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBOX;
import be.uclouvain.aptitude.surveillance.algorithm.util.BBoxe2D;
import be.uclouvain.python_access.BBoxes2DTrackResult;
import be.uclouvain.python_access.CommunicationManager;
import be.uclouvain.python_access.PartnerTrackingFound;
import be.uclouvain.python_access.messages.BBoxes2DTrackMessage;
import be.uclouvain.python_access.messages.BaseMessage;
import be.uclouvain.python_access.messages.RequestMessage;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO: write a description
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class TrackerPythonTwin extends ObserverPythonAccess implements TrackerPythonTwinCapacity {
  private final double FRAME_WIDTH = 1920.0;
  
  private double FRAME_HEIGHT = 1080.0;
  
  private double totalTrackerTime = 0;
  
  private double totalDetectorTime = 0;
  
  public void update(final BaseMessage m) {
    if ((m instanceof BBoxes2DTrackMessage)) {
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      BBoxes2DTrackResult _bBoxes2DTrackResult = new BBoxes2DTrackResult(((BBoxes2DTrackMessage)m));
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID $_iD_1;
        
        public $SerializableClosureProxy(final UUID $_iD_1) {
          this.$_iD_1 = $_iD_1;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          return Objects.equal(_uUID, $_iD_1);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _uUID = it.getUUID();
          UUID _iD = TrackerPythonTwin.this.getOwner().getID();
          return Objects.equal(_uUID, _iD);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, TrackerPythonTwin.this.getOwner().getID());
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_bBoxes2DTrackResult, _function);
    } else {
      if ((m instanceof RequestMessage)) {
        this.onRequestMessage(((RequestMessage)m));
      }
    }
  }
  
  private void onRequestMessage(final RequestMessage message) {
    if ((message.isAck() && Objects.equal(message.getRequestID(), this.pendingRequestID))) {
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.cancel(this.requestTask);
      CommunicationManager.getInstance().unsubscribeTopic(this.topicRequestSub);
      this.topicRequestSub = null;
      String _string = this.getOwner().getID().toString();
      String _clientName = message.getClientName();
      final String topicName = ((("tracking_" + _string) + "_") + _clientName);
      this.topicSignalAcquisition = CommunicationManager.getInstance().subscribeTopic(topicName, this);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      String _clientName_1 = message.getClientName();
      PartnerTrackingFound _partnerTrackingFound = new PartnerTrackingFound(_clientName_1);
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_partnerTrackingFound);
    }
  }
  
  public ArrayList<BBoxe2D> formatConversion(final BBoxes2DTrackMessage track) {
    double _talDetectorTime = this.totalDetectorTime;
    double _detectionTime = track.getDetectionTime();
    this.totalDetectorTime = (_talDetectorTime + _detectionTime);
    double _talTrackerTime = this.totalTrackerTime;
    double _trackingTime = track.getTrackingTime();
    this.totalTrackerTime = (_talTrackerTime + _trackingTime);
    int _dimWidth = track.getDimWidth();
    double ratio_width = (this.FRAME_WIDTH / _dimWidth);
    int _dimHeight = track.getDimHeight();
    double ratio_height = (this.FRAME_HEIGHT / _dimHeight);
    ArrayList<BBoxe2D> tmp = CollectionLiterals.<BBoxe2D>newArrayList();
    for (int i = 0; (i < track.getNumberObjects()); i++) {
      {
        int _get = track.getBboxes()[(4 * i)];
        double X = (_get * ratio_width);
        int _get_1 = track.getBboxes()[((4 * i) + 1)];
        double Y = (_get_1 * ratio_height);
        int _get_2 = track.getBboxes()[((4 * i) + 2)];
        double W = (_get_2 * ratio_width);
        int _get_3 = track.getBboxes()[((4 * i) + 3)];
        double H = (_get_3 * ratio_height);
        int classID = track.getClassIDs()[i];
        int globalID = track.getGlobalIDs()[i];
        double conf = track.getDetConfs()[i];
        BBOX _bBOX = new BBOX(X, Y, W, H);
        int _frameNumber = track.getFrameNumber();
        BBoxe2D _bBoxe2D = new BBoxe2D(_bBOX, conf, globalID, classID, _frameNumber);
        tmp.add(_bBoxe2D);
      }
    }
    return tmp;
  }
  
  @SuppressWarnings("discouraged_reference")
  public String print(final BBoxes2DTrackMessage track) {
    String _xblockexpression = null;
    {
      InputOutput.<String>println("***********************");
      int _frameNumber = track.getFrameNumber();
      InputOutput.<String>println(("* Tracking " + Integer.valueOf(_frameNumber)));
      double _trackingTime = track.getTrackingTime();
      InputOutput.<String>println(("*  " + Double.valueOf(_trackingTime)));
      String _string = Arrays.toString(track.getGlobalIDs());
      InputOutput.<String>println(("*  " + _string));
      String _string_1 = Arrays.toString(track.getBboxes());
      InputOutput.<String>println(("*  " + _string_1));
      String _string_2 = Arrays.toString(track.getClassIDs());
      InputOutput.<String>println(("*  " + _string_2));
      String _string_3 = Arrays.toString(track.getDetConfs());
      InputOutput.<String>println(("*  " + _string_3));
      boolean _isLastFrame = track.isLastFrame();
      InputOutput.<String>println(("*  " + Boolean.valueOf(_isLastFrame)));
      _xblockexpression = InputOutput.<String>println("***********************");
    }
    return _xblockexpression;
  }
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
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
    TrackerPythonTwin other = (TrackerPythonTwin) obj;
    if (Double.doubleToLongBits(other.FRAME_WIDTH) != Double.doubleToLongBits(this.FRAME_WIDTH))
      return false;
    if (Double.doubleToLongBits(other.FRAME_HEIGHT) != Double.doubleToLongBits(this.FRAME_HEIGHT))
      return false;
    if (Double.doubleToLongBits(other.totalTrackerTime) != Double.doubleToLongBits(this.totalTrackerTime))
      return false;
    if (Double.doubleToLongBits(other.totalDetectorTime) != Double.doubleToLongBits(this.totalDetectorTime))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.FRAME_WIDTH);
    result = prime * result + Double.hashCode(this.FRAME_HEIGHT);
    result = prime * result + Double.hashCode(this.totalTrackerTime);
    result = prime * result + Double.hashCode(this.totalDetectorTime);
    return result;
  }
  
  @SyntheticMember
  public TrackerPythonTwin() {
    super();
  }
  
  @SyntheticMember
  public TrackerPythonTwin(final Agent agent) {
    super(agent);
  }
}
