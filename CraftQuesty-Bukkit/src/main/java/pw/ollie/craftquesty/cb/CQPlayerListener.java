package pw.ollie.craftquesty.cb;

import com.volumetricpixels.questy.QuestInstance;
import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.objective.ObjectiveProgress;
import com.volumetricpixels.questy.objective.Outcome;
import com.volumetricpixels.questy.objective.OutcomeProgress;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * Listens to Bukkit {@link Event}s which are PlayerEvents for quest handling purposes.
 */
public final class CQPlayerListener implements Listener {
    private final QuestManager questManager;

    public CQPlayerListener(CraftQuestyPlugin cqPlugin) {
        this.questManager = cqPlugin.getQuestyManager().getQuestManager();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        // this method updates player quests relating to killing other players
        // this includes: killing a specific player, killer any single player, or killing a specific number of players

        final Player killed = event.getEntity();
        final EntityDamageEvent lastDamage = killed.getLastDamageCause();

        if (!(lastDamage instanceof EntityDamageByEntityEvent)) {
            return;
        }

        final EntityDamageByEntityEvent byEntity = (EntityDamageByEntityEvent) lastDamage;
        final Entity entityKiller = byEntity.getDamager();

        if (!(entityKiller instanceof Player)) {
            return;
        }

        final Player killer = (Player) entityKiller;
        final UUID killerID = killer.getUniqueId();
        final Collection<QuestInstance> killerQuestInstances = this.questManager.getQuestInstances(killerID.toString());

        for (QuestInstance questInstance : killerQuestInstances) {
            final ObjectiveProgress objectiveProgress = questInstance.getCurrentObjective();
            final Set<OutcomeProgress> outcomeProgresses = objectiveProgress.getOutcomeProgresses();

            for (OutcomeProgress outcomeProgress : outcomeProgresses) {
                final Outcome outcome = outcomeProgress.getInfo();
                final String[] typeSegments = outcome.getType().split("_");
                final int length = typeSegments.length;

                if (length == 1) {
                    if (typeSegments[0].equals("killplayer") && outcomeProgress.getProgress().toString().equals("0")) { // case-sensitive
                        outcomeProgress.setProgress(1);
                        questInstance.objectiveComplete(objectiveProgress, outcomeProgress);
                    }
                } else if (length > 1) {
                    if (typeSegments[0].equals("killplayer") && outcomeProgress.getProgress().toString().equals("0")) {
                        final String playerID = typeSegments[1];
                        if (killed.getUniqueId().toString().equals(playerID)) {
                            outcomeProgress.setProgress(1);
                            questInstance.objectiveComplete(objectiveProgress, outcomeProgress);
                        }
                    } else if (typeSegments[0].equals("killplayers")) {
                        final int killsNeeded = Integer.valueOf(typeSegments[1]); // if this causes an exception, the quest is wrongly made
                        outcomeProgress.setProgress(Integer.valueOf(outcomeProgress.getProgress().toString()) + 1);

                        if (Integer.valueOf(outcomeProgress.getProgress().toString()) >= killsNeeded) {
                            questInstance.objectiveComplete(objectiveProgress, outcomeProgress);
                        }
                    }
                }
            }
        }
    }
}
