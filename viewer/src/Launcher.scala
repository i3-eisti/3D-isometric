package org.blackpanther

import swing.SimpleSwingApplication
import ui.Viewer
import three.model.{Point3D, Scene, Box, Sphere}
import java.awt.{Color, Dimension}

/**
 * @author MACHIZAUD Andréa
 * @version 11/3/11
 */

object Launcher extends SimpleSwingApplication {

  val sceneDimension = new Dimension(500, 500)

  val shape0 = Sphere(radius = 30f)
  val shape1 = Sphere(radius = 30f)
  val shape2 = Box(
    width = 30f,
    height = 30f,
    depth = 10f
  )
  val shape3 = Box(
    width = 15f,
    height = 30f,
    depth = 60f
  )
  val shape4 = Sphere(radius = 30f)

  val scene = new Scene(sceneDimension)

  val shapePosition0 = new Point3D(
    sceneDimension.width / 4f,
    sceneDimension.height / 4f,
    -200f
  ).asFixedReferential

  val shapePosition1 = new Point3D(
    sceneDimension.width / 1.25f,
    sceneDimension.height / 4f,
    0f
  ).asFixedReferential

  val shapePosition2 = new Point3D(
    sceneDimension.width / 1.25f,
    sceneDimension.height / 1.25f,
    200f
  ).asFixedReferential

  val shapePosition3 = new Point3D(
    sceneDimension.width / 4f,
    sceneDimension.height / 1.25f,
    0f
  ).asFixedReferential

  val shapePosition5 = new Point3D(
    sceneDimension.width / 2f,
    sceneDimension.height / 2f,
    0f
  ).asFixedReferential

  val shapeColor0 = Color.RED
  val shapeColor1 = Color.BLUE
  val shapeColor2 = Color.GREEN

  scene += ((shape0, (shapePosition0, shapeColor0)))
  scene += ((shape1, (shapePosition2, shapeColor1)))
  scene += ((shape2, (shapePosition1, shapeColor0)))
  scene += ((shape3, (shapePosition3, shapeColor0)))
  scene += ((shape4, (shapePosition5, shapeColor2)))

  scene.render()

  val top = new Viewer(scene)

}