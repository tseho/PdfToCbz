/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.fileSelector;

import java.io.File;

/**
 *
 * @author Tseho
 */
public class FileAuthorizer {
    
    public static String[] extensionsAuthorized = {"pdf"};
    public static String[] directoryNamesHidden = {"System Volume Information"};
    public static String[] directoryStartWidthHidden = {"$"};
    
    public static boolean isFileAuthorized(File file){
        
        if(file.isDirectory()){
            return false;
        }
        
        for (int i = 0; i < extensionsAuthorized.length; i++) {
            if(file.getName().endsWith("." + extensionsAuthorized[i])){
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isDisplayable(File file){
        
        for (int i = 0; i < directoryNamesHidden.length; i++) {
            if(file.getName().equalsIgnoreCase(directoryNamesHidden[i])){
                return false;
            }
        }
        
        for (int i = 0; i < directoryStartWidthHidden.length; i++) {
            if(file.getName().startsWith(directoryStartWidthHidden[i])){
                return false;
            }
        }
        
        for (int i = 0; i < extensionsAuthorized.length; i++) {
            if(file.getName().endsWith("." + extensionsAuthorized[i])){
                return true;
            }
        }
        
        if(file.isDirectory()){
            return true;
        }
        return false;
    }
}
