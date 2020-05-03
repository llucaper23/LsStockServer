package Model;

public class BotThread {

    private int botid;
    private Thread idThread;

    public BotThread(int botid, Thread idThread) {
        this.botid = botid;
        this.idThread = idThread;
    }

    public int getBotid() {
        return botid;
    }

    public void setBotid(int botid) {
        this.botid = botid;
    }

    public Thread getIdThread() {
        return idThread;
    }

    public void setIdThread(Thread idThread) {
        this.idThread = idThread;
    }
}
