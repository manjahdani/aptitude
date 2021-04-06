package be.uclouvain.aptitude.surveillance.algorithm.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class BBOX {
  private double x;
  
  private double y;
  
  private double w;
  
  private double h;
  
  public BBOX(final double x, final double y, final double w, final double h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  @Pure
  public double getX() {
    return this.x;
  }
  
  @Pure
  public double getY() {
    return this.y;
  }
  
  @Pure
  public double getW() {
    return this.w;
  }
  
  @Pure
  public double getH() {
    return this.h;
  }
  
  public double update(final double X, final double Y, final double W, final double H) {
    double _xblockexpression = (double) 0;
    {
      this.x = X;
      this.y = Y;
      this.w = W;
      _xblockexpression = this.h = H;
    }
    return _xblockexpression;
  }
  
  @Pure
  public Point2d getCentroid() {
    return new Point2d((((this.x + this.x) + this.w) / 2), (((this.y + this.y) + this.h) / 2));
  }
  
  @Pure
  public double getArea() {
    return (this.w * this.h);
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
    BBOX other = (BBOX) obj;
    if (Double.doubleToLongBits(other.x) != Double.doubleToLongBits(this.x))
      return false;
    if (Double.doubleToLongBits(other.y) != Double.doubleToLongBits(this.y))
      return false;
    if (Double.doubleToLongBits(other.w) != Double.doubleToLongBits(this.w))
      return false;
    if (Double.doubleToLongBits(other.h) != Double.doubleToLongBits(this.h))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.x);
    result = prime * result + Double.hashCode(this.y);
    result = prime * result + Double.hashCode(this.w);
    result = prime * result + Double.hashCode(this.h);
    return result;
  }
}
