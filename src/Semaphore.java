public class Semaphore {
    private int permits;

    public Semaphore(int initialPermits) {
        if (initialPermits < 0) {
            throw new IllegalArgumentException("Permits cannot be negative");
        }
        this.permits = initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notifyAll();
    }

    public synchronized int availablePermits() {
        return permits;
    }

    public synchronized boolean tryAcquire(long timeoutMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;

        while (permits == 0) {
            long remaining = deadline - System.currentTimeMillis();
            if (remaining <= 0) {
                return false;
            }
            wait(remaining);
        }

        permits--;
        return true;
    }
}