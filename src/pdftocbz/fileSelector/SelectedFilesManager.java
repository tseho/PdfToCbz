/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.fileSelector;

import java.io.File;
import java.util.LinkedList;
import javax.swing.tree.TreePath;

/**
 *
 * @author Tseho
 */
public class SelectedFilesManager {

    private LinkedList<String> selectedFilesPaths = new LinkedList<String>();

    public SelectedFilesManager() {
    }

    public LinkedList<String> getSelectedFilesPaths() {
        return this.selectedFilesPaths;
    }

    public LinkedList<String> getList() {
        return this.getSelectedFilesPaths();
    }

    public void addTreePaths(TreePath[] paths) {
        for (int i = 0; i < paths.length; i++) {
            String selectedFile = (String) ((File) paths[i].getLastPathComponent()).getAbsolutePath();
            this.addFile(selectedFile);
        }
    }

    public void addFile(String fileName) {
        File file = new File(fileName);
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    if (children[i] != null) {
                        this.addFile(children[i].getAbsolutePath());
                    }
                }
            }
        } else if (!this.selectedFilesPaths.contains(file.getAbsolutePath()) && FileAuthorizer.isFileAuthorized(file)) {
            this.selectedFilesPaths.add(file.getAbsolutePath());
        }
    }

    public void removeElement(String fileName) {
        if(this.selectedFilesPaths.contains(fileName)){
            this.selectedFilesPaths.remove(fileName);
        }
    }
}
