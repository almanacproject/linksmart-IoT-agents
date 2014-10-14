package it.ismb.pertlab.pwal.connectors.gui;

import it.ismb.pertlab.pwal.connectors.rest.ChartService;
import it.ismb.pertlab.pwal.highcharts.bean.DataBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HighChartsController {

    @Autowired
    ChartService chartService;


    @RequestMapping(value="charts", method=RequestMethod.GET)
    public String showCharts() {
        return "gui";
    }

    @RequestMapping(value="/temperature", method=RequestMethod.GET)
    @ResponseBody
    public DataBean showLineChart1() {
        return chartService.getTemperaturelineChartData();
    }

    @RequestMapping(value="/smartsantander/{deviceId}", method=RequestMethod.GET)
    @ResponseBody
    public DataBean showAverageSpeed(@PathVariable String deviceId) {
        return chartService.getSantanderSplineChartData(deviceId);
    }
    
    @RequestMapping(value="/tempsplinechart", method=RequestMethod.GET)
    @ResponseBody
    public DataBean showSplineChart1() {
        return chartService.getTemperatureSplineChartData();
    }
    
    @RequestMapping(value="/distancesplinechart", method=RequestMethod.GET)
    @ResponseBody
    public DataBean showSplineChart2() {
        return chartService.getDistanceSplineChartData();
    }


    @RequestMapping(value="/accel3dchart", method=RequestMethod.GET)
    @ResponseBody
    public DataBean showLineChart3() {
        return chartService.getAccel3DChartData();
    }


}