package proyectofinalso;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Vista extends JFrame implements ActionListener, Runnable {

    int tiempo = 0;
    final int CUANTUM = 5;
    final int TIEMPOENVEJECIMIENTO = 20;
    final int TIEMPOBLOQUEO=3;
    int COLA = 0;
    

    Cola1 cola1;
    Cola2 cola2;
    Cola3 cola3;

    JButton btnIniciar;
    JButton btnAñadir;
    JButton btnBloquear;
    JLabel lblTiempo;
    JLabel lbl1 = new JLabel("Round Robin");
    JLabel lbl2 = new JLabel("Shortest Remaining Time First");
    JLabel lbl3 = new JLabel("Cola de Prioridades");
    JButton btnGraficar;
    JTextField txtProcesosInicio;
    JTextField txtPrioridad;
    JPanel panelSuperior;
    JPanel panelCentral = new JPanel(null);
    JPanel panelInferior = new JPanel();
    JComboBox cmbColas;

    DefaultTableModel modeloC;
    DefaultTableModel modeloSC;
    DefaultTableModel modeloB;
    DefaultTableModel modeloG;
    DefaultTableModel modelo1;
    DefaultTableModel modelo2;
    DefaultTableModel modelo3;

    JScrollPane scrollC;
    JScrollPane scrollSC;
    JScrollPane scrollB;
    JScrollPane scrollG;
    JScrollPane scroll1;
    JScrollPane scroll2;
    JScrollPane scroll3;

    Thread hilo;

    public Vista() {

        cola1 = new Cola1();
        cola2 = new Cola2();
        cola3 = new Cola3();

        setTitle("Proyecto final");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(-3, 0, 1374, 730);
        setResizable(false);
//        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //PANEL SUPERIOR
        panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.DARK_GRAY);
        add(panelSuperior, BorderLayout.NORTH);
        txtProcesosInicio = new JTextField("Procesos");
        cmbColas = new JComboBox();
        txtPrioridad = new JTextField("Prioridad");
        txtPrioridad.setEnabled(false);
        cmbColas.addItem("1. Round Robin");
        cmbColas.addItem("2. STRF");
        cmbColas.addItem("3. Lista de prioridades");
        ControladorComboBox controlador = new ControladorComboBox();
        cmbColas.addActionListener(controlador);
        btnAñadir = new JButton("Añadir proceso");
        btnAñadir.addActionListener(this);
        btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(this);
        btnBloquear = new JButton("Bloquear");
        btnBloquear.addActionListener(this);
        panelSuperior.add(txtProcesosInicio);
        panelSuperior.add(cmbColas);
        panelSuperior.add(txtPrioridad);
        panelSuperior.add(btnAñadir);
        panelSuperior.add(btnIniciar);
        panelSuperior.add(btnBloquear);
        lblTiempo = new JLabel("tiempo: " + tiempo);
        lblTiempo.setForeground(Color.WHITE);
        panelSuperior.add(lblTiempo);

        //PANEL CENTRAL
        add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(null);
        panelCentral.setBackground(Color.LIGHT_GRAY);
        modeloC = new DefaultTableModel();
        modeloC.addColumn("Proceso");
        modeloC.addColumn("Llegada");
        modeloC.addColumn("Ráfaga");
        modeloC.addColumn("Comienzo");
        modeloC.addColumn("Final");
        modeloC.addColumn("Retorno");
        modeloC.addColumn("Espera");
        Object p[] = {"-", "-", "-", "-", "-", "-", "-"};
        modeloC.addRow(p);

        modeloSC = new DefaultTableModel();
        modeloSC.addColumn("Proceso");
        modeloSC.addColumn("Ráfaga");
        modeloSC.addColumn("Restante S.C");

        modeloB = new DefaultTableModel();
        modeloB.addColumn("Proceso");
        modeloB.addColumn("Ráfaga");
        modeloB.addColumn("T. Restante");
        modeloB.addColumn("Cola");

        modelo1 = new DefaultTableModel();
        modelo1.addColumn("Proceso");
        modelo1.addColumn("Ráfaga");

        modelo2 = new DefaultTableModel();
        modelo2.addColumn("Proceso");
        modelo2.addColumn("Ráfaga");
        modelo2.addColumn("T.E");

        modelo3 = new DefaultTableModel();
        modelo3.addColumn("Proceso");
        modelo3.addColumn("Ráfaga");
        modelo3.addColumn("T.E");

        modeloG = new DefaultTableModel();
        Object q[] = {};
        modeloG.addRow(q);
        for (int i = 0; i <= 80; i++) {
            modeloG.addColumn(i);
            modeloG.setValueAt(".---.", 0, i);
        }

        JTable tablaC = new JTable(modeloC);
        JTable tablaL = new JTable(modeloSC);
        JTable tablaB = new JTable(modeloB);
        JTable tablaG = new JTable(modeloG);
        JTable tabla1 = new JTable(modelo1);
        JTable tabla2 = new JTable(modelo2);
        JTable tabla3 = new JTable(modelo3);

        scrollC = new JScrollPane(tablaC);
        scrollSC = new JScrollPane(tablaL);
        scrollB = new JScrollPane(tablaB);
        scrollG = new JScrollPane(tablaG);
        scroll1 = new JScrollPane(tabla1);
        scroll2 = new JScrollPane(tabla2);
        scroll3 = new JScrollPane(tabla3);

        panelCentral.add(scrollC);
        scrollC.setBounds(10, 50, 420, 200);
        panelCentral.add(scrollSC);
        scrollSC.setBounds(440, 50, 260, 39);
        panelCentral.add(scrollB);
        scrollB.setBounds(440, 95, 260, 155);
        panelCentral.add(scrollG);
        scrollG.setBounds(10, 320, 1344, 300);

        panelCentral.add(lbl1);
        lbl1.setBounds(715, 75, 100, 20);
        panelCentral.add(scroll1);

        scroll1.setBounds(715, 95, 190, 155);
        panelCentral.add(lbl2);
        lbl2.setBounds(915, 75, 200, 20);
        panelCentral.add(scroll2);

        scroll2.setBounds(915, 95, 190, 155);
        panelCentral.add(lbl3);
        lbl3.setBounds(1115, 75, 150, 20);

        panelCentral.add(scroll3);
        scroll3.setBounds(1115, 95, 190, 155);

        hilo = new Thread(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAñadir) {
            int rafaga = Integer.parseInt(txtProcesosInicio.getText());
            Nodo nuevo = new Nodo(tiempo, rafaga, 0);

            Object c[] = {nuevo.getIdProceso(), tiempo, nuevo.getRafagaRestante()};
            modeloC.addRow(c);

            Object gantt[] = {};
            modeloG.addRow(gantt);

            switch (cmbColas.getSelectedIndex()) {
                case 0://ROUND ROBIN
                    cola1.insertarNodo(nuevo);
                    Object cola1[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante()};
                    modelo1.addRow(cola1);
                    break;
                case 1://SHORTEST REMAINING TIME FIRST
                    if (COLA != 2) {
                        cola2.insertarNodo(nuevo);
                        Object cola2[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante(), TIEMPOENVEJECIMIENTO};
                        modelo2.addRow(cola2);
                    } else {
                        cola2.insertarNodo(nuevo);

                        if (modeloSC.getRowCount() > 0) {
                            if (cola2.getInicio().getRafagaRestante() < (int) modeloSC.getValueAt(0, 2)) {
                                int idProceso = (int) modeloSC.getValueAt(0, 0);
                                int rafagaRestante = (int) modeloSC.getValueAt(0, 2);
                                Nodo nuevo1 = new Nodo(idProceso, rafagaRestante);
                                Object q[] = {nuevo1.getIdProceso(), tiempo, nuevo1.getRafagaRestante()};
                                cola2.insertarNodo(nuevo1);

                                Object d[] = {nuevo1.getIdProceso(), nuevo1.getRafagaRestante(), TIEMPOENVEJECIMIENTO};
                                modelo2.addRow(d);

                                actualizaC();
                                modeloSC.removeRow(0);
                                entraSC();

                                modeloC.addRow(q);
                            }
                        } else {
                            Object cola2[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante(), TIEMPOENVEJECIMIENTO};
                            modelo2.addRow(cola2);
                        }
                    }
                    break;
                case 2://LISTA DE PRIORIDADES
                    int prioridad = Integer.parseInt(txtPrioridad.getText());
                    nuevo.setPrioridad(prioridad);
                    cola3.insertarNodo(nuevo);

                    Object cola3[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante(), TIEMPOENVEJECIMIENTO};
                    modelo3.addRow(cola3);

                    break;
                default:
                    break;
            }

        } else if (e.getSource() == btnIniciar) {
            hilo.start();
            entraSC();
            btnIniciar.setEnabled(false);
        } else if (e.getSource() == btnBloquear) {
            bloquear();
        }

    }

    public void entraSC() {

        if (modelo1.getRowCount() > 0) {
            COLA = 1;
            if (tiempo == 0) {
                modeloC.setValueAt(tiempo, cola1.getInicio().getIdProceso(), 3);
                Object p[] = {cola1.getInicio().getIdProceso(), cola1.getInicio().getRafagaRestante(), cola1.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);
                cola1.retirarNodo();
                modelo1.removeRow(0);
            } else {
                Object p[] = {cola1.getInicio().getIdProceso(), cola1.getInicio().getRafagaRestante(), cola1.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);
                int indexFila = 0;
                for (int i = 1; i < modeloC.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modeloC.getValueAt(i, 0)) {
                        if (modeloC.getValueAt(i, 3) == null) {
                            indexFila = i;
                        }
                    }
                }
                modeloC.setValueAt(tiempo, indexFila, 3);
                cola1.retirarNodo();
                modelo1.removeRow(0);
            }
        } else if (modelo2.getRowCount() > 0) {
            COLA = 2;

            if (tiempo == 0) {
                modeloC.setValueAt(tiempo, cola2.getInicio().getIdProceso(), 3);
                Object p[] = {cola2.getInicio().getIdProceso(), cola2.getInicio().getRafagaRestante(), cola2.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);
                cola2.retirarNodo();

                for (int i = 0; i < modelo2.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modelo2.getValueAt(i, 0)) {
                        modelo2.removeRow(i);
                    }
                }

            } else {

                Object p[] = {cola2.getInicio().getIdProceso(), cola2.getInicio().getRafagaRestante(), cola2.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);

                int indexFila = 0;
                for (int i = 1; i < modeloC.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modeloC.getValueAt(i, 0)) {
                        if (modeloC.getValueAt(i, 3) == null) {
                            indexFila = i;
                        }
                    }
                }
                modeloC.setValueAt(tiempo, indexFila, 3);
                cola2.retirarNodo();
                for (int i = 0; i < modelo2.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modelo2.getValueAt(i, 0)) {
                        modelo2.removeRow(i);
                    }
                }
            }

        } else if (modelo3.getRowCount() > 0) {
            COLA = 3;

            if (tiempo == 0) {
                modeloC.setValueAt(tiempo, cola3.getInicio().getIdProceso(), 3);
                Object p[] = {cola3.getInicio().getIdProceso(), cola3.getInicio().getRafagaRestante(), cola3.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);
                cola3.retirarNodo();

                for (int i = 0; i < modelo3.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modelo3.getValueAt(i, 0)) {
                        modelo3.removeRow(i);
                    }
                }

            } else {
                Object p[] = {cola3.getInicio().getIdProceso(), cola3.getInicio().getRafagaRestante(), cola3.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);
                int indexFila = 0;
                for (int i = 1; i < modeloC.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modeloC.getValueAt(i, 0)) {
                        if (modeloC.getValueAt(i, 3) == null) {
                            indexFila = i;
                        }
                    }
                }
                modeloC.setValueAt(tiempo, indexFila, 3);
                cola3.retirarNodo();

                for (int i = 0; i < modelo3.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modelo3.getValueAt(i, 0)) {
                        modelo3.removeRow(i);
                    }
                }

            }
        }
    }

    public void actualizaSC() {

        if (COLA == 1) {
            if ((int) modeloSC.getValueAt(0, 2) == 1) {
                actualizaC();
                modeloSC.removeRow(0);
                entraSC();
            } else if ((int) modeloSC.getValueAt(0, 2) == (int) modeloSC.getValueAt(0, 1) - CUANTUM + 1) {

                Nodo nuevo = new Nodo((int) modeloSC.getValueAt(0, 0), (int) modeloSC.getValueAt(0, 1) - CUANTUM);
                Object c[] = {nuevo.getIdProceso(), tiempo, nuevo.getRafagaRestante()};
                modeloC.addRow(c);
                cola1.insertarNodo(nuevo);
                Object cola1[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante()};
                modelo1.addRow(cola1);

                actualizaC();
                modeloSC.removeRow(0);
                entraSC();

            } else {
                modeloSC.setValueAt((int) modeloSC.getValueAt(0, 2) - 1, 0, 2);
            }
        } else if (COLA == 2) {

            if ((int) modeloSC.getValueAt(0, 2) == 1) {
                actualizaC();
                modeloSC.removeRow(0);
                entraSC();
                //modelo2.removeRow(0);
            } else {
                modeloSC.setValueAt((int) modeloSC.getValueAt(0, 2) - 1, 0, 2);
            }
        } else if (COLA == 3) {
            if ((int) modeloSC.getValueAt(0, 2) == 1) {
                actualizaC();
                modeloSC.removeRow(0);
                entraSC();
                //modelo2.removeRow(0);
            } else {
                modeloSC.setValueAt((int) modeloSC.getValueAt(0, 2) - 1, 0, 2);
            }
        }
    }

    public void actualizaC() {
        int indexFila = 0;
        for (int i = 1; i < modeloC.getRowCount(); i++) {
            if ((int) modeloSC.getValueAt(0, 0) == (int) modeloC.getValueAt(i, 0)) {
                if (modeloC.getValueAt(i, 4) == null) {
                    indexFila = i;
                    break;
                }
            }
        }

        int tiempoFinal = tiempo;

        modeloC.setValueAt(tiempoFinal, indexFila, 4);

        int tiempoLlegada = (int) modeloC.getValueAt(indexFila, 1);
        int tiempoRetorno = tiempoFinal - tiempoLlegada;

        int rafagaEjecutada = (tiempoFinal - (int) modeloC.getValueAt(indexFila, 3));
        int tiempoEspera = tiempoRetorno - rafagaEjecutada;

        modeloC.setValueAt(tiempoRetorno, indexFila, 5);
        modeloC.setValueAt(tiempoEspera, indexFila, 6);

        int indexGantt = (int) modeloSC.getValueAt(0, 0);
        int inicioEjecucion = (int) modeloC.getValueAt(indexFila, 3);
        int finEjecucion = (int) modeloC.getValueAt(indexFila, 4);
        for (int i = inicioEjecucion; i < finEjecucion; i++) {
            modeloG.setValueAt("Ej", indexGantt, i);
        }

        int inicioEspera = (int) modeloC.getValueAt(indexFila, 1);
        int finEspera = (int) modeloC.getValueAt(indexFila, 3);
        for (int i = inicioEspera; i < finEspera; i++) {
            modeloG.setValueAt("*", indexGantt, i);
        }

    }

    public void bloquear() {
        int idProceso = (int) modeloSC.getValueAt(0, 0);
        int rafagaRestante = (int) modeloSC.getValueAt(0, 2);
        Object p[] = {idProceso, rafagaRestante, TIEMPOBLOQUEO, COLA};
        modeloB.addRow(p);
        actualizaC();
        modeloSC.removeRow(0);
        entraSC();

    }

    public void actualizaB() {
        if ((int) modeloB.getValueAt(0, 2) == 1) {
            int idProceso = (int) modeloB.getValueAt(0, 0);
            int rafagaRestante = (int) modeloB.getValueAt(0, 1);

            Nodo nuevo = new Nodo(idProceso, rafagaRestante);
            Object p[] = {nuevo.getIdProceso(), tiempo, nuevo.getRafagaRestante()};
            modeloC.addRow(p);

            switch ((int) modeloB.getValueAt(0, 3)) {
                case 1:
                    cola1.insertarNodo(nuevo);
                    Object cola1[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante()};
                    modelo1.addRow(cola1);
                    break;
                case 2:
                    cola2.insertarNodo(nuevo);
                    Object cola2[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante(),TIEMPOENVEJECIMIENTO};
                    modelo2.addRow(cola2);
                    break;
                case 3:
                    cola3.insertarNodo(nuevo);
                    Object cola3[] = {nuevo.getIdProceso(), nuevo.getRafagaRestante(),TIEMPOENVEJECIMIENTO};
                    modelo3.addRow(cola3);
                    break;
                default:
                    break;
            }

            for (int i = tiempo - 5; i < tiempo; i++) {
                if (modeloG.getValueAt(idProceso, i) == null) {
                    modeloG.setValueAt("B", idProceso, i);
                }
            }

            modeloB.removeRow(0);

        } else {
            modeloB.setValueAt((int) modeloB.getValueAt(0, 2) - 1, 0, 2);
        }
    }

    public void actualizaEnvejecimiento() {

        for (int i = 0; i < modelo2.getRowCount(); i++) {

            if ((int) modelo2.getValueAt(i, 2) == 1) {
                Nodo cambio = cola2.getInicio();
                cola2.retirarNodo();
                cola1.insertarNodo(cambio);
                Object p[] = {cambio.getIdProceso(), cambio.getRafagaRestante()};
                modelo1.addRow(p);
            }

            modelo2.setValueAt((int) modelo2.getValueAt(i, 2) - 1, i, 2);
        }

        for (int i = 0; i < modelo3.getRowCount(); i++) {

            if ((int) modelo3.getValueAt(i, 2) == 1) {
                Nodo cambio = cola3.getInicio();
                cola3.retirarNodo();
                cola2.insertarNodo(cambio);
                Object p[] = {cambio.getIdProceso(), cambio.getRafagaRestante(), TIEMPOENVEJECIMIENTO};
                modelo2.addRow(p);
            }

            modelo3.setValueAt((int) modelo3.getValueAt(i, 2) - 1, i, 2);
        }

        while (modelo2.getRowCount() > 0 && (int) modelo2.getValueAt(0, 2) <= 0) {
            modelo2.removeRow(0);
        }
        while (modelo3.getRowCount() > 0 && (int) modelo3.getValueAt(0, 2) <= 0) {
            modelo3.removeRow(0);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                tiempo++;
                lblTiempo.setText("tiempo: " + tiempo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
            }

            actualizaEnvejecimiento();

            actualizaSC();
            if (modeloB.getRowCount() > 0) {
                actualizaB();
            }

        }
    }

    private class ControladorComboBox implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (cmbColas.getSelectedIndex()) {
                case 0:
                    txtPrioridad.setEnabled(false);
                    break;
                case 1:
                    txtPrioridad.setEnabled(false);
                    break;
                case 2:
                    txtPrioridad.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    }
}
