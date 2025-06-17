package price_comparison_website;

import shop.OnlineShop;
import shop.ShopItem;

import java.util.*;
import java.util.stream.Collectors;

public class PriceComparisonWebsite {
    private final List<OnlineShop> onlineShops;
    private final HashMap<OnlineShop, List<Integer>> shopRatings = new HashMap<>();

    public PriceComparisonWebsite(List<OnlineShop> onlineShops) {
        this.onlineShops = new ArrayList<>(onlineShops);
    }

    public void addShop(OnlineShop shop) {
        onlineShops.add(shop);
    }

    public void addRating(OnlineShop shop, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        shopRatings.computeIfAbsent(shop, k -> new ArrayList<>()).add(rating);
    }

    public OnlineShop findCheapestOffer(String itemName) {
        String lowerItemName = itemName.toLowerCase();
        OnlineShop cheapestShop = null;
        double lowestPrice = Double.MAX_VALUE;

        for (OnlineShop shop : onlineShops) {
            for (ShopItem item : shop.getAvailableItems()) {
                if (item.getName().toLowerCase().contains(lowerItemName)) {
                    if (item.getPrice() < lowestPrice) {
                        lowestPrice = item.getPrice();
                        cheapestShop = shop;
                    }
                }
            }
        }

        return cheapestShop;
    }

    public OnlineShop findOfferWithBestRatedShop(String itemName) {
        String lowerItemName = itemName.toLowerCase();
        OnlineShop bestShop = null;
        double highestAvgRating = -1;

        for (OnlineShop shop : onlineShops) {
            boolean hasItem = shop.getAvailableItems().stream()
                .anyMatch(item -> item.getName().toLowerCase().contains(lowerItemName));
            if (!hasItem) continue;

            List<Integer> ratings = shopRatings.getOrDefault(shop, List.of());
            if (ratings.isEmpty()) continue;

            double avg = ratings.stream().mapToInt(i -> i).average().orElse(0.0);
            if (avg > highestAvgRating) {
                highestAvgRating = avg;
                bestShop = shop;
            }
        }

        return bestShop;
    }

    public List<ShopItem> findSortedItemsInAllShops(String itemName) {
        String lowerItemName = itemName.toLowerCase();
        List<ShopItem> result = new ArrayList<>();

        //faulty

        result.sort(Comparator.comparingDouble(ShopItem::getPrice));
        return result;
    }
}
