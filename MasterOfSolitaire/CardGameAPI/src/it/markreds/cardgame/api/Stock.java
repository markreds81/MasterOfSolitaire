/*
 * CardGame API framework per lo sviluppo di giochi di carte.
 * Progetto realizzato da Marco Rossi <marco@markreds.it> per l'esame finale
 * del corso "Algoritmi e Strutture di dati" della prof.ssa Elena Lodi, c.d.l.
 * in Informatica, a.a. 2011/2012, Dipartimento di S.M.F.N. dell'Università di
 * Siena.
 * 
 * Modulo: Stack.java
 * 
 * Creato il: 21-04-2012
 * 
 * Classe per rappresentare un mazzo di 52 carte da gioco con la possibilità
 * di mescolarle in maniera causale.
 */
package it.markreds.cardgame.api;

import it.markreds.cardgame.util.CardUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class Stock extends Pile {
    
    public Stock() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
    
    public void shuffle(int seed) {
        Vector<Card> items = new Vector();
        while (!cards.isEmpty()) {
            items.add(cards.remove(0));
        }
        
        Random random = new Random();
        if (seed != -1) {
            random.setSeed(seed);
        } else {
            seed = random.nextInt(1000000);
            random.setSeed(seed);
        }
        
        while (!items.isEmpty()) {
            int i = random.nextInt(items.size());
            cards.add(items.elementAt(i));
            items.removeElementAt(i);
        }
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
            cards.lastElement().draw(g, c);
        }
    }
}
