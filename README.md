# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Chess Web API Diagram

My version of the diagram outlining the interaction from the server to the database.

[Web API Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5kvDrco67F8H5LCBALnAWspqig5QIAePKwvuh6ouisTYgmhgumGbpkhSBq0uWo4mkS4YWhy3K8gagrCjAr5iFhGq4SyHq6pksb+tOIautKkYctGMCcfGcrYcmUElihPLZrmmD-iCMElFcAzEaMC6Ts8+ivOB46tn035XgphTZD2MD9oOvQqSOam6dWcxaZ+6n6WYnCrt4fiBF4KDoHuB6+Mwx7pJkmAmReRTUNe0gAKK7lF9RRc0LQPqoT7dNOs7oO2v5nECJbpY2mXyWyWHwX5vrIWVYBoRimGicxZF4UYKDcBxQYBkGGVoKRTJuhR5TSM1FKGPlc4wKoEAwBkADkrJoBAzBUMAyC2o6zriblaYof5MkIHmElGeFSmljABkRQdoVgH2A7dDAx1Lq5njuRukK2ru0IwAA4qOrKBaeIXnswinXh9cWJfYo5pR1BVoFlbLyeUI2FftxWifB0LIdC1UYatiYEixpIwOSYCce1dbQ91ZoRoUlowFRMZBrRYSIzDTE4Q1rEwMgsRfaMqiwhT5H8eUkQWKgNAwEtCCE99MA42Jf77eUb2xDte0bZ2P5XFM4O8+MlT9DrKAAJLSHrACMvYAMwACxPCemQGhWExrN0fQ6AgoANo7wHO08hsAHKjs7eyzI0p3HEmxkA1dFm3T02vfXrFQG6OJvm1bttTPb+qqfcfQu-O7ue97am+wnoyB6Mweh-dK6PeugTYD4UDYNw8DsYYPOGL9wUXSjZ3lDeDRgxDwRQ3OQ4B6O4cdutqYI+P6CT6OlcoJB6uKeCMCenqsJwB3WNYnL9U9RzRMk8zAu9ULtM8vTcbaEKTOLyzdVs6fBN+FomRd7Chsm7MKeowr58WphyduXpMgS15HcT6o5j6RxyvPCBepVZyWRogzWx0+j-1NuUC2NsTqw0QRdGOQ5y7GzwTAAh1siHLjcg3AIlhmoIWSDAAAUhAHkcDRiBCLiABs-0ciA0vAPSoVRKR3haIbSGZMJ69FbsAZhUA4AQAQlAQBqdpAz2yvDCWL8hyKOUao9RmjRgm3XqmfuTp5QwAAFZcLQLCTh0kUBohqggvG7MCbnzapfUM3iqbsnKHTISDNH50WZp4mQ+N3SEwpL-XBIDzQ3xFlAMWhg-SGENtE3in9sDfxQIkrRsxugMXfB7YxajoDJKCTTQSRiOyQB4YxN+c9gTlH3pAlAaCiqYMMlrHRnZSFmWukkeOtcGEeQCF4JRXYvSwGANgVuhB4iJBSEFM8wjrGRRinFBKrRjDEIVhtcoIBuB4AALIsmAOSdBG9RE2LgtvC5cJEALMPrVWCJ9KZnNefzAJH86ngPeXgGAWT2QIPaSWUFWY1CyT6UDNMEziGnBGeZG6KL6FAA)


## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
