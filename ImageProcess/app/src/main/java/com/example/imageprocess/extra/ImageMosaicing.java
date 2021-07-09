package com.example.imageprocess.extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.example.imageprocess.pixels.Pixel;
import com.example.imageprocess.pixels.Pixels;

/**
 * A simple implementation of the Mosaicing interface that helps to mosaicing the image.
 */
public class ImageMosaicing implements Mosaicing {

  @Override
  public void apply(Pixels[][] valueOfrgb) {
    apply(valueOfrgb, 1000);
  }

  @Override
  public void apply(Pixels[][] pixels, int numberOfSeeds) {
    if (numberOfSeeds < 0) {
      throw new IllegalArgumentException("The number of seeds cannot be negative!");
    }

    Position2D[] positions = new Position2D[numberOfSeeds];
    ArrayList<ArrayList<Pixels>> seedPixels = new ArrayList<>();
    ArrayList<ArrayList<Position2D>> seedPixelsPosition = new ArrayList<>();
    Pixels[][] copy = pixels.clone();

    generateSeeds(numberOfSeeds, pixels, positions);

    for (int i = 0; i < numberOfSeeds; i++) {
      seedPixels.add(new ArrayList<>());
      seedPixelsPosition.add(new ArrayList<>());
    }

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        addToClosest(positions, seedPixels, seedPixelsPosition, i, j, pixels[i][j]);
      }
    }

    for (int i = 0; i < numberOfSeeds; i++) {
      if (seedPixels.get(i).size() != 0) {
        Pixels p = getAverage(seedPixels.get(i));

        for (int j = 0; j < seedPixelsPosition.get(i).size(); j++) {
          Position2D position = seedPixelsPosition.get(i).get(j);
          copy[position.x][position.y].setRGB(p);
        }
      }
    }

    // move the data from copy to original
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j].setRGB(copy[i][j]);
      }
    }
  }

  private void addToClosest(Position2D[] positions, ArrayList<ArrayList<Pixels>> seedPixels,
      ArrayList<ArrayList<Position2D>> seedPixelsPosition,
      int i, int j, Pixels pixel) {
    int minimumIndex = 0;
    double currentMinimumPath = 18888;

    for (int k = 0; k < positions.length; k++) {
      double path = Math.pow((j - positions[k].x), 2) + Math.pow((i - positions[k].y), 2);

      if (path < currentMinimumPath) {
        currentMinimumPath = path;
        minimumIndex = k;
      }
    }

    seedPixels.get(minimumIndex).add(pixel);
    seedPixelsPosition.get(minimumIndex).add(new Position2D(i, j));
  }

  private Pixels getAverage(List<Pixels> pixels) {
    int totalR = 0;
    int totalG = 0;
    int totalB = 0;
    int count = pixels.size();

    for (int i = 0; i < count; i++) {
      totalR = totalR + pixels.get(i).getR();
      totalG = totalG + pixels.get(i).getG();
      totalB = totalB + pixels.get(i).getB();
    }

    return new Pixel(totalR / count, totalG / count, totalB / count);
  }

  private int getNextLocation(int lowerBound, int upperBound) {
    Random r = new Random();
    return r.nextInt((upperBound - lowerBound) + 1) + lowerBound;
  }

  private void generateSeeds(int numberOfSeeds, Pixels[][] pixels, Position2D[] positions) {
    for (int i = 0; i < numberOfSeeds; i++) {
      int seedPosX = getNextLocation(0, pixels[0].length);
      int seedPosY = getNextLocation(0, pixels.length);

      positions[i] = new Position2D(seedPosX, seedPosY);
    }
  }

  private static class Position2D {

    private int x;
    private int y;

    Position2D(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
