// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WordsThumb.java

// Modified by Bo Zhang, Carnegie Mellon University, U.S.A. Feb 14, 2014.

package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;

import databeans.*;

// Referenced classes of package com.ctrip:
//            BitMap

public class WordsThumb {
	public static class MapComparator implements Comparator<Mapping> {
		public int compare(Mapping o1, Mapping o2) {
			return o2.value - o1.value;
		}
	}

	public static BufferedImage createWordsThumb(List<Mapping> words, int width, int height, String fontName, Integer maxFontSize, Integer minFontSize) {
		if(words == null || words.size() < 1 || width < MIN_WIDTH || width > MAX_WIDTH || height < MIN_HEIGHT || height > MAX_HEIGHT)
			return null;
		fontName = validateFontName(fontName, DEF_FONT);
		Collections.sort(words, new MapComparator());
		BufferedImage bi = createBufferedImage(width, height);
		if(bi == null)
			return null;
		if(maxFontSize == null)
			maxFontSize = Math.min(width, height) / 7;
		if(minFontSize == null)
			minFontSize = Math.min(width, height) / 30;
		if(minFontSize < MIN_FONT_SIZE)
			minFontSize = Integer.valueOf(MIN_FONT_SIZE);
		if(maxFontSize < minFontSize)
			maxFontSize = minFontSize;
		if(paintWords(bi, words, fontName, maxFontSize, minFontSize) < 1)
			return null;
		else
			return bi;
	}

	private static String validateFontName(String fontName, String defFont) {
		if(fontName != null) {
			Font font = new Font(fontName, 0, 16);
			if(font.getName().equals(fontName))
				return fontName;
		}
		return defFont;
	}
	
	private static BufferedImage createBufferedImage(int width, int height) {
		java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(width, height, 2);
		java.awt.Graphics2D g2 = bi.createGraphics();
		bi = g2.getDeviceConfiguration().createCompatibleImage(width, height, 3);
		g2.dispose();
		return bi;
	}

	private static int paintWords(BufferedImage bi, List<Mapping> words, String fontName, int maxFontSize, int minFontSize) {
		BitMap bitMap = initBitMap(bi);
		java.awt.Graphics2D g2 = bi.createGraphics();
		int wordCount = 0;
		int fontSizeAdjust = 0;
		int maxFrequency = words.get(0).value;
		int minFrequency = words.get(words.size() - 1).value;
		//int maxFrequency = words.size() - 1;
		//int minFrequency = 0;
		for (int i = 0; i < words.size(); i++) {
			do {
				java.awt.Font font = initFont(g2, fontName, maxFontSize, minFontSize, words.get(i).value, maxFrequency, minFrequency, fontSizeAdjust);
				if(font.getSize() < minFontSize)
					return wordCount;
				java.awt.geom.Rectangle2D bounds = getStringBounds(g2, font, words.get(i).key);
				int direction = (int)((Math.random() * 10000D) % 4D % 3D);
				java.awt.geom.Rectangle2D rect = bounds.getBounds();
				if(direction != 0)
					rect.setRect(rect.getX(), rect.getY(), rect.getHeight(), rect.getWidth());
				rect = findSpace(bitMap, rect);
				if(rect == null) {
					rect = bounds.getBounds();
					if(direction == 0) {
						direction = (int)((Math.random() * 10000D) % 2D + 1.0D);
						rect.setRect(rect.getX(), rect.getY(), rect.getHeight(), rect.getWidth());
					} else {
						direction = 0;
					}
					rect = findSpace(bitMap, rect);
				}
				if(rect != null) {
					paintOneWord(bi, g2, words.get(i).key, direction, rect, bounds);
					updateBitMap(bitMap, bi, rect);
					wordCount++;
					break;
				}
				fontSizeAdjust--;
			} while(true);
		}

		return wordCount;
	}

	private static BitMap initBitMap(BufferedImage bi) {
		int w = (int)Math.ceil((double)bi.getWidth() / 4D);
		int h = (int)Math.ceil((double)bi.getHeight() / 4D);
		return new BitMap(w, h);
	}

	private static Font initFont(Graphics2D g2, String fontName, Integer maxFontSize, Integer minFontSize, Integer frequency, Integer maxFrequency, Integer minFrequency, Integer fontSizeAdjust) {
		int fs = 24;
		if(maxFrequency.intValue() > minFrequency.intValue())
			fs = (int)((((double)frequency - (double)minFrequency) * (double)(maxFontSize - minFontSize)) / (double)(maxFrequency - minFrequency) + (double)minFontSize);
		if(fontSizeAdjust != null)
			fs += fontSizeAdjust.intValue();
		java.awt.Font font = new java.awt.Font(fontName, 1, fs);
		g2.setFont(font);
		g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		return font;
	}

	public static Rectangle2D getStringBounds(Graphics2D g2, java.awt.Font font, String key) {
		java.awt.font.FontRenderContext frc = g2.getFontRenderContext();
		java.awt.geom.Rectangle2D rc = font.getStringBounds(key, frc);
		return rc;
	}

	private static Rectangle2D findSpace(BitMap bitMap, Rectangle2D rect) {
		int w = pixel2bitMap((int)rect.getWidth());
		int h = pixel2bitMap((int)rect.getHeight());
		int boundW = bitMap.getWidth() - w;
		int boundH = bitMap.getHeight() - h;
		int start_x = boundW / 2;
		int start_y = boundH / 2;
		int maxBound = Math.max(boundW, boundH);
		double wRatio = (double)boundW / (double)maxBound;
		double hRatio = (double)boundH / (double)maxBound;
		double max_r = Math.sin(0.78539816339744828D) * (double)maxBound;
		double r = 1.0D;
		double a = 0.0D;
		double a_tmp = 0.0D;
		double s = 1.0D;
		double step = 1.0D;
		int x = start_x;
		for (int y = start_y; r < max_r; y = (int)(Math.cos(a) * r * hRatio + (double)start_y)) {
			if (isClean(bitMap, x, y, w, h)) {
				int targetX = bit2pixel(x);
				int targetY = bit2pixel(y);
				return new java.awt.Rectangle(targetX, targetY, (int)rect.getWidth(), (int)rect.getHeight());
			}
			a_tmp = s / r <= 0.78539816339744828D ? s / r : 0.78539816339744828D;
			a += a_tmp;
			r += step * (a_tmp / 6.2831853071795862D);
			x = (int)(Math.sin(a) * r * wRatio + (double)start_x);
		}

		return null;
	}

	private static Rectangle2D findSpace2(BitMap bitMap, Rectangle2D rect) {
		int w = pixel2bitMap((int)rect.getWidth());
		int h = pixel2bitMap((int)rect.getHeight());
		int bounds[] = {
				bitMap.getWidth() - w, bitMap.getHeight() - h
		};
		int step_len[] = new int[2];
		int marchDir = 0;
		int x;
		int y;
		if(bounds[0] > bounds[1]) {
			y = bounds[1] / 2;
			x = y;
			step_len[0] = bounds[0] - bounds[1];
			step_len[1] = 1;
		} else {
			marchDir = 3;
			x = bounds[0] / 2;
			y = x;
			step_len[0] = 1;
			step_len[1] = bounds[1] - bounds[0];
			if(step_len[1] == 0)
				step_len[1] = 1;
		}
		int step[] = new int[4];
		while(!isClean(bitMap, x, y, w, h)) {
			step[marchDir]++;
			if(step[marchDir] > step_len[marchDir % 2]) {
				step[marchDir] = 0;
				step_len[marchDir % 2]++;
				marchDir = ++marchDir % 4;
				if(step_len[marchDir % 2] > bounds[marchDir % 2])
					return null;
			}
			switch(marchDir) {
			case 0: // '\0'
				x++;
				break;

			case 1: // '\001'
				y--;
				break;

			case 2: // '\002'
				x--;
				break;

			case 3: // '\003'
				y++;
				break;
			}
		}
		int targetX = bit2pixel(x);
		int targetY = bit2pixel(y);
		return new java.awt.Rectangle(targetX, targetY, (int)rect.getWidth(), (int)rect.getHeight());
	}

	private static int pixel2bitMap(int v) {
		return (int)Math.ceil((double)v / 4D);
	}

	private static boolean isClean(BitMap bitMap, int x, int y, int w, int h) {
		if(x < 0 || x + w >= bitMap.getWidth() || y < 0 || y + h >= bitMap.getHeight())
			return false;
		for(int i = x; i < x + w; i++)
			for(int j = y; j < y + h; j++)
				if(bitMap.isUsed(i, j))
					return false;

		return true;
	}

	private static int bit2pixel(int v) { return v * 4; }

	public static void paintOneWord(BufferedImage bi, java.awt.Graphics2D g2, String word, int direction, Rectangle2D rect, Rectangle2D orgBounds) {
		g2.setPaint(randomColor());
		if(direction == 1) {
			g2.rotate(1.5707963267948966D, rect.getX(), rect.getY());
			g2.drawString(word, (int)rect.getX(), (int)(rect.getY() - rect.getWidth() - orgBounds.getY()));
			g2.rotate(-1.5707963267948966D, rect.getX(), rect.getY());
		} else
			if(direction == 2) {
				g2.rotate(-1.5707963267948966D, rect.getX(), rect.getY());
				g2.drawString(word, (int)(rect.getX() - rect.getHeight()), (int)(rect.getY() - orgBounds.getY()));
				g2.rotate(1.5707963267948966D, rect.getX(), rect.getY());
			} else {
				g2.drawString(word, (int)rect.getX(), (int)(rect.getY() - orgBounds.getY()));
			}
		bi.flush();
	}

	private static java.awt.Paint randomColor() {
		int rgb = COLORS[(int)(Math.random() * 10000D) % COLORS.length];
		return new java.awt.Color(rgb);
	}

	private static void updateBitMap(BitMap bitMap, java.awt.image.BufferedImage bi, java.awt.geom.Rectangle2D rect) {
		int l = (int)rect.getX();
		int t = (int)rect.getY();
		int r = l + (int)rect.getWidth();
		int b = t + (int)rect.getHeight();
		if(l < 0)
			l = 0;
		if(t < 0)
			t = 0;
		if(r > bi.getWidth())
			r = bi.getWidth();
		if(b > bi.getHeight())
			b = bi.getHeight();
		if(r <= l || b <= t)
			return;
		for(int y = t; y < (b + 4) - 1; y += 4) {
			for(int x = l; x < (r + 4) - 1; x += 4) {
				for(int i = 0; i < 16; i++) {
					int x2 = x + i % 4;
					int y2 = y + i / 4;
					if(x2 >= bi.getWidth() || y2 >= bi.getHeight() || bi.getRGB(x + i % 4, y + i / 4) == 0)
						continue;
					bitMap.setUsed(pixel2bitMap(x), pixel2bitMap(y), true);
					break;
				}

			}

		}

	}

	public static BufferedImage mixImages(BufferedImage images[], Float alphas[], int width, int height) {
		return mixImages(images, alphas, createBufferedImage(width, height));
	}

	public static BufferedImage mixImages(BufferedImage images[], Float alphas[], BufferedImage targetImage) {
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
	
	public static BufferedImage drawWordsThumb(ArrayList<Mapping> words, int width, int height, BufferedImage bg) throws IOException {
		if (words.size() < 1) {
			System.out.println("List words should not be empty");
			return null;
		}
		
		BufferedImage bi = createWordsThumb(words, width, height, DEF_FONT, null, null);
		float fgAlpha = 0.8f;
		
		bi = mixImages(new BufferedImage[] {bg, bi}, new Float[] {1.0f, fgAlpha}, bi.getWidth(), bi.getHeight());
		System.out.println("Image processed!");
		return bi;
	}

	public static final int MIN_WIDTH = 320;
	public static final int MIN_HEIGHT = 240;
	public static final int MAX_WIDTH = 2560;
	public static final int MAX_HEIGHT = 1920;
	public static final int BLOCK = 4;
	public static final String DEF_FONT = "Palatino Linotype";
	public static final int MIN_FONT_SIZE = 24;
	private static final int COLORS[] = {
		0x4169E1, 0x8B4513, 0xFA8072, 0xF4A460, 0x2E8B57, 0xE374AB, 0xA0522D, 0x0874A1, 0x433B78, 
		0x39536E, 0x066636, 0x4682B4, 0x806A4E, 0x474747, 0x008080, 0x8F1F8F, 0xFC3E1C, 0x20B3A4, 0x3B8A94
	};
}
