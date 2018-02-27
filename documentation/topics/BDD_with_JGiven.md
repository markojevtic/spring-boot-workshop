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
            testCompile 'com.tngtech.jgiven:jgiven-junit:0.15.3’ 
        }
        ``` 
* In case of srping application you we need to add @EnableJGiven in test configuration classes
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
                testCompile 'com.tngtech.jgiven:jgiven-spring:0.15.3’ 
    }
    ```
    