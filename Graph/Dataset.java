/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author paliesek.vladimir
 */
public class Dataset {
    private final XYSeriesCollection dataset;
    
    public Dataset(){
        dataset = new XYSeriesCollection();
    }
    
    public void addSeries(XYSeries newSeries){
        dataset.addSeries(newSeries);
    }
    public XYSeriesCollection getDataset(){
        return dataset;
    }
}
