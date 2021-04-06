package be.uclouvain.organisation.interactivity.inputDevice;

import UDPMessages.MessageDeserializer;
import UDPMessages.UDP_Message_Base;
import be.uclouvain.organisation.interactivity.inputDevice.InputDeviceCapacity;
import io.sarl.core.AgentTask;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Skill;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public abstract class InputSkill extends Skill implements InputDeviceCapacity {
  private final DatagramSocket client;
  
  protected TreeMap<UUID, EventSpace> listenersSpaceIDs;
  
  protected boolean serverRunning = true;
  
  private MessageDeserializer parser = new MessageDeserializer();
  
  public synchronized void EnableInputStream() {
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
            UDP_Message_Base data = this.parser.<UDP_Message_Base>Deserialize(message);
            this.MessageAnalysis(data, packet.getAddress().getHostAddress());
            packet.setLength(buffer.length);
          }
        }
        this.client.close();
      } catch (final Throwable _t) {
        if (_t instanceof SocketException) {
          final SocketException e = (SocketException)_t;
          e.printStackTrace();
        } else if (_t instanceof IOException) {
          final IOException e_1 = (IOException)_t;
          e_1.printStackTrace();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.execute(Enable_Server, _function);
  }
  
  public synchronized void uninstall() {
  }
  
  public synchronized void DisableInputStream() {
    this.client.close();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.cancel(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.task("Enable_Server"));
  }
  
  public abstract void MessageAnalysis(final UDP_Message_Base data, final String IPaddrs);
  
  public InputSkill(final int p) {
    try {
      DatagramSocket _datagramSocket = new DatagramSocket(p);
      this.client = _datagramSocket;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public InputSkill(final int p, final TreeMap<UUID, EventSpace> subWorldSpaceIDs) {
    try {
      DatagramSocket _datagramSocket = new DatagramSocket(p);
      this.client = _datagramSocket;
      this.listenersSpaceIDs = subWorldSpaceIDs;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public InputSkill(final DatagramSocket server) {
    this.client = server;
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
    InputSkill other = (InputSkill) obj;
    if (other.serverRunning != this.serverRunning)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Boolean.hashCode(this.serverRunning);
    return result;
  }
}
