# About
jasmine_rest is the JASMINE component that manages REST API.

# Configuration

JASMINE is customizable. It can be specified a path to a `.properties` file in order to override the configurations.
The following table shows configurations options:

|             property             | type    | description                                                                                                                                  | example        |
|--------------------------------- |---------|----------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| server.host                | String    | Server's host                                                                                              | localhost              |
| server.port                | int    | Server's port                                                                                              | 8082             |
| spring.data.mongodb.uri                | String | MongoDB uri                                                                                          | mongodb://localhost:27017/jasmine_db           |
| spring.data.mongodb.database            | String    | MongoDB database name                                                              | jasmine_db             |
| spring.redis.host | String    | Redis host                                                     | localhost             |
| spring.redis.port       | int  | Redis port                                                                              | 6379 |
| elasticsearch.cluster.address    | String  | Elasticsearch cluster remote address                                                                                                         | "localhost"    |
| spring.redis.password       | String     | Redis password                                              |            |
| spring.kafka.bootstrap-servers            | String  | Apache Kafka bootstrap-servers                                                                                                          | localhost:9092    |
| spring.kafka.consumer.group-id               | String     | Apache Kafka group-id                                                                                                             | rest_monitor_group           |
| kafka.top.ten.crossroads.topic                | String | Apache Kafka topic for the first query | top-ten-crossroads-           |
| kafka.outlier.crossroads.topic                | String | Apache Kafka topic for the second query | outlier-crossroads-           |
| kafka.damaged.semaphore.topic                | String | Apache Kafka topic for the damaged traffic lights | damaged-semaphore           |
| kafka.top.semaphore.route.topic                | String | Apache Kafka topic for the third query | top-semaphore-route           |
| kafka.small.window                | Long | Apache Kafka smallest time window | 15000           |
| kafka.medium.window                | Long | Apache Kafka medium time window | 60000           |
| kafka.large.window                | Long | Apache Kafka largest time window | 1440000           |
| kafka.topic.suffix                | String | Apache Kafka topic suffix | -topic           |
| websocket.top.ten.crossroads.topic | String| Websocket first query topic| top-ten-crossroads-|
|websocket.outlier.crossroads.topic| String| Websocket first query topic| outlier-crossroads-|
|websocket.damaged.semaphore.topic| String| Websocket first query topic| damaged-semaphore|
|websocket.top.semaphore.route.topic| String| Websocket first query topic| top-semaphore-route|
|websocket.top.semaphore.route.window| Long| Websocket first query topic| 5000|
|websocket.small.window| Long| Websocket smallest time window | 15000|
|websocket.medium.window| Long| Websocket medium time window | 60000|
|websocket.large.window| Long| Websocket largest time window| 1440000|
|websocket.topic.suffix| String |Websocket topic suffix | -topic|
|grid.top.left.latitude| double| Top-Left latitude of the city grid| 41.959598|
|grid.top.left.longitude| double| Top-Left longitude of the city grid| 12.389655|
|grid.bottom.right.latitude| double| Bottom-right latitude of the city grid| 41.836425|
|grid.bottom.right.longitude| double| Bottom-right longitude of the city grid| 12.593245|
|grid.latitude.sections| int| Number of cells| 5|
|grid.longitude.sections| int| Number of cells| 5|