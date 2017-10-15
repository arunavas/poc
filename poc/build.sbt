name := "poc"

version := "1.0"

scalaVersion := "2.11.11"

//crossScalaVersions := Seq("2.12.0", "2.11.8")

mainClass in Compile := Some("poc.frameworks.Main")

libraryDependencies ++= Seq(
  "org.http4s"          %%      "http4s-dsl"            %   "0.14.6",
  "org.http4s"          %%      "http4s-blaze-server"   %   "0.14.6",
  "org.http4s"          %%      "http4s-blaze-client"   %   "0.14.6",
  "net.liftweb"         %%      "lift-json"             %   "2.6",
  "org.scalaz"          %%      "scalaz-core"           %   "7.1.9",
  "org.scalatest"       %%      "scalatest"             %   "3.0.0"     % "test"
)