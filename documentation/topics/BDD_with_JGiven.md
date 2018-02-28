# Behaviour-Driver Development with JGiven

## Why do we should write tests

Many young and less experienced developers think that writing test is waste of time, 
and unnecessary work. But everyone who has experience with a big project and project that lasts long time, 
does not share that opinion. First, with a test you assure that your code work as it expected with given inputs,
and second every later change of your code will be easier and safer because tests will warm developer if hi/she breaks 
functionality. 

Many times I've heard: "My code works, I don't need to test it.",  let's suppose that is true, and put yourself in position 
that you have to add new functionality in an existing complex code, how and that code is written by someone with the same thinking. 
If there are no tests, how you will know that you don't break existing functionality. Literally you will hate that person. 
Or just imagine a pilot, that is overconfident and doesn't do all necessary checking, why to waste time, and repeat all procedures. 
Will you fly with him? I'm sure that answer is "No, he isn't professional". Is the same with the programing, why someone should hire 
a developer who is not professional.

##  What is Behaviour-Driver Development

Behaviour-Driver Development(BDD) is an agile software development process that enhance collaboration 
in a software project between developers, QAs, managers and business. Also BDD can be seen as set of 
best for practices for using common(ubiquitous) language by technical and business teams. Therefore it should be 
accepted by both sides. And at least it brings following benefits for both sides:
* Clean and transparent collaboration - by using the common language understood by all participants in project we reduce 
misconceptions and misunderstanding. Technical teams has better understanding what should be done, on other hand business teams have better overview about progress.
* Software design follows business values - BDD puts accent on business values and process, and it focuses on the business behaviour more than on implementation itself.
* Developer confidence - team that use BDD are more confident that understand requirements, and because of the same language in test, team is sure that it implemented story correctly. 

#### Writing user stories
Every development of a feature start with the feature specification. And because of that, in BDD is very important that feature user stories 
are written in ubiquitous language and they have following structure:
###### Title: The story should have a clear, explicit title.
###### Narrative
A short description, that contains:
* __Who__ - the actor who derives business benefit from the story.
* __What__ - effect the stakeholder wants the story have.
* __Why__ - business value the stakeholder will derive from this effect.  
###### Acceptance criteria
A description of each specific case of the narrative. Such a scenario has the following structure:
* __Given__ - the initial condition that is assumed to be true at the beginning of the scenario.
* __When__ - events trigger the start of the scenario.
* __Then__ - expected outcome, in one or more clauses.

Story example:
<pre>
<b>Title</b>: Premium customer can preorder a book before official release

<b>Description</b>:
    As a book seller,
    In order to improve user loyalty program
    I want to allow premium user to be able to order books 30 days earlier.
  
<b>Acceptance criteria</b>

    <b>A.</b> Premium user can order book earlier
        <b>Given</b> a book with id
            With official release date in 30 days
            And a premium customer
        <b>When</b> customer orders book
        <b>Then</b> customer library contains book with id
        
    <b>B.</b>Premium user can not order book that will be released in more than 30 days.
        <b>Given</b> a book with id
            With official release date in 31 days
            And a premium customer
        <b>When</b> customer orders book
        <b>Then</b> customer gets error message     
        
    <b>C.</b>Standard user can not order book before official release
        <b>Given</b> a book with id
            With official release date in 15 days
            And a regular customer
        <b>When</b> customer orders book
        <b>Then</b> customer gets error message
</pre>

#### BDD on technical teams side
For technical teams, BDD tells teams how to write tests which show that writen code
works in all scenario cases as it's expected. Test should express it in a natural language,
that non-technical person can understand it. It should be close to acceptance criteria from story as
it is possible. To achieve that, developers use many different testing framework(i.e. Cucumber, JBehave, etc).
On the last project I've used the JGive, which is not so popular, but it's very good for Java developers,
and I would recommend it because of the following:
* write scenarios in plane Java using fluent API
* small and easy to learn
* generates reports readable by business people in various formats(text, json, html)
* open source, it's mainly developed by German company TNG, but it's open source an every one can join development
* easy integration in JavaEE, Spring, Selenium and Android(experimental status)

##### Get start with JGiven
To enable JGiven in our project we have to include just JGiven dependency:
* JUnit tests:
    * Maven:
        ```
        <dependency>
            <groupId>com.tngtech.jgiven</groupId>
            <artifactId>jgiven-junit</artifactId>
            <version>0.15.3</version>
            <scope>test</scope>
        </dependency> 
        ```
    * Gradle:
        ```
        dependencies { 
            testCompile 'com.tngtech.jgiven:jgiven-junit:0.15.3'
        }
        ``` 
* In case of a Spring application you we need to add ``@EnableJGiven`` in the test configuration classes
and include following dependencies
    * Maven
    ```
    <dependency>
        <groupId>com.tngtech.jgiven</groupId>
        <artifactId>jgiven-spring</artifactId>
        <version>0.15.3</version>
        <scope>test</scope>
    </dependency> 
    ```    
    * Gradle:
    ```
    dependencies { 
                testCompile 'com.tngtech.jgiven:jgiven-spring:0.15.3' 
    }
    ```
    
##### Write JGiven test
Every JGiven test are build from the following elements:
* Test class with test methods, every method represent one scenario case.
* Given classes with methods that create initial conditions.
* When classes with methods that execute scenario.
* Then classes with methods that checks conditions after execution of scenario.

__Given, When, Then__ stages are reusable and they could be used in more than one test. That is one additional 
benefit of using JGiven tests.
###### Create Given classes
As it already mentioned, Given class should provide methods that create initial system condition. 
Every Given class should extend ``Stage<SELF>``. To make clear how to write Given class, let's write
the given class for the bookstore example.
```Java
public class GivenBook extends Stage<GivenBook> {
    @ProvidedScenarioState
    protected Book book;
    
    @AfterStage
    private void persistBookInDb() { 
        BookDao.save(book);
    }

    @AfterScenario
    private void cleanUpDb() {
        BookDao.delete(book);    
    }
    public SELF a_book_with_id( String id ) {
        book = new Book(id);
        return self();    
    }

    public SELF official_release_in_$_days( int days) {        
        book.releaseDate( today.add(days) );
        return self();    
    }
}
```
All class fields that we create in this stage, and we want to use in another stages(given,when,then),
we must mark with ``@ProvidedScenarioState``. In that way we say to JGiven, to pickup this property,
and inject it in another stages. Every stage could have method that is executed just after the stage completion,
this method must be mark ``@AfterStage``. Usually we use this method to persists stage objects into DB. 
Also every stage could have a method that is executed after the scenario completion, it is marked by ```@AfterScenario```. This method we use to clean up
system/db. And every stage should contain at least on stage method. Naming stage methods, doesn't follow java convention,
but that is ok, because every method call later will be converted in a text segment in test report. One more important thing: 
every method should return self() in order to allow fluent api.

###### Create When classes
Writing a When classes is almost identical to Given stage, but stage methods should execute scenario(calls service,
rest, do some scenario actions). Let's write when stage for our example:
```java
public class WhenOrderService extends Stage<WhenOrderService> {
    @ExpectedScenarioState    
    protected Book book;
    @ExpectedScenarioState
    protected Customer customer; 

    protected OrderService orderService;
    
    @BeforeStage
    private void init() {
        orderService = new OrderService();
    }
    
    public WhenOrderService the_customer_order_the_book() {
        orderService.order(customer, book); 
        return self();    
    }
}
```
Here we have fields ``Book book`` and ``Customer customer``, both fields are marked with ``@ExpectedScenarioState``.
This annotation tells to JGiven framework to populate those fields(similar to ```@Inject```) with values that are 
provided/created(``@ProvidedScenarioState``) in some previous stages. Here we have one annotated method ``@BeforeStage``,
this method will be executed first, similar to JUnit ``@Before``, usually we initialize some class property, in this case
our ``OrderService``.  

###### Create Then classes
Then class stages should do assertion of system, and writing these classes are very similar to Given and When.
Let's write then stage for our example:
```java
public class ThenLibrary extends Stage<ThenLibrary> {
    @ExpectedScenarioState
    protected Customer customer; 
    
    public ThenLibrary the_library_contains_book_with_id(String bookId) {
        assertThat( LibraryDao.exists(customer, bookId) ).isTrue();	
        return self();
    }
}
```
###### Create Test classes
Every test class extends ``ScenarioTest<GIVEN,WHEN,THEN>``. By extended this class, the class inherits methods: 
given(), when(), then() that represent default test stages. Test class should contain methods, each method test
one scenario case. Method name of class should be similar to title of scenario case from acceptance criteria.
To make things more clear, lets analyze test class for our example:
```java
public class PreOrderBookScenarioTest extends ScenarioTest<GivenBook, WhenOrder, ThenLibrary> {
    @ScenarioStage
    private GivenCustomer givenCustomer;

    @Test @Story("Story-555333") 
    public void premium_customer_can_order_book_earlier() {
        final String bookId = "123";
        
        given().a_book_with_id( bookId )
            .with().official_release_in_$_days(30);
        givenCustomer.and().a_premium_customer();

        when().the_customer_order_the_book();

        then().the_library_contains_book_with_id(bookId);
    }
    
    //Implementation of 2 other AC cases...
}
``` 
As it's mentioned earlier every test has three default stages in our cases: GivenBook, WhenOrder, ThenLibrary. 
But in many cases, these three stages are not enough for write all scenarios. Because of that JGiven provide
as ``@ScenarioStage``, which works similar like Spring ``@Autowire`` or ``@Inject``, and set stage property. In our case
we suppose that we have implemented GivenCustomer already. JGiven will set that property, and we can use it in test.
In test method, we are calling first methods of given stages in order to crate initial conditions, then we call methods 
of when stages and last methods of then stage. If you take a look to code of test method, code is very similar to
AC from story and it's easy to read and detect why test fails if it fails. It's worth to mention that we can have
more than one when, and more than one then stage. Let's imagine that in our case we want to check that an email 
notification has been sent to customer, we would introduce i.e. ThanEmail stage.

Also in the example we use custom ``@Story`` that extends ``@IsTag`` annotation, it allow us to do easy 
categorization of test. It could be very useful in reports. We will talk about it in the next section. 

#### Reporting
Reporting are very important for both sides, technical and business. Technical teams, will use report 
during development, and later to find issues if they exist. By default JGiven generates plain text report, 
and it is shown in console(of ide). In our example if we run method written above, in console of our IDE we will 
get following result:
```
Scenario: premium customer can order book earlier
    Given a book with id 123
        With official release in 30 days
        And a premium customer
    When the customer order the book
    Then the library contains book with id 123
``` 

Beside the plain text reports, JGiven has possibility to generate HTML reports. This kind of reports
is very useful because it could be integrated with an CLI(i.e. Jenkins). And business teams, can easy 
track progress and status of project. To easier tracking of story, use cases, etc JGiven provide us 
annotation ``@IsTag``, that allows easier classification of test by story, use case, and searching reports
by them. Here is an screen shoot of HTML reports:

````



````
An example of HTML reports you can find on JGiven site. 

### What is the next  