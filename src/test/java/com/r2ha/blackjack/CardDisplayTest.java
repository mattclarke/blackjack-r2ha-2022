package com.r2ha.blackjack;
import com.r2ha.blackjack.adaptor.in.console.ConsoleCard;
import com.r2ha.blackjack.domain.Card;
import com.r2ha.blackjack.domain.Rank;
import com.r2ha.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardDisplayTest {
    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    @Test
    public void displayTenAsString(){
        Card card = new Card(DUMMY_SUIT, Rank.TEN);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[31m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    public void displayNonTenAsString(){
        Card card = new Card(DUMMY_SUIT, Rank.SEVEN);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[31m┌─────────┐[1B[11D│7        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        7│[1B[11D└─────────┘");
    }
}
