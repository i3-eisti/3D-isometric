package org.blackpanther.math;

/**
 * Descibe a 3D's object transformation
 *
 * @author MACHIZAUD Andréa
 * @version 02/10/11
 */
public interface Transformation {
    /**
     * Get transformation matrix
     */
    public float[][] getMatrix();
    /**
     * Enable the power to compose multiple transformation as a single one
     * @param transformation - transformatio nto compose with
     * @return composed transformation
     */
    public Transformation compose(final Transformation transformation);
}
