package com.example.imageprocess.extra;

import com.example.imageprocess.pixels.Pixels;

/**
 * The downscaling interface that help to change the size of the interface.
 */
public interface Downscaling {

  /**
   * The method that takes an image and change its size to the expected.
   *
   * @param pixels    the data set that contains the pixels of a image.
   * @param newWidth  the new width of the image.
   * @param newHeight the new height of the image.
   * @return a new image with the given size.
   */
  Pixels[][] apply(Pixels[][] pixels, int newWidth, int newHeight);
}
