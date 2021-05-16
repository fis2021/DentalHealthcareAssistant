# DentalHealthcareAssistant
This application aims to help both dentists and patients by facilitating the communication between these two parties and offer access to a better and more organized dental healthcare management.

In our project we are using the following technologies:

In our project we are using the following technologies:
* [Java 15 ](https://www.oracle.com/java/technologies/javase-downloads.html)
* [JavaFX](https://openjfx.io/openjfx-docs/) (as GUI)
* [Gradle](https://gradle.org/) (as build tool)
* [Nitrite Java](https://www.dizitart.org/nitrite-database.html) (as Database)


## Prerequisites
To be able to install and run this project, please make sure you have installed Java 11 or higher. Otherwise, the setup will not work!
To check your java version, please run `java -version` in the command line.

To install a newer version of Java, you can go to [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://jdk.java.net/).

It would be good if you also installed Gradle to your system. To check if you have Gradle installed run `gradle -version`.

If you need to install Gradle, please refer to this [official Gradle docs](https://docs.gradle.org/current/userguide/installation.html).

Make sure you install JavaFX SDK on your machine, using the instructions provided in the [Official Documentation](https://openjfx.io/openjfx-docs/#install-javafx). Make sure to export the `PATH_TO_FX` environment variable, or to replace it in every command you will find in this documentation from now on, with the `path/to/javafx-sdk-15.0.1/lib`.



## Setup & Run
To set up and run the project locally on your machine, please follow the next steps.

### Clone the repository
Clone the repository using:
```git
https://github.com/fis2021/DentalHealthcareAssistant.git
```git

### Verify that the project Builds locally

If you have installed all the prerequisites, you should be able to run any of the following commands:
```
gradle clean build
```
If you prefer to run using the wrappers, you could also build the project using 
```
./gradlew clean build (for Linux or MacOS)
or 
gradlew.bat clean build (for Windows)
```

## Signing up for an account and logging in
Before using the platform, every user must make an account as a customer or a dentist. 

You will be logging in with your unique username, the password and role.
Based on the role of choice, specific functions will be granted to the user after logging in.

### The customer account:
* See a list of cabinets available in the application
* Make an appointemnt 
* See the status/history of his/her appointments


### The dentist account:
* My appointments
* See today's appointments
* Schedule an appointment

### Open in IntelliJ IDEA
To open the project in IntelliJ idea, you have to import it as a Gradle project. 
After you import it, in order to be able to run it, you need to set up your IDE according to the [official documentation](https://openjfx.io/openjfx-docs/). Please read the section for `Non-Modular Projects from IDE`.
If you managed to follow all the steps from the tutorial, you should also be able to start the application by pressing the run key to the left of the Main class.

### Run the project with Gradle
The project has already been setup for Gradle according to the above link.
To start and run the project use one of the following commands:
* `gradle run` or `./gradlew run` (to start the `run` task of the `org.openjfx.javafxplugin` plugin)

To understand better how to set up a project using JavaFX 11+ and [Gradle](https://openjfx.io/openjfx-docs/#gradle), please check the [official OpenJFX documentation](https://openjfx.io/).

### Technical Details

#### Encrypting Passwords
Encrypting the passwords is done via the following  Java function.

    public String hash(char[] password)
    {
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt, 1 << cost);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + cost + '$' + enc.encodeToString(hash);
    }
  ## Resources
To understand and learn more about **JavaFX**, you can take a look at some of the following links:
* [Introduction to FXML](https://openjfx.io/javadoc/16/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
* [Getting Started with JavaFX](https://openjfx.io/openjfx-docs/)
* [JavaFX Tutorial](https://code.makery.ch/library/javafx-tutorial/)
* [JavaFX Java GUI Design Tutorials](https://www.youtube.com/playlist?list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG)

To better understand how to use **Nitrite Java**, use the following links:
* [Nitrite Java Github Repository](https://github.com/nitrite/nitrite-java) 
* [Nitrite Java Project Page](https://www.dizitart.org/nitrite-database.html)
* [Nitrite Java Documentation Page](https://www.dizitart.org/nitrite-database/)
* [Nitrite Java: Filters](https://www.dizitart.org/nitrite-database/#filter)
* [Nitrite: How to Create an Embedded Database for Java and Android](https://dzone.com/articles/nitrite-how-to-create-an-embedded-database-for-jav)
* [Nitrite: An Embedded NoSQL Database for Java and Android](https://medium.com/@anidotnet/nitrite-an-embedded-nosql-database-for-java-and-android-318bf48c7758)  
    
    


