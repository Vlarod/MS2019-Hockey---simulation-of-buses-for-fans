/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;;

import org.jfree.data.xy.XYSeries;

/**
 *
 * @author paliesek.vladimir
 */
public class Series {
    private XYSeries series;
    
    public Series(String name){
        series = new XYSeries(name);
    }
    public void addSeries(double x, double y){
        series.add(x, y);
    }
    public XYSeries getSeries(){
        return series;
    }
}
