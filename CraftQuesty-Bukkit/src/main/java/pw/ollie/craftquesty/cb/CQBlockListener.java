package pw.ollie.craftquesty.cb;

import org.bukkit.event.Listener;

/**
 * Listens to Bukkit {@link Event}s which are {@link BlockEvent}s for quest handling purposes.
 */
public final class CQBlockListener implements Listener {
    private final CraftQuestyPlugin cqPlugin;

    public CQBlockListener(CraftQuestyPlugin cqPlugin) {
        this.cqPlugin = cqPlugin;
    }
}
