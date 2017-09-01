package nvz.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloadServlet
 */
@WebServlet("/filedownloader")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String filename = request.getParameter("filename");
		
		if (filename == null || filename.equals("")) {
			PrintWriter out = response.getWriter();
			out.println("Please Enter a valid filename");
			return;
		}
		/*
		File file = new File(request.getServletContext().getAttribute(
				"FILES_DIR")
				+ File.separator + fileName);
		*/
		File file = new File("C:/fileuploads/" + filename);
		if (!file.exists()) {
			PrintWriter out = response.getWriter();
			out.println("Unable to locate file on server");
			return;
		}
		
		ServletContext ctx = getServletContext();
		InputStream inStrm = new FileInputStream(file);
		BufferedInputStream bufferedInStrm = new BufferedInputStream(inStrm);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null ? mimeType
				: "application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + "\"");

		OutputStream out = response.getOutputStream();
		while (bufferedInStrm.available() != 0) {
			out.write(bufferedInStrm.read());
		}
		out.close();
		inStrm.close();
	}

}
