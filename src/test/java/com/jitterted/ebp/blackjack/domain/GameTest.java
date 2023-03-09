package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameTest {

    @Test
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Game game = new Game(StubDeck.playerHitsAndGoesBust());
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() {
        Game game = new Game(StubDeck.playerStandsAndBeatsDealer());
        game.initialDeal();

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEAT_DEALER);
    }

    @Test
    void playerDealtHandWithSameValueAsDealerThenPlayerPushesDealer() {
        Game game = new Game(StubDeck.playerPushesWithDealer());
        game.initialDeal();

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_PUSHES);
    }

    @Test
    void playerDealtBlackjackUponInitialDealThenWinsBlackjack() {
        Deck playerDealtBlackjack = new StubDeck(Rank.ACE, Rank.NINE,
                                                 Rank.JACK, Rank.EIGHT);
        Game game = new Game(playerDealtBlackjack);

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_WINS_BLACKJACK);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void noBlackjackDealtPlayerIsNotDoneYet() {
        Deck notDealtBlackjack = new StubDeck(Rank.EIGHT, Rank.NINE,
                                              Rank.THREE, Rank.EIGHT);
        Game game = new Game(notDealtBlackjack);

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    void playerWithHandValueOf21And3CardsIsNotBlackjack() {
        Deck twentyOneWithThreeCards = new StubDeck(Rank.EIGHT, Rank.NINE,
                                                    Rank.THREE, Rank.EIGHT,
                                                    Rank.TEN);
        Game game = new Game(twentyOneWithThreeCards);

        game.initialDeal();
        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEAT_DEALER);
    }

    @Test
    public void standResultsInDealerDrawingCardOnTheirTurn() throws Exception {
        Deck dealerDrawsAdditionalCardOnTheirTurnDeck =
                new StubDeck(Rank.TEN,  Rank.QUEEN,
                             Rank.NINE, Rank.FIVE,
                             Rank.SIX);
        Game game = new Game(dealerDrawsAdditionalCardOnTheirTurnDeck);
        game.initialDeal();

        game.playerStands();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }

}