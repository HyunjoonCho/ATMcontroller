# ATMcontroller
Simple ATM Controller for Coding Assessment

## Design 
- Order of function call does matter in this scenario.  
(Insert Card) > Validate PIN > Get Account List > (Select Account as index) > See Balance/Deposit/Withdraw  
I strictly followed above flow, so that if you do not validate PIN, cannot go any further.  

- Introduced token, which is managed by bank service and required to access bank API. 

- Though I added defense logic, may have missed at some points. My apologies for that.

## How to test
```
$ git clone https://github.com/HyunjoonCho/ATMcontroller.git
$ cd ATMcontroller
```
#### 1. Running Unit Tests 
When you install this repo using maven, automatically runs unit tests that I wrote
```
$ ./mvnw install
```

#### 2. Using Spring REST API  
Mapped controller methods to simple REST API  
Check behavior via accessing localhost:8080 via web browser  
```
$  java -jar ./target/atm-controller-0.0.1-SNAPSHOT.jar
```
Every result is logged at your console

#### Example URLs  
http://localhost:8080/atmController/validatePIN?pin=0123456789   
http://localhost:8080/atmController/accountList?pin=0123456789  
http://localhost:8080/atmController/balance?index=1  
http://localhost:8080/atmController/withdraw?index=1&amount=1000  

May modify default constructor of [Bank.java](https://github.com/HyunjoonCho/ATMcontroller/blob/main/src/main/java/org/hyunjoon/atmcontroller/model/Bank.java#L16) and [CashBin.java](https://github.com/HyunjoonCho/ATMcontroller/blob/main/src/main/java/org/hyunjoon/atmcontroller/model/CashBin.java#L9) to change the initial state. 

Thanks! 
