package com.jitterted.ebp.blackjack.domain;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    private boolean playerDone;

    public Game(Deck deck) {
        this.deck = deck;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public String determineOutcome() {
        if (playerHand.isBusted()) {
            return "You Busted, so you lose.  💸";
        } else if (dealerHand.isBusted()) {
            return "Dealer went BUST, Player wins! Yay for you!! 💵";
        } else if (playerHand.beats(dealerHand)) {
            return "You beat the Dealer! 💵";
        } else if (playerHand.pushes(dealerHand)) {
            return "Push: Nobody wins, we'll call it even.";
        } else {
            return "You lost to the Dealer. 💸";
        }
    }

    public void dealerTurn() {
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

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
    }

    public void playerStands() {
        playerDone = true;
    }

    public boolean isPlayerDone() {
        return playerDone;
    }

}
