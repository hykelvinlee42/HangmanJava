package ca.cmpt213.a4.onlinehangman.model;

/**
 * Game class models the information about a game.
 * Data includes id, word, length of the word, number of total guesses, number of incorrect guesses, game status and game progress.
 * @author Ho Yin Kelvin Lee
 * @author hyl30@sfu.ca
 * @version 1.0
 * @since 1.0
 */

public class Game {

    public enum GameStatus {
        ACTIVE, WON, LOST;
    }

    private long id;
    private String word;
    private int numTotalGuesses;
    private int numIncorrectGuesses;
    private GameStatus status;
    private String progress;
    private int wordLength;

    public Game() {

    }

    public Game(long id, String word, int numTotalGuesses, int numIncorrectGuesses, GameStatus status) {
        this.id = id;
        this.word = word;
        this.numTotalGuesses = numTotalGuesses;
        this.numIncorrectGuesses = numIncorrectGuesses;
        this.status = status;
        String progress = "";
        for (int i = 0; i < this.word.length(); i++) {
            progress += "-";
        }
        this.progress = progress;
        this.wordLength = this.word.length();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNumTotalGuesses() {
        return numTotalGuesses;
    }

    public void setNumTotalGuesses(int numTotalGuesses) {
        this.numTotalGuesses = numTotalGuesses;
    }

    public int getNumIncorrectGuesses() {
        return numIncorrectGuesses;
    }

    public void setNumIncorrectGuesses(int numIncorrectGuesses) {
        this.numIncorrectGuesses = numIncorrectGuesses;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(char guess) {
        char[] progress = this.progress.toCharArray();
        for (int i = 0; i < this.word.length(); i++) {
            if (this.word.charAt(i) == guess) {
                progress[i] = guess;
            }
        }
        this.progress = String.valueOf(progress);
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getWordLength() {
        return wordLength;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }
}
