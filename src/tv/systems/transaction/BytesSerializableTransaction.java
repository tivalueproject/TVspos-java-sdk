package tv.systems.transaction;

import tv.systems.error.SerializationError;
import tv.systems.serialization.BytesSerializable;
import tv.systems.type.Base58Field;
import tv.systems.utils.BytesHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BytesSerializableTransaction extends BasicTransaction implements BytesSerializable {

    protected abstract String[] getByteSerializedFields();

    public byte[] toBytes() throws SerializationError {
        return BytesHelper.toBytes(toByteList());
    }

    public List<Byte> toByteList() throws SerializationError {
        List<Byte> result = new ArrayList<Byte>();
        for (String fieldName : getByteSerializedFields()) {
            Field field = null;
            Object value = null;
            Class objClass = this.getClass();
            while (objClass != null && field == null) {
                try {
                    field = objClass.getDeclaredField(fieldName);
                    value = field.get(this);
                } catch (NoSuchFieldException e) {
                    objClass = objClass.getSuperclass();
                } catch (IllegalAccessException e) {
                    throw new SerializationError(String.format("Cannot access field '%s'", fieldName));
                }
            }
            if (field == null) {
                throw new SerializationError(String.format("Cannot find field '%s'", fieldName));
            }
            if (value == null) {
                throw new SerializationError(String.format("The value of field '%s' is null", fieldName));
            }
            byte[] bytesArray;
            if (String.class.isAssignableFrom(field.getType())) {
                Base58Field b58field = field.getAnnotation(Base58Field.class);
                if (b58field != null) {
                    if (!b58field.isFixedLength()) {
                        bytesArray = BytesHelper.serializeBase58WithSize(value.toString(), Short.BYTES);
                    } else {
                        bytesArray = BytesHelper.serializeBase58(value.toString());
                    }
                } else {
                    bytesArray = BytesHelper.toBytes(value.toString());
                }
            } else if (Long.class.isAssignableFrom(field.getType())){
                bytesArray = BytesHelper.toBytes((Long)value);
            } else if (Integer.class.isAssignableFrom(field.getType())){
                bytesArray = BytesHelper.toBytes((Integer)value);
            } else if (Short.class.isAssignableFrom(field.getType())){
                bytesArray = BytesHelper.toBytes((Short)value);
            } else if (Byte.class.isAssignableFrom(field.getType())){
                bytesArray = BytesHelper.toBytes((Byte)value);
            } else {
                throw new SerializationError("Unable to Serialized Field: " + fieldName);
            }
            for (byte b : bytesArray) {
                result.add(b);
            }
        }
        return result;
    }
}
