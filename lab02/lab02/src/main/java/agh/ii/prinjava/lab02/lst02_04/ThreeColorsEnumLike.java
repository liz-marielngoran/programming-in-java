final class ThreeColorsEnumLike {
    public final static ThreeColorsEnumLike BLUE =
            new ThreeColorsEnumLike("BLUE", new Actor("Juliette", "Binoche"));

    public final static ThreeColorsEnumLike WHITE =
            new ThreeColorsEnumLike("WHITE", new Actor("Zbigniew", "Zamachowski"));

    public final static ThreeColorsEnumLike RED =
            new ThreeColorsEnumLike("BLUE", new Actor("Irene", "Jacob"));

    // The constructors should be private
    private ThreeColorsEnumLike(String name, Actor leadingActor) {
        this.name = name;
        this.leadingActor = leadingActor;
    }

    // Why should this be static?
    private final static ThreeColorsEnumLike[] values = {BLUE, WHITE, RED};

    public static ThreeColorsEnumLike[] values() {
        return values;
    }

    // And why should this not be?
    private final String name;

    public String name() {
        return name;
    }

    private final Actor leadingActor;

    public Actor getLeadingActor() {
        return leadingActor;
    }
}