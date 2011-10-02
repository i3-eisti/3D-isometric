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
        this(x,y,z,1);
    }

    Point3D(
            final float x,
            final float y,
            final float z,
            final float t
    ) {
        coordinate = new float[4];
        coordinate[0] = x;
        coordinate[1] = y;
        coordinate[2] = z;
        coordinate[3] = t;
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

    final float getT() {
        return coordinate[3];
    }

    @Override
    public String toString() {
        return String.format(
                "{% .2f,% .2f,% .2f}",
                getX(), getY(), getZ()
        );
    }
}
