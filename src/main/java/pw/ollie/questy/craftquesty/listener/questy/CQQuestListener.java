package pw.ollie.questy.craftquesty.listener.questy;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.event.Listen;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import pw.ollie.questy.craftquesty.CraftQuestyPlugin;

import java.util.UUID;
import java.util.regex.Pattern;

public final class CQQuestListener {
    private static final int INVENTORY_EMPTY = -1;

    private final CraftQuestyPlugin plugin;

    public CQQuestListener(final CraftQuestyPlugin plugin) {
        this.plugin = plugin;
    }

    @Listen
    public void onQuestComplete(QuestCompleteEvent event) {
        final Quest questInfo = event.getQuestInfo();
        final String[] rewards = questInfo.getRewards();
        final String quester = event.getQuester();
        final UUID questerId = UUID.fromString(quester);
        final Player player = this.plugin.getServer().getPlayer(questerId);

        if (player == null) {
            // the player has completed a quest without being online. w0t.
            // todo: implement a waiting list so if this does happen the player can be rewarded when they next log on
            return;
        }

        if (rewards == null || rewards.length == 0) {
            return;
        }

        for (final String reward : rewards) {
            final String[] split = reward.split(Pattern.quote("-"));

            try {
                final String rewardName = split[0];
                final int quantity = Integer.parseInt(split[1]);
                final Material material = Material.getMaterial(rewardName);
                final ItemStack stack = new ItemStack(material, quantity);

                final PlayerInventory inventory = player.getInventory();
                if (inventory.firstEmpty() == INVENTORY_EMPTY) {
                    // TODO: implement a waiting list so they can be rewarded later
                    continue; // continue looping so we can later add other rewards to waiting list
                }

                player.getInventory().addItem(stack);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                // the person who wrote the quest messed up, smh
                this.plugin.getLogger()
                        .severe("The quest '" + questInfo.getName()
                                + "' is incorrectly configured. Rewards for this quest will not work");
            }
        }
    }
}
