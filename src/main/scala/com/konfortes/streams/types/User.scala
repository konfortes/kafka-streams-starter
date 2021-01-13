package com.konfortes.streams.types

// import io.circe.{Decoder, Encoder, HCursor, Json}

case class User(
    userid: String,
    gender: String,
    regionid: String,
    registertime: Long
)

// TODO: make it work
// object User {
//   implicit val encoder: Encoder[User] = new Encoder[User] {
//     final def apply(user: User): Json = Json.obj(
//       ("userid", Json.fromString(user.userId)),
//       ("gender", Json.fromString(user.gender)),
//       ("regionid", Json.fromString(user.regionId)),
//       ("registertime", Json.fromLong(user.registerTime))
//     )
//   }

//   implicit val decoder: Decoder[User] = new Decoder[User] {
//     final def apply(c: HCursor): Decoder.Result[User] =
//       for {
//         userId <- c.downField("userid").as[String]
//         gender <- c.downField("gender").as[String]
//         regionId <- c.downField("regionid").as[String]
//         registerTime <- c.downField("registertime").as[Long]
//       } yield {
//         new User(userId, gender, regionId, registerTime)
//       }
//   }
// }
