package givemeviews.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import aimv.views.FilterView;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class vpFilterView extends FilterView {

	public static final String ID = "givemeviews.views.vpFilterView"; //$NON-NLS-1$
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	public vpFilterView() {
		
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(null);
		
		Group grpFilters = new Group(container, SWT.NONE);
		grpFilters.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpFilters.setText("Filters");
		grpFilters.setBounds(10, 10, 679, 221);
		
		Label lblComparisonViewParameter = new Label(grpFilters, SWT.NONE);
		lblComparisonViewParameter.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblComparisonViewParameter.setText("Comparison View Parameter : ");
		lblComparisonViewParameter.setBounds(10, 31, 178, 15);
		
		text = new Text(grpFilters, SWT.BORDER);
		text.setText("20");
		text.setBounds(204, 31, 119, 21);
		
		Label lblRadialViewParameter = new Label(grpFilters, SWT.NONE);
		lblRadialViewParameter.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblRadialViewParameter.setBounds(10, 67, 162, 15);
		lblRadialViewParameter.setText("Radial View Parameter :");
		
		text_1 = new Text(grpFilters, SWT.BORDER);
		text_1.setText("10");
		text_1.setBounds(206, 64, 117, 21);
		
		Label lblTreeViewParameter = new Label(grpFilters, SWT.NONE);
		lblTreeViewParameter.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblTreeViewParameter.setBounds(10, 103, 162, 15);
		lblTreeViewParameter.setText("Tree View Parameter :");
		
		text_2 = new Text(grpFilters, SWT.BORDER);
		text_2.setText("0");
		text_2.setBounds(204, 100, 119, 21);
		
		text_3 = new Text(grpFilters, SWT.BORDER);
		text_3.setText("0");
		text_3.setBounds(204, 134, 119, 21);
		
		Label lblMatrixViewParameter = new Label(grpFilters, SWT.NONE);
		lblMatrixViewParameter.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMatrixViewParameter.setBounds(10, 137, 148, 15);
		lblMatrixViewParameter.setText("Matrix View Parameter : ");
		
		Label lblParameterIndicates = new Label(grpFilters, SWT.NONE);
		lblParameterIndicates.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblParameterIndicates.setBounds(10, 161, 287, 15);
		lblParameterIndicates.setText("Parameter 0 (zero) indicates undefined parameter");
		
		Button btnSave = new Button(grpFilters, SWT.NONE);
		btnSave.setBounds(222, 183, 75, 25);
		btnSave.setText("Save");

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	protected void createLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void closed() {
		// TODO Auto-generated method stub
		
	}

}
