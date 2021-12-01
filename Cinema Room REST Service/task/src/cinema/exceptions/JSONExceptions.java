package cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class JSONExceptions {

    public static String outOfBounds() {
        return "{ \"error\": \"The number of a row or a column is out of bounds!\" }";
    }

    public static String alreadyPurchased() {
        return "{ \"error\": \"The ticket has been already purchased!\" }";
    }

    public static String wrongToken() {
        return "{ \"error\": \"Wrong token!\" }";
    }

    public static String wrongPassword() { return "{ \"error\": \"The password is wrong!\" }";}

}
