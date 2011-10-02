package org.blackpanther.render;

import org.blackpanther.math.Cube;
import org.blackpanther.math.Point3D;
import org.blackpanther.math.Vector3D;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.blackpanther.math.Cube.*;

/**
 * @author MACHIZAUD AndrÃ©a
 * @version 9/29/11
 */
public class CubeRender {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(CubeRender.class.getCanonicalName());

    public static final float MAXIMUM_POINT_OF_VIEW_HEIGHT = 400f;

    public enum DrawMode {
        FILL,
        LINE
    }

    /**
     * Buffer où l'on dessine
     */
    private final BufferedImage imgBuffer;
    /**
     * Ecart avec le bord
     */
    private int padding;
    /**
     * Référence vers un modèle de cube
     */
    private Cube refCube;
    private final int cubeSide;

    private final Point3D cubeOrigin;
    private Point3D pointOfView;

    private Vector3D towardUserPointOfView;

    private final DrawMode mode;

    public CubeRender(
            final Cube cube,
            final int sideSize,
            final DrawMode mode,
            final Dimension dimension
    ) {
        imgBuffer = new BufferedImage(
                dimension.width,
                dimension.height,
                BufferedImage.TYPE_INT_ARGB
        );

        padding = 40;

        cubeSide = sideSize;
        cubeOrigin = new Point3D(
                dimension.width / 2f,
                dimension.height / 2f,
                0f
        );
        //user is just above the cube
        setPointOfView(
                cubeOrigin.getX(),
                cubeOrigin.getY(),
                MAXIMUM_POINT_OF_VIEW_HEIGHT - 100f
        );

        setCube(cube);

        this.mode = mode;
    }

    public final void render() {
        final Graphics2D painter = (Graphics2D) imgBuffer.getGraphics();
        painter.setColor(Color.WHITE);
        painter.fillRect(0, 0, imgBuffer.getWidth(), imgBuffer.getHeight());

        //render fixed referential axes
        painter.setColor(Color.BLACK);
        painter.fillOval(
                math2pixel(cubeOrigin).x,
                math2pixel(cubeOrigin).y,
                5, 5
        );

        //forget constant, just some padding
        //abscissa axe
        painter.drawLine(
                padding, imgBuffer.getHeight() - padding,
                imgBuffer.getWidth() - 2 * padding, imgBuffer.getHeight() - padding
        );
        painter.drawString(
                "Abscissa",
                5 + imgBuffer.getWidth() - 2 * padding, imgBuffer.getHeight() - padding
        );
        //ordinate axe
        painter.drawLine(
                padding, imgBuffer.getHeight() - padding,
                padding, padding
        );
        painter.drawString(
                "Ordinate",
                padding, padding - 5
        );
        //height axe
        painter.drawOval(
                (int) (padding * .75f), imgBuffer.getHeight() - (int) (padding * 1.25f),
                padding / 2, padding / 2
        );
        painter.drawString(
                "Height",
                (int) (padding * 1.25f), imgBuffer.getHeight() - (int) (padding * .5f)
        );

        final Point3D[] mathPoints = refCube.getPoints();
        final Point[] screenPoints = new Point[mathPoints.length];

        //first translate fixed referential to cube referential,
        // then apply change from math to screen referential
        for (int pointCode = screenPoints.length; pointCode-- > 0; ) {
            screenPoints[pointCode] =
                    math2pixel(
                            fixedReferential(
                                    expand(mathPoints[pointCode], cubeSide / 2f, pointOfView)
                            )
                    );
        }

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
                            expand(mathPoints[index], cubeSide / 2f, pointOfView);
                }

                logger.fine("Toward point of view : " + towardUserPointOfView);
                //compute for each side if it should be drawn or not
                // i.e side's normal vector's z-component must be > 0
                for (int face = 6; face-- > 0; ) {
                    Vector3D[] faceVector = getNormal(perspectivePoints, face);

                    Vector3D normal = faceVector[0].normal(faceVector[1]);

                    logger.info(String.format(
                            "%s : %s * %s = %s",
                            Cube.faceText(face),
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

        painter.dispose();
    }

    /**
     * Expand a point through the alignment from the cube's center,
     * and compute position relative to the point of view
     *
     * @param point       - point from cube's referential
     * @param length      - length to expland
     * @param pointOfView - position of the point of view
     * @return - expanded point
     */
    private Point3D expand(Point3D point, float length, Point3D pointOfView) {
        final float viewRatio =
                (MAXIMUM_POINT_OF_VIEW_HEIGHT - pointOfView.getZ()) /
                        (MAXIMUM_POINT_OF_VIEW_HEIGHT - (point.getZ() * length));
        final float realSide = length * viewRatio;
        return new Point3D(
                point.getX() * realSide,
                point.getY() * realSide,
                point.getZ() * realSide
        );
    }

    public void setPointOfView(final Point3D pov) {
        setPointOfView(pov.getX(), pov.getY(), pov.getZ());
    }

    public void setPointOfView(float x, float y, float z) {
        this.pointOfView = new Point3D(x, y, z);
        this.towardUserPointOfView = new Vector3D(cubeOrigin, pointOfView);
    }

    public final Point3D getPointOfView() {
        return pointOfView;
    }

    public final void setCube(final Cube cube) {
        this.refCube = cube;
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

    public final Cube getCube() {
        return refCube;
    }

    public final BufferedImage getBuffer() {
        return imgBuffer;
    }

    private Point3D fixedReferential(Point3D cubeReferential) {
        return new Point3D(
                cubeReferential.getX() + cubeOrigin.getX(),
                cubeReferential.getY() + cubeOrigin.getY(),
                cubeReferential.getZ() + cubeOrigin.getZ()
        );
    }

    /**
     * Fixed refrential is the bottom left corner
     *
     * @param point - math point
     * @return screen point
     */
    private Point math2pixel(Point3D point) {
        return new Point(
                (int) point.getX(),
                getBuffer().getHeight() - (int) point.getY()
        );
    }
}
