package readability;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Character> SENTENCE_END_CHARACTERS = new ArrayList<>(List.of('.', '?', '!'));
        int words = 0;
        int characters = 0;
        double score = 0;

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
            if (text.charAt(i) != ' ' && text.charAt(i) != '\n' && text.charAt(i) != '\t'){
                characters++;
            }
        }


        // Get sentences
        int sentenceStartIndex = 0;
        List<String> sentences = new ArrayList<>();
        for (int i = 0; i < text.length() -1; i++) {
            if (SENTENCE_END_CHARACTERS.contains(text.charAt(i))) {
                sentences.add(text.substring(sentenceStartIndex, i + 1));
                sentenceStartIndex = i + 1;
            }
        }
        sentences.add(text.substring(sentenceStartIndex));

        // Count words
        for (String sentence : sentences) {
            List<String> wordsInSentence = new ArrayList<>(List.of(sentence.split(" ")));
            while (wordsInSentence.contains("") || wordsInSentence.contains(" ")){
                wordsInSentence.remove("");
                wordsInSentence.remove(" ");
            }

            words += wordsInSentence.size();
        }

        // Calculate score
        score = (4.71 * ((double) characters / (double) words)) + (0.5 * ((double) words / (double) sentences.size())) - 21.43;

        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences.size());
        System.out.println("Characters: " + characters);
        System.out.println("The score is: " + score);
        printTextAge(score);
    }

    private static void printTextAge(double score){
        switch ((int) Math.ceil(score)){
            case 1:
                System.out.println("This text should be understood by 5-6-year-olds.");
                break;
            case 2:
                System.out.println("This text should be understood by 6-7-year-olds.");
                break;
            case 3:
                System.out.println("This text should be understood by 7-9-year-olds.");
                break;
            case 4:
                System.out.println("This text should be understood by 9-10-year-olds.");
                break;
            case 5:
                System.out.println("This text should be understood by 10-11-year-olds.");
                break;
            case 6:
                System.out.println("This text should be understood by 11-12-year-olds.");
                break;
            case 7:
                System.out.println("This text should be understood by 12-13-year-olds.");
                break;
            case 8:
                System.out.println("This text should be understood by 13-14-year-olds.");
                break;
            case 9:
                System.out.println("This text should be understood by 14-15-year-olds.");
                break;
            case 10:
                System.out.println("This text should be understood by 15-16-year-olds.");
                break;
            case 11:
                System.out.println("This text should be understood by 16-17-year-olds.");
                break;
            case 12:
                System.out.println("This text should be understood by 17-18-year-olds.");
                break;
            case 13:
                System.out.println("This text should be understood by 18-24-year-olds.");
                break;
            case 14:
                System.out.println("This text should be understood by 24+-year-olds.");
                break;
        }


    }
}
