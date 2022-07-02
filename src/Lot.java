import java.util.ArrayList;

/**
 * Lot --- Supporting class
 * @author : Shivangi Bhatt
 */
public class Lot {

    //define class variables

    private int lotNumber;
    private String auctionName;
    private int minBidIncrement;
    private int rememberedMaxBid = 0;
    private int currentWinningBid = 0;
    private int nextValidBid = 0;
    private int bidderIdOfRememberedMaxBid = 0;
    private int bidderIdOfCurrentWinningBid = 0;
    private ArrayList<Bid> placedBids = new ArrayList<Bid>();

    //Constructor sets the lotNumber, auctionName to which the lot belongs and minBidIncrement for the lot.

    Lot(int lotNumber, String auctionName, int minBidIncrement) {
        this.lotNumber = lotNumber;
        this.auctionName = auctionName;
        this.minBidIncrement = minBidIncrement;
        this.nextValidBid = minBidIncrement;
    }

    /*
     * method name : getLotnumber
     * method purpose : Get the Lot number
     * arguments : none
     * return value : Returns integer value of Lot number.
     */
    public int getLotnumber() {
        return this.lotNumber;
    }

    /*
     * method name : getAuctionName
     * method purpose : Get the name of the auction
     * arguments : none
     * return value : Returns string value of the auction Name to which the lot belongs.
     */
    public String getAuctionName() {
        return this.auctionName;
    }

    /*
     * method name : placeBid
     * method purpose : Places a bid on the lot.
     * arguments : Integer bidderId (Id of the bidder who placed the bid on the lot), Integer bid (The bid placed on the lot)
     * return value : Returns integer output value.
     */
    public int placeBid(int bidderId, int bid) {
        //set output to 0 initially
        int outputValue = 0;

        //check if there are already any placed Bids on the lot

        if (placedBids.size() == 0) {

            //check if the bid is less than next valid bid.

            if (bid < nextValidBid) {

                //if yes, set the output to 1.

                outputValue = 2;
            }

            //check if the bid is equal to the next valid bid

            else if (bid == nextValidBid) {

                // set the value of current winning bid and bidder id of current winning bid to bid and bidderId respectively.

                this.currentWinningBid = bid;
                this.bidderIdOfCurrentWinningBid = bidderId;

                //set next valid bid

                this.nextValidBid = this.currentWinningBid + minBidIncrement;

                //set the output value to 3

                outputValue = 3;

                //Add the bid to the list of the bids that are placed on the current lot.

                int acceptedBid = this.currentWinningBid;
                placedBids.add(new Bid(bidderId, acceptedBid, bid));
            }
            //if bid is greater than next valid bid
            else {

                // set the value of current winning bid and bidder id of current winning bid to next valid bid and bidderId respectively.

                this.currentWinningBid = this.nextValidBid;
                this.bidderIdOfCurrentWinningBid = bidderId;

                //set next valid bid

                this.nextValidBid = this.currentWinningBid + minBidIncrement;

                // set the value of remembered max bid and bidder id for remembered max bid to bid and bidder Id respectively.

                this.rememberedMaxBid = bid;
                this.bidderIdOfRememberedMaxBid = bidderId;

                //Add the bid to the list of the bids that are placed on the current lot.
                int acceptedBid = this.currentWinningBid;
                placedBids.add(new Bid(bidderId, acceptedBid, bid));

                //set the output value based on if automatic increment is possible

                if (bid >= nextValidBid) {
                    outputValue = 4;
                } else {
                    outputValue = 3;
                }
            }
        }

        //if there are already placed bid on the lot

        else {

            //check if the bid is less than next valid bid.

            if (bid < this.nextValidBid) {

                //if yes, set the output to 1.

                outputValue = 2;
            } else {

                //check if the winning bidder rebids on the lot where bid is greater than the bidder's remembered value

                if(bidderId == this.bidderIdOfCurrentWinningBid && bid >=this.nextValidBid && bid>=this.rememberedMaxBid){
                    //if yes, get the already placed bid by the bidder and set its bid as new bid.
                    for (Bid placedBid : placedBids) {
                        if (placedBid.getBidderId() == bidderId) {
                            placedBid.setBid(bid);
                        }
                    }
                    // perform automatic increment on all other bids
                    automaticIncrement(this.currentWinningBid);

                    //set the output value, remembered bid and bidder id of remembered bid based on whether automatic increment is possible

                    if (bid > nextValidBid) {
                        this.rememberedMaxBid = bid;
                        this.bidderIdOfRememberedMaxBid = bidderId;
                        outputValue = 4;
                    } else {
                        outputValue = 3;
                    }
                }
               else if(bidderId == this.bidderIdOfCurrentWinningBid && bid >=this.nextValidBid && bid<this.rememberedMaxBid){
                    
                    // perform automatic increment on all other bids
                    automaticIncrement(this.currentWinningBid);

                    //set the output value, remembered bid and bidder id of remembered bid based on whether automatic increment is possible

                    if (bid > nextValidBid) {
                        this.rememberedMaxBid = bid;
                        this.bidderIdOfRememberedMaxBid = bidderId;
                        outputValue = 4;
                    } else {
                        outputValue = 3;
                    }
                }
                else {
                    // if no, check if the bid is equal to next valid bid as well as remembered Max bid
                    if (bid == nextValidBid && rememberedMaxBid == 0) {

                        // set the value of current winning bid and bidder id of current winning bid to bid and bidderId respectively.

                        this.currentWinningBid = bid;
                        this.bidderIdOfCurrentWinningBid = bidderId;

                        //set next valid bid


                        this.nextValidBid = this.currentWinningBid + minBidIncrement;
                        int acceptedBid = this.currentWinningBid;

                        // set the bid as new bid if the bidder has placed bid in the past
                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId) {
                                    placedBid.setBid(bid);
                                }
                            }
                        } else {
                            // if not, add the bid to the list of bids placed on the lot
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);
                        outputValue = 3;

                    }

                    // if no, check if the bid is greater that next valid bid and there is no remembered Max bid

                    else if (bid > nextValidBid && rememberedMaxBid == 0) {

                        // set the value of current winning bid and bidder id of current winning bid to next valid bid and bidderId respectively.

                        this.currentWinningBid = this.nextValidBid;
                        this.bidderIdOfCurrentWinningBid = bidderId;

                        //set next valid bid

                        this.nextValidBid = this.currentWinningBid + minBidIncrement;

                        // set the value of remembered max bid and bidder id for remembered max bid to bid and bidder Id respectively.

                        this.rememberedMaxBid = bid;
                        this.bidderIdOfRememberedMaxBid = bidderId;

                        // set the bid as new bid if the bidder has placed bid in the past
                        int acceptedBid = this.currentWinningBid;

                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId) {
                                    placedBid.setBid(bid);
                                }
                            }
                        } else {
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);

                        // set the output value based on whether automatic increment is possible
                        if (bid >= nextValidBid) {
                            outputValue = 4;
                        } else {
                            outputValue = 3;
                        }
                    }
                    else if (bid == nextValidBid && bid == rememberedMaxBid && rememberedMaxBid != 0) {

                        // set the value of current winning bid and bidder id of current winning bid to bid and bidder Id of remembered max bid respectively.

                        this.currentWinningBid = bid;
                        this.bidderIdOfCurrentWinningBid = this.bidderIdOfRememberedMaxBid;

                        //set next valid bid

                        this.nextValidBid = this.currentWinningBid + minBidIncrement;

                        // set the value of remembered max bid and bidder id for remembered max bid to 0 and 0 respectively.
                        this.rememberedMaxBid = 0;
                        this.bidderIdOfRememberedMaxBid = 0;

                        // set the bid as new bid if the bidder has placed bid in the past

                        int acceptedBid = this.currentWinningBid;
                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId) {
                                    placedBid.setBid(bid);
                                }
                            }
                        } else {
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);

                        // set the output value based on whether automatic increment is possible

                        if (bidderId == this.bidderIdOfCurrentWinningBid) {
                            outputValue = 3;
                        } else {
                            outputValue = 2;
                        }
                    }
                    else if (bid == nextValidBid && bid > rememberedMaxBid && rememberedMaxBid != 0) {

                        // set the value of current winning bid and bidder id of current winning bid to bid and bidderId respectively.

                        this.currentWinningBid = bid;
                        this.bidderIdOfCurrentWinningBid = bidderId;

                        // set the value of remembered max bid and bidder id for remembered max bid to 0 and 0 respectively.
                        this.rememberedMaxBid = 0;
                        this.bidderIdOfRememberedMaxBid = 0;

                        //set next valid bid

                        this.nextValidBid = this.currentWinningBid + minBidIncrement;
                        int acceptedBid = this.currentWinningBid;

                        // set the bid as new bid if the bidder has placed bid in the past

                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId) {
                                    placedBid.setBid(bid);
                                }
                            }
                        } else {
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);

                        // set the output value based on whether automatic increment is possible

                        outputValue = 3;
                    }
                    else if (bid == nextValidBid && bid < rememberedMaxBid && rememberedMaxBid != 0) {

                        // set the value of current winning bid and bidder id of current winning bid to bid and bidder Id of remembered max bid respectively.

                        this.currentWinningBid = bid;
                        this.bidderIdOfCurrentWinningBid = this.bidderIdOfRememberedMaxBid;

                        //set next valid bid

                        this.nextValidBid = this.currentWinningBid + this.minBidIncrement;

                        // set the bid as new bid if the bidder has placed bid in the past

                        int acceptedBid = this.currentWinningBid;
                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId && bidderId != this.bidderIdOfCurrentWinningBid) {
                                    placedBid.setBid(bid);
                                }
                            }
                        } else {
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);

                        // set the output value

                        if (bidderId == this.bidderIdOfCurrentWinningBid) {
                            outputValue = 3;
                        } else {
                            outputValue = 2;
                        }

                    }
                    else if (bid > nextValidBid && bid == rememberedMaxBid && rememberedMaxBid != 0) {

                        // set the value of current winning bid and bidder id of current winning bid to bid and bidder Id of remembered max bid respectively.

                        this.currentWinningBid = bid;
                        this.bidderIdOfCurrentWinningBid = this.bidderIdOfRememberedMaxBid;

                        //set next valid bid

                        this.nextValidBid = this.currentWinningBid + this.minBidIncrement;

                        // set the value of remembered max bid and bidder id for remembered max bid to 0 and 0 respectively.
                        this.rememberedMaxBid = 0;
                        this.bidderIdOfRememberedMaxBid = 0;

                        // set the bid as new bid if the bidder has placed bid in the past

                        int acceptedBid = this.currentWinningBid;
                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId) {
                                    placedBid.setBid(bid);
                                }
                            }
                        } else {
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // set the output value based

                        if (bidderId == this.bidderIdOfCurrentWinningBid) {
                            outputValue = 3;
                        } else {
                            outputValue = 2;
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);
                    }
                    else if (bid > nextValidBid && bid < rememberedMaxBid && rememberedMaxBid != 0) {

                        // set the value of current winning bid and bidder id of current winning bid to bid and bidder Id of remembered max bid respectively.

                        this.currentWinningBid = bid;
                        this.bidderIdOfCurrentWinningBid = this.bidderIdOfRememberedMaxBid;

                        //set next valid bid

                        this.nextValidBid = this.currentWinningBid + this.minBidIncrement;

                        // set the bid as new bid if the bidder has placed bid in the past

                        int acceptedBid = this.currentWinningBid;
                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId && bidderId != this.bidderIdOfCurrentWinningBid) {
                                    placedBid.setBid(bid);
                                }
                            }

                        } else {
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);

                        // set the output value based

                        if (bidderId == this.bidderIdOfRememberedMaxBid) {
                            outputValue = 3;
                        } else {
                            outputValue = 2;
                        }

                    }
                    else if (bid > nextValidBid && bid > rememberedMaxBid && rememberedMaxBid != 0) {

                        // set the value of current winning bid and bidder id of current winning bid to bid and bidder Id of remembered max bid respectively.

                        this.currentWinningBid = this.rememberedMaxBid;
                        this.bidderIdOfCurrentWinningBid = bidderId;

                        //set next valid bid

                        this.nextValidBid = this.currentWinningBid + this.minBidIncrement;

                        // set the value of remembered max bid and bidder id for remembered max bid to bid and bidder Id respectively.

                        this.rememberedMaxBid = bid;
                        this.bidderIdOfRememberedMaxBid = bidderId;

                        // set the bid as new bid if the bidder has placed bid in the past

                        int acceptedBid = this.currentWinningBid;
                        if (bidderExists(bidderId)) {
                            for (Bid placedBid : placedBids) {
                                if (placedBid.getBidderId() == bidderId) {
                                    placedBid.setBid(bid);
                                }
                            }
                        } else {
                            placedBids.add(new Bid(bidderId, acceptedBid, bid));
                        }

                        // perform automatic increment on all the bids

                        automaticIncrement(this.currentWinningBid);

                        // set the output value based on whether automatic increment is possible

                        if (bid >= nextValidBid) {
                            outputValue = 4;
                        } else {
                            outputValue = 3;
                        }
                    }
                }
            }
        }

        return outputValue;
    }

    /*
     * method name : bidderExists
     * method purpose : Check if the bidder has already placed Bid on the lot in the past.
     * arguments : Integer bidderId (Id of the bidder who placed the bid on the lot)
     * return value : Returns true if bidder has placed bid in the past and false if not.
     */
    public boolean bidderExists(int bidderId) {
        return this.placedBids.stream().anyMatch(placedBid -> bidderId == placedBid.getBidderId());
    }

    /*
     * method name : automaticIncrement
     * method purpose : Automatically increments the bid of the bidder with high remembered bids
     * arguments : Integer currentWinningBid
     * return value : no return value
     */
    public void automaticIncrement(int currentWinningBid) {
        for (Bid placedBid : this.placedBids) {
            if (placedBid.getBid() >= currentWinningBid) {
                placedBid.setAcceptedBid(currentWinningBid);
            }
        }
    }

    /*
     * method name : getWinnerBid
     * method purpose : Gets the bid that is winning currently in the lot.
     * arguments : none
     * return value : Object Bid
     */
    public Bid getWinnerBid() {
        for (Bid placedBid : this.placedBids) {
            if (placedBid.getBid() >= bidderIdOfCurrentWinningBid) {
                return placedBid;
            }
        }
        return new Bid(0, 0, 0);
    }

    /*
     * method name : getCurrentWinningBid
     * method purpose : Gets the amount of current winning bid
     * arguments : none
     * return value : integer currentWinningBid
     */
    public int getCurrentWinningBid() {
        return currentWinningBid;
    }

    /*
     * method name : getBidderIdOfCurrentWinningBid
     * method purpose : Gets the bidder id of the bidder with current winning bid
     * arguments : none
     * return value : integer bidderIdOfCurrentWinningBid
     */
    public int getBidderIdOfCurrentWinningBid() {
        return bidderIdOfCurrentWinningBid;
    }

}
