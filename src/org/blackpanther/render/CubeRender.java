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
    /**
     * Cache des 2 vecteurs utiles pour calculer la normale des 6 faces
     */
    private final Vector3D[][] normalVectors = new Vector3D[6][2];

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
                (dimension.width - cubeSide) / 2f,
                (dimension.height - cubeSide) / 2f,
                0f
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
                                    expand(mathPoints[pointCode], cubeSide / 2f)));
        }

        //compute for each side if it should be drawn or not
        // i.e side's normal vector's z-component must be > 0
        final Stroke defaultStroke = painter.getStroke();

        painter.setStroke(new BasicStroke(2f));

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
                for (int face = normalVectors.length; face-- > 0; ) {
                    Vector3D[] faceVector = normalVectors[face];

                    Vector3D normal = faceVector[0].normal(faceVector[1]);

                    logger.info(String.format(
                            "%s : %s * %s = %s",
                            Cube.faceText(face),
                            faceVector[0],
                            faceVector[1],
                            normal
                    ));
                    if (normal.getZ() > 0) {

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

    private Point3D expand(Point3D point, float length) {
        return new Point3D(
                point.getX() * length,
                point.getY() * length,
                point.getZ() * length
        );
    }

    public final void setCube(final Cube cube) {
        this.refCube = cube;
        //cache vectors necessary to compute normal for each face
        final Point3D[] points = cube.getPoints();
        normalVectors[ABCD][0] = new Vector3D(points[B], points[A]);
        normalVectors[ABCD][1] = new Vector3D(points[B], points[C]);

        normalVectors[ABFE][0] = new Vector3D(points[B], points[F]);
        normalVectors[ABFE][1] = new Vector3D(points[B], points[A]);

        normalVectors[BCGF][0] = new Vector3D(points[B], points[C]);
        normalVectors[BCGF][1] = new Vector3D(points[B], points[F]);

        normalVectors[EHGF][0] = new Vector3D(points[H], points[E]);
        normalVectors[EHGF][1] = new Vector3D(points[H], points[G]);

        normalVectors[CDHG][0] = new Vector3D(points[H], points[G]);
        normalVectors[CDHG][1] = new Vector3D(points[H], points[D]);

        normalVectors[DAEH][0] = new Vector3D(points[H], points[D]);
        normalVectors[DAEH][1] = new Vector3D(points[H], points[E]);
    }

    public final Cube getCube() {
        return refCube;
    }

    public final BufferedImage getBuffer() {
        return imgBuffer;
    }

    private Point3D fixedReferential(Point3D fixedReferential) {
        return new Point3D(
                fixedReferential.getX() + cubeOrigin.getX(),
                fixedReferential.getY() + cubeOrigin.getY(),
                fixedReferential.getZ() + cubeOrigin.getZ()
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
