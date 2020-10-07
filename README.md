# Storage-Service

This Service is used to store data into the configured Path.
It recieves the Messages from Employee Storage Service (https://github.com/GauravBajpai92/Employee-Storage/tree/master) through Active MQ message queues.
It listens to Artemis ActiveMQ broker for incomming messages and stores them in .csv or .xml format.
vromero/activemq-artemis Docker image is used (https://hub.docker.com/r/vromero/activemq-artemis) for Active MQ.
Note: This is a Linux image and will not run on Windows container. Please switch to Linux docker container.
Follow https://github.com/vromero/activemq-artemis-docker/blob/master/README.md for image installation.

The Service also has a get end point which recives get requests from Employee Storage Service and returns the data in encrypted format.
For Security AES encryption is used, shared key between Employee Storage and Storage Service is used to encrypt and decrypt messages.
