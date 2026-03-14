/**
 * Enums are a good example of the use of nested classes
 */
class Price {
    private long value;
    private Currency currency;

    enum Currency {
        EUR, GBP, USD
    }
}