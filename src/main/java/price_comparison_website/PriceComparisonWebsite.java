package price_comparison_website;

import shop.OnlineShop;
import shop.ShopItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PriceComparisonWebsite {
    private final List<OnlineShop> onlineShops;
    private final HashMap<OnlineShop, List<Integer>> shopRatings = new HashMap<>();

    public PriceComparisonWebsite(List<OnlineShop> onlineShops) {
        this.onlineShops = onlineShops;
    }

    public void addShop(OnlineShop shop) {
        onlineShops.add(shop);
    }

    public void addRating(OnlineShop shop, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        if (!shopRatings.containsKey(shop)) {
            shopRatings.put(shop, new ArrayList<>());
        }
        shopRatings.get(shop).add(rating);
    }

    public OnlineShop findCheapestOffer(String itemName) {
        // TODO implement logic to find the online shop that offers the cheapest price for the given item name
        // case-insensitive contains search
        return null;
    }

    public OnlineShop findOfferWithBestRatedShop(String itemName) {
        // TODO implement logic to find the online shop with the best rating that offers the given item name
        // case-insensitive contains search
        return null;
    }

    public List<ShopItem> findSortedItemsInAllShops(String itemName) {
        // TODO implement logic to find all items with the given name in all shops and return them sorted by price ascending
        // case-insensitive contains search
        return new ArrayList<>();
    }
}
