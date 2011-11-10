package org.blackpanther.three
package model

import java.awt.geom.{Dimension2D, Point2D}
import java.awt.image.BufferedImage
import scala.swing.{Point, Color}
import render.{SpringRenderer, BoxRenderer, SphereRenderer}
import _root_.org.blackpanther.three.shapes.{Sphere, Box, Spring}

/**
 * @author MACHIZAUD Andréa
 * @version 11/3/11
 */

trait Renderer[T <: Shape] extends (
  (BufferedImage, FixedReferential, FixedReferential, T, Color) => Unit
) {

  def apply(
    buffer : BufferedImage,
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
    case _ : Spring => SpringRenderer.asInstanceOf[Renderer[T]]
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