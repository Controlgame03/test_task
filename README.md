# Java Binary Package Comparator

A Java application that compares binary packages from two branches of a public REST API provided by ALT Linux. This tool retrieves lists of binary packages from specified branches, compares them, and outputs the results in a JSON file.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Scripts](#scripts)

## Introduction

This project utilizes the **ALT Linux REST API** ([API Link](https://rdb.altlinux.org/api/)) to compare binary packages across different branches, such as `sisyphus`, `p9`, `p10`, and `p11`. The application identifies the following:

- Packages that exist in the **first branch** but not in the **second**
- Packages that exist in the **second branch** but not in the **first**
- Packages where the **`version-release`** in the **first branch** is greater than in the **second**

This functionality is available for each supported architecture, ensuring flexibility and compatibility.

## Features

- **Branch Comparison**: Compare binary packages between any two branches.
- **JSON Output**: Output comparison results in a well-structured JSON file.
- **Support for Multiple Branches**: Easily switch between branches: `sisyphus`, `p9`, `p10`, `p11`.


## Requirements

Before you begin, please ensure that you have the following software installed on your machine:

- **Java JDK**: Java Development Kit (JDK) version 11 or higher. If Java is not installed on your system, you can install it on ALT Linux using the following command (for ALT Linux):

  
`apt-get install java-11-openjdk`


- **Javac**: The Java compiler (included with the JDK installation). If the `javac` compiler is not available, you can install it with (for ALT Linux):

  
`apt-get install java-11-openjdk-devel`

You can check if Java and Javac are installed by running:


`java -version`

`javac -version`

### Setting the Default Java File Encoding to UTF-8

If you encounter the error `error: unmappable character for encoding ASCII`, you may need to set the default Java file encoding to UTF-8. You can do this by exporting the following environment variable:

`export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8`


This configuration ensures that Java application correctly handles files with non-ASCII characters, providing compatibility and preventing encoding-related issues.

### Dependency

This project requires the `java-json.jar` library, which is located in the `lib` directory. This library is essential for handling JSON data within the application. Please ensure that the library is available in your project before running the application.

## Installation

1. Clone the repository to your local machine:

`git clone https://github.com/Controlgame03/test_task.git`

`cd test_task`


2. Compile the project by running the compile script:

`./compile.sh`


## Usage

To compare packages between two branches, run the `run.sh` script with the branch names as arguments:

`./run.sh <branch1> <branch2>`


### Example:

`./run.sh sisyphus p10`


### Valid Branches

- `sisyphus`
- `p9`
- `p10`
- `p11`

Make sure to pass only valid branch names as arguments to avoid errors.

### Output Files

After the completion of the `run.sh` script, the resulting files will be generated in the format `output_{arch}.json`, where `{arch}` represents each architecture. These files contain the comparison results for the specified branches.

## Scripts

### `compile.sh`

This script compiles the Java files located in the `src` directory and generates the corresponding class files in the `bin` directory.

### `run.sh`

This script executes the Java application to compare packages. It requires two branch names as arguments and validates them to ensure they are among the supported branches.
