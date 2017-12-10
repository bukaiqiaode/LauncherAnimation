package com.home.mylauncher.MyUtils;

import android.animation.TypeEvaluator;


/**
 * Created by home on 2017/12/9.
 */

public class ViewPathEvaluator implements TypeEvaluator<ViewPoint> {
    /**
     * Returns the next point in the path
     * @param t progress of the operation.
     * @param startValue Point that contains the start-point fro the this movement
     * @param endValue The stop-point of this movement
     * @return
     */
    @Override
    public ViewPoint evaluate(float t, ViewPoint startValue, ViewPoint endValue) {
        float x = 0f;
        float y = 0f;
        float startX = 0f;
        float startY = 0f;

        //Decide (startX, startY), according to the type of startValue
        switch (startValue.operation)
        {
            case ViewPath.QUADTO:
                startX = startValue.x1;
                startY = startValue.y1;
                break;
            case ViewPath.CUBICTO:
                startX = startValue.x2;
                startY = startValue.y2;
                break;
            case ViewPath.LINETO:
            case ViewPath.MOVETO:
            default:
                //Line-to & Move-to & other share the same logic
                startX = startValue.x;
                startY = startValue.y;
                break;
        }
        //Calculate according to the type of endValue
        float oneMinusT = 1 - t;
        switch (endValue.operation)
        {
            case ViewPath.LINETO:
                /*
                Formula:
                    B(t) = P0 + (P1 - P0) * t = (1-t) * P0 + t * P1
                Here:
                    P0 = startX | startY,
                    P1 = endValue.x | endValue.y
                */
                x = startX + t * (endValue.x - startX);
                y = startY + t * (endValue.y - startY);
                break;
            case ViewPath.QUADTO:
                /*
                Formula:
                    B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2
                Here:
                    P0 = startX | startY
                    P1 = endValue.x | endValue.y
                    P2 = endValue.x1 | endValue.y1
                 */
                x = oneMinusT * oneMinusT * startX +
                        2 * t * oneMinusT * endValue.x +
                        t * t * endValue.x1;

                y = oneMinusT * oneMinusT * startY +
                        2 * t * oneMinusT * endValue.y +
                        t * t * endValue.y1;
                break;
            case ViewPath.CUBICTO:
                /*
                Formula:
                    B(t) = (1 - t)^3 * P0 + 3t * (1 - t)^2 * P1 + 3 * t^2 * (1 - t) * P2 + t^3 * P3
                Here:
                    P0 = startX | startY
                    P1 = endValue.x | endValue.y
                    P2 = endValue.x1 | endValue.y1
                    P3 = endValue.x2 | endValue.y2
                 */
                x = oneMinusT * oneMinusT * oneMinusT * startX +
                        3 * t * oneMinusT * oneMinusT * endValue.x +
                        3 * t * t * oneMinusT * endValue.x1 +
                        t * t * t * endValue.x2;

                y = oneMinusT * oneMinusT * oneMinusT * startY +
                        3 * t * oneMinusT * oneMinusT * endValue.y +
                        3 * t * t * oneMinusT * endValue.y1 +
                        t * t * t * endValue.y2;
                break;
            default:
                //Move_to & other share the same logic
                x = endValue.x;
                y = endValue.y;
                break;
        }

        return new ViewPoint(x, y);
    }
}
