package com.example.imageprocess.pixels;

/**
 * An interface for representing pixels. A pixel should have a valid representation of its color and
 * methods below to access them.
 */
public interface Pixels {

  /**
   * Get the red value of this Pixel.
   *
   * @return the copy of current red value
   */
  int getR();

  /**
   * Get the green value of this Pixel.
   *
   * @return the copy of current green value
   */
  int getG();

  /**
   * Get the blue value of this Pixel.
   *
   * @return the copy of current blue value
   */
  int getB();

  /**
   * set the current pixel's rgb value by using the designated pixel the value of this pixel should
   * not change as that pixel's value changed.
   *
   * @param pixel the desired pixel
   */
  void setRGB(Pixels pixel);


  Pixels getCopy();
}
