//COLA ROUND ROBIN
package proyectofinalso;

public class Cola1 {

    private Nodo inicio;
    private Nodo fin;

    public Cola1() {
        this.inicio = null;
        this.fin = null;
    }

    public void insertarNodo(Nodo nuevo) {//MÉTODO QUE INGRESA LOS NUEVOS NODOS A LA LISTA EN ORDEN DE LLEGADA.
        if (this.inicio == this.fin && this.inicio == null) {
            this.inicio = nuevo;
            this.fin = nuevo;
        } else {
            nuevo.setAnterior(this.fin);
            this.fin.setSiguiente(nuevo);
            this.fin = nuevo;
        }
    }

    public void retirarNodo() {
        if (this.inicio.getSiguiente() == null) {
            this.inicio = null;
            this.fin = null;
        } else {
            this.inicio = this.inicio.getSiguiente();
            this.inicio.getAnterior().setSiguiente(null);
            this.inicio.setAnterior(null);
        }
    }

    public void mostrarLista() {
        Nodo recorrido = this.inicio;
        while (recorrido != null) {
            System.out.println("ID: " + recorrido.getIdProceso() + "-->rafaga: " + recorrido.getRafagaRestante());
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
