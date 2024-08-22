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

This project utilizes the ALT Linux REST API (https://rdb.altlinux.org/api/) to compare binary packages across different branches, such as `sisyphus`, `p9`, `p10`, and `p11`. The application identifies:

- Packages that exist in the first branch but not in the second
- Packages that exist in the second branch but not in the first
- Packages where the `version-release` in the first branch is greater than in the second

This functionality is available for each supported architecture.

## Features

- Compare binary packages between two branches.
- Output comparison results in a JSON file.
- Support for multiple branches: `sisyphus`, `p9`, `p10`, `p11`.

## Requirements

Before you begin, ensure you have the following installed:

- **Java JDK**: Ensure you have Java Development Kit (JDK) 8 or higher installed on your machine.
- **Javac**: The Java compiler (should be included with the JDK installation).

You can check if Java and Javac are installed by running:

bash
java -version
javac -version

This project requires the `java-json.jar` library, which is located in the `lib` directory. This library is essential for handling JSON data within the application. Please ensure that the library is available in your project before running the application.

## Installation

1. Clone the repository:

   
bash
git clone https://github.com/Controlgame03/test_task.git
cd yourproject


2. Compile the project by running the compile script:

   
bash
./compile.sh


## Usage

To compare packages between two branches, execute the run script with the branch names as arguments:

bash
./run.sh <branch1> <branch2>


**Example:**

bash
./run.sh sisyphus p10


### Valid Branches

- `sisyphus`
- `p9`
- `p10`
- `p11`

Make sure to pass only valid branch names as arguments.


## Scripts

### `compile.sh`

This script compiles the Java files in the `src` directory and generates the corresponding class files in the `bin` directory.

### `run.sh`

This script runs the Java application to compare packages. It requires two branch names as arguments and performs validation to ensure they are among the supported branches.
