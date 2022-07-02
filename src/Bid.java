/**
 * Bid --- Supporting class
 * @author : Shivangi Bhatt
 */
public class Bid {

    //define class variables

    private int bidderId;
    private int bid;
    private int acceptedBid;

    //Constructor sets the bidder id, accepted bid for a lot and the original bid placed by the bidder

    Bid(int bidderId, int acceptedBid, int bid) {
        this.bidderId = bidderId;
        this.acceptedBid = acceptedBid;
        this.bid = bid;
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
     * method name : getBid
     * method purpose : Gets the original bid placed by the bidder
     * arguments : none
     * return value : Returns integer value of bid.
     */
    public int getBid() {
        return bid;
    }

    /*
     * method name : getAcceptedBid
     * method purpose : Gets accepted bid
     * arguments : none
     * return value : Returns the accepted bid
     */
    public int getAcceptedBid() {
        return acceptedBid;
    }

    /*
     * method name : setBid
     * method purpose : set the original bid value
     * arguments : integer bid
     * return value : no return value
     */
    public void setBid(int bid) {
        this.bid = bid;
    }

    /*
     * method name : setAcceptedBid
     * method purpose : set the accepted bid
     * arguments : integer accepted Bid
     * return value : no return value
     */
    public void setAcceptedBid(int acceptedBid) {
        this.acceptedBid = acceptedBid;
    }

}
