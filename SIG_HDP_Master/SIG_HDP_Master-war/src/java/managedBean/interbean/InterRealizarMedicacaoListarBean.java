/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterRealizarMedicacao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.segbean.SegLoginBean;
import sessao.InterRealizarMedicacaoFacade;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRealizarMedicacaoListarBean implements Serializable
{
    @EJB
    private InterRealizarMedicacaoFacade interRealizarMedicacaoFacade;
               
    private String nomeEnfermeiro, nomeDoMeioEnfermeiro, sobreNomeEnfermeiro;
    
    private Date dataRegisto;
    
    private List<InterRealizarMedicacao> listaMedicacaoRealizada;
    
    private final Calendar dataCorrente = Calendar.getInstance();
    
    /**
     * Creates a new instance of InterRealizarMedicacaoListarBean
     */
    public InterRealizarMedicacaoListarBean()
    {
    }
    
    public static InterRealizarMedicacaoListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterRealizarMedicacaoListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interRealizarMedicacaoListarBean");
    }
    
    public String descricaoOpcaoMedicacao(int fk_id_medicacao_produto, Long fk_id_registo, int hora, Date data)
    {
        String tipoServico;

        tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
        
        listaMedicacaoRealizada = interRealizarMedicacaoFacade.pesquisarRegisto(tipoServico, null, null, null, data, fk_id_registo, hora, fk_id_medicacao_produto);
        
        if (listaMedicacaoRealizada.isEmpty())
                return "MF";
        return listaMedicacaoRealizada.get(0).getFkIdOpcaoMedicacao().getCodigo();
    }
}
