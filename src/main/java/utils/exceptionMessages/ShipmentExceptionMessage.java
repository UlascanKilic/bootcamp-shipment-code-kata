package utils.exceptionMessages;

public enum ShipmentExceptionMessage {
    BASKET_IS_EMPTY("User's basket is empty!");

    private final String message;

    ShipmentExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
