package nvz.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class FileUploaderServlet
 */
@WebServlet("/fileuploader")
@MultipartConfig(location="c:/fileuploads")
public class FileUploaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FileUploaderServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName;
		String idStr = request.getParameter("submitterId");
		Part formPart = request.getPart("content");
		
		fileName = idStr + getFileSuffix(formPart);
		//get the "content" part and save it.
	    formPart.write(fileName);
	}
	
	protected String getFileSuffix(Part part) {
		String contentDispositionHeader = part.getHeader("content-disposition");
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
}


