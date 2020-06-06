import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class SearchTest {
    @Test
    public void testMain() throws Exception {
        assertEquals(Search.containsUppercase("AAA"), true);
        assertEquals(Search.containsUppercase("aaa"), false);
        assertEquals(Search.containPattern("FooBarBaz", "Foo"), true);
        assertEquals(Search.containPattern("FooBarBaz", "F*B"), true);
        assertEquals(Search.containPattern("FooBarBaz", "F*b"), false);
        assertEquals(Search.containPattern("FooBarBaz", "Foo*"), true);
        assertEquals(Search.containPattern("FooBarBaz", "F*Bar*"), true);
        assertEquals(Search.containPattern("FooBarBaz", "Baz "), true);
        assertEquals(Search.containPattern("FooBarBaz", "B*z "), true);
        assertEquals(Search.containPattern("FooBarBaz", "Bar "), false);
        assertEquals(Search.containPattern("FooBarBaz", "B*r "), false);
        assertEquals(Search.containPattern("codeborne.WishMaker", "cod"), false);
        assertEquals(Search.containPattern("codeborne.WishMaker", "Wis"), true);
        Stream<String> textStream = Stream.of("Java", "FooBarBaz", "codeborne.WishMaker");
        assertEquals(Search.find(textStream, "Wis"), Arrays.asList("codeborne.WishMaker"));
        textStream = Stream.of("Java", "a.b.FooBarBaz", "c.d.FooBar");
        assertEquals(Search.find(textStream, "Bar"), Arrays.asList("c.d.FooBar", "a.b.FooBarBaz"));
    }

}