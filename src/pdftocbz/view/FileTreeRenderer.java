/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.view;

import java.awt.Component;
import java.io.File;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Tseho
 */
public class FileTreeRenderer extends DefaultTreeCellRenderer {

    private FileSystemView fsv = FileSystemView.getFileSystemView();
    private File f;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        try {
            f = (File) value;
            setIcon(fsv.getSystemIcon(f));
        } catch (Exception e) {
        }

        return this;
    }
}
