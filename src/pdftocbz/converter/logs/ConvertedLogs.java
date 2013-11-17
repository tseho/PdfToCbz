/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.converter.logs;

import java.util.LinkedList;

/**
 *
 * @author Tseho
 */
public class ConvertedLogs {
    
    private LinkedList<ConvertedLogElement> convertedLogElements = new LinkedList<ConvertedLogElement>();
    
    public ConvertedLogs(){
        
    }
    
    public void addLog(ConvertedLogElement log){
        this.convertedLogElements.add(log);
    }
    
}
