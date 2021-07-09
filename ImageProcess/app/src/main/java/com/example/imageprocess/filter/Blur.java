package com.example.imageprocess.filter;

/**
 * Blur an image with the blur filter instance.
 */
public final class Blur extends AbstractFilter {

  private static final double[][] blurFilter = {{1.0 / 16, 1.0 / 8, 1.0 / 16},
      {1.0 / 8, 1.0 / 4, 1.0 / 8}, {1.0 / 16, 1.0 / 8, 1.0 / 16}};


  @Override
  protected double[][] getFilter() {
    return this.blurFilter;
  }
}
