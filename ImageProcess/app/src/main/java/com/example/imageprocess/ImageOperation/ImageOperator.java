package com.example.imageprocess.ImageOperation;

import android.graphics.Bitmap;

public interface ImageOperator {

    void blur();

    void sharpening();

    void sepia();

    void greyScale();

    void mosaic(int seeds);


    void setPicture(Bitmap image);

    Bitmap getPicture();

}
