package derevo;

public enum Color {

    BLACK(true),
    RED(false);

    private final boolean value;

    private Color(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
