package views.treemap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import views.treemap.utilities.Properties;
import aimv.modeling.Node;

public class SquarifiedLayout {
	
	private int offset;
	
	private int xInicialLinhaAtual;
	private int yInicialLinhaAtual;
	
	private int x;
	private int y;
	
	private int larguraTotal;
	private int alturaTotal;
	
	private int largura;
	private int altura;
	
	private int area;
	
	private int tamanhoLado;
	
	private double somatorioAreaItens;
	private boolean eixoVertical;
	
	
	public void layout(TreeMapItem[] itens, Rectangle areaDesenho) {
		
		this.somatorioAreaItens = 0;
		for (TreeMapItem item : itens)
			this.somatorioAreaItens += item.peso;

	
		this.offset = 3;
		this.x = areaDesenho.x + offset;
		this.y = areaDesenho.y + offset;
		this.largura = areaDesenho.width - 2 * offset;
		this.altura = areaDesenho.height - 2 * offset;
		this.larguraTotal = largura;
		this.alturaTotal = altura;
		this.area = largura * altura;

	
		this.xInicialLinhaAtual = this.x;
		this.yInicialLinhaAtual = this.y;
	
		this.tamanhoLado = altura;
		this.eixoVertical = true;
	
		if(largura < this.tamanhoLado){
			this.tamanhoLado = largura;
			this.eixoVertical = false;
		}

		
		double areaAtualItem = 0;
		double somatorioAreaDosItens = 0;
		
		Arrays.sort(itens, new Comparator<Object>(){

			@Override
			public int compare(Object node1, Object node2) {
				return new Double(((Integer)((TreeMapItem) node1).peso).intValue()).compareTo(new Double(((Integer)((TreeMapItem) node2).peso).intValue()));	
			}
	
		});
		
		LinkedList<TreeMapItem> listaItens = new LinkedList<TreeMapItem>();
		
		for(int i = 0; i < itens.length; i++)
			listaItens.addFirst(itens[i]);
		
		LinkedList<TreeMapItem> linha = new LinkedList<TreeMapItem>();
		
		while(!listaItens.isEmpty()){
			
			TreeMapItem itemAtual = listaItens.getFirst();
			
			areaAtualItem = ((Integer) itemAtual.peso);
			
			areaAtualItem = (areaAtualItem / this.somatorioAreaItens) * this.area;
							
			double aspectRationSemItem = 0;
			
			if(somatorioAreaDosItens == 0)
				aspectRationSemItem = 0;
			else 
				aspectRationSemItem = (this.tamanhoLado * this.tamanhoLado) / somatorioAreaDosItens;
			
			somatorioAreaDosItens += areaAtualItem;
			
			double aspectRationItemAtual = 0;

			double tamanhoDoMaiorLadoItemAtual = (areaAtualItem / somatorioAreaDosItens) * this.tamanhoLado;
			
			double tamanhoDoMenorLadoItemAtual = areaAtualItem / tamanhoDoMaiorLadoItemAtual;
			
			if(tamanhoDoMaiorLadoItemAtual < tamanhoDoMenorLadoItemAtual){
				double l = tamanhoDoMaiorLadoItemAtual;
				tamanhoDoMaiorLadoItemAtual = tamanhoDoMenorLadoItemAtual;
				tamanhoDoMenorLadoItemAtual = l;
			}
			
			if(tamanhoDoMenorLadoItemAtual != 0)
				aspectRationItemAtual = tamanhoDoMaiorLadoItemAtual / tamanhoDoMenorLadoItemAtual;


			if(aspectRationSemItem > aspectRationItemAtual || aspectRationSemItem == 0){
				listaItens.removeFirst();
				linha.addFirst(itemAtual);
				
			} else {
				somatorioAreaDosItens -= areaAtualItem;
				
				desenharLinha(linha, somatorioAreaDosItens);
				
				somatorioAreaDosItens = 0;
										
			}
			
		}
		
		desenharLinha(linha, somatorioAreaDosItens);
		
		for (TreeMapItem item : itens) {
			try {
				
				Node[] list = (Node[]) item.node.getProperty(TreeMap.propertyRelation);
				if (list != null) {
					TreeMapItem[] newItens = new TreeMapItem[list.length];
					for(int i = 0; i < list.length; i++)
						newItens[i] = (TreeMapItem) list[i].getProperty(Properties.TREEMAP_ITEM);
					layout(newItens, item.getBounds());
				}

			} catch (ClassCastException e) {}		
		}
		
	}//layout
	
	private void desenharLinha(LinkedList<TreeMapItem> linha, double somatorioAreaDosItens){
		
		int itemAtualX = 0;
		int itemAtualY = 0;
		
		int larguraItemAtual = 0;
		int alturaItemAtual = 0;
		
		int itemAtualOffsetX = this.xInicialLinhaAtual;
		int itemAtualOffsetY = this.yInicialLinhaAtual;

		int tamanhoLadoEixoItemAtual = 0;
		int tamanhoLadoEixoOpostoItemAtual = Math.round(0.5f + (float)((somatorioAreaDosItens / this.tamanhoLado)));
				
		double proporcao;
		
		while(!linha.isEmpty()){
			
			
			TreeMapItem itemAtual = linha.removeLast();
						
			double areaAtualItem = ((Integer) itemAtual.peso);
			
			proporcao = (areaAtualItem / this.somatorioAreaItens);
			
			areaAtualItem = proporcao * this.area;

			tamanhoLadoEixoItemAtual = Math.round(0.5f + (float)((areaAtualItem / somatorioAreaDosItens) * this.tamanhoLado));

			itemAtualX = itemAtualOffsetX;
			
			itemAtualY = itemAtualOffsetY;
			
			if(this.eixoVertical){
			
				larguraItemAtual = tamanhoLadoEixoOpostoItemAtual;
				alturaItemAtual = tamanhoLadoEixoItemAtual;
				
				itemAtualOffsetY += alturaItemAtual;
				
			} else {

				larguraItemAtual = tamanhoLadoEixoItemAtual;
				alturaItemAtual = tamanhoLadoEixoOpostoItemAtual;
				
				itemAtualOffsetX += larguraItemAtual;
			
			}
			
			if(itemAtualX + larguraItemAtual > this.x + this.larguraTotal)
				larguraItemAtual = (this.x + this.larguraTotal) - itemAtualX;
			
			if(itemAtualY + alturaItemAtual > this.y + this.alturaTotal)
				alturaItemAtual = (this.y + this.alturaTotal) - itemAtualY;
			
			itemAtual.setLocation(new Point(itemAtualX, itemAtualY));
			itemAtual.setSize(larguraItemAtual, alturaItemAtual);
	
		}//while
		
		if(this.eixoVertical){
			this.xInicialLinhaAtual += tamanhoLadoEixoOpostoItemAtual;
			this.largura = this.larguraTotal - (this.xInicialLinhaAtual - this.x);
			this.tamanhoLado = this.largura;
		} else {
			this.yInicialLinhaAtual += tamanhoLadoEixoOpostoItemAtual;
			this.altura = this.alturaTotal - (this.yInicialLinhaAtual - this.y);
			this.tamanhoLado = this.altura;
		}
		
		this.eixoVertical = !this.eixoVertical;
		
	}//desenharLinha
	
}//class
