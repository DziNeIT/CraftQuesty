package pw.ollie.craftquesty.cb;

import pw.ollie.craftquesty.CQHandler;
import pw.ollie.craftquesty.CQQuestListener;
import pw.ollie.craftquesty.QuestyManager;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main plugin class for CraftQuesty, a Bukkit plugin which uses the Questy Java framework to implement some simple
 * quests as a manner of showing how Questy can be used.
 */
public final class CraftQuestyPlugin extends JavaPlugin {
    private CQHandler handler;
    private Logger pluginLogger;
    private QuestyManager questyManager;

    @Override
    public void onEnable() {
        pluginLogger = getLogger();

        final File dataFolder = getDataFolder();
        final File storageFolder = new File(dataFolder, "store");

        if (!storageFolder.exists() && !storageFolder.mkdirs()) {
            pluginLogger.log(Level.SEVERE, "CraftQuesty was unable to create the necessary directory for data storage. Please create the directory manually");
            pluginLogger.log(Level.SEVERE, "CraftQuesty will not run correctly");
            return;
        }

        this.handler = new CQHandlerBukkit(this);
        this.questyManager = new QuestyManager(storageFolder);

        final Server server = getServer();
        final PluginManager serverPluginManager = server.getPluginManager();

        // register the plugin's various Bukkit listeners
        serverPluginManager.registerEvents(new CQBlockListener(this), this);
        serverPluginManager.registerEvents(new CQEntityListener(this), this);
        serverPluginManager.registerEvents(new CQPlayerListener(this), this);

        // register the questy listener
        questyManager.getEventManager().register(new CQQuestListener(handler));
    }

    @Override
    public void onDisable() {
        pluginLogger = null;
    }

    public QuestyManager getQuestyManager() {
        return questyManager;
    }
}
