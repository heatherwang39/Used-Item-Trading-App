#UNDO
####By Warren Zhu

###The Extent of the Feature
According to the Phase 2 instructions, we are supposed to be able to "[g]ive the admin user the ability to undo every action taken by regular users that can reasonably be undone."

Before implementing this feature, we must carefully decide on what this means (i.e., what actions can be undone). Below is a list outlining what is undoable, what isn't undoable, and why/how.

####Undoable:
#####1)Trade Completion

A Trade can be undone (i.e., the items are returned to their original owners, and the Trade is marked as cancelled). A Trade, however, cannot be erased.

#####2)Putting an Item on WishLists, Hiding/Unhiding Items, Verifying Items

Items can be removed from a User's WishList and can unhidden/hidden. Administrators can also unverify items (this is useful to Admins when Admins come to realize that a certain item has a problem with it, such as a counterfeit item). Ownership of items can be changed too! Items themselves, however, cannot be erased.

#####3)Changing a User Acccount's Status

A user that is set to frozen can be unfrozen, a user that is muted can be unmuted, etc.

####Not Undoable:
#####1) Sending messages

Once a message is sent, users should not be able to retract what they've said. We want people to be careful with their words, and to think carefully before a message is composed and sent.

#####2) Account Creation

Accounts cannot be deleted. They have far too many ties with other things, such as trade, meetings, and items. As such, accounts may be perma-banned, but they cannot be deleted.

#####3) Accepting A Trade

Administrators cannot undo the act of a user accepting a trade. Besides, if a Trader says that they agree to a certain trade, they should fully commit. The Trade itself, however, can be cancelled.

#####4) Accepting or Confirming A Meeting, Confirming/Cancelling Meetings.

Similar to sending messages and accepting trades, we want our users to be held accountable for their actions. Besides, to nullify the effect of cancelling and confirming meetings, we can just remove the given meeting from the associated trade. There really is no need to undo this.

#####5) Item Creation

Items cannot be deleted. If an admin wishes to "delete" an item, they could remove the owner of the item and make the item hidden. This will make it so that ordinary users will no longer be able to access the items.



##Implementation

####UseCase Level

"Undo" isn't really about how entities interact--rather, it's about what admins can do. As such, "Undo" should be in the controller layer rather than the use case layer, and the term "Undo" shouldn't be mentioned in the UseCase level unless absolutely necessary.

Then what needs to be done at the UseCase level? If you think about it, undoing a given action (let's call this 'A') is essentially performing the inverse of said action (let's call this 'A<sup>-1</sup>')--in other words, 'A' cancels out 'A<sup>-1</sup>'. Thus, in order to make it so that actions CAN be undone, we have to make sure for every undoable action, there can be an equal and opposite action to cancel the original action (Newton's Third Law for Traders).

Let's go through the "Undoable" list above and see what 'A<sup>-1</sup>' are implemented. If something isn't implemented, an implementation will be suggested.

1) Currently, tradeStorage and accountStorage already implement an Observer design pattern. This makes it so that the ownership of items are automatically updated when a trade is either cancelled, completed, or the program knows the item has been transferred (i.e., after the first meeting). As such, all we have to do to undo a completed trade is to cancel it. The observer pattern will automatically update the item owners.

2) This is already implemented.

3) This is already implemented.

As of now, everything in the UseCase level that is necessary for this feature is implemented. This means our program adheres well to the Open/Closed principle.

####Controllers

A new dedicated controller should be created with methods to undo given actions. However, for certain actions, such as undoing Trades, it should first check the magnitude of the side-effect of the given action. Say for example, an Admin wants to undo a Trade that happened 10 years ago. When attempting to perform this action, the system should check how many trades involving the given items have occured--if too many have occurred, this will cause innocent traders to suddenly have certain items snagged away after giving away their items. As such, new exceptions may need to be created in the controller layer.

####GUIs

A new dedicated GUI has to be created to deal with undoing actions.