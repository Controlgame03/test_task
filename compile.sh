#!/bin/bash

# Script to compile Java files in the project

# Check for the existence of the src folder
if [ ! -d "src" ]; then
    echo "Error: 'src' folder not found."
    exit 1
fi

# Compile files with lib/java-json.jar added to the classpath
javac -cp "lib/java-json.jar" -d bin src/com/example/*.java

# Check the exit code of the last command
if [ $? -eq 0 ]; then
    echo "Compilation completed successfully."
else
    echo "An error occurred during compilation."
fi

