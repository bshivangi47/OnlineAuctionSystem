import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * OnlineAuctionSystem --- program for online auction system.
 *
 * @author : Shivangi Bhatt
 */

public class OnlineAuctionSystem {

    //define class variables

    private String openAuctionStatus = "open";
    private String closedAuctionStatus = "closed";
    private String newAuctionStatus = "new";
    private ArrayList<Auction> definedAuctions = new ArrayList<>();
    private ArrayList<Bidder> definedBidders = new ArrayList<>();

    /*
     * method name : auctionExists
     * method purpose : Check if the auction with given auction name is already created in the past.
     * arguments : Arraylist of auctions, string of auction name
     * return value : Returns true if auction exists and false otherwise.
     */
    private static boolean auctionExists(final ArrayList<Auction> auctions, final String auctionName) {
        return auctions.stream().anyMatch(auction -> auctionName.equalsIgnoreCase(auction.getAuctionName()));
    }

    /*
     * method name : bidderExists
     * method purpose : Check if the bidder with given bidder id exits.
     * arguments : Arraylist of bidders, integer bidder id
     * return value : Returns true if bidder exists and false otherwise.
     */
    private static boolean bidderExists(final ArrayList<Bidder> bidders, final int bidderId) {
        return bidders.stream().anyMatch(bidder -> bidder.getBidderId() == bidderId);
    }


    /*
     * method name : createAuction
     * method purpose : create an auction.
     * arguments : String of auctionName, Integer firstLotnumber, Integer lastLotnumber and Integer minBidIncrement
     * return value : Returns the auction created or null in case of any error
     */
    public Auction createAuction(String auctionName, int firstLotNumber, int lastLotNumber, int
            minBidIncrement) {
        // check if auction name is null
        if (auctionName == null) {
            return null;
        }
        // check if auction with given auctionName already exists
        else if (auctionExists(definedAuctions, auctionName)) {
            return null;
        }
        // check if the given input data is valid
        else if (!isAuctionDataValid(auctionName, firstLotNumber, lastLotNumber, minBidIncrement)) {
            return null;
        }
        // check if the size of arraylist of lots i 0
        else if (definedAuctions.size() == 0) {

            // create auction
            Auction auction = new Auction(auctionName, firstLotNumber, lastLotNumber, minBidIncrement);
            // add auction to the arraylist of auctions
            definedAuctions.add(auction);

            // if yes, add create the object of lot and add it to the arraylist of lots
            for (int lot = firstLotNumber; lot <= lastLotNumber; lot++) {
                auction.getLots().add(new Lot(lot, auctionName, minBidIncrement));
            }
            return auction;

        } else {
            //check if the lot numbers exists in the arraylist of lots.
            if (lotNumberExists(firstLotNumber, lastLotNumber)) {
                return null;
            } else {

                // create auction
                Auction auction = new Auction(auctionName, firstLotNumber, lastLotNumber, minBidIncrement);
                // add auction to the arraylist of auctions
                definedAuctions.add(auction);
                //add create the object of lot and add it to the arraylist of lots
                for (int lot = firstLotNumber; lot <= lastLotNumber; lot++) {
                    auction.getLots().add(new Lot(lot, auctionName, minBidIncrement));
                }
                return auction;
            }
        }
    }

    /*
     * method name : createBidder
     * method purpose : create a bidder.
     * arguments : String of bidder name
     * return value : Returns the bidder created or null in case of any error
     */
    public Bidder createBidder(String bidderName) {
        // check if bidder name is null or empty string
        if (bidderName == null || bidderName.length() == 0) {
            return null;
        } else {
            // create bidder object
            Bidder bidder = new Bidder(bidderName);
            // assign an id to the bidder
            bidder.storeBidderId(1 + definedBidders.size());
            //add the bidder to the arraylist of bidders
            definedBidders.add(bidder);
            return bidder;
        }

    }

    /*
     * method name : auctionStatus
     * method purpose : Gives the status of all auctions.
     * arguments : none
     * return value : Returns the string of auction name, auction status and sum of current winning bids in all lots of the auction for each auction.
     */
    public String auctionStatus() {

        String outputString = "";

        if (this.definedAuctions.size() > 0) {

            for (Auction auction : this.definedAuctions) {
                outputString += auction.getAuctionName() + "\t" + auction.getAuctionStatus() + "\t" + auction.sumOfWinningBids() + "\n";

            }
        } else {
            outputString = "";
        }
        return outputString;

    }

    /*
     * method name : loadBids
     * method purpose : Load and place bids from a file
     * arguments : String of file name
     * return value : Returns the integer value of total accepted bids
     */
    public int loadBids(String filename) {
        try {
            AtomicInteger output = new AtomicInteger();
            Path path = Paths.get(filename);
            Stream lines = Files.lines(path);

            lines.forEach(line -> {
                String[] bids = line.toString().split("\\s+");
                int bidder = Integer.parseInt(bids[0]);
                int lotNumber = Integer.parseInt(bids[1]);
                int bid = Integer.parseInt(bids[2]);
                // place bid for each line
                int outcome = placeBid(lotNumber, bidder, bid);
                if (outcome == 2 || outcome == 3 || outcome == 4) {
                    output.addAndGet(1);
                }
            });
            return output.get();
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * method name : placeBid
     * method purpose : Place a bid on the lot
     * arguments : Integer value of lot number, Integer value of bidder id, Integer value of bid
     * return value : Returns the integer value of output
     */
    public int placeBid(int lotNumber, int bidderId, int bid) {
        int output = 0;
        // check if the given lot number and bidder exists
        if (lotNumberExists(lotNumber, lotNumber) && bidderExists(definedBidders, bidderId)) {
            for (Auction auction : definedAuctions) {
                for (Lot lot : auction.getLots()) {
                    if (lot.getLotnumber() == lotNumber) {

                        // check id the auction is open
                        if (auction.getAuctionName().equalsIgnoreCase(lot.getAuctionName()) && auction.getAuctionStatus().equalsIgnoreCase(openAuctionStatus)) {
                            // place bid
                            output = lot.placeBid(bidderId, bid);
                            break;
                        }
                    }

                }
            }
        }
        return output;
    }

    /*
     * method name : feesOwed
     * method purpose : Gives the information of fees owed by each bidder
     * arguments : none
     * return value : Returns the string for each bidder with bidder name, no of lots won by the bidder, and total amount of money they owe
     */
    public String feesOwed() {
        if (definedBidders.size() > 0) {
            String outputString = "";
            for (Bidder bidder : this.definedBidders) {
                int totalWinningBids = 0;
                int totalLotsWon = 0;
                for (Auction auction : this.definedAuctions) {
                    // check if auction is closed
                    if (auction.getAuctionStatus() == closedAuctionStatus) {
                        for (Lot lot : auction.getLots()) {
                            if (lot.getLotnumber() >= auction.getFirstLotNumber() && lot.getLotnumber() <= auction.getLastLotNumber()) {
                                if (lot.getBidderIdOfCurrentWinningBid() == bidder.getBidderId()) {
                                    totalLotsWon += 1;
                                    totalWinningBids += lot.getCurrentWinningBid();
                                }
                            }
                        }
                    }
                }
                // set values for total winning bids and total lots won by the bidder
                bidder.setTotalWinningBids(totalWinningBids);
                bidder.setTotalLotsWon(totalLotsWon);
                outputString += bidder.getBidderName() + "\t" + bidder.getTotalLotsWon() + "\t" + bidder.getTotalWinningBids() + "\n";
            }
            return outputString;
        } else {
            return "";
        }
    }

    /*
     * method name : lotNumberExists
     * method purpose : Check if the lot number exists
     * arguments : Integer of first lot number, Integer lot of last lot number
     * return value : Returns if the lot number exists
     */
    public boolean lotNumberExists(int firstLotNumber, int lastLotNumber) {

        boolean lotNumberExists = false;

        for (int i = firstLotNumber; i <= lastLotNumber; i++) {
            final int tempLotNumber = i;
            if (definedAuctions.stream().anyMatch(auction -> auction.getFirstLotNumber() <= tempLotNumber && auction.getLastLotNumber() >= tempLotNumber)) {
                lotNumberExists = true;
                break;
            }
        }

        return lotNumberExists;
    }

    /*
     * method name : isAuctionDataValid
     * method purpose : Check if the auction data is valid
     * arguments : String of auctionName, Integer firstLotnumber, Integer lastLotnumber and Integer minBidIncrement
     * return value : Returns true if auction data is valid and false otherwise.
     */
    public boolean isAuctionDataValid(String auctionName, int firstLotNumber, int lastLotNumber, int
            minBidIncrement) {

        boolean valid;

        if (auctionName.length() == 0 || auctionName == null) {
            valid = false;
        } else if (firstLotNumber <= 0 || lastLotNumber <= 0) {
            valid = false;
        } else if (minBidIncrement <= 0) {
            valid = false;
        } else if (firstLotNumber > lastLotNumber) {
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }


}
