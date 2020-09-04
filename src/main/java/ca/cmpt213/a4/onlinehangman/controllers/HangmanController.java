package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * HangmanController class controls all methods of the designed Spring Boot + Thymeleaf.
 * @author Ho Yin Kelvin Lee
 * @author hyl30@sfu.ca
 * @version 1.0
 * @since 1.0
 */

@Controller
public class HangmanController {

    final int MAX_NUM_INCORRECT_GUESS = 7;
    private Game reusableGame;
    private List<Game> games;
    private AtomicLong nextId;

    @PostConstruct
    public void hangmanControllerInit() {
        this.reusableGame = new Game();
        this.games = new ArrayList<>();
        this.nextId = new AtomicLong();
    }

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";
    }

    @PostMapping("/game")
    public String showGamePage(Model model) {
        String word = "";
        try {
            Random random = new Random();
            File wordList = new File("src/commonWords.txt");
            Scanner myReader = new Scanner(wordList);
            while (myReader.hasNextLine()) {
                word = myReader.nextLine();
                if (random.nextInt(916) == 0) {
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException error) {
            System.out.println("An error occurred when reading commonWords.txt.");
        }
        Game newGame = new Game(nextId.incrementAndGet(), word, 0, 0, Game.GameStatus.ACTIVE);
        this.reusableGame = newGame;
        this.games.add(newGame);
        model.addAttribute("game", this.reusableGame);
        return "game";
    }

    @PostMapping(value = "/game", params = "guess")
    public String getGuessAndUpdate(Model model, @RequestParam String guess) {
        if (guess != "") {
            char[] guessChar = guess.toLowerCase().toCharArray();
            if (this.reusableGame.getWord().indexOf(guessChar[0]) > -1) {
                this.reusableGame.setProgress(guessChar[0]);
            }
            else {
                this.reusableGame.setNumIncorrectGuesses(this.reusableGame.getNumIncorrectGuesses() + 1);
            }
            this.reusableGame.setNumTotalGuesses(this.reusableGame.getNumTotalGuesses() + 1);
            model.addAttribute("game", this.reusableGame);
            if (this.reusableGame.getNumIncorrectGuesses() >= MAX_NUM_INCORRECT_GUESS) {
                this.reusableGame.setStatus(Game.GameStatus.LOST);
                return "gameover";
            }
            if (this.reusableGame.getProgress().equals(this.reusableGame.getWord())) {
                this.reusableGame.setStatus(Game.GameStatus.WON);
                return "gameover";
            }
            return "game";
        }
        model.addAttribute("game", this.reusableGame);
        return "game";
    }

    @ExceptionHandler(GameNotFoundException.class)
    public String gameNotFoundExceptionHandler() {
        return showGameNotFoundPage();
    }

    @GetMapping("/game/{id}")
    public String showGamePageWithId(@PathVariable("id") long gameId, Model model) {
        Iterator<Game> iterator = this.games.iterator();
        while (iterator.hasNext()) {
            this.reusableGame = iterator.next();
            if (this.reusableGame.getId() == gameId) {
                model.addAttribute("game", this.reusableGame);
                if (this.reusableGame.getNumIncorrectGuesses() >= MAX_NUM_INCORRECT_GUESS || this.reusableGame.getProgress().equals(this.reusableGame.getWord())) {
                    return "gameover";
                }
                return "game";
            }
        }
        throw new GameNotFoundException();
    }

    @GetMapping("/gamenotfound")
    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "Game ID not found")
    public String showGameNotFoundPage() {
        return "gamenotfound";
    }
}
