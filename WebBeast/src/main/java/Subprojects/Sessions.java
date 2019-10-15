package Subprojects;

import java.io.File;

public class Sessions {
    private final String sessionID;
    private final String inputFilePath;

    /**
     * This is an constructor containing the variables which are then used by the startBEAUti method.
     * @param sessionID, is the unique ID created for each user.
     * @param inputFilePath, location of the sessionDirectories.
     */
    public Sessions(String sessionID, String inputFilePath) {
        this.sessionID = sessionID;
        this.inputFilePath = inputFilePath;
        session();
    }

    /**
     * This method creates a sessionDirectory for each time a user opens a session. This way there will be
     * no interruption between them.
     * @return sessionDirectory, which is the directory of the user containing its files(uploaded and produced)
     */
    public File session() {
        File dataDirectory = new File(inputFilePath);
        //TODO the "/" must be "\\" for windows users
        File sessionDirectory = new File(inputFilePath + "/" + sessionID);
        System.out.println("sessionDirectory = " + sessionDirectory);
        if (!dataDirectory.exists()) {
            dataDirectory.mkdir();
        }
        sessionDirectory.mkdir();
        return sessionDirectory;
    }
}