package org.blackpanther.three
package model

import _root_.render.SphereRenderer
import java.awt.geom.{Dimension2D, Point2D}
import java.awt.image.BufferedImage
import scala.swing.{Graphics2D, Point, Color}
import render.BoxRenderer

/**
 * @author MACHIZAUD Andréa
 * @version 11/3/11
 */

trait Renderer[T <: Shape] extends (
  (BufferedImage, Dimension2D, FixedReferential, FixedReferential, T, Color) => Unit
) {

  def apply(
    buffer : BufferedImage,
    bufferDimension : Dimension2D,
    pointOfView : FixedReferential,
    shapePosition : FixedReferential,
    model : T,
    modelColor : Color)

}

object Renderer {

  val MaximumPointOfView = 800f

  def forShape[T <: Shape](shape : T) : Renderer[T] = shape match {
    case _ : Box => BoxRenderer.asInstanceOf[Renderer[T]]
    case _ : Sphere => SphereRenderer.asInstanceOf[Renderer[T]]
    case otherwise => sys.error("No renderer available for %s " format shape.getClass)
  }

  def math2pixel(
    bufferDimension : Dimension2D,
    mthPoint: FixedReferential
  ): Point =
    new Point(
      mthPoint.x.toInt,
      (bufferDimension.getHeight - mthPoint.y).toInt
    )

  def pixel2math(
    bufferDimension : Dimension2D,
    scrPoint: Point
  ): Point2D =
    new Point2D.Float(
      scrPoint.x.toFloat,
      (bufferDimension.getHeight - scrPoint.y).toFloat
    )

  def applyPointOfView(
    pov : FixedReferential,
    point : ShapeReferential,
    length : Float
  ) : ShapeReferential = {
    val squeezeRatio : Float =
      (Renderer.MaximumPointOfView - pov.z) /
      (Renderer.MaximumPointOfView - (pov.z * length))

    val realLength  = squeezeRatio * length

    new Point3D(
      point.x * realLength,
      point.y * realLength,
      point.z * realLength
    ) with ShapeReferential
  }

  def changeReferential(
    shapePosition : FixedReferential,
    point : ShapeReferential
  ) : FixedReferential =
    new Point3D(
      point.x + shapePosition.x,
      point.y + shapePosition.y,
      point.z + shapePosition.z
    ) with FixedReferential


  def changeReferential(
    shapePosition : FixedReferential,
    point : FixedReferential
  ) : ShapeReferential =
    new Point3D(
      point.x - shapePosition.x,
      point.y - shapePosition.y,
      point.z - shapePosition.z
    ) with ShapeReferential

}