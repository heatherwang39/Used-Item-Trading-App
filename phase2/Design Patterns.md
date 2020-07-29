# Design Patterns

Below is a list of design patterns that have been used for phase 2 of the project. 

Instances of each pattern, as well as explanations for why these patterns have been chosen and how they work have been included under the header of each pattern.

(The list is sorted by alphabetical order for ease of navigation)

## Factory:

## Strategy:

####TradeAlgorithm
#####(By Warren Zhu)

Trade now implements a strategy pattern. In this case, the strategy is TradeAlgorithm, which is an algorithm that determines which items will be sent to who in the trade. My thought process for the design is below.

A trade, in essence, is an exchange of items. Thus, to understand what happens in a trade, one simply has to know who owned which item right before and right after the Trade. Furthermore, every trade depends on its pre-trade owners, whilst the post-trade owners depends on the exchange in the Trade. If one continues down this line of thought, one will realize that each trade is, in essence, a mutation of the item's original owners, and as such, each trade is really just an algorithm.

It was with this realization that I finally understood that the Strategy is the perfect design pattern for Trade. Ergo, I made each Trade require a Trade Algorithm in order to be initialized. Each Trade Algorithm is able to do two things:

1) When given the initial state of the items (i.e., who owns what), it is able to return a list of what happens after the exchange.

2) It is able to return the name of the algorithm, allowing for each Trade to identity itself as a certain type.

In order to further encapsulate our design, the TradeAlgorithms were given their own Factory. *Please examine "TradeAlgorithmFactory" under the Factory Subheading*

The benefits of this design pattern are quite profound--it allows the program to be easily extended, as new types of trades will only vary in the distribution of items. We've already coded Trades so that as many traders can participate in a Trade as the client deems fit, and now with the Strategy Design Pattern, the behaviour of each Trade can easily be modified--if someone wants to design a new kind of Trade, all they need to do is to create a new Trade Algorithm and add its constructor in the Trade Algorithm Factory.



## Observer:

####TradeStorage and MeetingStorage
#####(By Warren Zhu)

Trade Storage and Meeting Storage uses a