
package com.pillar.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test {
    // Single Random instance for better performance and randomness distribution
    private final Random random = new Random();

    // These should be passed in as program arguments for better generalization
    private final int NUMBER_OF_DICE = 5;
    private final int REPETITIONS = 10000;
    
    public static void main(String[] args) {        
        (new Test()).go();
    }

    private void go() {
        // Hashtable to record counts for each possible score
        Map<Integer, Integer> scoreCounts = new HashMap<>();
        
        int maxScoreObserved = 0;
        
        // Run the game REPETITIONS times and collect score statistics
        for (int i = 0; i < REPETITIONS; i++) {
            int score = playGame();
            scoreCounts.put(score, scoreCounts.getOrDefault(score, 0) + 1);
            maxScoreObserved = Math.max(maxScoreObserved, score);
        }
        
        // Display the results
        System.out.println("\n=== SCORE DISTRIBUTION RESULTS ===");
        System.out.println("Number of dice: " + NUMBER_OF_DICE);
        System.out.println("Number of games: " + REPETITIONS);
        System.out.println("Theoretical max score: " + (6 * NUMBER_OF_DICE));
        System.out.println("Actual max score observed: " + maxScoreObserved);
        System.out.println();
        
        // Sort scores for consistent output
        scoreCounts.keySet().stream().sorted().forEach(score -> {
            int count = scoreCounts.get(score);
            double percentage = (count * 100.0) / REPETITIONS;
            System.out.printf("Score %2d: %5d occurrences (%.2f%%)\n", score, count, percentage);
        });
        
        System.out.println();
        System.out.println("Total games recorded: " + scoreCounts.values().stream().mapToInt(Integer::intValue).sum());
    }

    private int playGame() {
        // Start with NUMBER_OF_DICE dice "on the board"
        java.util.List<Integer> dice = new java.util.ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            dice.add(random(6)); // Roll initial dice (1-6)
        }
        
        int totalScore = 0;
        
        // Repeat until all dice have been removed
        while (!dice.isEmpty()) {
            // Roll the remaining dice
            for (int i = 0; i < dice.size(); i++) {
                dice.set(i, random(6));
            }
            
            // Check if any dice show 3
            boolean hasThree = false;
            for (int die : dice) {
                if (die == 3) {
                    hasThree = true;
                    break;
                }
            }
            
            if (hasThree) {
                // Remove all dice that show 3 and set roll score to 0
                dice.removeIf(die -> die == 3);
                // Roll score is 0, so don't add to total
            } else {
                // Find the minimum value
                int minValue = dice.stream().min(Integer::compareTo).orElse(0);
                
                // Count how many dice have the minimum value
                long minCount = dice.stream().filter(die -> die == minValue).count();
                
                // Remove ALL dice with the lowest score and add their total to roll score
                dice.removeIf(die -> die == minValue);
                totalScore += minValue * (int)minCount;
            }
        }
        
        return totalScore;
    }
    
    /**
     * Generates a random number between 1 and n inclusive.
     * 
     * @param n upper bound (inclusive)
     * @return A random integer between 1 and n (inclusive)
     */
    public int random(int n) {
        // Generate random number between 1 and n inclusive
        // nextInt(n) returns 0 to n-1, so adding 1 gives us 1 to n
        return random.nextInt(n) + 1;
    }

 }
