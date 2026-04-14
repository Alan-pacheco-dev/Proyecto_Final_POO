package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import logico.EmpresaAltice;
import logico.Usuario;

public class Login extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtNombreDeUsuario;
    private JPasswordField txtContrsenia;
    private ArrayList<Usuario> usuarios;

    public Login(JFrame owner, ArrayList<Usuario> usuarios) {
        super(owner, true);
        this.usuarios = usuarios;
        
        setTitle("Acceso");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        Color alticeBlue = Color.decode("#0066FF");
        Color bgColor = Color.WHITE;
        Font fontLabel = new Font("SansSerif", Font.BOLD, 15);
        Font fontInput = new Font("SansSerif", Font.PLAIN, 16);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(bgColor);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // ==========================================
        // LOGO CON ESCALADO AUTOMÁTICO
        // ==========================================
        JLabel lblIconoLogo = new JLabel();
        lblIconoLogo.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            // 1. Cargamos la imagen original
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/recursos/Altice_logo_azul_sin_letras.png"));
            // 2. La escalamos a 60x60 píxeles para que no se desborde
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            // 3. Se la asignamos al JLabel
            lblIconoLogo.setIcon(new ImageIcon(imagenEscalada));
        } catch (Exception e) {
            System.out.println("No se encontró la imagen en /recursos/Altice_logo_azul.png");
        }
        lblIconoLogo.setBounds(0, 15, 500, 60);
        contentPanel.add(lblIconoLogo);

        JLabel lblLogo = new JLabel("altice", SwingConstants.CENTER);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 55));
        lblLogo.setForeground(alticeBlue);
        lblLogo.setBounds(0, 85, 500, 60);
        contentPanel.add(lblLogo);

        JLabel lblSubtitulo = new JLabel("Sistema de Gestión", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitulo.setForeground(Color.DARK_GRAY);
        lblSubtitulo.setBounds(0, 140, 500, 25);
        contentPanel.add(lblSubtitulo);

        // ==========================================
        // FORMULARIO
        // ==========================================
        JLabel lblNombreDeUsuario = new JLabel("Usuario");
        lblNombreDeUsuario.setFont(fontLabel);
        lblNombreDeUsuario.setBounds(70, 190, 360, 25);
        contentPanel.add(lblNombreDeUsuario);

        txtNombreDeUsuario = new JTextField();
        txtNombreDeUsuario.setFont(fontInput);
        txtNombreDeUsuario.setBorder(new LineBorder(alticeBlue, 2, true));
        txtNombreDeUsuario.setBounds(70, 220, 360, 45);
        contentPanel.add(txtNombreDeUsuario);

        JLabel lblContrasenia = new JLabel("Contraseña");
        lblContrasenia.setFont(fontLabel);
        lblContrasenia.setBounds(70, 290, 360, 25);
        contentPanel.add(lblContrasenia);

        txtContrsenia = new JPasswordField();
        txtContrsenia.setFont(fontInput);
        txtContrsenia.setBorder(new LineBorder(alticeBlue, 2, true));
        txtContrsenia.setBounds(70, 320, 360, 45);
        contentPanel.add(txtContrsenia);

        // ==========================================
        // BOTONES
        // ==========================================
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnIngresar.setBackground(alticeBlue);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setBounds(70, 420, 360, 50);
        
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombreDeUsuario.getText().trim();
                char[] con = txtContrsenia.getPassword();
                String contrasenia = new String(con);
                
                Usuario usuarioEncontrado = null;
                boolean encontrado = false;
                int ind = 0;
                
                while(ind < usuarios.size() && !encontrado) {
                    if(usuarios.get(ind).getNombreUsuario().equals(nombre)) {
                        usuarioEncontrado = usuarios.get(ind);
                        encontrado = true;
                    }
                    ind++;
                }
                
                if (usuarioEncontrado == null) {
                    JOptionPane.showMessageDialog(null, "El usuario ingresado no existe");
                } 
                else if (!usuarioEncontrado.getContrasenia().equals(contrasenia)) {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                } 
                else {
                    EmpresaAltice.getInstance().setLoginUser(usuarioEncontrado);
                    dispose();
                }
            }
        });
        contentPanel.add(btnIngresar);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSalir.setBackground(Color.WHITE);
        btnSalir.setForeground(alticeBlue);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(new LineBorder(alticeBlue, 2, true));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBounds(70, 490, 360, 50);
        
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        contentPanel.add(btnSalir);
    }
}