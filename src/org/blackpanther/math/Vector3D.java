package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Vector3D {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Vector3D.class.getCanonicalName());

    private final float[] chunks;

    public Vector3D(
            final float x,
            final float y,
            final float z
    ) {
        chunks = new float[]{x,y,z};
    }

    public Vector3D(Point3D origin, Point3D destination) {
        this(
            destination.getX() - origin.getX(),
            destination.getY() - origin.getY(),
            destination.getZ() - origin.getZ()
        );
    }

    public final float getX(){
        return chunks[0];
    }

    public final float getY(){
        return chunks[1];
    }

    public final float getZ(){
        return chunks[2];
    }

    public final Vector3D normal(Vector3D v) {
        final Vector3D u = this;
        return new Vector3D(
                u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX()
        );
    }
}
