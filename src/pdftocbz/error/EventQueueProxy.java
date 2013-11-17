/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdftocbz.error;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.io.PrintWriter;
import java.io.StringWriter;
import pdftocbz.view.BugReportDialog;

/**
 *
 * @author Tseho
 */
public class EventQueueProxy extends EventQueue {
 
    @Override
    protected void dispatchEvent(AWTEvent newEvent) {
        try {
            super.dispatchEvent(newEvent);
        } catch (Throwable t) {
            t.printStackTrace();
            
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            String message = sw.toString();
 
            if (message == null || message.length() == 0) {
                message = "Fatal: " + t.getClass();
            }
            
            BugReportDialog bugReportDialog = new BugReportDialog(null, true);
            bugReportDialog.setErrorMessage(message);
            bugReportDialog.setVisible(true);
        }
    }
}
