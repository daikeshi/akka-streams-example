package utils

import java.text.SimpleDateFormat

import org.json4s.DefaultFormats
import org.json4s.ext.JavaTypesSerializers

object Implicits {
  val default = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  }

  implicit val formats = default ++ org.json4s.ext.JodaTimeSerializers.all ++ JavaTypesSerializers.all
}
