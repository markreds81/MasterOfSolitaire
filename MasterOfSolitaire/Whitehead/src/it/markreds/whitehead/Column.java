/*
 * Implementazione del gioco solitario Whitehead.
 * Progetto realizzato da Marco Rossi <marco@markreds.it> per l'esame finale
 * del corso "Algoritmi e Strutture di dati" della prof.ssa Elena Lodi, c.d.l.
 * in Informatica, a.a. 2011/2012, Dipartimento di S.M.F.N. dell'Università di
 * Siena.
 * 
 * Modulo: Foundation.java
 * 
 * Creato il: 17-08-2012
 * 
 * Classe per rappresentare una pila di carte dello stesso colore e in ordine
 * decrescente.
 */
package it.markreds.whitehead;

import it.markreds.cardgame.api.Card;
import it.markreds.cardgame.api.Pile;
import it.markreds.cardgame.util.CardUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class Column extends Pile {
    
    public Column(List<Card> list) {
        cards.addAll(list);
    }

    @Override
    public boolean isValid(Card c) {
        if (isEmpty()) {
            return (c.getRank() == Card.Rank.KING);
        } else {
            return (c.getRank() == cards.lastElement().getRank().getPrevious() && c.isRed() == cards.lastElement().isRed() && cards.lastElement().isFaceUp());
        }
    }

    @Override
    public boolean isValid(Pile p) {
        return (p.size() == 1 && isValid(p.top()));
    }
    
    @Override
    public void draw(Graphics g, Component c) {
        if (cards.isEmpty()) {
            Point location = getLocation();
            g.setColor(new Color(0, 180, 0));
            g.fillRoundRect(location.x, location.y, CardUtil.DEFAULT_WIDTH, CardUtil.DEFAULT_HEIGHT, 20, 20);
        } else {
            super.draw(g, c);
        }
    }
}
