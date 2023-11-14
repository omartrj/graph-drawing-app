package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Graph;
import model.UndirectedGraph;
import view.GraphOptionsPanel.AbstractGraphOptionsPanel;
import view.AlgorithmOptionsPanel.AbstractAlgoPanel;

public class ControlPanel extends JPanel {

    public static final int WIDTH = 250;
    public static final int HEIGHT = MainFrame.HEIGHT;
    private static final Graph DEFAULT_GRAPH = new UndirectedGraph();

    public ControlPanel(DrawingPanel drawPanel) {
        super(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        this.add(new TitlePanel());
        this.add(new GraphGeneratorPanel(drawPanel));
        this.add(new AlgorithmPanel(drawPanel));
        this.add(new DrawingStrategyPanel(drawPanel));

        drawPanel.setGraph(DEFAULT_GRAPH);
    }

    private class TitlePanel extends JPanel {

        public TitlePanel() {
            super(true);
            //this.setBackground(Color.RED);
            this.setBackground(Color.LIGHT_GRAY);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            this.setBounds(0, 10, 250, 80);

            JLabel title = new JLabel("Graph Visualization App");
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            JLabel author = new JLabel("by: Omar Criacci");
            author.setFont(new Font("Arial", Font.PLAIN, 14));
            author.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            JLabel authorGithub = new JLabel("github.com/omartrj");
            authorGithub.setFont(new Font("Arial", Font.PLAIN, 14));
            authorGithub.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            this.add(title);
            this.add(author);
            this.add(authorGithub);
        }
    }

    private class GraphGeneratorPanel extends JPanel {

        public GraphGeneratorPanel(DrawingPanel drawPanel) {
            //this.setBackground(Color.BLUE);
            this.setBackground(Color.LIGHT_GRAY);
            this.setBounds(0, 100, 250, 220);
            this.setLayout(null);

            // Title
            JLabel title = new JLabel("Graph Generator Tool");
            title.setFont(new Font("Arial", Font.BOLD, 16));
            title.setBounds(0, 0, 250, 30);
            title.setHorizontalAlignment(JLabel.CENTER);
            this.add(title);

            // Graph Type Panel
            JPanel graphTypePanel = new JPanel();
            graphTypePanel.setBackground(getBackground());
            graphTypePanel.setLayout(new BoxLayout(graphTypePanel, BoxLayout.X_AXIS));
            graphTypePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 5, 30));
            graphTypePanel.setBounds(0, 30, 250, 30);

            JLabel graphTypeLabel = new JLabel("Graph Type: ");
            graphTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            graphTypeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            String[] graphTypes = {"Random", "Complete", "Regular", "DAG"};
            JComboBox<String> graphTypeComboBox = new JComboBox<>(graphTypes);
            graphTypeComboBox.setSelectedItem("Random");
            graphTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
            graphTypeComboBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            graphTypePanel.add(graphTypeLabel);
            graphTypePanel.add(graphTypeComboBox);
            this.add(graphTypePanel);

            // Graph Options Panel
            GraphOptionsPanel graphOptionsPanel = new GraphOptionsPanel(drawPanel);
            graphOptionsPanel.setBounds(25, 60, 200, 125);
            this.add(graphOptionsPanel);

            graphTypeComboBox.addActionListener(e -> {
                String selectedGraphType = (String) graphTypeComboBox.getSelectedItem();
                CardLayout cardLayout = (CardLayout) graphOptionsPanel.getLayout();
                cardLayout.show(graphOptionsPanel, selectedGraphType);
            });

            // Generate Graph Button
            JButton generateGraphButton = new JButton("Generate");
            generateGraphButton.setBounds(130, 190, 90, 30);
            this.add(generateGraphButton);

            generateGraphButton.addActionListener(e -> {
                // Launch the generateGraph() method of the selected graph options panel
                String selectedGraphType = (String) graphTypeComboBox.getSelectedItem();
                AbstractGraphOptionsPanel selectedGraphOptionsPanel = (AbstractGraphOptionsPanel) graphOptionsPanel.getPanelByName(selectedGraphType);
                selectedGraphOptionsPanel.generateGraph();
            });

            // Clear Graph Button
            JButton clearGraphButton = new JButton("Clear");
            clearGraphButton.setBounds(30, 190, 90, 30);
            this.add(clearGraphButton);

            clearGraphButton.addActionListener(e -> {
                drawPanel.setGraph(new UndirectedGraph());
            });

                
        }
    }

    private class AlgorithmPanel extends JPanel {

        public AlgorithmPanel(DrawingPanel drawPanel) {
            //this.setBackground(Color.GREEN);
            this.setBackground(Color.LIGHT_GRAY);
            this.setBounds(0, 330, 250, 215);
            this.setLayout(null);

            // Title
            JLabel title = new JLabel("Visualization Algorithm");
            title.setFont(new Font("Arial", Font.BOLD, 16));
            title.setBounds(0, 0, 250, 30);
            title.setHorizontalAlignment(JLabel.CENTER);
            this.add(title);

            // Algorithm Panel
            JPanel algorithmPanel = new JPanel();
            algorithmPanel.setBackground(getBackground());
            //algorithmPanel.setBackground(Color.WHITE);
            //algorithmPanel.setLayout(new GridLayout(1, 2));
            algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.X_AXIS));
            algorithmPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 5, 30));
            algorithmPanel.setBounds(0, 30, 250, 30);

            JLabel algorithmLabel = new JLabel("Algorithm:");
            algorithmLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            //algorithmLabel.setHorizontalAlignment(JLabel.CENTER);
            algorithmLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            String[] algorithms = {"Random", "Tutte's Barycenter", "Spring Embedder", "Fruchterman-Reingold"};
            JComboBox<String> algorithmComboBox = new JComboBox<>(algorithms);
            algorithmComboBox.setSelectedItem("Random");
            algorithmComboBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            algorithmComboBox.setFont(new Font("Arial", Font.PLAIN, 12));

            algorithmPanel.add(algorithmLabel);
            algorithmPanel.add(algorithmComboBox);
            this.add(algorithmPanel);

            // Algorithm Options Panel
            AlgorithmOptionsPanel algorithmOptionsPanel = new AlgorithmOptionsPanel(drawPanel);
            algorithmOptionsPanel.setBounds(25, 60, 200, 120);
            this.add(algorithmOptionsPanel);

            algorithmComboBox.addActionListener(e -> {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                CardLayout cardLayout = (CardLayout) algorithmOptionsPanel.getLayout();
                cardLayout.show(algorithmOptionsPanel, selectedAlgorithm);
            });

            // Run Algorithm Button
            JButton runAlgorithmButton = new JButton("Run");
            runAlgorithmButton.setBounds(80, 185, 90, 30);
            this.add(runAlgorithmButton);

            runAlgorithmButton.addActionListener(e -> {
                // Launch the runAlgorithm() method of the selected algorithm options panel
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                AbstractAlgoPanel selectedAlgorithmOptionsPanel = (AbstractAlgoPanel) algorithmOptionsPanel.getPanelByName(selectedAlgorithm);
                selectedAlgorithmOptionsPanel.runAlgorithm();
            });


        }
    }

    private class DrawingStrategyPanel extends JPanel {

        public DrawingStrategyPanel(DrawingPanel drawPanel) {
            //this.setBackground(Color.YELLOW);
            this.setBackground(Color.LIGHT_GRAY);
            this.setBounds(0, 555, 250, 215);
            this.setLayout(null);

            JLabel title = new JLabel("Drawing Strategy");
            title.setFont(new Font("Arial", Font.BOLD, 16));
            title.setBounds(0, 0, 250, 30);
            title.setHorizontalAlignment(JLabel.CENTER);
            this.add(title);

            String[] drawingStrategies = {"Light Mode", "Small Nodes", "Dark Mode"};
            JComboBox<String> drawingStrategiesComboBox = new JComboBox<>(drawingStrategies);
            drawingStrategiesComboBox.setSelectedItem("Classic Mode");
            drawingStrategiesComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
            drawingStrategiesComboBox.setBounds(25, 35, 200, 30);

            this.add(drawingStrategiesComboBox);

            drawingStrategiesComboBox.addActionListener(e -> {
                String selectedDrawingStrategy = (String) drawingStrategiesComboBox.getSelectedItem();
                switch (selectedDrawingStrategy) {
                    case "Light Mode":
                        drawPanel.setDrawingStrategy(DrawingStrategy.LIGHT_MODE);
                        break;
                    case "Small Nodes":
                        drawPanel.setDrawingStrategy(DrawingStrategy.SMALL_NODES);
                        break;
                    case "Dark Mode":
                        drawPanel.setDrawingStrategy(DrawingStrategy.DARK_MODE);
                        break;
                }
            });

        }
    }
}
