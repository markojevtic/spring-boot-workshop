# spring-boot-workshop

### About repository
The repository is a collection of materials for spring boot workshop. It contains a few maven projects with code examples, training documentation and docker configuration.

### Preparation for the training

#### Required knowledge
Before then we start with training, everyone has to be familiar with the following things:
* Java 8 - especially basic of stream API and lambdas. 
* Concept of JUnit with Mockito testing.
* Maven 
* Git

#### Development environment
##### JDK & IDE
As it’s mentioned above  we will use Java 8 and Lombok, than you need following:
* JDK 8 - installed on your machine
* IntelliJ/Eclipse/Netbeans - one of them, your favorite. 
* Lombok plugin -  you have to install lombok plugin, it’s straightforward.
* Git/SourceTree/etc - choose one git client that you prefer.
##### DBs
This training is going to cover usage spring data/cache with MongoDB and Redis, therefore we will need these databases installed. 
Since Redis doesn’t have official support for windows, we are going to use Docker. In order to setup dev environment you should do next steps:
* Install Docker
    * Windows 10/Mac/Linux - it should be straightforward
    * Windows 7 - you have to use docker toolbox, please read carefully instruction on Docker site.
* Configure Docker/VirtualBox(Windows 7 only!) - because you run docker inside of VirtualBox, you have to manually configure port redirection. 
Default docker ip is 192.168.99.100, but sometimes docker run on a different address, please check it after the run [see](documentation/images/docker_toolbox.png).
    
    ![Right click](documentation/images/VirtualBoxSettings.png) 
    ![Button port forwarding](documentation/images/VirtualBoxAdvanceSettings.png) 
    ![Mongo&Redis ports](documentation/images/VirtualBoxPortForwardin.png)
* Run Docker
* Clone this project to your machine
* goto `[PROJECT_DIR]/docker` 
* start containers by running following commands:
    ```
    $./start.sh
    $ docker ps
    CONTAINER ID        IMAGE               COMMAND                  CREATED                  STATUS              PORTS                      NAMES
    90efa77cff81        mongo:latest        "docker-entrypoint..."   Less than a second ago   Up 8 seconds        0.0.0.0:27017->27017/tcp   pd-mongo
    17cd03e9d433        redis:alpine        "docker-entrypoint..."   Less than a second ago   Up 8 seconds        0.0.0.0:6379->6379/tcp     pd-redis
    ```
* Import the project `spring-boot-workshop` into your IDE
* Run test `TestEnvApplicationTests.java`, it should be green.
* If you have any problem don't hesitate to ask for help :)

###### Optional tools
gtiThe following tools are very useful, but it's not required for this workshop:
* [RoboMongo](https://robomongo.org) - MongoDB GUI client
* [RedisDesktopManager](https://redisdesktop.com/) - Redis GUI client.