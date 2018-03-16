/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.tb;

import entidade.AmbConsulta;
import entidade.GrlPessoa;
import entidade.TbTriagem;
import java.util.List;

/**
 *
 * @author adelino
 */
public class MetodosGerais
{
    
    public static boolean isListEmpty(List list)
    {
        return (list == null || list.isEmpty());
    }

    public static boolean existeRegisto(Object object)
    {
        return object != null;
    }
    
    public static String concatenarNome(GrlPessoa p)
    {
        return p.getNome() + " " + p.getNomeDoMeio() + " " + p.getSobreNome();
    }
    
    /*public List<Cliente> porNomeSemelhante(String nome) {
		return manager.createQuery("from Cliente where nome like :nome", Cliente.class)
				.setParameter("nome", "%" + nome + "%")
				.getResultList();
	}*/
    
}
