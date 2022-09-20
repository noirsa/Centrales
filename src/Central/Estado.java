package Central;

import IA.Energia.Centrales;
import IA.Energia.Clientes;

import java.util.Vector;



public class Estado {
    private Clientes clientes;
    private Centrales centrales;
    private Consumo[][] estado;

    public Estado() {

    }

    public Estado(int[] nCent,int nClient) throws Exception {
        int semilla=100;

        estado=new Consumo[nCent.length][nClient];
        centrales=new Centrales(nCent,semilla);

        double [] progc={0.3,0.40,0.30};
        clientes = new Clientes(nClient,progc,0.3,semilla);
        
    }

    public void estadoSolucionIni(){
        for(int i=0; i< clientes.size(); i++){

        }
    }
}
