package Subprojects;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReturningZip {

    private final File file;
    private final HttpServletResponse response;

    /**
     * This is an constructor containing the paths which are then used by the startBEAUti method.
     * @param file, location of zipFile which will be created
     * @param response, response function to interact with the user weg page
     */
    public ReturningZip(File file, HttpServletResponse response) {
        this.file = file;
        this.response = response;
    }

    /*
     * All the files which has been created and stored in the sessionDirectory after finishing the whole process
     * will be archived into a .ZIP file. This created .ZIP file will be presented to the user as a download
     * button. This way the user can obtain all the files (used and produced).
     */
    public void returnZIP() throws IOException {
        ServletOutputStream out;
        try (FileInputStream fileIn = new FileInputStream(file)) {
            out = response.getOutputStream();
            byte[] outputByte = new byte[4096];
            // Copy binary content to output stream
            while (fileIn.read(outputByte, 0, 4096) != -1) {
                out.write(outputByte, 0, 4096);
            }
        }
        out.flush();
        out.close();
    }
}
