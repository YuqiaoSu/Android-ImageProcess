package com.example.imageprocess.pixels;

/**
 * To represent a pixel by separating r,g,b value into three int that in combination represent the
 * pixel's color.
 */
public class Pixel implements Pixels {

  private int r;
  private int g;
  private int b;

  /**
   * The constructor for a pixel. Each field of the pixel is a number between 0 and 255, to
   * represent a color in combine.
   *
   * @param r the value of red.
   * @param g the value of green.
   * @param b the value of blue.
   */
  public Pixel(int r, int g, int b) {
    if (checker(r) && checker(g) && checker(b)) {
      this.r = r;
      this.g = g;
      this.b = b;
    } else {
      throw new IllegalArgumentException("Illegal value for rgb!!!");
    }
  }

  private boolean checker(int num) {
    return num >= 0 && num <= 255;
  }

  @Override
  public int getR() {
    int temp = r;
    return temp;
  }

  @Override
  public int getG() {
    int temp = g;
    return temp;
  }

  @Override
  public int getB() {
    int temp = b;
    return temp;
  }

  @Override
  public void setRGB(Pixels pixel) {
    if (pixel == null) {
      throw new IllegalArgumentException("Illegal pixel");
    }
    this.r = pixel.getR();
    this.g = pixel.getG();
    this.b = pixel.getB();
  }

  @Override
  public Pixels getCopy() {
    int temp1 = r;
    int temp2 = g;
    int temp3 = b;
    return new Pixel(temp1, temp2, temp3);
  }

  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    if (object == this) {
      return true;
    }
    if (object instanceof Pixel) {
      Pixel p = (Pixel) object;
      return p.b == this.b && p.r == this.r && p.g == this.g;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(this.r) * 100
        + Integer.hashCode(this.g) * 10 + Integer.hashCode(this.b);
  }
}
