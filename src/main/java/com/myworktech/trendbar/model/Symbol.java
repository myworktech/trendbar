package com.myworktech.trendbar.model;

import lombok.Getter;

import java.util.Currency;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Symbol {

    private static final ConcurrentMap<String, Symbol> instances = new ConcurrentHashMap<>();

    static {
        instances.put("EURUSD", new Symbol(Currency.getInstance("EUR"), Currency.getInstance("USD")));
        instances.put("USDJPY", new Symbol(Currency.getInstance("USD"), Currency.getInstance("JPY")));
        instances.put("GBPUSD", new Symbol(Currency.getInstance("GBP"), Currency.getInstance("USD")));
        instances.put("AUDUSD", new Symbol(Currency.getInstance("AUD"), Currency.getInstance("USD")));
        instances.put("USDCHF", new Symbol(Currency.getInstance("USD"), Currency.getInstance("CHF")));
        instances.put("NZDUSD", new Symbol(Currency.getInstance("NZD"), Currency.getInstance("USD")));
        instances.put("USDCAD", new Symbol(Currency.getInstance("USD"), Currency.getInstance("CAD")));
    }

    @Getter
    private final Currency baseCurrency;
    @Getter
    private final Currency quoteCurrency;

    private Symbol(Currency baseCurrency, Currency quoteCurrency) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }

    public static Symbol getInstance(String pair) {
        Symbol symbol = instances.get(pair);
        if (symbol != null)
            return symbol;

        if (pair.length() != 6)
            throw new IllegalArgumentException();

        String first = pair.substring(0, 3);
        String second = pair.substring(3, 6);

        if (first.equals(second))
            throw new IllegalArgumentException("Currencies must be different.");

        Currency baseCurrency = Currency.getInstance(first);
        Currency quoteCurrency = Currency.getInstance(second);

        Symbol newSymbol = new Symbol(baseCurrency, quoteCurrency);

        Symbol instance = instances.putIfAbsent(pair, newSymbol);
        return instance == null ? newSymbol : instance;
    }

    @Override
    public String toString() {
        return baseCurrency.getCurrencyCode() + quoteCurrency.getCurrencyCode();
    }
}
