package pw.ollie.craftquesty;

import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.event.EventManager;
import com.volumetricpixels.questy.questy.SimpleQuestManager;
import com.volumetricpixels.questy.questy.event.SimpleEventManager;
import com.volumetricpixels.questy.questy.store.TestProgressStore;
import com.volumetricpixels.questy.storage.ProgressStore;

import java.io.File;

public class QuestyManager {
    private QuestManager questManager;
    private ProgressStore progressStore;
    private EventManager eventManager;

    public QuestyManager(File storageFolder) {
        progressStore = new TestProgressStore(storageFolder);
        eventManager = new SimpleEventManager();
        questManager = new SimpleQuestManager(progressStore, eventManager);
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }

    public ProgressStore getProgressStore() {
        return progressStore;
    }

    public void setProgressStore(ProgressStore progressStore) {
        this.progressStore = progressStore;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
