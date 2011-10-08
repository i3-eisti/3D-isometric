package org.blackpanther.render;

import org.blackpanther.math.Point3D;
import org.blackpanther.math.Sphere;
import org.blackpanther.math.Vector3D;

import java.awt.*;
import java.util.logging.Logger;

import static java.lang.Math.*;

public class SphereRender extends AbstractRenderer<Sphere> {

    private static Logger logger = Logger.getLogger(SphereRender.class.getCanonicalName());

    private float radius;

    private Color sphereColor = Color.RED;

    public SphereRender(Sphere shape, float initialRadius, Dimension dimension) {
        super(shape, dimension);
        radius = initialRadius;
    }

    @Override
    void modelRender(Graphics2D painter) {

        final Sphere sphere = getShape();

        for (int abscissa = getBuffer().getWidth(); abscissa-- > 0; ) {
            for (int ordinate = getBuffer().getHeight(); ordinate-- > 0; ) {

                final Point screenPoint = new Point(abscissa, ordinate);
                final Point3D mathPoint = pixel2math(screenPoint);

                final Point3D sphereReferential = shapeReferential(mathPoint);

                if (withinSphere(sphereReferential, radius)) {

                    final Point3D normalSphereReferential = normalized(sphereReferential);

                    final Point3D spherePositive = sphere.getPositiveSurfacePoint(normalSphereReferential);
                    final Point3D positive = sphere2fixed(spherePositive);
                    final Point3D sphereNegative = sphere.getNegativeSurfacePoint(normalSphereReferential);
                    final Point3D negative = sphere2fixed(sphereNegative);

                    final Vector3D towardLight = new Vector3D(getShapeOrigin(), getPointOfView());
                    final Vector3D towardPositive = new Vector3D(getShapeOrigin(), positive);
                    final Vector3D towardNegative = new Vector3D(getShapeOrigin(), negative);

                    final double angleWithPositive = towardLight.angle(towardPositive);
                    //final double angleWithNegative = towardLight.angle(towardNegative);

                    final Vector3D normal;
                    final Vector3D lightVector;

                    final boolean lessThanPIByTwo =
                            (0 < angleWithPositive && angleWithPositive < (PI / 2.0));
                    final boolean lessThanMinusPIByTwo =
                            (0 > angleWithPositive && angleWithPositive > (-PI / 2.0));

                    if (lessThanPIByTwo || lessThanMinusPIByTwo) {
                        // compute color rendering at (x,y) for positive solution
                        normal = towardPositive;
                        lightVector = new Vector3D(positive, getPointOfView());
                    } else {
                        // compute color rendering at (x,y) for negative solution
                        normal = towardNegative;
                        lightVector = new Vector3D(negative, getPointOfView());
                    }

                    final double incidence = normal.angle(lightVector);

                    final float rawColorStrength = (float) cos(incidence);
                    final float colorStrength = abs(rawColorStrength);

                    final int renderedRed = (int) (sphereColor.getRed() * colorStrength);
                    final int renderedGreen = (int) (sphereColor.getGreen() * colorStrength);
                    final int renderedBlue = (int) (sphereColor.getBlue() * colorStrength);

                    final Color renderedColor = new Color(renderedRed, renderedGreen, renderedBlue);

//                    logger.fine(String.format(
//                            "%s rendered for (%d,%d) pixel",
//                            renderedColor,
//                            screenPoint.x,
//                            screenPoint.y
//                    ));

                    getBuffer().setRGB(abscissa, ordinate, renderedColor.getRGB());
                }
            }
        }

    }

    private Point3D sphere2fixed(Point3D spherePoint) {
        return spherePoint.moveTo(
            spherePoint.getX() + getShapeOrigin().getX(),
            spherePoint.getY() + getShapeOrigin().getY(),
            spherePoint.getZ() + getShapeOrigin().getZ()
        );
    }

    private boolean withinSphere(Point3D point, float radius) {
        return sqrt( point.getX() * point.getX() + point.getY() * point.getY() ) <= radius;
    }

    @Override
    public Point3D normalized(Point3D point) {
        return point.moveTo(
                point.getX() / radius,
                point.getY() / radius,
                point.getZ() / radius
        );
    }
}
