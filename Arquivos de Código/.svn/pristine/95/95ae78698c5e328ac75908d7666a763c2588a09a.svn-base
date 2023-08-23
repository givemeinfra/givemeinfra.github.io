package sourceminer.filtersview;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import sourceminer.filters.controls.RangeSlider;
import sourceminer.utilities.Properties;
import aimv.controllers.AIMV;
import aimv.controllers.Nodes;
import aimv.filters.Filter;
import aimv.modeling.Node;
import aimv.utilities.Imagens;
import aimv.views.FilterView;


public class FiltersRanger extends FilterView {
	
	private Filter filter;
	
	private ArrayList<RangeSlider> sliders;
	private ArrayList<Button> resets;
	private ArrayList<Button> applys;
	private ArrayList<Combo> combosMin;
	private ArrayList<Combo> combosMax;
	private ArrayList<Group> groups;
	
	private MouseMoveListener sliderMove;
	
	private RangeSlider slider;
	private Combo cbMax;
	private Combo cbMin;
	private Button reset;
	private Button apply;
	
	private String[] filtros = {"Number of Methods (Class and Interface)","Lines of code (Class and Inerface)","Complexity (Method)",
			"Lines of code (Method)","Afferent Coupling","Efferent Coupling"};
	
	private String[] filters = {"nmfilter","locclassfilter","complexityfilter","locmethodfilter","acfilter","ecfilter"};

	private Label logResults;
	
	private void applyFilter() {
		
		if (filter == null)
			return;
		String[] args = {cbMin.getText(), cbMax.getText()};
		filter.apply(args);
			
	}//applyFilter
	
	private void indexOfControl(Object control) {
		
		int index;
		if (control instanceof Combo)
			index = groups.indexOf(((Control) control).getParent().getParent());
		else
			index = groups.indexOf(((Control) control).getParent());
		
		slider = sliders.get(index);
		cbMin = combosMin.get(index);
		cbMax = combosMax.get(index);
		reset = resets.get(index);
		apply = applys.get(index);
		filter = AIMV.getFilter("sourceminer.filters."+ filters[index]);
		
	}//indexOfControl

	@SuppressWarnings("unchecked")
	@Override
	protected void createLayout() {
		
		Composite parent = getComposite();
		LinkedHashMap<String, Object> valores = new LinkedHashMap<String, Object>();
		if (AIMV.getProperty("filtros_range") != null)
			valores = (LinkedHashMap<String, Object>) AIMV.getProperty("filtros_range");		
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		Composite c = new Composite(scrolledComposite, SWT.NONE);
		c.setLayout(new RowLayout(SWT.VERTICAL));
		
		groups = new ArrayList<Group>();
		sliders = new ArrayList<RangeSlider>();
		resets = new ArrayList<Button>();
		applys = new ArrayList<Button>();
		combosMin = new ArrayList<Combo>();
		combosMax = new ArrayList<Combo>();
		
		
		for (String filtro : filtros) {
			
			//cria o grupo para ordenar os filtros
			Group group = new Group(c, SWT.NONE);	
			group.setLayout(new GridLayout(3,false));
			group.setText(filtro);
			groups.add(group);
			
			Integer[] list = new Integer[0];
			if (valores.containsKey(filtro))
				list = (Integer[]) valores.get(filtro);
			
			String minString = "";
			String maxString = "";
			
			int min = 1;
			int max = 1;
			
			if (list.length != 0) {
				minString = list[0].toString();
				maxString = list[list.length-1].toString();
				max = list.length;
			}

			//cria o RangeSlider do filtro e adiciona na lista de sliders
			slider = new RangeSlider(group, SWT.NONE, min, max, minString, maxString);
			sliders.add(slider);
			
			apply = new Button(group, SWT.NONE);
			apply.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/filter.png"));
			apply.setToolTipText("Apply Filter");
			applys.add(apply);
			
			//cria o botão para resetar o filtro
			reset = new Button(group, SWT.NONE);
			reset.setImage(Imagens.getPluginImage(Properties.PLUGIN_ID, "icons/refresh.png"));
			reset.setToolTipText("Reset Filter");
			resets.add(reset);
			
			
			//cria o container para os textos
			Composite composite = new Composite(group, SWT.NONE);
			composite.setLayout(new GridLayout(4,false));
			composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
			
			Label lbMinNM = new Label(composite, SWT.NONE);
			lbMinNM.setText("Min:");
			
			String[] str = new String[list.length];
			for (int i = 0; i < list.length; i++)
				str[i] = list[i].toString();
				
			//cria o combo para os valores minimos
			cbMin = new Combo(composite, SWT.NONE);
			cbMin.setItems(str);
			cbMin.select(0);
			combosMin.add(cbMin);
			
			Label lbMaxNM = new Label(composite, SWT.CENTER);
			lbMaxNM.setText("Max:");
			
			//cria o combo para os valores maximos
			cbMax = new Combo(composite, SWT.NONE);
			cbMax.setItems(str);
			cbMax.select(list.length - 1);
			combosMax.add(cbMax);
			
			if (list.length == 0) {
				slider.setEnabled(false);
				reset.setEnabled(false);
				apply.setEnabled(false);
				cbMin.setEnabled(false);
				cbMax.setEnabled(false);
			}
			
			//atualização dos combos quando o range é movido
			sliderMove = new MouseMoveListener() {
				@Override
				public void mouseMove(MouseEvent e) {
					cbMin.select((int) Math.round(slider.getLeftValue()-1));
					cbMax.select((int) Math.round(slider.getRightValue()-1));
				}
			};
			
			slider.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					indexOfControl(e.getSource());
					slider.removeMouseMoveListener(sliderMove);
					//applyFilter();
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					indexOfControl(e.getSource());
					slider.addMouseMoveListener(sliderMove);
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {}
				
			});
			
			reset.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					indexOfControl(e.getSource());
					
					BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
						public void run() {
							
							cbMin.select(0);
							cbMax.select(cbMax.getItemCount() - 1);
							slider.setLeftValue(cbMin.getSelectionIndex() + 1);
							slider.setRightValue(cbMax.getSelectionIndex() + 1);
							applyFilter();
							logResults.setText("");					
						}
					});
				}
			});
			
			apply.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					indexOfControl(e.getSource());
					
					BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
						public void run() {
							
							applyFilter();
								
							int cont = 0;
							for(Node node : Nodes.getGroup("all").getNodes()){
								if(node.isFiltered()){
									cont++;
								}
							}
							logResults.setText(cont+" of "+Nodes.getGroup("all").getNodes().size()+" nodes filtered");							
						
						}
					});
					
					
					
				}
			});
			
			KeyListener key = new KeyListener() {
				
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.keyCode == 13 ) {
						indexOfControl(e.getSource());
						Combo cb = (Combo) e.getSource();
						int index = cb.indexOf(cb.getText());
						if (index == -1) {
							cbMax.select((int) Math.round(slider.getRightValue()-1));
							cbMin.select((int) Math.round(slider.getLeftValue()-1));
						}
						else 
							cb.select(index);
						
						if (cbMax.equals(e.getSource())) {
							if (cbMax.getSelectionIndex() < cbMin.getSelectionIndex())
								cbMin.select(cbMax.getSelectionIndex());
						}
						else {
							if (cbMin.getSelectionIndex() > cbMax.getSelectionIndex())
								cbMax.select(cbMin.getSelectionIndex());
						}
						slider.setLeftValue(cbMin.getSelectionIndex() + 1);
						slider.setRightValue(cbMax.getSelectionIndex() + 1);
						//applyFilter();
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {}
				
			};
			
			cbMin.addKeyListener(key);
			cbMax.addKeyListener(key);
			
			FocusListener focus = new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					indexOfControl(e.getSource());
					cbMin.select((int) Math.round(slider.getLeftValue()-1));
					cbMax.select((int) Math.round(slider.getRightValue()-1));
				}
				
				@Override
				public void focusGained(FocusEvent e) {}
				
			};
			
			cbMin.addFocusListener(focus);
			cbMax.addFocusListener(focus);
			
			SelectionAdapter adapter = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					indexOfControl(e.getSource());
					if (cbMax.equals(e.getSource())) {
						if (cbMax.getSelectionIndex() < cbMin.getSelectionIndex())
							cbMin.select(cbMax.getSelectionIndex());
					}
					else {
						if (cbMin.getSelectionIndex() > cbMax.getSelectionIndex())
							cbMax.select(cbMin.getSelectionIndex());
					}
					slider.setLeftValue(cbMin.getSelectionIndex() + 1);
					slider.setRightValue(cbMax.getSelectionIndex() + 1);
					//applyFilter();
				}
			};
			
			cbMin.addSelectionListener(adapter);
			cbMax.addSelectionListener(adapter);
			
		}
		
		logResults = new Label(c, SWT.LEFT);
		logResults.setSize(400, 20);
		logResults.setText("                                                                     ");

		scrolledComposite.setContent(c);
		scrolledComposite.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}//createLayout
	
	@Override
	protected void open() {}


	@Override
	protected void closed() {}
	
	
}//class



