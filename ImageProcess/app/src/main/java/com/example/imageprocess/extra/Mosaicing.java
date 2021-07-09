package com.example.imageprocess.extra;

import com.example.imageprocess.filter.Filter;
import com.example.imageprocess.pixels.Pixels;

/**
 * An interface that helps to mosicing an image.
 */
public interface Mosaicing extends Filter {

  /**
   * A method that takes a image and number of seeds and to mosicing the image with the given number
   * of seeds.
   *
   * @param pixels        the data set that contains the pixels of the image.
   * @param numberOfSeeds the number of seeds the user wants to generate in the image.
   */
  void apply(Pixels[][] pixels, int numberOfSeeds);
}
