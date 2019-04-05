package pw.ollie.craftquesty;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.event.Listen;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;

import java.util.regex.Pattern;

public final class CQQuestListener {
    private final CQHandler handler;

    public CQQuestListener(CQHandler handler) {
        this.handler = handler;
    }

    @Listen
    public void onQuestComplete(QuestCompleteEvent event) {
        final Quest questInfo = event.getQuestInfo();
        final String[] rewards = questInfo.getRewards();
        final String quester = event.getQuester();

        if (!handler.isOnline(quester)) {
            // the player has completed a quest without being online. w0t.
            // todo: implement a waiting list so if this does happen the player can be rewarded when they next log on
            return;
        }

        if (rewards.length == 0) {
            return;
        }

        for (final String reward : rewards) {
            final String[] split = reward.split(Pattern.quote("-"));

            try {
                final String rewardName = split[0];
                final int quantity = Integer.parseInt(split[1]);
                handler.giveReward(quester, rewardName, quantity);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                // the person who wrote the quest messed up, smh
                handler.getLogger().severe("The quest '" + questInfo.getName()
                                + "' is incorrectly configured. Rewards for this quest will not work");
            }
        }
    }
}
