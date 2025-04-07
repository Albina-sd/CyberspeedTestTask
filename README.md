# GameTestTask

For testing this project you should run these commands through CLI like below:
```bash
./gradlew clean build
./gradlew fatJar
java -jar build/libs/gameTestTask-all-1.0-SNAPSHOT.jar --config /config.json --betting-amount 100
```
Also, you can edit probability of bonuses in class `GenerateMatrix.class` in variable `PROBABILITY_OF_BONUS`.

Now it's 7 (more representative for showing work with bonuses) - higher probability than choosing 0.

# Description
Problem statement: You need to build a scratch game, that will generate a matrix (for example 3x3) from symbols(based on probabilities for each individual cell) and based on winning combintations user either will win or lost.
User will place a bet with any amount which we call betting amount in this assignment.

There are two types of symbols: Standard Symbols, Bonus Symbols.