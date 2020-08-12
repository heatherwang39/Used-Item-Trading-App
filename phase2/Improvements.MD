#Improvements from Phase 1
####By Warren Zhu, Robbert Liu

There have been massive changes in our project from Phase 1. As such, we have chosen to organize our changes by the Clean Architecture layers.

##Entities

1) Meetings were taken out of the Trade Class and were given their own Class. This better adheres to the Single Responsbility Principle.

2) The Trade Entity Class now uses a strategy design pattern, allowing it to be more easily extended. The strategy pattern also has a factory design pattern, making it very easy to add new strategies. This better adheres to the Open-Closed Principle.

3) Entities no longer throw exceptions. As entities are only suppose to represent the critical rules and data of a business, we've decided to move all the exceptions that entities originally threw to the UseCase level. Some entity methods now have preconditions stated in their JavaDocs.

4) Entities now incorporate more elements of polymorphism and abstraction. An example of such is the abstract class Account, which has subclasses such as LoginAccount, UserAccount, and AdminAccount.


##Use Cases

1) Use case classes no longer call Gateway-type methods, such as saving or getting data.

2) Several of the Storage classes now use an Observer Pattern.

3) The Storage classes themselves have a Factory (encapsulating construction of the Storage classes), and a Depot (which encapsulates the Observer pattern)



##Controllers/Gateways/Presenters

1) In Phase 1, some of our Entity classes were in the Controller Layer. This violates Clean Architecture. We've now completely removed Entities from the Controller Layer.

2) There is a single observer Gateway-type class tasked with the file reading and writing

3) There are dedicated Presenter classes other than just Controller classes with Presenter like methods.


##Other Changes

1) One massive improvement in our program was the fact that we've packaged (i.e., refactored) most of our classes. Entities and UseCase classes related to a specific functionality in the program were placed in a package that highlights their functionality (i.e., trade related classes were placed in trade and meeting related classes were place in meeting). This helps the program be better organized and reduces coupling too.


