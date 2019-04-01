package pw.ollie.craftquesty.listener.mc;

import org.bukkit.event.Listener;

import pw.ollie.craftquesty.CraftQuestyPlugin;

/**
 * Listens to Bukkit {@link Event}s which are {@link EntityEvent}s for quest handling purposes.
 */
public final class CQEntityListener implements Listener {
    private final CraftQuestyPlugin cqPlugin;

    public CQEntityListener(CraftQuestyPlugin cqPlugin) {
        this.cqPlugin = cqPlugin;
    }
}
