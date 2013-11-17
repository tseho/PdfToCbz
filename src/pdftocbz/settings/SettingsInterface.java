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
public interface SettingsInterface {
    
    public void parseProperties(Properties settings);
    
    public Properties convertToProperties();
    
    public void saveProperties();
    
    public Properties getDefaultsProperties();
    
}
