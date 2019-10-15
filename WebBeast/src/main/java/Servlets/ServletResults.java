package Servlets;


import Subprojects.ZippingFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(name = "ServletResults", urlPatterns = "/results")
public class ServletResults extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Create sessions for the users
        HttpSession session = request.getSession(true);
        String sessionID = session.getId();

        // Retrieve the location of the given inputFile given from jsp
        String inputFilePath = getServletContext().getInitParameter("pathForInputFile");

        String sessionDirectory = inputFilePath + "/" + sessionID;
        File folder = new File(sessionDirectory);

        //Calling the class which activates the method for controlling the folder for its files and existence
        //Searching the END tag of the .trees file to communicate with the user when the process is finished
        //to zip all the files which have been used and produced
        ZippingFiles ZF = new ZippingFiles(sessionDirectory, folder, response, inputFilePath);
        ZF.folderControl();
        ZF.findingEndTagForTreeFile();
        ZF.zipFiles();
    }
}
