import java.util.ArrayList;
import java.util.Scanner;
/**
 * mainUI --- Main UI Interface
 * @author : Shivangi Bhatt
 */
public class mainUI {

    // Retrieve data to the end of the line as an argument for a method call
    // Include two special kinds of arguments:
    //   "null" asks us to return no string
    //   "empty" asks us to return an empty string

    private static String getEndingString(Scanner userInput) {
        String userArgument = null;

        userArgument = userInput.nextLine();
        userArgument = userArgument.trim();

        // Include a "hack" to provide null and empty strings for testing
        if (userArgument.equalsIgnoreCase("empty")) {
            userArgument = "";
        } else if (userArgument.equalsIgnoreCase("null")) {
            userArgument = null;
        }

        return userArgument;
    }

    // Main program to process user commands.
    // This method is not robust.  When it asks for a command, it expects all arguments to be there.
    // It is a quickly-done test harness rather than a full solution for an assignment.

    public static void main(String[] args) {
        // Command options

        String createAuctionCommand = "auction";
        String createBidderCommand = "bidder";
        String auctionStatusCommand = "status";
        String loadBidsCommand = "load";
        String placeBidCommand = "bid";
        String feesOwedCommand = "owed";
        String openAuctionCommand = "open";
        String closeAuctionCommand = "close";
        String winningBidsCommand = "winning";
        String getBidderIdCommand = "id";
        String quitCommand = "quit";

        // Define variables to manage user input

        String userCommand = "";
        String userArgument = "";
        Scanner userInput = new Scanner(System.in);

        // Define the auction system that we will be testing.

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        ArrayList<Auction> definedAuctions = new ArrayList<>();
        ArrayList<Bidder> definedBidders = new ArrayList<>();

        if ((auctionSystem == null) || (definedAuctions == null) || (definedBidders == null)) {
            System.out.println("Unable to initialize the auction system.");
        } else {
            // Define variables to catch the return values of the methods

            Integer intOutcome;
            String ignoredString;

            // Let the user know how to use this interface

            System.out.println("Commands available:");
            System.out.println("  " + createAuctionCommand + " <first lot> <last lot> <min bid increment> <auction name>");
            System.out.println("  " + createBidderCommand + " <bidder name>");
            System.out.println("  " + auctionStatusCommand);
            System.out.println("  " + loadBidsCommand + " <filename>");
            System.out.println("  " + placeBidCommand + " <lot number> <bidder number> <bid>");
            System.out.println("  " + feesOwedCommand);
            System.out.println("  " + openAuctionCommand + " <auction number>");
            System.out.println("  " + closeAuctionCommand + " <auction number>");
            System.out.println("  " + winningBidsCommand + " <auction number>");
            System.out.println("  " + getBidderIdCommand + " <bidder number>");
            System.out.println("  " + quitCommand);


            // Process the user input until they provide the command "quit"

            do {
                // Find out what the user wants to do
                userCommand = userInput.next();

                        /* Do what the user asked for.  If condition for each command.  Since each command
                           has a different number of parameters, we do separate handling of each command. */

                if (userCommand.equalsIgnoreCase(createAuctionCommand)) {
                    int firstLot = userInput.nextInt();
                    int lastLot = userInput.nextInt();
                    int minBid = userInput.nextInt();
                    String name = getEndingString(userInput);

                    Auction newAuction = auctionSystem.createAuction(name, firstLot, lastLot, minBid);
                    if (newAuction == null) {
                        System.out.println("null returned for auction");
                    } else {
                        System.out.println("Auction created.  Refer to it as auction " + (1 + definedAuctions.size()));
                        definedAuctions.add(newAuction);
                    }
                } else if (userCommand.equalsIgnoreCase(createBidderCommand)) {
                    String name = getEndingString(userInput);

                    Bidder newBidder = auctionSystem.createBidder(name);
                    if (newBidder == null) {
                        System.out.println("null returned for bidder");
                    } else {
                        System.out.println("Bidder returned with id " + newBidder.getBidderId() + " created.  Refer to it as bider " + (1 + definedBidders.size()));
                        definedBidders.add(newBidder);
                    }
                } else if (userCommand.equalsIgnoreCase(auctionStatusCommand)) {
                    String theStatus = auctionSystem.auctionStatus();
                    ignoredString = getEndingString(userInput);

                    if (theStatus == null) {
                        System.out.println("null returned for status string");
                    } else {
                        System.out.println("Returned string:\n" + theStatus);
                    }
                } else if (userCommand.equalsIgnoreCase(loadBidsCommand)) {

                    // Get the parameters.

                    String filename = getEndingString(userInput);

                    // Call the method

                    intOutcome = auctionSystem.loadBids(filename);
                    System.out.println(userCommand + " \"" + userArgument + "\" returned " + intOutcome);

                } else if (userCommand.equalsIgnoreCase(placeBidCommand)) {
                    int lotNumber = userInput.nextInt();
                    int bidderNumber = userInput.nextInt();
                    int bid = userInput.nextInt();

                    // Clean up the end of the line

                    ignoredString = getEndingString(userInput);

                    // Call the method
                    if (definedBidders.size() >= bidderNumber && bidderNumber>0) {
                        intOutcome = auctionSystem.placeBid(lotNumber, definedBidders.get(bidderNumber - 1).getBidderId(), bid);
                    } else {
                        intOutcome = 0;
                    }
                    System.out.println(userCommand + " outcome " + intOutcome);
                } else if (userCommand.equalsIgnoreCase(feesOwedCommand)) {
                    ignoredString = getEndingString(userInput);

                    String owed = auctionSystem.feesOwed();

                    System.out.println("Returned string:\n" + owed);
                } else if (userCommand.equalsIgnoreCase(openAuctionCommand)) {
                    int auctionNumber = userInput.nextInt();
                    ignoredString = getEndingString(userInput);
                    boolean opened = false;
                    if (definedAuctions.size() >= auctionNumber && auctionNumber!=0) {
                        opened = definedAuctions.get(auctionNumber - 1).openAuction();
                    }
                    System.out.println("Returned boolean value: " + opened);
                } else if (userCommand.equalsIgnoreCase(closeAuctionCommand)) {
                    int auctionNumber = userInput.nextInt();
                    ignoredString = getEndingString(userInput);
                    boolean closed = false;
                    if (definedAuctions.size() >= auctionNumber && auctionNumber!=0) {
                        closed = definedAuctions.get(auctionNumber - 1).closeAuction();
                    }
                    System.out.println("Returned boolean value: " + closed);
                } else if (userCommand.equalsIgnoreCase(winningBidsCommand)) {
                    int auctionNumber = userInput.nextInt();
                    ignoredString = getEndingString(userInput);
                    String theBids = null;
                    if (definedAuctions.size() >= auctionNumber && auctionNumber!=0) {
                        theBids = definedAuctions.get(auctionNumber - 1).winningBids();
                    }
                    System.out.println("Returned string:\n" + theBids);
                } else if (userCommand.equalsIgnoreCase(getBidderIdCommand)) {
                    int bidderNumber = userInput.nextInt();
                    ignoredString = getEndingString(userInput);
                    intOutcome = 0;
                    if (definedBidders.size() >= bidderNumber && bidderNumber!=0) {
                        intOutcome = definedBidders.get(bidderNumber - 1).getBidderId();
                    }

                    System.out.println("Bidder has id " + intOutcome);
                } else if (userCommand.equalsIgnoreCase(quitCommand)) {
                    System.out.println(userCommand);
                } else {
                    System.out.println("Bad command: " + userCommand);
                }
            } while (!userCommand.equalsIgnoreCase("quit"));

            // The user is done so close the stream of user input before ending.

            userInput.close();
        }
    }
}


