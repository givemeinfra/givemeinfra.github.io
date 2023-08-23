package givemeviews.model;

import java.util.ArrayList;

public class BucketItem 
{
	private ArrayList<Item> listBucketItems;

	public ArrayList<Item> getListBucketItems() {
		return listBucketItems;
	}

	public void setListBucketItems(ArrayList<Item> listBucketItems) {
		this.listBucketItems = listBucketItems;
	}
	
	public BucketItem()
	{
		
	}
	
	public BucketItem(ArrayList<Item> listItems) // this list will contain one object item to different kind of View (suck as GridView, MatrixView etc)
	{
		this.listBucketItems = listItems;
	}

}
