package pw.ollie.craftquesty.cb;

import org.bukkit.event.Listener;

/**
 * Listens to Bukkit {@link Event}s which are {@link EntityEvent}s for quest handling purposes.
 */
public final class CQEntityListener implements Listener {
    private final CraftQuestyPlugin cqPlugin;

    public CQEntityListener(CraftQuestyPlugin cqPlugin) {
        this.cqPlugin = cqPlugin;
    }
}
