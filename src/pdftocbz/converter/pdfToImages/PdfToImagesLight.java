/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.converter.pdfToImages;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JProgressBar;
import org.apache.pdfbox.pdmodel.PDDocument;
import pdftocbz.settings.AppSettings;

/**
 *
 * @author Tseho
 */
public class PdfToImagesLight {
    
    public Boolean convertPdfToImages(File inputFile, AppSettings settings, JProgressBar progressBar, String outputDir) throws IOException {
        
        //Files
        PDDocument document = PDDocument.load(inputFile.getAbsolutePath());
        
        String imageFormat = "jpg";
        int startPage = 1;
        int endPage = document.getNumberOfPages();
        String outputPrefix = "";
        int imageType = BufferedImage.TYPE_INT_RGB;
        int resolution;
        /*
        try {
            resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        } catch (HeadlessException e) {
            resolution = 96;
        }
        */
        resolution = 200;
        
        System.out.println("rez : " + resolution);
        
        CustomPDFImageWriter imageWriter = new CustomPDFImageWriter(settings, outputDir);
            
        boolean success = imageWriter.writeImage(document, imageFormat, "",
                    startPage, endPage, outputPrefix, imageType, resolution, progressBar);
        
        document.close();
        
        return success;
    }
}
