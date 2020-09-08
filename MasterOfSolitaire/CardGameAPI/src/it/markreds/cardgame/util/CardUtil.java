/*
 * CardGame API framework per lo sviluppo di giochi di carte.
 * Progetto realizzato da Marco Rossi <marco@markreds.it> per l'esame finale
 * del corso "Algoritmi e Strutture di dati" della prof.ssa Elena Lodi, c.d.l.
 * in Informatica, a.a. 2011/2012, Dipartimento di S.M.F.N. dell'Università di
 * Siena.
 * 
 * Modulo: CardUtil.java
 * 
 * Creato il: 16-08-2012
 * 
 * Carta da gioco del tipo classico "alla fiorentina".
 */
package it.markreds.cardgame.util;

import it.markreds.cardgame.api.Card;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class CardUtil {
    public static final int DEFAULT_HEIGHT = 134;
    public static final int DEFAULT_WIDTH = 88;
    
    private static final String SUITS = "hdcs";
    private static final String FACES = "a23456789tjqk";
    
    public static String getImagePath(Card.Rank rank, Card.Suit suit) {
        int k = 0;  // ACE
        switch (rank) {
            case DEUCE: k = 1; break;
            case THREE: k = 2; break;
            case FOUR:  k = 3; break;
            case FIVE:  k = 4; break;
            case SIX:   k = 5; break;
            case SEVEN: k = 6; break;
            case EIGHT: k = 7; break;
            case NINE:  k = 8; break;
            case TEN:   k = 9; break;
            case JACK:  k = 10; break;
            case QUEEN: k = 11; break;
            case KING:  k = 12; break;
        }
        
        int j = 0;  // HEARTS
        switch (suit) {
            case DIAMONDS:  j = 1; break;
            case CLUBS:     j = 2; break;
            case SPADES:    j = 3; break;
        }
        
        String result = "it/markreds/cardgame/util/card" + CardUtil.FACES.charAt(k) + CardUtil.SUITS.charAt(j) + ".png";
        
        return result;
    }    
}
