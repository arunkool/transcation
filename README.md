# transcation- Springboot Rest API

Requests:

1. Add Customer
Method : Post
URL: http://localhost:9292/docomo/addCustomer
Needed Fields
Body : {
"name":"TestName",
"phoneNo":123456789,
"amount":30
}

2. Charge against customer phone crrdit
Method : Patch
URL: http://localhost:9292/docomo/chargeToCustomer/{customerId}
Body : {"name": "dummyName", "amount": 10}

3.Refund to customer phone credit
Method : Patch
URL: http://localhost:9292/docomo/refundToCustomer/{customerId}
Body : {"name": "dummy Name", "amount": 10}

4. List of transcations by customer
Method: Get
URL : http://localhost:9292/docomo/transcationsByCustomer/{customerId}
