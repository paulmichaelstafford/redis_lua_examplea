# Running and Debugging Lua scripts

A Spring Boot Java application that connects to redis and executes LUA scripts.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* OpenJDK 8 - [Download & Install](https://openjdk.java.net/install/)
* Docker - [Download & Install](https://www.docker.com/get-started)
* Maven - [Download & Install](https://maven.apache.org/install.html)
* Docker-Compose - [Download & Install](https://docs.docker.com/compose/install/)
* Curl - [Download & Install](https://curl.haxx.se/download.html)
* RDM - [Download & install](https://docs.rdm.dev/en/latest/install/)

## Installing

### Setup Database

* Run docker-compose to start a redis

```
$ docker-compose up -d
```

when done make sure to take down the docker container

```
$ docker-compose down
```

## Run Build

#### With test

```
$ ./mvn clean compile test
```

## Serve App on Apache Tomcat

run

```
$ ./mvn spring-boot:run
```

## Deployment

This is a demo project, meant to use locally only.

## Testing

Once the application is up and running, you can test the endpoints by running these commands:

```
$ curl --location --request POST 'localhost:8080/findUniqueRouteIdsNear'  --header 'Content-Type: application/json; charset=utf8' --data-raw '{ "neLatitude": "53", "neLongitude": "6", "swLatitude": "51", "swLongitude": "8" }'
```

```
$  curl --location --request POST 'localhost:8080/findCommonTags'  --header 'Content-Type: application/json; charset=utf8'  --data-raw '{ "routeIds": [1,2] }'
```

## Debugging Lua scripts

Please make sure you have previously started the application or else there won't be any data in redis

Navigate to the project working directory and run either of these commands:

```
redis-cli -n 0 -h localhost -p 6666 -a notsecurepassword --ldb --eval ./src/main/resources/findRouteIdsAndMaxMinPositionByPointNear.lua 6 , 'GPXPointCache' 'point' 52 7 200 'km'
```

```
redis-cli -n 0 -h localhost -p 6666 -a notsecurepassword --ldb --eval ./src/main/resources/intersectOfSets.lua 4 , 'RouteTagCache' 'TagCache' 'tagId' '1,2'
```

Please note, for debug instructions please refer to the Reference Documentation below

## Connecting from RDM

To save to looking for the credentials, when you want to connect to redis, use the following:

```
name: LuaScripts
address:127.0.0.1
password:notsecurepassword
```

### Reference Documentation

For further reference, please consider the following sections:

[Redis commands](https://redis.io/commands)
[Redis Lua Debugging](https://redis.io/topics/ldb)

