import org.apache.commons.codec.digest.HmacUtils.hmacSha256Hex
import play.api.Application

object SignData {
  def sign(d: (String, String)*)(implicit app: Application): String = {
    val text = d.map {
      case (k, v) => s"$k=$v"
    }.mkString("&")

    val secret = app.configuration.getString("sd.pay.sfs1pay.secret").get
    hmacSha256Hex(secret, text)
  }

  @inline def apply(d: (String, String)*)(implicit app: Application): Seq[(String, String)] =
    d :+ ("signature" -> sign(d: _*))
}