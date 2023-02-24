package ru.clevertec.cheque;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Processor {
    private final DataStorage dataStorage;

    public Processor(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public Map<String, String> parseArguments(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (String arg : args) {
            String[] arguments = arg.split("-");
            map.put(arguments[0], arguments[1]);
        }
        return map;
    }

    public boolean validateArguments(Map<String, String> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException("No arguments found");
        }
        Set<Long> itemIds = dataStorage.getItems().keySet();
        List<Long> cardNumbers = dataStorage.getCardNumbers();
        args.forEach((id, quantity) -> {
            if ("card".equals(id)) {
                if (!cardNumbers.contains(Long.valueOf(quantity))) {
                    throw new IllegalArgumentException("Card number not found");
                }
                return;
            }

            if (!itemIds.contains(Long.valueOf(id))) {
                throw new IllegalArgumentException("One of your products is not found");
            }
        });
        return true;
    }

    public List<Map<String, String>> countTotalAndDiscount(Map<String, String> args) {
        List<Map<String, String>> arguments = new ArrayList<>();
        args.forEach((id, quantity) -> {
            if (!"card".equals(id)) {
                Map<String, String> itemData = new HashMap<>();
                itemData.put("quantity", quantity);
                String itemName = dataStorage.getItems().get(Long.valueOf(id));
                itemData.put("itemName", itemName);
                BigDecimal itemPrice = dataStorage.getPriceList().get(Long.valueOf(id));
                itemData.put("itemPrice", String.valueOf(itemPrice));
                BigDecimal totalPrice = itemPrice.multiply(BigDecimal.valueOf(Long.parseLong(quantity)));
                itemData.put("totalPrice", String.valueOf(totalPrice));

                BigDecimal discount = BigDecimal.ZERO;
                if (itemName.contains("!") && Integer.parseInt(quantity) > 5) {
                    discount = BigDecimal.valueOf(Long.parseLong(quantity))
                            .multiply(itemPrice)
                            .multiply(BigDecimal.valueOf(0.1));
                }
                if (args.containsKey("card")) {
                    discount = discount.add(totalPrice.multiply(BigDecimal.valueOf(0.05)));
                }
                itemData.put("discount", String.valueOf(discount));
                arguments.add(itemData);
            }
        });
        return arguments;
    }

    public BigDecimal calculateTotal(List<Map<String, String>> args) {
        return args.stream()
                .map(map -> map.get("totalPrice"))
                .map(Double::valueOf)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal::add)
                .get()
                .setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal calculateDiscount(List<Map<String, String>> args) {
        return args.stream()
                .map(map -> map.get("discount"))
                .map(Double::valueOf)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal::add)
                .get()
                .setScale(2, RoundingMode.CEILING);

    }

    public BigDecimal calculateTotalWithDiscount(BigDecimal total, BigDecimal discount) {
        return total.subtract(discount);
    }

}
