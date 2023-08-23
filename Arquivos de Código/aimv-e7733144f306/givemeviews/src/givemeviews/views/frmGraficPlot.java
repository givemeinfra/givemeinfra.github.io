package givemeviews.views;

import givemeviews.model.Arquivo;
import givemeviews.model.SetModules;
import givemeviews.model.SortModules;
import givemeviews.views.provider.ModulesComponentsProvider;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;

public class frmGraficPlot extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelPlot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmGraficPlot frame = new frmGraficPlot("");
				    frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void plot(String module)
	{
		ModulesComponentsProvider provider = new ModulesComponentsProvider();
		Arquivo arquivo = new Arquivo();
		ArrayList<SetModules> listComponents = arquivo.getListComponentsByModule(provider.providerChangeRequestList(), module);
		SortModules objSort = new SortModules();
		Collections.sort(listComponents, objSort);
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		int count = 20;		
		if(listComponents.size() < 20)
			count = listComponents.size();
		
		for(int i = 0; i < count; i++)
			dataset.setValue(listComponents.get(i).getOcorrencia(),"", listComponents.get(i).getComponenteAssociado());
			
		JFreeChart chart = ChartFactory.createBarChart("Components from " + module, "", "", dataset, PlotOrientation.VERTICAL, false, false, false);
		
		CategoryPlot catPlot = chart.getCategoryPlot();
		catPlot.setRangeGridlinePaint(Color.BLACK);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(0, 0, 776, 555);
		chartPanel.setMaximumDrawWidth(500);
		chartPanel.setMaximumDrawHeight(310);
		panelPlot.removeAll();
		panelPlot.setLayout(null);
		panelPlot.add(chartPanel);
		chartPanel.setAutoscrolls(true);
		chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.X_AXIS));
		panelPlot.validate();
		
	}

	/**
	 * Create the frame.
	 */
	public frmGraficPlot(String module) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelPlot = new JPanel();
		panelPlot.setBounds(0, 0, 1024, 600);
		
		
		contentPane.add(panelPlot);
		plot(module);
	}	
}
