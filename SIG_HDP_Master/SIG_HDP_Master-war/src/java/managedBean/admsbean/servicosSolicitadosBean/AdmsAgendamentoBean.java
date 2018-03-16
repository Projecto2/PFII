/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.servicosSolicitadosBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.Mensagem;
import util.RelatorioJasper;
import util.adms.ConstantesAdms;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsAgendamentoBean extends AdmsAgendamentoGenericoBean implements Serializable
{
    
    
    private ConexaoPostgresSQL conexaoPostgresSQL = new ConexaoPostgresSQL();
    /**
     * Creates a new instance of AdmsSolicitacoesBean
     */
    public AdmsAgendamentoBean()
    {
    }
    
    public static AdmsAgendamentoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsAgendamentoBean admsServicoSolicitadoBean = 
            (AdmsAgendamentoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsAgendamentoBean");
        
        return admsServicoSolicitadoBean;
    }
    
   

    public void pesquisar()
    {
        if(agendamentos == null)
        {
            getAgendamentos();
            getAgendamentosMedicos();
        }
        else{
            agendamentos.clear();
            agendamentosMedicos.clear();
        }
        
        if (listaServicosSolicitados == null)
        {
            listaServicosSolicitados = new ArrayList<>();
        }
        if (validarDadosAPesquisar())
        {
            //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
            if(estadoAgendamento.getPkIdEstadoAgendamento() != null && estadoAgendamento.getPkIdEstadoAgendamento() != ConstantesAdms.NAO_APARECEU_COD)
                estadoAgendamento = admsEstadoAgendamentoFacade.find(estadoAgendamento.getPkIdEstadoAgendamento());
            //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
            
            
            listaServicosSolicitados = admsServicoSolicitadoFacade.findServicoSolicitado
           (servicoSolicitadoPesquisar, estadoAgendamento, funcionario, dataInicial, dataFinal, dataAgendadaInicial, dataAgendadaFinal, null, 100);
            if(listaServicosSolicitados == null || listaServicosSolicitados.isEmpty())
                Mensagem.warnMsg("Nenhum Serviço Solicitado Encontrado");
            else Mensagem.sucessoMsg("Tabela Atualizada. "+listaServicosSolicitados.size()+" registos!");
        }
    }
    
    public void exportPDF (ActionEvent evt)
    {
        HashMap<String, Object> parametrosMap = new HashMap<>();
        RelatorioJasper.exportPDF("adms/listaServicosSolicitados.jasper", parametrosMap, listaServicosSolicitados);
        
        conexaoPostgresSQL = new ConexaoPostgresSQL();
        parametrosMap.put("REPORT_CONNECTION", conexaoPostgresSQL.getConnection());
    }

}
