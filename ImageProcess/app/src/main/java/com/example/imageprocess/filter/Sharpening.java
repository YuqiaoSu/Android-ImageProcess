package com.example.imageprocess.filter;

/**
 * Sharpening an image by the filter instance.
 */
public final class Sharpening extends AbstractFilter {

  private static final double[][] sharFilter = {{-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8,},
      {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
      {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
      {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
      {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8,}};


  @Override
  protected double[][] getFilter() {
    return sharFilter;
  }
}
