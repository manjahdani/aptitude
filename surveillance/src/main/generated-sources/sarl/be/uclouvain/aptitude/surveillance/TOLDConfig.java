package be.uclouvain.aptitude.surveillance;

import be.uclouvain.organisation.util.HolonicConfig;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AgentContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class TOLDConfig extends HolonicConfig {
  private AgentContext parentContext;
  
  private ArrayList<String> init_tasks;
  
  private final HashMap<TOLDConfig, UUID> subTOLDConfig = new HashMap<TOLDConfig, UUID>();
  
  private String accountablePlatform;
  
  public TOLDConfig(final int l, final String platformName, final AgentContext parent, final ArrayList<String> init_tasks) {
    super(l);
    this.parentContext = parent;
    this.init_tasks = init_tasks;
    this.accountablePlatform = platformName;
  }
  
  @Pure
  public String getAccountablePlatform() {
    return this.accountablePlatform;
  }
  
  @Pure
  public AgentContext getParentContext() {
    return this.parentContext;
  }
  
  @Pure
  public ArrayList<String> getInit_tasks() {
    return this.init_tasks;
  }
  
  public TOLDConfig addSubTOLD(final TOLDConfig wc, final UUID id) {
    this.subTOLDConfig.put(wc, id);
    return wc;
  }
  
  public void removeSubPlatform(final TOLDConfig wc) {
    this.subTOLDConfig.remove(wc);
  }
  
  @Pure
  public HashMap<TOLDConfig, UUID> getSubTOLDConfig() {
    return this.subTOLDConfig;
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
    TOLDConfig other = (TOLDConfig) obj;
    if (!Objects.equals(this.accountablePlatform, other.accountablePlatform))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.accountablePlatform);
    return result;
  }
}
