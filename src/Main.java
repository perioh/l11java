import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("toys.txt"));
            List<Toy> toys = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                Toy toy = Toy.tryFrom(line);
                toys.add(toy);
            }

            toys.sort((a, b) -> Integer.compare(a.getPrice(), b.getPrice()));

            toys.stream()
                    .filter(toy -> toy.getName().equals("constructor"))
                    .forEach(toy -> System.out.println(
                            String.format("%s (%s) - %s", toy.getName(), toy.getAge(), Price.from(toy.getPrice()))));

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Toy {
    private String name;
    private ToySpecific specific;
    private int price;
    private String age;
    private int toy_specific;


    public Toy(String name, ToySpecific specific, int price, String age,int toy_specific) {
        this.name = name;
        this.specific = specific;
        this.price = price;
        this.age = age;
        this.toy_specific=toy_specific;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getAge() {
        return age;
    }

    public static Toy tryFrom(String value) {
        String[] splitData = value.split(",");
        String toyName = splitData[0];
        String toyPrice = splitData[1];
        String toyAge = splitData[2];
        String toySpecific = splitData[3];

        ToySpecific toy_type;

        switch (toyName.toLowerCase()) {
            case "doll":
                toy_type =  ToySpecific.Doll;
                break;
            case "constructor":
                toy_type =  ToySpecific.Constructor;
                break;
            case "ball":
                toy_type =  ToySpecific.Ball;
                break;
            case "bricks":
                toy_type =  ToySpecific.Bricks;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported toy type");
        }

        return new Toy(toyName, toy_type, Integer.parseInt(toyPrice), toyAge,Integer.parseInt(toySpecific));
    }
}

enum ToySpecific {
    Doll,
    Ball,
    Bricks,
    Constructor
}

class Price {
    private int hrn;
    private int cop;

    public Price(int hrn, int cop) {
        this.hrn = hrn;
        this.cop = cop;
    }

    public static Price from(int value) {
        int cop = value % 100;
        int hrn = (value - cop) / 100;
        return new Price(hrn, cop);
    }

    @Override
    public String toString() {
        return String.format("%dгрн %dкоп", hrn, cop);
    }
}
