package be.uclouvain.aptitude.surveillance.algorithm.messages;

import be.uclouvain.aptitude.surveillance.algorithm.messages.BaseMessage;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.io.IOException;
import java.util.Objects;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author samelson
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class EvaluationMessage extends BaseMessage {
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String requestID;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String predictions;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private String gts;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int maxFrame;
  
  /**
   * HOTA Metrics
   */
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double HOTA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double DetA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double AssA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double DetRe;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double DetPr;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double AssRe;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double AssPr;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double LocA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double HOTA_TP;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double HOTA_FN;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double HOTA_FP;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] HOTA_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] DetA_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] AssA_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] DetRe_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] DetPr_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] AssRe_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] AssPr_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] LocA_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] HOTA_TP_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] HOTA_FN_Array;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double[] HOTA_FP_Array;
  
  /**
   * CLEAR - MOT Metrics
   */
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double MOTA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double MOTP;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double MODA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double CLR_Re;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double CLR_Pr;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double CLR_F1;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double MTR;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double PTR;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double MLR;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double sMOTA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double FP_perFrame;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double MOTAL;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double MOTP_sum;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int CLR_TP;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int CLR_FN;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int CLR_FP;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int IDSW;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int mostlyTracked;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int partlyTracked;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int mostlyLost;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int fragmentations;
  
  /**
   * Identity Metrics
   */
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double IDF1;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double IDR;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double IDP;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int IDTP;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int IDFN;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int IDFP;
  
  /**
   * VACE Metrics
   */
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double STDA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int VACE_IDs;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int VACE_GT_IDs;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double FDA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int numNonEmptyTimesteps;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double ATA;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private double SFDA;
  
  /**
   * Count Metrics
   */
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int count_dets;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int count_GT_dets;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int count_IDs;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int count_GT_IDs;
  
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
  private int count_frames;
  
  private final int FACTORY_ID = 1;
  
  private final int CLASS_ID = 30;
  
  public int getFactoryId() {
    return this.FACTORY_ID;
  }
  
  public int getId() {
    return this.CLASS_ID;
  }
  
  public void readData(final ObjectDataInput odi) throws IOException {
    super.readData(odi);
    this.requestID = odi.readUTF();
    this.predictions = odi.readUTF();
    this.gts = odi.readUTF();
    this.maxFrame = odi.readInt();
    this.HOTA = odi.readDouble();
    this.DetA = odi.readDouble();
    this.AssA = odi.readDouble();
    this.DetRe = odi.readDouble();
    this.DetPr = odi.readDouble();
    this.AssRe = odi.readDouble();
    this.AssPr = odi.readDouble();
    this.LocA = odi.readDouble();
    this.HOTA_TP = odi.readDouble();
    this.HOTA_FP = odi.readDouble();
    this.HOTA_FN = odi.readDouble();
    this.HOTA_Array = odi.readDoubleArray();
    this.DetA_Array = odi.readDoubleArray();
    this.AssA_Array = odi.readDoubleArray();
    this.DetRe_Array = odi.readDoubleArray();
    this.DetPr_Array = odi.readDoubleArray();
    this.AssRe_Array = odi.readDoubleArray();
    this.AssPr_Array = odi.readDoubleArray();
    this.LocA_Array = odi.readDoubleArray();
    this.HOTA_TP_Array = odi.readDoubleArray();
    this.HOTA_FN_Array = odi.readDoubleArray();
    this.HOTA_FP_Array = odi.readDoubleArray();
    this.MOTA = odi.readDouble();
    this.MOTP = odi.readDouble();
    this.MODA = odi.readDouble();
    this.CLR_Re = odi.readDouble();
    this.CLR_Pr = odi.readDouble();
    this.CLR_F1 = odi.readDouble();
    this.MTR = odi.readDouble();
    this.PTR = odi.readDouble();
    this.MLR = odi.readDouble();
    this.sMOTA = odi.readDouble();
    this.FP_perFrame = odi.readDouble();
    this.MOTAL = odi.readDouble();
    this.MOTP_sum = odi.readDouble();
    this.CLR_TP = odi.readInt();
    this.CLR_FN = odi.readInt();
    this.CLR_FP = odi.readInt();
    this.IDSW = odi.readInt();
    this.mostlyTracked = odi.readInt();
    this.partlyTracked = odi.readInt();
    this.mostlyLost = odi.readInt();
    this.fragmentations = odi.readInt();
    this.IDF1 = odi.readDouble();
    this.IDR = odi.readDouble();
    this.IDP = odi.readDouble();
    this.IDTP = odi.readInt();
    this.IDFN = odi.readInt();
    this.IDFP = odi.readInt();
    this.STDA = odi.readDouble();
    this.VACE_IDs = odi.readInt();
    this.VACE_GT_IDs = odi.readInt();
    this.FDA = odi.readDouble();
    this.numNonEmptyTimesteps = odi.readInt();
    this.ATA = odi.readDouble();
    this.SFDA = odi.readDouble();
    this.count_dets = odi.readInt();
    this.count_GT_dets = odi.readInt();
    this.count_IDs = odi.readInt();
    this.count_GT_IDs = odi.readInt();
    this.count_frames = odi.readInt();
  }
  
  public void writeData(final ObjectDataOutput odo) throws IOException {
    super.writeData(odo);
    odo.writeUTF(this.requestID);
    odo.writeUTF(this.predictions);
    odo.writeUTF(this.gts);
    odo.writeInt(this.maxFrame);
    odo.writeDouble(this.HOTA);
    odo.writeDouble(this.DetA);
    odo.writeDouble(this.AssA);
    odo.writeDouble(this.DetRe);
    odo.writeDouble(this.DetPr);
    odo.writeDouble(this.AssRe);
    odo.writeDouble(this.AssPr);
    odo.writeDouble(this.LocA);
    odo.writeDouble(this.HOTA_TP);
    odo.writeDouble(this.HOTA_FN);
    odo.writeDouble(this.HOTA_FP);
    odo.writeDoubleArray(this.HOTA_Array);
    odo.writeDoubleArray(this.DetA_Array);
    odo.writeDoubleArray(this.AssA_Array);
    odo.writeDoubleArray(this.DetRe_Array);
    odo.writeDoubleArray(this.DetPr_Array);
    odo.writeDoubleArray(this.AssRe_Array);
    odo.writeDoubleArray(this.AssPr_Array);
    odo.writeDoubleArray(this.LocA_Array);
    odo.writeDoubleArray(this.HOTA_TP_Array);
    odo.writeDoubleArray(this.HOTA_FN_Array);
    odo.writeDoubleArray(this.HOTA_FP_Array);
    odo.writeDouble(this.MOTA);
    odo.writeDouble(this.MOTP);
    odo.writeDouble(this.MODA);
    odo.writeDouble(this.CLR_Re);
    odo.writeDouble(this.CLR_Pr);
    odo.writeDouble(this.CLR_F1);
    odo.writeDouble(this.MTR);
    odo.writeDouble(this.PTR);
    odo.writeDouble(this.MLR);
    odo.writeDouble(this.sMOTA);
    odo.writeDouble(this.FP_perFrame);
    odo.writeDouble(this.MOTAL);
    odo.writeDouble(this.MOTP_sum);
    odo.writeDouble(this.CLR_TP);
    odo.writeDouble(this.CLR_FN);
    odo.writeDouble(this.CLR_FP);
    odo.writeDouble(this.IDSW);
    odo.writeDouble(this.mostlyTracked);
    odo.writeDouble(this.partlyTracked);
    odo.writeDouble(this.mostlyLost);
    odo.writeDouble(this.fragmentations);
    odo.writeDouble(this.IDF1);
    odo.writeDouble(this.IDR);
    odo.writeDouble(this.IDP);
    odo.writeInt(this.IDTP);
    odo.writeInt(this.IDFN);
    odo.writeInt(this.IDFP);
    odo.writeDouble(this.STDA);
    odo.writeInt(this.VACE_IDs);
    odo.writeInt(this.VACE_GT_IDs);
    odo.writeDouble(this.FDA);
    odo.writeInt(this.numNonEmptyTimesteps);
    odo.writeDouble(this.ATA);
    odo.writeDouble(this.SFDA);
    odo.writeInt(this.count_dets);
    odo.writeInt(this.count_GT_dets);
    odo.writeInt(this.count_IDs);
    odo.writeInt(this.count_GT_IDs);
    odo.writeInt(this.count_frames);
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
    EvaluationMessage other = (EvaluationMessage) obj;
    if (!Objects.equals(this.requestID, other.requestID))
      return false;
    if (!Objects.equals(this.predictions, other.predictions))
      return false;
    if (!Objects.equals(this.gts, other.gts))
      return false;
    if (other.maxFrame != this.maxFrame)
      return false;
    if (Double.doubleToLongBits(other.HOTA) != Double.doubleToLongBits(this.HOTA))
      return false;
    if (Double.doubleToLongBits(other.DetA) != Double.doubleToLongBits(this.DetA))
      return false;
    if (Double.doubleToLongBits(other.AssA) != Double.doubleToLongBits(this.AssA))
      return false;
    if (Double.doubleToLongBits(other.DetRe) != Double.doubleToLongBits(this.DetRe))
      return false;
    if (Double.doubleToLongBits(other.DetPr) != Double.doubleToLongBits(this.DetPr))
      return false;
    if (Double.doubleToLongBits(other.AssRe) != Double.doubleToLongBits(this.AssRe))
      return false;
    if (Double.doubleToLongBits(other.AssPr) != Double.doubleToLongBits(this.AssPr))
      return false;
    if (Double.doubleToLongBits(other.LocA) != Double.doubleToLongBits(this.LocA))
      return false;
    if (Double.doubleToLongBits(other.HOTA_TP) != Double.doubleToLongBits(this.HOTA_TP))
      return false;
    if (Double.doubleToLongBits(other.HOTA_FN) != Double.doubleToLongBits(this.HOTA_FN))
      return false;
    if (Double.doubleToLongBits(other.HOTA_FP) != Double.doubleToLongBits(this.HOTA_FP))
      return false;
    if (Double.doubleToLongBits(other.MOTA) != Double.doubleToLongBits(this.MOTA))
      return false;
    if (Double.doubleToLongBits(other.MOTP) != Double.doubleToLongBits(this.MOTP))
      return false;
    if (Double.doubleToLongBits(other.MODA) != Double.doubleToLongBits(this.MODA))
      return false;
    if (Double.doubleToLongBits(other.CLR_Re) != Double.doubleToLongBits(this.CLR_Re))
      return false;
    if (Double.doubleToLongBits(other.CLR_Pr) != Double.doubleToLongBits(this.CLR_Pr))
      return false;
    if (Double.doubleToLongBits(other.CLR_F1) != Double.doubleToLongBits(this.CLR_F1))
      return false;
    if (Double.doubleToLongBits(other.MTR) != Double.doubleToLongBits(this.MTR))
      return false;
    if (Double.doubleToLongBits(other.PTR) != Double.doubleToLongBits(this.PTR))
      return false;
    if (Double.doubleToLongBits(other.MLR) != Double.doubleToLongBits(this.MLR))
      return false;
    if (Double.doubleToLongBits(other.sMOTA) != Double.doubleToLongBits(this.sMOTA))
      return false;
    if (Double.doubleToLongBits(other.FP_perFrame) != Double.doubleToLongBits(this.FP_perFrame))
      return false;
    if (Double.doubleToLongBits(other.MOTAL) != Double.doubleToLongBits(this.MOTAL))
      return false;
    if (Double.doubleToLongBits(other.MOTP_sum) != Double.doubleToLongBits(this.MOTP_sum))
      return false;
    if (other.CLR_TP != this.CLR_TP)
      return false;
    if (other.CLR_FN != this.CLR_FN)
      return false;
    if (other.CLR_FP != this.CLR_FP)
      return false;
    if (other.IDSW != this.IDSW)
      return false;
    if (other.mostlyTracked != this.mostlyTracked)
      return false;
    if (other.partlyTracked != this.partlyTracked)
      return false;
    if (other.mostlyLost != this.mostlyLost)
      return false;
    if (other.fragmentations != this.fragmentations)
      return false;
    if (Double.doubleToLongBits(other.IDF1) != Double.doubleToLongBits(this.IDF1))
      return false;
    if (Double.doubleToLongBits(other.IDR) != Double.doubleToLongBits(this.IDR))
      return false;
    if (Double.doubleToLongBits(other.IDP) != Double.doubleToLongBits(this.IDP))
      return false;
    if (other.IDTP != this.IDTP)
      return false;
    if (other.IDFN != this.IDFN)
      return false;
    if (other.IDFP != this.IDFP)
      return false;
    if (Double.doubleToLongBits(other.STDA) != Double.doubleToLongBits(this.STDA))
      return false;
    if (other.VACE_IDs != this.VACE_IDs)
      return false;
    if (other.VACE_GT_IDs != this.VACE_GT_IDs)
      return false;
    if (Double.doubleToLongBits(other.FDA) != Double.doubleToLongBits(this.FDA))
      return false;
    if (other.numNonEmptyTimesteps != this.numNonEmptyTimesteps)
      return false;
    if (Double.doubleToLongBits(other.ATA) != Double.doubleToLongBits(this.ATA))
      return false;
    if (Double.doubleToLongBits(other.SFDA) != Double.doubleToLongBits(this.SFDA))
      return false;
    if (other.count_dets != this.count_dets)
      return false;
    if (other.count_GT_dets != this.count_GT_dets)
      return false;
    if (other.count_IDs != this.count_IDs)
      return false;
    if (other.count_GT_IDs != this.count_GT_IDs)
      return false;
    if (other.count_frames != this.count_frames)
      return false;
    if (other.FACTORY_ID != this.FACTORY_ID)
      return false;
    if (other.CLASS_ID != this.CLASS_ID)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.requestID);
    result = prime * result + Objects.hashCode(this.predictions);
    result = prime * result + Objects.hashCode(this.gts);
    result = prime * result + Integer.hashCode(this.maxFrame);
    result = prime * result + Double.hashCode(this.HOTA);
    result = prime * result + Double.hashCode(this.DetA);
    result = prime * result + Double.hashCode(this.AssA);
    result = prime * result + Double.hashCode(this.DetRe);
    result = prime * result + Double.hashCode(this.DetPr);
    result = prime * result + Double.hashCode(this.AssRe);
    result = prime * result + Double.hashCode(this.AssPr);
    result = prime * result + Double.hashCode(this.LocA);
    result = prime * result + Double.hashCode(this.HOTA_TP);
    result = prime * result + Double.hashCode(this.HOTA_FN);
    result = prime * result + Double.hashCode(this.HOTA_FP);
    result = prime * result + Double.hashCode(this.MOTA);
    result = prime * result + Double.hashCode(this.MOTP);
    result = prime * result + Double.hashCode(this.MODA);
    result = prime * result + Double.hashCode(this.CLR_Re);
    result = prime * result + Double.hashCode(this.CLR_Pr);
    result = prime * result + Double.hashCode(this.CLR_F1);
    result = prime * result + Double.hashCode(this.MTR);
    result = prime * result + Double.hashCode(this.PTR);
    result = prime * result + Double.hashCode(this.MLR);
    result = prime * result + Double.hashCode(this.sMOTA);
    result = prime * result + Double.hashCode(this.FP_perFrame);
    result = prime * result + Double.hashCode(this.MOTAL);
    result = prime * result + Double.hashCode(this.MOTP_sum);
    result = prime * result + Integer.hashCode(this.CLR_TP);
    result = prime * result + Integer.hashCode(this.CLR_FN);
    result = prime * result + Integer.hashCode(this.CLR_FP);
    result = prime * result + Integer.hashCode(this.IDSW);
    result = prime * result + Integer.hashCode(this.mostlyTracked);
    result = prime * result + Integer.hashCode(this.partlyTracked);
    result = prime * result + Integer.hashCode(this.mostlyLost);
    result = prime * result + Integer.hashCode(this.fragmentations);
    result = prime * result + Double.hashCode(this.IDF1);
    result = prime * result + Double.hashCode(this.IDR);
    result = prime * result + Double.hashCode(this.IDP);
    result = prime * result + Integer.hashCode(this.IDTP);
    result = prime * result + Integer.hashCode(this.IDFN);
    result = prime * result + Integer.hashCode(this.IDFP);
    result = prime * result + Double.hashCode(this.STDA);
    result = prime * result + Integer.hashCode(this.VACE_IDs);
    result = prime * result + Integer.hashCode(this.VACE_GT_IDs);
    result = prime * result + Double.hashCode(this.FDA);
    result = prime * result + Integer.hashCode(this.numNonEmptyTimesteps);
    result = prime * result + Double.hashCode(this.ATA);
    result = prime * result + Double.hashCode(this.SFDA);
    result = prime * result + Integer.hashCode(this.count_dets);
    result = prime * result + Integer.hashCode(this.count_GT_dets);
    result = prime * result + Integer.hashCode(this.count_IDs);
    result = prime * result + Integer.hashCode(this.count_GT_IDs);
    result = prime * result + Integer.hashCode(this.count_frames);
    result = prime * result + Integer.hashCode(this.FACTORY_ID);
    result = prime * result + Integer.hashCode(this.CLASS_ID);
    return result;
  }
  
  @SyntheticMember
  public EvaluationMessage() {
    super();
  }
  
  @Pure
  public String getRequestID() {
    return this.requestID;
  }
  
  public void setRequestID(final String requestID) {
    this.requestID = requestID;
  }
  
  @Pure
  public String getPredictions() {
    return this.predictions;
  }
  
  public void setPredictions(final String predictions) {
    this.predictions = predictions;
  }
  
  @Pure
  public String getGts() {
    return this.gts;
  }
  
  public void setGts(final String gts) {
    this.gts = gts;
  }
  
  @Pure
  public int getMaxFrame() {
    return this.maxFrame;
  }
  
  public void setMaxFrame(final int maxFrame) {
    this.maxFrame = maxFrame;
  }
  
  @Pure
  public double getHOTA() {
    return this.HOTA;
  }
  
  public void setHOTA(final double HOTA) {
    this.HOTA = HOTA;
  }
  
  @Pure
  public double getDetA() {
    return this.DetA;
  }
  
  public void setDetA(final double DetA) {
    this.DetA = DetA;
  }
  
  @Pure
  public double getAssA() {
    return this.AssA;
  }
  
  public void setAssA(final double AssA) {
    this.AssA = AssA;
  }
  
  @Pure
  public double getDetRe() {
    return this.DetRe;
  }
  
  public void setDetRe(final double DetRe) {
    this.DetRe = DetRe;
  }
  
  @Pure
  public double getDetPr() {
    return this.DetPr;
  }
  
  public void setDetPr(final double DetPr) {
    this.DetPr = DetPr;
  }
  
  @Pure
  public double getAssRe() {
    return this.AssRe;
  }
  
  public void setAssRe(final double AssRe) {
    this.AssRe = AssRe;
  }
  
  @Pure
  public double getAssPr() {
    return this.AssPr;
  }
  
  public void setAssPr(final double AssPr) {
    this.AssPr = AssPr;
  }
  
  @Pure
  public double getLocA() {
    return this.LocA;
  }
  
  public void setLocA(final double LocA) {
    this.LocA = LocA;
  }
  
  @Pure
  public double getHOTA_TP() {
    return this.HOTA_TP;
  }
  
  public void setHOTA_TP(final double HOTA_TP) {
    this.HOTA_TP = HOTA_TP;
  }
  
  @Pure
  public double getHOTA_FN() {
    return this.HOTA_FN;
  }
  
  public void setHOTA_FN(final double HOTA_FN) {
    this.HOTA_FN = HOTA_FN;
  }
  
  @Pure
  public double getHOTA_FP() {
    return this.HOTA_FP;
  }
  
  public void setHOTA_FP(final double HOTA_FP) {
    this.HOTA_FP = HOTA_FP;
  }
  
  @Pure
  public double[] getHOTA_Array() {
    return this.HOTA_Array;
  }
  
  public void setHOTA_Array(final double[] HOTA_Array) {
    this.HOTA_Array = HOTA_Array;
  }
  
  @Pure
  public double[] getDetA_Array() {
    return this.DetA_Array;
  }
  
  public void setDetA_Array(final double[] DetA_Array) {
    this.DetA_Array = DetA_Array;
  }
  
  @Pure
  public double[] getAssA_Array() {
    return this.AssA_Array;
  }
  
  public void setAssA_Array(final double[] AssA_Array) {
    this.AssA_Array = AssA_Array;
  }
  
  @Pure
  public double[] getDetRe_Array() {
    return this.DetRe_Array;
  }
  
  public void setDetRe_Array(final double[] DetRe_Array) {
    this.DetRe_Array = DetRe_Array;
  }
  
  @Pure
  public double[] getDetPr_Array() {
    return this.DetPr_Array;
  }
  
  public void setDetPr_Array(final double[] DetPr_Array) {
    this.DetPr_Array = DetPr_Array;
  }
  
  @Pure
  public double[] getAssRe_Array() {
    return this.AssRe_Array;
  }
  
  public void setAssRe_Array(final double[] AssRe_Array) {
    this.AssRe_Array = AssRe_Array;
  }
  
  @Pure
  public double[] getAssPr_Array() {
    return this.AssPr_Array;
  }
  
  public void setAssPr_Array(final double[] AssPr_Array) {
    this.AssPr_Array = AssPr_Array;
  }
  
  @Pure
  public double[] getLocA_Array() {
    return this.LocA_Array;
  }
  
  public void setLocA_Array(final double[] LocA_Array) {
    this.LocA_Array = LocA_Array;
  }
  
  @Pure
  public double[] getHOTA_TP_Array() {
    return this.HOTA_TP_Array;
  }
  
  public void setHOTA_TP_Array(final double[] HOTA_TP_Array) {
    this.HOTA_TP_Array = HOTA_TP_Array;
  }
  
  @Pure
  public double[] getHOTA_FN_Array() {
    return this.HOTA_FN_Array;
  }
  
  public void setHOTA_FN_Array(final double[] HOTA_FN_Array) {
    this.HOTA_FN_Array = HOTA_FN_Array;
  }
  
  @Pure
  public double[] getHOTA_FP_Array() {
    return this.HOTA_FP_Array;
  }
  
  public void setHOTA_FP_Array(final double[] HOTA_FP_Array) {
    this.HOTA_FP_Array = HOTA_FP_Array;
  }
  
  @Pure
  public double getMOTA() {
    return this.MOTA;
  }
  
  public void setMOTA(final double MOTA) {
    this.MOTA = MOTA;
  }
  
  @Pure
  public double getMOTP() {
    return this.MOTP;
  }
  
  public void setMOTP(final double MOTP) {
    this.MOTP = MOTP;
  }
  
  @Pure
  public double getMODA() {
    return this.MODA;
  }
  
  public void setMODA(final double MODA) {
    this.MODA = MODA;
  }
  
  @Pure
  public double getCLR_Re() {
    return this.CLR_Re;
  }
  
  public void setCLR_Re(final double CLR_Re) {
    this.CLR_Re = CLR_Re;
  }
  
  @Pure
  public double getCLR_Pr() {
    return this.CLR_Pr;
  }
  
  public void setCLR_Pr(final double CLR_Pr) {
    this.CLR_Pr = CLR_Pr;
  }
  
  @Pure
  public double getCLR_F1() {
    return this.CLR_F1;
  }
  
  public void setCLR_F1(final double CLR_F1) {
    this.CLR_F1 = CLR_F1;
  }
  
  @Pure
  public double getMTR() {
    return this.MTR;
  }
  
  public void setMTR(final double MTR) {
    this.MTR = MTR;
  }
  
  @Pure
  public double getPTR() {
    return this.PTR;
  }
  
  public void setPTR(final double PTR) {
    this.PTR = PTR;
  }
  
  @Pure
  public double getMLR() {
    return this.MLR;
  }
  
  public void setMLR(final double MLR) {
    this.MLR = MLR;
  }
  
  @Pure
  public double getSMOTA() {
    return this.sMOTA;
  }
  
  public void setSMOTA(final double sMOTA) {
    this.sMOTA = sMOTA;
  }
  
  @Pure
  public double getFP_perFrame() {
    return this.FP_perFrame;
  }
  
  public void setFP_perFrame(final double FP_perFrame) {
    this.FP_perFrame = FP_perFrame;
  }
  
  @Pure
  public double getMOTAL() {
    return this.MOTAL;
  }
  
  public void setMOTAL(final double MOTAL) {
    this.MOTAL = MOTAL;
  }
  
  @Pure
  public double getMOTP_sum() {
    return this.MOTP_sum;
  }
  
  public void setMOTP_sum(final double MOTP_sum) {
    this.MOTP_sum = MOTP_sum;
  }
  
  @Pure
  public int getCLR_TP() {
    return this.CLR_TP;
  }
  
  public void setCLR_TP(final int CLR_TP) {
    this.CLR_TP = CLR_TP;
  }
  
  @Pure
  public int getCLR_FN() {
    return this.CLR_FN;
  }
  
  public void setCLR_FN(final int CLR_FN) {
    this.CLR_FN = CLR_FN;
  }
  
  @Pure
  public int getCLR_FP() {
    return this.CLR_FP;
  }
  
  public void setCLR_FP(final int CLR_FP) {
    this.CLR_FP = CLR_FP;
  }
  
  @Pure
  public int getIDSW() {
    return this.IDSW;
  }
  
  public void setIDSW(final int IDSW) {
    this.IDSW = IDSW;
  }
  
  @Pure
  public int getMostlyTracked() {
    return this.mostlyTracked;
  }
  
  public void setMostlyTracked(final int mostlyTracked) {
    this.mostlyTracked = mostlyTracked;
  }
  
  @Pure
  public int getPartlyTracked() {
    return this.partlyTracked;
  }
  
  public void setPartlyTracked(final int partlyTracked) {
    this.partlyTracked = partlyTracked;
  }
  
  @Pure
  public int getMostlyLost() {
    return this.mostlyLost;
  }
  
  public void setMostlyLost(final int mostlyLost) {
    this.mostlyLost = mostlyLost;
  }
  
  @Pure
  public int getFragmentations() {
    return this.fragmentations;
  }
  
  public void setFragmentations(final int fragmentations) {
    this.fragmentations = fragmentations;
  }
  
  @Pure
  public double getIDF1() {
    return this.IDF1;
  }
  
  public void setIDF1(final double IDF1) {
    this.IDF1 = IDF1;
  }
  
  @Pure
  public double getIDR() {
    return this.IDR;
  }
  
  public void setIDR(final double IDR) {
    this.IDR = IDR;
  }
  
  @Pure
  public double getIDP() {
    return this.IDP;
  }
  
  public void setIDP(final double IDP) {
    this.IDP = IDP;
  }
  
  @Pure
  public int getIDTP() {
    return this.IDTP;
  }
  
  public void setIDTP(final int IDTP) {
    this.IDTP = IDTP;
  }
  
  @Pure
  public int getIDFN() {
    return this.IDFN;
  }
  
  public void setIDFN(final int IDFN) {
    this.IDFN = IDFN;
  }
  
  @Pure
  public int getIDFP() {
    return this.IDFP;
  }
  
  public void setIDFP(final int IDFP) {
    this.IDFP = IDFP;
  }
  
  @Pure
  public double getSTDA() {
    return this.STDA;
  }
  
  public void setSTDA(final double STDA) {
    this.STDA = STDA;
  }
  
  @Pure
  public int getVACE_IDs() {
    return this.VACE_IDs;
  }
  
  public void setVACE_IDs(final int VACE_IDs) {
    this.VACE_IDs = VACE_IDs;
  }
  
  @Pure
  public int getVACE_GT_IDs() {
    return this.VACE_GT_IDs;
  }
  
  public void setVACE_GT_IDs(final int VACE_GT_IDs) {
    this.VACE_GT_IDs = VACE_GT_IDs;
  }
  
  @Pure
  public double getFDA() {
    return this.FDA;
  }
  
  public void setFDA(final double FDA) {
    this.FDA = FDA;
  }
  
  @Pure
  public int getNumNonEmptyTimesteps() {
    return this.numNonEmptyTimesteps;
  }
  
  public void setNumNonEmptyTimesteps(final int numNonEmptyTimesteps) {
    this.numNonEmptyTimesteps = numNonEmptyTimesteps;
  }
  
  @Pure
  public double getATA() {
    return this.ATA;
  }
  
  public void setATA(final double ATA) {
    this.ATA = ATA;
  }
  
  @Pure
  public double getSFDA() {
    return this.SFDA;
  }
  
  public void setSFDA(final double SFDA) {
    this.SFDA = SFDA;
  }
  
  @Pure
  public int getCount_dets() {
    return this.count_dets;
  }
  
  public void setCount_dets(final int count_dets) {
    this.count_dets = count_dets;
  }
  
  @Pure
  public int getCount_GT_dets() {
    return this.count_GT_dets;
  }
  
  public void setCount_GT_dets(final int count_GT_dets) {
    this.count_GT_dets = count_GT_dets;
  }
  
  @Pure
  public int getCount_IDs() {
    return this.count_IDs;
  }
  
  public void setCount_IDs(final int count_IDs) {
    this.count_IDs = count_IDs;
  }
  
  @Pure
  public int getCount_GT_IDs() {
    return this.count_GT_IDs;
  }
  
  public void setCount_GT_IDs(final int count_GT_IDs) {
    this.count_GT_IDs = count_GT_IDs;
  }
  
  @Pure
  public int getCount_frames() {
    return this.count_frames;
  }
  
  public void setCount_frames(final int count_frames) {
    this.count_frames = count_frames;
  }
}
