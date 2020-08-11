#Improvements from Phase 1
####By Warren Zhu

There have been massive changes in our project from Phase 1. As such, we have chosen to organize our changes by the Clean Architecture layers.

##Entities

1) Meetings were taken out of the Trade Class and were given their own Class. This better adheres to the Single Responsbility Principle

2) The Trade Entity Class now uses a strategy design pattern, allowing it to be more easily extended. The strategy pattern also has a factory design pattern, making it very easy to add new strategies. This better adheres to the Open-Closed Principle.

3) Entities no longer throw exceptions. As entities are only suppose to represent the critical rules and data of a business, we've decided to move all the exceptions that entities originally threw to the UseCase level. Some entity methods now have preconditions stated in their JavaDocs.


##Use Cases

1) The Use Case

2) Several of the Storage classes now use an Observer Pattern, making the 

##Controllers/Gateways/Presenters

1) In Phase 1, some of our Entity classes were in the Controller Layer. This violates Clean Architecture.







##Other Changes

1) One massive improvement in our program was the fact that we've packaged (i.e., refactored) most of our classes. Entities and UseCase classes related to a specific functionality in the program were placed in a package that highlights their functionality (i.e., trade related classes were placed in trade and meeting related classes were place in meeting). This helps the program be better organized and reduces coupling too.


