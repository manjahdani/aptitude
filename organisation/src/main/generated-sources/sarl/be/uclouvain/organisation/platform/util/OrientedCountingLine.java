package be.uclouvain.organisation.platform.util;

import be.uclouvain.organisation.platform.util.CountingLine;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.arakhne.afc.math.geometry.d2.d.Vector2d;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The class represents a crossable counting line with a direction.
 * 
 * @param : lx : X coordinate of the left point A
 * @param : ly : Y coordinate of the left point A
 * @param : rX : X coordinate of the right point B
 * @param : rY : Y coordinate of the right point B
 * @param : numberOfClass : The number of object's classification
 * @param : d  : direction  @FIXME not clear
 * 
 * @author $manjahdani$
 * @version $0.0.1$
 * @date $31/03/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $surveillance$
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class OrientedCountingLine extends CountingLine {
  private final double ANGLE_ALONGSIDE = ((Math.PI * 80) / 180);
  
  private final double ANGLE_OPPOSITE = ((Math.PI * 120) / 180);
  
  public int direction;
  
  private final Vector2d normale;
  
  public OrientedCountingLine(final int lX, final int lY, final int uX, final int uY, final int numberOfClass, final int d) {
    super(lX, lY, uX, uY, numberOfClass);
    this.direction = d;
    Point2d _point2d = new Point2d(lX, lY);
    Point2d _point2d_1 = new Point2d(uX, uY);
    this.normale = this.get_normale(_point2d, _point2d_1, this.direction);
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
    OrientedCountingLine other = (OrientedCountingLine) obj;
    if (Double.doubleToLongBits(other.ANGLE_ALONGSIDE) != Double.doubleToLongBits(this.ANGLE_ALONGSIDE))
      return false;
    if (Double.doubleToLongBits(other.ANGLE_OPPOSITE) != Double.doubleToLongBits(this.ANGLE_OPPOSITE))
      return false;
    if (other.direction != this.direction)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.ANGLE_ALONGSIDE);
    result = prime * result + Double.hashCode(this.ANGLE_OPPOSITE);
    result = prime * result + Integer.hashCode(this.direction);
    return result;
  }
}
