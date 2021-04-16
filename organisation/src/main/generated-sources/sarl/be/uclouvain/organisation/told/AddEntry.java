package be.uclouvain.organisation.told;

import be.uclouvain.organisation.told.DataEntry;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.UUID;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class AddEntry extends DataEntry {
  @SyntheticMember
  public AddEntry(final UUID k, final Object d) {
    super(k, d);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -1663036387L;
}
