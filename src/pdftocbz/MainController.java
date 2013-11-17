/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JProgressBar;
import pdftocbz.converter.Converter;
import pdftocbz.converter.logs.ConvertedLogs;
import pdftocbz.fileSelector.SelectedFilesManager;
import pdftocbz.settings.AppSettings;
import pdftocbz.settings.Configuration;
import pdftocbz.settings.UiSettings;
import pdftocbz.view.WindowFrame;

/**
 *
 * @author Tseho
 */
public class MainController {
    
    private WindowFrame window;
    private AppSettings appSettings;
    private UiSettings uiSettings;
    private SelectedFilesManager selectedFilesManager;
    private ConvertedLogs logs;
    
    
    public MainController(){
        this.appSettings = new AppSettings(Configuration.getConfigurationFile(Configuration.APPSETTINGS));
        this.uiSettings = new UiSettings(Configuration.getConfigurationFile(Configuration.UISETTINGS));
        this.selectedFilesManager = new SelectedFilesManager();
        this.logs = new ConvertedLogs();
        
        this.window = new WindowFrame(this);
        this.window.setSelectedFilesManager(this.selectedFilesManager);
        this.window.setDriveList(File.listRoots());
        this.window.prepare();
        
        this.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window.beforeClose();
            }
        });
        
        this.window.display();
    }
    
    public void launchConverter(JProgressBar progressBar){
        /*
        this.converter = new Converter(this.appSettings);
        converter.setSelectedFiles(this.filesManager.getSelectedFiles());
        converter.setProgressBar(progressBar);
        */
        
        Converter converter = new Converter(appSettings, this.selectedFilesManager.getList(), progressBar, this.logs);
        converter.start();
        
    }
    
    public AppSettings getAppSettings(){
        return this.appSettings;
    }
    
    public UiSettings getUiSettings(){
        return this.uiSettings;
    }
}
