package org.blackpanther.model;

import org.blackpanther.math.*;

/**
 * @author MACHIZAUD Andréa
 * @version 19/10/11
 */
public final class Arm implements ComplexShape {

    private final Box midArm;
    private final Box endArm;

    public Arm() {
        midArm = new Box();
        endArm = new Box();
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
