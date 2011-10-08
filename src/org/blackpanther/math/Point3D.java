package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Point3D {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Point3D.class.getCanonicalName());

    /**
     * Point's coordinate
     */
    private float[] coordinate;

    public Point3D(
            final float x,
            final float y,
            final float z
    ) {
        this(x, y, z, 1);
    }

    /**
     * Internal constructor to manipulate virtual fourth component
     */
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

    public final float getX() {
        return coordinate[0];
    }

    public final float getY() {
        return coordinate[1];
    }

    public final float getZ() {
        return coordinate[2];
    }

    /**
     * Virtual fourth component.
     * Present to ease some computations.
     */
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

    public Point3D moveTo(
            final float newX,
            final float newY,
            final float newZ
    ) {
        return new Point3D(
                newX,
                newY,
                newZ
        );
    }

    /**
     * Point with incomplete data, created for example to change from screen point to math point
     */
    public static class Incomplete extends Point3D {

        public Incomplete(final float x, final float y) {
            super(x, y, Float.NaN);
        }

        @Override
        public Point3D moveTo(
                final float newX,
                final float newY,
                final float newZ
        ) {
            return new Incomplete(
                    newX,
                    newY
            );
        }

        @Override
        public String toString() {
            return String.format(
                    "{% .2f,% .2f, ???}",
                    getX(), getY()
            );
        }
    }
}
