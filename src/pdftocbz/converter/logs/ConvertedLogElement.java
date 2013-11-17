/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdftocbz.converter.logs;

/**
 *
 * @author Tseho
 */
public class ConvertedLogElement {
    
    public static int SUCCESS = 1;
    public static int ABORTED = 2;
    public static int CREATEDWITHOUTDELETION = 3;
    public static int ERROR = 4;
    
    private String srcFilePath;
    private String distFilePath;
    private int status;
    
    public ConvertedLogElement(String srcFilePath, int status){
        this.srcFilePath = srcFilePath;
        this.status = status;
    }
    
    public ConvertedLogElement(String srcFilePath, String distFilePath, int status){
        this.srcFilePath = srcFilePath;
        this.distFilePath = distFilePath;
        this.status = status;
    }

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

    public String getDistFilePath() {
        return distFilePath;
    }

    public void setDistFilePath(String distFilePath) {
        this.distFilePath = distFilePath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
}
