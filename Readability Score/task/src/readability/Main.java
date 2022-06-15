package readability;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Character> SENTENCE_END_CHARACTERS = new ArrayList<>(List.of('.', '?', '!'));
        int words = 0;
        int characters = 0;
        int syllables = 0;
        int polysyllables = 0;

        String fileName = args[0];
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

//         Read text from file
        StringBuilder text = new StringBuilder();
        while (scanner.hasNextLine()) {
            text.append(scanner.nextLine());
        }
        scanner.close();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != '\n' && text.charAt(i) != '\t') {
                characters++;
            }
        }

        // Get sentences
        int sentenceStartIndex = 0;
        List<String> sentences = new ArrayList<>();
        for (int i = 0; i < text.length() - 1; i++) {
            if (SENTENCE_END_CHARACTERS.contains(text.charAt(i))) {
                sentences.add(text.substring(sentenceStartIndex, i + 1));
                sentenceStartIndex = i + 1;
            }
        }
        sentences.add(text.substring(sentenceStartIndex));

        // Count words
        for (String sentence : sentences) {
            List<String> wordsInSentence = new ArrayList<>(List.of(sentence.split(" ")));
            while (wordsInSentence.contains("") || wordsInSentence.contains(" ")) {
                wordsInSentence.remove("");
                wordsInSentence.remove(" ");
            }

            for (String word : wordsInSentence) {
                int syllablesInWord = countSyllables(word);
                syllables += syllablesInWord;

                if (syllablesInWord > 2) {
                    polysyllables++;
                }
            }

            words += wordsInSentence.size();
        }

        // Calculate score
        double ARIScore = calculateARIScore(characters, words, sentences.size());
        double FKScore = calculateFKScore(words, sentences.size(), syllables);
        double SMOGScore = calculateSMOGScore(polysyllables, sentences.size());
        double CLScore = calculateCLScore(characters, words, sentences.size());

        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences.size());
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);


        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner consoleScanner = new Scanner(System.in);
        String scoreToCalculate = consoleScanner.next();

        switch (scoreToCalculate) {
            case "ARI":
                System.out.println("Automated Readability Index: (about " + ARIScore + " " + getTextAge(ARIScore) + ").");
                break;
            case "FK":
                System.out.println("Flesch–Kincaid readability tests: (about " + FKScore + " " + getTextAge(FKScore) + ").");
                break;
            case "SMOG":
                System.out.println("Simple Measure of Gobbledygook: (about " + SMOGScore + " " + getTextAge(SMOGScore) + ").");
                break;
            case "CL":
                System.out.println("Coleman–Liau index: (about " + CLScore + " " + getTextAge(CLScore) + ").");
                break;
            case "all":
                double avgScore = (ARIScore + FKScore + SMOGScore + CLScore) / 4;
                System.out.println();
                System.out.println("Automated Readability Index: (about " + ARIScore + " " + getTextAge(ARIScore) + ").");
                System.out.println("Flesch–Kincaid readability tests: (about " + FKScore + " " + getTextAge(FKScore) + ").");
                System.out.println("Simple Measure of Gobbledygook: (about " + SMOGScore + " " + getTextAge(SMOGScore) + ").");
                System.out.println("Coleman–Liau index: (about " + CLScore + " " + getTextAge(CLScore) + ").");
                System.out.println();
                System.out.println("This text should be understood in average by " + getTextAge(avgScore) + ".");
                break;
        }
    }

    private static double calculateARIScore(int characters, int words, int sentences) {
        return (4.71 * ((double) characters / (double) words)) + (0.5 * ((double) words / (double) sentences)) - 21.43;
    }

    private static double calculateFKScore(int words, int sentences, int syllables) {
        return 0.39 * ((double) words / (double) sentences) + 11.8 * ((double) syllables / (double) words) - 15.59;
    }

    private static double calculateSMOGScore(int polysyllables, int sentences) {
        return 1.043 * Math.sqrt((double) polysyllables * (30 / (double) sentences)) + 3.1291;
    }

    private static double calculateCLScore(int characters, int words, int sentences) {
        // L is the average number of characters per 100 words
        double L = (100 * (double) characters) / (double) words;
        // S is the average number of sentences per 100 words
        double S = (100 * (double) sentences) / (double) words;

        return 0.0588 * L - 0.296 * S - 15.8;
    }

    private static int countSyllables(String word) {
        List<Character> VOWELS = new ArrayList<>(List.of('a', 'e', 'i', 'o', 'u', 'y'));

        int vowels = 0;
        boolean isPreviousLetterVowel = false;
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            boolean isLetterVowel = VOWELS.contains(letter);

            if (!isLetterVowel) {
                isPreviousLetterVowel = false;
                continue;
            }

            if (isPreviousLetterVowel) {
                continue;
            }

            vowels++;
            isPreviousLetterVowel = true;
        }

        // If the last letter in the word is 'e' do not count it as a vowel
        if (word.charAt(word.length() - 1) == 'e') {
            vowels -= 1;
        }

        return vowels > 0 ? vowels : 1;
    }

    private static String getTextAge(double score) {
        switch ((int) Math.ceil(score)) {
            case 1:
                return "6-year-olds";
            case 2:
                return "7-year-olds";
            case 3:
                return "9-year-olds";
            case 4:
                return "10-year-olds";
            case 5:
                return "11-year-olds";
            case 6:
                return "12-year-olds";
            case 7:
                return "13-year-olds";
            case 8:
                return "14-year-olds";
            case 9:
                return "15-year-olds";
            case 10:
                return "16-year-olds";
            case 11:
                return "17-year-olds";
            case 12:
                return "18-year-olds";
            case 13:
                return "24-year-olds";
        }
        return "24+-year-olds";
    }
}
