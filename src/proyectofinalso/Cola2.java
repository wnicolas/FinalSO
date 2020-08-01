//COLA SHORTEST REMAINING TIME FIRST
package proyectofinalso;

public class Cola2 {

    private Nodo inicio;
    private Nodo fin;

    public Cola2() {
        this.inicio = null;
        this.fin = null;
    }
    
    public void insertarNodo(Nodo nuevo) {//MÉTODO QUE INGRESA LOS NUEVOS NODOS A LA LISTA DE MANERA ORDENADA POR RÁFAGA.
        if (this.inicio == this.fin && this.inicio == null) {
            this.inicio = nuevo;
            this.fin = nuevo;
        } else {
            if (nuevo.getRafagaRestante() < this.inicio.getRafagaRestante()) {
                nuevo.setSiguiente(this.inicio);
                this.inicio.setAnterior(nuevo);
                this.inicio = nuevo;
            } else if (nuevo.getRafagaRestante() >= this.fin.getRafagaRestante()) {
                nuevo.setAnterior(this.fin);
                this.fin.setSiguiente(nuevo);
                this.fin = nuevo;
            } else {
                Nodo recorrido = this.inicio;
                while (recorrido != null) {
                    if (nuevo.getRafagaRestante() >= recorrido.getRafagaRestante()) {
                        recorrido = recorrido.getSiguiente();
                    } else {
                        nuevo.setSiguiente(recorrido);
                        nuevo.setAnterior(recorrido.getAnterior());
                        recorrido.getAnterior().setSiguiente(nuevo);
                        recorrido.setAnterior(nuevo);
                        break;
                    }

                }
            }

        }
    }
    
    public void retirarNodo(){
        if(this.inicio.getSiguiente()==null){
            this.inicio=null;
            this.fin=null;
        }else{
            this.inicio=this.inicio.getSiguiente();
            this.inicio.getAnterior().setSiguiente(null);
            this.inicio.setAnterior(null);
        }
    }

    public void mostrarLista() {
        Nodo recorrido = this.inicio;
        while (recorrido != null) {
            System.out.println("ID: "+recorrido.getIdProceso()+"-->rafaga: "+recorrido.getRafagaRestante());
            recorrido = recorrido.getSiguiente();
        }
    }

    public Nodo getInicio() {
        return inicio;
    }

    public void setInicio(Nodo inicio) {
        this.inicio = inicio;
    }

    public Nodo getFin() {
        return fin;
    }

    public void setFin(Nodo fin) {
        this.fin = fin;
    }

}
