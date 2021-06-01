package be.uclouvain.organisation.platform.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO: comment
 * 
 * @author $Author: manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class PlatformConfig {
  private final LinkedList<PlatformConfig> subPlatformConfig = new LinkedList<PlatformConfig>();
  
  private final int LEVEL;
  
  private final AtomicInteger CPU_MAX_USAGE = new AtomicInteger();
  
  private final String Location;
  
  /**
   * Constructor
   * 
   * @param	lvl	The hierarchical level of the Platform(0 is the highest level)
   * @param	shp	The shape of the Platform
   */
  public PlatformConfig(final int entity_max, final int lvl, final String location) {
    this.CPU_MAX_USAGE.set(entity_max);
    this.LEVEL = lvl;
    this.Location = location;
  }
  
  public void addSubPlatform(final PlatformConfig wc) {
    this.subPlatformConfig.add(wc);
  }
  
  public void removeSubPlatform(final PlatformConfig wc) {
    this.subPlatformConfig.remove(wc);
  }
  
  @Pure
  public String getLocation() {
    return this.Location;
  }
  
  @Pure
  public int getLevel() {
    return this.LEVEL;
  }
  
  @Pure
  public List<PlatformConfig> getSubPlatformConfig() {
    return Collections.<PlatformConfig>synchronizedList(this.subPlatformConfig);
  }
  
  @Pure
  public int getENTITY_MAX() {
    return this.CPU_MAX_USAGE.get();
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
    PlatformConfig other = (PlatformConfig) obj;
    if (other.LEVEL != this.LEVEL)
      return false;
    if (!Objects.equals(this.Location, other.Location))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.LEVEL);
    result = prime * result + Objects.hashCode(this.Location);
    return result;
  }
}
