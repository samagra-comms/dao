# Spring Example Reactive Cassandra Repository

Example using spring boot, and spring data reactive cassandra repository

## Getting Started

### Setup cassandra
1. Get cassandra image
* docker pull cassandra

2. start cassandra node
* docker run -it --rm --name cassandra-node -p7000:7000 -p7001:7001 -p9042:9042 -p9160:9160 cassandra

### Run client
* build using mvn clean install -U
* run spring boot application

### Prerequisites

* java 8
* docker
* cassandra
* lombok plugin for IDE
* maven