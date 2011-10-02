package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 02/10/11
 */
public interface Transformation {
    public float[][] getMatrix();

    public Transformation compose(final Transformation transformation);
}
