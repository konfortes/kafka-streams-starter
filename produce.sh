kafka-console-producer --broker-list localhost:9092 --topic order-events --property "parse.key=true" --property "key.separator=:"

111:{"items": ["item1"], "totalAmount": 30, "userId": "111", "status": "created", "createdAt": "2020-01-23T10:09:02.00Z"}
111:{"items": ["item2"], "totalAmount": 50, "userId": "111", "status": "created", "createdAt": "2020-01-23T10:09:07.00Z"}
222:{"items": ["item2"], "totalAmount": 50, "userId": "222", "status": "created", "createdAt": "2020-01-23T10:09:13.00Z"}
222:{"items": ["item0"], "totalAmount": 0, "userId": "222", "status": "created", "createdAt": "2020-01-23T10:09:13.00Z"}
111:{"items": ["item1"], "totalAmount": 30, "userId": "111", "status": "canceled", "createdAt": "2020-01-23T10:09:25.00Z"}
333:{"items": ["item1", "item2"], "totalAmount": 80, "userId": "333", "status": "created", "createdAt": "2020-01-23T10:26:10.00Z"}
111:{"items": ["item3"], "totalAmount": 120, "userId": "111", "status": "created", "createdAt": "2020-01-23T10:26:58.00Z"}
444:{"items": ["item3"], "totalAmount": 120, "userId": "444", "status": "created", "createdAt": "2020-01-25T10:32:58.00Z"}
