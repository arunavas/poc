name := "poc"

version := "1.0"

scalaVersion := "2.11.8"

//crossScalaVersions := Seq("2.12.0", "2.11.8")

mainClass in Compile := Some("poc.frameworks.Main")

libraryDependencies ++= Seq(
  "org.http4s"          %      "http4s-dsl_2.11"            %   "0.14.6",
  "org.http4s"          %      "http4s-blaze-server_2.11"   %   "0.14.6",
  "org.http4s"          %      "http4s-blaze-client_2.11"   %   "0.14.6",
  "net.liftweb"         %      "lift-json_2.11"             %   "2.6",
  "org.scalatest"       %%      "scalatest"                 % "3.0.0"     % "test"
)