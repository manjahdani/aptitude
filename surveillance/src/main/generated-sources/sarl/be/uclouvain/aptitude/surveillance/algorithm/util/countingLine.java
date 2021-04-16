package be.uclouvain.aptitude.surveillance.algorithm.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.arakhne.afc.math.geometry.d2.d.Vector2d;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
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
@SarlElementType(10)
@SuppressWarnings("all")
public class countingLine {
  public int direction;
  
  public double lowerX;
  
  public double upperX;
  
  public double lowerY;
  
  public double upperY;
  
  public final AtomicInteger counts = new AtomicInteger(0);
  
  public final ArrayList<Integer> ObjEncountered = new ArrayList<Integer>();
  
  private final Vector2d normale;
  
  private final double ANGLE_ALONGSIDE = ((Math.PI * 80) / 180);
  
  private final double ANGLE_OPPOSITE = ((Math.PI * 120) / 180);
  
  public countingLine(final double lX, final double lY, final double uX, final double uY, final int d) {
    this.lowerX = lX;
    this.upperX = uX;
    this.lowerY = lY;
    this.upperY = uY;
    this.direction = d;
    Point2d _point2d = new Point2d(lX, lY);
    Point2d _point2d_1 = new Point2d(uX, uY);
    this.normale = this.get_normale(_point2d, _point2d_1, this.direction);
  }
  
  @Pure
  public boolean isInWorldLimit(final double x, final double y, final double z) {
    if (((x > this.lowerX) && (x < this.upperX))) {
      return false;
    }
    if (((y > this.lowerY) && (y < this.upperY))) {
      return false;
    }
    return true;
  }
  
  public int CopyWorld(final countingLine w) {
    int _xblockexpression = (int) 0;
    {
      this.lowerX = w.lowerX;
      this.upperX = w.upperX;
      this.lowerY = w.lowerY;
      this.upperY = w.upperY;
      _xblockexpression = this.direction = w.direction;
    }
    return _xblockexpression;
  }
  
  @Pure
  public AtomicInteger getCounts() {
    return this.counts;
  }
  
  @Pure
  public int IncrementCounts() {
    return this.counts.getAndIncrement();
  }
  
  @Pure
  public ArrayList<Point2d> getLine() {
    Point2d _point2d = new Point2d(this.lowerX, this.lowerY);
    Point2d _point2d_1 = new Point2d(this.upperX, this.upperY);
    return CollectionLiterals.<Point2d>newArrayList(_point2d, _point2d_1);
  }
  
  public boolean ObjectEncountered(final int ID) {
    boolean _contains = this.ObjEncountered.contains(Integer.valueOf(ID));
    if (_contains) {
      return true;
    } else {
      this.ObjEncountered.add(Integer.valueOf(ID));
      return false;
    }
  }
  
  @Pure
  public Vector2d getNormale() {
    return this.normale;
  }
  
  @Pure
  public Vector2d get_normale(final Point2d P1, final Point2d P2, final int flag) {
    Point2d A = P1;
    Point2d B = P2;
    double _x = P1.getX();
    double _x_1 = P2.getX();
    if ((_x > _x_1)) {
      A = P2;
      B = P1;
    }
    double _y = B.getY();
    double _y_1 = A.getY();
    double _x_2 = B.getX();
    double _x_3 = A.getX();
    double m = ((_y - _y_1) / (_x_2 - _x_3));
    double _sqrt = Math.sqrt(((m * m) + 1));
    double norm_n = (_sqrt * flag);
    return new Vector2d(((-m) / norm_n), (1 / norm_n));
  }
  
  @Pure
  public int Orientation(final Vector2d v) {
    double angle = this.normale.angle(v);
    if ((angle < this.ANGLE_ALONGSIDE)) {
      return 1;
    }
    if ((angle > this.ANGLE_OPPOSITE)) {
      return (-1);
    }
    return 0;
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
    countingLine other = (countingLine) obj;
    if (other.direction != this.direction)
      return false;
    if (Double.doubleToLongBits(other.lowerX) != Double.doubleToLongBits(this.lowerX))
      return false;
    if (Double.doubleToLongBits(other.upperX) != Double.doubleToLongBits(this.upperX))
      return false;
    if (Double.doubleToLongBits(other.lowerY) != Double.doubleToLongBits(this.lowerY))
      return false;
    if (Double.doubleToLongBits(other.upperY) != Double.doubleToLongBits(this.upperY))
      return false;
    if (Double.doubleToLongBits(other.ANGLE_ALONGSIDE) != Double.doubleToLongBits(this.ANGLE_ALONGSIDE))
      return false;
    if (Double.doubleToLongBits(other.ANGLE_OPPOSITE) != Double.doubleToLongBits(this.ANGLE_OPPOSITE))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.direction);
    result = prime * result + Double.hashCode(this.lowerX);
    result = prime * result + Double.hashCode(this.upperX);
    result = prime * result + Double.hashCode(this.lowerY);
    result = prime * result + Double.hashCode(this.upperY);
    result = prime * result + Double.hashCode(this.ANGLE_ALONGSIDE);
    result = prime * result + Double.hashCode(this.ANGLE_OPPOSITE);
    return result;
  }
}
