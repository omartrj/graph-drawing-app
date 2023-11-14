package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import model.DirectedGraphFactory;
import model.Graph;
import model.GraphFactory;
import model.UndirectedGraphFactory;

public class GraphOptionsPanel extends JPanel {

    protected DrawingPanel drawingPanel;
    protected GraphFactory graphFactory;

    public GraphOptionsPanel(DrawingPanel drawingPanel) {
        super(true);
        this.drawingPanel = drawingPanel;
        this.setLayout(new CardLayout());
        this.add(new RandomGraphOptionsPanel(), "Random");
        this.add(new CompleteGraphOptionsPanel(), "Complete");
        this.add(new RegularGraphOptionsPanel(), "Regular");
        this.add(new DagGraphOptionsPanel(), "DAG");
    }


    public JPanel getPanelByName(String panelName) {
        switch(panelName) {
            case "Random":
                return (RandomGraphOptionsPanel) this.getComponent(0);
            case "Complete":
                return (CompleteGraphOptionsPanel) this.getComponent(1);
            case "Regular":
                return (RegularGraphOptionsPanel) this.getComponent(2);
            case "DAG":
                return (DagGraphOptionsPanel) this.getComponent(3);
            default:
                return null;
        }
    }

    private class RandomGraphOptionsPanel extends AbstractGraphOptionsPanel {

        public RandomGraphOptionsPanel() {
            super("Random Graph Options");
            this.add(numOfNodesPanel());
            this.add(probPanel());
            this.add(directedPanel());
        }

        @Override
        public void generateGraph() {
            int numOfNodes = (int) numOfNodesSpinner.getValue();
            double prob = (double) probSlider.getValue() / 100;

            if (directedCheckBox.isSelected()) {
                graphFactory = new DirectedGraphFactory();
            } else {
                graphFactory = new UndirectedGraphFactory();
            }

            Graph graph = graphFactory.generateRandomGraph(numOfNodes, prob);
            drawingPanel.setGraph(graph);
            
        }
    }

    private class CompleteGraphOptionsPanel extends AbstractGraphOptionsPanel {

        public CompleteGraphOptionsPanel() {
            super("Complete Graph Options");
            this.add(numOfNodesPanel());
            this.add(directedPanel());
        }

        @Override
        public void generateGraph() {
            int numOfNodes = (int) numOfNodesSpinner.getValue();

            if (directedCheckBox.isSelected()) {
                graphFactory = new DirectedGraphFactory();
            } else {
                graphFactory = new UndirectedGraphFactory();
            }

            Graph graph = graphFactory.generateCompleteGraph(numOfNodes);
            drawingPanel.setGraph(graph);
        }
    }

    private class RegularGraphOptionsPanel extends AbstractGraphOptionsPanel {

        public RegularGraphOptionsPanel() {
            super("Regular Graph Options");
            this.add(numOfNodesPanel());
            this.add(degreePanel());
            this.add(directedPanel());
        }

        @Override
        public void generateGraph() {
            int numOfNodes = (int) numOfNodesSpinner.getValue();
            int degree = (int) degreeSpinner.getValue();

            if (directedCheckBox.isSelected()) {
                graphFactory = new DirectedGraphFactory();
            } else {
                graphFactory = new UndirectedGraphFactory();
            }

            Graph graph = graphFactory.generateRegularGraph(numOfNodes, degree);
            drawingPanel.setGraph(graph);
        }

    }

    private class DagGraphOptionsPanel extends AbstractGraphOptionsPanel {

        public DagGraphOptionsPanel() {
            super("DAG Graph Options");
            this.add(numOfNodesPanel());
            this.add(probPanel());
        }
        
        @Override
        public void generateGraph() {
            int numOfNodes = (int) numOfNodesSpinner.getValue();
            double prob = (double) probSlider.getValue() / 100;

            DirectedGraphFactory graphFactory = new DirectedGraphFactory();
            Graph graph = graphFactory.generateDAG(numOfNodes, prob);
            drawingPanel.setGraph(graph);
        }
    }


    public abstract class AbstractGraphOptionsPanel extends JPanel {

        protected JCheckBox directedCheckBox;
        protected JSpinner numOfNodesSpinner;
        protected JSlider probSlider;
        protected JSpinner degreeSpinner;

        public AbstractGraphOptionsPanel(String title) {
            super(true);
            this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }

        public abstract void generateGraph();
        
        protected JPanel numOfNodesPanel() {
            JPanel numOfNodesPanel = new JPanel(true);
            //numOfNodesPanel.setBackground(Color.YELLOW);
            numOfNodesPanel.setLayout(new BoxLayout(numOfNodesPanel, BoxLayout.X_AXIS));
            numOfNodesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel numOfNodesLabel = new JLabel("Number of Nodes: ");
            numOfNodesLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            numOfNodesLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            numOfNodesPanel.add(numOfNodesLabel);

            numOfNodesSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 50, 1));
            numOfNodesSpinner.setValue(5);
            numOfNodesSpinner.setAlignmentX(JSpinner.LEFT_ALIGNMENT);
            //Min value = 0, Max value = 50, Initial value = 5
            numOfNodesSpinner.setMaximumSize(new Dimension(100, 20));
            numOfNodesPanel.add(numOfNodesSpinner);
            
            return numOfNodesPanel;
        }

        protected JPanel probPanel() {
            JPanel probPanel = new JPanel(true);
            //probPanel.setBackground(Color.YELLOW);
            probPanel.setLayout(new BoxLayout(probPanel, BoxLayout.X_AXIS));
            probPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            
            JLabel probLabel = new JLabel("Probability: ");
            probLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            probLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            probPanel.add(probLabel);

            probSlider = new JSlider(0, 100, 50);
            probSlider.setMajorTickSpacing(50);
            probSlider.setMinorTickSpacing(25);
            //probSlider.setPaintTicks(true);
            probSlider.setPaintLabels(true);
            probSlider.setAlignmentX(JSlider.LEFT_ALIGNMENT);
            probPanel.add(probSlider);
            
            return probPanel;
        }

        protected JPanel degreePanel() {
            JPanel degreePanel = new JPanel(true);
            //degreePanel.setBackground(Color.YELLOW);
            degreePanel.setLayout(new BoxLayout(degreePanel, BoxLayout.X_AXIS));
            degreePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            
            JLabel degreeLabel = new JLabel("Degree: ");
            degreeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            degreeLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            degreePanel.add(degreeLabel);

            degreeSpinner = new JSpinner();
            SpinnerModel degreeSpinnerModel = new SpinnerNumberModel(2, 0, 49, 1);
            degreeSpinner.setModel(degreeSpinnerModel);
            degreeSpinner.setAlignmentX(JSpinner.LEFT_ALIGNMENT);
            degreeSpinner.setMaximumSize(new Dimension(100, 20));
            degreePanel.add(degreeSpinner);
            
            return degreePanel;
        }

        protected JPanel directedPanel() {
            JPanel directedPanel = new JPanel(true);
            //directedPanel.setBackground(Color.YELLOW);
            directedPanel.setLayout(new BoxLayout(directedPanel, BoxLayout.X_AXIS));
            directedPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            
            JLabel directedLabel = new JLabel("Directed: ");
            directedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            directedLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            directedPanel.add(directedLabel);

            directedCheckBox = new JCheckBox();
            directedCheckBox.setSelected(false);
            directedCheckBox.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
            directedPanel.add(directedCheckBox);
            
            return directedPanel;
        }
        
    }

    




}
