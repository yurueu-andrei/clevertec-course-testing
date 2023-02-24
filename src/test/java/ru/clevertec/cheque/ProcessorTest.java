package ru.clevertec.cheque;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ProcessorTest {
    private final Processor processor = new Processor(new DataStorage());

    @Test
    void checkParseArgumentsShouldReturnMapOf4Arguments() {
        //given
        Map<String, String> expected = new HashMap<>();
        expected.put("1", "2");
        expected.put("2", "5");
        expected.put("3", "8");
        expected.put("card", "1020");

        String[] args = {"1-2", "2-5", "3-8", "card-1020"};

        //when
        Map<String, String> actual = processor.parseArguments(args);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkParseArgumentsShouldReturnMapOf2Arguments() {
        //given
        Map<String, String> expected = new HashMap<>();
        expected.put("1", "5");
        expected.put("2", "9");

        String[] args = {"1-2", "2-9", "1-3", "1-5"};

        //when
        Map<String, String> actual = processor.parseArguments(args);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EmptySource
    void checkParseArgumentsShouldReturnEmptyMap(String[] arg) {
        //when
        Map<String, String> actual = processor.parseArguments(arg);

        //then
        Assertions.assertTrue(actual.isEmpty());
    }

    @ParameterizedTest
    @EmptySource
    void checkValidateArgumentsShouldThrowExceptionInCaseOfEmptyMap(Map<String, String> args) {
        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> processor.validateArguments(args));
    }

    @ParameterizedTest
    @MethodSource("validateArgumentsArgsProvider")
    void checkValidateArgumentsShouldReturnTrueForCorrectArguments(Map<String, String> args) {
        //when
        boolean actual = processor.validateArguments(args);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void checkValidateArgumentsShouldThrowExceptionInCaseOfIncorrectProducts() {
        //given
        Map<String, String> args = new HashMap<>();
        args.put("20", "5");
        args.put("30", "5");
        args.put("40", "5");

        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> processor.validateArguments(args));
    }

    @Test
    void checkValidateArgumentsShouldThrowExceptionInCaseOfIncorrectDiscountCard() {
        //given
        Map<String, String> args = new HashMap<>();
        args.put("1", "5");
        args.put("2", "5");
        args.put("card", "0");

        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> processor.validateArguments(args));
    }

    @Test
    void checkCountTotalAndDiscountShouldReturnListOf2MapsWithProductsInfo() {
        //given
        Map<String, String> args = new HashMap<>();
        args.put("1", "2");
        args.put("3", "9");

        Map<String, String> firstArgument = new HashMap<>() {{
            put("quantity", "2");
            put("itemName", "Eggs !");
            put("itemPrice", "2.32");
            put("totalPrice", "4.64");
            put("discount", "0");
        }};

        Map<String, String> secondArgument = new HashMap<>() {{
            put("quantity", "9");
            put("itemName", "Butter");
            put("itemPrice", "3.17");
            put("totalPrice", "28.53");
            put("discount", "0");
        }};

        List<Map<String, String>> expected = new ArrayList<>() {{
            add(firstArgument);
            add(secondArgument);
        }};

        //when
        List<Map<String, String>> actual = processor.countTotalAndDiscount(args);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkCountTotalAndDiscountShouldReturnListOf2MapsWithProductsInfoAndDiscountForEachProduct() {
        //given
        Map<String, String> args = new HashMap<>();
        args.put("1", "9");
        args.put("3", "2");
        args.put("card", "1025");

        Map<String, String> firstArgument = new HashMap<>() {{
            put("quantity", "9");
            put("itemName", "Eggs !");
            put("itemPrice", "2.32");
            put("totalPrice", "20.88");
            put("discount", "3.1320");
        }};

        Map<String, String> secondArgument = new HashMap<>() {{
            put("quantity", "2");
            put("itemName", "Butter");
            put("itemPrice", "3.17");
            put("totalPrice", "6.34");
            put("discount", "0.3170");
        }};

        List<Map<String, String>> expected = new ArrayList<>() {{
            add(firstArgument);
            add(secondArgument);
        }};

        //when
        List<Map<String, String>> actual = processor.countTotalAndDiscount(args);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkCalculateTotalShouldReturnSumOfEachProductTotalPrice() {
        //given
        Map<String, String> firstArgument = new HashMap<>() {{
            put("quantity", "9");
            put("itemName", "Eggs !");
            put("itemPrice", "2.32");
            put("totalPrice", "20.88");
            put("discount", "3.1320");
        }};
        Map<String, String> secondArgument = new HashMap<>() {{
            put("quantity", "2");
            put("itemName", "Butter");
            put("itemPrice", "3.17");
            put("totalPrice", "6.34");
            put("discount", "0.3170");
        }};

        List<Map<String, String>> args = new ArrayList<>() {{
            add(firstArgument);
            add(secondArgument);
        }};

        BigDecimal expected = BigDecimal.valueOf(27.22);

        //when
        BigDecimal actual = processor.calculateTotal(args);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkCalculateDiscountShouldReturnSumOfEachProductDiscount() {
        //given
        Map<String, String> firstArgument = new HashMap<>() {{
            put("quantity", "9");
            put("itemName", "Eggs !");
            put("itemPrice", "2.32");
            put("totalPrice", "20.88");
            put("discount", "3.1320");
        }};
        Map<String, String> secondArgument = new HashMap<>() {{
            put("quantity", "2");
            put("itemName", "Butter");
            put("itemPrice", "3.17");
            put("totalPrice", "6.34");
            put("discount", "0.3170");
        }};

        List<Map<String, String>> args = new ArrayList<>() {{
            add(firstArgument);
            add(secondArgument);
        }};

        BigDecimal expected = BigDecimal.valueOf(3.45);

        //when
        BigDecimal actual = processor.calculateDiscount(args);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void checkCalculateTotalWithDiscountShouldReturnDifferenceOfTotalAndDiscount() {
        //given
        BigDecimal total = BigDecimal.valueOf(27.22);
        BigDecimal discount = BigDecimal.valueOf(3.45);

        BigDecimal expected = BigDecimal.valueOf(23.77);

        //when
        BigDecimal actual = processor.calculateTotalWithDiscount(total, discount);

        //then
        Assertions.assertEquals(expected, actual);
    }

    static List<Map<String, String>> validateArgumentsArgsProvider() {
        Map<String, String> arg1 = new HashMap<>() {{
            put("1", "5");
        }};
        Map<String, String> arg2 = new HashMap<>() {{
            put("1", "1");
            put("3", "20");
            put("5", "9");
        }};
        Map<String, String> arg3 = new HashMap<>() {{
            put("2", "17");
            put("4", "2");
            put("6", "90");
            put("8", "55");
            put("7", "13");
            put("5", "28");
            put("9", "6");
        }};
        Map<String, String> arg4 = new HashMap<>() {{
            put("1", "10");
            put("2", "9");
            put("3", "8");
            put("4", "7");
            put("5", "6");
            put("6", "5");
            put("7", "4");
            put("8", "3");
            put("9", "2");
            put("10", "1");
        }};
        Map<String, String> arg5 = new HashMap<>() {{
            put("7", "55");
            put("4", "550");
            put("2", "40");
            put("1", "32");
            put("9", "64");
            put("5", "128");
            put("10", "256");
            put("3", "512");
            put("card", "1020");
        }};

        return List.of(arg1, arg2, arg3, arg4, arg5);
    }
}