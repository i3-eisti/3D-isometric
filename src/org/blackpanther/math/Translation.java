package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 02/10/11
 */
public class Translation extends RawTransformation {

    public Translation(float[] v) {
        this(v[0], v[1], v[2]);
    }

    public Translation(float ax, float ay, float az) {
        super(
                1f, 0f, 0f, ax,
                0f, 1f, 0f, ay,
                0f, 0f, 1f, az,
                0f, 0f, 0f, 1f
        );
    }
}
