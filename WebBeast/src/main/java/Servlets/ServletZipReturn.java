package Servlets;

import Subprojects.ReturningZip;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "ServletZipReturn",urlPatterns = {"/download"})
public class ServletZipReturn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputFilePath = getServletContext().getInitParameter("pathForInputFile");
        String fileToDl = "Obtained_Data.zip";

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileToDl);

        File file = new File(inputFilePath + "/" + fileToDl);

        //Calling the class which has the method to return the zip properly to the web page for the user.
        ReturningZip RZ = new ReturningZip(file, response);
        RZ.returnZIP();
    }
}
