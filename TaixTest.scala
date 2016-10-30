package com.cloudera.datascience.taix

import com.esri.core.geometry.Point
import spray.json._

import com.cloudera.datascience.taix.GeoJsonProtocol._

object TaixTest {
  def main(args: Array[String]) {
    val geojson = scala.io.Source.fromURL(getClass.getResource("/nyc-boroughs.geojson")).mkString


    val features = geojson.parseJson.convertTo[FeatureCollection]
    val areaSortedFeatures = features.sortBy(f => {
      val borough = f("boroughCode").convertTo[Int]
      (borough, -f.geometry.area2D())
    })

    areaSortedFeatures.find(f=>{
      f.geometry.contains(new Point("-73.96682".toDouble, "40.770138".toDouble))
    }).foreach(println)

  }
}
