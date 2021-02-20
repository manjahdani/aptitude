package be.uclouvain.aptitude.other;

import be.uclouvain.aptitude.messages.ActionMessage;
import be.uclouvain.aptitude.messages.BBoxes2DMessage;
import be.uclouvain.aptitude.messages.BBoxes2DTrackMessage;
import be.uclouvain.aptitude.messages.RequestMessage;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;

/**
 * @author samelson
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class SampleDataSerializableFactory implements DataSerializableFactory {
  public IdentifiedDataSerializable create(final int typeId) {
    if ((typeId == 10)) {
      return new RequestMessage();
    } else {
      if ((typeId == 11)) {
        return new ActionMessage();
      } else {
        if ((typeId == 21)) {
          return new BBoxes2DMessage();
        } else {
          if ((typeId == 22)) {
            return new BBoxes2DTrackMessage();
          }
        }
      }
    }
    return null;
  }
  
  @SyntheticMember
  public SampleDataSerializableFactory() {
    super();
  }
}
