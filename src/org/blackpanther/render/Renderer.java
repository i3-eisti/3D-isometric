package org.blackpanther.render;

import java.awt.*;

import org.blackpanther.math.Point3D;
import org.blackpanther.math.Shape;

public interface Renderer<S extends Shape> {
	
	public Image getBuffer();
	public void render();
	
	public S getShape();
	public void setShape(S rotatedShape);

	public Point3D getPointOfView();
	public void setPointOfView(Point3D pov);

    public Point3D fixedReferential(Point3D shapePoint);
    public Point3D shapeReferential(Point3D fixedPoint);

    public Point3D normalized(Point3D rawPoint);

    public Point3D pixel2math(Point point);
    public Point math2pixel(Point3D point);
}
