import com.myworktech.trendbar.model.Quote;
import com.myworktech.trendbar.model.Symbol;
import com.myworktech.trendbar.service.QuoteProvider;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class TestQuoteProvider implements QuoteProvider {

    @Override
    public Quote getQuote() {
        return new Quote(Symbol.getInstance("USDRUB"), next(), LocalDateTime.now());
    }

    private AtomicReference<Long> refCounter = new AtomicReference(null);

    public Long next() {
        Long oldValue;
        Long newValue;
        do {
            oldValue = refCounter.get();
            newValue = oldValue == null ? 1L : oldValue + 1;
        } while (!refCounter.compareAndSet(oldValue, newValue));
        return newValue;
    }
}
