package com.jitterted.ebp.blackjack.application.port;

import com.jitterted.ebp.blackjack.domain.Game;

// PORT definition for Notifying
public interface GameMonitor {
    void roundCompleted(Game game);
}
