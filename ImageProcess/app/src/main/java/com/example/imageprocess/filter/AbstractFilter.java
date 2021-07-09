package com.example.imageprocess.filter;

import com.example.imageprocess.pixels.Pixel;
import com.example.imageprocess.pixels.Pixels;

/**
 * The abstract filter that mainly support applying different filter to a given image.
 */
public abstract class AbstractFilter implements Filter {

  @Override
  public void apply(Pixels[][] valueOfrgb) {
    if (valueOfrgb == null) {
      throw new IllegalArgumentException("Null Input");
    }

    double[][] filter = getFilter();
    Pixels[][] original = valueOfrgb.clone();

    for (int i = 0; i < valueOfrgb.length; i++) {
      for (int j = 0; j < valueOfrgb[i].length; j++) {
        int centerPosition = (filter.length - 1) / 2;
        int startPositionX = i - centerPosition;
        int startPositionY = j - centerPosition;

        Pixel p = calculateAPoint(startPositionX, startPositionY, filter.length, original, filter);

        valueOfrgb[i][j].setRGB(p);

      }
    }
  }

  /**
   * Factory method that determine which filter to use.
   *
   * @return the private filter in each implementation
   */
  protected abstract double[][] getFilter();

  private Pixel calculateAPoint(int startPositionX, int startPositionY, int filterLength,
                                Pixels[][] valueOfrgb, double[][] filter) {

    double countR = 0;
    double countG = 0;
    double countB = 0;

    for (int i = startPositionX; i < startPositionX + filterLength; i++) {
      for (int j = startPositionY; j < startPositionY + filterLength; j++) {
        if (i < 0 || j < 0 || i >= valueOfrgb.length || j >= valueOfrgb[0].length) {
          continue;
        }

        double getNum = filter[i - startPositionX][j - startPositionY];

        countR = getNum * valueOfrgb[i][j].getR() + countR;
        countG = getNum * valueOfrgb[i][j].getG() + countG;
        countB = getNum * valueOfrgb[i][j].getB() + countB;
      }
    }
    countR = clamping(countR);
    countG = clamping(countG);
    countB = clamping(countB);

    return new Pixel((int) countR, (int) countG, (int) countB);
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

}
