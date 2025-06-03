package com.homework;

public class Errors {

    public static class DafNotFoundException extends Exception {
        public DafNotFoundException(String dafName) {
            super("דף " + dafName + " לא נמצא במסכת");
        }
    }

    public static class MasechetNotFoundException extends Exception {
        public MasechetNotFoundException(String masechetName) {
            super(masechetName + " לא נמצאה בקובץ");
        }
    }

    public static class InvalidSearchFormatException extends Exception {
        public InvalidSearchFormatException(String input) {
            super("קלט לא תקין: " + input + " (צורת קלט צפויה: (מסכת) <שם> דף <מספר> <א/ב>)");
        }
    }

    public static class TheTextNotFoundException extends Exception {
        public TheTextNotFoundException(String searchText) {
            super("הטקסט \"" + searchText + "\" לא נמצא");
        }
    }

    public static class TheObjectsWereNotCreatedException extends Exception {
        public TheObjectsWereNotCreatedException(String message) {
            super("האובייקטים לא נוצרו כמצופה: " + message);
        }
    }
}
