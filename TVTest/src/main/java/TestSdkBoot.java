import tv.systems.Blockchain;
import tv.systems.type.NetworkType;
import tv.systems.Account;
import tv.systems.transaction.*;

public class TestSdkBoot {
    public static void main(String[] args){
        Blockchain chain = new Blockchain(NetworkType.Mainnet, "https://testwallet.t.top/api/");
        Account acc = new Account(NetworkType.Mainnet, "123", 0);

        Long amount = 1 * Blockchain.V_UNITY;  // Send 1.0 V coin
        PaymentTransaction tx = TransactionFactory.buildPaymentTx("u6RfmT67ZWqr6aZhA6j3vgDXTxJesp75s9o", amount);
        String txId = tx.getId(); // get Tx ID offline

        try {
            // Usage 1: for hot wallet sending transaction
            Transaction result = acc.sendTransaction(chain, tx);

            System.out.println(acc.getAddress());

            // Usage 2: for cold wallet signing transaction
            String signature = acc.getSignature(tx);

        }catch (Exception e){

        }


    }
}
