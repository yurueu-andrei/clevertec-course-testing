package ru.clevertec.cheque;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class Printer {

    public void printCheque(
            List<Map<String, String>> items,
            BigDecimal total,
            BigDecimal discount,
            BigDecimal totalWithDiscount
    ) {
        System.out.println("\t\t\t\t\tCASH RECEIPT");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\tSUPERMARKET 123\t\t\t\t\t\t\t\t\t\t\t");
        System.out.println("\ti2, MILKYWAY Galaxy/Earth\t\t\t\t\t\t\t\t");
        System.out.println("\tTEL: +375-25-999-99-99\t\t\t\t\t\t\t\t\t");
        System.out.println("\tCASHIER: Andrei Yurueu\t\t\t\tDATE: " + LocalDate.now() + "\t");
        System.out.println("\t\t\t\t\t\t\tTIME: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "\t\t");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\tQTY\t\tDESCRIPTION\t\t\t\tPRICE\t\tTOTAL\t\t");

        items.forEach(map -> {
            System.out.print("\t" + map.get("quantity") + "\t\t");
            System.out.print(map.get("itemName") + "\t\t\t\t\t");
            System.out.print(map.get("itemPrice") + "$\t\t");
            System.out.print(map.get("totalPrice") + "$");
            System.out.println();
        });

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\tTOTAL:\t\t\t\t\t\t" + total + "$");
        System.out.println("\tDISCOUNT:\t\t\t\t\t" + discount + "$");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
        System.out.println("\tTOTAL WITH DISCOUNT:\t\t\t\t" + totalWithDiscount + "$");
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
