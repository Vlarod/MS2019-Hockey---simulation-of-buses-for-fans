/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author paliesek.vladimir
 */
public class Chart {
    private JFreeChart chart = null;
    private String axisXName;
    private String axisYName;
    private XYDataset dataset;
    private XYPlot plot;
    
    public Chart(String axisY,XYDataset dataset){
        this.axisYName = axisY;
        this.dataset = dataset;
        
        chart = ChartFactory.createXYLineChart("graf", "Zamestnanec", axisYName, dataset);
        plot = chart.getXYPlot();
        ((NumberAxis)chart.getXYPlot().getRangeAxis()).setAutoRangeIncludesZero(false);
        ((NumberAxis)chart.getXYPlot().getDomainAxis()).setAutoRangeIncludesZero(false);
       
    }
    
    public Chart(String name,String axisX, String axisY,XYDataset dataset){
        this.axisXName = axisX;
        this.axisYName = axisY;
        this.dataset = dataset;
        
        chart = ChartFactory.createXYLineChart(name, axisXName, axisYName, dataset);
        plot = chart.getXYPlot();
        ((NumberAxis)chart.getXYPlot().getRangeAxis()).setAutoRangeIncludesZero(false);
        ((NumberAxis)chart.getXYPlot().getDomainAxis()).setAutoRangeIncludesZero(false);
       
    }
    
    public void setDataset(XYDataset newDataset){
        plot.setDataset(newDataset);
    }
    
    public ChartPanel getChartPanel()
    {
        return new ChartPanel(chart) {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(550, 300);
        }
    };
    }
    public JFreeChart getChart(){
        return chart;
    }
    
    public void setName(String newTitle) {
        this.chart.setTitle(newTitle);
    }

}
