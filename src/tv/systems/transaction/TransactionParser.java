package tv.systems.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import tv.systems.type.TransactionType;
import tv.systems.utils.JsonHelper;

public class TransactionParser {

    private static Gson gson = JsonHelper.getGsonInstance();
    private static JsonParser parser = JsonHelper.getParserInstance();

    public static Transaction parse(String json) throws JsonSyntaxException {
        JsonElement e = parser.parse(json);
        return parse(e);
    }

    public static Transaction parse(JsonElement json) throws JsonSyntaxException {
        Transaction tx = gson.fromJson(json, UnknownTransaction.class);
        TransactionType txType = TransactionType.parse(tx.getType());
        if (txType == null) {
            return tx;
        }
        switch (txType) {
            case Payment:
                tx = gson.fromJson(json, PaymentTransaction.class);
                break;
            case Lease:
                tx = gson.fromJson(json, LeaseTransaction.class);
                break;
            case CancelLease:
                tx = gson.fromJson(json, LeaseCancelTransaction.class);
                break;
            case Minting:
                tx = gson.fromJson(json, MintingTransaction.class);
                break;
        }
        return tx;
    }
}
