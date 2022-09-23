package Central;


import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scan= new Scanner(System.in);
        System.out.println("Centrales (XG MG G): ");
        int[] i = new int[3];
        i[0]=scan.nextInt();
        i[1]=scan.nextInt();
        i[2]=scan.nextInt();
        System.out.println("Clientes: ");
        int c= scan.nextInt();
        Estado estado = new Estado(i,c);
        try{
            estado.solucionIni();

            System.out.println(estado.toString());
        }catch(Exception e){
            System.out.println(e);
        }


        /*
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
        */

    }
}