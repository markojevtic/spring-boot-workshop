# The Idea project 

Let's imagine that we have a customer, that want to implement a small application for sharing ideas. And the customer requirement sounds like:

    As user I want to have a RESTful application that manipulates and serves the idea data to various clients( web, android, iphone). 
    Users should have possibilities to create, read, update, delete an idea. 
    The idea should contains basic information( title, description, category[TECH, CASUAL, OTHER], …). 
    Also users should be able to vote for an idea, and discuss about ideas(write comments).

In order to provide all those functionality we will do it trough implementing further stories.

## Stories

#### [1] Setup application
Create spring boot application skeleton, with mongoDB and HATEOAS dependencies. 
Because of easier communication I suggest that we should follow some conventions, it's not necessary but will save our time.
* package organisation firstly by features than by layers, i.e.:
    * `com.prodyna` - main package
        * `idea` - the idea future package 
            * `domain` - repository layer
                * `model` - entities
                * `impl` - repository implementation
            * `services` - service layer
                * `impl`
            *  `web` - controllers
                * `dto` - dto
                * `converter` - converters Dto to entity and                   
* Class naming:
    * Entities, just name of entity without any suffix( i.e. `Idea, User, ...` )
    * Repository components with suffix Repository ( i.e. `IdeaRepository` )
    * Service component with suffix Service ( i.e. `IdeaService` )
    * Rest controllers with suffix Resource ( i.e. `IdeaResource` )
    * DTO classes with suffix Dto (i.e. `IdeaDto` )
    * Unit test with suffix UnitTest ( i.e. `IdeaServiceImplUnitTest` )
    * Component test with suffix ComponentTest ( i.e. `IdeaServiceImplComponentTest` )
    
#### [2] Idea RESTFull API
Create all necessary entities and dtos, controllers, converters, etc. In this phrase we should provide only skeleton for service layer, 
and we should not implement repository layer. Ensure that controller returns proper HTTP status. Write unit test. 
( Create and Load ) - on workshop, other operations at home.

#### [3] Idea Service and Repository layer
Implement service and repository to support CRUD operation for Ideas. First implement create and load operations( other operations as homework). 
Write component test to prove that service works as it is expected.

#### [4] Adding comments to existing Idea 

Implement everything what is necessary to support adding comments to an existing Idea. 
Comments in this iteration should contains only 2 fields( comment text, and creation date ). Write necessary tests. 
