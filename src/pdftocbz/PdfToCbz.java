/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz;

import java.awt.EventQueue;
import java.awt.Toolkit;
import pdftocbz.error.EventQueueProxy;

/**
 *
 * @author Tseho
 */
public class PdfToCbz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Handle errors for reporting
        EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        queue.push(new EventQueueProxy());
        
        new MainController();
        
    }
}
