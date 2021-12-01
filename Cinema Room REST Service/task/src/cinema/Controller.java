package cinema;

import cinema.entities.Cinema;
import cinema.entities.Seats;
import cinema.entities.Token;
import cinema.exceptions.JSONExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
public class Controller {

    private final Cinema cinema = new Cinema();


    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> checkSeat(@RequestBody Seats seat) {
        if (seat.getRow() > 9
                || seat.getColumn() > 9
                || seat.getRow() < 1
                || seat.getColumn() < 1) {
            return new ResponseEntity<>(JSONExceptions.outOfBounds()
                    , HttpStatus.BAD_REQUEST);
        }
        List<Seats> seats = cinema.getAvailableSeats();
        int arrPos = cinema.getArrayPosition(seat.getRow(), seat.getColumn());
        Seats seatToBook = seats.get(arrPos);

        if (seatToBook.isReserved())
            return new ResponseEntity<>(JSONExceptions.alreadyPurchased(),
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(seatToBook.bookSeat(), HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnSeat(@RequestBody Token token) {
        Optional<Seats> st = cinema.findSeat(token);
        if (st.isEmpty()) {
            return new ResponseEntity<>(JSONExceptions.wrongToken(),
                    HttpStatus.BAD_REQUEST);
        }
        Seats s = st.get();
        return new ResponseEntity<>(s.returnSeat(), HttpStatus.OK);
    }

    @PostMapping("/stats")
    public ResponseEntity<String> returnStats(@RequestParam(value = "password", required = false) @RequestBody String password) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity<>(JSONExceptions.wrongPassword(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(cinema.getStats(), HttpStatus.OK);
    }
}
