package pw.ollie.craftquesty;

import java.util.logging.Logger;

public interface CQHandler {
    boolean isOnline(String quester);

    void giveReward(String quester, String rewardName, int quantity);

    Logger getLogger();
}
