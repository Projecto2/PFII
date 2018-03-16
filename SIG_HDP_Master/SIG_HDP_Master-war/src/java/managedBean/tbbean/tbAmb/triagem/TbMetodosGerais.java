/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.tbAmb.triagem;

import entidade.AmbConsulta;
import entidade.GrlPessoa;
import entidade.RhFuncionario;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import util.GeradorCodigo;
import util.tb.MetodosGerais;

/**
 *
 * @author adelino
 */
@Named
@RequestScoped
public class TbMetodosGerais implements Serializable
{
    public static TbTriagemBean getInstanciaBean()
    {
        return (TbTriagemBean) GeradorCodigo.getInstanciaBean("tbMetodosGerais");
    }
    /**
     * Creates a new instance of tbMetodosGerais
     */
    public TbMetodosGerais()
    {
    }
    
    public String getPacienteHistorico(AmbConsulta c)
    {
        GrlPessoa p = c.getFkIdConsultorioAtendimento().getFkIdTriagem().
            getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().
            getFkIdPaciente().getFkIdPessoa();
        return MetodosGerais.concatenarNome(p);
    }
    
    public String getEnfermeiro(RhFuncionario f)
    {
        return MetodosGerais.concatenarNome(f.getFkIdPessoa());
    }
    
}
