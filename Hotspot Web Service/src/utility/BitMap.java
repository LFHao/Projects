// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BitMap.java

// Modified by Bo Zhang, Carnegie Mellon University, U.S.A. Feb 14, 2014.

package utility;

public class BitMap {

    public BitMap(int width, int height) {
        if(width <= 0 || height <= 0)
            throw new IllegalArgumentException();
        this.width = width;
        this.height = height;
        byteWidth = (int)Math.ceil((double)width / 8D);
        bitMap = new byte[height][byteWidth];
        for(int y = 0; y < height; y++)
            for(int x = 0; x < byteWidth; x++)
                bitMap[y][x] = 0;
    }

    public int getWidth(){ return width; }

    public int getHeight() { return height; }

    public void updateArea(BitMap bm, int x, int y) {
        int y1 = y < 0 ? 0 : y;
        for(int y2 = y1 - y; y1 < height && y2 < bm.height; y2++) {
            int x1 = x < 0 ? 0 : x;
            for(int x2 = x1 - x; x1 < byteWidth && x2 < bm.byteWidth; x2++) {
                setUsed(x1, y1, bm.isUsed(x2, y2));
                x1++;
            }
            y1++;
        }
    }

    public boolean isOverlap(BitMap bm, int x, int y) {
        int y1 = y < 0 ? 0 : y;
        for(int y2 = y1 - y; y1 < height && y2 < bm.height; y2++) {
            int x1 = x < 0 ? 0 : x;
            for(int x2 = x1 - x; x1 < width && x2 < bm.width; x2++) {
                if(isUsed(x1, y1) && bm.isUsed(x2, y2))
                    return true;
                x1++;
            }
            y1++;
        }
        return false;
    }

    public boolean isUsed(int x, int y) {
        int byteX = x / 8;
        if(byteX < 0 || byteX >= byteWidth || y < 0 || y >= height)
            throw new IllegalArgumentException();
        int mask = 1 << 7 - (x % 8 + 8) % 8;
        return (bitMap[y][byteX] & mask) != 0;
    }

    public void setUsed(int x, int y, boolean used) {
        int byteX = x / 8;
        if(byteX < 0 || byteX >= byteWidth || y < 0 || y >= height)
            throw new IllegalArgumentException();
        int mask = 1 << 7 - (x % 8 + 8) % 8;
        if(used)
            bitMap[y][byteX] |= mask;
        else
            bitMap[y][byteX] &= ~mask;
    }

    public void debug() {
        System.out.println((new StringBuilder("=== DEBUG ( ")).append(width).append(" x ").append(height).append(" ) ===").toString());
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                boolean used = isUsed(x, y);
                System.out.print(used ? "*" : ".");
            }
            System.out.println();
        }
    }

    private byte bitMap[][];
    private int width;
    private int height;
    private int byteWidth;
}