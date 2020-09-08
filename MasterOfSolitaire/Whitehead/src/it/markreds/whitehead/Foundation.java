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
 * Classe per rappresentare una pila di carte che segue le regole del solitario.
 * In questo tipo di pila posso mettere l'asso di un qualunque colore se la
 * pila è vuota, oppure la successiva dello stesso seme.
 */
package it.markreds.whitehead;

import it.markreds.cardgame.api.Card;
import it.markreds.cardgame.api.Pile;
import it.markreds.cardgame.util.CardUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class Foundation extends Pile {

    @Override
    public boolean isValid(Card c) {
        if (isEmpty()) {
            return (c.getRank() == Card.Rank.ACE);
        } else {
            return (c.getRank() == cards.lastElement().getRank().getNext() && c.getSuit() == cards.lastElement().getSuit());
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
            g.setColor(Color.darkGray);
            g.fillRect(location.x, location.y, CardUtil.DEFAULT_WIDTH, CardUtil.DEFAULT_HEIGHT);
            g.setColor(Color.black);
            g.drawRect(location.x, location.y, CardUtil.DEFAULT_WIDTH, CardUtil.DEFAULT_HEIGHT);
        } else {
            super.draw(g, c);
        }
    }
}
