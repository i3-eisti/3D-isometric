package org.blackpanther.render;

import org.blackpanther.math.Box;
import org.blackpanther.math.Cube;
import org.blackpanther.math.Point3D;
import org.blackpanther.math.Vector3D;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.blackpanther.math.BoxConstants.*;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class BoxRender extends AbstractRenderer<Box> {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(BoxRender.class.getCanonicalName());

    public enum DrawMode {
        FILL,
        LINE
    }

    private final float cubeSide;

    private Vector3D towardUserPointOfView;

    private final DrawMode mode;

    public BoxRender(
            final Box cube,
            final float sideSize,
            final DrawMode mode,
            final Dimension dimension
    ) {
        super(cube, dimension);

        cubeSide = sideSize;

        this.mode = mode;
    }

    @Override
    public Point3D normalized(Point3D point) {
        final float halfSide = cubeSide / 2f;
        return new Point3D(
                point.getX() / halfSide,
                point.getY() / halfSide,
                point.getZ() / halfSide
        );
    }

    protected final void modelRender(final BufferedImage _buffer) {
        final Graphics2D painter = (Graphics2D) _buffer.getGraphics();

        final Point3D[] mathPoints = getShape().getPoints();
        final Point[] screenPoints = new Point[mathPoints.length];

        //first translate fixed referential to cube referential,
        // then apply change from math to screen referential
        for (int pointCode = screenPoints.length; pointCode-- > 0; ) {
            screenPoints[pointCode] =
                    math2pixel(
                            fixedReferential(
                                    expand(mathPoints[pointCode], cubeSide / 2f, getPointOfView())
                            )
                    );
        }

        painter.setColor(Color.BLACK);
        final Stroke defaultStroke = painter.getStroke();

        //make line little thicker
        painter.setStroke(new BasicStroke(1f));

        switch (mode) {
            case LINE:
                painter.drawLine(
                        screenPoints[A].x, screenPoints[A].y,
                        screenPoints[B].x, screenPoints[B].y
                );
                painter.drawLine(
                        screenPoints[B].x, screenPoints[B].y,
                        screenPoints[F].x, screenPoints[F].y
                );
                painter.drawLine(
                        screenPoints[F].x, screenPoints[F].y,
                        screenPoints[E].x, screenPoints[E].y
                );
                painter.drawLine(
                        screenPoints[E].x, screenPoints[E].y,
                        screenPoints[A].x, screenPoints[A].y
                );
                painter.drawLine(
                        screenPoints[A].x, screenPoints[A].y,
                        screenPoints[D].x, screenPoints[D].y
                );
                painter.drawLine(
                        screenPoints[D].x, screenPoints[D].y,
                        screenPoints[C].x, screenPoints[C].y
                );
                painter.drawLine(
                        screenPoints[D].x, screenPoints[D].y,
                        screenPoints[H].x, screenPoints[H].y
                );
                painter.drawLine(
                        screenPoints[C].x, screenPoints[C].y,
                        screenPoints[B].x, screenPoints[B].y
                );
                painter.drawLine(
                        screenPoints[C].x, screenPoints[C].y,
                        screenPoints[G].x, screenPoints[G].y
                );
                painter.drawLine(
                        screenPoints[G].x, screenPoints[G].y,
                        screenPoints[H].x, screenPoints[H].y
                );
                painter.drawLine(
                        screenPoints[G].x, screenPoints[G].y,
                        screenPoints[F].x, screenPoints[F].y
                );
                painter.drawLine(
                        screenPoints[H].x, screenPoints[H].y,
                        screenPoints[E].x, screenPoints[E].y
                );
                break;
            case FILL:
                //build points as see them with perspective
                final Point3D[] perspectivePoints = new Point3D[8];
                for (int index = mathPoints.length; index-- > 0; ) {
                    perspectivePoints[index] =
                            expand(mathPoints[index], cubeSide / 2f, getPointOfView());
                }

                logger.fine("Toward point of view : " + towardUserPointOfView);
                //compute for each side if it should be drawn or not
                // i.e side's normal vector's z-component must be > 0
                for (int face = 6; face-- > 0; ) {
                    Vector3D[] faceVector = getNormal(perspectivePoints, face);

                    Vector3D normal = faceVector[0].normal(faceVector[1]);

                    logger.info(String.format(
                            "%s : %s * %s = %s",
                            FACES_TEXT[face],
                            faceVector[0],
                            faceVector[1],
                            normal
                    ));
//                    if (normal.dot(towardUserPointOfView) > 0f) {
                    if (normal.getZ() > 0f) {

                        switch (face) {
                            case ABFE:
                                logger.info("Face ABFE is visible");
                                painter.drawLine(
                                        screenPoints[A].x, screenPoints[A].y,
                                        screenPoints[B].x, screenPoints[B].y
                                );
                                painter.drawLine(
                                        screenPoints[B].x, screenPoints[B].y,
                                        screenPoints[F].x, screenPoints[F].y
                                );
                                painter.drawLine(
                                        screenPoints[F].x, screenPoints[F].y,
                                        screenPoints[E].x, screenPoints[E].y
                                );
                                painter.drawLine(
                                        screenPoints[E].x, screenPoints[E].y,
                                        screenPoints[A].x, screenPoints[A].y
                                );
                                break;
                            case ABCD:
                                logger.info("Face ABCD is visible");
                                painter.drawLine(
                                        screenPoints[A].x, screenPoints[A].y,
                                        screenPoints[B].x, screenPoints[B].y
                                );
                                painter.drawLine(
                                        screenPoints[B].x, screenPoints[B].y,
                                        screenPoints[C].x, screenPoints[C].y
                                );
                                painter.drawLine(
                                        screenPoints[C].x, screenPoints[C].y,
                                        screenPoints[D].x, screenPoints[D].y
                                );
                                painter.drawLine(
                                        screenPoints[D].x, screenPoints[D].y,
                                        screenPoints[A].x, screenPoints[A].y
                                );
                                break;
                            case BCGF:
                                logger.info("Face BCGF is visible");
                                painter.drawLine(
                                        screenPoints[F].x, screenPoints[F].y,
                                        screenPoints[B].x, screenPoints[B].y
                                );
                                painter.drawLine(
                                        screenPoints[B].x, screenPoints[B].y,
                                        screenPoints[C].x, screenPoints[C].y
                                );
                                painter.drawLine(
                                        screenPoints[C].x, screenPoints[C].y,
                                        screenPoints[G].x, screenPoints[G].y
                                );
                                painter.drawLine(
                                        screenPoints[G].x, screenPoints[G].y,
                                        screenPoints[F].x, screenPoints[F].y
                                );
                                break;
                            case CDHG:
                                logger.info("Face CDHG is visible");
                                painter.drawLine(
                                        screenPoints[H].x, screenPoints[H].y,
                                        screenPoints[D].x, screenPoints[D].y
                                );
                                painter.drawLine(
                                        screenPoints[D].x, screenPoints[D].y,
                                        screenPoints[C].x, screenPoints[C].y
                                );
                                painter.drawLine(
                                        screenPoints[C].x, screenPoints[C].y,
                                        screenPoints[G].x, screenPoints[G].y
                                );
                                painter.drawLine(
                                        screenPoints[G].x, screenPoints[G].y,
                                        screenPoints[H].x, screenPoints[H].y
                                );
                                break;
                            case EHGF:
                                logger.info("Face EHGF is visible");
                                painter.drawLine(
                                        screenPoints[H].x, screenPoints[H].y,
                                        screenPoints[E].x, screenPoints[E].y
                                );
                                painter.drawLine(
                                        screenPoints[E].x, screenPoints[E].y,
                                        screenPoints[F].x, screenPoints[F].y
                                );
                                painter.drawLine(
                                        screenPoints[F].x, screenPoints[F].y,
                                        screenPoints[G].x, screenPoints[G].y
                                );
                                painter.drawLine(
                                        screenPoints[G].x, screenPoints[G].y,
                                        screenPoints[H].x, screenPoints[H].y
                                );
                                break;
                            case DAEH:
                                logger.info("Face DAEH is visible");
                                painter.drawLine(
                                        screenPoints[H].x, screenPoints[H].y,
                                        screenPoints[E].x, screenPoints[E].y
                                );
                                painter.drawLine(
                                        screenPoints[E].x, screenPoints[E].y,
                                        screenPoints[A].x, screenPoints[A].y
                                );
                                painter.drawLine(
                                        screenPoints[A].x, screenPoints[A].y,
                                        screenPoints[D].x, screenPoints[D].y
                                );
                                painter.drawLine(
                                        screenPoints[D].x, screenPoints[D].y,
                                        screenPoints[H].x, screenPoints[H].y
                                );
                                break;
                        }
                    }
                }
                break;
        }
        painter.setStroke(defaultStroke);

        for (int pointCode = screenPoints.length; pointCode-- > 0; ) {
            Point screenPoint = screenPoints[pointCode];
            switch (pointCode) {
                case A:
                    painter.setColor(Color.BLUE);
                    painter.drawString("A", screenPoint.x, screenPoint.y);
                    break;
                case B:
                    painter.setColor(Color.CYAN);
                    painter.drawString("B", screenPoint.x, screenPoint.y);
                    break;
                case C:
                    painter.setColor(Color.RED);
                    painter.drawString("C", screenPoint.x, screenPoint.y);
                    break;
                case D:
                    painter.setColor(Color.PINK);
                    painter.drawString("D", screenPoint.x, screenPoint.y);
                    break;
                case E:
                    painter.setColor(Color.GREEN);
                    painter.drawString("E", screenPoint.x, screenPoint.y);
                    break;
                case F:
                    painter.setColor(Color.YELLOW);
                    painter.drawString("F", screenPoint.x, screenPoint.y);
                    break;
                case G:
                    painter.setColor(Color.MAGENTA);
                    painter.drawString("G", screenPoint.x, screenPoint.y);
                    break;
                case H:
                    painter.setColor(Color.ORANGE);
                    painter.drawString("H", screenPoint.x, screenPoint.y);
                    break;
            }
        }
    }

    @Override
    public void setPointOfView(float x, float y, float z) {
        super.setPointOfView(x, y, z);
        this.towardUserPointOfView = new Vector3D(getShapeOrigin(), getPointOfView());
    }

    private Vector3D[] getNormal(final Point3D[] points, final int face) {
        switch (face) {
            case ABCD:
                return new Vector3D[]{
                        new Vector3D(points[B], points[A]),
                        new Vector3D(points[B], points[C])
                };
            case ABFE:
                return new Vector3D[]{
                        new Vector3D(points[B], points[F]),
                        new Vector3D(points[B], points[A])
                };
            case BCGF:
                return new Vector3D[]{
                        new Vector3D(points[B], points[C]),
                        new Vector3D(points[B], points[F])
                };
            case CDHG:
                return new Vector3D[]{
                        new Vector3D(points[H], points[G]),
                        new Vector3D(points[H], points[D])
                };
            case EHGF:
                return new Vector3D[]{
                        new Vector3D(points[H], points[E]),
                        new Vector3D(points[H], points[G])
                };
            case DAEH:
                return new Vector3D[]{
                        new Vector3D(points[H], points[D]),
                        new Vector3D(points[H], points[E])
                };
            default:
                throw new IllegalArgumentException("Unknown face ! " + face);
        }
    }
}
