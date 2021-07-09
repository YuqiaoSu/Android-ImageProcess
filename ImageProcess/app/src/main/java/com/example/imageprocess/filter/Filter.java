package com.example.imageprocess.filter;


import com.example.imageprocess.pixels.Pixels;

/**
 * The interface for filter that applies to the image.
 */
public interface Filter {


  /**
   * Apply a certain filter to an image If valueOfrgb is null, throw new IllegalArgumentException
   * (blur before import) Operate one at a time for pixels inside the given picture with the center
   * of a filter at current pixel and cumulate overlapped value and the value for red, green, blue
   * is calculated separately. For each calculation the original image is remained the same and a
   * new image is created due to incremented calculation and in the designated sequence.
   *
   * <p>If the calculation returns the value either greater than 255 or lower than 0, set them to
   * 255 and 0 respectively.
   */
  void apply(Pixels[][] valueOfrgb);
}
