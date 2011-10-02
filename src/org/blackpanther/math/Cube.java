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
                new Point3D(+1f, -1f, +1f), //C
                new Point3D(+1f, -1f, -1f), //D
                new Point3D(-1f, +1f, -1f), //E
                new Point3D(-1f, +1f, +1f), //F
                new Point3D(+1f, +1f, +1f), //G
                new Point3D(+1f, +1f, -1f)  //H
        });
//        this(new Point3D[]{
//                new Point3D(-1f, -.5f, -.2f), //A
//                new Point3D(+0f, -1f, +.75f), //B
//                new Point3D(+1f, -.5f, -.2f), //C
//                new Point3D(+0f, +0f, -1f), //D
//                new Point3D(-1f, +.5f, +.2f), //E
//                new Point3D(+0f, +.25f, +1f), //F
//                new Point3D(+1f, +.5f, +.2f), //G
//                new Point3D(+0f, +1f, -.75f)  //H
//        });
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
        //just create a variable to manipulate matrix easily
        final float[][] m = trans.getMatrix();
        //future cube's apex
        final Point3D[] rotatedPoints = new Point3D[apex.length];
        //apply rotation to every apex
        for (int index = apex.length; index-- > 0; ) {
            Point3D p = apex[index];
            rotatedPoints[index] = new Point3D(
                    m[0][0] * p.getX() + m[0][1] * p.getY() + m[0][2] * p.getZ(),
                    m[1][0] * p.getX() + m[1][1] * p.getY() + m[1][2] * p.getZ(),
                    m[2][0] * p.getX() + m[2][1] * p.getY() + m[2][2] * p.getZ()
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

    private static final String[] FACES_TEXT = new String[]{
            "ABFE",
            "ABCD",
            "BCGF",
            "CDHG",
            "EHGF",
            "DAEH",
    };

    public static String faceText(int face) {
        return FACES_TEXT[face];
    }
}
