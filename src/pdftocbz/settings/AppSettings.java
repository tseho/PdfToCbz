/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.settings;

import java.util.Properties;

/**
 *
 * @author Tseho
 */
public final class AppSettings implements SettingsInterface {

    private boolean deletePdfAfter;
    private boolean deleteImagesAfter;
    private int quality;
    private int outputFormat;
    private boolean renameArchives;
    private String renameArchivesSyntax;
    private boolean renameImages;
    private String renameImagesSyntax;
    private String lastDrive;

    public AppSettings() {
    }

    public AppSettings(Properties settings) {
        this.parseProperties(settings);
    }

    @Override
    public Properties convertToProperties() {
        Properties settings = new Properties();
        settings.setProperty("deletePdfAfter", Boolean.toString(this.isDeletePdfAfter()));
        settings.setProperty("deleteImagesAfter", Boolean.toString(this.isDeleteImagesAfter()));
        settings.setProperty("quality", Integer.toString(this.getQuality()));
        settings.setProperty("outputFormat", Integer.toString(this.getOutputFormat()));
        settings.setProperty("renameArchives", Boolean.toString(this.isRenameArchives()));
        settings.setProperty("renameArchivesSyntax", this.getRenameArchivesSyntax());
        settings.setProperty("renameImages", Boolean.toString(this.isRenameImages()));
        settings.setProperty("renameImagesSyntax", this.getRenameImagesSyntax());
        settings.setProperty("lastDrive", this.getLastDrive());
        return settings;
    }

    @Override
    public void parseProperties(Properties settings) {
        this.deletePdfAfter = Boolean.parseBoolean(settings.getProperty("deletePdfAfter"));
        this.deleteImagesAfter = Boolean.parseBoolean(settings.getProperty("deleteImagesAfter"));
        this.quality = Integer.parseInt(settings.getProperty("quality"));
        this.outputFormat = Integer.parseInt(settings.getProperty("outputFormat"));
        this.renameArchives = Boolean.parseBoolean(settings.getProperty("renameArchives"));
        this.renameArchivesSyntax = settings.getProperty("renameArchivesSyntax");
        this.renameImages = Boolean.parseBoolean(settings.getProperty("renameImages"));
        this.renameImagesSyntax = settings.getProperty("renameImagesSyntax");
        this.lastDrive = settings.getProperty("lastDrive");
    }

    @Override
    public void saveProperties() {
        Configuration.updateConfigurationFile(Configuration.APPSETTINGS, this.convertToProperties());
    }
    
    @Override
    public Properties getDefaultsProperties() {
        Properties settings = new Properties();
        settings.setProperty("deletePdfAfter", "false");
        settings.setProperty("deleteImagesAfter", "false");
        settings.setProperty("quality", "3");
        settings.setProperty("outputFormat", "2");
        settings.setProperty("renameArchives", "false");
        settings.setProperty("renameArchivesSyntax", "$m - $t");
        settings.setProperty("renameImages", "false");
        settings.setProperty("renameImagesSyntax", "$n3");
        settings.setProperty("lastDrive", "");
        return settings;
    }

    public String getLastDrive() {
        return lastDrive;
    }

    public void setLastDrive(String lastDrive) {
        this.lastDrive = lastDrive;
    }

    public boolean isDeletePdfAfter() {
        return deletePdfAfter;
    }

    public void setDeletePdfAfter(boolean deletePdfAfter) {
        this.deletePdfAfter = deletePdfAfter;
    }

    public boolean isDeleteImagesAfter() {
        return deleteImagesAfter;
    }

    public void setDeleteImagesAfter(boolean deleteImagesAfter) {
        this.deleteImagesAfter = deleteImagesAfter;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(int outputFormat) {
        this.outputFormat = outputFormat;
    }

    public boolean isRenameArchives() {
        return renameArchives;
    }

    public void setRenameArchives(boolean renameArchives) {
        this.renameArchives = renameArchives;
    }

    public String getRenameArchivesSyntax() {
        return renameArchivesSyntax;
    }

    public void setRenameArchivesSyntax(String renameArchivesSyntax) {
        this.renameArchivesSyntax = renameArchivesSyntax;
    }

    public boolean isRenameImages() {
        return renameImages;
    }

    public void setRenameImages(boolean renameFiles) {
        this.renameImages = renameFiles;
    }

    public String getRenameImagesSyntax() {
        return renameImagesSyntax;
    }

    public void setRenameImagesSyntax(String renameFilesSyntax) {
        this.renameImagesSyntax = renameFilesSyntax;
    }

    
}
