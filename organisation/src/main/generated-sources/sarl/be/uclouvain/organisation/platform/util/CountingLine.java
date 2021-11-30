package be.uclouvain.organisation.platform.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The class represents a crossable counting line.
 * 
 * @param : lx : X coordinate of the left point A
 * @param : ly : Y coordinate of the left point A
 * @param : rX : X coordinate of the right point B
 * @param : rY : Y coordinate of the right point B
 * @param : numberOfClass : The number of object's classification
 * 
 * @author manjahdani
 * @version 0.0.2
 * @date $17/06/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class CountingLine {
  public int X_A;
  
  public int Y_A;
  
  public int X_B;
  
  public int Y_B;
  
  public final ArrayList<Integer> crossedObjects = new ArrayList<Integer>();
  
  public final TreeMap<Integer, AtomicInteger> counts = new TreeMap<Integer, AtomicInteger>();
  
  public CountingLine(final int lX, final int lY, final int rX, final int rY, final int numberOfClass) {
    this.X_A = lX;
    this.Y_A = lY;
    this.X_B = rX;
    this.Y_B = rY;
    for (int i = 0; (i < numberOfClass); i++) {
      AtomicInteger _atomicInteger = new AtomicInteger(0);
      this.counts.put(Integer.valueOf(i), _atomicInteger);
    }
  }
  
  @Pure
  public TreeMap<Integer, AtomicInteger> getCounts() {
    return this.counts;
  }
  
  @Pure
  public int incrementCount(final int classID) {
    return this.counts.get(Integer.valueOf(classID)).getAndIncrement();
  }
  
  @Pure
  public ArrayList<Point2d> getLine() {
    Point2d _point2d = new Point2d(this.X_A, this.Y_A);
    Point2d _point2d_1 = new Point2d(this.X_A, this.Y_A);
    return CollectionLiterals.<Point2d>newArrayList(_point2d, _point2d_1);
  }
  
  @Pure
  public boolean hasCrossed(final int ID) {
    boolean _contains = this.crossedObjects.contains(Integer.valueOf(ID));
    if (_contains) {
      return true;
    } else {
      this.crossedObjects.add(Integer.valueOf(ID));
      return false;
    }
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
    CountingLine other = (CountingLine) obj;
    if (other.X_A != this.X_A)
      return false;
    if (other.Y_A != this.Y_A)
      return false;
    if (other.X_B != this.X_B)
      return false;
    if (other.Y_B != this.Y_B)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.X_A);
    result = prime * result + Integer.hashCode(this.Y_A);
    result = prime * result + Integer.hashCode(this.X_B);
    result = prime * result + Integer.hashCode(this.Y_B);
    return result;
  }
}
