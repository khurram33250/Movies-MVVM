package com.example.hasham.movies_mvvm.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log

import com.squareup.picasso.Transformation

/**
 * Developed by hasham on 8/1/17.
 */

class CropTransformation : Transformation {

    private var mAspectRatio: Float = 0.toFloat()
    private var mLeft: Int = 0
    private var mTop: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mWidthRatio: Float = 0.toFloat()
    private var mHeightRatio: Float = 0.toFloat()
    private var mGravityHorizontal: GravityHorizontal? = GravityHorizontal.CENTER
    private var mGravityVertical: GravityVertical? = GravityVertical.CENTER

    enum class GravityHorizontal {
        LEFT,
        CENTER,
        RIGHT
    }

    enum class GravityVertical {
        TOP,
        CENTER,
        BOTTOM
    }

    /**
     * Crops to the given size and offset in pixels.
     * If either width or height is zero then the original image's dimension is used.
     *
     * @param left   offset in pixels from the left edge of the source image
     * @param top    offset in pixels from the top of the source image
     * @param width  in pixels
     * @param height in pixels
     */
    constructor(left: Int, top: Int, width: Int, height: Int) {
        mLeft = left
        mTop = top
        mWidth = width
        mHeight = height
    }

    /**
     * Crops to the given width and height (in pixels) using the given gravity.
     * If either width or height is zero then the original image's dimension is used.
     *
     * @param width             in pixels
     * @param height            in pixels
     * @param gravityHorizontal position of the cropped area within the larger source image
     * @param gravityVertical   position of the cropped area within the larger source image
     */
    @JvmOverloads constructor(width: Int, height: Int, gravityHorizontal: GravityHorizontal = GravityHorizontal.CENTER,
                              gravityVertical: GravityVertical = GravityVertical.CENTER) {
        mWidth = width
        mHeight = height
        mGravityHorizontal = gravityHorizontal
        mGravityVertical = gravityVertical
    }

    /**
     * Crops to a ratio of the source image's width/height.
     *
     *
     * e.g. (0.5, 0.5, LEFT, TOP) will crop a quarter-sized box from the top left of the original.
     *
     *
     * If widthRatio or heightRatio are zero then 100% of the original image's dimension will be
     * used.
     *
     * @param widthRatio        width of the target image relative to the width of the source image; 1 =
     * 100%
     * @param heightRatio       height of the target image relative to the height of the source image; 1 =
     * 100%
     * @param gravityHorizontal position of the cropped area within the larger source image
     * @param gravityVertical   position of the cropped area within the larger source image
     */
    @JvmOverloads constructor(widthRatio: Float, heightRatio: Float,
                              gravityHorizontal: GravityHorizontal = GravityHorizontal.CENTER, gravityVertical: GravityVertical = GravityVertical.CENTER) {
        mWidthRatio = widthRatio
        mHeightRatio = heightRatio
        mGravityHorizontal = gravityHorizontal
        mGravityVertical = gravityVertical
    }

    /**
     * Crops to the desired aspectRatio driven by either width OR height in pixels.
     * If one of width/height is greater than zero the other is calculated
     * If width and height are both zero then the largest area matching the aspectRatio is returned
     * If both width and height are specified then the aspectRatio is ignored
     *
     *
     * If aspectRatio is 0 then the result will be the same as calling the version without
     * aspectRatio.
     *
     * @param width             in pixels, one of width/height should be zero
     * @param height            in pixels, one of width/height should be zero
     * @param aspectRatio       width/height: greater than 1 is landscape, less than 1 is portrait, 1 is
     * square
     * @param gravityHorizontal position of the cropped area within the larger source image
     * @param gravityVertical   position of the cropped area within the larger source image
     */
    constructor(width: Int, height: Int, aspectRatio: Float,
                gravityHorizontal: GravityHorizontal, gravityVertical: GravityVertical) {
        mWidth = width
        mHeight = height
        mAspectRatio = aspectRatio
        mGravityHorizontal = gravityHorizontal
        mGravityVertical = gravityVertical
    }

    /**
     * Crops to the desired aspectRatio driven by either width OR height as a ratio to the original
     * image's dimension.
     * If one of width/height is greater than zero the other is calculated
     * If width and height are both zero then the largest area matching the aspectRatio is returned
     * If both width and height are specified then the aspectRatio is ignored
     *
     *
     * If aspectRatio is 0 then the result will be the same as calling the version without
     * aspectRatio.
     *
     *
     * e.g. to get an image with a width of 50% of the source image's width and cropped to 16:9 from
     * the center/center:
     * CropTransformation(0.5, (float)0, (float)16/9, CENTER, CENTER);
     *
     * @param widthRatio        width of the target image relative to the width of the source image; 1 =
     * 100%
     * @param heightRatio       height of the target image relative to the height of the source image; 1 =
     * 100%
     * @param aspectRatio       width/height: greater than 1 is landscape, less than 1 is portrait, 1 is
     * square
     * @param gravityHorizontal position of the cropped area within the larger source image
     * @param gravityVertical   position of the cropped area within the larger source image
     */
    constructor(widthRatio: Float, heightRatio: Float, aspectRatio: Float,
                gravityHorizontal: GravityHorizontal, gravityVertical: GravityVertical) {
        mWidthRatio = widthRatio
        mHeightRatio = heightRatio
        mAspectRatio = aspectRatio
        mGravityHorizontal = gravityHorizontal
        mGravityVertical = gravityVertical
    }

    /**
     * Crops to the largest image that will fit the given aspectRatio.
     * This will effectively chop off either the top/bottom or left/right of the source image.
     *
     * @param aspectRatio       width/height: greater than 1 is landscape, less than 1 is portrait, 1 is
     * square
     * @param gravityHorizontal position of the cropped area within the larger source image
     * @param gravityVertical   position of the cropped area within the larger source image
     */
    constructor(aspectRatio: Float, gravityHorizontal: GravityHorizontal,
                gravityVertical: GravityVertical) {
        mAspectRatio = aspectRatio
        mGravityHorizontal = gravityHorizontal
        mGravityVertical = gravityVertical
    }

    override fun transform(source: Bitmap): Bitmap {
        if (Log.isLoggable(TAG, Log.VERBOSE)) Log.v(TAG, "transform(): called, " + key())

        if (mWidth == 0 && mWidthRatio != 0f) {
            mWidth = (source.width.toFloat() * mWidthRatio).toInt()
        }
        if (mHeight == 0 && mHeightRatio != 0f) {
            mHeight = (source.height.toFloat() * mHeightRatio).toInt()
        }

        if (mAspectRatio != 0f) {
            if (mWidth == 0 && mHeight == 0) {
                val sourceRatio = source.width.toFloat() / source.height.toFloat()

                if (Log.isLoggable(TAG, Log.VERBOSE)) {
                    Log.v(TAG,
                            "transform(): mAspectRatio: $mAspectRatio, sourceRatio: $sourceRatio")
                }

                if (sourceRatio > mAspectRatio) {
                    // source is wider than we want, restrict by height
                    mHeight = source.height
                } else {
                    // source is taller than we want, restrict by width
                    mWidth = source.width
                }
            }

            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "transform(): before setting other of h/w: mAspectRatio: " + mAspectRatio
                        + ", set one of width: " + mWidth + ", height: " + mHeight)
            }

            if (mWidth != 0) {
                mHeight = (mWidth.toFloat() / mAspectRatio).toInt()
            } else {
                if (mHeight != 0) {
                    mWidth = (mHeight.toFloat() * mAspectRatio).toInt()
                }
            }

            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG,
                        "transform(): mAspectRatio: " + mAspectRatio + ", set width: " + mWidth + ", height: "
                                + mHeight)
            }
        }

        if (mWidth == 0) {
            mWidth = source.width
        }

        if (mHeight == 0) {
            mHeight = source.height
        }

        if (mGravityHorizontal != null) {
            mLeft = getLeft(source)
        }
        if (mGravityVertical != null) {
            mTop = getTop(source)
        }

        val sourceRect = Rect(mLeft, mTop, mLeft + mWidth, mTop + mHeight)
        val targetRect = Rect(0, 0, mWidth, mHeight)

        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG,
                    "transform(): created sourceRect with mLeft: " + mLeft + ", mTop: " + mTop + ", right: "
                            + (mLeft + mWidth) + ", bottom: " + (mTop + mHeight))
        }
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "transform(): created targetRect with width: $mWidth, height: $mHeight")
        }

        val bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "transform(): copying from source with width: " + source.width + ", height: "
                    + source.height)
        }
        canvas.drawBitmap(source, sourceRect, targetRect, null)

        source.recycle()

        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "transform(): returning bitmap with width: " + bitmap.width + ", height: "
                    + bitmap.height)
        }

        return bitmap
    }

    override fun key(): String {
        return ("CropTransformation(width=" + mWidth + ", height=" + mHeight + ", mWidthRatio="
                + mWidthRatio + ", mHeightRatio=" + mHeightRatio + ", mAspectRatio=" + mAspectRatio
                + ", gravityHorizontal=" + mGravityHorizontal + ", mGravityVertical=" + mGravityVertical
                + ")")
    }

    private fun getTop(source: Bitmap): Int {
        when (mGravityVertical) {
            CropTransformation.GravityVertical.TOP -> return 0
            CropTransformation.GravityVertical.CENTER -> return (source.height - mHeight) / 2
            CropTransformation.GravityVertical.BOTTOM -> return source.height - mHeight
            else -> return 0
        }
    }

    private fun getLeft(source: Bitmap): Int {
        when (mGravityHorizontal) {
            CropTransformation.GravityHorizontal.LEFT -> return 0
            CropTransformation.GravityHorizontal.CENTER -> return (source.width - mWidth) / 2
            CropTransformation.GravityHorizontal.RIGHT -> return source.width - mWidth
            else -> return 0
        }
    }

    companion object {
        private val TAG = "PicassoTransformation"
    }
}
/**
 * Crops to the given width and height (in pixels), defaulting gravity to CENTER/CENTER.
 * If either width or height is zero then the original image's dimension is used.
 *
 * @param width  in pixels
 * @param height in pixels
 */
/**
 * Crops to a ratio of the source image's width/height, defaulting to CENTER/CENTER gravity.
 *
 *
 * e.g. (0.5, 0.5) will crop a quarter-sized box from the middle of the original.
 *
 *
 * If widthRatio or heightRatio are zero then 100% of the original image's dimension will be
 * used.
 *
 * @param widthRatio  width of the target image relative to the width of the source image; 1 =
 * 100%
 * @param heightRatio height of the target image relative to the height of the source image; 1 =
 * 100%
 */
