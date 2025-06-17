package price_comparison_website;

import bank.Bank;
import main.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.OnlineShop;
import shop.ShopItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PriceComparisonWebsiteTest {

    private Bank bank;
    private Person alice, bob, clara;
    private OnlineShop shop1, shop2;
    private PriceComparisonWebsite comparison;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        alice = new Person("Alice");
        bob = new Person("Bob");
        clara = new Person("Clara");

        bank.registerCustomer(alice, 100.0);
        bank.registerCustomer(bob, 50.0);
        bank.registerCustomer(clara, 30.0);

        shop1 = new OnlineShop(bank, bob, "Bob's Beverages");
        shop2 = new OnlineShop(bank, clara, "Clara's Cola Corner");

        shop2.addItem(new ShopItem("Spezi", 15.0)); // cheaper item

        comparison = new PriceComparisonWebsite(List.of(shop1, shop2));

        comparison.addRating(shop1, 4);
        comparison.addRating(shop1, 5);
        comparison.addRating(shop2, 3);
    }

    @Test
    public void testFindCheapestOffer() {
        OnlineShop cheapest = comparison.findCheapestOffer("spezi");
        assertNotNull(cheapest);
        assertEquals("Clara's Cola Corner", cheapest.getName());
    }

    @Test
    public void testFindOfferWithBestRatedShop() {
        OnlineShop bestRated = comparison.findOfferWithBestRatedShop("cola");
        assertNotNull(bestRated);
        assertEquals("Bob's Beverages", bestRated.getName());
    }

    @Test
    public void testFindSortedItemsInAllShops() {
        List<ShopItem> items = comparison.findSortedItemsInAllShops("spezi");
        assertEquals(3, items.size());
        assertTrue(items.get(0).getPrice() <= items.get(1).getPrice());
        assertEquals(15.0, items.get(0).getPrice(), 0.01);
        assertEquals(20.0, items.get(1).getPrice(), 0.01);
    }

    @Test
    public void testAddInvalidRatingThrows() {
        assertThrows(IllegalArgumentException.class, () -> comparison.addRating(shop1, 0));
        assertThrows(IllegalArgumentException.class, () -> comparison.addRating(shop2, 6));
    }
}
