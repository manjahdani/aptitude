package be.uclouvain.aptitudeAgents.util;

import be.uclouvain.aptitudeAgents.util.BBOX;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.arakhne.afc.math.geometry.d2.d.Vector2d;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class BBoxes2D {
  private final BBOX bbox;
  
  private double conf;
  
  private int frame;
  
  private final int globalID;
  
  private int classeID;
  
  private final ArrayList<String> lines_crossed = new ArrayList<String>();
  
  private final Point2d oldPos = new Point2d(0, 0);
  
  private final Point2d curPos = new Point2d(0, 0);
  
  private final Vector2d direction = new Vector2d(0, 0);
  
  public BBoxes2D(final BBOX bb, final double conf, final int globalID, final int classeID, final int frame) {
    this.bbox = bb;
    this.conf = conf;
    this.frame = frame;
    this.globalID = globalID;
    this.classeID = classeID;
  }
  
  @Pure
  public double getConf() {
    return this.conf;
  }
  
  @Pure
  public int getFrame() {
    return this.frame;
  }
  
  @Pure
  public BBOX getBBOX() {
    return this.bbox;
  }
  
  @Pure
  public int getGlobalID() {
    return this.globalID;
  }
  
  @Pure
  public int getClasseID() {
    return this.classeID;
  }
  
  @Pure
  public ArrayList<String> getLines_crossed() {
    return this.lines_crossed;
  }
  
  @Pure
  public boolean hasCrossedLine(final String label) {
    boolean _contains = this.lines_crossed.contains(label);
    if (_contains) {
      return true;
    } else {
      this.lines_crossed.add(label);
      return false;
    }
  }
  
  public double update(final double X, final double Y, final double W, final double H, final int classID, final int frameNumber, final double conf) {
    double _xblockexpression = (double) 0;
    {
      Point2d newPos = new Point2d(X, Y);
      double _distance = newPos.getDistance(this.curPos);
      if ((_distance > 0.01)) {
        this.bbox.update(X, Y, W, H);
        double _x = newPos.getX();
        double _x_1 = this.curPos.getX();
        double _y = newPos.getY();
        double _y_1 = this.curPos.getY();
        Vector2d v_dir = new Vector2d((_x - _x_1), (_y - _y_1));
        this.curPos.set(newPos);
        v_dir.normalize();
        this.direction.set(v_dir);
      }
      this.classeID = classID;
      this.frame = frameNumber;
      _xblockexpression = this.conf = conf;
    }
    return _xblockexpression;
  }
  
  @Pure
  public Vector2d getDirection() {
    return this.direction;
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
    BBoxes2D other = (BBoxes2D) obj;
    if (Double.doubleToLongBits(other.conf) != Double.doubleToLongBits(this.conf))
      return false;
    if (other.frame != this.frame)
      return false;
    if (other.globalID != this.globalID)
      return false;
    if (other.classeID != this.classeID)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.conf);
    result = prime * result + Integer.hashCode(this.frame);
    result = prime * result + Integer.hashCode(this.globalID);
    result = prime * result + Integer.hashCode(this.classeID);
    return result;
  }
}
