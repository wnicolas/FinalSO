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
        modeloB.addColumn("Restante C.B");

        modelo1 = new DefaultTableModel();
        modelo1.addColumn("Proceso");
        modelo1.addColumn("Ráfaga");
        modelo1.addColumn("T.E");

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

        switch (cmbColas.getSelectedIndex()) {
            case 0:
                System.out.println("RR");
                
                
                int rafaga1 = Integer.parseInt(txtProcesosInicio.getText());
                
                Nodo nuevo5=new Nodo(tiempo, rafaga1, 0);

                
                cola2.insertarNodo(nuevo5);
                
                cola2.mostrarLista();
                
                
                txtPrioridad.setEnabled(false);
                break;
            case 1:
                System.out.println("SRTF");
                txtPrioridad.setEnabled(false);

                if (e.getSource() == btnAñadir) {

                    int rafaga = Integer.parseInt(txtProcesosInicio.getText());
                    Nodo nuevo = new Nodo(tiempo, rafaga, 0);

                    Object p[] = {nuevo.getIdProceso(), tiempo, nuevo.getRafagaRestante()};
                    modeloC.addRow(p);

                    cola2.insertarNodo(nuevo);

                    Object r[] = {};
                    modeloG.addRow(r);

                    if (modeloSC.getRowCount() > 0) {
                        if (cola2.getInicio().getRafagaRestante() < (int) modeloSC.getValueAt(0, 2)) {

                            int idProceso = (int) modeloSC.getValueAt(0, 0);
                            int rafagaRestante = (int) modeloSC.getValueAt(0, 2);
                            Nodo nuevo1 = new Nodo(idProceso, rafagaRestante);
                            Object q[] = {nuevo1.getIdProceso(), tiempo, nuevo1.getRafagaRestante()};
                            cola2.insertarNodo(nuevo1);

                            actualizaC();
                            modeloSC.removeRow(0);
                            entraSC();

                            modeloC.addRow(q);

                        }
                    }

                } else if (e.getSource() == btnIniciar) {
                    hilo.start();
                    entraSC();
                } else if (e.getSource() == btnBloquear) {
                    bloquear();
                }

                break;
            case 2:
                System.out.println("LP");
                
                
                
                txtPrioridad.setEnabled(true);
                break;
            default:
                break;
        }

    }

    public void bloquear() {
        int idProceso = (int) modeloSC.getValueAt(0, 0);
        int rafagaRestante = (int) modeloSC.getValueAt(0, 2);
        //Nodo nuevo = new Nodo(idProceso, rafagaRestante);
        Object p[] = {idProceso, rafagaRestante, 5};
        modeloB.addRow(p);

        actualizaC();
        modeloSC.removeRow(0);
        entraSC();

    }

    public void entraSC() {
        if (cola2.getInicio() == null) {
            JOptionPane.showMessageDialog(null, "Procesos finalizados");
        } else {

            if (tiempo == 0) {
                modeloC.setValueAt(tiempo, cola2.getInicio().getIdProceso(), 3);
                Object p[] = {cola2.getInicio().getIdProceso(), cola2.getInicio().getRafagaTotal(), cola2.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);
                cola2.retirarNodo();
                System.out.println("hola");

            } else {

                Object p[] = {cola2.getInicio().getIdProceso(), cola2.getInicio().getRafagaTotal(), cola2.getInicio().getRafagaRestante()};
                modeloSC.addRow(p);

                int indexFila = 0;
                for (int i = 1; i < modeloC.getRowCount(); i++) {
                    if ((int) modeloSC.getValueAt(0, 0) == (int) modeloC.getValueAt(i, 0)) {
                        if (modeloC.getValueAt(i, 4) == null) {
                            indexFila = i;
                        }
                    }
                }

                modeloC.setValueAt(tiempo, indexFila, 3);
//                Object p[] = {cola2.getInicio().getIdProceso(), cola2.getInicio().getRafagaTotal(), cola2.getInicio().getRafagaRestante()};
//                modeloSC.addRow(p);
                cola2.retirarNodo();
            }
        }

    }

    public void actualizaSC() {

        if ((int) modeloSC.getValueAt(0, 2) == 1) {
            actualizaC();
            modeloSC.removeRow(0);
            entraSC();
        } else {
            modeloSC.setValueAt((int) modeloSC.getValueAt(0, 2) - 1, 0, 2);
        }
    }

    public void actualizaC() {
        int indexFila = 0;
        for (int i = 1; i < modeloC.getRowCount(); i++) {
            if ((int) modeloSC.getValueAt(0, 0) == (int) modeloC.getValueAt(i, 0)) {
                if (modeloC.getValueAt(i, 4) == null) {
                    indexFila = i;
                    System.out.println(indexFila);
                }
            }
        }

        int tiempoFinal = tiempo;
//        modeloC.setValueAt(tiempoFinal, (int) modeloSC.getValueAt(0, 0), 4);
        modeloC.setValueAt(tiempoFinal, indexFila, 4);

//        int tiempoLlegada = (int) modeloC.getValueAt((int) modeloSC.getValueAt(0, 0), 1);
        int tiempoLlegada = (int) modeloC.getValueAt(indexFila, 1);
        int tiempoRetorno = tiempoFinal - tiempoLlegada;

//        int rafagaEjecutada = (tiempoFinal - (int) modeloC.getValueAt((int) modeloSC.getValueAt(0, 0), 3));
        int rafagaEjecutada = (tiempoFinal - (int) modeloC.getValueAt(indexFila, 3));
        int tiempoEspera = tiempoRetorno - rafagaEjecutada;

//        modeloC.setValueAt(tiempoRetorno, (int) modeloSC.getValueAt(0, 0), 5);
//        modeloC.setValueAt(tiempoEspera, (int) modeloSC.getValueAt(0, 0), 6);
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

    public void actualizaB() {
        if ((int) modeloB.getValueAt(0, 2) == 1) {
            int idProceso = (int) modeloB.getValueAt(0, 0);
            int rafagaRestante = (int) modeloB.getValueAt(0, 1);

            Nodo nuevo = new Nodo(idProceso, rafagaRestante);
            Object p[] = {nuevo.getIdProceso(), tiempo, nuevo.getRafagaRestante()};
            modeloC.addRow(p);

            cola2.insertarNodo(nuevo);

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
                    System.out.println("RR");
                    txtPrioridad.setEnabled(false);
                    break;
                case 1:
                    System.out.println("SRTF");
                    txtPrioridad.setEnabled(false);
                    break;
                case 2:
                    System.out.println("LP");
                    txtPrioridad.setEnabled(true);
                    break;
                default:
                    break;
            }
        }

    }

}
