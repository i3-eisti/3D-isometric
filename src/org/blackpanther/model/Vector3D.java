package org.blackpanther.model;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Vector3D {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Vector3D.class.getCanonicalName());

    private final int[] chunks;

    public Vector3D(
            final int x,
            final int y,
            final int z
    ) {
        chunks = new int[]{x,y,z};
    }

    public Vector3D(Point3D origin, Point3D destination) {
        this(
            destination.getX() - origin.getX(),
            destination.getY() - origin.getY(),
            destination.getZ() - origin.getZ()
        );
    }

    public final int getX(){
        return chunks[0];
    }

    public final int getY(){
        return chunks[1];
    }

    public final int getZ(){
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
