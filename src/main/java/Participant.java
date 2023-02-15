public class Participant implements Runnable
{
    private int dorsal = 0;
    private int raceLength = 0; //metres
    private int remainingLength = 0; //meters
    private Referee theReferee = null;

    public Participant(Referee _raceReferee, int _newDorsal, int _newRaceLength)
    {
        theReferee = _raceReferee;
        raceLength = _newRaceLength;
        dorsal = _newDorsal + 1;
    }

    public int getDorsal()
    {
        return dorsal;
    }

    @Override
    public void run()
    {
        //1. Announce that you are starting the race
        System.out.printf("Participant %d: Starting race\n\n", dorsal);

        /*
        2. Run the race, loop decrementing the remaining
        length 1 metre at a time, and announce the crossing
        of each km mark.
        */

        int kmMark = 0;
        for(remainingLength = raceLength; remainingLength > 0; remainingLength--)
        {
            if(remainingLength % 1000 == 0)
            {
                System.out.printf("Participant %d: Passed km %d.\n\n", dorsal, ++kmMark);
            }
        }

        //3. Announce that you have finished the race.
        System.out.printf("Participant %d: Finished race.\n\n", dorsal);
        theReferee.finished(dorsal);
    }
}
