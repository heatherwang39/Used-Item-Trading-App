#README
####By Robbert Liu

##How to run

The program should be run from the main method in TraderMain.java. The strings for the 
config file name and path is hardcoded in TraderSystem in case they need to be changed, but the config is required for the 
rest of the program to run as they contain the filenames of the serialized storage files. The admins can adjust thresholds
under Trade Threshold tab in the program, or modify the data in thresholds.txt, located in src/main/java/resources.

##Admin

When accounts.ser is created, there will a default Admin account with username and password "admin", from which one can add more admins. This can be changed in AccountStorage.getNewStorageData().

##Dependencies

Java