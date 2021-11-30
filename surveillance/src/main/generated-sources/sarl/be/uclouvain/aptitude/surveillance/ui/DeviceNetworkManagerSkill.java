package be.uclouvain.aptitude.surveillance.ui;

import UDPMessages.ActorStats;
import UDPMessages.UDP_Message_AckRequestAllStats;
import UDPMessages.UDP_Message_AckRequestSpawn;
import UDPMessages.UDP_Message_AckRequestWithdraw;
import UDPMessages.UDP_Message_AuthenticateMobile;
import UDPMessages.UDP_Message_Base;
import UDPMessages.UDP_Message_RequestSpawn;
import UDPMessages.UDP_Message_RequestStats;
import UDPMessages.UDP_Message_RequestWithdraw;
import be.uclouvain.aptitude.surveillance.ui.AuthenticateUser;
import be.uclouvain.aptitude.surveillance.ui.UDPSender;
import be.uclouvain.organisation.interactivity.element.ElementInformation;
import be.uclouvain.organisation.interactivity.inputDevice.InputSkill;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO: write a description
 * 
 * @author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class DeviceNetworkManagerSkill extends InputSkill implements Capacity {
  private UDPSender clientSender = new UDPSender();
  
  private final HashMap<UUID, String> mobileAddress = new HashMap<UUID, String>();
  
  private final HashMap<String, UUID> mobileIPAddress = new HashMap<String, UUID>();
  
  private final UUID parentID;
  
  public DeviceNetworkManagerSkill(final int p, final UUID holonID) {
    super(p);
    this.parentID = holonID;
  }
  
  public void messageAnalysis(final UDP_Message_Base data, final String IPadrss) {
    final String _switchValue = data.method;
    if (_switchValue != null) {
      switch (_switchValue) {
        case "authenticateMobile":
          boolean _containsValue = this.mobileAddress.containsValue(IPadrss);
          if ((!_containsValue)) {
            final UUID ID = UUID.randomUUID();
            this.mobileAddress.put(ID, IPadrss);
            this.mobileIPAddress.put(IPadrss, ID);
            this.authenticateUser(ID, ((UDP_Message_AuthenticateMobile) data));
          }
          break;
        case "requestSpawn":
          this.requestSpawn(((UDP_Message_RequestSpawn) data), IPadrss);
          break;
        case "requestWithdraw":
          this.requestWithdraw(((UDP_Message_RequestWithdraw) data), IPadrss);
          break;
        default:
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Unknown Messages" + data));
          break;
      }
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Unknown Messages" + data));
    }
  }
  
  public void authenticateUser(final UUID mobileID, final UDP_Message_AuthenticateMobile data) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    AuthenticateUser _authenticateUser = new AuthenticateUser(mobileID, data);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_parentID;
      
      public $SerializableClosureProxy(final UUID $_parentID) {
        this.$_parentID = $_parentID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, $_parentID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _uUID = it.getUUID();
        return Objects.equal(_uUID, DeviceNetworkManagerSkill.this.parentID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, DeviceNetworkManagerSkill.this.parentID);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_authenticateUser, _function);
  }
  
  @Pure
  public HashMap<UUID, String> getMobileAddress() {
    return this.mobileAddress;
  }
  
  public void requestWithdraw(final UDP_Message_RequestWithdraw data, final String adrs) {
    UDP_Message_AckRequestWithdraw msgOut = new UDP_Message_AckRequestWithdraw(data.sequenceNumber);
    this.clientSender.<UDP_Message_AckRequestWithdraw>SendData(msgOut, adrs, 65003);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
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
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_elementInformation, _function);
  }
  
  public void requestSpawn(final UDP_Message_RequestSpawn data, final String adrs) {
    UDP_Message_AckRequestSpawn _uDP_Message_AckRequestSpawn = new UDP_Message_AckRequestSpawn(data.sequenceNumber);
    this.clientSender.<UDP_Message_AckRequestSpawn>SendData(_uDP_Message_AckRequestSpawn, adrs, 65003);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
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
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_elementInformation, _function);
  }
  
  public void requestStats(final UDP_Message_RequestStats data, final String adrs) {
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
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
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
    DeviceNetworkManagerSkill other = (DeviceNetworkManagerSkill) obj;
    if (!java.util.Objects.equals(this.parentID, other.parentID))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.parentID);
    return result;
  }
}
