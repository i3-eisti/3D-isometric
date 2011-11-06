package org.blackpanther.three
package render

import java.awt.geom.{Point2D, Dimension2D}
import swing._
import org.blackpanther.three.model._
import scala.math.{sqrt, cos, Pi}


/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */

object SphereRenderer extends Renderer[Sphere] {

  def apply(
    buffer : java.awt.image.BufferedImage,
    dimension : Dimension2D,
    pointOfView : FixedReferential,
    shapePosition : FixedReferential,
    model : Sphere,
    modelColor : Color
  ) {

    @inline def toSphereReferential(inc : Point2D) : Point2D =
      new Point2D.Double(
        inc.getX - shapePosition.x,
        inc.getY - shapePosition.x
      )

    val squeezeRatio = model.radius / Vector3D(shapePosition,pointOfView).euclidNorm
    val realRadius = model.radius * squeezeRatio

    @inline def normalized(point : Point2D) : Point2D =
      new Point2D.Double(
       point.getX / realRadius,
       point.getY / realRadius
      )

    @inline def toFixed(pt : ShapeReferential) : FixedReferential =
      new Point3D(
        pt.x * realRadius + shapePosition.x,
        pt.y * realRadius + shapePosition.y,
        pt.z * realRadius + shapePosition.z
      ) with FixedReferential

    @inline def isWithinSphere(pt : Point2D) : Boolean =
      sqrt(pt.getX * pt.getX + pt.getY * pt.getY).toFloat <= realRadius

    @inline def surfacePointFrom(pt : Point2D) : (ShapeReferential, ShapeReferential) = {
       val height = sqrt(
        realRadius * realRadius -
        pt.getX * pt.getX -
        pt.getY * pt.getY
      ).toFloat
      (
        new Point3D(pt.getX.toFloat, pt.getY.toFloat,  height) with ShapeReferential,
        new Point3D(pt.getX.toFloat, pt.getY.toFloat, -height) with ShapeReferential
      )
    }

    val towardLightSource = Vector3D(shapePosition, pointOfView)

    for {
      x <- 0 until dimension.getWidth.toInt
      y <- 0 until dimension.getHeight.toInt
      fixedPoint = Renderer.pixel2math(dimension, new Point(x,y))
      shapePoint = toSphereReferential(fixedPoint)
      normShapePoint = normalized(shapePoint)
      if isWithinSphere(normShapePoint)
    } {
      val (shapePositive, shapeNegative) = surfacePointFrom(normShapePoint)
      val fixedPositive = toFixed(shapePositive)
      val fixedNegative = toFixed(shapeNegative)

      val towardPositive = Vector3D.fromOriginTo(shapePositive)
      val towardNegative = Vector3D.fromOriginTo(shapeNegative)

      val isPositiveVisible = {
        val angle = towardLightSource angle towardPositive

        (0 <= angle && angle < (Pi / 2.0)) ||
        (0 >= angle && angle > (-Pi / 2.0))
      }


      val colorStrengh =
        (if( isPositiveVisible ){
          val surfaceToLight = Vector3D(fixedPositive, pointOfView)
          val incidence = towardPositive angle surfaceToLight
          val cosa = cos(incidence)
          if( cosa > 0.0)
            cosa
          else
            0.0
        } else {
          0.0
        }).abs

      val rendererColor = new Color(
        (colorStrengh * modelColor.getRed).toInt,
        (colorStrengh * modelColor.getGreen).toInt,
        (colorStrengh * modelColor.getBlue).toInt
      )

      buffer.setRGB(x, y, rendererColor.getRGB)
    }

  }
}