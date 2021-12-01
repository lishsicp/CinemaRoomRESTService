package cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@NoArgsConstructor
@Getter
public class Seats {

    @NonNull
    private int row;

    @NonNull
    private int column;

    @NonNull
    private int price;

    @JsonIgnore
    private final String token = new Token().toString();
    @JsonIgnore
    @Setter
    private boolean reserved;

    public Seats(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    @Override
    public String toString() {
        return "\t{\n" +
                "\"row\":" + row + ",\n" +
                "\"column\":" + column + "\n" +
                "\"price\":" + price + "\n" +
                "}\n";
    }

    public String returnSeat() {
        setReserved(false);
        return String.format("{\n" +
                "\t\"returned_ticket\": {\n" +
                "\t\t\"row\": %s,\n" +
                "\t\t\"column\": %s,\n" +
                "\t\t\"price\": %s\n" + "\n}" +
                "}", getRow(), getColumn(), getPrice());
    }

    public String bookSeat() {
        setReserved(true);
        return String.format("{\n" +
                        "\t\"token\": %s,\n" +
                        "\t\"ticket\": {\n" +
                        "\t\t\"row\": %s,\n" +
                        "\t\t\"column\": %s,\n" +
                        "\t\t\"price\": %s\n" + "\n}" +
                        "}", getToken(), getRow(), getColumn(), getPrice());
    }

}
