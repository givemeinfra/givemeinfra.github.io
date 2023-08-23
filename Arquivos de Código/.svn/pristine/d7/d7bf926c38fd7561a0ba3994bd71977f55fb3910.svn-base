package collaborative.service.facade;

import javax.xml.rpc.ServiceException;

public class FacadeFactory {

	private static FacadeFactory instance;
	private ServiceFacadeServiceLocator messageFacadeService;
	private ServiceFacade serviceFacade;
	
	public FacadeFactory(){
		
	}
	
	public static FacadeFactory GET_INSTANCE(){
		
		if(FacadeFactory.instance==null){
			FacadeFactory.instance = new FacadeFactory();
		}
		
		return FacadeFactory.instance;
	}
	
	public ServiceFacade getMessageService(){
		
		if(messageFacadeService==null){
			messageFacadeService = new ServiceFacadeServiceLocator();
		}
		
		if(serviceFacade==null){
			try{
				serviceFacade = messageFacadeService.getServiceFacade();
			}catch(ServiceException e){
				e.printStackTrace();
			}
		}
		
		return serviceFacade;
	}
	
}
