package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Cube {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Cube.class.getCanonicalName());

    //apex
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

    /**
     * Cube's apex.
     * We don't record cube side length, this is left to the render.
     */
    private final Point3D[] apex;

    /**
     * Constructor with default cube with BCGF only visible face
     */
    public Cube() {
        this(new Point3D[]{
                new Point3D(-1f, -1f, -1f), //A
                new Point3D(-1f, -1f, +1f), //B
                new Point3D(+1f, -1f, +1f), //C
                new Point3D(+1f, -1f, -1f), //D
                new Point3D(-1f, +1f, -1f), //E
                new Point3D(-1f, +1f, +1f), //F
                new Point3D(+1f, +1f, +1f), //G
                new Point3D(+1f, +1f, -1f)  //H
        });
    }

    /**
     * Full parameterized constructor
     * @param apex - array of apex's coordinate
     */
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

    /**
     * Full parameterized constructor
     * @param apex -- array of apex's coordinate
     */
    public Cube(Point3D[] apex) {
        this.apex = apex;
    }

    /**
     * Getting all cube's apex
     * @return cube's apex
     */
    public final Point3D[] getPoints() {
        return apex;
    }

    /**
     * Apply a transformation to this cube
     * @param trans - transformation to apply
     * @return transformed cube
     * @see Transformation
     */
    public final Cube transform(Transformation trans) {
        //just create a variable to manipulate matrix easily
        final float[][] m = trans.getMatrix();
        //future cube's apex
        final Point3D[] rotatedPoints = new Point3D[apex.length];
        //apply rotation to every apex
        for (int index = apex.length; index-- > 0; ) {
            Point3D p = apex[index];
            rotatedPoints[index] = new Point3D(
                    m[0][0] * p.getX() + m[0][1] * p.getY() + m[0][2] * p.getZ() + m[0][3] * p.getT(),
                    m[1][0] * p.getX() + m[1][1] * p.getY() + m[1][2] * p.getZ() + m[1][3] * p.getT(),
                    m[2][0] * p.getX() + m[2][1] * p.getY() + m[2][2] * p.getZ() + m[2][3] * p.getT(),
                    m[3][0] * p.getX() + m[3][1] * p.getY() + m[3][2] * p.getZ() + m[3][3] * p.getT()
            );
        }
        return new Cube(rotatedPoints);
    }

    @Override
    public String toString() {
        //muahaha nobody will ever want to read this !
        return String.format(
                "Cube{%n" +
                        "\tA %s%n" +
                        "\tB %s%n" +
                        "\tC %s%n" +
                        "\tD %s%n" +
                        "\tE %s%n" +
                        "\tF %s%n" +
                        "\tG %s%n" +
                        "\tH %s%n" +
                        "}%n", (Object[]) apex);
    }

    /**
     * Cache of string representation of face index
     */
    private static final String[] FACES_TEXT = new String[]{
            "ABFE",
            "ABCD",
            "BCGF",
            "CDHG",
            "EHGF",
            "DAEH",
    };

    /**
     * Helper to display face's name
     * @param face - face's index
     * @return face's name
     */
    public static String faceText(int face) {
        return FACES_TEXT[face];
    }
}
