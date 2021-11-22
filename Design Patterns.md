# Design Patterns

Below is a list of design patterns that have been used for phase 2 of the project. 

Instances of each pattern, as well as explanations for why these patterns have been chosen and how they work have been included under the header of each pattern.

(The list is sorted by alphabetical order for ease of navigation)




## Factory:

####StatusFactory
#####(By Robbert Liu)

The constructor of every Status is identical, and I wanted an extensible way to lower the dependency between the Status constructor and StatusStorage, which only needs to access Stati after they are created.

####StorageFactory
#####(By Robbert Liu)

StorageFactory is a Factory class that creates Storages. It's little more than a facade for the data retrieval that happens inside StorageGateway, but it fits the pattern nonetheless.

####TradeAlgorithm/TradeAlgorithmFactory
#####(By Warren Zhu)

*Please refer to "Trade/TradeAlgorithm" under the Strategy Subheading to better understand TradeAlgorithms*


######Classes involved:

TradeAlgorithm (Interface for the Product)

CycleTradeAlgorithm (Concrete Product)

RandomTradeAlgorithm (Concrete Product)

TradeAlgorithmFactory (Creator)

TradeAlgorithmName

######Details:

As the program grows, a variety of TradeAlgorithms will very likely come into existence. As such, a TradeAlgorithmFactory has been implemented to make it easier to add new TradeAlgorithms.

The implementation is quite simple--we placed all TradeAlgorithm constructors inside of the TradeAlgorithmFactory. We then placed the factory inside of TradeStorage. Thus, when TradeStorage is given the name of a TradeAlgorithm, it can go to the TradeAlgorithmFactory to retrieve one such TradeAlgorithm when initializing a new Trade. (Our TradeAlgorithmName--which is an enum file--tells us what type of TradeAlgorithms can be created.)

This design makes it particularly easy to add new TradeAlgorithms--all we need to do is to write code for the TradeAlgorithm, and then add its constructor into the factory and the enum file!




## Strategy:

####Trade/TradeAlgorithm
#####(By Warren Zhu)

######Classes involved:

TradeAlgorithm (Strategy)

CycleTradeAlgorithm (Concrete Strategy)

RandomTradeAlgorithm (Concrete Strategy)

Trade (Context)

######Details:

Trade now implements a strategy pattern. In this case, the strategy is TradeAlgorithm, which is an algorithm that determines which items will be sent to who in the trade. My thought process for the design is below.

A trade, in essence, is an exchange of items. Thus, to understand what happens in a trade, one simply has to know who owned which item right before and right after the Trade. Furthermore, every trade depends on its pre-trade owners, whilst the post-trade owners depends on the exchange in the Trade. If one continues down this line of thought, one will realize that each trade is, in essence, a mutation of the item's original owners, and as such, each trade is really just an algorithm.

It was with this realization that I finally understood that the Strategy is the perfect design pattern for Trade. Ergo, I made each Trade require a Trade Algorithm in order to be initialized. Each Trade Algorithm is able to do two things:

1) When given the initial state of the items (i.e., who owns what), it is able to return a list of what happens after the exchange.

2) It is able to return the name of the algorithm, allowing for each Trade to identity itself as a certain type.

In order to further encapsulate our design, the TradeAlgorithms were given their own Factory. *Please refer to "TradeAlgorithm/TradeAlgorithmFactory" under the Factory Subheading for further reading*

The benefits of this design pattern are quite profound--it allows the program to be easily extended, as new types of trades will only vary in the distribution of items. We've already coded Trades so that as many traders can participate in a Trade as the client deems fit, and now with the Strategy Design Pattern, the behaviour of each Trade can easily be modified--if someone wants to design a new kind of Trade, all they need to do is to add a new Trade Algorithm.




## Observer:

####MeetingStorage/TradeStorage
#####(By Warren Zhu)

######Classes involved:

MeetingObservee (Subject)

MeetingObserver (Observer)

MeetingStorage (Concrete Subject)

TradeStorage (Concrete Observer)

######Details:

Trade Storage and Meeting Storage uses an Observer Pattern, where MeetingStorage is the Subject and TradeStorage is the Observer. My thought process on the design is below:

Only trades depend on meetings, yet meetings do not depend on any class (not even trades). In fact, the statuses of some Trades are dependent on the statuses of meetings! As MeetingStorage is the Use Case class that is responsible for managing meetings, we've made it so that when you confirm a meeting, it notifies the TradeStorage, which goes through all its Trades to see if there is a Trade with the given Meeting. Then the TradeStorage class acts accordingly:

1) If the first Meeting for a Trade has been confirmed, the TradeStorage class will update the corresponding Trade to record the fact the items have changed hands. This prevents the Trade from being cancelled.

2) If the final Meeting for a Trade has been confirmed, the TradeStorage class will update the corresponding Trade to record the fact the Trade is now finished.

This design pattern allows for TradeStorage and MeetingStorage to be abstract (i.e., coupling is reduced between the two classes).

####StorageGateway
#####(By Robbert Liu)

Originally the use case classes called the gateway method directly that would save its serialized data, and ever use case class had its own gateway method. Now, there's one gateway class, and the Observee, a Controller class, calls StorageGateway.saveStorageData whenever something needs to be saved.

####TradeStorage/ItemStorage
#####(By Warren Zhu)

######Classes involved:

TradeObservee (Subject)

TradeObserver (Observer)

TradeStorage (Concrete Subject)

ItemStorage (Concrete Observer)

######Details:

Once a Trade has been completed, TradeStorage will automatically notify its observers (in this case, ItemStorage) that a given Trade has been completed. In this particularly instance, ItemStorage will update the Items' owners according. Note that this Observer pattern works in tandem with the Observer pattern for TradeStorage and MeetingStorage--if the final meeting of a Trade has been completed, the Trade will then be marked as complete, which tells ItemStorage to update the owners accordingly.