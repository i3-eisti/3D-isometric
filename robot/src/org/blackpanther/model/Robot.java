package org.blackpanther.model;

import org.blackpanther.math.*;

/**
 * @author MACHIZAUD Andréa
 * @version 18/10/11
 */
public final class Robot implements ComplexShape {

    private final Sphere head;
    private final Box body;
    private final Arm[] arms;
    private final Leg[] legs;

    public Robot() {
        this.head = new Sphere();
        this.body = new Box();
        this.arms = new Arm[2];
        this.legs = new Leg[2];
    }

    @Override
    public Shape transform(Transformation trans) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean contains(Point3D point) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
