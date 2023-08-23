package com.br.collaborativeAIMV.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import aimv.views.UtilityView;

import com.br.collaborativeAIMV.control.LoginControl;
import com.br.collaborativeAIMV.control.SystemMessagesControl;

public class SourceCompareView  extends UtilityView{

	private StyledText stOldContent;
	private StyledText stNewContent;

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void createLayout() {
		
		//Composite parent = getComposite();
		if (LoginControl.GET_INSTANCE().logado) {

			getComposite().setBackground(getComposite().getDisplay().getSystemColor(
					SWT.COLOR_DARK_GRAY));

			GridLayout layout = new GridLayout(2, true);
			getComposite().setLayout(layout);

			stOldContent = new StyledText(getComposite(), SWT.V_SCROLL);
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			stOldContent.setLayoutData(data);

			if (!SystemMessagesControl.old_content.equals(""))
				stOldContent.setText(SystemMessagesControl.old_content);
			else
				stOldContent.setText("");

			stNewContent = new StyledText(getComposite(), SWT.V_SCROLL);
			stNewContent.setLayoutData(data);

			if (!SystemMessagesControl.new_content.equals(""))
				stNewContent.setText(SystemMessagesControl.new_content);
			else
				stNewContent.setText("");

			
			getComposite().pack();
			
			
		}else{
			Label lblLog = new Label(getComposite(), SWT.NONE);
			lblLog.setText("You must Perform Login");
		}
		
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
