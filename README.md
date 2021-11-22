#README
####By Robbert Liu

##How to run

The program should be run from the main method in TraderMain.java. The strings for the 
config file name and path is hardcoded in TraderSystem in case they need to be changed, but the config is required for the 
rest of the program to run as they contain the filenames of the serialized storage files. The admins can adjust thresholds
under Trade Threshold tab in the program, or modify the data in thresholds.txt, located in src/main/java/resources.

Important Note: some pages will only refresh after you close the program and run again.

##Admin

When accounts.ser is created, there will a default Admin account with username and password "admin", from which one can add more admins. This can be changed in AccountStorage.getNewStorageData().

##Other Users

You can register and login with a new account, however to add new items, you must login with admin account to validate items after sending an item request.
Progressing meetings/trade offers also requires you to log in and out between accounts. Here are some accounts you can use, the password is "password1" for
all of them:

Usernames: Rob, Warren, Heather, Charles, Sarah, Fadi


##Dependencies

Java