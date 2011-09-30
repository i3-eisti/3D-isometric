package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Cube {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Cube.class.getCanonicalName());

    //top
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;
    public static final int D = 3;
    public static final int E = 4;
    public static final int F = 5;
    public static final int G = 6;
    public static final int H = 7;

    //face
    public static final int ABFE = 0;
    public static final int ABCD = 1;
    public static final int BCGF = 2;
    public static final int CDHG = 3;
    public static final int EHGF = 4;
    public static final int DAEH = 5;

    private final Point3D[] apex;

    public Cube() {
        this(new Point3D[]{
                new Point3D(-1f, -1f, -1f), //A
                new Point3D(-1f, -1f, +1f), //B
                new Point3D(-1f, +1f, +1f), //C
                new Point3D(-1f, +1f, -1f), //D
                new Point3D(+1f, -1f, -1f), //E
                new Point3D(+1f, -1f, +1f), //F
                new Point3D(+1f, +1f, +1f), //G
                new Point3D(+1f, +1f, -1f)  //H
        });
    }

    public Cube(float[][] apex) {
        this(new Point3D[]{
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

    public Cube(Point3D[] apex) {
        this.apex = apex;
    }

    public final Point3D[] getPoints() {
        return apex;
    }

    public final Cube transform(Transformation trans) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
