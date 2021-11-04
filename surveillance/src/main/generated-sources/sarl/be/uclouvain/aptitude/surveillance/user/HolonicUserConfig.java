package be.uclouvain.aptitude.surveillance.user;

import be.uclouvain.organisation.util.HolonicConfig;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class HolonicUserConfig extends HolonicConfig {
  private String name;
  
  private LinkedList<String> initialPlatforms = new LinkedList<String>();
  
  public HolonicUserConfig(final int lvl, final String id) {
    super(lvl);
    this.name = id;
  }
  
  public HolonicUserConfig(final int lvl, final String id, final Collection<String> platformsList) {
    super(lvl);
    this.name = id;
    this.initialPlatforms.addAll(platformsList);
  }
  
  @Pure
  public LinkedList<String> getInitialPlatforms() {
    return this.initialPlatforms;
  }
  
  @Pure
  public String getName() {
    return this.name;
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
    HolonicUserConfig other = (HolonicUserConfig) obj;
    if (!Objects.equals(this.name, other.name))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.name);
    return result;
  }
}
