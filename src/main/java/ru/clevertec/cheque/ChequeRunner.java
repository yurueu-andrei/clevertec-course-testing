package ru.clevertec.cheque;

public class ChequeRunner {
    public static void main(String[] args) {
        CashMachine cashMachine = new CashMachine(new Processor(new DataStorage()), new Printer());
        cashMachine.printCheque(args);
    }
}
