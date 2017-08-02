import java.awt.*;
import javax.swing.*;

public class Logo extends javax.swing.JFrame {
    public static final int ANCHO = 680;
    public static final int LARGO = 830;
    private Parser parser;
    private Paleta paleta;
    private boolean modoDebug;
    private JScrollPane jScrollPane1;
    private JButton jButton1;
    private JButton jButton2;
    private JButton poligono;
    private JButton pentagrama;
    private JButton estrellaDavid;
    private JButton espiralC;
    private JButton cc;
    private JButton espirografo;
    private JButton regla;
    private JButton arbol;
    private JButton helecho;
    private JLabel jLabel1;
    private JTextArea jTextArea1;

    public Logo() {
        super("LOGO- Javier Chavez   Chavez");
        initComponents();
    }

    private void initComponents() {
        modoDebug=false;
                      
        jTextArea1 = new JTextArea(20,20);
        jTextArea1.setFont(new Font("Courier New", Font.PLAIN, 14));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setTabSize(4);
        jTextArea1.setBackground(new Color(252, 245, 252));
        jScrollPane1 = new JScrollPane (jTextArea1);
        jScrollPane1.setBounds(10,420,650,210);
        add(jScrollPane1);

        paleta = new Paleta(650, 400);
        paleta.setBounds(10,10, 650, 400);
        paleta.setBackground( new Color(255, 255, 255) );
        add(paleta);

        jButton1 = new JButton();
        jButton1.setText("Ejecutar");
        jButton1.setBounds(10, 640, 250, 20);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);

        jButton2 = new JButton();
        jButton2.setText("Reset");
        jButton2.setBounds(410, 640, 250, 20);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);


        setLayout(null);
        setVisible(true);
        setResizable(false);
        this.getContentPane().setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(ANCHO, LARGO));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setPreferredSize(new java.awt.Dimension(ANCHO, LARGO));
        
        

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        jTextArea1.setText(null);
        paleta.limpiar();
        paleta.repaint();
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String codigo=jTextArea1.getText();
        //System.out.println(codigo);
        parser= new Parser();
        paleta.setDibujo(parser.ejecutarCodigo(codigo));
        paleta.repaint();
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new Logo();
    }   
}
