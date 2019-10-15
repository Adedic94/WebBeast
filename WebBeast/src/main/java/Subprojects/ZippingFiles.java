package Subprojects;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZippingFiles {
    private final String sessionDirectory;
    private final File folder;
    private final HttpServletResponse response;
    private final String inputFilePath;

    /**
     * This is an constructor containing the paths which are then used by the startBEAUti method.
     * @param sessionDirectory, location of each user sessionDirectory
     * @param folder, file object containing the path to the sessionDirectory
     * @param response, response function to interact with the user weg page
     * @param inputFilePath, location of the data folder containing the sessionDirectories
     */
    public ZippingFiles(String sessionDirectory, File folder, HttpServletResponse response, String inputFilePath) {
        this.sessionDirectory = sessionDirectory;
        this.folder = folder;
        this.response = response;
        this.inputFilePath = inputFilePath;
    }


     //Controlling if the sessionDirectory contains any files and if directory of the sessions actually is a directory
    public void folderControl() throws IOException {
        if (folder.isDirectory()) {
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles.length < 1)
                System.out.println("There is no File inside Folder");
            else
                System.out.println("List of Files & Folder");
            for (File file : listOfFiles) {
                if (!file.isDirectory())
                    System.out.println(file.getCanonicalPath());
            }
            // Folder does not exist, exit!
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson("No job found..."));
        }
    }

    /*
     * Reading the .trees file which has been produced by the program BEAST. While reading the file, it searches
     * for the END tag to know when the process has been finished. This way The program can communicate with the
     * web page to inform the user about the status of the process.
     */
    public void findingEndTagForTreeFile() throws IOException {
        File[] listOfFiles = folder.listFiles();
        String result = new Gson().toJson("running");
        for(File treesFile : listOfFiles){
            if(treesFile.getName().endsWith(".trees")){
                try {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(sessionDirectory + "/" + treesFile.getName()));
                    String lineInFile;
                    while ((lineInFile = bufferedReader.readLine()) != null) {
                        sb.append(lineInFile);
                    }
                    if(sb.toString().endsWith("End;")) {
                        result = new Gson().toJson("finished");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }


    /*
     * This method will search for files int the sessionDirectory to archive all the files into .ZIP file
     * This way all the used and produced files are collected.
     */
    public void zipFiles(){
        String zipFile = inputFilePath + "/Obtained_Data.zip";
        try {
            // create byte buffer
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File dir = new File(sessionDirectory);
            File[] files = dir.listFiles();
            for (File file : files) {
                System.out.println("Adding file: " + file.getName());
                FileInputStream fis = new FileInputStream(file);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(file.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        }
        catch (IOException ioe) {
            System.out.println("Error creating zip file" + ioe);
        }
    }
}
