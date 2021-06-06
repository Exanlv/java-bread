package nl.landviz.daemons;

import java.util.TimerTask;

import nl.landviz.storage.BreadStorage;

public class SaveBread extends TimerTask {
    private BreadStorage breadStorage = BreadStorage.getInstance();
    private String storageDir;

    public SaveBread(String storageDir) {
        this.storageDir = storageDir;
    }

    public void run() {
        this.breadStorage.saveBread(this.storageDir);
    }
}
