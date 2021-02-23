package be.uclouvain.aptitude.agents.algorithm;

import be.uclouvain.aptitude.agents.algorithm.BBoxes2DResult;
import be.uclouvain.aptitude.agents.algorithm.BBoxes2DTrackResult;
import be.uclouvain.aptitude.agents.algorithm.Detection;
import be.uclouvain.aptitude.agents.algorithm.DetectionImpl;
import be.uclouvain.aptitude.agents.algorithm.PartnerDetectionFound;
import be.uclouvain.aptitude.agents.algorithm.PartnerTrackingFound;
import be.uclouvain.aptitude.agents.algorithm.Tracking;
import be.uclouvain.aptitude.agents.algorithm.TrackingImpl;
import be.uclouvain.aptitude.agents.algorithm.util.BBOX;
import be.uclouvain.aptitude.agents.algorithm.util.BBoxes2D;
import be.uclouvain.aptitude.agents.algorithm.util.countingLine;
import be.uclouvain.organisation.platform.ObserverCapacity;
import be.uclouvain.organisation.told.LeavePlatform;
import io.sarl.core.Behaviors;
import io.sarl.core.Destroy;
import io.sarl.core.ExternalContextAccess;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.core.OpenEventSpace;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.arakhne.afc.math.geometry.d2.d.Point2d;
import org.arakhne.afc.math.geometry.d2.d.Vector2d;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author manjah
 */
@SarlSpecification("0.11")
@SarlElementType(21)
@SuppressWarnings("all")
public class ObserverRole extends Behavior {
  protected OpenEventSpace PlatformSpace;
  
  protected UUID PlatformID;
  
  protected UUID ExpertID;
  
  private String configPathYOLO = "F:/aptitude/aptitude-agents/src/main/resources/config/test-YOLO.json";
  
  private String configPathTinyYOLO = "F:/aptitude/aptitude-agents/src/main/resources/config/test-TinyYOLO.json";
  
  private String configPathSORT = "F:/aptitude/aptitude-agents/src/main/resources/config/test-SORT.json";
  
  private String configPathDeepSORT = "F:/aptitude/aptitude-agents/src/main/resources/config/test-DeepSORT.json";
  
  private String partnerDetectionName;
  
  private String partnerTrackingName;
  
  private long start;
  
  private final TreeMap<String, countingLine> countingLines = new TreeMap<String, countingLine>();
  
  private final TreeMap<Integer, BBoxes2D> ObjectPresentInframe = new TreeMap<Integer, BBoxes2D>();
  
  private final ArrayList<BBoxes2D> ObjectToBeAnalyzed = new ArrayList<BBoxes2D>();
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Observer Role started");
      DetectionImpl _detectionImpl = new DetectionImpl();
      this.<DetectionImpl>setSkill(_detectionImpl);
      TrackingImpl _trackingImpl = new TrackingImpl();
      this.<TrackingImpl>setSkill(_trackingImpl);
      countingLine _countingLine = new countingLine(1235, 700, 309, 664, 1);
      this.countingLines.put("A", _countingLine);
      countingLine _countingLine_1 = new countingLine(1477, 324, 1676, 310, (-1));
      this.countingLines.put("B", _countingLine_1);
      JSONParser parser = new JSONParser();
      String configPathDetector = this.configPathYOLO;
      FileReader _fileReader = new FileReader(configPathDetector);
      Object _parse = parser.parse(_fileReader);
      JSONObject jsonDetector = ((JSONObject) _parse);
      Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER.requestDetector(jsonDetector);
      String configPathTracker = this.configPathSORT;
      FileReader _fileReader_1 = new FileReader(configPathTracker);
      Object _parse_1 = parser.parse(_fileReader_1);
      JSONObject jsonTracker = ((JSONObject) _parse_1);
      Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER.requestTracker(jsonTracker);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$LeavePlatform$1(final LeavePlatform occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("ObserverLeaving");
    Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER.sendAction(4);
    ExternalContextAccess _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER.leave(this.PlatformID);
  }
  
  private void $behaviorUnit$PartnerDetectionFound$2(final PartnerDetectionFound occurrence) {
    try {
      this.partnerDetectionName = occurrence.partnerName;
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Detection partner found: " + this.partnerDetectionName));
      Thread.sleep(2000);
      Detection _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER();
      _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER.sendAction(1);
      this.start = System.currentTimeMillis();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$PartnerTrackingFound$3(final PartnerTrackingFound occurrence) {
    this.partnerTrackingName = occurrence.partnerName;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Tracking Partner found: " + this.partnerTrackingName));
  }
  
  private void $behaviorUnit$BBoxes2DResult$4(final BBoxes2DResult occurrence) {
    Tracking _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER = this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER();
    _$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER.getTrack(occurrence.bboxes2D);
  }
  
  private void $behaviorUnit$BBoxes2DTrackResult$5(final BBoxes2DTrackResult occurrence) {
    int _dimWidth = occurrence.bboxes2DTrack.getDimWidth();
    double ratio_width = (1920.0 / _dimWidth);
    int _dimHeight = occurrence.bboxes2DTrack.getDimHeight();
    double ratio_height = (1080.0 / _dimHeight);
    int frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
    this.ObjectToBeAnalyzed.clear();
    for (int i = 0; (i < occurrence.bboxes2DTrack.getNumberObjects()); i++) {
      {
        int _get = occurrence.bboxes2DTrack.getBboxes()[(4 * i)];
        double X = (_get * ratio_width);
        int _get_1 = occurrence.bboxes2DTrack.getBboxes()[((4 * i) + 1)];
        double Y = (_get_1 * ratio_height);
        int _get_2 = occurrence.bboxes2DTrack.getBboxes()[((4 * i) + 2)];
        double W = (_get_2 * ratio_width);
        int _get_3 = occurrence.bboxes2DTrack.getBboxes()[((4 * i) + 3)];
        double H = (_get_3 * ratio_height);
        int classID = occurrence.bboxes2DTrack.getClassIDs()[i];
        int globalID = occurrence.bboxes2DTrack.getGlobalIDs()[i];
        double conf = occurrence.bboxes2DTrack.getDetConfs()[i];
        boolean _containsKey = this.ObjectPresentInframe.containsKey(Integer.valueOf(globalID));
        if (_containsKey) {
          this.ObjectPresentInframe.get(Integer.valueOf(globalID)).update(X, Y, W, H, classID, frameNumber, conf);
        } else {
          BBOX _bBOX = new BBOX(X, Y, W, H);
          BBoxes2D _bBoxes2D = new BBoxes2D(_bBOX, conf, globalID, classID, frameNumber);
          this.ObjectPresentInframe.put(Integer.valueOf(globalID), _bBoxes2D);
        }
        this.ObjectToBeAnalyzed.add(this.ObjectPresentInframe.get(Integer.valueOf(globalID)));
      }
    }
    ArrayList<BBoxes2D> _arrayList = new ArrayList<BBoxes2D>(this.ObjectToBeAnalyzed);
    this.attemp_count(_arrayList);
    boolean _isLastFrame = occurrence.bboxes2DTrack.isLastFrame();
    if (_isLastFrame) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("That was it! ");
      long _currentTimeMillis = System.currentTimeMillis();
      final long totalTime = ((_currentTimeMillis - this.start) / 1000);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _string = Long.valueOf(totalTime).toString();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((("It took " + _string) + " seconds"));
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      int _frameNumber = occurrence.bboxes2DTrack.getFrameNumber();
      String _string_1 = Long.valueOf((_frameNumber / totalTime)).toString();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Average FPS : " + _string_1));
    }
  }
  
  public void attemp_count(final ArrayList<BBoxes2D> boundingBoxes) {
    for (final BBoxes2D bb : boundingBoxes) {
      Set<String> _keySet = this.countingLines.keySet();
      for (final String counting_line : _keySet) {
        if ((this.has_crossed_counting_line(bb.getBBOX(), this.countingLines.get(counting_line)) && (!this.countingLines.get(counting_line).ObjectEncountered(bb.getGlobalID())))) {
          final int orientation = this.countingLines.get(counting_line).Orientation(bb.getDirection());
          Vector2d bbDir = bb.getDirection();
          Vector2d nDir = this.countingLines.get(counting_line).getNormale();
          if ((orientation != 0)) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
            int _frame = bb.getFrame();
            int _IncrementCounts = this.countingLines.get(counting_line).IncrementCounts();
            String _plus = ((((((("Counted at frame " + Integer.valueOf(_frame)) + " and ") + counting_line) + " is : ") + Integer.valueOf(_IncrementCounts)) + 
              " v=") + "(");
            double _x = bbDir.getX();
            String _plus_1 = ((_plus + Double.valueOf(_x)) + " , ");
            double _y = bbDir.getY();
            String _plus_2 = (((_plus_1 + Double.valueOf(_y)) + 
              "And n=") + "(");
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
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(((((((((((((((((((("ID :" + Integer.valueOf(_globalID)) + " Counted Horizontally at the frame ") + Integer.valueOf(_frame_1)) + " at : ") + counting_line) + " v=") + "(") + Double.valueOf(_x_2)) + " , ") + Double.valueOf(_y_2)) + ")") + " and n=") + "(") + Double.valueOf(_x_3)) + 
              ",") + Double.valueOf(_y_3)) + ")") + " orientation:") + Integer.valueOf(orientation)));
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
    if (((((q.getX() <= Math.max(p.getX(), r.getX())) && (q.getX() >= Math.min(p.getX(), r.getX()))) && (q.getY() <= Math.max(p.getY(), r.getY()))) && (q.getY() >= Math.min(p.getY(), r.getY())))) {
      return true;
    }
    return false;
  }
  
  private void $behaviorUnit$Destroy$6(final Destroy occurrence) {
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
  
  @Extension
  @ImportedCapacityFeature(ObserverCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY;
  
  @SyntheticMember
  @Pure
  private ObserverCapacity $CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY == null || this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY = $getSkill(ObserverCapacity.class);
    }
    return $castSkill(ObserverCapacity.class, this.$CAPACITY_USE$BE_UCLOUVAIN_ORGANISATION_PLATFORM_OBSERVERCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(ExternalContextAccess.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS;
  
  @SyntheticMember
  @Pure
  private ExternalContextAccess $CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS == null || this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS = $getSkill(ExternalContextAccess.class);
    }
    return $castSkill(ExternalContextAccess.class, this.$CAPACITY_USE$IO_SARL_CORE_EXTERNALCONTEXTACCESS);
  }
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
  }
  
  @Extension
  @ImportedCapacityFeature(Detection.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION;
  
  @SyntheticMember
  @Pure
  private Detection $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION = $getSkill(Detection.class);
    }
    return $castSkill(Detection.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_DETECTION);
  }
  
  @Extension
  @ImportedCapacityFeature(Tracking.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING;
  
  @SyntheticMember
  @Pure
  private Tracking $CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING$CALLER() {
    if (this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING == null || this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING.get() == null) {
      this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING = $getSkill(Tracking.class);
    }
    return $castSkill(Tracking.class, this.$CAPACITY_USE$BE_UCLOUVAIN_APTITUDE_AGENTS_ALGORITHM_TRACKING);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$LeavePlatform(final LeavePlatform occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$LeavePlatform$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DResult(final BBoxes2DResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DResult$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerTrackingFound(final PartnerTrackingFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerTrackingFound$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PartnerDetectionFound(final PartnerDetectionFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PartnerDetectionFound$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BBoxes2DTrackResult(final BBoxes2DTrackResult occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BBoxes2DTrackResult$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$6(occurrence));
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
    ObserverRole other = (ObserverRole) obj;
    if (!Objects.equals(this.PlatformID, other.PlatformID))
      return false;
    if (!Objects.equals(this.ExpertID, other.ExpertID))
      return false;
    if (!Objects.equals(this.configPathYOLO, other.configPathYOLO))
      return false;
    if (!Objects.equals(this.configPathTinyYOLO, other.configPathTinyYOLO))
      return false;
    if (!Objects.equals(this.configPathSORT, other.configPathSORT))
      return false;
    if (!Objects.equals(this.configPathDeepSORT, other.configPathDeepSORT))
      return false;
    if (!Objects.equals(this.partnerDetectionName, other.partnerDetectionName))
      return false;
    if (!Objects.equals(this.partnerTrackingName, other.partnerTrackingName))
      return false;
    if (other.start != this.start)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.PlatformID);
    result = prime * result + Objects.hashCode(this.ExpertID);
    result = prime * result + Objects.hashCode(this.configPathYOLO);
    result = prime * result + Objects.hashCode(this.configPathTinyYOLO);
    result = prime * result + Objects.hashCode(this.configPathSORT);
    result = prime * result + Objects.hashCode(this.configPathDeepSORT);
    result = prime * result + Objects.hashCode(this.partnerDetectionName);
    result = prime * result + Objects.hashCode(this.partnerTrackingName);
    result = prime * result + Long.hashCode(this.start);
    return result;
  }
  
  @SyntheticMember
  public ObserverRole(final Agent agent) {
    super(agent);
  }
}
