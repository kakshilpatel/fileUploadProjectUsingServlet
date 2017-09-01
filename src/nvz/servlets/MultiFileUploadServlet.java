package nvz.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class MultiFileUploadServlet
 */
@WebServlet("/multifileuploader")
@MultipartConfig(location="c:/fileuploads")
public class MultiFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultiFileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected String getFileSuffix(Part part) {
		String contentDispositionHeader = part.getHeader("content-disposition");
		System.out.println(contentDispositionHeader);
		String[] elements = contentDispositionHeader.split(";");
		String tstElement, fileName, suffixStr;
		int fileNameIdx, suffixIdx;
		for (String element: elements) {
			tstElement = element.trim();
			if (tstElement.startsWith("filename")) {
				fileNameIdx = element.indexOf('=')+1;
				fileName = tstElement.substring(fileNameIdx);
				suffixIdx = fileName.indexOf('.');
				suffixStr = fileName.substring(suffixIdx, fileName.length()-1);
				return suffixStr;
			}
		}
		
		return ".txt";
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String idStr = request.getParameter("submitterId");
		String fileName, fileSuffix;
		int fileCnt = 1;
		Collection<Part> parts = request.getParts();
		
		for (Part formPart: parts) {
			if (formPart.getContentType() == null) {
				// This is not a part of the form that is not a file -- e.g. a submit button
			} else {
				fileSuffix = getFileSuffix(formPart);
				fileName = idStr + "_" + fileCnt + fileSuffix;
				System.out.println(fileName);
				formPart.write(fileName);
				fileCnt++;
			}
		}
		
		out.write("The files have been uploaded.");
	}

}
