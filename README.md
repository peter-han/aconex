
# 1-800 Coding challenge exercise @ Aconex

## Table of contents:

* [Setup](./README.md#setup)
* [Running the app](./README.md#running-the-app)
* [Running the tests](./README.md#running-the-tests)

### Setup
1. Make sure you have Java 8 installed in your machine. 

2. Build app from command line:

    ```./gradlew build```

### Running the app:
```java -jar ./build/libs/aconex-1.0-SNAPSHOT.jar```

### Running the tests:
    
```./gradlew clean test```

Then you would see testing report at 
```../aconex/build/reports/tests/test/index.html```

### Functions
show a user possible matches for a list of provided phone numbers.

- input
    - reads from files specified as command-line arguments
    - STDIN when no files are given
    - All punctuation and whitespace should be ignored in both phone numbers and the dictionary file. 
    - The program should not be case sensitive
- read phone number
    - replace every digit of the provided phone number with a letter from a dictionary word
    - if no match can be made, a single digit can be left as is at that point.
    - No two consecutive digits can remain unchanged 
    - the program should skip over a number (producing no output) if a match cannot be made.
- output
    - all possible word replacements from a dictionary
    - capital letters and digits separated at word boundaries with a single dash (-)
    - one possible word encoding per line
- dictionary
    - user to set a dictionary with the -d command-line option
    - a reasonable default on system
    - validation: The dictionary is expected to have one word per line.
