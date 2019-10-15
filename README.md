Parking Lot Refactor
---------------------

#### Code Smells:
#####TEST 
- 	ParkingBoyFacts.java
	
	1. Repeating strings instead of using constant
	2. Unused imports

#####MAIN 
- 	ParkingBoy.java, SmartParkingBoy.java, SuperSmartParkingBoy.java
	
	1. Repeating strings instead of using constant
	2. Complex park and fetch methods

-   ParkingBoy.java


    1. Using different coding pattern (for each) in park method instead of following standard of other ParkingBoy methods (stream)
