package be.uclouvain.organisation.told;

import be.uclouvain.organisation.told.AccessDatabaseCapacity;
import be.uclouvain.organisation.told.util.AlgorithmInfo;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import java.util.HashMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @TODO : Descrribe
 * 
 * @author $Author: manjahdani$
 * @version $0.0.2$
 * @date $16/04/2021$
 * @mavengroupid $be.uclouvain.aptitude$
 * @mavenartifactid $organisation$
 */
@SarlSpecification("0.11")
@SarlElementType(22)
@SuppressWarnings("all")
public class VirtualDatabaseSkill extends Skill implements AccessDatabaseCapacity {
  protected final HashMap<UUID, Object> Database = new HashMap<UUID, Object>();
  
  public void install() {
    UUID _randomUUID = UUID.randomUUID();
    AlgorithmInfo _algorithmInfo = new AlgorithmInfo("YOLO", "F:/aptitude/surveillance/src/main/resources/config/test-YOLO.json", "DETECTOR");
    this.Database.put(_randomUUID, _algorithmInfo);
    UUID _randomUUID_1 = UUID.randomUUID();
    AlgorithmInfo _algorithmInfo_1 = new AlgorithmInfo("TinyYOLO", "F:/aptitude/surveillance/src/main/resources/config/test-TinyYOLO.json", 
      "DETECTOR");
    this.Database.put(_randomUUID_1, _algorithmInfo_1);
    UUID _randomUUID_2 = UUID.randomUUID();
    AlgorithmInfo _algorithmInfo_2 = new AlgorithmInfo("SORT", "F:/aptitude/surveillance/src/main/resources/config/test-SORT.json", "TRACKER");
    this.Database.put(_randomUUID_2, _algorithmInfo_2);
    UUID _randomUUID_3 = UUID.randomUUID();
    AlgorithmInfo _algorithmInfo_3 = new AlgorithmInfo("DeepSORT", "F:/aptitude/surveillance/src/main/resources/config/test-DeepSORT.json", "TRACKER");
    this.Database.put(_randomUUID_3, _algorithmInfo_3);
    UUID _randomUUID_4 = UUID.randomUUID();
    AlgorithmInfo _algorithmInfo_4 = new AlgorithmInfo("APTITUDE", "NoneForTheMoment", "COUNTER");
    this.Database.put(_randomUUID_4, _algorithmInfo_4);
    this.Database.put(UUID.randomUUID(), "a");
  }
  
  public void prepareUninstallation() {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Preparing the uninstallation of the skill");
  }
  
  public void uninstall() {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Uninstalling the skill");
  }
  
  @Override
  public void delete(final UUID key) {
    this.Database.remove(key);
  }
  
  public void create(final UUID id, final Object data) {
    this.Database.put(id, data);
  }
  
  public Object read(final UUID key) {
    return this.Database.get(key);
  }
  
  @Pure
  public HashMap<UUID, Object> getDatabase() {
    return this.Database;
  }
  
  @Override
  public void update(final UUID id, final Object data) {
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
  public VirtualDatabaseSkill() {
    super();
  }
  
  @SyntheticMember
  public VirtualDatabaseSkill(final Agent agent) {
    super(agent);
  }
}
