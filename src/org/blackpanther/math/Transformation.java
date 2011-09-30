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
}
