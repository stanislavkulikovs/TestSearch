import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Search {

    public static void main(String[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException("Необходимо 2 аргумента");
        }

        String fileName = args[0];
        String pattern = args[1];

        try(Stream<String> textStream = Files.lines(Paths.get(fileName))) {
            List<String> found = find(textStream, pattern);
            System.out.println(found);
        } catch (IOException e){
            System.out.println("Ошибка");
        }
    }

    protected static List<String> find(Stream<String> textStream, String pattern) {
        boolean useUppercase = containsUppercase(pattern);
        return textStream.filter(text ->
                containPattern(useUppercase? text : text.toLowerCase(), pattern)
        ).distinct().sorted(Comparator.comparing(Search::getLastWord)).collect(Collectors.toList());
    }

    protected static boolean containsUppercase(String str) {
        return str.chars().filter(Character::isUpperCase)
                .findFirst().isPresent();
    }

    protected static boolean containPattern(String text, String pattern) {
        char[] patternArr = pattern.toCharArray();
        return findFromIndex(getLastWord(text), patternArr, 0, 0);
    }

    private static boolean findFromIndex(String text, char[] patternArr, int patternIndex, int fromIndex) {
        int findIndex = -1;
        switch (patternArr[patternIndex]) {
            case '*':
                findIndex = fromIndex;
                break;
            case ' ':
                return fromIndex == text.length();
            default:
                findIndex = text.indexOf(patternArr[patternIndex], fromIndex);
        }
        return findIndex != -1 && ((patternIndex + 1) >= patternArr.length || findFromIndex(text, patternArr, patternIndex + 1, findIndex + 1));
    }

    private static String getLastWord(String text) {
        return text.substring(text.lastIndexOf(".") + 1);
    }
}
