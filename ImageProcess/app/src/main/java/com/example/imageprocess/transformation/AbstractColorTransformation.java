package com.example.imageprocess.transformation;


import com.example.imageprocess.pixels.Pixel;
import com.example.imageprocess.pixels.Pixels;

/**
 * An abstract color transformation that contains the redundant apply method.
 */
public abstract class AbstractColorTransformation implements ColorTransformation {

  @Override
  public void apply(Pixels[][] valueOfrgb) {
    if (valueOfrgb == null) {
      throw new IllegalArgumentException("Null Input");
    }
    double[][] matrix = this.getTransformation();
    Pixels[][] copy = valueOfrgb.clone();
    for (int i = 0; i < valueOfrgb.length; i++) {
      for (int j = 0; j < valueOfrgb[i].length; j++) {
        Pixels p = getNewPixel(matrix, copy[i][j]);
        valueOfrgb[i][j].setRGB(p);
      }
    }
  }

  //a factory methods here for user to change Pixel implementation?
  private Pixels getNewPixel(double[][] matrix, Pixels pixel) {
    return new Pixel(getNewColor(matrix, pixel, 0),
        getNewColor(matrix, pixel, 1),
        getNewColor(matrix, pixel, 2));
  }

  private int getNewColor(double[][] matrix, Pixels pixel, int k) {
    return (int) clamping(pixel.getR() * matrix[k][0] + pixel.getG() * matrix[k][1]
        + pixel.getB() * matrix[k][2]);
  }

  private double clamping(double k) {
    if (k < 0) {
      return 0;
    } else if (k > 255) {
      return 255;
    } else {
      return k;
    }
  }

  protected abstract double[][] getTransformation();

}
