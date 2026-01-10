# Docker host bridge

### Content
* [Description](#description)
* [Accessing docker from host](#accessing-docker-from-host)
* [Accessing host from docker](#accessing-host-from-docker)
* [Project setup](#project-setup)
* [Running on MacOS](#running-on-macos)

### Description
The main goal of this project is show different type of communication between docker and host. There are 2 main scenarios. First you may like to access apps running inside docker from the host. The second scenario is that app running inside the docker needs to access the app running on host. But before jumping with more details and examples into these 2, let's first talk about 2 primary network modes inside docker:
* bridge (default) - this mode is set by default if you don't set anything explicitly. Docker running inside private network, and by default access to/from is closed. To access container from outside you have to add explicit port mapping. To address host from inside you can't use `localhost`, because it would point to inside the private network, you have to use `host.docker.internal`
* host - this is the simplest and widely used for development mode. It runs the docker instances inside the host. In this case you can easily access to/from without any additional settings. The only caveat here is that it works only on Linux. It doesn't fully supported by MasOS/Windows.

### Accessing docker from host
This is the most common one and probably the simplest one. Let's consider 2 network modes:
* bridge - you have to explicitly open ports with this command: `ports: HOST:CONTAINER`
* host - you don't need anything else, just access by `127.0.0.1:PORT`

### Accessing host from docker
Let's consider 2 types of network mode:
* bridge - you have to access the host using `host.docker.internal`
* host - you don't need anything else, just access by `127.0.0.1:PORT`. Unfortunately it works only on Linux, I've checked, and it doesn't work for MacOS/Windows.

### Project setup
There are 3 ports:
* 5555 - port of the host app
* 4444 - port of the docker app (available only form inside)
* 6666 - port mapped from docker to host (available on the host)

Host app (run on host):
```shell
# get status on host
curl -H "Content-Type: application/json" http://127.0.0.1:5555/api/info
# call docker from inside the host
curl -X POST -H "Content-Type: application/json" http://127.0.0.1:5555/api/call
```

Docker app (connect to docker and run from inside):
```shell
# get status on docker
curl -H "Content-Type: application/json" http://127.0.0.1:4444/api/info
# call host from inside the docker
curl -X POST -H "Content-Type: application/json" http://127.0.0.1:4444/api/call
```

Build docker container:
```shell
# build project
mvn clean package
# build docker image
docker build -t docker-app .
# run from docker-compose
docker-compose -f docker-compose.yml up -d
# connect to inside of container (you can run curl inside)
docker-compose exec docker-app sh
```

### Running on MacOS
Running with `bridge` network mode works perfectly fine. But switching to host is not properly working:
* docker on MacOS runs inside a lightweight Linux VM (HyperKit)
* `network_mode: host` on Linux means the container shares host network
* But on MacOS, the container would only share the VM’s network stack, not your Mac’s network, so localhost still points to the VM