package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    // WARNING: do not hold onto Domain Objects in the Adapter class
    private final Game game;

    public BlackjackController(Game game) {
        this.game = game;
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        GameView gameView = GameView.from(game);
        model.addAttribute("gameView", gameView);
        return "blackjack";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        model.addAttribute("gameView", GameView.from(game));
        model.addAttribute("outcome", game.determineOutcome().message());
        return "done";
    }

    @PostMapping("/start-game")
    public String startGame() {
        game.initialDeal();
        return redirectBasedOnGameState();
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        return redirectBasedOnGameState();
    }

    @PostMapping("/stand")
    public String standCommand() {
        game.playerStands();
        return redirectBasedOnGameState();
    }

    private String redirectBasedOnGameState() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        } else {
            return "redirect:/game";
        }
    }
}
