package comparisonview.views;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Log;
import org.jfree.util.PrintStreamLogTarget;

/**
 * A simple demonstration application showing how to create a horizontal 3D bar chart using data
 * from a {@link CategoryDataset}.
 *
 */
public class Bar3DChart extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFreeChart chart;
	private static ChartPanel chartPanel;
	private static JPanel panel;
	
	public static JPanel getPanel() {
		return panel;
	}

	public Bar3DChart(final String title, float modificationLength, float indicationLength) 
    {
        super(title);
        
        // create the chart...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(modificationLength, "Modified", "Modified");  
        dataset.addValue(indicationLength, "Indications", "Indications");  
        
        chart = ChartFactory.createBarChart3D(
                "",       // chart title
                "",                  // domain axis label
                "",                     // range axis label
                dataset,                     // data
                PlotOrientation.VERTICAL,  // orientation
                false,                        // include legend
                true,                        // tooltips
                false                        // urls
            );

        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setForegroundAlpha(1.0f);

        // left align the category labels...
        final CategoryAxis axis = plot.getDomainAxis();
        final CategoryLabelPositions p = axis.getCategoryLabelPositions();
        
        final CategoryLabelPosition left = new CategoryLabelPosition(
            RectangleAnchor.LEFT, TextBlockAnchor.TOP_LEFT, 
            TextAnchor.TOP_LEFT, 0.0,
            CategoryLabelWidthType.RANGE, 0.30f
        );
        axis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(p, left));
        
        // add the chart to a panel...
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(270, 162));
        chartPanel.setBackground(Color.white);
        panel = new JPanel();
        panel.add(chartPanel);
        //setContentPane(chartPanel);
    }

   
    /*public static void main(final String[] args) {

        Log.getInstance().addTarget(new PrintStreamLogTarget());
        final Bar3DChart demo = new Bar3DChart("3D Bar Chart Demo 2");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }*/

	public Bar3DChart()
	{
		
	}
	
	public JComponent getBarChart(float modificationLength, float indicationLength)
	{
		Log.getInstance().addTarget(new PrintStreamLogTarget());
        final Bar3DChart demo = new Bar3DChart("", modificationLength, indicationLength);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
		return panel;
	}
}
