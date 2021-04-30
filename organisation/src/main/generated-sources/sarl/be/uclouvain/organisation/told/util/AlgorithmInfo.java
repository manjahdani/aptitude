package be.uclouvain.organisation.told.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO : comment
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class AlgorithmInfo implements Cloneable {
  private final String name;
  
  private String belief;
  
  private final String task;
  
  private int level;
  
  public AlgorithmInfo(final String n, final String b, final String t, final Integer l) {
    this.name = n;
    this.belief = b;
    this.task = t;
    this.level = ((l) == null ? 0 : (l).intValue());
  }
  
  public AlgorithmInfo(final String n, final String t) {
    this.name = n;
    this.belief = null;
    this.task = t;
    this.level = (-1);
  }
  
  public AlgorithmInfo(final String n, final String b, final String t) {
    this.name = n;
    this.belief = b;
    this.task = t;
    this.level = 0;
  }
  
  public void setLevel(final int a) {
    this.level = a;
  }
  
  public AlgorithmInfo IncrementLevelAndGet() {
    this.level = (this.level + 1);
    return this;
  }
  
  @Pure
  public int getLevel() {
    return this.level;
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
    if (other.level != this.level)
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
    result = prime * result + Integer.hashCode(this.level);
    return result;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public AlgorithmInfo clone() {
    try {
      return (AlgorithmInfo) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }
}