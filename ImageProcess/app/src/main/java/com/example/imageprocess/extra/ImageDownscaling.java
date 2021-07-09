package com.example.imageprocess.extra;

import com.example.imageprocess.pixels.Pixel;
import com.example.imageprocess.pixels.Pixels;

/**
 * A simple implementation of the Downscaling interface that helps to down scale the image.
 */
public class ImageDownscaling implements Downscaling {

  @Override
  public Pixels[][] apply(Pixels[][] pixels, int newWidth, int newHeight) {

    if (pixels == null || pixels.length == 0 || pixels[0].length == 0) {
      throw new IllegalArgumentException("The input image is empty.");
    }

    if (newHeight < 0 || newWidth < 0) {
      throw new IllegalArgumentException("Cannot resize the image to negative size!");
    }

    if (newHeight >= pixels.length || newWidth >= pixels[0].length) {
      throw new IllegalArgumentException("The input size cannot be bigger than the original.");
    }

    int width = pixels[0].length;
    int height = pixels.length;
    Pixels[][] newImage = new Pixels[newHeight][newWidth];

    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {
        getNewPixel(pixels, i, j, width, height, newWidth, newHeight, newImage);
      }
    }

    return newImage;
  }

  private void getNewPixel(Pixels[][] pixels,
      int i, int j, int width, int height, int newWidth
      , int newHeight, Pixels[][] newImage) {

    double newX = (j / (double) newWidth) * (double) width;
    double newY = (i / (double) newHeight) * (double) height;

    if (newX == 0) {
      newX = 0.5;
    }

    if (newY == 0) {
      newY = 0.5;
    }

    Pixels[] pixelsAround = getPixelAround(pixels, newX, newY);

    int newRed = getCP(newY,
        getMorN(newX, pixelsAround[0].getR(), pixelsAround[1].getR()),
        getMorN(newX, pixelsAround[2].getR(), pixelsAround[3].getR()));
    int newGreen = getCP(newY,
        getMorN(newX, pixelsAround[0].getG(), pixelsAround[1].getG()),
        getMorN(newX, pixelsAround[2].getG(), pixelsAround[3].getG()));
    int newBlue = getCP(newY,
        getMorN(newX, pixelsAround[0].getB(), pixelsAround[1].getB()),
        getMorN(newX, pixelsAround[2].getB(), pixelsAround[3].getB()));
    Pixels newPixel = new Pixel(newRed, newGreen, newBlue);
    newImage[i][j] = newPixel;
  }

  private int getCP(double y, double m, double n) {
    if (y == Math.floor(y)) {
      return (int) (n + m) / 2;
    }

    return (int) (n * (y - Math.floor(y)) + m * (Math.ceil(y) - y));
  }

  private double getMorN(double x, double ca, double cb) {
    if (x == Math.floor(x)) {
      return (cb + ca) / 2.0;
    }

    return cb * (x - Math.floor(x)) + ca * (Math.ceil(x) - x);
  }

  private Pixels getPixel(Pixels[][] pixels, int x, int y) {
    if (x >= pixels[0].length || y >= pixels.length) {
      return new Pixel(1, 1, 1);
    }

    return pixels[y][x];
  }

  private Pixels[] getPixelAround(Pixels[][] pixels, double newX, double newY) {
    int ceilX = (int) Math.ceil(newX);
    int floorX = (int) Math.floor(newX);
    int ceilY = (int) Math.ceil(newY);
    int floorY = (int) Math.floor(newY);

    Pixels a = getPixel(pixels, floorX, floorY);
    Pixels b = getPixel(pixels, ceilX, floorY);
    Pixels c = getPixel(pixels, floorX, ceilY);
    Pixels d = getPixel(pixels, ceilX, ceilY);

    Pixels[] result = {a, b, c, d};

    return result;
  }
}
