package Week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import java.util.HashMap;

/**
 * created: 19.04.2020
 *
 * @author David Zashkolny
 * 3 course, comp math
 * Taras Shevchenko National University of Kyiv
 * email: davendiy@gmail.com
 */
public class BaseballElimination {

    private final int n;
    private final int[] w, l, r;
    private final int[][] g;
    private final HashMap<String, Integer> team2num;
    private FlowNetwork network;
    private FordFulkerson flow;
    private final HashMap<Integer, Integer> team2net;
    private final HashMap<Integer, HashMap<Integer, Integer>> teams2net;
    private int found = -1;
    private int index = 2;
    private final static int source = 0;
    private final static int target = 1;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = in.readInt();
        team2num = new HashMap<>();
        team2net = new HashMap<>();
        teams2net = new HashMap<>();
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];

        for (int i = 0; i < n; i++){
            String name = in.readString();
            team2num.put(name, i);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();

            for (int j = 0; j < n; j++)
                g[i][j] = in.readInt();
        }
    }

    private void addPair(int team1, int team2) {

        network.addEdge(
                new FlowEdge(source, toFlowCoordinate(team1, team2), g[team1][team2])
        );
        network.addEdge(
                new FlowEdge(toFlowCoordinate(team1, team2), toFlowCoordinate(team1), Double.POSITIVE_INFINITY)
        );
        network.addEdge(
                new FlowEdge(toFlowCoordinate(team1, team2), toFlowCoordinate(team2), Double.POSITIVE_INFINITY)
        );
    }

    private int toFlowCoordinate(int team1, int team2) {
        if (teams2net.containsKey(team2))
            if (teams2net.get(team2).containsKey(team1))
                return teams2net.get(team2).get(team1);

        if (!teams2net.containsKey(team1))
            teams2net.put(team1, new HashMap<>());

        if (!teams2net.get(team1).containsKey(team2)) {
            teams2net.get(team1).put(team2, index);
            index++;
        }

        return teams2net.get(team1).get(team2);
    }

    private int toFlowCoordinate(int team) {
        if (!team2net.containsKey(team)) {
            team2net.put(team, index);
            index++;
        }
        return team2net.get(team);
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return team2num.keySet();
    }

    private void validateTeam(String team) {
        if (!team2num.containsKey(team))
            throw new IllegalArgumentException("There is no such team: " + team);
    }

    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);
        return w[team2num.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);
        return l[team2num.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);
        return r[team2num.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);
        return g[team2num.get(team1)][team2num.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateTeam(team);
        int x = team2num.get(team);

        network = new FlowNetwork(
                n * (n-1) / 2         // amount of pairs
                 + n + 2);               // + strict amount of teams + source + target
        for (int i = 0; i < n; i++) {
            if (i == x) continue;

            if (w[x] + r[x] - w[i] < 0) {
                found = i;
                return true;
            }
            network.addEdge(
                    new FlowEdge(toFlowCoordinate(i), target, w[x] + r[x] - w[i])
            );
            for (int j = i+1; j < n; j++){
                if (j == x || j == i) continue;
                addPair(i, j);
            }
        }
        flow = new FordFulkerson(network, source, target);
        boolean res = false;
        for (int i = 1; i < network.V(); i++)
            if (flow.inCut(i)){
                res = true;
                break;
            }
        return res;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        boolean isEliminated = isEliminated(team);
        if (!isEliminated) return null;

        Queue<String> res= new Queue<>();
        if (found != -1) {
            for (String tmpTeam : team2num.keySet())
                if (team2num.get(tmpTeam) == found) {
                    res.enqueue(tmpTeam);
                    found = -1;
                    return res;
                }

        }

        for (String tmpTeam : team2num.keySet()) {
            if (flow.inCut(toFlowCoordinate(
                    team2num.get(tmpTeam)
            )))
                res.enqueue(tmpTeam);
        }
        return res;
    }

    public static void main(String[] args) {
        BaseballElimination division;
        if (args.length == 0) {
             division = new BaseballElimination("files/week3/teams4.txt");
        } else {
            division = new BaseballElimination(args[0]);
        }

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

}
