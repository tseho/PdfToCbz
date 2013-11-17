/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.fileSelector;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author Tseho
 */
public class FileComparator implements Comparator<File> {
    
    @Override
    public int compare(File f1, File f2) {
        if (f1.isDirectory() ^ f2.isDirectory()) {
            return (f1.isDirectory() ? -1 : 1);
        }
        return f1.getName().compareToIgnoreCase(f2.getName());
    }
}