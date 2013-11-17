/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.converter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import pdftocbz.converter.imagesToZip.zipDirectory;
import pdftocbz.converter.logs.ConvertedLogElement;
import pdftocbz.converter.logs.ConvertedLogs;
import pdftocbz.converter.pdfToImages.PdfToImagesLight;
import pdftocbz.settings.AppSettings;
import pdftocbz.settings.Configuration;

/**
 *
 * @author Tseho
 */
public final class Converter extends Thread {

    private AppSettings appSettings;
    private JProgressBar progressBar;
    private LinkedList<String> selectedFilesPaths;
    private Boolean cancelActionned = false;
    private ConvertedLogs logs;

    public Converter(AppSettings appSettings, LinkedList<String> selectedFilesPaths, JProgressBar progressBar, ConvertedLogs logs) {
        this.appSettings = appSettings;
        this.selectedFilesPaths = selectedFilesPaths;
        this.progressBar = progressBar;
        this.logs = logs;
    }

    /*
     public void setSelectedFiles(LinkedList<SelectedFile> selectedFiles){
     //this.filesToProcess = (LinkedList<SelectedFile>) selectedFiles.clone();
     this.filesToProcess = selectedFiles;
     }
    
     public void setProgressBar(JProgressBar progressBar){
     this.progressBar = progressBar;
     }
     */
    @Override
    public void run() {
        
        int i = 1;
        int totalPages = 0;
        
        Iterator<String> it = this.selectedFilesPaths.iterator();
        PdfToImagesLight pdfToImagesLight = new PdfToImagesLight();
        
        /*
        this.progressBar.addPropertyChangeListener("cancel", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                cancelActionned = true;
            }
        });
        */
        
        //First loop, we count the number of pages
        while (it.hasNext() && this.cancelActionned == false) {
            String nextFilePath = it.next();
            try {
                PDDocument document = PDDocument.load(nextFilePath);
                totalPages += document.getNumberOfPages();
                document.close();
            } catch (IOException ex) {
                Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.progressBar.setMaximum(totalPages);
        
        it = this.selectedFilesPaths.iterator();

        //Second loop, we execute the converter.
        while (it.hasNext()  && this.cancelActionned == false) {
            String nextFilePath = it.next();
            File nextFile = new File(nextFilePath);
            String outputPath = null;
            Boolean archived = false;
            Boolean success = true;
            Boolean bSuccess = true;
            
            //The OUTPUT Directory
            
            String realOutputDir = this.getOutputDirectory(nextFile);
            
            
            System.out.println("Output :" + realOutputDir);
            
            //Create images
            try {
                bSuccess = pdfToImagesLight.convertPdfToImages(nextFile, this.appSettings, this.progressBar, realOutputDir);
            } catch (IOException ex) {
                Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                bSuccess = false;
            }
            
            if(!this.cancelActionned){

                if (this.appSettings.getOutputFormat() == Configuration.OUTPUTCBZ) {
                    outputPath = realOutputDir + ".cbz";
                    zipDirectory.zipFiles(realOutputDir, outputPath);
                    archived = true;
                }

                if (this.appSettings.isDeletePdfAfter()) {
                    Boolean pdfDeletion = nextFile.delete();
                }

                if (this.appSettings.isDeleteImagesAfter()) {
                    File dirToDelete = new File(realOutputDir);
                    
                    System.gc();

                    try {
                        FileUtils.forceDelete(dirToDelete);
                    } catch (IOException ex) {
                        Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                        success = false;
                    }
                }

                if (this.progressBar != null) {
                    this.progressBar.firePropertyChange("pdfConverted", i - 1, i);
                }
                
                int logState;
                if(bSuccess == true && success == true){
                    logState = ConvertedLogElement.SUCCESS;
                }else if(bSuccess == true && success == false){
                    logState = ConvertedLogElement.CREATEDWITHOUTDELETION;
                }else{
                    logState = ConvertedLogElement.ERROR;
                }

                this.logs.addLog(new ConvertedLogElement(nextFilePath, outputPath, logState));
                
            }else{
                this.logs.addLog(new ConvertedLogElement(nextFilePath, ConvertedLogElement.ABORTED));
            }
            
            it.remove();

            i++;
        }
        this.progressBar.firePropertyChange("endThread", false, true);
    }
    
    private String getOutputDirectory(File file){
        String idealOutputDir = file.getParentFile().getAbsolutePath() + File.separator + file.getName().substring(0, file.getName().lastIndexOf('.'));
            
        File targetDirectory = new File(idealOutputDir);

        int duplicateNameNumber = 1;

        while (targetDirectory.exists()) {
            targetDirectory = new File(idealOutputDir + " (" + duplicateNameNumber + ")");
            duplicateNameNumber++;
        }
        targetDirectory.mkdir();

        String realOutputDir = targetDirectory.getAbsolutePath();
        
        return realOutputDir;
    }
}
