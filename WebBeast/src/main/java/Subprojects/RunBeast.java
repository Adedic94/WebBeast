package Subprojects;

import java.io.*;

public class RunBeast {
    private String beast;
    private File sessionDir;
    private String xmlName;


    /**
     * This is an constructor containing the paths which are then used by the runBEAST method.
     * @param beastPath, location of the BEAST.jar program
     * @param sessionDir, location of each userSession directory
     * @param xmlName, location of the produced .XML file from the .NEX file
     */
    public RunBeast(String beastPath, File sessionDir, String xmlName){
        this.beast = beastPath;
        this.sessionDir = sessionDir;
        this.xmlName = xmlName;
    }


    /**
     * This method runs the BEAST program which accepts a .XML file to produce .trees files.
     * The produced .trees file will be stored in the same sessionDirectory.
     * At the end of the whole program it will be accessible from a .ZIP archive
     */
    public void runBeast(){

        try {
            String line;
            //TODO the "/" must be "\\" for windows users
            Process p = Runtime.getRuntime().exec("java -jar " + beast + " -working " + sessionDir + "/" +
                    xmlName);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = inputStream.readLine()) != null){
                System.out.println(line);
            }
            inputStream.close();
            while ((line = errorStream.readLine()) !=null){
                System.out.println(line);
            }
            errorStream.close();
            p.waitFor();
            System.out.println("Done");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
