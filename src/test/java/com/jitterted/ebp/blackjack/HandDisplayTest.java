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

        assertThat(hand.displayFaceUpCard())
                .isEqualTo("[31m┌─────────┐[1B[11D│5        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        5│[1B[11D└─────────┘");
    }
}