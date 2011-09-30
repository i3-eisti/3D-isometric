package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Point3D {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Point3D.class.getCanonicalName());

    private float[] coordinate;

    public Point3D(
            final float x,
            final float y,
            final float z
    ) {
        coordinate = new float[3];
        coordinate[0] = x;
        coordinate[1] = y;
        coordinate[2] = z;
    }

    public final float getX(){
        return coordinate[0];
    }

    public final float getY(){
        return coordinate[1];
    }

    public final float getZ(){
        return coordinate[2];
    }
}
