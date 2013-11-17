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
public final class UiSettings implements SettingsInterface{
    
    public UiSettings(){
        
    }
    
    public UiSettings(Properties settings){
        
    }

    @Override
    public Properties convertToProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseProperties(Properties settings) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Properties getDefaultsProperties() {
        Properties settings = new Properties();
        return settings;
    }
    
}
