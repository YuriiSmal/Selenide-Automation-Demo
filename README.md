# Automation Project Demo

This is a demo project created for portfolio purposes. It demonstrates how to set up and run an automation project from scratch using the following technology stack:

- **Java 17**
- **Gradle** as the build tool
- **TestNG** for testing
- **Selenoid** for browser setup
- **Selenide** as the automation tool
- **Allure** for reporting
- **GitHub Actions** for CI/CD

## Project Overview

This project showcases the following features:
1. **Automated UI Testing**: Using Selenide for writing and executing UI tests.
2. **Reporting**: Generating Allure reports to visualize test results, including videos of test executions.
3. **Continuous Integration**: GitHub Actions is configured to:
    - Reject pull requests if the `gradlew clean build` step fails.
    - Set up a temporary Selenoid container to run UI automation tests.

## Prerequisites

- **Java 17** installed
- **GitHub Account** for accessing and managing repositories

## Setup and Execution

### Clone the Repository

```bash
git clone https://github.com/YuriiSmal/MyDemo.git
cd MyDemo
```

## Run Tests Locally

### To run the tests locally, execute:

```bash
./gradlew suiteTest -PtestType=suiteTest
```

## Generate Allure Reports

### To generate and view Allure reports, execute:

```bash
./gradlew report
```

### GitHub Actions Configuration

This project is configured with GitHub Actions to ensure continuous integration and testing. The following workflows are provide:

1. **Project Build Check**: This workflow runs on every push and pull request to the `master` branch. It includes steps to:
   - Checkout the code
   - Set up Java 17
   - Run `./gradlew clean build` 
   - Reject the pull request if any of the steps fail

2. **Google Search Simple Test**: This workflow runs as workflow_dispatch. It includes steps to:
   - Checkout the code
   - Set up Java 17
   - Spin up a Selenoid container
   - Run `./gradlew suiteTest -PtestType=suiteTest`
   - Execute UI tests
   - Generate Allure reports

### Allure Report

The latest test results can be checked at the following link:
[Allure Report](https://yuriismal.github.io/MyDemo/)