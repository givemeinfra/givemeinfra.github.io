package aimv.core;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.part.ViewPart;

public class ViewLabel implements ILabelProvider {

	@Override
	public Image getImage(Object element) {
		return ((ViewPart)element).getTitleImage();
	}

	@Override
	public String getText(Object element) {
		return ((ViewPart)element).getTitle();
	}

	@Override
	public void addListener(ILabelProviderListener listener) {}

	@Override
	public void dispose() {}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {}
	

}//class
