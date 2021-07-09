package com.example.imageprocess.transformation;

/**
 * Transform the color in an image with grey scale transformation instance.
 */
public class Greyscale extends AbstractColorTransformation {

  private static final double[][] grey = {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722}};

  @Override
  protected double[][] getTransformation() {
    return grey;
  }
}
