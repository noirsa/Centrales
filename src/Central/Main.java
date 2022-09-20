package Central;


import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Estado estadoInicial = new Estado();

        Problem problema = new Problem(estadoInicial, new Sucesores(), new GoalTests(), new Heuristica() );

        Search search =  new HillClimbingSearch();

        try {
            Date d1,d2;
            Calendar c1,c2;

            d1 = new Date();
            SearchAgent agent = new SearchAgent(problema,search);
            d2 = new Date();

            c1 = Calendar.getInstance();
            c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);

            long m = c2.getTimeInMillis() - c1.getTimeInMillis();

            Estado estadoFinal = (Estado) search.getGoalState();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}