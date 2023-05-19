package com.r2ha.blackjack.adaptor.in.console;

import com.r2ha.blackjack.domain.Game;
import com.r2ha.blackjack.domain.Hand;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleGame {

    private static final Scanner scanner = new Scanner(System.in);
    public static PrintStream stream = System.out;
    private final Game game;

    public static void directOutputTo(PrintStream printStream) {
        stream = printStream;
    }

    private static void resetScreen() {
        println(ansi().reset());
    }

    private static void println(Object toPrint) {
        stream.println(toPrint);
    }

    private static void println() {
        stream.println();
    }

    private static void print(Object toPrint) {
        stream.print(toPrint);
    }

    public ConsoleGame(Game game) {
        this.game = game;
    }

    private static void displayWelcomeScreen() {
        AnsiConsole.systemInstall();
        println(ansi()
                             .bgBright(Ansi.Color.WHITE)
                             .eraseScreen()
                             .cursor(1, 1)
                             .fgGreen().a("Welcome to")
                             .fgRed().a(" JitterTed's")
                             .fgBlack().a(" Blackjack game"));
    }

    private static void waitForEnterFromUser() {
        println(ansi()
                             .cursor(3, 1)
                             .fgBrightBlack().a("Hit [ENTER] to start..."));

        scanner.nextLine();
    }

    public void start() {
        displayWelcomeScreen();

        waitForEnterFromUser();

        game.initialDeal();

        playerPlays();

        game.dealerTurn();

        displayGameState(game.dealerHand(), game.playerHand(), true);

        displayFinalResult(game.determineOutcome());

        resetScreen();
    }

    private static void displayFinalResult(Game.GameResult result) {
        if (result == Game.GameResult.PLAYER_BUST) {
            println("You Busted, so you lose.  💸");
        } else if (result == Game.GameResult.DEALER_BUST) {
            println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (result == Game.GameResult.PLAYER_WINS) {
            println("You beat the Dealer! 💵");
        } else if (result == Game.GameResult.DRAW) {
            println("Push: Nobody wins, we'll call it even.");
        } else {
            println("You lost to the Dealer. 💸");
        }
    }
    private String inputFromPlayer() {
        println("[H]it or [S]tand?");
        return scanner.nextLine();
    }

    private void playerPlays() {
        while (!game.isPlayerDone()) {
            displayGameState(game.dealerHand(), game.playerHand(), false);
            handle(inputFromPlayer());
        }
    }

    private void handle(String command) {
        if (command.toLowerCase().startsWith("h")) {
            game.playerHits();
        } else if (command.toLowerCase().startsWith("s")) {
            game.playerStands();
        }
    }

    private void displayGameState(final Hand dealerHand, final Hand playerHand, final boolean handFinished) {
        print(ansi().eraseScreen().cursor(1, 1));
        println("Dealer has: ");

        if (handFinished) {
            println(ConsoleHand.cardsAsString(dealerHand.cards()));
            println(" (" + dealerHand.value() + ")");
        } else {
            println(ConsoleHand.displayFaceUpCard(dealerHand));
            // second card is the hole card, which is hidden, or "face down"
            displayBackOfCard();
        }

        println();
        println("Player has: ");
        println(ConsoleHand.cardsAsString(playerHand.cards()));
        println(" (" + playerHand.value() + ")");
    }

    private void displayBackOfCard() {
        print(
                ansi()
                        .cursorUp(7)
                        .cursorRight(12)
                        .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("└─────────┘"));
    }

}
