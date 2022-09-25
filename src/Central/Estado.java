package Central;

import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;

import java.util.ArrayList;
import java.util.Arrays;

public class Estado {
    private Clientes clientes;
    private Centrales centrales;
    private int[] estado;
    private double[] productionNoConsumed;
    private double[] ganacia;
    private double ganaciaT;
    private double costeT;

    public Estado() {

    }

    public Estado(int[] nCent,int nClient) throws Exception {
        int semilla=100;

        this.centrales = new Centrales(nCent,semilla);

        double [] progc={0.3,0.40,0.30};
        this.clientes = new Clientes(nClient,progc,0.3,semilla);
        sortCl(this.clientes); //Garantiza que los clientes con prioridad garantizada sean prioritarios.

        this.estado=new int[nClient];

        this.productionNoConsumed=new double[this.centrales.size()];
        for(int i=0; i<this.centrales.size();i++){
            this.productionNoConsumed[i]=this.centrales.get(i).getProduccion();
        }

        this.ganacia= new double[this.centrales.size()];
        Arrays.fill(this.ganacia, 0.0);

        this.ganaciaT=0.0;

        this.costeT=0.0;
    }

    public void solucionIni() throws myExceptions {

        int indexCentral=0;
        int indexCliente=0;

        Central c = this.centrales.get(indexCentral);

        while(indexCliente<this.clientes.size()){
            Cliente cl=this.clientes.get(indexCliente);

            double consumo = clneed(cl,c);
            if(indexCentral==-1)this.estado[indexCliente++]=-1;
            else if(indexCentral!=-1 && consumo <= this.productionNoConsumed[indexCentral]){

                this.productionNoConsumed[indexCentral]-=consumo;
                this.estado[indexCliente]=indexCentral;

                double value;

                if(this.ganacia[indexCentral]==0.0) {

                    switch (c.getTipo()) {
                        case 0:
                            value=c.getProduccion() * 50 + 20000;
                            this.ganacia[indexCentral] -= value;
                            break;
                        case 1:
                            value=c.getProduccion() * 80 + 10000;
                            this.ganacia[indexCentral] -= value;
                            break;
                        default:
                            value=c.getProduccion() * 150 + 5000;
                            this.ganacia[indexCentral] -= value;
                            break;
                    }
                    this.costeT+=value;
                }


                switch (cl.getTipo()){
                    case 0:
                        value=cl.getConsumo()*(400-(cl.getTipo()*100));
                        this.ganacia[indexCentral] += value;
                        break;
                    case 1:
                        value=cl.getConsumo()*(500-(cl.getTipo()*100));
                        this.ganacia[indexCentral] += value;
                        break;
                    default:
                        value=cl.getConsumo()*(600-(cl.getTipo()*100));
                        this.ganacia[indexCentral] += value;
                        break;
                }

                this.ganaciaT += value;
                indexCliente++;
            }else{
                indexCentral++;
                if(indexCentral<this.centrales.size()) c = this.centrales.get(indexCentral);
                else if(cl.getContrato()==0) throw new myExceptions("No hay suficientes centrales para clientes garantizados");
                else{
                    this.ganacia[indexCentral-1] -= 50 * cl.getConsumo();
                    this.costeT += 50 * cl.getConsumo();
                    indexCentral=-1;
                    indexCliente++;
                }
            }
        }

        while(indexCentral!=-1 && indexCentral<this.centrales.size()){
            if(this.ganacia[indexCentral]!=0){
                indexCentral++;
            }else{

                switch (this.centrales.get(indexCentral).getTipo()){
                    case 0:
                        this.ganacia[indexCentral]-= 15000;
                        this.costeT +=15000;
                        break;
                    case 1:
                        this.ganacia[indexCentral]-= 5000;
                        this.costeT +=5000;
                        break;
                    default:
                        this.ganacia[indexCentral]-= 1500;
                        this.costeT +=1500;
                        break;
                }
            }
        }

    }

    @Override
    public String toString() {
        String str="NºClientes: "+this.clientes.size()+" NºCentrales: "+this.centrales.size()+"\n";
        for (int i=0; i<this.estado.length;i++){
            if(this.estado[i] == -1) str+= "Cliente "+i+" "+this.clientes.get(i).getContrato()+" "+this.clientes.get(i).getConsumo()+" -> \n";
            else str+= "Cliente "+i+" "+this.clientes.get(i).getContrato()+" "+this.clientes.get(i).getConsumo()+" -> Central "+this.estado[i]+" "+this.centrales.get(this.estado[i]).getProduccion()+"\n";
        }
        str+="Centrales :";
        for(int i=0; i<this.productionNoConsumed.length; i++){
            str+=" c"+i+"->"+this.productionNoConsumed[i];
        }
        str+="\n";
        for(int i=0; i<this.ganacia.length; i++){
            str+=" c"+i+"->"+this.ganacia[i];
        }
        str+="\nGanancia total: "+this.ganaciaT+" Coste total: "+this.costeT+" Ganancia final: "+ (this.ganaciaT-this.costeT);
        return  str;
    }

    //clneed: Aplicar porcentaje extra de la distacia entre cliete y central.
    private static double clneed(Cliente cl,Central c){
        int clx=cl.getCoordX();
        int cly=cl.getCoordY();
        int cx=c.getCoordX();
        int cy=c.getCoordY();
        double distancia = Math.sqrt((cx-clx)^2 + (cy-cly)^2);
        double need = cl.getConsumo();
        if(distancia <= 10) need=need;
        else if(distancia <= 25)need += need*0.1;
        else if(distancia <= 50)need += need*0.2;
        else if(distancia <= 75) need += need*0.4;
        else if(distancia > 75 ) need+=need*0.6;
        return  need;
    }

    private static void sortCl(Clientes cls){
        ArrayList<Cliente> newClsG=  new ArrayList<Cliente>();
        ArrayList<Cliente>  newClsNG=new ArrayList<Cliente>();
        for(Cliente cl : cls){
            if(cl.getContrato()==0) newClsG.add(cl);
            else newClsNG.add(cl);
        }
        newClsG.addAll(newClsNG);

        if(newClsG.size()!=cls.size()){
            System.out.println("Tamaño diferente");
            return;
        }
        for(int i=0; i<newClsG.size(); i++){
            cls.set(i,newClsG.get(i));
        }

    }

}
