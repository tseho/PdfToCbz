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
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import pdftocbz.settings.AppSettings;

/**
 *
 * @author Tseho
 */
public class PdfToImages {
    
    public PdfToImages(){
        
    }
    

    public String convertPdfToImages(File inputFile, AppSettings settings) {

        boolean useNonSeqParser = false;
        String outputPrefix = null;
        String imageFormat = "jpg";
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;
        String color = "rgb";
        int resolution;
        float cropBoxLowerLeftX = 0;
        float cropBoxLowerLeftY = 0;
        float cropBoxUpperRightX = 0;
        float cropBoxUpperRightY = 0;
        try {
            resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        } catch (HeadlessException e) {
            resolution = 96;
        }

        /*
        if (outputPrefix == null) {
            outputPrefix = inputFile.getName().substring(0, inputFile.getName().lastIndexOf('.'));
        }
        */

        PDDocument document = null;
        try {
            if (useNonSeqParser) {
                //document = PDDocument.loadNonSeq(file, null, password);
                document = PDDocument.loadNonSeq(inputFile, null);
            } else {
                document = PDDocument.load(inputFile.getAbsolutePath());
            }
            int imageType = 24;
            if ("bilevel".equalsIgnoreCase(color)) {
                imageType = BufferedImage.TYPE_BYTE_BINARY;
            } else if ("indexed".equalsIgnoreCase(color)) {
                imageType = BufferedImage.TYPE_BYTE_INDEXED;
            } else if ("gray".equalsIgnoreCase(color)) {
                imageType = BufferedImage.TYPE_BYTE_GRAY;
            } else if ("rgb".equalsIgnoreCase(color)) {
                imageType = BufferedImage.TYPE_INT_RGB;
            } else if ("rgba".equalsIgnoreCase(color)) {
                imageType = BufferedImage.TYPE_INT_ARGB;
            } else {
                System.err.println("Error: the number of bits per pixel must be 1, 8 or 24.");
                System.exit(2);
            }

            //if a CropBox has been specified, update the CropBox:
            //changeCropBoxes(PDDocument document,float a, float b, float c,float d)
            if (cropBoxLowerLeftX != 0 || cropBoxLowerLeftY != 0
                    || cropBoxUpperRightX != 0 || cropBoxUpperRightY != 0) {
                changeCropBoxes(document,
                        cropBoxLowerLeftX, cropBoxLowerLeftY,
                        cropBoxUpperRightX, cropBoxUpperRightY);
            }

            String directoryName;

            /*
             if(settings.isRenameArchives()){
             directoryName = createCustomDirectoryName(selectedFile.getFile(), settings.getRenameArchivesSyntax());
             }else{
             directoryName = createDefaultDirectoryName(selectedFile.getFile());
             }
             */

            directoryName = createDefaultDirectoryName(inputFile);

            //The directory for this pdf
            File targetDirectory = new File(inputFile.getParentFile().getAbsolutePath() + File.separator + directoryName);

            int duplicateNameNumber = 1;

            while (targetDirectory.exists()) {
                targetDirectory = new File(inputFile.getParentFile().getAbsolutePath() + File.separator + directoryName + " (" + duplicateNameNumber + ")");
                duplicateNameNumber++;
            }

            //If it doesn't exist
            if (!targetDirectory.exists()) {
                targetDirectory.mkdir();
            }

            //Make the call
            CustomPDFImageWriter imageWriter = new CustomPDFImageWriter(settings, targetDirectory.getAbsolutePath());
            
            boolean success = imageWriter.writeImage(document, imageFormat, "",
                    startPage, endPage, outputPrefix, imageType, resolution);
            
            /*
            String fullFileName = targetDirectory.getAbsolutePath() + File.separator + Integer.toString(1);
            String outputFileName = fullFileName + "." + imageFormat;
            File outputFile = new File(outputFileName);
            outputFile.createNewFile();
            boolean success = true;
            */

            
            if (!success) {
                System.err.println("Error: no writer found for image format '"
                        + imageFormat + "'");
                System.exit(1);
            }
            
            String targetDirectoryPath = targetDirectory.getAbsolutePath();
            
            return targetDirectoryPath;
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            
        }
        
        return null;
    }

    private void changeCropBoxes(PDDocument document, float a, float b, float c, float d) {
        List pages = document.getDocumentCatalog().getAllPages();
        for (int i = 0; i < pages.size(); i++) {
            PDPage page = (PDPage) pages.get(i);
            PDRectangle rectangle = new PDRectangle();
            rectangle.setLowerLeftX(a);
            rectangle.setLowerLeftY(b);
            rectangle.setUpperRightX(c);
            rectangle.setUpperRightY(d);
            page.setMediaBox(rectangle);
            page.setCropBox(rectangle);

        }
    }

    private String createDefaultDirectoryName(File pdfFile) {
        return pdfFile.getName().substring(0, pdfFile.getName().lastIndexOf('.'));
    }

    private String createCustomDirectoryName(File pdfFile, String syntax) {
        //TODO
        return "custom";
    }
}
