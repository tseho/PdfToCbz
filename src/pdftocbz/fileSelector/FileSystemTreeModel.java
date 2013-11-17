/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.fileSelector;

import pdftocbz.fileSelector.FileComparator;
import pdftocbz.fileSelector.FileAuthorizer;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Tseho
 */
public class FileSystemTreeModel implements TreeModel {

    private File root;
    private Vector listeners = new Vector();
    

    public FileSystemTreeModel(File rootDirectory) {
        root = rootDirectory;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        List<File> children = this.getChildrenList(directory);
        return new TreeFile(directory, children.get(index).getName());
    }

    private List<File> getChildrenList(File directory) {
        File[] children = directory.listFiles();
        List<File> childrenList = new LinkedList<File>(Arrays.asList(children));
        
        Iterator<File> childrenIterator = childrenList.iterator();
        while (childrenIterator.hasNext()) {
            //File next = childrenIterator.next();
            if (!FileAuthorizer.isDisplayable(childrenIterator.next())) {
                childrenIterator.remove();
            }
        }

        Collections.sort(childrenList, new FileComparator());

        return childrenList;
    }

    @Override
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            List<File> fileList = this.getChildrenList(file);
            if (fileList != null) {
                return fileList.size();
            }
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        File file = (File) node;
        return file.isFile();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = (File) child;
        List<File> children = this.getChildrenList(directory);
        for (int i = 0; i < children.size(); i++) {
            if (file.getName().equals(children.get(i).getName())) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public void valueForPathChanged(TreePath path, Object value) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) value;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        Object[] changedChildren = {targetFile};
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);

    }

    private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener = null;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        listeners.remove(listener);
    }
}
