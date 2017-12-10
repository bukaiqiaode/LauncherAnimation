package com.home.mylauncher.MyUtils;

/**
 * Created by home on 2017/12/9.
 * References:
 *      http://blog.csdn.net/cxmscb/article/details/52485878
 *  Steps:
 *  1. Create the ViewPoint class
 *  2.              10:07, start to read to source code for ViewPath.
 *  3. (1 minute)   10:08, finished reading.
 *  4.              10:08, start to write the code for ViewPath.
 *  5. (10 minutes) 10:18, finished writing.
 *  6.              10:42, read the code for ViewPathEvaluator
 *  7. (50 minutes) 11:32, finished reading code for ViewPathEvaluator
 *  8.              11:35, start write code for ViewPathEvaluator
 *  9. (30 minutes) 12:15, finished writing code for ViewPathEvaluator
 *  10.             12:15, following the other code to complete
 *  11.(60 minutes) 13:15, finished, success ~~
 *  Comments:
 *  a. If you want to use some value-member in 'switch', you have to make it *final*
 *  b. The function 'setFebLoc' must be 'public void'
 */

public class ViewPoint {
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    float x, y;
    float x1, y1;
    float x2, y2;

    int operation;

    /**
     * Will be used when evaluating the path, need to be public.
     * @param x
     * @param y
     */
    public ViewPoint(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    private ViewPoint(float x, float y, int operation)
    {
        this.x = x;
        this.y = y;
        this.operation = operation;
    }

    private  ViewPoint(float x, float y, float x1, float y1, int operation)
    {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.operation = operation;
    }

    private ViewPoint(float x, float y, float x1, float y1, float x2, float y2, int operation)
    {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.operation = operation;
    }

    /**
     * Not drawing, move directly to (x, y)
     * @param x
     * @param y
     * @param operation
     * @return
     */
    public static ViewPoint moveTo(float x, float y, int operation)
    {
        return new ViewPoint(x, y, operation);
    }

    /**
     * Draw a line to (x, y)
     * @param x
     * @param y
     * @param operation
     * @return
     */
    public static ViewPoint lineTo(float x, float y, int operation)
    {
        return new ViewPoint(x, y, operation);
    }

    /**
     * Quad-to (x1, y1), controlled by (x, y)
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param operation
     * @return
     */
    public static ViewPoint quadTo(float x, float y, float x1, float y1, int operation)
    {
        return  new ViewPoint(x, y, x1, y1, operation);
    }

    /**
     * Cubic-to (x2, y2), controlled by (x,y) and (x1,y1)
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param operation
     * @return
     */
    public static ViewPoint cubicTo(float x, float y, float x1, float y1, float x2, float y2, int operation)
    {
        return  new ViewPoint(x, y, x1, y1, x2, y2, operation);
    }
}
