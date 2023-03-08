package com.jitterted.ebp.blackjack;

public class ConsoleHand {

    // transforms DOMAIN OBJECT (Hand) -> CONSOLE I/O (String)
    static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.faceUpCard());
    }
}
