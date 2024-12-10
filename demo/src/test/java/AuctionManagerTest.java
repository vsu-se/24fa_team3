import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.models.Auction;
import com.example.demo.models.Category;
import com.example.demo.utils.AuctionManager;

import java.util.List;

public class AuctionManagerTest {

    private List<Auction> allAuctions = AuctionManager.getInstance().getAllAuctions();

    public static void main(String[] args) {
        AuctionManagerTest test = new AuctionManagerTest();
        test.testDriver();

    }

    public void testDriver() {
        Category category = new Category("Electronics");

        //AuctionManager.getInstance().makeMockAuctions();
        System.out.println("Testing auction filtering: ");
        testFiltering();
        System.out.println("Testing auction managing: ");
        testManaging();

    }

    public void testFiltering() {
        testAllNullFilters();
        testCategoryFilterOnly();
        testEndedFilterOnly();
        testOwnedByUserFilterOnly();
        testCategoryAndEndedFilters();
        testCategoryAndOwnedByUserFilters();
        testEndedAndOwnedByUserFilters();
        testAllFilters();
    }

    public void testManaging() {
        testAddAuction();
        testRemoveAuction();
        testClearAllAuctions();
    }

    @Test
    public void testAddAuction() {
        Auction auction = new Auction("Item9", null, true, false, null, null);
        addAuction(auction);
        assertTrue(allAuctions.contains(auction), "Test failed: Auction not added");
        System.out.println("\033[0;32m- Test passed: Add auction\033[0m");
    }

    @Test
    public void testRemoveAuction() {
        Auction auction = new Auction("Item10", null, true, false, null, null);
        addAuction(auction);
        assertTrue(allAuctions.contains(auction), "Test failed: Auction not added");
        AuctionManager.getInstance().removeAuction(auction);
        assertFalse(allAuctions.contains(auction), "Test failed: Auction not removed");
        System.out.println("\033[0;32m- Test passed: Remove auction\033[0m");
    }

    @Test
    public void testClearAllAuctions() {
        Auction auction = new Auction("Item11", null, true, false, null, null);
        addAuction(auction);
        assertTrue(allAuctions.contains(auction), "Test failed: Auction not added");
        AuctionManager.getInstance().clearAuctions();
        assertTrue(allAuctions.isEmpty(), "Test failed: Auctions not cleared");
        System.out.println("\033[0;32m- Test passed: Clear all auctions\033[0m");
    }

    @Test
    public void testAllNullFilters() {
        // All filters null -> return all auctions
        List<Auction> result = getFilteredAuctions(null, null, null);
        assertEquals(result.size(), allAuctions.size(), "Test failed: Size mismatch");
        assertTrue(result.containsAll(allAuctions), "Test failed: Not all auctions present");
        System.out.println("\033[0;32m- Test passed: All null filters\033[0m");
    }

    @Test
    public void testCategoryFilterOnly() {
        // Only category filter applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, null, null);
        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category)));
        System.out.println("\033[0;32m- Test passed: Category filter only\033[0m");
    }

    @Test
    public void testEndedFilterOnly() {
        // Only ended filter applied
        List<Auction> result = getFilteredAuctions(null, true, null);
        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(Auction::isEnded));
        System.out.println("\033[0;32m- Test passed: Ended filter only\033[0m");

    }

    @Test
    public void testOwnedByUserFilterOnly() {
        // Only ownedByUser filter applied
        List<Auction> result = getFilteredAuctions(null, null, true);
        assertEquals(4, result.size());
        assertTrue(result.stream().allMatch(Auction::isOwnedByUser));
        System.out.println("\033[0;32m- Test passed: Owned by user filter only\033[0m");

    }

    @Test
    public void testCategoryAndEndedFilters() {
        // Category and ended filters applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, false, null);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category) && !a.isEnded()));
        System.out.println("\033[0;32m- Test passed: Category and ended filters\033[0m");

    }

    @Test
    public void testCategoryAndOwnedByUserFilters() {
        // Category and ownedByUser filters applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, null, true);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category) && a.isOwnedByUser()));
        System.out.println("\033[0;32m- Test passed: Category and owned by user filters\033[0m");

    }

    @Test
    public void testEndedAndOwnedByUserFilters() {
        // Ended and ownedByUser filters applied
        List<Auction> result = getFilteredAuctions(null, true, true);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.isEnded() && a.isOwnedByUser()));
        System.out.println("\033[0;32m- Test passed: Ended and owned by user filters\033[0m");

    }

    @Test
    public void testAllFilters() {
        // All filters applied
        Category category = new Category("Electronics");
        List<Auction> result = getFilteredAuctions(category, true, true);
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategory().equals(category) && a.isEnded() && a.isOwnedByUser()));
        System.out.println("\033[0;32m- Test passed: All filters\033[0m");

    }

    private List<Auction> getFilteredAuctions(Category category, Boolean ended, Boolean ownedByUser) {
        return AuctionManager.getInstance().getFilteredAuctions(category, ended, ownedByUser);
    }

    private void addAuction(Auction auction) {
        AuctionManager.getInstance().addAuction(auction);
    }

}
