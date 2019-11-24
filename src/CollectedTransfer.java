import static java.lang.Math.min;

public class CollectedTransfer implements Runnable {
    Account source1;
    Account source2;
    Account destination1;
    int amount;

    public CollectedTransfer(Account source1, Account source2, Account destination1, int amount) {
        this.source1 = source1;
        this.source2 = source2;
        this.destination1 = destination1;
        this.amount = amount;
    }
    private boolean collectedTransferInternal(Account src1, Account src2, Account dest1, int amount){
        synchronized(src1) {
            synchronized(src2) {
                int currentBalance1 = src1.getBalance();
                int currentBalance2 = src2.getBalance();
                synchronized (dest1){
                    int toDep = amount;
                    if(currentBalance1 + currentBalance2 >= amount){
                        int temp = min(amount, currentBalance1);
                        src1.withdraw(temp);
                        amount = amount - temp;
                        if(amount > 0){
                            temp = min(amount, currentBalance2);
                            src2.withdraw(temp);
                            dest1.deposit(toDep);
                            return true;
                        }
                    }else {
                        return false;
                    }
                    }
            }
        }
        return false;
    }
    private boolean collectedTransfer(Account src1, Account src2, Account dest1, int amount) {
        if (src1.id < src2.id) {
            return collectedTransferInternal(src1, src2, dest1, amount);
        } else {
            return collectedTransferInternal(src2, src1, dest1, amount);
        }
    }
    @Override
    public void run() {
        this.collectedTransfer(this.source1,this.source2,this.destination1,this.amount);
    }
}
