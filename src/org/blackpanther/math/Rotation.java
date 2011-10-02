package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 02/10/11
 */
public class Rotation extends RawTransformation {

    public Rotation(float[][] m) {
        super(
                m[0][0], m[0][1], m[0][2], 0f,
                m[1][0], m[1][1], m[1][2], 0f,
                m[2][0], m[2][1], m[2][2], 0f,
                0f, 0f, 0f, 1f
        );
    }
}
