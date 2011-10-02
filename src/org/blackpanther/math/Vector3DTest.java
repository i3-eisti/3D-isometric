package org.blackpanther.math;

/**
 * @author MACHIZAUD Andréa
 * @version 02/10/11
 */
public class Vector3DTest {

    public static void main(String[] args) {
        final Vector3D i = new Vector3D(1f, 0f, 0f).normalize();
        final Vector3D j = new Vector3D(0f, 1f, 0f).normalize();
        final Vector3D k = new Vector3D(0f, 0f, 1f).normalize();

        final Vector3D u = new Vector3D(1f, 0f, 0f).normalize();

        final Vector3D v1 = new Vector3D(1f, 0f, 1f) .normalize();
        final Vector3D v2 = new Vector3D(-1f, 0f, 0f).normalize();

        System.out.println("Angle :");
        System.out.println("PI / 2 : " + (Math.PI / 2.0));
        System.out.println("PI / 4 : " + (Math.PI / 4.0));
        System.out.println(String.format("i %s . j %s = %s", i, j, i.angle(j)));
        System.out.println(String.format("j %s . k %s = %s", j, k, j.angle(k)));
        System.out.println(String.format("k %s . i %s = %s", k, i, k.angle(i)));

        System.out.println(String.format("u %s . v1 %s = %s", u, v1, u.angle(v1)));
        System.out.println(String.format("u %s . v2 %s = %s", u, v2, u.angle(v2)));
        System.out.println(String.format("v1 %s . u %s = %s", v1, u, v1.angle(u)));
        System.out.println(String.format("v2 %s . u %s = %s", v2, u, v2.angle(u)));

        System.out.println("Dot :");
        System.out.println(String.format("i %s . j %s = %s", i, j, i.dot(j)));
        System.out.println(String.format("j %s . k %s = %s", j, k, j.dot(k)));
        System.out.println(String.format("k %s . i %s = %s", k, i, k.dot(i)));

        System.out.println(String.format("u %s . v1 %s = %s", u, v1, u.dot(v1)));
        System.out.println(String.format("u %s . v2 %s = %s", u, v2, u.dot(v2)));
        System.out.println(String.format("v1 %s . u %s = %s", v1, u, v1.dot(u)));
        System.out.println(String.format("v2 %s . u %s = %s", v2, u, v2.dot(u)));
    }
}
