package pw.ollie.craftquesty.listener.mc;

import org.bukkit.event.Listener;

import pw.ollie.craftquesty.CraftQuestyPlugin;

/**
 * Listens to Bukkit {@link Event}s which are {@link BlockEvent}s for quest handling purposes.
 */
public final class CQBlockListener implements Listener {
    private final CraftQuestyPlugin cqPlugin;

    public CQBlockListener(CraftQuestyPlugin cqPlugin) {
        this.cqPlugin = cqPlugin;
    }
}
