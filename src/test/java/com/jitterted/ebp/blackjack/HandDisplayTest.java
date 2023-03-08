package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HandDisplayTest {

    @Test
    void displayFaceUpCard() {
        Hand hand = new Hand(List.of(
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.CLUBS, Rank.EIGHT)
        ));

        assertThat(ConsoleHand.displayFaceUpCard(hand))
                .isEqualTo("[31m┌─────────┐[1B[11D│5        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        5│[1B[11D└─────────┘");
    }

    @Test
    void cardsAsString() {
        Hand hand = new Hand(List.of(new Card(Suit.DIAMONDS, Rank.JACK),
                                     new Card(Suit.SPADES, Rank.FIVE)));

        assertThat(hand.cardsAsString())
                .isEqualTo("[31m┌─────────┐[1B[11D│J        │[1B[11D│         │[1B[11D│    ♦    │[1B[11D│         │[1B[11D│        J│[1B[11D└─────────┘[6A[1C[30m┌─────────┐[1B[11D│5        │[1B[11D│         │[1B[11D│    ♠    │[1B[11D│         │[1B[11D│        5│[1B[11D└─────────┘");
    }
}