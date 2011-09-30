package org.blackpanther.model;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Point3D {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Point3D.class.getCanonicalName());

    private int[] coordinate;

    @SuppressWarnings("unchecked")
    public Point3D(
            final int x,
            final int y,
            final int z
    ) {
        coordinate = new int[3];
        coordinate[0] = x;
        coordinate[1] = y;
        coordinate[2] = z;
    }

    public final int getX(){
        return coordinate[0];
    }

    public final int getY(){
        return coordinate[1];
    }

    public final int getZ(){
        return coordinate[2];
    }
}
