package com.example.imageprocess.ImageOperation;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.imageprocess.extra.ImageMosaicing;
import com.example.imageprocess.extra.Mosaicing;
import com.example.imageprocess.filter.Blur;
import com.example.imageprocess.filter.Filter;
import com.example.imageprocess.filter.Sharpening;
import com.example.imageprocess.pixels.Pixel;
import com.example.imageprocess.pixels.Pixels;
import com.example.imageprocess.transformation.ColorTransformation;
import com.example.imageprocess.transformation.Greyscale;
import com.example.imageprocess.transformation.Sepia;

import java.nio.ByteBuffer;
import java.util.Stack;

public class ImageProcessor implements ImageOperator {

    Pixels[][] image=null;

    Filter filter;
    ColorTransformation transformation;

    @Override
    public void blur() {
        filter = new Blur();
        filter.apply(image);
    }

    @Override
    public void sharpening() {
        filter = new Sharpening();
        filter.apply(image);
    }

    @Override
    public void sepia() {
        transformation = new Sepia();
        transformation.apply(image);
    }

    @Override
    public void greyScale() {
        transformation = new Greyscale();
        transformation.apply(image);
    }

    @Override
    public void mosaic(int seeds) {
        Mosaicing filter = new ImageMosaicing();
        filter.apply(image, seeds);
    }



    @Override
    public void setPicture(Bitmap image) {
        if (image == null) {
            throw new IllegalArgumentException("Null Image");
        }
        Pixels[][] temp = getRGBFromBMP(image);
        this.image = temp;
    }

    @Override
    public Bitmap getPicture() {
        return rgb2Bitmap(image,image[0].length,image.length);
    }


    public static Pixels[][] bitmap2RGB(Bitmap bitmap) {
        int bytes = bitmap.getByteCount();  //返回可用于储存此位图像素的最小字节数

        ByteBuffer buffer = ByteBuffer.allocate(bytes); //  使用allocate()静态方法创建字节缓冲区
        bitmap.copyPixelsToBuffer(buffer); // 将位图的像素复制到指定的缓冲区

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        byte[] rgba = buffer.array();
        Pixels[][] pixels = new Pixels[height][width];

        int max = rgba.length / 4;
        int count = 0;
        while (count < max) {
            //Bitmap像素点的色彩通道排列顺序是RGBA
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    Pixels pixel = new Pixel(rgba[count * 4], rgba[count * 4 + 1], rgba[count * 4 + 2]);
                    pixels[i][j] = pixel;
                    count++;
//            pixels[i * 3] = rgba[i * 4];        //R
//            pixels[i * 3 + 1] = rgba[i * 4 + 1];    //G
//            pixels[i * 3 + 2] = rgba[i * 4 + 2];       //B

                }
            }
        }

        return pixels;

    }

    public static Pixels[][] getRGBFromBMP(Bitmap bmp) {

        int w = bmp.getWidth();
        int h = bmp.getHeight();
        Pixels[][] pixels = new Pixels[h][w];
        int k = 0;

        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++) {
                int color = bmp.getPixel(y, x);
                Pixel pixel = new Pixel(Color.red(color),
                        Color.green(color),
                        Color.blue(color));
                pixels[x][y] = pixel;
                k++;
            }
        }

        return pixels;
//source code :https://blog.csdn.net/jhope/article/details/80843686?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_baidulandingword-0&spm=1001.2101.3001.4242

    }

    static public Bitmap rgb2Bitmap(Pixels[][] data, int width, int height) {
        int[] colors = convertByteToColor(data);
        if (colors == null) {
            return null;
        }
        Bitmap bmp = Bitmap.createBitmap(colors, 0, width, width, height,
                Bitmap.Config.ARGB_8888);
        return bmp;
    }


    public static int[] convertByteToColor(Pixels[][] data) {
        int size = data.length * data[0].length;
        if (size == 0) {
            return null;
        }
        int[] color = new int[size];
        int red, green, blue;
        int colorLen = 0;
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < data[i].length; ++j) {
                red = data[i][j].getR();
                green = data[i][j].getG();
                blue = data[i][j].getB();

                // 获取RGB分量值通过按位或生成int的像素值
                color[colorLen] = (red << 16) | (green << 8) | blue | 0xFF000000;
                colorLen++;
            }
        }
        return color;
    }
}
