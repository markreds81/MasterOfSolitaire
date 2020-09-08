/*
 * Implementazione del gioco solitario Whitehead.
 * Progetto realizzato da Marco Rossi <marco@markreds.it> per l'esame finale
 * del corso "Algoritmi e Strutture di dati" della prof.ssa Elena Lodi, c.d.l.
 * in Informatica, a.a. 2011/2012, Dipartimento di S.M.F.N. dell'Università di
 * Siena.
 * 
 * Modulo: Foundation.java
 * 
 * Creato il: 18-08-2012
 * 
 * Classe per rappresentare lo stato del gioco.
 */
package it.markreds.whitehead;

import it.markreds.cardgame.api.Card;
import it.markreds.cardgame.api.Pile;
import it.markreds.cardgame.api.Stock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class WhiteheadState {
    private Stock deck;
    private Pile waste;
    private Pile stack;
    private Column[] column;
    private Foundation[] foundation;
    private int score;
    
    public WhiteheadState(Stock deck, Pile waste, Column[] column, Foundation[] foundation, int score) {
        this.deck = (Stock) copy(deck);
        this.waste = (Pile) copy(waste);
        this.column = (Column[]) copy(column);
        this.foundation = (Foundation[]) copy(foundation);
        this.score = score;
    }
    
    private Object copy(Serializable source) {
        Object copy;
        
        try {
            // Serialize object
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(source);
            out.flush();
            out.close();
            
            // De-serialize object into new buffer
            byte[] data = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream in = new ObjectInputStream(bais);
            copy = in.readObject();
            in.close();
        } catch (IOException ex) {
            copy = null;
        }catch (ClassNotFoundException ex) {
            copy = null;
        }
        
        return copy;
    }
    
    public Stock getDeck() {
        return deck;
    }
    
    public Pile getWaste() {
        return waste;
    }
    
    public Pile getStack() {
        return stack;
    }
    
    public Column[] getColumn() {
        return column;
    }
    
    public Foundation[] getFoundation() {
        return foundation;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean isGameWon() {
        int count = 0;
        for (int i = 0; i < foundation.length; i++) {
            count += foundation[i].size();
        }
        return (count == (Card.Rank.values().length * Card.Suit.values().length));
    }
}
