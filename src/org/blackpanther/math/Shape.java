package org.blackpanther.math;

public interface Shape {

    public Shape transform(Transformation trans);

    public boolean contains(Point3D point);

}
