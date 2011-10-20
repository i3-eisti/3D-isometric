package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 19/10/11
 */
public interface BoxConstants {

    //vertices
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

    /*=========================================================================
                                       STATIC
    =========================================================================*/

    /**
     * Cache of string representation of face index
     */
    public static final String[] FACES_TEXT = new String[]{
            "ABFE",
            "ABCD",
            "BCGF",
            "CDHG",
            "EHGF",
            "DAEH",
    };

}
