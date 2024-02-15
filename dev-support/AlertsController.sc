package controllers

import javax.inject.Inject
import javax.inject.Singleton
import play.api.db.Database
import play.api.libs.json.{Json, Writes}
import play.api.mvc.*

import java.time.LocalDateTime
import scala.concurrent.Future
import anorm.SqlParser.*
import anorm.*
import models.DatabaseExecutionContext

case class Alert(source: String, message: String, error: String, date: LocalDateTime, machineId: Int)

object Alert {

  var parser: RowParser[Alert] = {
    str("Origem") ~
      str("Mensagem") ~
      str("Erro") ~
      get[LocalDateTime]("ErrorDate") ~
      int("maqId") map {
      case source ~ message ~ error ~ date ~ maqId => Alert(source, message, error, date, maqId)
    }
  }

  implicit val alertWrites: Writes[Alert] = Json.writes[Alert]
}

@Singleton
class AlertController @Inject()(database: Database, ec: DatabaseExecutionContext, val controllerComponents: ControllerComponents) extends BaseController {

  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Future {
      database.withConnection { implicit conn =>
        val errors = SQL("SELECT Origem, Mensagem, Erro, ErrorDate, maqId FROM View_Erros").as(Alert.parser.*)
        Ok(Json.toJson(errors))
      }
    }(ec)
  }
}
