import java.util.concurrent.locks.ReentrantLock;

public class CollectedTransferRe implements Runnable{
    Account source1;
    Account destination1;
    int amount;

    public CollectedTransferRe(Account source1,  Account destination1, int amount) {
        this.source1 = source1;
        this.destination1 = destination1;
        this.amount = amount;
    }

    private boolean collectedTransfer(Account src1, Account dest1, int amount) {
        if (src1.getBalance() < amount) {
            return false;
        }
        if (src1.id < dest1.id) {

            if ((!src1.lock.isLocked())) {
                src1.lock.lock();
                if ((!dest1.lock.isLocked())) {
                    dest1.lock.lock();
                    if (src1.withdraw(amount)) {
                        dest1.deposit(amount);
                        src1.lock.unlock();
                        dest1.lock.unlock();
                        return true;
                    }
                    else {
                        src1.deposit(amount);
                        src1.lock.unlock();
                        return false;
                    }
                }else{
                    src1.lock.unlock();
                    return false;
                }
            }
        }else {
            if ((!dest1.lock.isLocked())) {
                dest1.lock.lock();
                if ((!src1.lock.isLocked())) {
                    src1.lock.lock();
                    if (src1.withdraw(amount)) {
                        dest1.deposit(amount);
                        src1.lock.unlock();
                        return true;
                    } else {
                        src1.deposit(amount);
                        src1.lock.unlock();
                        return false;
                    }
                }else{
                    dest1.lock.unlock();
                    return false;
                }
            }
        }
        return false;
    }
    @Override
    public void run() {
        this.collectedTransfer(this.source1,this.destination1,this.amount);
    }

}