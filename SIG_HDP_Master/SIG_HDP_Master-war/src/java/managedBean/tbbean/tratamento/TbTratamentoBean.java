/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.tratamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import util.GeradorCodigo;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbTratamentoBean implements Serializable
{

    /**
     * Creates a new instance of TbTratamentoBean
     */
    public TbTratamentoBean()
    {
    }

    public static TbTratamentoBean getInstanciaTratamentoBean()
    {
        return (TbTratamentoBean) GeradorCodigo.getInstanciaBean("tbTratamentoBean");
    }

    /**
     * Metodo usado para auto completar um texto
     */
    public List<String> completeText(String query)
    {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            results.add(query + i);
        }

        return results;
    }
    
    
}
