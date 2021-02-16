package be.uclouvain.launcher;

import UDPMessages.MessageDeserializer;
import UDPMessages.UDP_Message_Base;
import be.uclouvain.aptitudeAgents.CommitSuicide;
import be.uclouvain.aptitudeAgents.Paraddis;
import be.uclouvain.aptitudeAgents.PlatformAgent;
import be.uclouvain.launcher.GUI;
import be.uclouvain.launcher.UDPSender;
import be.uclouvain.organisation.platform.util.PlatformConfig;
import io.sarl.core.AgentTask;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import javafx.application.Application;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class bootAgent extends Paraddis {
  private final String junctionList = "MacqWoolridge";
  
  private final DatagramSocket client = new Function0<DatagramSocket>() {
    @Override
    public DatagramSocket apply() {
      try {
        DatagramSocket _datagramSocket = new DatagramSocket(65000);
        return _datagramSocket;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    }
  }.apply();
  
  private final UDPSender clientSender = new UDPSender();
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    UUID _iD = this.getID();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName((("Boot" + "-") + _iD));
    this.setAgentsReady();
    this.enableInputStream();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      Application.launch(GUI.class, " ");
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.execute(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("Application-Launcher"), _function);
  }
  
  protected void setAgentsReady() {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Setting the agents");
    PlatformConfig PlatformHolonConfig = new PlatformConfig(5, 0);
    PlatformConfig _platformConfig = new PlatformConfig(100, 1);
    PlatformHolonConfig.addSubPlatform(_platformConfig);
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(PlatformAgent.class, PlatformHolonConfig);
  }
  
  protected void MessageAnalysis(final UDP_Message_Base msg, final String adrss) {
    final String _switchValue = msg.method;
    if (_switchValue != null) {
      switch (_switchValue) {
        case "stopSimulation":
          this.disable();
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
          CommitSuicide _commitSuicide = new CommitSuicide();
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_commitSuicide);
          break;
        default:
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Unknown message received by boot agent : " + msg.method));
          break;
      }
    } else {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Unknown message received by boot agent : " + msg.method));
    }
  }
  
  protected synchronized AgentTask enableInputStream() {
    AgentTask _xblockexpression = null;
    {
      MessageDeserializer parser = new MessageDeserializer();
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      final AgentTask Enable_Server = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.task("Enable_Server");
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      final Procedure1<Agent> _function = (Agent it) -> {
        try {
          while (((!this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER().isCanceled(Enable_Server)) && (!this.client.isClosed()))) {
            {
              byte[] buffer = new byte[8192];
              int _length = buffer.length;
              DatagramPacket packet = new DatagramPacket(buffer, _length);
              this.client.receive(packet);
              byte[] _data = packet.getData();
              int _offset = packet.getOffset();
              int _length_1 = packet.getLength();
              String message = new String(_data, _offset, _length_1);
              UDP_Message_Base data = parser.<UDP_Message_Base>Deserialize(message);
              if ((data != null)) {
                this.MessageAnalysis(data, packet.getAddress().getHostAddress());
              } else {
                InputOutput.<String>println(("I received it NULLL" + data));
              }
              packet.setLength(buffer.length);
            }
          }
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      _xblockexpression = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.execute(Enable_Server, _function);
    }
    return _xblockexpression;
  }
  
  protected synchronized boolean disable() {
    boolean _xblockexpression = false;
    {
      this.client.close();
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("Enable_Server"));
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      _xblockexpression = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_3.task("Application-Launcher"));
    }
    return _xblockexpression;
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
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
    bootAgent other = (bootAgent) obj;
    if (!Objects.equals(this.junctionList, other.junctionList))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.junctionList);
    return result;
  }
  
  @SyntheticMember
  public bootAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public bootAgent(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public bootAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
