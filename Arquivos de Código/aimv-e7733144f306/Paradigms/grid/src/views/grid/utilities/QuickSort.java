package views.grid.utilities;

import aimv.modeling.Node;


public class QuickSort {
	
	
	public static void sort(Node[] list, String property, boolean cutoff) {
		if (cutoff)
			quickSort(list,0,list.length - 1,20, property);
		else
			quickSort(list,0,list.length - 1,0, property);
	}//sort
	
	private static void quickSort(Node[] list, int left, int right, int cutoff, String property) {
		
		if (left + cutoff < right) {
			Node pivo = median3(list, left, right, property);
			int i = left, j = right;
			for(;;) {
				do {
					if (i+1 >= right)
						break;
					i++;
				} while (compareTo(list[i], pivo, property) < 0);
				do {
					if (j-1 < left)
						break;
					j--;
				} while (compareTo(list[j], pivo, property) > 0);
				
				if (i < j)
					swapReferences(list,i,j);
				else
					break;
			}
			swapReferences(list, i, right);
			quickSort(list, left, i-1,cutoff, property);
			quickSort(list, i+1, right,cutoff, property);
		}
		else if (cutoff > 0) {
			int j;
			for (int i = left + 1; i <= right; i++) {
				Node ref = list[i];
				for (j = i; j > left && compareTo(ref, list[j-1], property) < 0; j--)
					list[j] = list[j-1];
				list[j] = ref;
			}
		}
		
	}//quickSort
	
	private static Node median3(Node[] list, int left, int right, String property) {
		
		int center = (left + right)/2;
		if (compareTo(list[center], list[left], property) < 0)
			swapReferences(list, left, center);
		if (compareTo(list[right], list[left], property) < 0)
			swapReferences(list, left, right);
		if (compareTo(list[right], list[center], property) < 0)
			swapReferences(list, center, right);
		swapReferences(list, center, right);
		return list[right];
		
	}//median3
	
	private static void swapReferences(Node[] list, int ind1, int ind2) {
		
		Node tmp = list[ind1];
		list[ind1] = list[ind2];
		list[ind2] = tmp;
		
	}//swapReferences
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int compareTo(Node node1, Node node2, String property) {
		
		if (node1 == null || node2 == null || property == null)
			return 0;
		
		Comparable valor1, valor2;
		if (node1.getProperty(property) instanceof Comparable) {
			valor1 = (Comparable) node1.getProperty(property);
			valor2 = (Comparable) node2.getProperty(property);
			return valor1.compareTo(valor2);
		}
		return 0;
		
	}//compareTo
	
	
}//QuickSort
