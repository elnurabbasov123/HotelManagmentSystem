package enums;

public enum RoomType {
    STANDART("standart"),
    DELUX("delux"),
    TRIPLE("triple"),
    VIP("vip");

    private final String value;

    RoomType(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
