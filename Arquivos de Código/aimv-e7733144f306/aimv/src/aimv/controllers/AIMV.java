package aimv.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

import aimv.core.ImportJob;
import aimv.events.ControllerEvent;
import aimv.filters.Filter;
import aimv.listeners.IControllerListener;
import aimv.modules.Module;
import aimv.tools.Tool;
import aimv.views.ViewAIMV;


public class AIMV {

	
	private static final AIMV controller = new AIMV();
	private static ListenerList listeners;
	private static LinkedHashMap<String, Object> properties;
	
	private static Object fonte;
	private static LinkedHashMap<String, Tool> tools;
	private static LinkedHashMap<String, Filter> filters;
	private static LinkedHashMap<String, Module> modules;
	
	private static List<ViewAIMV> activeViews;
	private static LinkedHashMap<String, ViewAIMV> views;
	
	public static boolean flag = true;
	
	
	public AIMV() {
		
		try {
			IConfigurationElement[] config;
			IViewDescriptor[] viewDescriptor;
			
			listeners = new ListenerList();
			properties = new LinkedHashMap<String, Object>();
			
			//registrando ferramentas
			tools = new LinkedHashMap<String, Tool>();
			config = Platform.getExtensionRegistry().getConfigurationElementsFor("aimv.tools");
			for (IConfigurationElement e : config)
				tools.put(e.getAttribute("id"), (Tool)e.createExecutableExtension("class"));
			
			//registrando filtros
			filters = new LinkedHashMap<String, Filter>();
			config = Platform.getExtensionRegistry().getConfigurationElementsFor("aimv.filters");
			for (IConfigurationElement e : config)
				filters.put(e.getAttribute("id"), (Filter)e.createExecutableExtension("class"));
			
			//registrando modulos de importação
			modules = new LinkedHashMap<String, Module>();
			config = Platform.getExtensionRegistry().getConfigurationElementsFor("aimv.modules");
			for (IConfigurationElement e : config)
				modules.put(e.getAttribute("id"), (Module)e.createExecutableExtension("class"));
			
			//registrando visões
			views = new LinkedHashMap<String, ViewAIMV>();
			activeViews = new ArrayList<ViewAIMV>();
			viewDescriptor = PlatformUI.getWorkbench().getViewRegistry().getViews();
			for(int i = 0; i < viewDescriptor.length; i++){
				IViewPart view = viewDescriptor[i].createView();
				if (view instanceof ViewAIMV)
					views.put(((ViewAIMV)view).getID(), (ViewAIMV)view);
			}
			
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
			
	}//AIMV

	public static AIMV getDefault() {
		return controller;
	}//getDefault
	
	public static Object getProperty(String property) {
		return properties.get(property);
	}
	
	public static boolean hasProperty(String property) {
		 return properties.containsKey(property);
	}
	
	public static void setProperty(String property, Object value) {
		
		if (property == null)
			return;
		
		Object valor = properties.put(property, value);
		
		boolean flag = true;
		if (valor != null)
			flag = valor.equals(value);
		else if (value != null)
			flag = value.equals(valor);
	
		if (!flag) {
			ControllerEvent event = new ControllerEvent(controller);   
			event.property = property;
			event.previousValue = valor;
			event.afterValue = value;
			for (Object listener : listeners.getListeners())
				((IControllerListener) listener).changeProperty(event);
		}
		
	}//setProperty
    
    public synchronized static void addListener(IControllerListener listener) {
		listeners.add(listener);
    }//addListener

    public synchronized static void removeListener(IControllerListener listener) {
    	listeners.remove(listener);
    }//removeListener
    
    public synchronized static void removeAllListeners() {
		listeners.clear();
    }//removeAllListeners
    
    public static Tool getTool(String id) {
		return tools.get(id);
	}
	
	public static Collection<Tool> getTools() {
		return tools.values();
	}
	
	public static Filter getFilter(String id) {
		return filters.get(id);
	}
	
	public static Collection<Filter> getFilters() {
		return filters.values();
	}
	
	public static ViewAIMV getView(String id) {
		return views.get(id);
	}

	public static Collection<ViewAIMV> getViews() {
		return views.values();
	}
	
	public static List<ViewAIMV> getActiveViews() {
		return activeViews;
	}
	
	public static Collection<Module> getModules() {
		return modules.values();
	}

	public static Object getFonte() {
		return fonte;
	}

	public static void setFonte(Object fonte) {
		AIMV.fonte = fonte;
		
		ControllerEvent event = new ControllerEvent(AIMV.getDefault());   
		for (Object listener : listeners.getListeners())
			((IControllerListener) listener).startImport(event);
		
		ImportJob job = new ImportJob("Data import: ", fonte, listeners);
		job.schedule();
	}//setFonte
	
	
}//class
