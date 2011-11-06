package org.blackpanther.three
package model

import java.awt.geom.Point2D
import scala.math.{sqrt}

/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */

final class Sphere(
  val radius : Float
)extends Shape {

  def transform(trans: Transformation) = this

  override val toString = "Sphere[radius= %.4f]" format radius

  def surfacePointFrom(pt : Point2D) : (ShapeReferential, ShapeReferential) = {
    val height = sqrt(
      1.0 -
      pt.getX * pt.getX -
      pt.getY * pt.getY
    ).toFloat
    (
      new Point3D(pt.getX.toFloat, pt.getY.toFloat,  height) with ShapeReferential,
      new Point3D(pt.getX.toFloat, pt.getY.toFloat, -height) with ShapeReferential
    )
  }

  def contains(pt : ShapeReferential) : Boolean =
    sqrt(pt.x * pt.x + pt.y * pt.y + pt.z * pt.z).toFloat <= radius

  override lazy val hashCode = Sphere.getID() + radius.##
}

object Sphere {

  private var counter = 0

  @inline private[Sphere] def getID() = {
    counter += 1
    counter
  }

  val DefaultRadius = 1f

  def apply(radius : Float = DefaultRadius) = new Sphere(radius)

}