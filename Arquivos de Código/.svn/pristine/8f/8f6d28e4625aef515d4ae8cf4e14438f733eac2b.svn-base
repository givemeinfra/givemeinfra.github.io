package givemeviews.metrics;

import java.util.ArrayList;

import givemeviews.model.Arquivo;

public class Percent {
	
	public Percent()
	{
		
	}
	
	public float calculateEstimatedPercentage(ArrayList<Arquivo> list)
    {
        Arquivo objArquivo;
        ArrayList<String> listModulos;
        ArrayList<String> listModulosRDM;
        float contItens = 0;
        float contItensIguais = 0;
        for (int i = 0; i < list.size(); i++)
        {
            objArquivo = (Arquivo)list.get(i);
            listModulos = objArquivo.getListaModulos();
            listModulosRDM = objArquivo.getListaModuloRDM();
            for (int j = 0; j < listModulos.size(); j++ )
            {
                contItens++;
                if (listModulos.get(j).equals(listModulosRDM.get(j)))
                {
                    contItensIguais++;
                }
            }
        }
        // calcula porcentagem:
        if(list.size() == 0)
        {
            return -1;
        }
        else if (contItensIguais == 0)
        {
            return 0;
        }
        else
        {
            float porcentagem = (contItensIguais / contItens) * 100;
            return porcentagem;
        }            
    }
	
	public String formatInputPercentage(float percentage)
    {
        if (percentage == 0)
        {
            return "0%";
        }
        else if (percentage == -1)
        {
        	return "0 modules";            
        }
        else
        {
        	int x = (int)percentage;
            return Float.toString(x) + "%";
        }
    }

    public float formatOutputPercentage(String percentage)
    {
        float saida = 0;
        if (percentage.equals("0%"))
        {
            return 0;
        }
        else if (percentage.equals("0 módulos") || percentage.equals("0 modules"))
        {
            return -1;
        }
        else
        {
        	
            String[] valores = percentage.split("%");
            int x = Integer.parseInt(valores[0]);
            saida = (float)x;
            return saida;
        }
    }
    
    public float getMaintenancePercentByComponent(float totalMaintenances, float setMaintenances)
    {
        float porcentagem = setMaintenances / totalMaintenances;
        porcentagem = porcentagem * 100;
        return porcentagem;
    }  

}
