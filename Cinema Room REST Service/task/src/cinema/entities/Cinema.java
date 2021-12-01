package cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Cinema {

    private final int totalRows;

    private final int totalColumns;

    private int price;

    @Getter
    private final List<Seats> availableSeats;

    public Cinema() {
        availableSeats = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            for(int j = 1;j < 10; j++) {
                price = i <= 4 ? 10 : 8;
                availableSeats.add(new Seats(i, j, price));
            }
        }
        totalRows = 9;
        totalColumns = 9;
    }

    @JsonIgnore
    public Optional<Seats> findSeat(Token token) {
        Optional<Seats> seat = Optional.of(new Seats());
        seat = availableSeats.stream()
                .filter(x -> x.getToken().equals(token.toString()))
                .findFirst();
        return seat;
    }
    @Override
    public String toString() {
        return "{\n" +
                "\t\"total_rows\":" + totalRows + ",\n" +
                "\t\"total_columns\":" + totalColumns + ",\n" +
                "\t\"available_seats\":" + availableSeats.toString() + "\n" +
                '}';
    }

    @JsonIgnore
    public int getArrayPosition(int row, int column) {
        return (row-1) * 9 + column - 1;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public Cinema(int totalRows, int totalColumns, List<Seats> availableSeats) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = availableSeats;
    }

    @JsonIgnore
    public String getStats() {
        return String.format(
                        "{\n" +
                        "\t\"current_income\": %s,\n" +
                        "\t\"number_of_available_seats\": %s,\n" +
                        "\t\"number_of_purchased_tickets\": %s\n" +
                        '}', getIncome(), getFreeSeats(), getPurchasedTickets()
        );
    }

    private List<Seats> listOfReservedSeats() {
        return availableSeats.stream()
                .filter(Seats::isReserved)
                .collect(Collectors.toList());
    }

    private int getPurchasedTickets() {
        return listOfReservedSeats().size();
    }

    private int getFreeSeats() {
        return availableSeats.size() - listOfReservedSeats().size();
    }

    private int getIncome() {
        return listOfReservedSeats().stream()
                .mapToInt(Seats::getPrice)
                .sum();
    }


}

