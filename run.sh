#!/bin/bash

# Script for comparing packages between two branches

# Function to display help
usage() {
    echo "Usage: $0 <branch1> <branch2>"
    echo "Example: $0 sisyphus p10"
    exit 1
}

# Check for the presence of required arguments
if [ "$#" -ne 2 ]; then
    echo "Error: Incorrect number of arguments."
    usage
fi

branch1=$1
branch2=$2

# List of valid branches
valid_branches=("sisyphus" "p9" "p10" "p11")

# Check for valid branches
if [[ ! " ${valid_branches[@]} " =~ " ${branch1} " ]] || [[ ! " ${valid_branches[@]} " =~ " ${branch2} " ]]; then
    echo "Error: Invalid branches."
    echo "Valid branches: ${valid_branches[*]}"
    exit 1
fi

# Running the Java program
echo "Starting package comparison between branches $branch1 and $branch2..."
java -cp "bin:lib/java-json.jar" com.example.Main "$branch1" "$branch2"

# Check the exit code of the last command
if [ $? -eq 0 ]; then
    echo "Comparison completed successfully."
else
    echo "An error occurred while running the program."
fi

