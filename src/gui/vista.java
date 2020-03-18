package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import db.*;
import datos.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class vista implements ActionListener{
    JFrame frame;
    JPanel panel;
    JMenuBar menuBar;
    JMenu menu, subMenu;
    JMenuItem menuItem;
    JScrollPane scrollPanel;
    DefaultTableModel modeloTabla;
    JTable tablaContactos;
    JTabbedPane pestana;
    Container panelInformacion;
    JLabel labelId,labelNombre,labelNota1,labelNota2,
    labelNota3;
    JTextField textId,textNombre,textNota1,textNota2,textNota3;
    JButton botonNuevoContacto,botonGuardarContacto,botonEditarContacto,botonBorrarContacto;
    JButton botonPromedio;
    String[] columNames = {"id_estudiantes","Nombres","Nota1","Nota2","Nota3"};

    
    int estado=0;
    int fila;
    public vista() throws SQLException {
        initComponents();
    }

    public void initComponents() throws SQLException{
        daoCliente dbc = new daoCliente();
        cliente[] clientes;
        frame = new JFrame("Notas estudiantes");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        frame.setSize((width/2)+20, (height/2)+20);		
        frame.setLocationRelativeTo(null);
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        menu = new JMenu("Opciones");
        menuBar.add(menu);
        menuItem = new JMenuItem("Salir");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        panelInformacion = new JPanel(null);
        panelInformacion.setLayout(null);
        pestana = new JTabbedPane();
        pestana.addTab("Información de estudiante", panelInformacion);

        int y = 5;
        int x = 10;
        int labelAncho = 150;
        int labelAlto = 20;
        int textAncho = 200;
        int textAlto = 20;
        labelId = new JLabel("Id");
        labelId.setBounds(x,y,20,20);
        panelInformacion.add(labelId);

        textId = new JTextField();
        textId.setBounds(x+30,y,50,20);
        panelInformacion.add(textId);

        y+=30;
        labelNombre = new JLabel("Nombre",SwingConstants.RIGHT);
        labelNombre.setBounds(x,y,labelAncho,labelAlto);
        panelInformacion.add(labelNombre);

        textNombre = new JTextField();
        textNombre.setBounds(x+160,y,textAncho,textAlto);
        panelInformacion.add(textNombre);

        labelNota1 = new JLabel("Nota 1",SwingConstants.RIGHT);
        labelNota1.setBounds(x+350,y,labelAncho,labelAlto);
        panelInformacion.add(labelNota1);

        textNota1 = new JTextField();
        textNota1.setBounds(x+510,y,textAncho,textAlto);
        panelInformacion.add(textNota1);
        y+=30;
        labelNota2 = new JLabel("Nota 2",SwingConstants.RIGHT);
        labelNota2.setBounds(x,y,labelAncho,labelAlto);
        panelInformacion.add(labelNota2);

        textNota2 = new JTextField();
        textNota2.setBounds(x+160,y,textAncho,textAlto);
        panelInformacion.add(textNota2);

        labelNota3 = new JLabel("Nota 3",SwingConstants.RIGHT);
        labelNota3.setBounds(x+350,y,labelAncho,labelAlto);
        panelInformacion.add(labelNota3);

        textNota3 = new JTextField();
        textNota3.setBounds(x+510,y,textAncho,textAlto);
        panelInformacion.add(textNota3);

        y+=30;

        y+=60;
        botonNuevoContacto = new JButton("Nuevo estudiante");
        botonNuevoContacto.setBounds(x,y,labelAncho-20,30);
        panelInformacion.add(botonNuevoContacto);
        botonNuevoContacto.addActionListener(this);

        botonGuardarContacto = new JButton("Guardar estudiante");
        botonGuardarContacto.setBounds(x+150,y,labelAncho-20,30);
        panelInformacion.add(botonGuardarContacto);
        botonGuardarContacto.addActionListener(this);
        botonEditarContacto = new JButton("Editar estudiante");
        botonEditarContacto.setBounds(x+300,y,labelAncho-20,30);
        panelInformacion.add(botonEditarContacto);
        botonEditarContacto.addActionListener(this);

        botonBorrarContacto = new JButton("Borrar estudiante");
        botonBorrarContacto.setBounds(x+450,y,labelAncho-20,30);
        panelInformacion.add(botonBorrarContacto);
        botonBorrarContacto.addActionListener(this);
        
        botonPromedio = new JButton("Promedio estudiante");
        botonPromedio.setBounds(x+600,y,labelAncho-20,30);
        panelInformacion.add(botonPromedio);
        botonPromedio.addActionListener(this);
        clientes = dbc.get();
        Object [][] data = new Object[clientes.length][11];
        System.out.println(data);                    

        for (int c=0;c<clientes.length;c++){
            data[c][0]=clientes[c].getK_IDENTIFICACION();
            data[c][1]=clientes[c].getI_TIPO_IDENTIFICACION();
            data[c][2]=clientes[c].getN_NOMBRE();
            data[c][3]=clientes[c].getN_APELLIDO();
            data[c][4]=clientes[c].getO_DIRECCION();
            data[c][5]=clientes[c].getO_TELEFONO();
            data[c][6]=clientes[c].getI_SEXO();
            data[c][7]=clientes[c].getF_NACIMIENTO();
            data[c][8]=clientes[c].getO_OCUPACION();
            data[c][9]=clientes[c].getO_CORREO_ELECTRONICO();
            data[c][10]=clientes[c].getV_INGRESO_MENSUAL();
        }

    

    modeloTabla= new DefaultTableModel(data, columNames);

    tablaContactos = new JTable(modeloTabla);
    tablaContactos.setPreferredScrollableViewportSize(new Dimension(500, 150));

    scrollPanel = new JScrollPane(tablaContactos);

    tablaContactos.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            fila = tablaContactos.rowAtPoint(e.getPoint());
            int columna = tablaContactos.columnAtPoint(e.getPoint());
            if ((fila > -1) && (columna > -1)){
                textId.setText(String.valueOf(tablaContactos.getValueAt(fila,0)));
                /*for(int i=0;i<clientes.length;i++){
                    if(String.valueOf(clientes[i].getId()).equals(
                    String.valueOf(tablaContactos.getValueAt(fila, 0)))){
                    textNombre.setText(clientes[i].getNombre());
                    textNota1.setText(Float.toString(contactos[i].getNota1()));
                    textNota2.setText(Float.toString(contactos[i].getNota2()));
                    textNota3.setText(Float.toString(contactos[i].getNota3()));
                    }

                }*/
                estado = 2;
                alterarEstado();
            }   
        }
    });

    frame.getContentPane().add(scrollPanel,BorderLayout.NORTH);
    frame.getContentPane().add(pestana,BorderLayout.CENTER);

    frame.pack();
    frame.setSize(800, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);

    alterarEstado();
    }

    public void alterarEstado(){
        switch(this.estado){
            case 0://estado por defecto
                botonNuevoContacto.setEnabled(true);
                botonBorrarContacto.setEnabled(false);
                botonEditarContacto.setEnabled(false);
                botonGuardarContacto.setEnabled(false);
                botonPromedio.setEnabled(false);
                textId.setEditable(false);
                textNombre.setEditable(false);
                textNota1.setEditable(false);
                textNota2.setEditable(false);
                textNota3.setEditable(false);
                break;
            case 1://estado para un nuevo contacto
                botonNuevoContacto.setEnabled(false);
                botonBorrarContacto.setEnabled(false);
                botonEditarContacto.setEnabled(false);
                botonPromedio.setEnabled(false);
                botonGuardarContacto.setEnabled(true);
                textId.setEditable(false);
                textNombre.setEditable(true);
                textNota1.setEditable(true);
                textNota2.setEditable(true);
                textNota3.setEditable(true);
                break;
            case 2://estado de carga de un contacto
                botonNuevoContacto.setEnabled(false);
                botonBorrarContacto.setEnabled(true);
                botonEditarContacto.setEnabled(true);
                botonPromedio.setEnabled(true);
                botonGuardarContacto.setEnabled(false);
                textId.setEditable(false);
                textNombre.setEditable(false);
                textNota1.setEditable(false);
                textNota2.setEditable(false);
                textNota3.setEditable(false);
                break;
            case 3://estado para editar un contacto
                botonNuevoContacto.setEnabled(false);
                botonBorrarContacto.setEnabled(false);
                botonEditarContacto.setEnabled(false);
                botonPromedio.setEnabled(false);
                botonGuardarContacto.setEnabled(true);
                textId.setEditable(false);
                textNombre.setEditable(true);
                textNota1.setEditable(true);
                textNota2.setEditable(true);
                textNota3.setEditable(true);
            break;
        }
    }

    public void limpiarCampos(){
        textId.setText("");
        textNombre.setText("");
        textNota1.setText("");
        textNota2.setText("");
        textNota3.setText("");
    }

    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();
        System.out.println(accion);
        if(accion.equals("Salir")){
            frame.dispose();
        }
    }
}