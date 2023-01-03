# Timetracker Project

## Warum Quarkus?

Quarkus wurde von Grund auf so entwickelt, dass es von Anfang an "Cloud-native" ist.
<br> Vorallem schneidet Quarkus besser ab in Startzeit, Speicherverbrauch etc als die Alternativen Frameworks.
<br>Quarkus kombiniert den traditionellen imperativen Kodierungsstil mit dem Cloud-nativen, reaktiven Kodierungsstil.

## Dependencies

- <b>RESTEasy Reactive</b> (basierend auf Vert.x. Es nutzt die reaktiven, nicht-blockierenden Fähigkeiten von Quarkus, die die Gesamtleistung der Anwendung verbessern.)
- <b>Quarkus ArC</b> (Injection-lösung)
- <b>quarkus-hibernate-reactive-panache</b> (Hibernate ist ein ORM-Bibliothek und stellt uns JPA zur verfügung; Panache ist eine von Quarkus-spezifiesche vereinfachte Version von Hibernate die es uns erlaubt JPA Entitäten mit CRUD - Operationen auszuführen ohne einen Boilerplate-Code zu haben)
- <b>quarkus-reactive-mysql-client</b>
- <b>quarkus-resteasy-reactive-jackson</b> (die Serialisierungsunterstützung für RESTEasy Reactive unter Verwendung der Jackson-Bibliothek; Jackson ist eine der beliebtesten Java-Bibliotheken, die vor allem für ihre Serialisierungs- und Deserialisierungsfunktionen im JSON-Datenformat bekannt ist. )

## Packaging the application

The final step to being able to distribute and run the application would be to package it. Besides the native mode, which we already analyzed in the Profiles section, Quarkus offers the following package types:
- fast-jar: This is the default packaging mode. It creates a highly optimized runner JAR package, along with a directory and its dependencies.
- uber-jar: This mode will generate a fat JAR containing all of the required dependencies. This JAR package is suitable for distribution of the application on its own.
- native: This mode uses GraalVM to package your application into a single native binary executable file for your platform.
- native-sources: This type is intended for advanced users. It generates the files that will be needed by GraalVM to create the native image binary. It’s like the native packaging type but stops before triggering the actual GraalVM invocation. This allows performing the GraalVM invocation in a separate step, which might be useful for CI/CD pipelines.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/timetracker-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
