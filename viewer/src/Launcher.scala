package org.blackpanther

import swing.SimpleSwingApplication
import ui.Viewer
import org.blackpanther.three.model.{Point3D, Scene, Box, Sphere}
import java.awt.{Color, Dimension}

/**
 * @author MACHIZAUD Andréa
 * @version 11/3/11
 */

object Launcher extends SimpleSwingApplication {

  val sceneDimension = new Dimension(500, 500)

  val side = 100f
  val shape = Box(side, side, side)
  val shape0 = Sphere(side)

  val scene = new Scene(sceneDimension)

  val shapePosition = new Point3D(
    sceneDimension.width / 2f,
    sceneDimension.height   / 2f,
    0f
  ).asFixedReferential

  val shapePosition0 = new Point3D(
    sceneDimension.width / 4f,
    sceneDimension.height / 4f,
    0f
  ).asFixedReferential

  val shapeColor = Color.RED
  val shapeColor0 = Color.RED

  scene += ((shape0, (shapePosition, shapeColor)))
//  scene += ((shape0, (shapePosition0, shapeColor0)))

  scene.render()

  val top = new Viewer(scene)

}