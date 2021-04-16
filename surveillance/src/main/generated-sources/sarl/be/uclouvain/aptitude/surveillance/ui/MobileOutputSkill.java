package be.uclouvain.aptitude.surveillance.ui;

import UDPMessages.UDP_Message_Base;
import be.uclouvain.aptitude.surveillance.ui.UDPSender;
import be.uclouvain.organisation.interactivity.outputDevice.OutputDeviceCapacity;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Skill;
import java.util.TreeMap;
import java.util.UUID;
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
public class MobileOutputSkill extends Skill implements OutputDeviceCapacity {
  private final TreeMap<UUID, String> mobileAddress;
  
  private final UDPSender clientSender = new UDPSender();
  
  public MobileOutputSkill(final TreeMap<UUID, String> m) {
    this.mobileAddress = m;
  }
  
  public void outputConversion(final UUID idSender, final UDP_Message_Base data) {
    this.clientSender.<UDP_Message_Base>SendData(data, this.mobileAddress.get(idSender), 65003);
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
