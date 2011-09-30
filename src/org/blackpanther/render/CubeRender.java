package org.blackpanther.render;

import org.blackpanther.model.Cube;
import org.blackpanther.model.Point3D;
import org.blackpanther.model.Vector3D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static org.blackpanther.model.Cube.*;

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

    /** Buffer oÃ¹ l'on dessine */
    private final BufferedImage imgBuffer;
    /** Référence vers un modÃ¨le de cube */
    private final Cube refCube;
    private final int cubeSide;
    private final Point3D cubeOrigin;
    /** Cache des 2 vecteurs utiles pour calculer la normale des 6 faces */
    private final Vector3D[][] normalVectors = new Vector3D[6][2];

    private DrawMode mode = DrawMode.LINE;

    public CubeRender(
            final Cube cube,
            final int sideSize,
            final Dimension dimension
    ) {
        imgBuffer = new BufferedImage(
                dimension.width,
                dimension.height,
                BufferedImage.TYPE_INT_ARGB
        );

        refCube = cube;
        cubeSide = sideSize;
        cubeOrigin = new Point3D(
                ( dimension.width  - cubeSide ) / 2,
                ( dimension.height - cubeSide ) / 2,
                0
        );

        //cache vectors necessary to compute normal for each face
        final Point3D[] points = cube.getPoints();
        normalVectors[ABCD][0] = new Vector3D(points[B], points[C]);
        normalVectors[ABCD][1] = new Vector3D(points[B], points[A]);

        normalVectors[ABFE][0] = new Vector3D(points[B], points[A]);
        normalVectors[ABFE][1] = new Vector3D(points[B], points[F]);

        normalVectors[BCGF][0] = new Vector3D(points[B], points[F]);
        normalVectors[BCGF][1] = new Vector3D(points[B], points[C]);

        normalVectors[EHGF][0] = new Vector3D(points[H], points[D]);
        normalVectors[EHGF][1] = new Vector3D(points[H], points[G]);

        normalVectors[CDHG][0] = new Vector3D(points[H], points[G]);
        normalVectors[CDHG][1] = new Vector3D(points[H], points[E]);

        normalVectors[DAEH][0] = new Vector3D(points[H], points[E]);
        normalVectors[DAEH][1] = new Vector3D(points[H], points[D]);
    }

    public final void render() {
        final Graphics2D painter = (Graphics2D) imgBuffer.getGraphics();

        final Point3D[] points = refCube.getPoints();

        for (int face = normalVectors.length; face-- > 0; ) {
            Vector3D[] faceVector = normalVectors[face];

            Vector3D normal = faceVector[0].normal(faceVector[1]);

            //face is visible
            if( normal.getZ() > 0 ) {

                switch (face){
                    case ABFE:
                        logger.info("Face ABFE is visible");
                    	painter.drawLine(
                                points[A].getX(), points[A].getY(),
                                points[B].getX(), points[B].getY()
                        );
                    	painter.drawLine(
                                points[B].getX(), points[B].getY(),
                                points[F].getX(), points[F].getY()
                        );
                    	painter.drawLine(
                                points[F].getX(), points[F].getY(),
                                points[E].getX(), points[E].getY()
                        );
                    	painter.drawLine(
                                points[E].getX(), points[E].getY(),
                                points[A].getX(), points[A].getY()
                        );
                        break;
                    case ABCD:
                        logger.info("Face ABCD is visible");
                    	painter.drawLine(
                                points[A].getX(), points[A].getY(),
                                points[B].getX(), points[B].getY()
                        );
                    	painter.drawLine(
                                points[B].getX(), points[B].getY(),
                                points[C].getX(), points[C].getY()
                        );
                    	painter.drawLine(
                                points[C].getX(), points[C].getY(),
                                points[D].getX(), points[D].getY()
                        );
                    	painter.drawLine(
                                points[D].getX(), points[D].getY(),
                                points[A].getX(), points[A].getY()
                        );
                        break;
                    case BCGF:
                        logger.info("Face BCGF is visible");
                    	painter.drawLine(
                                points[F].getX(), points[F].getY(),
                                points[B].getX(), points[B].getY()
                        );
                    	painter.drawLine(
                                points[B].getX(), points[B].getY(),
                                points[C].getX(), points[C].getY()
                        );
                    	painter.drawLine(
                                points[C].getX(), points[C].getY(),
                                points[G].getX(), points[G].getY()
                        );
                    	painter.drawLine(
                                points[G].getX(), points[G].getY(),
                                points[F].getX(), points[F].getY()
                        );
                        break;
                    case CDHG:
                        logger.info("Face CDHG is visible");
                    	painter.drawLine(
                                points[H].getX(), points[H].getY(),
                                points[D].getX(), points[D].getY()
                        );
                    	painter.drawLine(
                                points[D].getX(), points[D].getY(),
                                points[C].getX(), points[C].getY()
                        );
                    	painter.drawLine(
                                points[C].getX(), points[C].getY(),
                                points[G].getX(), points[G].getY()
                        );
                    	painter.drawLine(
                                points[G].getX(), points[G].getY(),
                                points[H].getX(), points[H].getY()
                        );
                        break;
                    case EHGF:
                        logger.info("Face EHGF is visible");
                    	painter.drawLine(
                                points[H].getX(), points[H].getY(),
                                points[E].getX(), points[E].getY()
                        );
                    	painter.drawLine(
                                points[E].getX(), points[E].getY(),
                                points[F].getX(), points[F].getY()
                        );
                    	painter.drawLine(
                                points[F].getX(), points[F].getY(),
                                points[G].getX(), points[G].getY()
                        );
                    	painter.drawLine(
                                points[G].getX(), points[G].getY(),
                                points[H].getX(), points[H].getY()
                        );
                        break;
                    case DAEH:
                        logger.info("Face DAEH is visible");
                    	painter.drawLine(
                                points[H].getX(), points[H].getY(),
                                points[E].getX(), points[E].getY()
                        );
                    	painter.drawLine(
                                points[E].getX(), points[E].getY(),
                                points[A].getX(), points[A].getY()
                        );
                    	painter.drawLine(
                                points[A].getX(), points[A].getY(),
                                points[D].getX(), points[D].getY()
                        );
                    	painter.drawLine(
                                points[D].getX(), points[D].getY(),
                                points[H].getX(), points[H].getY()
                        );
                        break;
                }
            }
        }


        painter.dispose();
    }

    public BufferedImage getBuffer() {
        return imgBuffer;
    }

    private Point3D cubeReferential(Point3D fixedReferential) {
        return new Point3D(
                fixedReferential.getX() + cubeOrigin.getX(),
                fixedReferential.getY() + cubeOrigin.getY(),
                fixedReferential.getZ() + cubeOrigin.getZ()
        );
    }

    /**
     * Fixed refrential is the bottom left corner
     * @param point - math point
     * @return screen point
     */
    private Point math2pixel(Point3D point){
        return new Point(
            point.getX(),
            point.getY() - getBuffer().getHeight(null)
        );
    }
}
