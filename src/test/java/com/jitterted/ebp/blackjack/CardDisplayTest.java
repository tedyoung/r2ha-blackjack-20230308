package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CardDisplayTest {

    @Test
    void displayTenAsString() {
        Card card = new Card(Suit.SPADES, Rank.TEN);

        assertThat(card.display())
                .isEqualTo("[30m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♠    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }
}
