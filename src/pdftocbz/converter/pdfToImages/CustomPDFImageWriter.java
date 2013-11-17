/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.converter.pdfToImages;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JProgressBar;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFImageWriter;
import pdftocbz.settings.AppSettings;

/**
 *
 * @author Tseho
 */
public class CustomPDFImageWriter extends PDFImageWriter {

    private AppSettings settings;
    private String outputDirectory;
    private Boolean cancelActionned = false;

    public CustomPDFImageWriter(AppSettings settings, String outputDirectory) {
        this.settings = settings;
        this.outputDirectory = outputDirectory;
    }

    public boolean writeImage(PDDocument document, String imageFormat, String password,
            int startPage, int endPage, String outputPrefix, int imageType, int resolution, JProgressBar progressBar)
            throws IOException {

        boolean bSuccess = true;
        
        progressBar.addPropertyChangeListener("cancel", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                cancelActionned = true;
            }
        });
        
        List pages = document.getDocumentCatalog().getAllPages();
        for (int i = startPage - 1; i < endPage && i < pages.size(); i++) {
            PDPage page = (PDPage) pages.get(i);
            BufferedImage image = page.convertToImage(imageType, resolution);

            String fileName;

            if (this.settings.isRenameImages()) {
                fileName = this.createCustomImageName(i);
            } else {
                fileName = this.createDefaultImageName(i);
            }

            //The current output image name
            String fullFileName = this.outputDirectory + File.separator + fileName;
            String outputFileName = fullFileName + "." + imageFormat;
            File outputFile = new File(outputFileName);
            
            System.out.println(outputFile.getAbsolutePath());
            try {
                ImageOutputStream stream;
                try {
                    outputFile.delete();
                    stream = ImageIO.createImageOutputStream(outputFile);
                } catch (IOException e) {
                    throw new IIOException("Can't create output stream!", e);
                }

                ImageWriter writer = null;
                ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(image);
                Iterator iter = ImageIO.getImageWriters(type, imageFormat);
                if (iter.hasNext()) {
                    writer = (ImageWriter) iter.next();
                }
                if (writer == null) {
                    return false;
                }

                writer.setOutput(stream);
                try {
                    writer.write(image);
                } finally {
                    writer.dispose();
                    stream.flush();
                    stream.close();
                }
            } catch (IOException e) {
                System.out.println(e.toString());
                bSuccess = false;
            }

            progressBar.setValue(progressBar.getValue() + 1);
            progressBar.firePropertyChange("value", progressBar.getValue() - 1, progressBar.getValue());
            if(this.cancelActionned){
                return false;
            }

        }
        return bSuccess;
    }

    public String createDefaultImageName(int i) {
        return Integer.toString(i + 1);
    }

    public String createCustomImageName(int i) {

        String fileName = this.settings.getRenameImagesSyntax();

        for (int j = 5; j > 0; j--) {
            String number = Integer.toString(i + 1);
            while (number.length() < j) {
                number = "0" + number;
            }
            fileName = fileName.replace("$n" + j, number);
            if (j == 0) {
                fileName = fileName.replace("$n", number);
            }
        }

        File directory = new File(this.outputDirectory);

        fileName = fileName.replace("$d", directory.getName());

        return fileName;
    }
}
