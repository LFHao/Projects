package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import databeans.*;
import flickrapi.*;
import twitterapi.*;

public class MapAction extends Action {

	@Override
	public String getName() {
		return "map.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		String location = null;
		
		String val = request.getParameter("search");	
		HttpSession session = request.getSession(true);
		
		if (request.getParameter("mapbutton") == null && request.getParameter("search")==null) {
			session.invalidate();
			return "map.jsp";
		}

		if (request.getParameter("search") != null && (val == null || val.length() == 0)) {
			session.invalidate();
			return "map.jsp";
		}
		
		if (val != null)
			location = val;
		else
			location = request.getParameter("location");
		List<String> photos = new ArrayList<String>();
		try {
			photos.addAll(FlickrSearchTopic.getPhotos(location));
			ArrayList<Tweet> tweets = TwitterSearchTopic.searchTopic(location, 15);
			ArrayList<Mapping> forPaint = TwitterGetHotWords.getPopularWords(location);
			ArrayList<Mapping> popular = new ArrayList<Mapping>();
			for (int i = 0; i < 10; i++) {
				if (i < forPaint.size())
					popular.add(forPaint.get(i));
			}
			
			session.setAttribute("forPaint", forPaint);
			session.setAttribute("popular", popular);
			session.setAttribute("tweets", tweets);
			session.setAttribute("location", location);
			session.setAttribute("photos", photos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "searchresult.jsp";
	}

}
