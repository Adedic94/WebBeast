package Servlets;

import Subprojects.RunBeast;
import Subprojects.RunningPrograms;
import Subprojects.Sessions;
import java.io.*;
import java.nio.file.Paths;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "UploadServlet", urlPatterns = "/test")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Create sessions for the users
        HttpSession session = request.getSession(true);
        String sessionID = session.getId();

        // Make sure current page is not changed
        String viewJsp = "index.jsp";
        if (session.getId() == null) {
            viewJsp = "index.jsp";
        }

        // Retrieve input type/name from index.jsp
        Part nexFilePart = request.getPart("nexFile");

        // Retrieve the input filepath from index.jsp
        String nexFileName = Paths.get(nexFilePart.getSubmittedFileName()).getFileName().toString();

        InputStream nexFileContent = nexFilePart.getInputStream();

        // Retrieve the location of the given inputFile given from jsp
        String inputFilePath = getServletContext().getInitParameter("pathForInputFile");

        // Retrieve the location of Beast
        String beastPath = getServletContext().getInitParameter("pathForBeast");

        // Retrieve the location of BEAUti
        String beautiPath = getServletContext().getInitParameter("pathForBEAUti");

        // send the variables to the next class
        Sessions ss = new Sessions(sessionID, inputFilePath);
        File sessionDirectory = ss.session();

        //Write the uploaded file to a new session directory
        nexFilePart.write(inputFilePath + "/" + sessionID + "/" + nexFileName);

        /*
         * Runs the BEAUti program which creates an .XML file from .NEX file given by the user
         * This .XML file is then used in a TIME control mechanism to know whether the process has finished
         * after it has finished, it returns the .XML file which can then be used in the further process:BEAST
         */
        RunningPrograms RP = new RunningPrograms(beautiPath, sessionDirectory, nexFileName);
        String xmlName = RP.startBEAUti();

        //Runs the BEAST program which creates an .trees file from the .XML file which has just been produced.
        RunBeast rb = new RunBeast(beastPath, sessionDirectory, xmlName);
        rb.runBeast();

        RequestDispatcher view = request.getRequestDispatcher(viewJsp);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}