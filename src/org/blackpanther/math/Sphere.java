package org.blackpanther.math;

import static java.lang.Math.sqrt;

public class Sphere implements Shape {

    public Point3D getPositiveSurfacePoint(Point3D incompletePoint) {
        final float zComponent =
                (float) sqrt(
                        1f -
                                incompletePoint.getX() * incompletePoint.getX() -
                                incompletePoint.getY() * incompletePoint.getY()
                );
        return new Point3D(
                incompletePoint.getX(),
                incompletePoint.getY(),
                zComponent
        );
    }

    public Point3D getNegativeSurfacePoint(Point3D incompletePoint) {
        final float zComponent =
                (float) -sqrt(
                        1f -
                                incompletePoint.getX() * incompletePoint.getX() -
                                incompletePoint.getY() * incompletePoint.getY()
                );
        return new Point3D(
                incompletePoint.getX(),
                incompletePoint.getY(),
                zComponent
        );
    }

    @Override
    public Shape transform(Transformation trans) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean contains(Point3D point) {
        return sqrt(point.getX() * point.getX() + point.getY() * point.getY() + point.getZ() * point.getZ()) <= 1f;
    }

    @Override
    public String toString() {
        return "Sphere";
    }

}
