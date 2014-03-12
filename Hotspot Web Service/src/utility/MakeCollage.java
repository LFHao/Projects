package utility;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

public class MakeCollage {
	public static BufferedImage getImageByUrl(String url) {
		try {
			URL iurl = new URL(url);
			BufferedImage ret = ImageIO.read(iurl);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage make(String location, BufferedImage fg, List<String> murl, int width, int height) {
		if (murl == null || murl.size() < 1 || width < MIN_WIDTH || width > MAX_WIDTH || height < MIN_HEIGHT || height > MAX_HEIGHT)
			return null;

		int maxWidth = width / 5;
		int maxHeight = height / 4;
		
		ArrayList<String> urls = new ArrayList<String>();
		if (murl.size() < MAX_SIZE)
			urls.addAll(murl);
		else {
			for (int i = 0; i < MAX_SIZE; i++)
				urls.add(murl.get(i));
		}
		
		
		BufferedImage bg = createBufferedImage(width, height);
		Graphics2D g = bg.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setPaint(new Color(BG_COLOR));
		g.fillRect(0, 0, bg.getWidth(), bg.getHeight());
		
		double delta = 2 * 3.141592653589793d / urls.size();
		double cur = 0;

		for (String url : urls) {
			BufferedImage origin = getImageByUrl(url);
			if (origin == null)
				continue;
			int cwidth = origin.getWidth();
			int cheight = origin.getHeight();
			int rwidth = 0, rheight = 0;

			if (cwidth > cheight) {			
				float ratio = cheight * 1.0f / cwidth;
				rwidth = cwidth > maxWidth ? maxWidth : (cwidth < MIN_RESIZE_WIDTH ? MIN_RESIZE_WIDTH : cwidth);
				rheight = Math.round(rwidth * ratio);
			} else {				
				float ratio = cwidth * 1.0f / cheight;
				rheight = cheight > maxHeight ? maxHeight : (cheight < MIN_RESIZE_HEIGHT ? MIN_RESIZE_HEIGHT : cheight);
				rwidth = Math.round(rheight * ratio);
			}		
			
			int d1 = width / 2 - rwidth / 2 - 5;
			int d2 = height / 2 - rheight / 2 - 5;
			int x = width / 2 + (int) Math.round(d1 * Math.cos(cur)) - rwidth / 2;
			int y = height / 2 + (int) Math.round(d2 * Math.sin(cur)) - rheight / 2;
			
			g.drawImage(origin, x, y, rwidth, rheight, null);
			cur += delta;
		}
		
		g.setComposite(java.awt.AlphaComposite.getInstance(3, 1.0f));
		g.drawImage(fg, width / 2 - fg.getWidth() / 2, height / 2 - fg.getHeight() / 2, fg.getWidth(), fg.getHeight(), null);
		
		String produce = "Produced by zSpot";
		Font font = new Font("Palatino Linotype", 1, 22);
		g.setFont(font);
		g.setPaint(new Color(SIGN_COLOR));
		g.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Rectangle2D bounds = WordsThumb.getStringBounds(g, font, produce);
		Integer w1 = Math.round(width);
		Integer w2 = (int) Math.round(bounds.getWidth());
		Integer h1 = Math.round(height);
		Integer h2 = (int) Math.round(bounds.getHeight());
		
		Rectangle rect = new Rectangle(w1 - w2 - 5, h1 - h2 - 5, w2, h2);
		
		g.drawString(produce, (int)rect.getX(), (int)(rect.getY() - bounds.getY()));
		
		String head = location;
		Font fonth = new Font("Palatino Linotype", 1, 26);
		g.setFont(fonth);
		g.setPaint(new Color(HEAD_COLOR));
		g.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Rectangle2D bounds2 = WordsThumb.getStringBounds(g, font, produce);
		Integer w2h = (int) Math.round(bounds2.getWidth());
		Integer h2h = (int) Math.round(bounds2.getHeight());
		
		Rectangle recth = new Rectangle(5, 5, w2h, h2h);
		
		g.drawString(head, (int)recth.getX(), (int)(recth.getY() - bounds2.getY()));
		
		g.dispose();
		return bg;
	}
	
	public BufferedImage mixImages(BufferedImage images[], Float alphas[], int width, int height) {
		return mixImages(images, alphas, createBufferedImage(width, height));
	}

	public BufferedImage mixImages(BufferedImage images[], Float alphas[], BufferedImage targetImage) {
		if(images == null || images.length < 1)
			return targetImage;
		if(targetImage == null)
		{
			for(int i = 0; i < images.length; i++)
			{
				if(images[i] == null)
					continue;
				targetImage = createBufferedImage(images[0].getWidth(), images[0].getHeight());
				break;
			}

			if(targetImage == null)
				return null;
		}
		java.awt.Graphics2D g2 = targetImage.createGraphics();
		float alpha = 1.0F;
		for(int i = 0; i < images.length; i++)
			if(images[i] != null)
			{
				if(alphas != null && i < alphas.length && alphas[i] != null)
					alpha = alphas[i].floatValue();
				else
					alpha = 1.0F;
				g2.setComposite(java.awt.AlphaComposite.getInstance(3, alpha));
				g2.drawImage(images[i], 0, 0, targetImage.getWidth(), targetImage.getHeight(), 0, 0, images[i].getWidth(), images[i].getHeight(), null);
			}

		g2.dispose();
		targetImage.flush();
		return targetImage;
	}

	private static BufferedImage createBufferedImage(int width, int height) {
		BufferedImage bi = new java.awt.image.BufferedImage(width, height, 2);
		java.awt.Graphics2D g2 = bi.createGraphics();
		bi = g2.getDeviceConfiguration().createCompatibleImage(width, height, 3);
		
		g2.dispose();
		return bi;
	}

	public static final int MIN_RESIZE_WIDTH = 160;
	public static final int MIN_RESIZE_HEIGHT = 120;

	public static final int MIN_WIDTH = 320;
	public static final int MIN_HEIGHT = 240;
	public static final int MAX_WIDTH = 2560;
	public static final int MAX_HEIGHT = 1920;
	
	public static final int SIGN_COLOR = 0x0B2161;
	public static final int HEAD_COLOR = 0x0B3B39;
	public static final int BG_COLOR = 0xBDBDBD;
	
	public static final int MAX_SIZE = 16;
}
