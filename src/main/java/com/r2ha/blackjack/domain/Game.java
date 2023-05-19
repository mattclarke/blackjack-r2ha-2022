package com.r2ha.blackjack.domain;

import com.r2ha.blackjack.adaptor.in.console.ConsoleGame;

import java.io.PrintStream;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    public enum GameResult {
        PLAYER_BUST,
        DEALER_BUST,
        PLAYER_WINS,
        DRAW,
        DEALER_WINS;
    }

    private final Deck deck;
    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();
    private boolean playerDone;

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameResult determineOutcome() {
        if (playerHand.isBusted()) {
            return GameResult.PLAYER_BUST;
        } else if (dealerHand.isBusted()) {
            return GameResult.DEALER_BUST;
        } else if (playerHand.beats(dealerHand)) {
            return GameResult.PLAYER_WINS;
        } else if (playerHand.pushes(dealerHand)) {
            return GameResult.DRAW;
        } else {
            return GameResult.DEALER_WINS;
        }
    }

    public void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
    }

    public void playerStands() {
        playerDone = true;
    }

    public boolean isPlayerDone() {
        return playerDone;
    }

    public Hand playerHand() {
        return new Hand(playerHand.cards().toList());
    }

    public Hand dealerHand() {
        return new Hand(dealerHand.cards().toList());
    }

}
