package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import utility.MakeCollage;
import utility.WordsThumb;

import java.awt.image.BufferedImage;

import databeans.Mapping;

public class ImageAction extends Action {
	public String getName() {
		return "image.do";
	}

	public String perform(HttpServletRequest request) {
		String pid = request.getParameter("collage");
		if (pid != null)
			return "image.jsp";
		HttpSession session = request.getSession(true);

		try {
			if (session.getAttribute("img") == null) {			
				ArrayList<Mapping> forPaint = (ArrayList<Mapping>) session.getAttribute("forPaint");
				ArrayList<String> photos = (ArrayList<String>) session.getAttribute("photos");
				String location = (String) session.getAttribute("location");

				int w = 800, h = 600;
				int sw = w / 20 * 14, sh = h / 20 * 14;
				BufferedImage fg = WordsThumb.drawWordsThumb(forPaint, sw, sh,
						null);
				BufferedImage img = MakeCollage
						.make(location, fg, photos, w, h);

				session.setAttribute("img", img);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "image";
	}
}
