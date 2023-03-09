package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;

public class Game {

    private final Deck deck;
    private final GameMonitor gameMonitor;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    private boolean playerDone;

    public Game(Deck deck) {
        this.deck = deck;
        this.gameMonitor = game -> {};
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.isBlackjack()) {
            return GameOutcome.PLAYER_WINS_BLACKJACK;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEAT_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    // Idempotent
    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    // Can't return Hand (violates Integrity and Point-In-Time rules for Queries)
    // (as well as being misleading to the caller)
    // Instead, we can return:
    // 1. Copy of Hand: misleading to the consumer/client
    // 1a. "Read Only" Interface: HandReadOnly (only query methods)
    // 2. HandView (Value Object): query methods for faceUpCard(), cards(), and value()
    // --> Not a DTO
    public Hand playerHand() {
        return playerHand;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
        updatePlayerDoneStateTo(playerHand.isBlackjack());
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        updatePlayerDoneStateTo(playerHand.isBusted());
    }

    public void playerStands() {
        dealerTurn();
        updatePlayerDoneStateTo(true);
    }

    private void updatePlayerDoneStateTo(boolean isPlayerDone) {
        playerDone = isPlayerDone;
        if (playerDone) {
            gameMonitor.roundCompleted(this);
        }
    }

    public boolean isPlayerDone() {
        return playerDone;
    }

}
