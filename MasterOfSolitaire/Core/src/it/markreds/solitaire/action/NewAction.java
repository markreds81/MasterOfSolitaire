/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.markreds.solitaire.action;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

public interface NewAction {
    @ActionID(
        category = "File", 
        id = "it.markreds.solitaire.action.NewAction")
    
    @ActionRegistration(
        iconBase = "it/markreds/solitaire/action/new.png",
        displayName = "#CTL_NewAction")
    
    @ActionReferences({
        @ActionReference(path = "Menu/File", position = 1300),
        @ActionReference(path = "Toolbars/File", position = 300),
        @ActionReference(path = "Shortcuts", name = "M-N")
    })
    
    @Messages("CTL_NewAction=New game")
    
    public static final String NEW_ACTION = "NewAction";
}