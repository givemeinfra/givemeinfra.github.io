package givemeviews.views.provider;

import givemeviews.metrics.Metric;
import givemeviews.persistence.MemoryApplication;

import java.util.ArrayList;

public class MetricViewProvider 
{
	public MetricViewProvider()
	{
		
	}
	
	public ArrayList<Metric> providerMetrics()
	{
		MemoryApplication.getMemory();
		return MemoryApplication.getListMetrics();
	}

}
