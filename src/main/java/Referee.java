import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Referee extends Thread
{
    private int raceLength = 0;
    private int nParticipants = 0;
    private List<Participant> participants = null;
    private List<Thread> participantThreads = null;
    private CountDownLatch finishLineTape = null;
    private String winner = null;

    public Referee(int _newRaceLength)
    {
        raceLength = _newRaceLength;
        finishLineTape = new CountDownLatch(1);
        participants = new ArrayList<>();
        participantThreads = new ArrayList<>();
    }

    public void setNumberOfParticipants(int _nParticipants)
    {
        this.nParticipants = _nParticipants;
    }

    public void registerParticipant(int _newDorsal)
    {
        Participant participant = new Participant(this, _newDorsal, raceLength);
        participants.add(participant);
    }
    public void ready()
    {
        //Create the n participants and add them to the list
        //of participants.
        for(int i = 0; i < nParticipants; i++)
        {
            registerParticipant(i);
        }

        System.out.println("Referee: Ready!");
    }

    public void set()
    {
        //Create the wrapping threads for each participant
        //and add them to the list of participants threads.
        for (Participant participant : participants)
        {
            participantThreads.add(new Thread(participant));
        }
        System.out.println("Referee: Set!");
    }

    public void go()
    {
        //Start the participants
        for (Thread participantThread : participantThreads)
        {
            participantThread.start();
        }
        System.out.println("Referee: Go!");
    }

    public synchronized void finished(int _participantDorsal)
    {
        //TODO Understand what this does.
        finishLineTape.countDown();
        winner = String.valueOf(_participantDorsal);
    }

    public void run()
    {
        this.ready();
        this.set();
        this.go();

        try
        {
            finishLineTape.await();
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args)
    {
        final int PARTICIPANTS = 2;
        final int LENGTH_OF_RACE = 10500;

        Referee ref = new Referee(LENGTH_OF_RACE);
        ref.setNumberOfParticipants(PARTICIPANTS);

        try
        {
            ref.start();
            ref.join();
        }
        catch(InterruptedException e)
        {
            System.out.println("Race interrupted");
        }
        System.out.printf("Referee: The winner is Participant %s\n\n", ref.winner);
        System.out.println("Race ended\n");
    }
}
