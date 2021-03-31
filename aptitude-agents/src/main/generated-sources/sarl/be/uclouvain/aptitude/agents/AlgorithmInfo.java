/**
 * @Name       : TOLDAgent
 * @Project    : APTITUDE
 * @Author     : Dani Manjah
 * @Version    : V.0.1
 * @Date       : 22/03/2021
 */
package be.uclouvain.aptitude.agents;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class AlgorithmInfo {
  private final String name;
  
  private final String belief;
  
  private final String task;
  
  public AlgorithmInfo(final String n, final String b, final String t) {
    this.name = n;
    this.belief = b;
    this.task = b;
  }
  
  @Pure
  public String getName() {
    return this.name;
  }
  
  @Pure
  public String getBelief() {
    return this.belief;
  }
  
  @Pure
  public String getTask() {
    return this.task;
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
    AlgorithmInfo other = (AlgorithmInfo) obj;
    if (!Objects.equals(this.name, other.name))
      return false;
    if (!Objects.equals(this.belief, other.belief))
      return false;
    if (!Objects.equals(this.task, other.task))
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
    result = prime * result + Objects.hashCode(this.belief);
    result = prime * result + Objects.hashCode(this.task);
    return result;
  }
}
