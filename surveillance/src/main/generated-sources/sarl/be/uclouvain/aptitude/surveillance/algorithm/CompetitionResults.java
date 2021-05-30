package be.uclouvain.aptitude.surveillance.algorithm;

import be.uclouvain.aptitude.surveillance.algorithm.messages.EvaluationMessage;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class CompetitionResults {
  private UUID CompetitorID;
  
  private String CompetitorBelief;
  
  private int Frame;
  
  private double DetectionTime;
  
  private double TrackingTime;
  
  private double HOTA;
  
  private double DetA;
  
  private double AssA;
  
  private double DetRe;
  
  private double DetPr;
  
  private double AssRe;
  
  private double AssPr;
  
  private double LocA;
  
  private double[] HOTA_Array;
  
  private double MOTA;
  
  private double MOTP;
  
  public CompetitionResults(final UUID id, final String s, final double ts, final double tt, final double h) {
    this.CompetitorID = id;
    this.CompetitorBelief = s;
    this.DetectionTime = ts;
    this.TrackingTime = tt;
    this.HOTA = h;
  }
  
  public double setValues(final EvaluationMessage e) {
    double _xblockexpression = (double) 0;
    {
      this.HOTA = e.getHOTA();
      this.DetA = e.getDetA();
      this.AssA = e.getAssA();
      this.DetRe = e.getDetRe();
      this.DetPr = e.getDetPr();
      this.AssRe = e.getAssRe();
      this.AssPr = e.getAssPr();
      this.LocA = e.getLocA();
      this.HOTA_Array = e.getHOTA_Array();
      this.MOTA = e.getMOTA();
      _xblockexpression = this.MOTP = e.getMOTP();
    }
    return _xblockexpression;
  }
  
  public CompetitionResults(final UUID id, final String s, final double ts, final double tt, final int f) {
    this.CompetitorID = id;
    this.CompetitorBelief = s;
    this.DetectionTime = ts;
    this.TrackingTime = tt;
    this.Frame = f;
  }
  
  @Pure
  public UUID getCompetitorID() {
    return this.CompetitorID;
  }
  
  @Pure
  public String getBelief() {
    return this.CompetitorBelief;
  }
  
  @Pure
  public int getFrame() {
    return this.Frame;
  }
  
  @Pure
  public double[] getHOTA_Array(final double[] l) {
    return this.HOTA_Array;
  }
  
  @Pure
  public double getMOTA() {
    return this.MOTA;
  }
  
  @Pure
  public double getMOTP() {
    return this.MOTP;
  }
  
  @Pure
  public double getLocA() {
    return this.LocA;
  }
  
  @Pure
  public double getDetA() {
    return this.DetA;
  }
  
  @Pure
  public double getAssA() {
    return this.AssA;
  }
  
  @Pure
  public double getDetRe() {
    return this.DetRe;
  }
  
  @Pure
  public double getPr() {
    return this.DetPr;
  }
  
  @Pure
  public double getAssRe() {
    return this.AssRe;
  }
  
  @Pure
  public double getAssPr() {
    return this.AssPr;
  }
  
  public void setHOTA_Array(final double[] l) {
    this.HOTA_Array = l;
  }
  
  public void setMOTA(final double l) {
    this.MOTA = l;
  }
  
  public void setMOTP(final double l) {
    this.MOTP = l;
  }
  
  public void setLocA(final double l) {
    this.LocA = l;
  }
  
  public void setDetA(final double l) {
    this.DetA = l;
  }
  
  public void setAssA(final double l) {
    this.AssA = l;
  }
  
  public void setDetRe(final double l) {
    this.DetRe = l;
  }
  
  public void DetPr(final double l) {
    this.DetPr = l;
  }
  
  public void setAssRe(final double l) {
    this.AssRe = l;
  }
  
  public void DetAssPr(final double l) {
    this.AssPr = l;
  }
  
  public void setDetectionTime(final double l) {
    this.DetectionTime = l;
  }
  
  public void setTrackingTime(final double l) {
    this.TrackingTime = l;
  }
  
  public void setHOTA(final double h) {
    this.HOTA = h;
  }
  
  @Pure
  public double getDetectionTime() {
    return this.DetectionTime;
  }
  
  @Pure
  public double getTrackingTime() {
    return this.TrackingTime;
  }
  
  @Pure
  public double getHOTA() {
    return this.HOTA;
  }
  
  public void EvaluationPrint() {
    InputOutput.<String>println(this.CompetitorBelief);
    System.out.printf(Locale.FRANCE, "%-10.4f%n", Double.valueOf(this.HOTA));
    System.out.printf(Locale.FRANCE, "%-10.4f%n", Double.valueOf(this.DetectionTime));
    System.out.printf(Locale.FRANCE, "%-10.4f%n", Double.valueOf(this.TrackingTime));
    System.out.format("***********************");
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
    CompetitionResults other = (CompetitionResults) obj;
    if (!Objects.equals(this.CompetitorID, other.CompetitorID))
      return false;
    if (!Objects.equals(this.CompetitorBelief, other.CompetitorBelief))
      return false;
    if (other.Frame != this.Frame)
      return false;
    if (Double.doubleToLongBits(other.DetectionTime) != Double.doubleToLongBits(this.DetectionTime))
      return false;
    if (Double.doubleToLongBits(other.TrackingTime) != Double.doubleToLongBits(this.TrackingTime))
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
    if (Double.doubleToLongBits(other.MOTA) != Double.doubleToLongBits(this.MOTA))
      return false;
    if (Double.doubleToLongBits(other.MOTP) != Double.doubleToLongBits(this.MOTP))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.CompetitorID);
    result = prime * result + Objects.hashCode(this.CompetitorBelief);
    result = prime * result + Integer.hashCode(this.Frame);
    result = prime * result + Double.hashCode(this.DetectionTime);
    result = prime * result + Double.hashCode(this.TrackingTime);
    result = prime * result + Double.hashCode(this.HOTA);
    result = prime * result + Double.hashCode(this.DetA);
    result = prime * result + Double.hashCode(this.AssA);
    result = prime * result + Double.hashCode(this.DetRe);
    result = prime * result + Double.hashCode(this.DetPr);
    result = prime * result + Double.hashCode(this.AssRe);
    result = prime * result + Double.hashCode(this.AssPr);
    result = prime * result + Double.hashCode(this.LocA);
    result = prime * result + Double.hashCode(this.MOTA);
    result = prime * result + Double.hashCode(this.MOTP);
    return result;
  }
}
