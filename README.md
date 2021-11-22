# Used-Item-Trading-App
A Java program that simulates a marketplace for trading used items.

This program follows Software Development Life Cycle SDLC, Object Oriented Design (OOD) SOLID principles and Clean Architecture. Design Patterns, such as Factory, Observer, Strategy, etc., were impletemented in this program. Applied UML modeling tools to design Use Case, Activity Diagrams etc..

## How to run

The program should be run from the main method in TraderMain.java. The strings for the 
config file name and path is hardcoded in TraderSystem in case they need to be changed, but the config is required for the 
rest of the program to run as they contain the filenames of the serialized storage files. The admins can adjust thresholds
under Trade Threshold tab in the program, or modify the data in thresholds.txt, located in src/main/java/resources.

## Admin

When accounts.ser is created, there will a default Admin account with username and password "admin", from which one can add more admins. This can be changed in AccountStorage.getNewStorageData().
Admin Users can validate items on the market, mute/unmute users, freeze/unfreeze users, set thresholds for trading, and create a gilde club for active users.

## Trader

Users can register as a trader. Registered Traders can post, lend, and borrow items from the market, schedule a meeting time and place, complete a trading process. After the trader post a new item, the item needs to be validated by an admin user first and then appear on the market. If a trader is muted/freezed by the admin, the trader can send messages to all the admin users in the system to submit an unmute/unfreeze application.

Here are some exsiting accounts you can use, the password is "password1" for all of them:

Usernames: Rob, Warren, Heather, Charles, Sarah, Fadi
