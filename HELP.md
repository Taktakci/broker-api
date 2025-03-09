# Simple Broker Api
This is a Simple Broker Api Project developed in Spring Boot.

## How to Run
* Clone this repository
* Make sure you are using JDK 17 and Maven 3.x
* You can build the project and run the tests by running
  * **mvn clean package**
* you can run the service by running 
  * **mvn spring-boot:run**


## h2 in-memory database
* **resources/schema.sql** is used to create tables and insert test data into these tables.
  * ASSET table keeps the assets belonging to the users
  * STOCK_ORDER table keeps and manages the orders sent by the Clients
  * CUSTOMER table is used authenticate the users and manage the access to the apis.
    * There are 2 users defined in schema.sql file. 
      * username: **user1** - password: **userpass**, is a standard user. It has access to all endpoints except the /match api.
      * username: **admin** - password: **adminpass**, is an Admin user. It has access to all endpoints.
    * You can add more users in schema.sql file manually. Password should be bcrypt encoded.
  * You can reach the h2 database portal on this address,
    * http://localhost:8080/h2-console/
    * username: **sa** - password: [blank]


## Rest Apis
All Apis use **Basic Authentication**. There are 2 hardcoded users in schema.sql file. You can add more users as defined above.
* **user1** has access to **/order/*** and **/asset/*** endpoints. 
* **admin** has access to all endpoints, **/order/*** , **/asset/*** and **/match/***

### Create A Buy Order
Below request creates a Buy order for Customer 1. He wants to buy 3 "stock2" shares, and he is ready to pay 20 TRY for each.

```
POST /order
Accept: application/json
Content-Type: application/json

{
  "customerId": 1,
  "assetName": "stock2",
  "orderSide": "BUY",
  "size": 3,
  "price": 20
}
```

### Create A Sell Order
Below request creates a Sell order for Customer 2. He wants to sell 3 "stock2" shares, and he wants 7 TRY for each one.

```
POST /order
Accept: application/json
Content-Type: application/json

{
  "customerId": 2,
  "assetName": "stock2",
  "orderSide": "SELL",
  "size": 3,
  "price": 7
}
```

### List Orders of a Customer
Below request lists the orders given by Customer 1, between two dates

```
GET /order/customer/1/fromDate/25-02-01/toDate/25-03-01
Accept: application/json
```

### Delete an Order
Below api call deletes the order with id 15. 

Delete means making the Order Status CANCELED.

```
DELETE /order/15
Accept: application/json
```
### List Assets of a Customer
Below api returns the list of the Assets of Customer 1

```
GET /asset/customer/1
Accept: application/json
```

### Match Request
Only an Admin can call this api.

First, these two orders are tested to see if they are compatible with each other. Then transaction gets executed, and Customer assets are updated.

```
POST /match
Accept: application/json
Content-Type: application/json

{
  "buyOrderId": 4,
  "sellOrderId": 5
}
```


