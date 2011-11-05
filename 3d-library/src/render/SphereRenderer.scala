package render

import java.awt.geom.{Point2D, Dimension2D}
import swing._
import org.blackpanther.three.model._
import scala.math.{sqrt, cos, abs, Pi}


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

    @inline def toFixed(pt : ShapeReferential) : FixedReferential =
      new Point3D(
        pt.x + shapePosition.x,
        pt.y + shapePosition.y,
        pt.z
      ) with FixedReferential

    @inline def isWithinSphere(pt : Point2D) : Boolean =
      sqrt(pt.getX * pt.getX + pt.getY * pt.getY).toFloat <= model.radius

    for {
      x <- 0 until dimension.getWidth.toInt
      y <- 0 until dimension.getHeight.toInt
      fixedPoint = Renderer.pixel2math(dimension, new Point(x,y))
      shapePoint = toSphereReferential(fixedPoint)
      if isWithinSphere(shapePoint)
    } {
      val (shapePositive, _) = model.surfacePointFrom(shapePoint)
      val fixedPositive = toFixed(shapePositive)
      //val fixedNegative = toFixed(shapeNegative)

      val towardLightSource = Vector3D(shapePosition, pointOfView)
      val towardPositive = Vector3D(shapePosition, fixedPositive)
      //val towardNegative = Vector3D(shapePosition, fixedNegative)

      val isPositiveVisible = {
        val angle = towardLightSource angle towardPositive

        (0 < angle && angle < (Pi / 2.0)) ||
        (0 > angle && angle > (-Pi / 2.0))
      }


      val colorStrengh =
        if( isPositiveVisible ){
          val light = Vector3D(fixedPositive, pointOfView)
          val incidence = towardPositive angle light
          val cosa = cos(incidence).toFloat
          if (cosa > 0f)
            cosa
          else
            0f
        } else {
          0f
        }

      val rendererColor = new Color(
        (colorStrengh * modelColor.getRed).toInt,
        (colorStrengh * modelColor.getGreen).toInt,
        (colorStrengh * modelColor.getBlue).toInt
      )

      buffer.setRGB(x, y, rendererColor.getRGB)
    }

  }
}