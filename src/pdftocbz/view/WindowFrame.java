/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import pdftocbz.MainController;
import pdftocbz.fileSelector.FileSystemTreeModel;
import pdftocbz.fileSelector.SelectedFilesManager;
import pdftocbz.settings.Configuration;

/**
 *
 * @author Tseho
 */
public class WindowFrame extends javax.swing.JFrame {

    private TreeModel currentTreeFiles;
    private MainController controller;
    private AdvancedOptionsFrame advancedOptionsFrame;
    private SelectedFilesManager selectedFilesManager;

    /**
     * Creates new form WindowFrame
     */
    public WindowFrame(MainController controller) {
        initComponents();
        this.controller = controller;
    }

    public void prepare() {
        
        this.applySettings();
        
        //this.deleteImagesAfterCheckBox.setSelected(false);
        //this.deleteImagesAfterCheckBox.setEnabled(false);
        
        this.outputQualityComboBox.setEnabled(false);
        
        if(this.outputImagesRadio.isSelected()){
            this.deleteImagesAfterCheckBox.setSelected(false);
            this.deleteImagesAfterCheckBox.setEnabled(false);
        }

        this.fileSelectorJTree.setCellRenderer(new FileTreeRenderer());
        
        //ScrollPane fix
        this.fileSelectorJTree.setPreferredSize(null);
        this.fileSelectedList.setPreferredSize(null);
        
        this.setTreeFile(new FileSystemTreeModel((File) this.driveComboBox.getSelectedItem()));
        
        this.initializeSelectedList();
        
        this.convertProgressBar.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //updateProgressing();
            }
        });
        
        this.convertProgressBar.addPropertyChangeListener("endThread", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                convertIsFinish();
            }
        });
        
        this.convertProgressBar.addPropertyChangeListener("pdfConverted", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateProgressing((Integer) evt.getNewValue());
            }
        });
        

    }
    
    public void initializeSelectedList(){
        this.fileSelectedList.setModel(new javax.swing.AbstractListModel() {
            @Override
            public int getSize() {
                return selectedFilesManager.getList().size();
            }

            @Override
            public Object getElementAt(int i) {
                return selectedFilesManager.getList().get(i);
            }
        });
        this.fileSelectedList.updateUI();
    }

    public void beforeClose() {
        this.updateSettings();
    }

    public void applySettings() {
        //Output format
        if (this.controller.getAppSettings().getOutputFormat() == Configuration.OUTPUTCBZ) {
            this.outputCbzRadio.setSelected(true);
        } else {
            this.outputImagesRadio.setSelected(true);
        }

        //Delete pdf after
        this.deletePdfAfterCheckBox.setSelected(this.controller.getAppSettings().isDeletePdfAfter());

        //Delete images after
        this.deleteImagesAfterCheckBox.setSelected(this.controller.getAppSettings().isDeleteImagesAfter());

        //output quality
        this.outputQualityComboBox.setSelectedIndex(this.controller.getAppSettings().getQuality());
        
        for (int i = 0; i < this.driveComboBox.getItemCount(); i++) {
            if(this.driveComboBox.getItemAt(i).toString().equals(this.controller.getAppSettings().getLastDrive())){
                this.driveComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public void updateSettings() {
        //Output format
        if (this.outputCbzRadio.isSelected()) {
            this.controller.getAppSettings().setOutputFormat(Configuration.OUTPUTCBZ);
        } else {
            this.controller.getAppSettings().setOutputFormat(Configuration.OUTPUTIMAGES);
        }

        //Delete images after
        this.controller.getAppSettings().setDeleteImagesAfter(this.deleteImagesAfterCheckBox.isSelected());

        //Delete pdf after
        this.controller.getAppSettings().setDeletePdfAfter(this.deletePdfAfterCheckBox.isSelected());

        //output quality
        this.controller.getAppSettings().setQuality(this.outputQualityComboBox.getSelectedIndex());
        
        //Drive selected
        this.controller.getAppSettings().setLastDrive(this.driveComboBox.getSelectedItem().toString());

        this.controller.getAppSettings().saveProperties();
    }

    public void display() {
        //LookAndFeel
        try {
            //Sélection du Look & Feel du système
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);

        } catch (InstantiationException e) {
        } catch (ClassNotFoundException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } catch (IllegalAccessException e) {
        }
        
        //Image icon = Toolkit.getDefaultToolkit().getImage("resources/cbz.png");
        Image icon = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/pdftocbz/resources/cbz.png"));
        this.setIconImage(icon);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.progressPanel.setVisible(false);
        this.removeFileButton.setVisible(false);
    }

    public void updateSelectedList() {
        this.fileSelectedList.updateUI();
        String number = Integer.toString(this.selectedFilesManager.getList().size());
        this.numberPdfSelectedLabel.setText(number);
        this.numberTotalToConvertLabel.setText(number);
    }

    public void setTreeFile(TreeModel model) {
        this.currentTreeFiles = model;
        this.fileSelectorJTree.setModel(model);
    }

    public TreeModel getCurrentTreeFile() {
        return this.currentTreeFiles;
    }

    public void setSelectedFilesManager(SelectedFilesManager selectedFilesManager) {
        this.selectedFilesManager = selectedFilesManager;
    }

    public void setDriveList(Object[] list) {
        driveComboBox.setModel(new javax.swing.DefaultComboBoxModel(list));
    }
    
    public MainController getController(){
        return this.controller;
    }
    
    public void updateProgressing(int newValue){
        this.numberConvertedLabel.setText(Integer.toString(newValue));
    }
    
    public void convertIsFinish(){

        this.initializeSelectedList();
        this.addFileButton.setEnabled(true);
        this.fileSelectedList.setEnabled(true);
        this.cancelButton.setVisible(false);
        this.convertButton.setEnabled(true);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        fileSelectorPanel = new javax.swing.JPanel();
        jSplitPane = new javax.swing.JSplitPane();
        leftPanel = new javax.swing.JPanel();
        driveComboBox = new javax.swing.JComboBox();
        fileSelectorJScrollPane = new javax.swing.JScrollPane();
        fileSelectorJTree = new javax.swing.JTree();
        rightPanel = new javax.swing.JPanel();
        fileButtonsBarPanel = new javax.swing.JPanel();
        addFileButton = new javax.swing.JButton();
        removeFileButton = new javax.swing.JButton();
        fileSelectedScrollPane = new javax.swing.JScrollPane();
        fileSelectedList = new javax.swing.JList();
        menuPanel = new javax.swing.JPanel();
        appTitle = new javax.swing.JLabel();
        outputChoiceLabel = new javax.swing.JLabel();
        outputCbzRadio = new javax.swing.JRadioButton();
        deletePdfAfterCheckBox = new javax.swing.JCheckBox();
        convertButton = new javax.swing.JButton();
        progressPanel = new javax.swing.JPanel();
        convertProgressBar = new javax.swing.JProgressBar();
        progressNumbersPanel = new javax.swing.JPanel();
        numberConvertedLabel = new javax.swing.JLabel();
        separatorNumbersLabel = new javax.swing.JLabel();
        numberTotalToConvertLabel = new javax.swing.JLabel();
        labelProgressNumberLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        outputQualityLabel = new javax.swing.JLabel();
        outputQualityComboBox = new javax.swing.JComboBox();
        displaySelectedPdfPanel = new javax.swing.JPanel();
        numberPdfSelectedLabel = new javax.swing.JLabel();
        labelPdfSelectedLabel = new javax.swing.JLabel();
        deleteImagesAfterCheckBox = new javax.swing.JCheckBox();
        outputImagesRadio = new javax.swing.JRadioButton();
        advancedOptionsButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PdfToCbz Converter");

        jSplitPane.setBorder(BorderFactory.createEmptyBorder());
        jSplitPane.setDividerSize(10);
        jSplitPane.setResizeWeight(0.8);
        jSplitPane.setToolTipText("");

        driveComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        driveComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                driveComboBoxActionPerformed(evt);
            }
        });

        fileSelectorJScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fileSelectorJScrollPane.setMinimumSize(new java.awt.Dimension(80, 80));
        fileSelectorJScrollPane.setPreferredSize(new java.awt.Dimension(80, 322));

        fileSelectorJTree.setMaximumSize(new java.awt.Dimension(120, 64));
        fileSelectorJTree.setMinimumSize(new java.awt.Dimension(120, 400));
        fileSelectorJTree.setPreferredSize(new java.awt.Dimension(120, 64));
        fileSelectorJScrollPane.setViewportView(fileSelectorJTree);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileSelectorJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                    .addComponent(driveComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addComponent(driveComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileSelectorJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))
        );

        jSplitPane.setLeftComponent(leftPanel);

        addFileButton.setText("->");
        addFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileButtonActionPerformed(evt);
            }
        });

        removeFileButton.setText("X");
        removeFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fileButtonsBarPanelLayout = new javax.swing.GroupLayout(fileButtonsBarPanel);
        fileButtonsBarPanel.setLayout(fileButtonsBarPanelLayout);
        fileButtonsBarPanelLayout.setHorizontalGroup(
            fileButtonsBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addFileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(removeFileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        fileButtonsBarPanelLayout.setVerticalGroup(
            fileButtonsBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileButtonsBarPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeFileButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fileSelectedScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fileSelectedList.setMaximumSize(new java.awt.Dimension(120, 200));
        fileSelectedList.setMinimumSize(new java.awt.Dimension(120, 200));
        fileSelectedList.setPreferredSize(new java.awt.Dimension(120, 200));
        fileSelectedList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fileSelectedListKeyReleased(evt);
            }
        });
        fileSelectedScrollPane.setViewportView(fileSelectedList);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileButtonsBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileSelectedScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileSelectedScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
            .addComponent(fileButtonsBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane.setRightComponent(rightPanel);

        javax.swing.GroupLayout fileSelectorPanelLayout = new javax.swing.GroupLayout(fileSelectorPanel);
        fileSelectorPanel.setLayout(fileSelectorPanelLayout);
        fileSelectorPanelLayout.setHorizontalGroup(
            fileSelectorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileSelectorPanelLayout.createSequentialGroup()
                .addComponent(jSplitPane)
                .addContainerGap())
        );
        fileSelectorPanelLayout.setVerticalGroup(
            fileSelectorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        appTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        appTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appTitle.setText("PdfToCbz");

        outputChoiceLabel.setText("Convertir en :");

        buttonGroup1.add(outputCbzRadio);
        outputCbzRadio.setSelected(true);
        outputCbzRadio.setText("Cbz (zip)");
        outputCbzRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputCbzRadioActionPerformed(evt);
            }
        });

        deletePdfAfterCheckBox.setText("Supprimer les Pdf après l'extraction");
        deletePdfAfterCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePdfAfterCheckBoxActionPerformed(evt);
            }
        });

        convertButton.setText("Convertir");
        convertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertButtonActionPerformed(evt);
            }
        });

        numberConvertedLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        numberConvertedLabel.setText("0");

        separatorNumbersLabel.setText("/");

        numberTotalToConvertLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        numberTotalToConvertLabel.setText("0");

        labelProgressNumberLabel.setText("Pdf converti(s)");

        javax.swing.GroupLayout progressNumbersPanelLayout = new javax.swing.GroupLayout(progressNumbersPanel);
        progressNumbersPanel.setLayout(progressNumbersPanelLayout);
        progressNumbersPanelLayout.setHorizontalGroup(
            progressNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressNumbersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numberConvertedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorNumbersLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberTotalToConvertLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelProgressNumberLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        progressNumbersPanelLayout.setVerticalGroup(
            progressNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, progressNumbersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(numberConvertedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(separatorNumbersLabel)
                .addComponent(numberTotalToConvertLabel)
                .addComponent(labelProgressNumberLabel))
        );

        cancelButton.setText("Annuler");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout progressPanelLayout = new javax.swing.GroupLayout(progressPanel);
        progressPanel.setLayout(progressPanelLayout);
        progressPanelLayout.setHorizontalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressNumbersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(convertProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, progressPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        progressPanelLayout.setVerticalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, progressPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressNumbersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(convertProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );

        outputQualityLabel.setText("Qualité :");

        outputQualityComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "faible", "moyenne", "haute", "maximale" }));

        numberPdfSelectedLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        numberPdfSelectedLabel.setText("0");

        labelPdfSelectedLabel.setText("Pdf sélectionné(s)");

        javax.swing.GroupLayout displaySelectedPdfPanelLayout = new javax.swing.GroupLayout(displaySelectedPdfPanel);
        displaySelectedPdfPanel.setLayout(displaySelectedPdfPanelLayout);
        displaySelectedPdfPanelLayout.setHorizontalGroup(
            displaySelectedPdfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displaySelectedPdfPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numberPdfSelectedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPdfSelectedLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        displaySelectedPdfPanelLayout.setVerticalGroup(
            displaySelectedPdfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displaySelectedPdfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(numberPdfSelectedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(labelPdfSelectedLabel))
        );

        deleteImagesAfterCheckBox.setText("Supprimer les images extraites (béta)");
        deleteImagesAfterCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteImagesAfterCheckBoxActionPerformed(evt);
            }
        });

        buttonGroup1.add(outputImagesRadio);
        outputImagesRadio.setText("Images (jpeg)");
        outputImagesRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputImagesRadioActionPerformed(evt);
            }
        });

        advancedOptionsButton.setText("Options avancées");
        advancedOptionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advancedOptionsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(displaySelectedPdfPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(convertButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(appTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(advancedOptionsButton))
                    .addComponent(deletePdfAfterCheckBox)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(outputChoiceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outputImagesRadio)
                            .addComponent(outputCbzRadio)))
                    .addComponent(deleteImagesAfterCheckBox)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(outputQualityLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputQualityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(appTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputChoiceLabel)
                    .addComponent(outputCbzRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputImagesRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteImagesAfterCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deletePdfAfterCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputQualityLabel)
                    .addComponent(outputQualityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(advancedOptionsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displaySelectedPdfPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(convertButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileSelectorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileSelectorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertButtonActionPerformed
        // TODO add your handling code here:
        this.updateSelectedList();
        this.updateSettings();
        
        if(this.selectedFilesManager.getList().size()>0){
            
            this.controller.launchConverter(this.convertProgressBar);
            this.cancelButton.setVisible(true);
            this.progressPanel.setVisible(true);
            this.fileSelectedList.setEnabled(false);
            this.addFileButton.setEnabled(false);
            this.convertButton.setEnabled(false);
            this.convertProgressBar.setMinimum(0);
            this.convertProgressBar.setValue(0);
            this.convertProgressBar.setMaximum(this.selectedFilesManager.getList().size());
        }
    }//GEN-LAST:event_convertButtonActionPerformed

    private void deleteImagesAfterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteImagesAfterCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteImagesAfterCheckBoxActionPerformed

    private void deletePdfAfterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePdfAfterCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deletePdfAfterCheckBoxActionPerformed

    private void removeFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFileButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeFileButtonActionPerformed

    private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
        this.progressPanel.setVisible(false);
        TreePath[] selectedFilesTmp = this.fileSelectorJTree.getSelectionPaths();
        this.selectedFilesManager.addTreePaths(selectedFilesTmp);
        this.updateSelectedList();
    }//GEN-LAST:event_addFileButtonActionPerformed

    private void driveComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_driveComboBoxActionPerformed
        // TODO add your handling code here:
        JComboBox cb = (JComboBox) evt.getSource();
        File root = (File) cb.getSelectedItem();
        this.setTreeFile(new FileSystemTreeModel(root));
    }//GEN-LAST:event_driveComboBoxActionPerformed

    private void fileSelectedListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fileSelectedListKeyReleased
        // Key delete
        if (evt.getKeyCode() == 127) {
            List selectedFilesToRemove = this.fileSelectedList.getSelectedValuesList();
            for (Iterator it = selectedFilesToRemove.iterator(); it.hasNext();) {
                String selectedFileToRemove = (String) it.next();
                this.selectedFilesManager.removeElement(selectedFileToRemove);
            }
            this.fileSelectedList.clearSelection();
        }
        
        this.updateSelectedList();
    }//GEN-LAST:event_fileSelectedListKeyReleased

    private void advancedOptionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_advancedOptionsButtonActionPerformed
        // TODO add your handling code here:
        this.advancedOptionsFrame = new AdvancedOptionsFrame(this, true);
    }//GEN-LAST:event_advancedOptionsButtonActionPerformed

    private void outputImagesRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputImagesRadioActionPerformed
        // TODO add your handling code here:
        this.deleteImagesAfterCheckBox.setSelected(false);
        this.deleteImagesAfterCheckBox.setEnabled(false);
    }//GEN-LAST:event_outputImagesRadioActionPerformed

    private void outputCbzRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputCbzRadioActionPerformed
        // TODO add your handling code here:
        this.deleteImagesAfterCheckBox.setEnabled(true);
    }//GEN-LAST:event_outputCbzRadioActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Cancel called");
        this.convertProgressBar.firePropertyChange("cancel", false, true);
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFileButton;
    private javax.swing.JButton advancedOptionsButton;
    private javax.swing.JLabel appTitle;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton convertButton;
    private javax.swing.JProgressBar convertProgressBar;
    private javax.swing.JCheckBox deleteImagesAfterCheckBox;
    private javax.swing.JCheckBox deletePdfAfterCheckBox;
    private javax.swing.JPanel displaySelectedPdfPanel;
    private javax.swing.JComboBox driveComboBox;
    private javax.swing.JPanel fileButtonsBarPanel;
    private javax.swing.JList fileSelectedList;
    private javax.swing.JScrollPane fileSelectedScrollPane;
    private javax.swing.JScrollPane fileSelectorJScrollPane;
    private javax.swing.JTree fileSelectorJTree;
    private javax.swing.JPanel fileSelectorPanel;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JLabel labelPdfSelectedLabel;
    private javax.swing.JLabel labelProgressNumberLabel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JLabel numberConvertedLabel;
    private javax.swing.JLabel numberPdfSelectedLabel;
    private javax.swing.JLabel numberTotalToConvertLabel;
    private javax.swing.JRadioButton outputCbzRadio;
    private javax.swing.JLabel outputChoiceLabel;
    private javax.swing.JRadioButton outputImagesRadio;
    private javax.swing.JComboBox outputQualityComboBox;
    private javax.swing.JLabel outputQualityLabel;
    private javax.swing.JPanel progressNumbersPanel;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JButton removeFileButton;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JLabel separatorNumbersLabel;
    // End of variables declaration//GEN-END:variables
}
