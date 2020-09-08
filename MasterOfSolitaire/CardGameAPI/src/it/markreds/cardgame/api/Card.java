/*
 * CardGame API framework per lo sviluppo di giochi di carte.
 * Progetto realizzato da Marco Rossi <marco@markreds.it> per l'esame finale
 * del corso "Algoritmi e Strutture di dati" della prof.ssa Elena Lodi, c.d.l.
 * in Informatica, a.a. 2011/2012, Dipartimento di S.M.F.N. dell'Università di
 * Siena.
 * 
 * Card.java, 
 * 
 * Creato il: 21-04-2012
 * 
 * Classe astratta per rappresentare una carta da gioco. Le classi discendenti
 * da questa devo implementare i metodi necessari per disegnare su schermo la
 * carta.
 */
package it.markreds.cardgame.api;

import it.markreds.cardgame.util.CardUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class Card implements Serializable {    
    public static enum Rank {
        ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK,
        QUEEN, KING;
        
        public Rank getNext() {
            return (this.ordinal() < Rank.values().length - 1) ? Rank.values()[this.ordinal() + 1] : null;
        }
        
        public Rank getPrevious() {
            return (this.ordinal() > 0) ? Rank.values()[this.ordinal() - 1] : null;
        }
    }
    
    public static enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;
        
        public Rank getNext() {
            return (this.ordinal() < Rank.values().length - 1) ? Rank.values()[this.ordinal() + 1] : null;
        }
        
        public Rank getPrevious() {
            return (this.ordinal() > 0) ? Rank.values()[this.ordinal() - 1] : null;
        }
    }
    
    private Rank rank;
    private Suit suit;
    private ImageIcon image;
    private Dimension size;
    private Point location;
    private boolean faceUp;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource(CardUtil.getImagePath(rank, suit));
        image = new ImageIcon(url);
        size = new Dimension(image.getIconWidth(), image.getIconHeight());
        faceUp = false;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    public Image getImage() {
        return image.getImage();
    }
    
    public Dimension getSize() {
        return size;
    }
    
    public Point getLocation() {
        return location;
    }
    
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }
    
    public void setLocation(int x, int y) {
        location = new Point(x, y);
    }
    
    public void turnFaceUp() {
        faceUp = true;
    }
    
    public void turnFaceDown() {
        faceUp = false;
    }
    
    public void flip() {
        if (!faceUp) {
            turnFaceUp();
        } else {
            turnFaceDown();
        }
    }
    
    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(location.x, location.y, size.width, size.height);
        return (rect.contains(p));
    }
    
    public boolean isRed() {
        return (suit == Suit.DIAMONDS) || (suit == Suit.HEARTS);
    }
    
    public boolean isFaceUp() {
        return faceUp;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card c = (Card) obj;
            return (c.getRank() == rank && c.getSuit() == suit);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.rank != null ? this.rank.hashCode() : 0);
        hash = 23 * hash + (this.suit != null ? this.suit.hashCode() : 0);
        return hash;
    }
    
    public void draw(Graphics g, Component c) {
        // Background
        RoundRectangle2D border = new RoundRectangle2D.Double(location.x, location.y, CardUtil.DEFAULT_WIDTH - 1, CardUtil.DEFAULT_HEIGHT - 1, 20, 20);
        g.setClip(border);
        
        if (!faceUp) {
            g.setColor(Color.blue);
            g.fillRect(location.x, location.y, CardUtil.DEFAULT_WIDTH - 1, CardUtil.DEFAULT_HEIGHT - 1);
        } else {
            g.setColor(Color.white);
            g.fillRect(location.x, location.y, CardUtil.DEFAULT_WIDTH - 1, CardUtil.DEFAULT_HEIGHT - 1);
            image.paintIcon(c, g, location.x + 4, location.y + 6);
        }
        
        g.setClip(null);
        
        // Frame
        g.setColor(Color.black);
        g.drawRoundRect(location.x, location.y, CardUtil.DEFAULT_WIDTH - 1, CardUtil.DEFAULT_HEIGHT - 1, 20, 20);
    }
    
    @Override
    public String toString() {
        String s = null;
        switch (this.rank) {
            case ACE:
                s = "A"; break;
            case DEUCE:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
                s = String.valueOf(this.rank); break;
            case TEN:
                s = "T"; break;
            case JACK:
                s = "J"; break;
            case QUEEN:
                s = "Q"; break;
            case KING:
                s = "K"; break;
        }
        
        String result = null;
        switch (this.suit) {
            case HEARTS:
                result = String.format("%s%s", s, "\u2665"); break;
            case DIAMONDS:
                result = String.format("%s%s", s, "\u2666"); break;
            case CLUBS:
                result = String.format("%s%s", s, "\u2663"); break;
            case SPADES:
                result = String.format("%s%s", s, "\u2660"); break;
        }
        
        return result;
    }
}
