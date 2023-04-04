package com.r2ha.blackjack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleHand {
    static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.dealerFaceUpCard());
    }

    public static String cardsAsString(Stream<Card> cards) {
        return cards.map(ConsoleCard::display)
                    .collect(Collectors.joining(
                            ansi().cursorUp(6).cursorRight(1).toString()));
    }

}
