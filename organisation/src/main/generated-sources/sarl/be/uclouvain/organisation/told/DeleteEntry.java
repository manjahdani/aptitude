package be.uclouvain.organisation.told;

import be.uclouvain.organisation.told.Entry;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.UUID;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class DeleteEntry extends Entry {
  @SyntheticMember
  public DeleteEntry(final UUID k) {
    super(k);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 135099303L;
}
