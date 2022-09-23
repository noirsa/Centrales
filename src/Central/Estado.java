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

    private double[] ganacia;

    public Estado() {

    }

    public Estado(int[] nCent,int nClient) throws Exception {
        int semilla=100;
        this.centrales=new Centrales(nCent,semilla);
        double [] progc={0.3,0.40,0.30};
        this.clientes = new Clientes(nClient,progc,0.3,semilla);

        this.estado=new int[nClient];
        this.productionNoConsumed=new double[this.centrales.size()];

        for(int i=0; i<this.centrales.size();i++){
            this.productionNoConsumed[i]=this.centrales.get(i).getProduccion();
        }
        this.ganacia= new double[this.centrales.size()];
        for(int i=0; i<this.ganacia.length;i++) this.ganacia[i]=0.0;

    }

    public void solucionIni() throws myExceptions {

        int indexCentral=0;
        int indexCliente=0;

        Central c = this.centrales.get(indexCentral);
        for(Cliente cl : this.clientes){
            if(cl.getConsumo() <= this.productionNoConsumed[indexCentral]){
                this.productionNoConsumed[indexCentral]-=cl.getConsumo();
                this.estado[indexCliente]=indexCentral;

                double p = c.getProduccion();
                switch (c.getTipo()){
                    case 0:
                        this.ganacia[indexCentral]-= p*50+20000;
                        break;
                    case 1:
                        this.ganacia[indexCentral]-= p*80+10000;
                        break;
                    default:
                        this.ganacia[indexCentral]-= p*150+5000;
                        break;
                }
            }else{
                indexCentral++;
                if(indexCentral<this.centrales.size()) c = this.centrales.get(indexCentral);
                else throw new myExceptions("No hay suficientes centrales");
            }
            indexCliente++;
        }

        while(indexCentral<this.centrales.size()){
            if(this.ganacia[indexCentral]<0){
                indexCentral++;
            }else{
                switch (this.centrales.get(indexCentral).getTipo()){
                    case 0:
                        this.ganacia[indexCentral]-= 15000;
                        break;
                    case 1:
                        this.ganacia[indexCentral]-= 5000;
                        break;
                    default:
                        this.ganacia[indexCentral]-= 1500;
                        break;
                }
            }
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
        str+="\n";
        for(int i=0; i<this.ganacia.length; i++){
            str+=" c"+i+"->"+this.ganacia[i];
        }
        return  str;
    }
}
