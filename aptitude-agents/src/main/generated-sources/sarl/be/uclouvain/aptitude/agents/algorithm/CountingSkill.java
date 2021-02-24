package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.ObserverCapacity;
import be.uclouvain.aptitude.agents.algorithm.util.BBOX;
import be.uclouvain.aptitude.agents.algorithm.util.BBoxes2D;
import be.uclouvain.aptitude.agents.algorithm.util.countingLine;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.arakhne.afc.math.geometry.d2.d.Vector2d;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class CountingSkill extends Skill implements ObserverCapacity {
  private final TreeMap<String, countingLine> countingLines = new TreeMap<String, countingLine>();
  
  public void install() {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Installing the skill");
    countingLine _countingLine = new countingLine(1235, 700, 309, 664, 1);
    this.countingLines.put("A", _countingLine);
    countingLine _countingLine_1 = new countingLine(1477, 324, 1676, 310, (-1));
    this.countingLines.put("B", _countingLine_1);
  }
  
  public void Signal2Perception(final ArrayList<BBoxes2D> boundingBoxes) {
    for (final BBoxes2D bb : boundingBoxes) {
      Set<String> _keySet = this.countingLines.keySet();
      for (final String counting_line : _keySet) {
        if ((this.has_crossed_counting_line(bb.getBBOX(), this.countingLines.get(counting_line)) && 
          (!this.countingLines.get(counting_line).ObjectEncountered(bb.getGlobalID())))) {
          final int orientation = this.countingLines.get(counting_line).Orientation(bb.getDirection());
          Vector2d bbDir = bb.getDirection();
          Vector2d nDir = this.countingLines.get(counting_line).getNormale();
          if ((orientation != 0)) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
            int _frame = bb.getFrame();
            int _IncrementCounts = this.countingLines.get(counting_line).IncrementCounts();
            String _plus = ((((((("Counted at frame " + Integer.valueOf(_frame)) + " and ") + counting_line) + " is : ") + Integer.valueOf(_IncrementCounts)) + " v=") + "(");
            double _x = bbDir.getX();
            String _plus_1 = ((_plus + Double.valueOf(_x)) + " , ");
            double _y = bbDir.getY();
            String _plus_2 = (((_plus_1 + Double.valueOf(_y)) + "And n=") + "(");
            double _x_1 = nDir.getX();
            String _plus_3 = ((_plus_2 + Double.valueOf(_x_1)) + " , ");
            double _y_1 = nDir.getY();
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((((_plus_3 + Double.valueOf(_y_1)) + ")") + " ori:") + Integer.valueOf(orientation)));
          } else {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
            int _globalID = bb.getGlobalID();
            int _frame_1 = bb.getFrame();
            double _x_2 = bbDir.getX();
            double _y_2 = bbDir.getY();
            double _x_3 = nDir.getX();
            double _y_3 = nDir.getY();
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(((((((((((((((((((("ID :" + Integer.valueOf(_globalID)) + " Counted Horizontally at the frame ") + Integer.valueOf(_frame_1)) + " at : ") + counting_line) + " v=") + "(") + Double.valueOf(_x_2)) + " , ") + Double.valueOf(_y_2)) + ")") + " and n=") + "(") + Double.valueOf(_x_3)) + ",") + Double.valueOf(_y_3)) + ")") + " orientation:") + Integer.valueOf(orientation)));
          }
        }
      }
    }
  }
  
  @Pure
  public boolean has_crossed_counting_line(final BBOX b, final countingLine cl) {
    double _x = b.getX();
    double _y = b.getY();
    Point2d _point2d = new Point2d(_x, _y);
    double _x_1 = b.getX();
    double _w = b.getW();
    double _y_1 = b.getY();
    Point2d _point2d_1 = new Point2d((_x_1 + _w), _y_1);
    final ArrayList<Point2d> bbox_line1 = CollectionLiterals.<Point2d>newArrayList(_point2d, _point2d_1);
    double _x_2 = b.getX();
    double _w_1 = b.getW();
    double _y_2 = b.getY();
    Point2d _point2d_2 = new Point2d((_x_2 + _w_1), _y_2);
    double _x_3 = b.getX();
    double _w_2 = b.getW();
    double _y_3 = b.getY();
    double _h = b.getH();
    Point2d _point2d_3 = new Point2d((_x_3 + _w_2), (_y_3 + _h));
    final ArrayList<Point2d> bbox_line2 = CollectionLiterals.<Point2d>newArrayList(_point2d_2, _point2d_3);
    double _x_4 = b.getX();
    double _y_4 = b.getY();
    Point2d _point2d_4 = new Point2d(_x_4, _y_4);
    double _x_5 = b.getX();
    double _y_5 = b.getY();
    double _h_1 = b.getH();
    Point2d _point2d_5 = new Point2d(_x_5, (_y_5 + _h_1));
    final ArrayList<Point2d> bbox_line3 = CollectionLiterals.<Point2d>newArrayList(_point2d_4, _point2d_5);
    double _x_6 = b.getX();
    double _y_6 = b.getY();
    double _h_2 = b.getH();
    Point2d _point2d_6 = new Point2d(_x_6, (_y_6 + _h_2));
    double _x_7 = b.getX();
    double _w_3 = b.getW();
    double _y_7 = b.getY();
    double _h_3 = b.getH();
    Point2d _point2d_7 = new Point2d((_x_7 + _w_3), (_y_7 + _h_3));
    final ArrayList<Point2d> bbox_line4 = CollectionLiterals.<Point2d>newArrayList(_point2d_6, _point2d_7);
    if ((((this.line_segments_intersect(bbox_line1, cl.getLine()) || this.line_segments_intersect(bbox_line2, cl.getLine())) || 
      this.line_segments_intersect(bbox_line3, cl.getLine())) || this.line_segments_intersect(bbox_line4, cl.getLine()))) {
      return true;
    }
    return false;
  }
  
  @Pure
  public boolean line_segments_intersect(final ArrayList<Point2d> line1, final ArrayList<Point2d> line2) {
    Point2d p1 = line1.get(0);
    Point2d q1 = line1.get(1);
    Point2d p2 = line2.get(0);
    Point2d q2 = line2.get(1);
    int o1 = this.get_orientation(p1, q1, p2);
    int o2 = this.get_orientation(p1, q1, q2);
    int o3 = this.get_orientation(p2, q2, p1);
    int o4 = this.get_orientation(p2, q2, q1);
    if (((o1 != o2) && (o3 != o4))) {
      return true;
    }
    if (((o1 == 0) && this.is_on_segment(p1, p2, q1))) {
      return true;
    }
    if (((o2 == 0) && this.is_on_segment(p1, q2, q1))) {
      return true;
    }
    if (((o3 == 0) && this.is_on_segment(p2, p1, q2))) {
      return true;
    }
    if (((o4 == 0) && this.is_on_segment(p2, q1, q2))) {
      return true;
    }
    return false;
  }
  
  @Pure
  public int get_orientation(final Point2d p, final Point2d q, final Point2d r) {
    double _y = q.getY();
    double _y_1 = p.getY();
    double _x = r.getX();
    double _x_1 = q.getX();
    double _x_2 = q.getX();
    double _x_3 = p.getX();
    double _y_2 = r.getY();
    double _y_3 = q.getY();
    final double a = (((_y - _y_1) * (_x - _x_1)) - ((_x_2 - _x_3) * (_y_2 - _y_3)));
    if ((a == 0)) {
      return 0;
    } else {
      if ((a > 0)) {
        return 1;
      }
    }
    return 2;
  }
  
  @Pure
  public boolean is_on_segment(final Point2d p, final Point2d q, final Point2d r) {
    if (((((q.getX() <= Math.max(p.getX(), r.getX())) && (q.getX() >= Math.min(p.getX(), r.getX()))) && (q.getY() <= Math.max(p.getY(), r.getY()))) && 
      (q.getY() >= Math.min(p.getY(), r.getY())))) {
      return true;
    }
    return false;
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  @SyntheticMember
  public CountingSkill() {
    super();
  }
  
  @SyntheticMember
  public CountingSkill(final Agent agent) {
    super(agent);
  }
}
