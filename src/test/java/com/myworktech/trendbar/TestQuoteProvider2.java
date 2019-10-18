package com.myworktech.trendbar;

import com.myworktech.trendbar.model.Quote;
import com.myworktech.trendbar.model.Symbol;
import com.myworktech.trendbar.service.QuoteProvider;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class TestQuoteProvider2 implements QuoteProvider {

    private final Symbol symbol;

    public TestQuoteProvider2(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public Quote getQuote() {
        return new Quote(symbol, nextPrice(), LocalDateTime.now());
    }

    private AtomicReference<Long> priceCounter = new AtomicReference<>(null);

    public Long nextPrice() {
        Long oldValue;
        Long newValue;
        do {
            oldValue = priceCounter.get();
            newValue = oldValue == null ? 1L : oldValue + 1;
        } while (!priceCounter.compareAndSet(oldValue, newValue));
        return newValue;
    }


}
