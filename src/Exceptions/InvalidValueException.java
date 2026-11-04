package Exceptions;
/**
 * Netinkamų verčių laukui išimtys.
 */
public class InvalidValueException extends GameException {
    private String fieldName;
    private int invalidValue;
    private String invalidObject;

    public InvalidValueException(String message, String fieldName, int invalidValue, String invalidObject) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
        this.invalidObject = invalidObject;

    }

    public String getFieldName() {
        return fieldName;
    }

    public int getInvalidValue() {
        return invalidValue;
    }

    public String getInvalidObject() {
        return invalidObject;
    }
}