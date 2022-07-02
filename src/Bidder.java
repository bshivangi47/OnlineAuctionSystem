/**
 * Auction --- Supporting Bidder
 * @author : Shivangi Bhatt
 */
public class Bidder {

    //define class variables
    private int bidderId;
    private int totalWinningBids=0;
    private int totalLotsWon=0;
    private String bidderName;

    //Constructor sets the name of the bidder.
    Bidder(String bidderName) {
        this.bidderName = bidderName;
    }


    /*
     * method name : storeBidderId
     * method purpose : Stores bidder id
     * arguments : An integer id
     * return value : No return value.
     */
    public void storeBidderId(int id){
        this.bidderId=id;
    }


    /*
     * method name : getBidderId
     * method purpose : Gets bidder id
     * arguments : none
     * return value : Returns integer value of bidder id.
     */
    public int getBidderId() {
        return bidderId;
    }

    /*
     * method name : getBidderName
     * method purpose : Gets bidder name
     * arguments : none
     * return value : Returns String value of bidder name.
     */
    public String getBidderName() {
        return bidderName;
    }

    /*
     * method name : getTotalWinningBids
     * method purpose : Gets the sum of the bids for all the lots that the bidder have won.
     * arguments : none
     * return value : Returns integer value of sum of winning bids.
     */
    public int getTotalWinningBids() {
        return totalWinningBids;
    }

    /*
     * method name : setTotalWinningBids
     * method purpose : Set the value of total winning bids
     * arguments : integer totalWinningBids
     * return value : No return value.
     */
    public void setTotalWinningBids(int totalWinningBids) {
        this.totalWinningBids = totalWinningBids;
    }

    /*
     * method name : getTotalLotsWon
     * method purpose : Gets the number of lots won by the bidder
     * arguments : none
     * return value : Returns integer value of total lots won by the bidder.
     */
    public int getTotalLotsWon() {
        return totalLotsWon;
    }

    /*
     * method name : setTotalLotsWon
     * method purpose : Sets the number of lots won by the bidder
     * arguments : none
     * return value : No return value.
     */
    public void setTotalLotsWon(int totalLotsWon) {
        this.totalLotsWon = totalLotsWon;
    }


}
