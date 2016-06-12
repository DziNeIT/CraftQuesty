package pw.ollie.questy.craftquesty;

import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.SimpleQuestManager;
import com.volumetricpixels.questy.event.EventManager;
import com.volumetricpixels.questy.questy.event.SimpleEventManager;
import com.volumetricpixels.questy.storage.ProgressStore;
import com.volumetricpixels.questy.storage.store.SimpleProgressStore;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pw.ollie.questy.craftquesty.listener.mc.CQBlockListener;
import pw.ollie.questy.craftquesty.listener.mc.CQEntityListener;
import pw.ollie.questy.craftquesty.listener.mc.CQPlayerListener;
import pw.ollie.questy.craftquesty.listener.questy.CQQuestListener;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main plugin class for CraftQuesty, a Bukkit plugin which uses the Questy Java framework to implement some simple quests as a manner of showing how Questy can be used.
 */
public final class CraftQuestyPlugin extends JavaPlugin {
    private Logger pluginLogger;
    private QuestManager questManager;
    private ProgressStore progressStore;
    private EventManager questyEventManager;

    @Override
    public void onEnable() {
        this.pluginLogger = this.getLogger();

        final File dataFolder = this.getDataFolder();
        final File storageFolder = new File(dataFolder, "store");

        if (!storageFolder.exists() && !storageFolder.mkdirs()) {
            this.pluginLogger.log(Level.SEVERE, "CraftQuesty was unable to create the necessary directory for data storage. Please create the directory manually");
            this.pluginLogger.log(Level.SEVERE, "CraftQuesty will not run correctly");
            return;
        }

        this.progressStore = new SimpleProgressStore(storageFolder);
        this.questyEventManager = new SimpleEventManager();
        this.questManager = new SimpleQuestManager(this.progressStore, this.questyEventManager);

        final Server server = this.getServer();
        final PluginManager serverPluginManager = server.getPluginManager();

        // register the plugin's various Bukkit listeners
        serverPluginManager.registerEvents(new CQBlockListener(this), this);
        serverPluginManager.registerEvents(new CQEntityListener(this), this);
        serverPluginManager.registerEvents(new CQPlayerListener(this), this);

        // register the questy listener
        this.questyEventManager.register(new CQQuestListener(this));
    }

    @Override
    public void onDisable() {
        this.pluginLogger = null;
        this.questManager = null;
        this.progressStore = null;
        this.questyEventManager = null;
    }

    public QuestManager getQuestManager() {
        return this.questManager;
    }

    public EventManager getQuestyEventManager() {
        return this.questyEventManager;
    }
}
