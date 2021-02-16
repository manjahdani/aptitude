package be.uclouvain.aptitudeAgents;

import UDPMessages.UDP_Message_Base;
import be.uclouvain.aptitudeAgents.UDPSender;
import be.uclouvain.organisation.interactivity.outputDevice.OutputDeviceCapacity;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
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
public class MobileOutputSkill extends Skill implements OutputDeviceCapacity {
  private final TreeMap<UUID, String> mobileAddress;
  
  private final UDPSender clientSender = new UDPSender();
  
  public MobileOutputSkill(final TreeMap<UUID, String> m) {
    this.mobileAddress = m;
  }
  
  public void outputConversion(final UUID idSender, final UDP_Message_Base data) {
    this.clientSender.<UDP_Message_Base>SendData(data, this.mobileAddress.get(idSender), 65003);
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
