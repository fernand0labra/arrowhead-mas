# Arrowhead Application Library (Java Spring-Boot)
##### The project provides an application development library for the Eclipse Arrowhead Framework

Arrowhead Application Library contains all the common Arrowhead dependencies and data transfer objects and also an 'ArrowheadService' with the purpose of easily interacting with the framework. The library is built on top of Spring Boot which makes it easy to create stand-alone, production-grade application systems with embedded web server environment that you can **"just run"**.

### Requirements

The project has the following dependencies:
* JRE/JDK 11 [Download from here](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
* Maven 3.5+ [Download from here](http://maven.apache.org/download.cgi) | [Install guide](https://www.baeldung.com/install-maven-on-windows-linux-mac)

### How to use it?

The library is available via the Maven Central Repository.

In case of a maven project copy the following into the `<dependencies>` element in your project `pom.xml` file.

```xml
<dependency>
   <groupId>ai.aitia</groupId>
   <artifactId>arrowhead-application-library-java-spring</artifactId>
   <version>4.4.0.2</version>
</dependency>
```
Or look for other Maven Central based solutions such as Gradle, Scala SBT, Apache Ivy and so on. 

### `Boost your project by the:` [Arrowhead Application Skeletons](https://github.com/arrowhead-f/application-skeleton-java-spring)
