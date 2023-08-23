package givemeviews.persistence;

import givemeviews.metrics.Metric;
import givemeviews.model.BucketItem;
import givemeviews.model.MaintenanceType;
import givemeviews.model.Projeto;
import givemeviews.model.SetModules;

import java.util.ArrayList;

public class MemoryApplication 
{
	private static MemoryApplication instancia = null; // Patten Object
	private static ArrayList<Metric> listMetrics; // List of GiveMe Views Metrics
	private static ArrayList<MaintenanceType> listTypesMaintenance; // contain list of maintenance types (ex: Erro no sistema, Alteração legal, Solicitação de novo recurso)
	private static ArrayList<SetModules> listModules; // Modules that set maintenance    
	private static ArrayList<SetModules> listModulesAndComponents; // Full list
    private static Projeto project; // selected project    
    private static Boolean generatedSource; // can be true (where are statistic date) or false (where aren't statistic date)    
    private static float[][] masterProbabilityTable;	           // Full statistic matrix
    private static ArrayList<SetModules> MatrixViewlistModulesAndComponents; // Simple list. Contain only Components in the selected module.
    private static float[][] matrixViewProbabilityTable;
    private static BucketItem bucket; // Bucket of items. Items are process by GiveMe Views
    private static String gmmRepositoryPath;  
    private static int statisticRange; 

	private MemoryApplication()
	{
		listModules = new ArrayList<>();
		listMetrics = new ArrayList<>();
		listTypesMaintenance = new ArrayList<>();  
	    project = new Projeto();	    
	    generatedSource = false; // NEVER change to false manually, only in frequencyDistribution.class	    
	    masterProbabilityTable = null;
	    MatrixViewlistModulesAndComponents = new ArrayList<>();
	    matrixViewProbabilityTable = null;
	    listModulesAndComponents = new ArrayList<>();
	    bucket = new BucketItem();
	    gmmRepositoryPath = "C:\\GiveMeRepository";
	    statisticRange = 10;
	}
	
	public static MemoryApplication getMemory()
	{
		if(instancia == null)
			instancia = new MemoryApplication();
		return instancia;		
	}

	public static ArrayList<SetModules> getListModules() {
		return listModules;
	}

	public static void setListModules(ArrayList<SetModules> listModules) 
	{
		getMemory();
		MemoryApplication.listModules.removeAll(listModules);
		MemoryApplication.listModules = listModules;
	}
	
	public static ArrayList<Metric> getListMetrics() {
		return listMetrics;
	}
	
	public static void setMetrics(Metric objMetric)
	{
		getMemory();
		instancia.listMetrics.add(objMetric);
	}
	
	public static ArrayList<MaintenanceType> getListTypesMaintenance() {
		return listTypesMaintenance;
	}
	
	public static void setMaintenanceTypes(ArrayList<MaintenanceType> list)
	{
		getMemory();
		instancia.listTypesMaintenance.removeAll(instancia.listTypesMaintenance);
		for(int i = 0; i < list.size(); i++)
			instancia.listTypesMaintenance.add(list.get(i));
	}
	
	public static void clearMaintenanceTypes()
	{
		getMemory();
		instancia.listTypesMaintenance.removeAll(listTypesMaintenance);
	}
	
	public static Projeto getProject() {
		getMemory();
		return project;
	}

	public static void setProject(Projeto newProject) {
		getMemory();
		instancia.project = newProject;
	}
	
	
	public static void clearListMetrics() 
	{
		getMemory();
		if(instancia.listMetrics.size() > 0)
			MemoryApplication.listMetrics.removeAll(listMetrics);
	}
	
	public static Boolean getGeneratedSource() {
		return generatedSource;
	}

	public static void setGeneratedSource(Boolean generatedSource) {
		getMemory();
		instancia.generatedSource = generatedSource;
	}
	
	public static float[][] getMasterProbabilityTable() {
		return masterProbabilityTable;
	}

	public static void setMasterProbabilityTable(float[][] masterProbabilityTable) {
		getMemory();
		instancia.masterProbabilityTable = masterProbabilityTable;
	}
	
	public static ArrayList<SetModules> getMatrixListModulesAndComponents() {
		return MatrixViewlistModulesAndComponents;
	}

	public static void setMatrixListModulesAndComponents(ArrayList<SetModules> pmatrixViewListModulesAndComponents) {
		getMemory();
		//instancia.MatrixViewlistModulesAndComponents.removeAll(instancia.MatrixViewlistModulesAndComponents);
		instancia.MatrixViewlistModulesAndComponents = pmatrixViewListModulesAndComponents;
	}
	
	public static float[][] getMatrixViewProbabilityTable() {
		return matrixViewProbabilityTable;
	}

	public static void setMatrixViewProbabilityTable(float[][] matrixViewProbabilityTable) {
		getMemory();
		instancia.matrixViewProbabilityTable = matrixViewProbabilityTable;
	}
	
	public static ArrayList<SetModules> getListModulesAndComponents() {
		return listModulesAndComponents;
	}

	public static void setListModulesAndComponents(ArrayList<SetModules> plistModulesAndComponents) {
		getMemory();
		//instancia.listModulesAndComponents.removeAll(instancia.listModulesAndComponents);
		instancia.listModulesAndComponents = plistModulesAndComponents;
	}

	public static BucketItem getBucket() {
		return bucket;
	}

	public static void setBucket(BucketItem bucket) {
		getMemory();
		instancia.bucket = bucket;
	}

	public static String getGmmRepositoryPath() {
		return gmmRepositoryPath;
	}

	public static void setGmmRepositoryPath(String gmmRepositoryPath) {
		MemoryApplication.gmmRepositoryPath = gmmRepositoryPath;
	}
	
	public static void clearMasterProbabilityTable()
	{
		masterProbabilityTable = null;	
	}
	
	public static void clearMatrixProbabilityTable()
	{
		matrixViewProbabilityTable = null;
	}
	
	public static void clearBucket()
	{
		bucket = null;
		bucket = new BucketItem();
	}
	
	public static int getStatisticRange() {
		return statisticRange;
	}

	public static void setStatisticRange(int statisticRange) {
		getMemory();
		MemoryApplication.statisticRange = statisticRange;
	}
}
