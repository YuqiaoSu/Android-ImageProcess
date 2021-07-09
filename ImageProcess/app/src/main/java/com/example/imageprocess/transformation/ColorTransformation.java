package com.example.imageprocess.transformation;


import com.example.imageprocess.pixels.Pixels;

/**
 * The interface for color transformation that applies to the image.
 */
public interface ColorTransformation {

  /**
   * Apply an matrix multiplication with a matrix to a vector that is constructed using the r,g,b
   * value in each pixels of the given picture. For each of the calculation, If the calculation
   * returns the value either greater than 255 or lower than 0, set them to 255 and 0 respectively.
   * The original image is preserved during calculation and the new value is introduce after the end
   * of entire transformation.
   *
   * @param valueOfrgb An representation of image in Pixels
   */
  void apply(Pixels[][] valueOfrgb);
}
