package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 30/09/11
 */
public class Transformation {

    private final float[][] matrix;

    public Transformation(float[][] matrix) {
        this.matrix = matrix;
    }

    public Transformation(
            float ax, float ay, float az,
            float bx, float by, float bz,
            float cx, float cy, float cz
    ) {
        matrix = new float[][]{
                {ax, ay, az},
                {bx, by, bz},
                {cx, cy, cz},
        };
    }

    public final float[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        final float[][] m = getMatrix();
        return String.format(
                "[% .2f % .2f % .2f]%n" +
                "[% .2f % .2f % .2f]%n" +
                "[% .2f % .2f % .2f]%n",
                m[0][0], m[0][1], m[0][2],
                m[1][0], m[1][1], m[1][2],
                m[2][0], m[2][1], m[2][2]
        );
    }
}
