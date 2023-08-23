package implementacoes.radial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;  
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class GeraRadialXML 
{	
	private String arquivoXML;
	//private String arquivoXMLRecompilado = "C:/Analisador/RadialRastreabilidadeComponenteRecompilado.xml";
	//private String moduloOrigemXML;
	//private String componenteOrigemXMl;
	private String moduloClicado;
	private String componenteClicado;
	private ArrayList<SetModules> listaModulosEComponentes;
	public Random gerador = new Random();
	public ArrayList<String> listaArestas;
	public ArrayList<SetModules> listaTempNos;
	public ArrayList<SetModules> listaDefinitivaNos;
	private float[][] matrizFrequencias;
	
	
	public void createRadialViewXML(String pathToSave, String module, String component, ArrayList<String> nodesList, ArrayList<String> edgeList)
	{
		try
		{
			PrintWriter writer = null;
			writer = new PrintWriter(new BufferedWriter(new FileWriter(pathToSave)));
			
			writer.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "ISO8859-1" + '"' + "?>");
            writer.println("<graphml xmlns=" + '"' + "http://graphml.graphdrawing.org/xmlns" + '"' + ">");
            writer.println("<graph>");

            writer.println("<key id=" + '"' + "name" + '"' + " for=" + '"' + "node" + '"' + " attr.name=" + '"' + "name" + '"' + " attr.type=" + '"' + "string" + '"' + "/>");
            writer.println("<key id=" + '"' + "gender" + '"' + " for=" + '"' + "node" + '"' + " attr.name=" + '"' + "gender" + '"' + " attr.type=" + '"' + "string" + '"' + "/>");
            
            // Record Nodes
            writer.println("<node id=" + '"' + -1 + '"' + ">");
    		writer.println("<data key=" + '"' + "name" + '"' + ">" + module + "/"  + component + "</data>");
    		writer.println("<data key=" + '"' + "gender" + '"' + ">A</data>");
    		writer.println("</node>");
    		
            for(int i = 0; i < nodesList.size(); i++)
            {
            	writer.println(nodesList.get(i));
            }            
            
            // Record Edges
            for(int i = 0; i < edgeList.size(); i++)
            {
            	writer.println(edgeList.get(i));
            }
            
            writer.println();
            writer.println("</graph>");
            writer.println("</graphml>");
            writer.close();
		}
		catch(Exception ex)
		{
			System.out.println("_________________________");
       	 	ex.printStackTrace();       	 	
		}
	}
	
	public int getRandom()
	{
		int i = gerador.nextInt();
		return i;
	}	
	
	public void recriarXML(String informacao) 
	{
		// Caminho GMMRepository
		String path = "C:/GiveMeRepository/Brain";
		// Caminhos dos logs
		String pathLogMin = path + "/logMin.txt";
		String pathLog = path + "/log.txt";
		
		// Obtenção do modulo e componente clicados na visualização
		obterModuloComponenteClicado(informacao);
		
		// obtem lista dos modulos e componentes que são indices da matriz
		listaModulosEComponentes = obterListaModulosEComponentesDoLog(pathLogMin);
		
		// obtem a matriz de dados.
		obterMatrizFrequencias(listaModulosEComponentes.size(), pathLog);	
		
		// Seta caminho do RadialXML
		arquivoXML = path + "/radialview.xml";
		
		// codigo teste de geração do novo XML.
	    criarXMLRadialGraphRecompilado(moduloClicado, componenteClicado, listaModulosEComponentes);
	}
	
	public ArrayList<OrdenaPorcentagem> recuperarNodesDoXML()
	{
	    NodeXMLReader objReader = new NodeXMLReader(arquivoXML);
	    ArrayList<OrdenaPorcentagem> listNosNoXML = new ArrayList<OrdenaPorcentagem>();
	    try {
			Vector<node> listNodes = objReader.lerNodes();
			for( int i=0; i< listNodes.size(); i++ ) 
			{
				OrdenaPorcentagem objNoAtual = new OrdenaPorcentagem();
	            node no = (node) listNodes.get( i ); 
	            
	            String [] noInfos = no.nome.split("/");
	            if(noInfos[1].contains("%"))
	            {
	            	String [] noInfos2 = noInfos[1].split(" ");	            	
	            	objNoAtual.setComponenteAssociado(noInfos2[0]);	
	            	noInfos2[2] = noInfos2[2].replace(",",".");
	            	objNoAtual.setPorcentagemAssociada(Float.parseFloat(noInfos2[2]));
	            }
	            else
	            {
	            	objNoAtual.setComponenteAssociado(noInfos[1]);
	            }
	            
	            objNoAtual.setId(no.id);
	            objNoAtual.setNome(noInfos[0]);	
	            objNoAtual.setRandomID(getRandom());
	            listNosNoXML.add(objNoAtual);
	        }		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return listNosNoXML;	    
	}	
	
	public ArrayList<OrdenaPorcentagem> recuperarNodesDoXMLPath(String path)
	{
	    NodeXMLReader objReader = new NodeXMLReader(path);
	    ArrayList<OrdenaPorcentagem> listNosNoXML = new ArrayList<OrdenaPorcentagem>();
	    try {
			Vector<node> listNodes = objReader.lerNodes();
			for( int i=0; i< listNodes.size(); i++ ) 
			{
				OrdenaPorcentagem objNoAtual = new OrdenaPorcentagem();
	            node no = (node) listNodes.get( i ); 
	            
	            String [] noInfos = no.nome.split("/");
	            if(noInfos[1].contains("%"))
	            {
	            	String [] noInfos2 = noInfos[1].split(" ");	            	
	            	objNoAtual.setComponenteAssociado(noInfos2[0]);	
	            	noInfos2[2] = noInfos2[2].replace(",",".");
	            	objNoAtual.setPorcentagemAssociada(Float.parseFloat(noInfos2[2]));
	            }
	            else
	            {
	            	objNoAtual.setComponenteAssociado(noInfos[1]);
	            }
	            
	            objNoAtual.setId(no.id);
	            objNoAtual.setNome(noInfos[0]);	
	            objNoAtual.setRandomID(getRandom());
	            listNosNoXML.add(objNoAtual);
	        }		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return listNosNoXML;	    
	}	
	
	private void obterModuloComponenteClicado(String itemClicado)
	{
		String [] part1 = itemClicado.split("/");
		String modulo = part1[0];
		//modulo = modulo.replaceAll(" ", "");
		String [] part2 = part1[1].split(" ");
		String componente = part2[0];
		
		moduloClicado = modulo;
		componenteClicado = componente;		
	}
	
	public ArrayList<SetModules> obterListaModulosEComponentesDoLog(String caminho)
	{
		ArrayList<SetModules> lista = new ArrayList<>();
		
		// abertura do arquivo
		BufferedReader myBuffer = null;
		try {
			myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// lê e imprime todas as linhas do arquivo
		String linha = null;
		try {
			linha = myBuffer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (linha != null) 
		{
			try {					
				String [] linhaLida = linha.split(":::");
				SetModules objMod = new SetModules(linhaLida[0], linhaLida[1]);
				lista.add(objMod);
				linha = myBuffer.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			myBuffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return lista;
	 }	
	
	 private void obterMatrizFrequencias(int larguraMatriz, String caminho)
	 {
		    matrizFrequencias = new float[larguraMatriz][larguraMatriz];
		    ArrayList<String> listaLinhaLida = new ArrayList<>();
		    //ArrayList<String> listaCelulaLida = new ArrayList<String>();
			// abertura do arquivo
			BufferedReader myBuffer = null;
			try {
				myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// lê e imprime todas as linhas do arquivo
			String linha = null;
			try {
				linha = myBuffer.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (linha != null) 
			{
				try{
				//System.out.println(linha);
				listaLinhaLida.add(linha);
				linha = myBuffer.readLine();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			int largura = 0;
			int altura = 0;
			for(int i = 0; i < listaLinhaLida.size(); i++)
			{
				String posicao = listaLinhaLida.get(i);
				String[] dados = posicao.split("::"); // cada posição aqui tem um objeto
						
				for(int j = 1; j < dados.length; j++)
				{
					dados[j] = dados[j].replace(",",".");
					dados[j] = dados[j]+ "f";
					matrizFrequencias[altura][largura] = Float.parseFloat(dados[j]);
					largura++;
					if(largura == larguraMatriz)
					{
						altura++;
						largura = 0;
					}
													
				}
			}
			try {
				myBuffer.close();
				//System.out.println(obterIndice(listaModulosEComponentes, componenteClicado, moduloClicado));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 	 
	private int obterIdRandomNoClicado(String moduloCli, String componenteCli, ArrayList<OrdenaPorcentagem> listaNosNoXML) 
	{
		int id = 0;
		for(int  i  = 0; i < listaNosNoXML.size(); i++)
		{
			if(listaNosNoXML.get(i).getNome().equals(moduloCli) && listaNosNoXML.get(i).getComponenteAssociado().equals(componenteCli))
			{
				id = listaNosNoXML.get(i).getRandomID();
			}
		}
		return id;
	}
	
	private OrdenaPorcentagem retornarNoClicado(String moduloCli, String componenteCli, ArrayList<OrdenaPorcentagem> listaNosNoXML) 
	{
		OrdenaPorcentagem objNo = new OrdenaPorcentagem();
		for(int  i  = 0; i < listaNosNoXML.size(); i++)
		{
			if(listaNosNoXML.get(i).getNome().equals(moduloCli) && listaNosNoXML.get(i).getComponenteAssociado().equals(componenteCli))
			{
				objNo = (OrdenaPorcentagem)listaNosNoXML.get(i);
			}
		}
		return objNo;
	}

	private ArrayList<String> montarTagsEdgesXML(ArrayList<OrdenaPorcentagem> listNosOrigem) 
	{
	    NodeXMLReader objReader = new NodeXMLReader(arquivoXML);
		Vector<edge> listEdges;
		ArrayList<String> listTagsEdges = new ArrayList<String>();
		try {
			listEdges = objReader.lerEdges();
			for( int i=0; i< listEdges.size(); i++ ) 
			{
	            edge aresta = (edge)listEdges.get(i);	
	            for(int l = 0; l < listNosOrigem.size(); l++)
	            {
	                 OrdenaPorcentagem objM = listNosOrigem.get(l);
	                 if(objM.getId() == aresta.source)
	                 {
	                	 aresta.source = objM.getRandomID();
	                 }
	                 if(objM.getId() == aresta.target)
	                 {
	                	 aresta.target = objM.getRandomID();
	                 }
	            }
	            // constroi uma tag e insere na lista
	            listTagsEdges.add("<edge source=" + '"' + aresta.source + '"' + " target=" + '"' + aresta.target + '"' + "></edge>");
                
	        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTagsEdges;
	}

	private ArrayList<String> montarTagsNodes(ArrayList<OrdenaPorcentagem> listaNosNoXML) 
	{
		ArrayList<String> listaTagsNos = new ArrayList<String>();
		for(int i = 0; i < listaNosNoXML.size(); i++)
		{
			String no = "";
			no +=("<node id=" + '"' + listaNosNoXML.get(i).getRandomID() + '"' + ">") + "\n"; 
			if(listaNosNoXML.get(i).getPorcentagemAssociada() == 0)
			{
				no +=("<data key=" + '"' + "name" + '"' + ">" + listaNosNoXML.get(i).getNome() + "/" + listaNosNoXML.get(i).getComponenteAssociado() + "</data>")+ "\n";
			}
			else
			{
			    no +=("<data key=" + '"' + "name" + '"' + ">" + listaNosNoXML.get(i).getNome() + "/" + listaNosNoXML.get(i).getComponenteAssociado() + " ( " + listaNosNoXML.get(i).getPorcentagemAssociada() + " %)" + "</data>")+ "\n";
			}
			no +=("<data key=" + '"' + "gender" + '"' + ">A</data>")+ "\n";
			no +=("</node>");
			listaTagsNos.add(no);
		}
		return listaTagsNos;
	}

	public int obterIndice(ArrayList<SetModules> listComps, String componenteV, String moduloV)
    {
         for (int i = 0; i < listComps.size(); i++)
         {
             SetModules objModulo = (SetModules)listComps.get(i);
             String mod = objModulo.getNome();
             String comp = objModulo.getComponenteAssociado();
             if(mod.equals(moduloV) && comp.equals(componenteV))
             {
            	 return i;         	 
             }
         }
         return -1;
     }
	
	private ArrayList<OrdenaPorcentagem> retornaListaFilhos(ArrayList<SetModules> plistaModulosEComps, OrdenaPorcentagem noPai)
	{
		//ArrayList<OrdenaPorcentagem> listaAuxFilhos = new ArrayList<OrdenaPorcentagem>();
		ArrayList<OrdenaPorcentagem> listaFinalFilhos = new ArrayList<OrdenaPorcentagem>();
		// cria nó principal
   	    int indice = obterIndice(plistaModulosEComps, noPai.getComponenteAssociado(), noPai.getNome());        
		
		for(int i = 0; i < plistaModulosEComps.size(); i++)
        {
       	 if(i == indice)
   		 {
       		 for(int j = 0; j < plistaModulosEComps.size(); j++)
           	 {
           		 if(matrizFrequencias[i][j] > 0 && i!= j)
           		 {
           			 SetModules objModAux = (SetModules)plistaModulosEComps.get(j);
                     OrdenaPorcentagem objMod = new OrdenaPorcentagem(objModAux.getId(), objModAux.getRandomID(), objModAux.getNome(), objModAux.getOcorrencia(), objModAux.getComponenteAssociado(), objModAux.getPorcentagemAssociada());
           			 objMod.setPorcentagemAssociada(matrizFrequencias[i][j]);
           			 objMod.setId(obterIndice(plistaModulosEComps, objMod.getComponenteAssociado(), objMod.getNome()));
           			 objMod.setRandomID(getRandom());
           			 listaFinalFilhos.add(objMod);
           	     }           		 
           	 }
       		 break;
       	   }        	   
        }
		return listaFinalFilhos;	
		
	}
	
	private ArrayList<String> criaTagsEdgesEntreNos(ArrayList<OrdenaPorcentagem> listaNos, int idSource)
	{
		ArrayList<String> listTagsEdgeFilhos = new ArrayList<String>();
		for(int i = 0; i < listaNos.size(); i ++)
		{
			listTagsEdgeFilhos.add("<edge source=" + '"' + idSource + '"' + " target=" + '"' + listaNos.get(i).getRandomID() + '"' + "></edge>");
		}
		return listTagsEdgeFilhos;
	}
	
	public void criarXMLRadialGraphRecompilado(String moduloCli, String componenteCli, ArrayList<SetModules> listaModulosEComps) // cria grafo da relação de um componente com os demais existentes.
    {
		// recupero os nós do xml existente.
		ArrayList<OrdenaPorcentagem> listaNosNoXML = recuperarNodesDoXML();
		// monta lista tags Nós.
		ArrayList<String> listaTagsNodesExistentes = montarTagsNodes(listaNosNoXML);		
		// monta lista tags Arestas
		ArrayList<String> listaTagsEdgesExistentes = montarTagsEdgesXML(listaNosNoXML);		
		// recupera id Random do no clicado.
		int idClicado = obterIdRandomNoClicado(moduloCli, componenteCli, listaNosNoXML);		
		// recupera no clicado.
		OrdenaPorcentagem objNoClicado = retornarNoClicado(moduloCli, componenteCli, listaNosNoXML);		
		// retorna lista filhos do nó clicado.
		ArrayList<OrdenaPorcentagem> listaFilhosNoClicado = retornaListaFilhos(listaModulosEComps, objNoClicado);
		//Ordena por maior porcentagem
		Collections.sort(listaFilhosNoClicado);
		// limita a quantidade de filhos que um nó pode ter em 10 nós. (Número bom para uma boa visualização)
		ArrayList<OrdenaPorcentagem> listaFilhosNoClicadoTamLimitado = limitarNumeroItensArray(listaFilhosNoClicado, 10);
		// monta lista tags filhos do nó clicado.
		ArrayList<String> listaTagsNoClicado = montarTagsNodes(listaFilhosNoClicadoTamLimitado); 		
		// monta lista tags edges entre os filhos.
		ArrayList<String> listaTagsEdgesNoClicado = criaTagsEdgesEntreNos(listaFilhosNoClicadoTamLimitado, idClicado);		
				
		// Início - criar XML Recompilado
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(arquivoXML)));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 				 
        try
        {
        	 writer.println("<?xml version=" + '"' + "1.0" + '"' + " encoding=" + '"' + "ISO8859-1" + '"' + "?>");
             writer.println("<graphml xmlns=" + '"' + "http://graphml.graphdrawing.org/xmlns" + '"' + ">");
             writer.println("graph");

             writer.println("<key id=" + '"' + "name" + '"' + " for=" + '"' + "node" + '"' + " attr.name=" + '"' + "name" + '"' + " attr.type=" + '"' + "string" + '"' + "/>");
             writer.println("<key id=" + '"' + "gender" + '"' + " for=" + '"' + "node" + '"' + " attr.name=" + '"' + "gender" + '"' + " attr.type=" + '"' + "string" + '"' + "/>");
             
             //____________________________________________________________
             // grava nos
             for(int i = 0; i < listaTagsNodesExistentes.size(); i++)
             {
            	 writer.println(listaTagsNodesExistentes.get(i));
             }
             for(int i = 0; i < listaTagsNoClicado.size(); i++)
             {
            	 writer.println(listaTagsNoClicado.get(i));
             }             
             // grava arestas
             for(int i = 0; i < listaTagsEdgesExistentes.size(); i++)
             {
            	 writer.println(listaTagsEdgesExistentes.get(i));
             }
             for(int i = 0; i < listaTagsEdgesNoClicado.size(); i++)
             {
            	 writer.println(listaTagsEdgesNoClicado.get(i));
             }
             //__________________________________________________________
             
             writer.println();
             writer.println("</graphml>");
             writer.close();
             JFrame frame = prefuse.demosRadial.RadialGraphView.obterFrame();
             frame.setContentPane(prefuse.demosRadial.RadialGraphView.demo(arquivoXML, "name"));             
             //frame.setIconImage(Toolkit.getDefaultToolkit().getImage(RadialGraphView.class.getResource("/radial/radialview.ico")));
             frame.pack();             
             frame.setVisible(true);
             frame.setLocationRelativeTo(null);
         }
         catch (Exception ex)
         {
        	 JOptionPane.showMessageDialog(null, ex.getMessage());
         }      		
		 // Final - criar XML Recompilado			
    }

	private ArrayList<OrdenaPorcentagem> limitarNumeroItensArray(ArrayList<OrdenaPorcentagem> listaFilhosNoClicado, int numLimite) 
	{
		ArrayList<OrdenaPorcentagem> listaReduzida = new ArrayList<OrdenaPorcentagem>();
		int limite = 0;
		if(listaFilhosNoClicado.size() > numLimite)
			limite = numLimite;
		else
			limite = listaFilhosNoClicado.size();
		
		for(int i = 0; i < limite; i++)
		{
			OrdenaPorcentagem objAux = listaFilhosNoClicado.get(i);
			listaReduzida.add(objAux);
		}
		Collections.sort(listaReduzida);
		return listaReduzida;
	}  
	
}
