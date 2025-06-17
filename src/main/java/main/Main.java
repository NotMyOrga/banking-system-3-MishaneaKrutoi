package main;

import bank.Bank;
import main.Person;
import price_comparison_website.PriceComparisonWebsite;
import shop.OnlineShop;
import shop.ShopItem;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        Person clara = new Person("Clara");

        bank.registerCustomer(alice, 100.0);
        bank.registerCustomer(bob, 50.0);
        bank.registerCustomer(clara, 30.0);

        OnlineShop shop1 = new OnlineShop(bank, bob, "Bob's Beverages");
        OnlineShop shop2 = new OnlineShop(bank, clara, "Clara's Cola Corner");

        // Add a cheaper Spezi to shop2
        shop2.addItem(new ShopItem("Spezi", 15.0));

        PriceComparisonWebsite comparison = new PriceComparisonWebsite(List.of(shop1, shop2));

        comparison.addRating(shop1, 4);
        comparison.addRating(shop1, 5);
        comparison.addRating(shop2, 3);

        // Search for cheapest shop offering "Spezi"
        OnlineShop cheapestSpeziShop = comparison.findCheapestOffer("Spezi");
        System.out.println("Cheapest Spezi found in: " + (cheapestSpeziShop != null ? cheapestSpeziShop.getName() : "none"));

        // Buy cheapest Spezi
        if (cheapestSpeziShop != null) {
            ShopItem spezi = cheapestSpeziShop.getAvailableItems().stream()
                    .filter(i -> i.getName().equalsIgnoreCase("Spezi"))
                    .min((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
                    .orElseThrow();

            cheapestSpeziShop.addToCart(alice, spezi);
            boolean bought = cheapestSpeziShop.placeOrder(alice);
            if (bought) {
                System.out.println("Alice successfully bought " + spezi.getName() + " for " + spezi.getPrice() + " from " + cheapestSpeziShop.getName());
            } else {
                System.out.println("Alice could not buy Spezi from " + cheapestSpeziShop.getName());
            }
        }

        // Find shop with best rating that has "Cola"
        OnlineShop bestRatedColaShop = comparison.findOfferWithBestRatedShop("Cola");
        System.out.println("Best-rated shop offering Cola: " + (bestRatedColaShop != null ? bestRatedColaShop.getName() : "none"));
    }
}
