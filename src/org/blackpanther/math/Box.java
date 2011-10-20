package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 18/10/11
 */
public class Box implements Shape, Cloneable, BoxConstants  {

    private static final float DEFAULT_WIDTH = 1f;
    private static final float DEFAULT_HEIGHT = 1f;
    private static final float DEFAULT_DEPTH = 1f;

    /**
     * Cube's vertices.
     * We don't record cube side length, this is left to the render.
     */
    protected final Point3D[] vertices;

    private final float width;
    private final float height;
    private final float depth;

    public Box(
            final float width,
            final float height,
            final float depth)
    {
        this.width = width;
        this.height = height;
        this.depth = depth;
        final float mw = width / 2f;
        final float mh = height / 2f;
        final float md = depth / 2f;
        this.vertices = new Point3D[]{
                new Point3D(-mw, -mh, -md), //A
                new Point3D(-mw, -mh, +md), //B
                new Point3D(+mw, -mh, +md), //C
                new Point3D(+mw, -mh, -md), //D
                new Point3D(-mw, +mh, -md), //E
                new Point3D(-mw, +mh, +md), //F
                new Point3D(+mw, +mh, +md), //G
                new Point3D(+mw, +mh, -md)  //H
        };
    }

    /**
     * Constructor with default cube with BCGF only visible face
     */
    public Box() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_DEPTH);
    }

    /**
     * Full parametrized constructor
     * @param vertices - array of vertices's coordinate
     */
    protected Box(float[][] vertices) {
        this(new Point3D[]{
                new Point3D(vertices[0][1], vertices[0][2], vertices[0][3]),
                new Point3D(vertices[1][1], vertices[1][2], vertices[1][3]),
                new Point3D(vertices[2][1], vertices[2][2], vertices[2][3]),
                new Point3D(vertices[3][1], vertices[3][2], vertices[3][3]),
                new Point3D(vertices[4][1], vertices[4][2], vertices[4][3]),
                new Point3D(vertices[5][1], vertices[5][2], vertices[5][3]),
                new Point3D(vertices[6][1], vertices[6][2], vertices[6][3]),
                new Point3D(vertices[7][1], vertices[7][2], vertices[7][3])
        });
    }

    /**
     * Full parametrized constructor
     * @param vertices -- array of vertices's coordinate
     */
    protected Box(Point3D[] vertices) {
        this.vertices = vertices;
        this.width = new Vector3D(vertices[B],vertices[C]).euclidianNorm();
        this.height = new Vector3D(vertices[B],vertices[F]).euclidianNorm();
        this.depth = new Vector3D(vertices[A],vertices[B]).euclidianNorm();
    }

    /**
     * Getting all cube's vertices
     * @return cube's vertices
     */
    public final Point3D[] getPoints() {
        return vertices;
    }

    public final float getWidth() {
        return width;
    }

    public final float getHeight() {
        return height;
    }

    public final float getDepth() {
        return depth;
    }

    /**
     * Apply a transformation to this cube
     * @param trans - transformation to apply
     * @return transformed rectangle
     * @see Transformation
     */
    public Box transform(Transformation trans) {
        //just create a variable to manipulate matrix easily
        final float[][] m = trans.getMatrix();
        //future cube's vertices
        final Point3D[] rotatedPoints = new Point3D[vertices.length];
        //apply rotation to every vertices
        for (int index = vertices.length; index-- > 0; ) {
            Point3D p = vertices[index];
            rotatedPoints[index] = new Point3D(
                    m[0][0] * p.getX() + m[0][1] * p.getY() + m[0][2] * p.getZ() + m[0][3] * p.getT(),
                    m[1][0] * p.getX() + m[1][1] * p.getY() + m[1][2] * p.getZ() + m[1][3] * p.getT(),
                    m[2][0] * p.getX() + m[2][1] * p.getY() + m[2][2] * p.getZ() + m[2][3] * p.getT(),
                    m[3][0] * p.getX() + m[3][1] * p.getY() + m[3][2] * p.getZ() + m[3][3] * p.getT()
            );
        }
        return new Box(rotatedPoints);
    }

    @Override
    public boolean contains(Point3D point) {
        final float x = point.getX();
        final float y = point.getY();
        final float z = point.getZ();
        return (-width <= x || x <= width) &&
               (-height <= y || y <= height) &&
               (-depth <= z || z <= depth);
    }

    @Override
    public String toString() {
        //muahaha nobody will ever want to read this !
        return String.format(
                "Box{%n" +
                        "\tA %s%n" +
                        "\tB %s%n" +
                        "\tC %s%n" +
                        "\tD %s%n" +
                        "\tE %s%n" +
                        "\tF %s%n" +
                        "\tG %s%n" +
                        "\tH %s%n" +
                        "}%n", (Object[]) vertices);
    }

    @Override
    protected Box clone() {
        try {
            return (Cube) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new Error("Exception thrown whilst class implements Cloneable");
        }
    }

}
