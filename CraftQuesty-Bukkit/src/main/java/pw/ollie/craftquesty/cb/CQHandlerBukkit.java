package pw.ollie.craftquesty.cb;

import pw.ollie.craftquesty.CQHandler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;
import java.util.logging.Logger;

public class CQHandlerBukkit implements CQHandler {
    private static final int INVENTORY_EMPTY = -1;

    private final CraftQuestyPlugin plugin;

    public CQHandlerBukkit(CraftQuestyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isOnline(String quester) {
        return plugin.getServer().getPlayer(UUID.fromString(quester)) != null;
    }

    @Override
    public void giveReward(String quester, String rewardName, int quantity) {
        Player player = plugin.getServer().getPlayer(UUID.fromString(quester));
        final Material material = Material.getMaterial(rewardName);
        final ItemStack stack = new ItemStack(material, quantity);
        final PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() == INVENTORY_EMPTY) {
            // TODO: implement a waiting list so they can be rewarded later
            return;
        }
        player.getInventory().addItem(stack);
    }

    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }
}
