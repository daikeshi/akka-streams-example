logLevel := Level.Warn

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "2.2.9")