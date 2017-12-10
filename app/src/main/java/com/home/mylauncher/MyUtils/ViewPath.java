package com.home.mylauncher.MyUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by home on 2017/12/9.
 */

public class ViewPath {
    public static final int MOVETO = 0;
    public static final int LINETO = 1;
    public static final int QUADTO = 2;
    public static final int CUBICTO = 3;

    private ArrayList<ViewPoint> mPoints = null;

    public ViewPath()
    {
        this.mPoints = new ArrayList<>();
    }

    public void moveTo(float x, float y)
    {
        this.mPoints.add(ViewPoint.moveTo(x, y, MOVETO));
    }

    public void lineTo(float x, float y)
    {
        this.mPoints.add(ViewPoint.lineTo(x, y, LINETO));
    }

    public void quadTo(float x, float y, float x1, float y1)
    {
        this.mPoints.add(ViewPoint.quadTo(x, y , x1, y1, QUADTO));
    }

    public void cubicTo(float x, float y, float x1, float y1, float x2, float y2)
    {
        this.mPoints.add(ViewPoint.cubicTo(x, y, x1, y1, x2, y2, CUBICTO));
    }

    public Collection<ViewPoint> getPoints()
    {
        return this.mPoints;
    }
}
