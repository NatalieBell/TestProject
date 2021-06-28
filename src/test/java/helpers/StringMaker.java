package helpers;

import java.util.UUID;

public class StringMaker {

    public static String createRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
