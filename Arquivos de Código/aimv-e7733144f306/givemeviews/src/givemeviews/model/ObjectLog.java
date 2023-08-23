package givemeviews.model;

import java.util.ArrayList;
import java.util.Random;

public class ObjectLog 
{
	private int randomID;
	private ArrayList<String> listLogComponents;
	
	public int getRandomID() {
		return randomID;
	}
	public void setRandomID(int randomID) {
		this.randomID = randomID;
	}
	public ArrayList<String> getListLogComponents() {
		return listLogComponents;
	}
	public void setListLogComponents(ArrayList<String> listLogComponents) {
		this.listLogComponents = listLogComponents;
	}
	
	public ObjectLog(ArrayList<String> listMantisComponents)
	{
		Random gerador = new Random();
		this.randomID = gerador.nextInt();
		this.listLogComponents = listMantisComponents;
	}
	
}
