package be.uclouvain.aptitudeAgents;

import UDPMessages.ActorStats;
import UDPMessages.UDP_Message_AckRequestAllStats;
import UDPMessages.UDP_Message_AckRequestSpawn;
import UDPMessages.UDP_Message_AckRequestWithdraw;
import UDPMessages.UDP_Message_AuthenticateMobile;
import UDPMessages.UDP_Message_Base;
import UDPMessages.UDP_Message_RequestSpawn;
import UDPMessages.UDP_Message_RequestStats;
import UDPMessages.UDP_Message_RequestWithdraw;
import be.uclouvain.aptitudeAgents.UDPSender;
import be.uclouvain.aptitudeAgents.User;
import be.uclouvain.organisation.interactivity.element.ElementInformation;
import be.uclouvain.organisation.interactivity.inputDevice.InputSkill;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class DeviceNetworkManagerSkill extends InputSkill implements Capacity {
  private UDPSender clientSender = new UDPSender();
  
  private final TreeMap<UUID, String> mobileAddress = new TreeMap<UUID, String>();
  
  private final TreeMap<String, UUID> mobileIPAddress = new TreeMap<String, UUID>();
  
  private final TreeMap<UUID, EventSpace> worldlistenersSpaceIDs = super.listenersSpaceIDs;
  
  private final ArrayList<UUID> WorldList = new ArrayList<UUID>();
  
  public void install() {
    this.WorldList.addAll(this.worldlistenersSpaceIDs.keySet());
  }
  
  public void MessageAnalysis(final UDP_Message_Base data, final String IPadrss) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(data.method);
    final String _switchValue = data.method;
    if (_switchValue != null) {
      switch (_switchValue) {
        case "authenticateMobile":
          boolean _containsValue = this.mobileAddress.containsValue(IPadrss);
          if ((!_containsValue)) {
            final UUID id = UUID.randomUUID();
            this.mobileAddress.put(id, IPadrss);
            this.mobileIPAddress.put(IPadrss, id);
            Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
            InnerContextAccess _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER();
            _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(User.class, id, _$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER.getInnerContext(), this.worldlistenersSpaceIDs, this.WorldList, ((UDP_Message_AuthenticateMobile) data).playerUID);
          }
          break;
        case "requestSpawn":
          this.RequestSpawn(((UDP_Message_RequestSpawn) data), IPadrss);
          break;
        case "requestWithdraw":
          this.RequestWithdraw(((UDP_Message_RequestWithdraw) data), IPadrss);
          break;
        default:
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("Unknown Messages" + data));
          break;
      }
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("Unknown Messages" + data));
    }
  }
  
  @Pure
  public TreeMap<UUID, String> getMobileAddress() {
    return this.mobileAddress;
  }
  
  public void RequestWithdraw(final UDP_Message_RequestWithdraw data, final String adrs) {
    UDP_Message_AckRequestWithdraw msgOut = new UDP_Message_AckRequestWithdraw(data.sequenceNumber);
    this.clientSender.<UDP_Message_AckRequestWithdraw>SendData(msgOut, adrs, 65003);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    ElementInformation _elementInformation = new ElementInformation(data);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_get;
      
      public $SerializableClosureProxy(final UUID $_get) {
        this.$_get = $_get;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_get);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        UUID _get = DeviceNetworkManagerSkill.this.mobileIPAddress.get(adrs);
        return Objects.equal(_uUID, _get);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, DeviceNetworkManagerSkill.this.mobileIPAddress.get(adrs));
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_elementInformation, _function);
  }
  
  public void RequestSpawn(final UDP_Message_RequestSpawn data, final String adrs) {
    UDP_Message_AckRequestSpawn _uDP_Message_AckRequestSpawn = new UDP_Message_AckRequestSpawn(data.sequenceNumber);
    this.clientSender.<UDP_Message_AckRequestSpawn>SendData(_uDP_Message_AckRequestSpawn, adrs, 65003);
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    ElementInformation _elementInformation = new ElementInformation(data);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_get;
      
      public $SerializableClosureProxy(final UUID $_get) {
        this.$_get = $_get;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_get);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        UUID _get = DeviceNetworkManagerSkill.this.mobileIPAddress.get(adrs);
        return Objects.equal(_uUID, _get);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, DeviceNetworkManagerSkill.this.mobileIPAddress.get(adrs));
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_elementInformation, _function);
  }
  
  public void RequestStats(final UDP_Message_RequestStats data, final String adrs) {
    ActorStats[] allStats = new ActorStats[1];
    ActorStats _actorStats = new ActorStats(data.actorUID, "2");
    allStats[0] = _actorStats;
    UDP_Message_AckRequestAllStats _uDP_Message_AckRequestAllStats = new UDP_Message_AckRequestAllStats(data.actorUID, allStats);
    this.clientSender.<UDP_Message_AckRequestAllStats>SendData(_uDP_Message_AckRequestAllStats, adrs, 65003);
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(InnerContextAccess.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS;
  
  @SyntheticMember
  @Pure
  private InnerContextAccess $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS == null || this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS = $getSkill(InnerContextAccess.class);
    }
    return $castSkill(InnerContextAccess.class, this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS);
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
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
  public DeviceNetworkManagerSkill(final int p) {
    super(p);
  }
  
  @SyntheticMember
  public DeviceNetworkManagerSkill(final DatagramSocket server) {
    super(server);
  }
  
  @SyntheticMember
  public DeviceNetworkManagerSkill(final int p, final TreeMap<UUID, EventSpace> subWorldSpaceIDs) {
    super(p, subWorldSpaceIDs);
  }
}
