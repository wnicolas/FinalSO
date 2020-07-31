package proyectofinalso;

public class Nodo {

    static int CONTADOR = 0;
    private int idProceso;
    private int tiempoLlegada;
    private int rafagaTotal;
    private int rafagaEjecutada;
    private int rafagaRestante;
    
    private Nodo siguiente;
    private Nodo anterior;

    public Nodo(int tiempoLlegada, int rafagaTotal, int rafagaEjecutada) {
        CONTADOR++;
        this.idProceso=CONTADOR;
        this.tiempoLlegada=tiempoLlegada;
        this.rafagaTotal=rafagaTotal;
        this.rafagaEjecutada=rafagaEjecutada;
        this.rafagaRestante=this.rafagaTotal-this.rafagaEjecutada;
        
        this.siguiente=null;
        this.anterior=null;
    }
    
    public Nodo(int idProceso, int rafagaRestante){
        this.idProceso=idProceso;
        this.rafagaRestante=rafagaRestante;
        
    }
    
    public void calcularRafagaRestante(){
        this.rafagaRestante=this.rafagaTotal-this.rafagaEjecutada;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }
    
    public int getIdProceso() {
        return idProceso;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int getRafagaTotal() {
        return rafagaTotal;
    }

    public int getRafagaEjecutada() {
        return rafagaEjecutada;
    }

    public int getRafagaRestante() {
        return rafagaRestante;
    }

    public void setRafagaEjecutada(int rafagaEjecutada) {
        this.rafagaEjecutada = rafagaEjecutada;
    }

}
