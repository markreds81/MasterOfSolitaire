/*
 * Implementazione del gioco solitario Whitehead.
 * Progetto realizzato da Marco Rossi <marco@markreds.it> per l'esame finale
 * del corso "Algoritmi e Strutture di dati" della prof.ssa Elena Lodi, c.d.l.
 * in Informatica, a.a. 2011/2012, Dipartimento di S.M.F.N. dell'Università di
 * Siena.
 * 
 * Modulo: WhiteheadSolitaire.java
 * 
 * Creato il: 18-08-2012
 * 
 * Classe vista che si occupa di disegnare il gioco e gestire l'ìnterazione con
 * l'utente attraverso il mouse.
 */
package it.markreds.whitehead;

import it.markreds.cardgame.api.Card;
import it.markreds.cardgame.api.Pile;
import it.markreds.cardgame.api.Stock;
import it.markreds.cardgame.util.CardUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEditSupport;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Marco Rossi <marco@markreds.it>
 */
public class WhiteheadSolitaire extends javax.swing.JPanel implements MouseListener, MouseMotionListener {
    private static final int MAX_COLUMN = 7;
    private static final int MAX_FOUNDATION = 4;
    private static final Point DECK_POS = new Point(5, 5);
    private static final Point WASTE_POS = new Point(DECK_POS.x + CardUtil.DEFAULT_WIDTH + 10, 5);
    private static final Point COLUMN_POS = new Point(DECK_POS.x, DECK_POS.y + CardUtil.DEFAULT_HEIGHT + 30);
    private static final Point FOUNDATION_POS = new Point(WASTE_POS.x + CardUtil.DEFAULT_WIDTH + 110, DECK_POS.y);
    private static final Point INFO_POS = new Point(DECK_POS.x, 450);
    private static final Color TABLE_COLOR = new Color(0, 150, 0);
    
    private final UndoableEditSupport undoSupport = new UndoableEditSupport();
    
    private Stock deck;
    private Pile waste;
    private Column[] column;
    private Foundation[] foundation;
    private int score;
    
    private WhiteheadState state = null;
    private Pile dragPile;
    private Point dragPoint;
    
    private class UndoableState extends AbstractUndoableEdit {
        private WhiteheadState undoState;
        
        UndoableState(WhiteheadState s) {
            undoState = s;
        }
        
        @Override
        public boolean canUndo() {
            return true;
        }
        
        @Override
        public boolean canRedo() {
            return true;
        }
        
        @Override
        public void undo() {
            state = undoState;
            revertState();
            score -= 5;
        }
        
        @Override
        public void redo() {
            state = undoState;
            revertState();
            score += 5;
        }
    }
    
    /**
     * Creates new form WhiteheadSolitaire
     */
    public WhiteheadSolitaire() {
        initComponents();
    }
    
    private void addlisteners() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    private void removeListeners() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
    }
    
    private void updateState() {
        undoSupport.postEdit(new UndoableState(state));
        state = new WhiteheadState(deck, waste, column, foundation, score);
        this.repaint();
    }
    
    private void revertState() {
        if (state != null) {
            deck = state.getDeck();
            waste = state.getWaste();
            column = state.getColumn();
            foundation = state.getFoundation();
            state = new WhiteheadState(deck, waste, column, foundation, score);
            dragPile.clear();
            this.repaint();
        }
    }
    
    public UndoableEditSupport getUndoSupport() {
        return undoSupport;
    }
    
    public void newGame() {
        deck = new Stock();
        deck.shuffle(-1);
        deck.setLocation(DECK_POS);
        
        waste = new Pile();
        waste.setLocation(WASTE_POS);
        
        dragPile = new Pile();
        dragPile.setSpreadingDirection(Pile.SPREAD_SOUTH);
        dragPile.setSpreadingDelta(20);
        
        column = new Column[MAX_COLUMN];
        for (int i = 0; i < column.length; i++) {
            Vector<Card> cards = new Vector();
            for (int j = 0; j < i + 1; j++) {
                cards.add(deck.pop());
            }
            column[i] = new Column(cards);
            column[i].top().turnFaceUp();
            column[i].setLocation(COLUMN_POS.x + i * (CardUtil.DEFAULT_WIDTH + 10), COLUMN_POS.y);
            column[i].setSpreadingDirection(Pile.SPREAD_SOUTH);
            column[i].setSpreadingDelta(20);
        }
        
        foundation = new Foundation[MAX_FOUNDATION];
        for (int i = 0; i < foundation.length; i++) {
            foundation[i] = new Foundation();
            foundation[i].setLocation(FOUNDATION_POS.x + i * (CardUtil.DEFAULT_WIDTH + 10), FOUNDATION_POS.y);
            foundation[i].setSpreadingDirection(Pile.SPREAD_SOUTH);
            foundation[i].setSpreadingDelta(2);
        }
        
        score = 0;
        state = new WhiteheadState(deck, waste, column, foundation, score);
        
        this.repaint();        
        addlisteners();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void paintComponent(Graphics g) {
        // Draw background
        g.setColor(TABLE_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw elements
        if (state != null) {
            deck.draw(g, this);
            
            waste.draw(g, this);
            
            for (int i = 0; i < foundation.length; i++) {
                foundation[i].draw(g, this);
            }
            
            int count = waste.size() + dragPile.size();
            for (int i = 0; i < column.length; i++) {
                column[i].draw(g, this);
                count += column[i].size();
            }
            
            g.setColor(Color.black);
            g.drawString("Punteggio: " + score, INFO_POS.x, INFO_POS.y);
            g.drawString("Carte nel mazzo: " + deck.size(), INFO_POS.x, INFO_POS.y + 15);
            g.drawString("Carte sul tavolo: " + count, INFO_POS.x, INFO_POS.y + 30);
            
            dragPile.draw(g, this);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        Point p = me.getPoint();
        if (deck.contains(p) && !deck.isEmpty()) {
            waste.push(deck.pop()).flip();
            updateState();
        }
        
        for (int i = 0; i < column.length; i++) {
            if (column[i].contains(p) && !column[i].isEmpty()) {
                if (!column[i].top().isFaceUp()) {
                    column[i].top().flip();
                    updateState();
                }
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (!me.isMetaDown() && ! me.isControlDown() && ! me.isShiftDown()) {
            // Where user has pressed down the mouse?
            Card c = null;
            Point p = me.getPoint();
            Pile source = null;
                
            // On the waste?
            if (!waste.isEmpty() && waste.contains(p)) {
                source = waste;
                c = source.top();
            }

            // On a foundation?
            if (source == null) {
                for (int i = 0; i < foundation.length; i++) {
                    if (!foundation[i].isEmpty() && foundation[i].contains(p)) {
                        source = foundation[i];
                        c = source.elementAt(p);
                    }
                }
            }

            // On a column?
            if (source == null) {
                for (int i = 0; i < column.length; i++) {
                    if (!column[i].isEmpty() && column[i].contains(p)) {
                        source = column[i];
                        //c = source.top();
                        c = source.elementAt(p);
                    }
                }
            }

            if (c != null && c.isFaceUp()) {
                Point location = c.getLocation();
                dragPoint = new Point(p.x - location.x, p.y - location.y);
                dragPile = source.pop(c);
                dragPile.reverse();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (!dragPile.isEmpty()) {
            Point p = me.getPoint();
            Pile dest = null;

            for (int i = 0; i < foundation.length; i++) {
                if (foundation[i].contains(p)) {
                    dest = foundation[i];
                }
            }

            for (int i = 0; i < column.length; i++) {
                if (column[i].contains(p)) {
                    dest = column[i];
                }
            }

            try {
                dragPile.reverse();
                while (!dragPile.isEmpty()) {
                    dest.push(dragPile.pop());
                }
                if (dest instanceof Foundation) {
                    score += 10;
                }                
                updateState();
                if (state.isGameWon()) {
                    DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Complimenti, hai vinto!", NotifyDescriptor.INFORMATION_MESSAGE));
                    removeListeners();
                }
            } catch (IllegalArgumentException ex) {
                revertState();
                NotificationDisplayer.getDefault().notify("Errore", ImageUtilities.loadImageIcon("it/markreds/whitehead/error.png", true), "Non puoi fare questa mossa", null);
            } catch (NullPointerException ex) {
                revertState();
            } catch (NoSuchElementException ex) {
                
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // Nothing to do here
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // Nothing to do here
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (dragPile != null && dragPoint != null) {
            Point p = me.getPoint();
            dragPile.setLocation(p.x - dragPoint.x, p.y -  dragPoint.y);
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        // Nothing to do here
    }
}
