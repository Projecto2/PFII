/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean.encargo;

//import entidade.AdmsPaciente;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class FinEncargoDevidoPacienteBean extends FinEncargoDevidoGenericoBean
{

    /**
     * Creates a new instance of FinEncargoPacienteBean
     */
    public FinEncargoDevidoPacienteBean()
    {
    }
    
    public static FinEncargoDevidoPacienteBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FinEncargoDevidoPacienteBean finEncargoDevidoPacienteBean = 
            (FinEncargoDevidoPacienteBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "finEncargoDevidoPacienteBean");
        
        return finEncargoDevidoPacienteBean;
    }



//
//
//    public void pesquisar()
//    {
//        if(agendamentos == null)
//        {
//            getAgendamentos();
//            getAgendamentosMedicos();
//        }
//        agendamentos.clear();
//        agendamentosMedicos.clear();
//        
//        if (listaServicosSolicitados == null)
//        {
//            listaServicosSolicitados = new ArrayList<>();
//        }
//        if (validarDadosAPesquisar())
//        {
//            //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
//            if(estadoAgendamento.getPkIdEstadoAgendamento() != null && estadoAgendamento.getPkIdEstadoAgendamento() != ConstantesAdms.NAO_APARECEU_COD)
//                estadoAgendamento = admsEstadoAgendamentoFacade.find(estadoAgendamento.getPkIdEstadoAgendamento());
//            //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
//            
//            listaServicosSolicitados = admsServicoSolicitadoFacade.findServicoSolicitado
//           (servicoSolicitadoPesquisar, estadoAgendamento, funcionario, dataInicial, dataFinal, dataAgendadaInicial, dataAgendadaFinal, paciente, 100);
//            if(listaServicosSolicitados == null || listaServicosSolicitados.isEmpty())
//                Mensagem.warnMsg("Nenhum Serviço Solicitado Encontrado");
//            else Mensagem.sucessoMsg("Tabela Atualizada. "+listaServicosSolicitados.size()+" registos!");
//        }
//    }
    
}
