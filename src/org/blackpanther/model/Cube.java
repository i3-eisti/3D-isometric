package org.blackpanther.model;

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

    private final Point3D[] matrix;

    @SuppressWarnings("unchecked")
    public Cube() {
        this.matrix = new Point3D[]{
                new Point3D(-1, -1, -1), //A
                new Point3D(-1, -1, +1), //B
                new Point3D(-1, +1, +1), //C
                new Point3D(-1, +1, -1), //D
                new Point3D(+1, -1, -1), //E
                new Point3D(+1, -1, +1), //F
                new Point3D(+1, +1, +1), //G
                new Point3D(+1, +1, -1)  //H
        };
    }

    public final Point3D[] getPoints() {
        return matrix;
    }
}
