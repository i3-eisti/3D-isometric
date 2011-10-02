package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 30/09/11
 */
public class RawTransformation
        implements Transformation {

    private final float[][] matrix;

    public RawTransformation(float[][] matrix) {
        this.matrix = matrix;
    }

    public RawTransformation(
            float ax, float ay, float az, float tx,
            float bx, float by, float bz, float ty,
            float cx, float cy, float cz, float tz,
            float dx, float dy, float dz, float tt
    ) {
        matrix = new float[][]{
                {ax, ay, az, tx},
                {bx, by, bz, ty},
                {cx, cy, cz, tz},
                {dx, dy, dz, tt},
        };
    }

    public final float[][] getMatrix() {
        return matrix;
    }

    @Override
    public Transformation compose(Transformation transformation) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String toString() {
        final float[][] m = getMatrix();
        return String.format(
                "[% .2f % .2f % .2f % .2f]%n" +
                        "[% .2f % .2f % .2f % .2f]%n" +
                        "[% .2f % .2f % .2f % .2f]%n" +
                        "[% .2f % .2f % .2f % .2f]%n",
                m[0][0], m[0][1], m[0][2], m[0][3],
                m[1][0], m[1][1], m[1][2], m[1][3],
                m[2][0], m[2][1], m[2][2], m[2][3],
                m[3][0], m[3][1], m[3][2], m[3][3]
        );
    }
}
