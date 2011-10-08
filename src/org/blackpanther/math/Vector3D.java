package org.blackpanther.math;

import static java.lang.Math.*;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Vector3D {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Vector3D.class.getCanonicalName());

    //internal vector's components */
    protected final float[] chunks;

    /**
     * Constructor with 3D spaced parameter
     *
     * @param x - abscissa component
     * @param y - ordinate component
     * @param z - height   component
     */
    public Vector3D(
            final float x,
            final float y,
            final float z
    ) {
        chunks = new float[]{x, y, z};
    }

    /**
     * Constructor with 3D spaced parameter
     *
     * @param point - point
     */
    public Vector3D(final Point3D point)
    {
        this(point.getX(),point.getY(),point.getZ());
    }

    /**
     * Constructor with two points
     *
     * @param origin      - vector's origin
     * @param destination - vector's destination
     */
    public Vector3D(Point3D origin, Point3D destination) {
        this(
                destination.getX() - origin.getX(),
                destination.getY() - origin.getY(),
                destination.getZ() - origin.getZ()
        );
    }

    /**
     * Get abscissa component
     */
    public final float getX() {
        return chunks[0];
    }

    /**
     * Get ordinate component
     */
    public final float getY() {
        return chunks[1];
    }

    /**
     * Get height component
     */
    public final float getZ() {
        return chunks[2];
    }

    /**
     * Get a normalized version of this vector
     */
    public Vector3D normalize() {
        return new Normalized(this);
    }

    /**
     * Compute the euclidian norm of this vector
     *
     * @return euclidian's norm
     */
    public float euclidianNorm() {
        final Vector3D u = this;
        return (float) sqrt((double) (
                u.getX() * u.getX() +
                        u.getY() * u.getY() +
                        u.getZ() * u.getZ()
        ));
    }

    /**
     * Compute the oriented angle between this vector and given one
     *
     * @param rawV - vector to compute angle with
     * @return oriented angle between this vector and the given one  [-PI;PI]
     */
    public final double angle(Vector3D rawV) {
        final Vector3D u = this.normalize();
        final Vector3D v = rawV.normalize();
        final double top =
                u.getX() * v.getX() +
                        u.getY() * v.getY() +
                        u.getZ() * v.getZ();
        final double determinant =
                u.getY() * v.getZ() - u.getZ() * v.getY() +
                        u.getZ() * v.getX() - u.getX() * v.getZ() +
                        u.getX() * v.getY() - u.getY() * v.getX();
        // u . v / ( ||u|| * ||v|| ) -> get rid of ( ||u||* ||v|| ) because we manipulated normalized vector
        final double cosa = top;
        // u ^ v / ( ||u|| * ||v|| ) -> get rid of ( ||u||* ||v|| ) because we manipulated normalized vector
        final double sina = determinant;
        final double angle =
                acos(cosa);
        //FIXME Better handling where determinant == 0, is it even possible with this strategy ?
        final double sign = sina > 0 ? 1.0 : -1.0;
        return sign * angle;
    }

    /**
     * Return the normal vector made of this vector and the given one
     *
     * @param v - vector to compute normal vector with
     * @return normal vector made of this vector and the given one
     */
    public final Vector3D normal(Vector3D v) {
        final Vector3D u = this;
        return new Vector3D(
                u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX()
        );
    }

    /**
     * Compute the dot product between this vector and the given one
     *
     * @param v - vector to compute dot product with
     * @return dot product compute from this vector and the given one
     */
    public float dot(Vector3D v) {
        final Vector3D u = this;
        return u.euclidianNorm() * v.euclidianNorm() * (float) cos(u.angle(v));
    }

    @Override
    public String toString() {
        return String.format(
                "{%+.2f,%+.2f,%+.2f}",
                getX(), getY(), getZ()
        );
    }

    /**
     * Vector that is forced to be normalized,
     * can enable some optimizations on some computations
     */
    public static final class Normalized extends Vector3D {

        /**
         * Fully qualified constructor.
         * Computations are made to ensure that resulting vector is normalized.
         *
         * @param x - abscissa component
         * @param y - ordinate component
         * @param z - height   component
         */
        public Normalized(
                final float x,
                final float y,
                final float z) {
            super(x, y, z);
            //can't preprocess a 'super'
            final float norm = (float) sqrt((double) (
                    x * x +
                            y * y +
                            z * z
            ));

            //am i wrong to test equality on a floating number ?
            if (norm != 1f) {
                this.chunks[0] = x / norm;
                this.chunks[1] = y / norm;
                this.chunks[2] = z / norm;
            }
        }

        /**
         * Build a normalized vector from another one
         */
        public Normalized(final Vector3D vector3D) {
            this(vector3D.getX(), vector3D.getY(), vector3D.getZ());
        }

        /** {@inheritDoc} */
        @Override
        public final Vector3D normalize() {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final float euclidianNorm() {
            return 1f;
        }

        /** {@inheritDoc} */
        @Override
        public float dot(Vector3D v) {
            final Vector3D u = this;
            return (float) cos(u.angle(v));
        }
    }
}
