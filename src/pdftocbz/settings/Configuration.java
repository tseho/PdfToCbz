/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


/**
 *
 * @author Tseho
 */
public class Configuration {
    
    public static String APPSETTINGS = "settings.properties";
    public static String UISETTINGS = "ui.properties";
    public static int OUTPUTCBZ = 1;
    public static int OUTPUTIMAGES = 2;
    
    public static Properties getConfigurationFile(String fileName) {
        Properties settingsProperties = new Properties();
        try {
            File file = getConfigFile(fileName);
            FileInputStream fileIS = new FileInputStream(file);
            settingsProperties.load(fileIS);
        } catch (IOException ex) {
        }
        return settingsProperties;
    }
    
    
    public static void updateConfigurationFile(String fileName, Properties settings) {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(getConfigFile(fileName));
        } catch (FileNotFoundException ex) {
        }
        try {
            settings.store(file, null);
        } catch (IOException ex) {
        }
    }

    private static File getConfigFile(String fileName) {
        String fullPath = getAppDataDirectory() + File.separator + fileName;

        File userFile = new File(fullPath);

        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException ex) {
            }
            
            if(fileName.equals(APPSETTINGS)){
                AppSettings appSettingsDefault = new AppSettings();
                Properties defaultSettings = appSettingsDefault.getDefaultsProperties();
                try {
                    defaultSettings.store(new FileOutputStream(userFile), null);
                } catch (IOException ex) {
                }
            }else if(fileName.equals(UISETTINGS)){
                UiSettings uiSettingsDefault = new UiSettings();
                Properties defaultSettings = uiSettingsDefault.getDefaultsProperties();
                try {
                    defaultSettings.store(new FileOutputStream(userFile), null);
                } catch (IOException ex) {
                }
            }
        }
        return userFile;
        
    }

    public static void copyFile(File src, File dst) {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    private static String getAppDataDirectory() {
        String AppDataDirectory = null;
        File windowsAppData = new File(System.getenv("APPDATA"), "PdfToCbz");
        if (!windowsAppData.exists()) {
            windowsAppData.mkdir();
        }
        if (windowsAppData.isDirectory() && windowsAppData.canWrite()) {
            return windowsAppData.getAbsolutePath();
        }

        File userHomeDirectory = new File(System.getProperty("user.home"), ".PdfToCbz");
        if (!userHomeDirectory.exists()) {
            userHomeDirectory.mkdir();
        }

        if (userHomeDirectory.isDirectory() && userHomeDirectory.canWrite()) {
            return userHomeDirectory.getAbsolutePath();
        }

        return null;
    }
}
