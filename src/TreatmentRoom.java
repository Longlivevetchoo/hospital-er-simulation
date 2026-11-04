public class TreatmentRoom {
    private final Semaphore s ;

    public TreatmentRoom  (int rooms){
        this.s = new Semaphore(rooms);
    }

    public boolean tryAcquireRoom (long milliseconds) throws InterruptedException{
        return s.tryAcquire(milliseconds);
    }


    public void freeRoom (){
        s.release();
    }


}
