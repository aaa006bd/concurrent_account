public class AccountMain {
    public static void main(String[] args) {
        Account acc1 = new Account(1,1000);
        Account acc2 = new Account(2,1000);
        Account acc3 = new Account(3,500);
        new Thread(new CollectedTransfer(acc1,acc2,acc3,500)).start();
        new Thread(new CollectedTransferRe(acc1,acc2,300)).start();
    }
}
