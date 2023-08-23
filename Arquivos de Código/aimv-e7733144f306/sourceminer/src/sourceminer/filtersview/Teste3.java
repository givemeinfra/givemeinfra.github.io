package sourceminer.filtersview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowLayout;

public class Teste3 extends Shell {
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Teste3 shell = new Teste3(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Teste3(final Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new GridLayout(1, false));
		
		Group grpId = new Group(this, SWT.NONE);
		grpId.setText("ID");
		grpId.setLayout(new GridLayout(3, false));
		
		text = new Text(grpId, SWT.BORDER);
		
		Button btnOk = new Button(grpId, SWT.NONE);
		btnOk.setText("ok");
		
		Button button = new Button(grpId, SWT.NONE);
		//grpId.pack();
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
