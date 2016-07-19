/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.lang.Exception;

public class FileSelect extends AbstractArchive {

    protected FileFilter filter;

    protected int counter = 1;

    FileSelect () {
        
        /*
        * only show txt and csv files
        */
        filter = new FileNameExtensionFilter(
            "text and csv",
            "txt",
            "csv"
        );

        this.setFileFilter(filter);
    }

    /*
    * select a file to save to
    */
    protected String chooseSaveFile() {
        this.setSelectedFile(new File("telemetryData"));
        int returnVal = this.showSaveDialog(getParent());


        if(returnVal == JFileChooser.APPROVE_OPTION &&  this.approved(this.getSelectedFile().toString())) {
            System.out.println("File Name: " + this.getSelectedFile().getName());
            System.out.println("Writing to: " + this.getSelectedFile() + ".txt");
        }

        return this.getSelectedFile().toString();
    }

    /*
    * checks that file name is open
    */
    protected boolean approved (String filePath){
        
        /*
        * copy original file name
        */
        String fileName = filePath;
        
        /*
        * create file path
        */
        File file = new File(filePath + ".txt");

        try{

            /*
            * Check if file already exists
            */
            while(file.isFile()){

                /*
                * Add (#) to original file name.
                */
                fileName = (filePath + "(" + counter + ")");

                /*
                * Modify search path with new name
                */
                file = new File(fileName + ".txt");

                this.counter++;
            }

            /*
            * Set file name once approved
            */
            this.setFile(fileName);

            return true;

        } catch(Exception e) {
            System.out.println("failed to make file...");

            return false;
        }
        
    }

    /*
    * sets file to approved file name
    */
    protected void setFile (String fileName) {
        File file = new File(fileName);

        /*
        * set approved file name
        */
        this.setSelectedFile(file);

    }

}
