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
 * Classe per rappresentare una generica pila di carte.
 */
package it.markreds.cardgame.api;

import it.markreds.cardgame.util.CardUtil;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class Pile implements Serializable {
    public static final int SPREAD_NONE = 0;
    public static final int SPREAD_NORTH = 1;
    public static final int SPREAD_EAST = 2;
    public static final int SPREAD_SOUTH = 3;
    public static final int SPREAD_WEST = 4;
    
    protected Vector<Card> cards;
    private Point location;
    private Point nextLocation;
    private int spreadingDirection;
    private int spreadingDelta;
    
    public Pile() {
        cards = new Vector();
        location = new Point(0, 0);
        nextLocation = new Point(0, 0);
        spreadingDirection = SPREAD_NONE;
        spreadingDelta = 0;
    }
    
    private void relocateCards() {
        int x = location.x;
        int y = location.y;
        for (Card c : cards) {
            c.setLocation(x, y);
            switch (spreadingDirection) {
                case SPREAD_NORTH: y -= spreadingDelta; break;
                case SPREAD_EAST: x += spreadingDelta; break;
                case SPREAD_SOUTH: y += spreadingDelta; break;
                case SPREAD_WEST: x -= spreadingDelta; break;
            }
        }
        nextLocation = new Point(x, y);
    }
    
    public boolean isValid(Card c) {
        return true;
    }

    public boolean isValid(Pile p) {
        return true;
    }
    
    public void draw(Graphics g, Component c) {
        if (!cards.isEmpty()) {
            for (Enumeration<Card> e = cards.elements(); e.hasMoreElements(); ) {
                e.nextElement().draw(g, c);
            }
        }
    }
    
    public Card push(Card c) {
        if (!isValid(c)) {
            throw new IllegalArgumentException();
        }
        cards.add(c);
        c.setLocation(nextLocation);
        switch (spreadingDirection) {
            case SPREAD_NORTH: nextLocation.y -= spreadingDelta; break;
            case SPREAD_EAST: nextLocation.x += spreadingDelta; break;
            case SPREAD_SOUTH: nextLocation.y += spreadingDelta; break;
            case SPREAD_WEST: nextLocation.x -= spreadingDelta; break;
        }
        return c;
    }
    
    public Card pop() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException();
        }
        switch (spreadingDirection) {
            case SPREAD_NORTH: nextLocation.y += spreadingDelta; break;
            case SPREAD_EAST: nextLocation.x -= spreadingDelta; break;
            case SPREAD_SOUTH: nextLocation.y -= spreadingDelta; break;
            case SPREAD_WEST: nextLocation.x += spreadingDelta; break;
        }
        return cards.remove(cards.size() - 1);
    }
    
    public Pile pop(Card c) {
        Pile p = new Pile();
        
        for ( ; !top().equals(c) && !isEmpty(); ) {
            p.push(pop());
        }
        
        if (!isEmpty()) {
            p.push(pop());
        }
        
        p.setSpreadingDirection(spreadingDirection);
        p.setSpreadingDelta(spreadingDelta);
        
        return p;
    }
    
    public Card top() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException();
        }
        return cards.lastElement();
    }
    
    public Card elementAt(int i) {
        return cards.elementAt(i);
    }
    
    public Card elementAt(Point p) {
        boolean found = false;
        Card card = null;
        for (int i = cards.size() - 1; !found && i >= 0; i--) {
            card = cards.elementAt(i);
            found = card.contains(p);
        }
        return card;
    }
    
    public Card firstFaceUp() {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (card.isFaceUp()) {
                return card;
            }
        }
        
        return null;
    }
    
    public int size() {
        return cards.size();
    }
    
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    public boolean contains(Card c) {
        return cards.contains(c);
    }
    
    public boolean contains(Point p) {
        Rectangle rect = null;
        switch (spreadingDirection) {
            case SPREAD_NONE:
                rect = new Rectangle(location.x, location.y, CardUtil.DEFAULT_WIDTH, CardUtil.DEFAULT_HEIGHT);
                break;
            case SPREAD_NORTH:
                int height = CardUtil.DEFAULT_HEIGHT + (cards.size() - 1) * spreadingDelta;
                rect = new Rectangle(location.x - height, location.y, height, CardUtil.DEFAULT_WIDTH);
                break;
            case SPREAD_EAST:
                rect = new Rectangle(location.x, location.y, CardUtil.DEFAULT_WIDTH + (cards.size() - 1) * spreadingDelta, CardUtil.DEFAULT_HEIGHT);
                break;
            case SPREAD_SOUTH:
                rect = new Rectangle(location.x, location.y, CardUtil.DEFAULT_WIDTH, CardUtil.DEFAULT_HEIGHT + (cards.size() - 1) * spreadingDelta);
                break;
            case SPREAD_WEST:
                int width = CardUtil.DEFAULT_WIDTH + (cards.size() - 1) * spreadingDelta;
                rect = new Rectangle(location.x - width, location.y, width, CardUtil.DEFAULT_HEIGHT );
                break;
        }
        
        return rect.contains(p);
    }
    
    public void reverse() {
        Vector<Card> v = new Vector();
        while (!isEmpty()) {
            v.add(pop());
        }
        cards = v;
    }
    
    public void clear() {
        switch (spreadingDirection) {
            case SPREAD_NORTH: nextLocation.y += spreadingDelta * cards.size(); break;
            case SPREAD_EAST: nextLocation.x -= spreadingDelta * cards.size(); break;
            case SPREAD_SOUTH: nextLocation.y -= spreadingDelta * cards.size(); break;
            case SPREAD_WEST: nextLocation.x += spreadingDelta * cards.size(); break;
        }
        cards.clear();
    }
    
    public Point getLocation() {
        return location;
    }
    
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }
    
    public void setLocation(int x, int y) {
        location = new Point(x, y);
        relocateCards();
    }
    
    public int getSpreadingDirection() {
        return spreadingDirection;
    }
    
    public void setSpreadingDirection(int value) {
        spreadingDirection = value;
        relocateCards();
    }
    
    public int getSpreadingDelta() {
        return spreadingDelta;
    }
    
    public void setSpreadingDelta(int value) {
        spreadingDelta = value;
        relocateCards();
    }
    
    @Override
    public String toString() {
        return cards.toString();
    }
}
