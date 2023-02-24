package ru.clevertec.cheque;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CashMachine {
    private final Processor processor;
    private final Printer printer;

    public CashMachine(Processor processor, Printer printer) {
        this.processor = processor;
        this.printer = printer;
    }

    public void printCheque(String[] args) {
        try {
            Map<String, String> parsedArgs = processor.parseArguments(args);
            processor.validateArguments(parsedArgs);
            List<Map<String, String>> items = processor.countTotalAndDiscount(parsedArgs);

            BigDecimal total = processor.calculateTotal(items);
            BigDecimal discount = processor.calculateDiscount(items);
            BigDecimal totalWithDiscount = processor.calculateTotalWithDiscount(total, discount);

            printer.printCheque(items, total, discount, totalWithDiscount);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
