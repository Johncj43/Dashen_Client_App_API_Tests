# Dashen_Client_App_API_Tests
This repository hosts a comprehensive API Test Automation Framework developed to validate the backend services of the Dashen Bank Client Mobile Application. The framework ensures that all client-facing APIs operate securely, reliably, and in full compliance with functional and business requirements.
The project focuses on end-to-end API validation, covering both positive and negative scenarios to guarantee system stability across different customer journeys. It is built with maintainability, scalability, and clarity in mind, enabling efficient collaboration between QA engineers, backend developers, and DevOps teams.
## Project Pre-Requisites

### Required Tools

- **IntelliJ IDEA** (Optional but recommended)
    - Download the latest **Community** version: [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows)

- **Java 17**
    - Download: [Oracle Java 17](https://www.oracle.com/java/technologies/javase/jdk17-downloads.html)
    - You'll probably want the **Windows ZIP version**, then set up your `JAVA_HOME` environment variable.
    - Instructions: [Set JAVA_HOME](https://confluence.atlassian.com/conf92/setting-the-java_home-variable-in-windows-1477577437.html)
    - Instructions video: [Set jav_home video](https://www.youtube.com/watch?v=0y5SwMSBKNY&t=51s).

- **Maven**
    - Download: [Apache Maven](https://maven.apache.org/download.cgi)
    - Instructions on setting up Maven on Windows: [Setup Guide](https://maven.apache.org/install.html)
    - Instructions on setting video: [Setup Guide video](https://www.youtube.com/watch?v=XEphzGQz-nI).

### IntelliJ Plugins

To install plugins in IntelliJ:  
Go to **File > Settings > Plugins > Marketplace**, then search and install:

- **Gherkin**
- **Cucumber for Java**

## Project Location

Ensure **Git** is installed (IntelliJ should include Git or prompt you to install it).  
To clone this project in IntelliJ:

1. Go to **File > New > Project from Version Control**
2. Enter this repository URL:  git@github.com/Johncj43/Dashen_Client_App_API_Tests.git
3. Click **Clone**.

## Running the Tests

You can run tests in multiple ways:

- **Using Run Configurations** (Upper-right corner in IntelliJ)
- **Right-click** on a test class and select **Run**
- **Click the green play arrows** next to a line number in a test file
- **Using the command line:**

```sh
 mvn clean                   # Cleans the project  
 mvn install                 # Installs all dependencies  
 mvn verify serenity:aggregate  # Runs tests and generates reports  
```
## Writing Tests

Tests are written in **Gherkin (.feature files)**.

- Each step in a `.feature` file corresponds to a **Step Definition** (written in Java).

## Accessing Reports

There are two types of reports available:

### Serenity Reports

- Navigate to:

```sh
target/site/serenity/index.html
```

- Right-click the file, hover over **"Open In"**, then choose **"Browser"** to open the report.

### Maven Surefire Reports

- After running tests with `mvn verify`, reports can be found at:

```sh
target/surefire-reports/
```

> **Note:** If the reports do not generate, ensure that the tests are executed successfully and dependencies are correctly configured in `pom.xml`.


