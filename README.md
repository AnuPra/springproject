# springproject

Banking Application
	This application supports banking operations such as balance inquiry. For each request api, a transaction log is sent through Kafka producer to topic ‘transaction-input’. Kafka stream is used to compute no of balance inquiries for each account and sent to topic ‘balance-output’.

Getting Started
1)	Start zookeeper and kafka servers
2)	Create topics ‘transaction-input’ and ‘balance-output’	

Built With
1)	Spring Boot
2)	Kafka 
3)	Hibernate 
4)	MySQL

Author
•	Anusha Pratty

References
1) https://docs.spring.io/spring-kafka/reference/htmlsingle/
2) https://kafka.apache.org/quickstart
3) https://kafka.apache.org/11/documentation/streams/tutorial
4) https://spring.io/guides/gs/spring-boot/
