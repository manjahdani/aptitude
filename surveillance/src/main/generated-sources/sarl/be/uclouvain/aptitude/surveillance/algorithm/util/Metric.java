package be.uclouvain.aptitude.surveillance.algorithm.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class Metric {
  private String scoreName;
  
  private String gtFilePath;
  
  private String[] params;
  
  public Metric(final String score, final String gtPatch, final String[] p) {
    this.gtFilePath = gtPatch;
    this.scoreName = score;
    this.params = p;
  }
  
  @Pure
  public String getScoreName() {
    return this.scoreName;
  }
  
  @Pure
  public String getGtFilePatch() {
    return this.gtFilePath;
  }
  
  @Pure
  public String[] getParams() {
    return this.params;
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
    Metric other = (Metric) obj;
    if (!Objects.equals(this.scoreName, other.scoreName))
      return false;
    if (!Objects.equals(this.gtFilePath, other.gtFilePath))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.scoreName);
    result = prime * result + Objects.hashCode(this.gtFilePath);
    return result;
  }
}
