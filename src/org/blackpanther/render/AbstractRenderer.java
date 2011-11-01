package org.blackpanther.render;

import org.blackpanther.math.Point3D;
import org.blackpanther.math.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public abstract class AbstractRenderer<S extends Shape> implements Renderer<S> {

    private static final Logger logger =
            Logger.getLogger(AbstractRenderer.class.getCanonicalName());

    public static final int PADDING = 40;
    public static final float MAXIMUM_POINT_OF_VIEW_HEIGHT = 800f;

    private final BufferedImage imgBuffer;
    private final Object _lock = new Object();

    private S refShape;

    private Point3D shapeOrigin;
    private Point3D pointOfView;

    public AbstractRenderer(
            final S shape,
            final Dimension dimension
    ) {
        imgBuffer = new BufferedImage(
                dimension.width,
                dimension.height,
                BufferedImage.TYPE_INT_ARGB
        );

        refShape = shape;
        shapeOrigin = new Point3D(
                dimension.width / 2f,
                dimension.height / 2f,
                0f
        );
        //user is just above the cube
        setPointOfView(
                shapeOrigin.getX(),
                shapeOrigin.getY(),
                300f
        );
    }

    @Override
    public void render() {
        final BufferedImage _buffer = new BufferedImage(
                getBufferWidth(),
                getBufferHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        final Graphics2D painter = (Graphics2D) _buffer.getGraphics();
        painter.setColor(Color.WHITE);
        painter.fillRect(0, 0, imgBuffer.getWidth(), imgBuffer.getHeight());

        //render fixed referential axes
        painter.setColor(Color.BLACK);
        painter.fillOval(
                math2pixel(shapeOrigin).x,
                math2pixel(shapeOrigin).y,
                5, 5
        );

        //forget constant, just some padding
        //abscissa axe
        painter.drawLine(
                PADDING, imgBuffer.getHeight() - PADDING,
                imgBuffer.getWidth() - 2 * PADDING, imgBuffer.getHeight() - PADDING
        );
        painter.drawString(
                "Abscissa",
                5 + imgBuffer.getWidth() - 2 * PADDING, imgBuffer.getHeight() - PADDING
        );
        //ordinate axe
        painter.drawLine(
                PADDING, imgBuffer.getHeight() - PADDING,
                PADDING, PADDING
        );
        painter.drawString(
                "Ordinate",
                PADDING, PADDING - 5
        );
        //height axe
        painter.drawOval(
                (int) (PADDING * .75f), imgBuffer.getHeight() - (int) (PADDING * 1.25f),
                PADDING / 2, PADDING / 2
        );
        painter.drawString(
                "Height",
                (int) (PADDING * 1.25f), imgBuffer.getHeight() - (int) (PADDING * .5f)
        );

        modelRender(_buffer);

        painter.dispose();

        logger.finer("Attempt to draw onto internal buffer");
        final Graphics _painter = imgBuffer.createGraphics();
        _painter.drawImage(
                _buffer,
                0,
                0,
                null
        );
        _painter.dispose();
        logger.finer("Drawing onto internal buffer done.");
    }

    abstract protected void modelRender(final BufferedImage _buffer);

    public final BufferedImage getBuffer() {
        return imgBuffer;
    }

    public final int getBufferWidth() {
        return imgBuffer.getWidth();
    }

    public final int getBufferHeight() {
        return imgBuffer.getHeight();
    }

    public Point3D getShapeOrigin() {
        return shapeOrigin;
    }

    public void setShapeOrigin(final Point3D origin) {
        this.shapeOrigin = origin;
    }

    public S getShape() {
        return refShape;
    }

    public void setShape(S newShape) {
        this.refShape = newShape;
    }

    public void setPointOfView(final Point3D pov) {
        setPointOfView(pov.getX(), pov.getY(), pov.getZ());
    }

    public void setPointOfView(float x, float y, float z) {
        this.pointOfView = new Point3D(x, y, z);
    }

    public final Point3D getPointOfView() {
        return pointOfView;
    }

    /**
     * Expand a point through the alignment from the cube's center,
     * and compute position relative to the point of view
     *
     * @param point       - point from cube's referential
     * @param length      - length to expand
     * @param pointOfView - position of the point of view
     * @return - expanded point
     */
    protected Point3D expand(Point3D point, float length, Point3D pointOfView) {
        final float viewRatio =
                (MAXIMUM_POINT_OF_VIEW_HEIGHT - pointOfView.getZ()) /
                        (MAXIMUM_POINT_OF_VIEW_HEIGHT - (point.getZ() * length));
        final float realSide = length * viewRatio;
        return point.moveTo(
                point.getX() * realSide,
                point.getY() * realSide,
                point.getZ() * realSide
        );
    }

    /**
     * Fixed referential is the bottom left corner
     *
     * @param point - math point
     * @return screen point
     */
    public Point math2pixel(Point3D point) {
        return new Point(
                (int) point.getX(),
                getBufferHeight() - (int) point.getY()
        );
    }

    public Point3D pixel2math(Point screen) {
        return new Point3D.Incomplete(
                (float) screen.getX(),
                (float) (getBufferHeight() - screen.getY())
        );
    }

    public Point3D fixedReferential(Point3D shapeReferential) {
        return shapeReferential.moveTo(
                shapeReferential.getX() + shapeOrigin.getX(),
                shapeReferential.getY() + shapeOrigin.getY(),
                shapeReferential.getZ() + shapeOrigin.getZ()
        );
    }

    public Point3D shapeReferential(Point3D fixedReferential) {
        return fixedReferential.moveTo(
                fixedReferential.getX() - getShapeOrigin().getX(),
                fixedReferential.getY() - getShapeOrigin().getY(),
                fixedReferential.getZ() - getShapeOrigin().getZ()
        );
    }

}
