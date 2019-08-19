package tv.systems.transaction;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import tv.systems.type.Base58Field;
import tv.systems.type.TransactionType;

public class LeaseTransaction extends ProvenTransaction {
    public final String[] BYTE_SERIALIZED_FIELDS = {"type", "recipient", "amount", "fee", "feeScale", "timestamp"};
    @Base58Field
    protected String recipient;
    protected Long amount;

    public LeaseTransaction() {
        type = TransactionType.Lease.getTypeId();
    }

    @Override
    public JsonElement toAPIRequestJson(String publicKey, String signature) {
        JsonObject json = super.toAPIRequestJson(publicKey, signature).getAsJsonObject();
        json.addProperty("amount", this.amount);
        json.addProperty("recipient", this.recipient);
        return json;
    }

    @Override
    public JsonElement toColdSignJson(String publicKey) {
        int api = getColdSignAPIVersion(this.amount);
        JsonObject json = super.toColdSignJson(publicKey, api).getAsJsonObject();
        json.addProperty("amount", this.amount);
        json.addProperty("recipient", this.recipient);
        return json;
    }

    @Override
    protected String[] getByteSerializedFields() {
        return BYTE_SERIALIZED_FIELDS;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
