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
public class TreeFile extends File {

    public TreeFile(File parent, String child) {
        super(parent, child);
    }

    @Override
    public String toString() {
        return getName();
    }
}
