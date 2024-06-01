package gradle.javadoc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 * @author alumne-DAM
 */
/*Clase principal*/
public class App {

    // Listas para almacenar datos de alumnos y asignaturas.
    public static List<Alumno> AlumnoBBDD = new ArrayList();
    public static List<Asignatura> AsignaturaBBDD = new ArrayList();
    
    
    // Método principal que inicia la aplicacion.
    public static void main(String[] args) {

        // Creamos el menu principal de la aplicacion
        JFrame menu = new JFrame("Gestor Matriculas");
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // Con este añadido cuando cerremos el menu, automaticamente va a cerrarlo todo
        menu.setSize(300, 300);
        
        // Creamos 4 marcos adicionales para mostrar datos
        JFrame frame = new JFrame("Alumnos");
        frame.setSize(610, 400);

        JFrame frame2 = new JFrame("Asignaturas");
        frame2.setSize(600, 400);

        JFrame frame3 = new JFrame("Profesores");
        frame3.setSize(600, 400);

        JFrame frame4 = new JFrame("Añadir Datos");
        frame4.setSize(600, 400);

        // Creamos paneles para organizar la interfaz
        JPanel panel0 = new JPanel(); // Panel del menu principal
        menu.setLayout(new GridLayout(4, 1));
        menu.add(panel0);
        
        JPanel panel = new JPanel(); // Panel para mostrar datos de alumnos
        frame.getContentPane().setLayout(new FlowLayout());
        frame.add(panel);

        // Configuramos los items de la interfaz grafica
        JComboBox filtro = new JComboBox<String>(); // Menu desplegable para filtrar datos
        panel.add(filtro); 
        filtro.addItem("ID Alumno");
        filtro.addItem("Nombre");
        filtro.addItem("Apellido");
        filtro.addItem("Fecha Nacimiento");

        // Repetimos paneles para organizar mas datos
        JPanel panel2 = new JPanel();
        frame2.getContentPane().setLayout(new FlowLayout());
        frame2.add(panel2);

        JPanel panel3 = new JPanel();
        frame3.getContentPane().setLayout(new FlowLayout());
        frame3.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(5, 2));
        frame4.getContentPane().setLayout(new FlowLayout());
        frame4.add(panel4);

        // Creamos el DefaultTableModel vacío
        DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();
        DefaultTableModel model3 = new DefaultTableModel();

        // Implementamos el primer ActionListener para captar los inputs y devolver los alumnos en el orden que le pedimos
        filtro.addActionListener((ActionEvent e) -> {
            model.setRowCount(0);
            String selectedItem = filtro.getSelectedItem().toString();
            if ("ID Alumno".equals(selectedItem)) {
                AlumnoBBDD.sort(Comparator.comparing(Alumno::getID_Alumno));
            } else if ("Nombre".equals(selectedItem)) {
                AlumnoBBDD.sort(Comparator.comparing(Alumno::getNombre));
            } else if ("Apellido".equals(selectedItem)) {
                AlumnoBBDD.sort(Comparator.comparing(Alumno::getApellido));
            } else if ("Fecha Nacimiento".equals(selectedItem)) {
                AlumnoBBDD.sort(Comparator.comparing(Alumno::getFecha_nacimiento));
            }
            AlumnoBBDD.forEach(alumno -> model.addRow(alumno.devolverAlumno()));
        });

        // Añadimos las columnas al modelo
        model.addColumn("ID Alumno");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Fecha Nacimiento");

        model2.addColumn("ID Asignatura");
        model2.addColumn("Nombre");
        model2.addColumn("Codigo");
        model2.addColumn("Creditos");

        model3.addColumn("ID Profesor");
        model3.addColumn("Nombre");
        model3.addColumn("Apellido");
        model3.addColumn("Especialidad");

        // Creamos el JTable con el DefaultTableModel
        JTable table = new JTable(model);
        JTable table2 = new JTable(model2);
        JTable table3 = new JTable(model3);

        // Creamos las etiquetas y campos de texto donde añadiremos datos con informacion
        JLabel label4 = new JLabel("Añadir ID Asignatura ");
        panel4.add(label4);
        JTextField textfield = new JTextField();
        panel4.add(textfield);

        JLabel label5 = new JLabel("Añadir Nombre Asignatura ");
        panel4.add(label5);
        JTextField textfield2 = new JTextField();
        panel4.add(textfield2);

        JLabel label6 = new JLabel("Añadir Codigo Asignatura ");
        panel4.add(label6);
        JTextField textfield3 = new JTextField();
        panel4.add(textfield3);

        JLabel label7 = new JLabel("Añadir Creditos Asignatura ");
        panel4.add(label7);
        JTextField textfield4 = new JTextField();
        panel4.add(textfield4);

        // Creamos los botones que nos permitiran hacer varias funciones, incluyendo el acceso a nuevas ventanas
        JButton Enviar = new JButton("Enviar");
        panel4.add(Enviar);
        
        JButton Alumno = new JButton("Alumnos");
        panel0.add(Alumno);
        
        JButton Asignatura = new JButton("Asignaturas");
        panel0.add(Asignatura);
        
        JButton Profesor = new JButton("Profesores");
        panel0.add(Profesor);
        
        JButton Anadir = new JButton("Añadir Asignaturas");
        panel0.add(Anadir);

        // Creamos la DefaultTableModel para estructurar la tabla asignatura
        DefaultTableModel estructuraAsig = new DefaultTableModel();
        for (int i = 0; i < App.AsignaturaBBDD.size(); i++) {
            String[] asignaturas = App.AsignaturaBBDD.get(i).devolverAsignatura();
            estructuraAsig.addRow(asignaturas);
        }
        
        // Añadimos un segundo ActionListener para darle la funcion de enviar datos para crear una nueva asignatura
        Enviar.addActionListener((ActionEvent e) -> {
            String url = "jdbc:mysql://localhost:3306/Programacion2024"; // Conectamos con la base de datos
            try { // Comenzamos un try/catch para dar avisos sobre posibles errores y darnos pistas sobre el fallo
                Connection conexion2 = DriverManager.getConnection(url, "root", "");
            Asignatura asignatura = new Asignatura(textfield.getSelectedText(), textfield2.getSelectedText(),
            textfield3.getSelectedText(),textfield4.getSelectedText());
            AsignaturaBBDD.add(asignatura);
            estructuraAsig.addRow(asignatura.devolverAsignatura());

            String sql = "INSERT INTO asignaturas values (?,?,?,?)"; // Con este insert podremos meter la informacion en la base de datos
            PreparedStatement myst;
                myst = conexion2.prepareStatement(sql);
                myst.setString(1, textfield.getText());
                myst.setString(2, textfield2.getText());
                myst.setString(3, textfield3.getText());
                myst.setString(4, textfield4.getText());
                int resultado = myst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Se ha añadido " + resultado + " asignatura nueva.",
                        "Nueva Calificacion", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al insertar en BBDD: " + ex,
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException ex2) {
                JOptionPane.showMessageDialog(null, "Hay que rellenar todos los huecos del formulario: " + ex2,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Añadimos acciones para que los botones abran las ventanas correspondientes
        Alumno.addActionListener((ActionEvent e) -> {
            frame.setVisible(true);
        });

        Asignatura.addActionListener((ActionEvent e) -> {
            frame2.setVisible(true);
        });

        Profesor.addActionListener((ActionEvent e) -> {
            frame3.setVisible(true);
        });

        Anadir.addActionListener((ActionEvent e) -> {
            frame4.setVisible(true);
        });

        // Creamos un JScrollPane para poder mover las columnas de sitio
        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        JScrollPane scrollPane3 = new JScrollPane(table3);

        // Añadimos el JScrollPane al JFrame
        panel.add(scrollPane, BorderLayout.CENTER);
        panel2.add(scrollPane2, BorderLayout.CENTER);
        panel3.add(scrollPane3, BorderLayout.CENTER);
        
        // Establecemos posiciones del menu y de las ventanas
        int posX;
        int posY;
        menu.setLocation(posX = 650, posY = 350);
        menu.setVisible(true);
        
        frame.setLocation(posX = 0, posY = 0);
        frame2.setLocation(posX = 1000, posY = 0);
        frame3.setLocation(posX = 0, posY = 470);
        frame4.setLocation(posX = 1000, posY = 470);

        // Url de nuestra conexion a la BBDD y comenzamos try catch
        String url = "jdbc:mysql://localhost:3306/Programacion2024";
        try {
            Connection conexion = DriverManager.getConnection(url, "root", "");

            PreparedStatement myst = conexion.prepareStatement("SELECT * FROM alumnos");
            ResultSet rs = myst.executeQuery();

            // En los while, iteramos sobre cada registro de las tablas 
            while (rs.next()) { 
                Alumno alumno = new Alumno(rs.getString("ID_Alumno"), rs.getString("Nombre"),
                        rs.getString("Apellido"), rs.getString("Fecha_nacimiento"));
                model.addRow(new Object[]{rs.getString("ID_Alumno"), rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Fecha_nacimiento")});
                AlumnoBBDD.add(alumno);
            }

            PreparedStatement myst2 = conexion.prepareStatement("SELECT * FROM asignaturas");
            ResultSet rs2 = myst2.executeQuery();

            while (rs2.next()) {
                System.out.println(rs2.getString("ID_Asignatura") + (" - ") + rs2.getString("Nombre")
                        + (" ") + rs2.getString("Codigo") + (" - ") + rs2.getString("Creditos"));
                model2.addRow(new Object[]{rs2.getString("ID_Asignatura"), rs2.getString("Nombre"), rs2.getString("Codigo"), rs2.getString("Creditos")});
            }

            PreparedStatement myst3 = conexion.prepareStatement("SELECT * FROM profesores");
            ResultSet rs3 = myst3.executeQuery();

            while (rs3.next()) {
                System.out.println(rs3.getString("ID_Profesor") + (" - ") + rs3.getString("Nombre")
                        + (" ") + rs3.getString("Apellido") + (" - ") + rs3.getString("Especialidad"));
                model3.addRow(new Object[]{rs3.getString("ID_Profesor"), rs3.getString("Nombre"), rs3.getString("Apellido"), rs3.getString("Especialidad")});
            }

        } catch (SQLException e) {
            System.out.println("Error de BBDD: " + e);
        }
    }

}