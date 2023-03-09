package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameTest {

    @Test
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Game game = new Game(new StubDeck(Rank.TEN, Rank.EIGHT,
                                          Rank.QUEEN, Rank.JACK,
                                          Rank.THREE));
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualTo("You Busted, so you lose.  💸");
    }

    @Test
    void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() {
        Deck playerStandsAndBeatsDealer = new StubDeck(Rank.TEN, Rank.EIGHT,
                                                       Rank.QUEEN, Rank.JACK);
        Game game = new Game(playerStandsAndBeatsDealer);
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualTo("You beat the Dealer! 💵");
    }

    @Test
    void playerDealtHandWithSameValueAsDealerThenPlayerPushesDealer() {
        Game game = new Game(new StubDeck(Rank.TEN, Rank.QUEEN,
                                          Rank.NINE, Rank.NINE));
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualTo("Push: Nobody wins, we'll call it even.");
    }


}