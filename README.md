## Prerequisites
* Docker: https://docs.docker.com/get-docker/

## Running Kafka as a Docker container
* Run `docker-compose up` in order to start Zookeeper and Kafka within docker container.

## Native image properties setup

<code>Args= -H:IncludeResources=logback.xml|application.yml \
      -H:Name=mn-pricing \
      -H:Class=com.danielprinz.udemy.Application \
      --report-unsupported-elements-at-runtime \
      --initialize-at-run-time=org.apache.kafka.common.security.authenticator.SaslClientAuthenticator</code>

Some classes have to be initialized during an image runtime. That can be achieved by using the following option: `--initialize-at-run-time=<class-name>`

## Building native image with gradle
* When building `nativeImage` with gradle we have to be careful with how we are setting up dependencies e.g. if some test 
dependencies are problematic we have to replace the deprecated `testCompile` with `testImplementation`.
* Once everything configured, we can create a native image in the following way:
_**$ ./gradlew nativeImage   (or gradle nativeImage)**_

## Running native-image
If native-image is successfully created, it can be launched by simply running the following command:
_**.\build\native-image\application.exe**_

