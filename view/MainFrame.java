package view;

import javax.swing.JFrame;
import java.awt.Toolkit;

public class MainFrame extends JFrame {

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;
    
    private DrawingPanel drawPanel;
    private ControlPanel controlPanel;

    public MainFrame() {
        super("Graph Editor");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        this.drawPanel = new DrawingPanel();
        this.controlPanel = new ControlPanel(drawPanel);

        this.getContentPane().add(controlPanel, "West");
        this.getContentPane().add(drawPanel, "Center");

        //add a icon
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/figures/icon.png")));
        
    }
    
}
