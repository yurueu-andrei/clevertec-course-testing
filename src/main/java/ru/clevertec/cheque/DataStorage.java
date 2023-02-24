package ru.clevertec.cheque;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStorage {
    private final HashMap<Long, String> items;
    private final HashMap<Long, BigDecimal> priceList;
    private final List<Long> cardNumbers;

    public DataStorage() {
        this.items = fillItems();
        this.priceList = fillItemsPrice();
        this.cardNumbers = fillCards();
    }

    private HashMap<Long, String> fillItems() {
        HashMap<Long, String> items = new HashMap<>();
        String[] itemNames = {"Eggs !", "Milk !", "Butter", "Cheese", "Bread !", "Onion", "Tomato", "Potato", "Salt", "Sugar"};
        for (int i = 0; i < itemNames.length; i++) {
            items.put((long) i + 1, itemNames[i]);
        }
        return items;
    }

    private HashMap<Long, BigDecimal> fillItemsPrice() {
        HashMap<Long, BigDecimal> prices = new HashMap<>();
        BigDecimal[] itemsPrice = {BigDecimal.valueOf(2.32), BigDecimal.valueOf(1.73),
                BigDecimal.valueOf(3.17), BigDecimal.valueOf(2.95),
                BigDecimal.valueOf(1.46), BigDecimal.valueOf(4.61),
                BigDecimal.valueOf(3.52), BigDecimal.valueOf(0.94),
                BigDecimal.valueOf(1.09), BigDecimal.valueOf(2.09)};
        for (int i = 0; i < itemsPrice.length; i++) {
            prices.put((long) i + 1, itemsPrice[i]);
        }
        return prices;
    }

    private List<Long> fillCards() {
        List<Long> cards = new ArrayList<>();
        for (long i = 1000; i < 1051; i++) {
            cards.add(i);
        }
        return cards;
    }

    public HashMap<Long, String> getItems() {
        return items;
    }

    public HashMap<Long, BigDecimal> getPriceList() {
        return priceList;
    }

    public List<Long> getCardNumbers() {
        return cardNumbers;
    }
}
