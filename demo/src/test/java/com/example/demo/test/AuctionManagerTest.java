package com.example.demo.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.models.Auction;
import com.example.demo.models.Category;
import com.example.demo.utils.AuctionManager;

import java.util.List;

public class AuctionManagerTest {

    private List<Auction> allAuctions = AuctionManager.getInstance().getAllAuctions();

    @Test
    public void testAddAuction() {
        Auction auction = new Auction("Item9", null, true, false, 5, null);
        addAuction(auction);
        assertTrue(allAuctions.contains(auction), "Test failed: Auction not added");
    }

    @Test
    public void testRemoveAuction() {
        Auction auction = new Auction("Item10", null, true, false, 5, null);
        addAuction(auction);
        assertTrue(allAuctions.contains(auction), "Test failed: Auction not added");
        AuctionManager.getInstance().removeAuction(auction);
        assertFalse(allAuctions.contains(auction), "Test failed: Auction not removed");
    }

    @Test
    public void testClearAllAuctions() {
        Auction auction = new Auction("Item11", null, true, false, 5, null);
        addAuction(auction);
        assertTrue(allAuctions.contains(auction), "Test failed: Auction not added");
        AuctionManager.getInstance().clearAuctions();
        assertTrue(allAuctions.isEmpty(), "Test failed: Auctions not cleared");
    }

    @Test
    public void testAllNullFilters() {
        // All filters null -> return all auctions
        List<Auction> result = getFilteredAuctions(null, null, null);
        assertEquals(result.size(), allAuctions.size(), "Test failed: Size mismatch");
        assertTrue(result.containsAll(allAuctions), "Test failed: Not all auctions present");
    }

    @Test
    public void testCategoryFilterOnly() {
        // Only category filter applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, null, null);
        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category)));
    }

    @Test
    public void testEndedFilterOnly() {
        // Only ended filter applied
        List<Auction> result = getFilteredAuctions(null, true, null);
        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(Auction::isEnded));
    }

    @Test
    public void testOwnedByUserFilterOnly() {
        // Only ownedByUser filter applied
        List<Auction> result = getFilteredAuctions(null, null, true);
        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(Auction::isOwnedByUser));
    }

    @Test
    public void testCategoryAndEndedFilters() {
        // Category and ended filters applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, false, null);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category) && !a.isEnded()));
    }

    @Test
    public void testCategoryAndOwnedByUserFilters() {
        // Category and ownedByUser filters applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, null, true);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category) && a.isOwnedByUser()));
    }

    @Test
    public void testEndedAndOwnedByUserFilters() {
        // Ended and ownedByUser filters applied
        List<Auction> result = getFilteredAuctions(null, true, true);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.isEnded() && a.isOwnedByUser()));
    }

    @Test
    public void testAllFilters() {
        // All filters applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, true, true);
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category) && a.isEnded() && a.isOwnedByUser()));
    }

    private List<Auction> getFilteredAuctions(Category category, Boolean ended, Boolean ownedByUser) {
        return AuctionManager.getInstance().getFilteredAuctions(category, ended, ownedByUser);
    }

    private void addAuction(Auction auction) {
        AuctionManager.getInstance().addAuction(auction);
    }

}
