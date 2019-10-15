package Subprojects;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class RunningPrograms {
    private final String BEAUtiPath;
    private final File sessionDirectory;
    private final String nexFileName;

    /**
     * This is an constructor containing the paths which are then used by the startBEAUti method.
     * @param BEAUtiPath, location of the BEAUti.exe program
     * @param sessionDirectory, location of each user sessionDirectory
     * @param nexFileName, the nexusFile which has been uploaded by the user.
     */
    public RunningPrograms(String BEAUtiPath, File sessionDirectory, String nexFileName){
        this.BEAUtiPath = BEAUtiPath;
        this.sessionDirectory = sessionDirectory;
        this.nexFileName = nexFileName;
    }


    /**
     * This method executes the BEAUti.exe program which accepts the argument as a .NEX file. This produces
     * an output .XML file which will be stored in the sessionDirectory.
     */
    public String startBEAUti(){
        String xmlName = nexFileName.replace(".nex", ".xml");
        try {
            //TODO the "/" must be "\\" for windows users
            Process process = Runtime.getRuntime().exec(   BEAUtiPath + " -nex " + sessionDirectory + "/" +
                    nexFileName + " -exitaction writexml -out " + sessionDirectory + "/" + xmlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!new File(sessionDirectory + "/" + xmlName).isFile()){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return xmlName;
    }
}
