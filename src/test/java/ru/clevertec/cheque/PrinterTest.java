package ru.clevertec.cheque;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PrinterTest {
    private final Printer printer = new Printer();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static final List<Map<String, String>> arguments = new ArrayList<>();

    @BeforeAll
    public static void setUpList() {
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

        arguments.add(firstArgument);
        arguments.add(secondArgument);
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void checkPrintChequeShouldPrintChequeBasedOnMethodArguments() {
        //given
        BigDecimal total = BigDecimal.valueOf(27.22);
        BigDecimal discount = BigDecimal.valueOf(3.45);
        BigDecimal totalWithDiscount = BigDecimal.valueOf(23.77);

        //when
        printer.printCheque(arguments, total, discount, totalWithDiscount);

        boolean actual =
                    outContent.toString().contains(LocalDate.now().toString()) &&
                    outContent.toString().contains(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString()) &&
                    outContent.toString().contains("\t2\t\tEggs !\t\t\t\t\t2.32$\t\t4.64$") &&
                    outContent.toString().contains("\t9\t\tButter\t\t\t\t\t3.17$\t\t28.53$") &&
                    outContent.toString().contains("\tTOTAL:\t\t\t\t\t\t27.22$") &&
                    outContent.toString().contains("\tDISCOUNT:\t\t\t\t\t3.45$") &&
                    outContent.toString().contains("\tTOTAL WITH DISCOUNT:\t\t\t\t23.77$");
        //then
        Assertions.assertTrue(actual);
    }
}