package tv.systems.serialization;

import tv.systems.error.SerializationError;

import java.util.List;

public interface BytesSerializable {
    byte[] toBytes() throws SerializationError;
    List<Byte> toByteList() throws SerializationError;
}
