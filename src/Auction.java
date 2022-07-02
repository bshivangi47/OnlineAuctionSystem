import java.util.ArrayList;

/**
 * Auction --- Supporting class
 * @author : Shivangi Bhatt
 */
public class Auction {

    //define class variables

    String openAuctionStatus = "open";
    String closedAuctionStatus = "closed";
    String newAuctionStatus = "new";

    private String auctionName;
    private int firstLotNumber;
    private int lastLotNumber;
    private int minBidIncrement;
    private ArrayList<Lot> lots = new ArrayList<Lot>();
    private String auctionStatus = newAuctionStatus;

    //Constructor sets the auctionName, first lot number, last lot number and min bid increment.

    Auction(String auctionName, int firstLotNumber, int lastLotNumber, int
            minBidIncrement) {

        this.auctionName = auctionName;
        this.firstLotNumber = firstLotNumber;
        this.lastLotNumber = lastLotNumber;
        this.minBidIncrement = minBidIncrement;

    }

    /*
     * method name : openAuction
     * method purpose : Set the auction as open
     * arguments : none
     * return value : Returns true if auction is open, and false otherwise
     */
    public boolean openAuction() {

        if (this.auctionStatus.equalsIgnoreCase(newAuctionStatus)) {
            this.auctionStatus = openAuctionStatus;
            return true;
        } else if (this.auctionStatus.equalsIgnoreCase(openAuctionStatus)) {
            return true;
        } else {
            return false;
        }

    }

    /*
     * method name : closeAuction
     * method purpose : Set the auction as close
     * arguments : none
     * return value : Returns true if auction is close, and false otherwise
     */
    public boolean closeAuction() {

        if (this.auctionStatus.equalsIgnoreCase(closedAuctionStatus)) {
            return true;
        } else if (this.auctionStatus.equalsIgnoreCase(openAuctionStatus)) {
            this.auctionStatus = closedAuctionStatus;
            return true;
        } else {
            return false;
        }

    }

    /*
     * method name : winningBids
     * method purpose : Gived the winning bids for each lot in an auction
     * arguments : none
     * return value : Returns the string of lot number, current winning bid and bidder id of winning bid for each lot
     */
    public String winningBids() {
        ArrayList<Lot> allLots = this.lots;
        String outputString = "";
        for (Lot lot : allLots) {
            if (lot.getLotnumber() >= firstLotNumber && lot.getLotnumber() <= lastLotNumber) {

                outputString += lot.getLotnumber() + "\t" + lot.getCurrentWinningBid() + "\t" + lot.getBidderIdOfCurrentWinningBid() + "\n";
            }
        }
        return outputString;

    }

    /*
     * method name : getAuctionName
     * method purpose : Get the name of the auction
     * arguments : none
     * return value : Returns string value of the auction Name.
     */
    public String getAuctionName() {

        return auctionName;

    }

    /*
     * method name : getAuctionStatus
     * method purpose : Get the status of the auction
     * arguments : none
     * return value : Returns string value of the auction status.
     */
    public String getAuctionStatus() {

        return auctionStatus;

    }

    /*
     * method name : getFirstLotNumber
     * method purpose : Gets the first lot number of the auction
     * arguments : none
     * return value : Returns integer value for first lot number.
     */
    public int getFirstLotNumber() {
        return firstLotNumber;
    }

    /*
     * method name : getLastLotNumber
     * method purpose : Gets the last lot number of the auction
     * arguments : none
     * return value : Returns integer value for last lot number.
     */
    public int getLastLotNumber() {
        return lastLotNumber;
    }

    /*
     * method name : sumOfWinningBids
     * method purpose : Gets the sum of all the winning bids in all the lots of the auction
     * arguments : none
     * return value : Returns integer value for sum of winning bids.
     */
    public int sumOfWinningBids() {
        ArrayList<Lot> allLots = this.lots;
        int sum = 0;
        for (Lot lot : allLots) {
            if (lot.getLotnumber() >= firstLotNumber && lot.getLotnumber() <= lastLotNumber) {

                sum += lot.getCurrentWinningBid();
            }
        }
        return sum;
    }

    public ArrayList<Lot> getLots() {
        return lots;
    }




}
