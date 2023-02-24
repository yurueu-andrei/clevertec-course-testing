package ru.clevertec.cheque;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CashMachineTest {
    @InjectMocks
    private CashMachine cashMachine;
    @Spy
    private Processor processor = new Processor(new DataStorage());
    @Mock
    private Printer printer;

    @Test
    void printCheque() {
        //given
        String[] args = {"1-2", "2-10", "card-1020"};
        Map<String, String> firstArgument = new HashMap<>() {{
            put("quantity", "2");
            put("itemName", "Eggs !");
            put("itemPrice", "2.32");
            put("totalPrice", "4.64");
            put("discount", "0.2320");
        }};
        Map<String, String> secondArgument = new HashMap<>() {{
            put("quantity", "10");
            put("itemName", "Milk !");
            put("itemPrice", "1.73");
            put("totalPrice", "17.30");
            put("discount", "2.5950");
        }};

        List<Map<String, String>> list = new ArrayList<>() {{
            add(firstArgument);
            add(secondArgument);
        }};

        //when
        cashMachine.printCheque(args);

        //then
        Mockito.verify(printer).printCheque(eq(list),
                eq(BigDecimal.valueOf(21.94)),
                eq(BigDecimal.valueOf(2.83)),
                eq(BigDecimal.valueOf(19.11)));
    }
}