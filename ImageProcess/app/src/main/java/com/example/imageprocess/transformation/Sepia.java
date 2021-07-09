package com.example.imageprocess.transformation;

/**
 * Transform the color in an image with sepia transformation instance.
 */
public class Sepia extends AbstractColorTransformation {

  private static final double[][] sepia = {{0.393, 0.769, 0.189},
      {0.349, 0.686, 0.168}, {0.272, 0.54, 0.131}};

  @Override
  protected double[][] getTransformation() {
    return sepia;
  }
}
