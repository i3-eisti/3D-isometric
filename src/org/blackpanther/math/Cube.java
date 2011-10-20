package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public final class Cube extends Box {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Cube.class.getCanonicalName());

    private Box encapsulated;

    /**
     * Constructor with default cube with BCGF only visible face
     */
    public Cube() {
        super();
    }

    /**
     * Full parametrized constructor
     *
     * @param apex - array of apex's coordinate
     */
    public Cube(float[][] apex) {
        super(new Point3D[]{
                new Point3D(apex[0][1], apex[0][2], apex[0][3]),
                new Point3D(apex[1][1], apex[1][2], apex[1][3]),
                new Point3D(apex[2][1], apex[2][2], apex[2][3]),
                new Point3D(apex[3][1], apex[3][2], apex[3][3]),
                new Point3D(apex[4][1], apex[4][2], apex[4][3]),
                new Point3D(apex[5][1], apex[5][2], apex[5][3]),
                new Point3D(apex[6][1], apex[6][2], apex[6][3]),
                new Point3D(apex[7][1], apex[7][2], apex[7][3])
        });
    }

    public Cube(float side) {
        super(side, side, side);
    }

    @Override
    protected Cube clone() {
        return (Cube) super.clone();
    }
}
