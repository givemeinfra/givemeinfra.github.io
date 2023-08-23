package givemeviews.views;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.SortOrder;
import java.awt.* ;
import java.text.NumberFormat;


public class frmGraficPlotWithPareto extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final frmGraficPlotWithPareto demo = new frmGraficPlotWithPareto("Pareto Chart Demo");
					demo.pack();
					RefineryUtilities.centerFrameOnScreen(demo);
					demo.setVisible(true);
					demo.setDefaultCloseOperation (DISPOSE_ON_CLOSE) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmGraficPlotWithPareto(final String title) 
	{
		super(title);

		final DefaultKeyedValues data = new DefaultKeyedValues();
		data.addValue("C", new Integer(4843));
		data.addValue("C++", new Integer(2098));
		data.addValue("C#", new Integer(26));
		data.addValue("Java", new Integer(1901));
		data.addValue("Perl", new Integer(2507));
		data.addValue("PHP", new Integer(1689));
		data.addValue("Python", new Integer(948));
		data.addValue("Ruby", new Integer(100));
		data.addValue("SQL", new Integer(263));
		data.addValue("Unix Shell", new Integer(485));

		data.sortByValues(SortOrder.DESCENDING);
		final KeyedValues cumulative = DataUtilities.getCumulativePercentages(data);
		final CategoryDataset dataset = DatasetUtilities.createCategoryDataset("Languages", data); // aqui vai ser "Modules/Components"

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart(
		"Freshmeat Software Projects", // chart title    (Título do gráfico)
		"Language", // domain axis label         "Modules/Components"
		"Projects", // range axis label          "% Maintenances"
		dataset, // data
		PlotOrientation.VERTICAL,
		true, // include legend
		true,
		false
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.addSubtitle(new TextTitle("By Programming Language")); // tirar
		chart.addSubtitle(new TextTitle("As at 5 March 2003"));      // tirar

		// set the background color for the chart...
		chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 1000, 0, Color.green));

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.02);
		domainAxis.setUpperMargin(0.02);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();

		final CategoryDataset dataset2 = DatasetUtilities.createCategoryDataset(
		"Cumulative", cumulative //trocar o nome Cumulative por "Pareto"
		);
		final NumberAxis axis2 = new NumberAxis("Percent");
		axis2.setNumberFormatOverride(NumberFormat.getPercentInstance());
		plot.setRangeAxis(1, axis2);
		plot.setDataset(1, dataset2);
		plot.setRenderer(1, renderer2);
		plot.mapDatasetToRangeAxis(1, 1);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		// OPTIONAL CUSTOMISATION COMPLETED.

		// add the chart to a panel...
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(550, 270));
		setContentPane(chartPanel);

	}

}
