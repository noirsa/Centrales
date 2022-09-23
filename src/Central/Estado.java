package Central;

import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;

import java.util.Arrays;
import java.util.Vector;

public class Estado {
    private Clientes clientes;
    private Centrales centrales;
    private int[] estado;
    private double[] productionNoConsumed;

    public Estado() {

    }

    public Estado(int[] nCent,int nClient) throws Exception {
        int semilla=100;
        this.centrales=new Centrales(nCent,semilla);
        double [] progc={0.3,0.40,0.30};
        this.clientes = new Clientes(nClient,progc,0.3,semilla);

        this.estado=new int[nClient];
        this.productionNoConsumed=new double[this.centrales.size()];
        int i=0;
        for(Central c: this.centrales){
            this.productionNoConsumed[i]=c.getProduccion();
            i++;
        }
    }

    public void solucionIni() throws myExceptions {

        int indexCentral=0;
        int indexCliente=0;

        Central c = this.centrales.get(indexCentral);
        for(Cliente cl : this.clientes){
            if(cl.getConsumo() <= this.productionNoConsumed[indexCentral]){
                this.productionNoConsumed[indexCentral]-=cl.getConsumo();
                this.estado[indexCliente]=indexCentral;
            }else{
                indexCentral++;
                if(indexCentral<this.centrales.size()) c = this.centrales.get(indexCentral);
                else throw new myExceptions("No hay suficientes centrales");
            }
            indexCliente++;
        }

    }

    @Override
    public String toString() {
        String str="NºClientes: "+this.clientes.size()+" NºCentrales: "+this.centrales.size()+"\n";
        for (int i=0; i<this.estado.length;i++){
            str+= "Cliente "+i+" "+this.clientes.get(i).getConsumo()+" -> Central "+this.estado[i]+" "+this.centrales.get(this.estado[i]).getProduccion()+"\n";
        }
        str+="Centrales :";
        for(int i=0; i<this.productionNoConsumed.length; i++){
            str+=" c"+i+"->"+this.productionNoConsumed[i];
        }
        return  str;
    }
}
