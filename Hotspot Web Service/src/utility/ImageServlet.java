package utility;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HttpSession session = request.getSession(true);
    	BufferedImage img = (BufferedImage) session.getAttribute("img");

        if (img == null) {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }
        
        response.setContentType("image/png");

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(img, "png", out);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	doGet(request, response);
    }
}