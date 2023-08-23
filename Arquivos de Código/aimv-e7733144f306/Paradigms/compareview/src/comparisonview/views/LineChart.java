package comparisonview.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class LineChart extends JFrame 
{

	private static final long serialVersionUID = 1L;
	private static JPanel panel;

    public static JPanel getPanel() {
		return panel;
	}

	public LineChart(final String title, ArrayList<String[]> listItens, int begin, int end) {
        super(title);
        
        // row keys...
        final String series1 = "Modified";
        final String series2 = "Indication";

        final DefaultCategoryDataset datasetAux = new DefaultCategoryDataset();       
        
        for(int i = begin; i < end; i++)
        {
        	datasetAux.addValue(Integer.parseInt(listItens.get(i)[1]), series1, listItens.get(i)[0]);
        	datasetAux.addValue(Integer.parseInt(listItens.get(i)[2]), series2, listItens.get(i)[0]);
        }
        
        final CategoryDataset dataset = datasetAux;     
        
        
        final JFreeChart chart = ChartFactory.createLineChart(
                //"Historical Compare View",      // chart title
        		"",      // chart title
                "Period",                   // domain axis label
                "Values",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
            );

        
        final Shape[] shapes = new Shape[3];
        int[] xpoints;
        int[] ypoints;

        // right-pointing triangle
        xpoints = new int[] {-3, 3, -3};
        ypoints = new int[] {-3, 0, 3};
        shapes[0] = new Polygon(xpoints, ypoints, 3);

        // vertical rectangle
        shapes[1] = new Rectangle2D.Double(-2, -3, 3, 6);

        // left-pointing triangle
        xpoints = new int[] {-3, 3, 3};
        ypoints = new int[] {0, -3, 3};
        shapes[2] = new Polygon(xpoints, ypoints, 3);

        final DrawingSupplier supplier = new DefaultDrawingSupplier(
            DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
            DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
            shapes
        );
        
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setDrawingSupplier(supplier);
        

        chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 3000, 0, Color.white));
        
        Color c = new Color(248,248,255);
		chart.getPlot().setBackgroundPaint(c);

        // set the stroke for each series...
        plot.getRenderer().setSeriesStroke(
            0, 
            new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        plot.getRenderer().setSeriesStroke(
            1, 
            new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        plot.getRenderer().setSeriesStroke(
            2, 
            new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );
        
        CategoryItemRenderer renderer = plot.getRenderer();  
        renderer.setBaseItemLabelFont(new Font("SansSerif", Font.PLAIN, 1));  

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setUpperMargin(0.12);
        rangeAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
        rangeAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10)); 
                
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1227, 225));
        chartPanel.setLayout(null);
        chartPanel.setBackground(Color.white);
        panel = new JPanel();
        panel.add(chartPanel);
        //setContentPane(panel); //
    }

    /*public static void main(final String[] args) {

        final LineChart demo = new LineChart("", null, 4, 10);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }*/
	
	public LineChart()
	{
		
	}
    
    public JComponent getLineChart(ArrayList<String[]> listItens, int begin, int end)
    {
    	panel = null;
    	final LineChart demo = new LineChart("", listItens, begin, end);
    	demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        return panel;
    }

}
