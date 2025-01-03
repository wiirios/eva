package org.william.eva;

import javax.swing.SwingUtilities;

import org.william.eva.ui.Frame;

/**
 * @author William Rios
 * @author https://github.com/wiirios
 */

public class Main {
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Frame();
            }
        });
    }
}