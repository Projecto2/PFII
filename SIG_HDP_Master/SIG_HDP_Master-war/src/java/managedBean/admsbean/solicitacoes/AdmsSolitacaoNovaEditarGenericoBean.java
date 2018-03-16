/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.solicitacoes;

import entidade.AdmsCategoriaServico;
import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsGrupoServico;
import entidade.AdmsResponsavelPaciente;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.AdmsSubprocesso;
import entidade.AdmsTipoServico;
import entidade.AdmsTipoSolicitacaoServico;
import entidade.FinEncargoDevido;
import entidade.FinPrecoCategoriaServico;
import entidade.GrlAreaInterna;
import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import entidade.GrlGrauParentesco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import managedBean.admsbean.AdmsDefsGenericaSolicitacaoBean;
import sessao.AdmsCategoriaServicoFacade;
import sessao.AdmsClassificacaoServicoSolicitadoFacade;
import sessao.AdmsEstadoPagamentoFacade;
import sessao.AdmsServicoFacade;
import sessao.AdmsSubprocessoFacade;
import sessao.AdmsTipoPagamentoSubprocessoFacade;
import sessao.AdmsTipoSolicitacaoServicoFacade;
import sessao.FinPrecoCategoriaServicoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
//@SessionScoped
public abstract class AdmsSolitacaoNovaEditarGenericoBean extends AdmsDefsGenericaSolicitacaoBean implements Serializable
{
    @EJB
    protected AdmsTipoPagamentoSubprocessoFacade admsTipoPagamentoSubprocessoFacade;
    @EJB
    protected FinPrecoCategoriaServicoFacade finPrecoCategoriaServicoFacade;
    @EJB
    protected AdmsCategoriaServicoFacade admsCategoriaServicoFacade;
    
    @EJB
    protected AdmsTipoSolicitacaoServicoFacade admsTipoSolicitacaoServicoFacade;
    @EJB
    protected AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    @EJB
    protected AdmsClassificacaoServicoSolicitadoFacade admsClassificacaoServicoSolicitadoFacade;
    @EJB
    protected AdmsServicoFacade admsServicoFacade;
    
    
    
    protected List<AdmsCategoriaServico> listaPrecos;

    protected AdmsCategoriaServico preco;
    
    protected FinPrecoCategoriaServico valor;
    
    protected double valorTotalDoPreco = 0, fatorDeMultiplicacao = 1;


    public AdmsSolitacaoNovaEditarGenericoBean(){
        
    }
    
    
    public AdmsSolicitacao getSolicitacao()
    {
        if(solicitacao == null)
        {
            solicitacao = new AdmsSolicitacao();
            solicitacao.setFkIdCentro(new GrlCentroHospitalar());
            solicitacao.setAdmsServicoSolicitadoList(new ArrayList<AdmsServicoSolicitado>());
            solicitacao.setFkIdResponsavelPaciente(new AdmsResponsavelPaciente());
            solicitacao.getFkIdResponsavelPaciente().setNomeCompleto("");
            solicitacao.getFkIdResponsavelPaciente().setTelefone1("");
            solicitacao.getFkIdResponsavelPaciente().setFkIdGrauParentesco(new GrlGrauParentesco());
            solicitacao.setFkIdSubprocesso(new AdmsSubprocesso());
        }
        return solicitacao;
    }


    public void setSolicitacao(AdmsSolicitacao solicitacao)
    {
        this.solicitacao = solicitacao;
    }
    
    
    public AdmsServicoSolicitado getServicoSolicitado()
    {
        if(servicoSolicitado == null)
        {
            servicoSolicitado = criarInstanciaEAdicionar();
        }
        return servicoSolicitado;
    }


    public void setServicoSolicitado(AdmsServicoSolicitado servicoSolicitado)
    {
        this.servicoSolicitado = servicoSolicitado;
    }
    
    public AdmsServico getInstanciaServico()
    {
        AdmsServico servico = new AdmsServico();

        servico.setFkIdArea(new GrlAreaInterna());
        servico.setFkIdEspecialidade(new GrlEspecialidade());
        servico.setFkIdGrupoServico(new AdmsGrupoServico());
        servico.setFkIdTipoServico(new AdmsTipoServico());

        return servico;
    }
    
    
    public AdmsServicoSolicitado criarInstanciaEAdicionar()
    {
        AdmsServicoSolicitado servicoSolicitadoAdd = new AdmsServicoSolicitado();
        servicoSolicitadoAdd.setFkIdClassificacaoServicoSolicitado(new AdmsClassificacaoServicoSolicitado());
        servicoSolicitadoAdd.setFkIdTipoSolicitacao(new AdmsTipoSolicitacaoServico());
        servicoSolicitadoAdd.setFkIdEstadoPagamento(new AdmsEstadoPagamento());
        servicoSolicitadoAdd.setFkIdServico(new AdmsServico());
        servicoSolicitadoAdd.setFkIdSolicitacao(new AdmsSolicitacao());
        servicoSolicitadoAdd.setFkIdSolicitacao(solicitacao);
        servicoSolicitadoAdd.setFkIdPrecoCategoriaServico(new FinPrecoCategoriaServico());
        
        return servicoSolicitadoAdd;
    }
        
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public FinPrecoCategoriaServico getValorPreco()
    {
        try{
            if(!(preco.getPkIdCategoriaServico()== null))
            { 
                preco = admsCategoriaServicoFacade.find(preco.getPkIdCategoriaServico());
                servicoSolicitado.setFkIdTipoSolicitacao(admsTipoSolicitacaoServicoFacade.find(servicoSolicitado.getFkIdTipoSolicitacao().getPkIdTipoSolicitacao()));
                if(preco.getTipoUnico())
                {
                    valor = finPrecoCategoriaServicoFacade.findValorDoPrecoComTipo(preco, "Único");
                }
                else
                {
                    if(servicoSolicitado.getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico().equalsIgnoreCase("Primeira Vez"))
                    {
                        valor = finPrecoCategoriaServicoFacade.findValorDoPrecoComTipo(preco, "Primeira Vez");
                    }
                    else if(servicoSolicitado.getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico().equalsIgnoreCase("Retorno"))
                    {
                        valor = finPrecoCategoriaServicoFacade.findValorDoPrecoComTipo(preco, "Retorno");
                    }
                }
                return valor;
            }
        }catch(Exception ex)
        {
        }
        valor = null;
        return valor;
    }
    
   
     /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
//    */
    public List<AdmsCategoriaServico> getListaPrecos()
    {
        if(listaPrecos == null)
        {
            listaPrecos = new ArrayList<>();
        }
//Codigo usado para superar o erro do value not valid que aparece caso usar a primeira solucao
        if(servicoPesquisa.getPkIdServico() != null)
        {
            listaPrecos = admsCategoriaServicoFacade.findCategoriasServicoAtivos(this.servicoPesquisa);
            if(!listaPrecos.isEmpty())
                preco = listaPrecos.get(0);
            else{
                listaPrecos = null;
                preco = null;
            }
        }
        return listaPrecos;
    }
    
        public void setListaPrecos(List<AdmsCategoriaServico> listaPrecos)
    {
        this.listaPrecos = listaPrecos;
    }



    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
        
    public List<AdmsServico> getServicoLista()
    {
        if(servicoLista == null)
        {
            servicoLista = new ArrayList<>();
        }
        return servicoLista;
    }
        
    public void pesquisarServicos()
    {
        if(listaPrecos == null) getListaPrecos();
        if(servicoLista == null) getServicoLista();
        carregarServicos();
        if(servicoLista.isEmpty())
        {
            limparDeServicoParaBaixo();
        }
        else definirPreco(listaPrecos);
        
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
   public void changeServico(ValueChangeEvent e)
   {
       servicoPesquisa.setPkIdServico((Integer)e.getNewValue());
       carregarPrecosServico(servicoPesquisa);
        definirPreco(listaPrecos);
   }
   
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
   public void definirPreco(List<AdmsCategoriaServico> listaPrecos)
   {
        if(!listaPrecos.isEmpty())
        {
            preco = listaPrecos.get(0);
        }
        else{
            listaPrecos = null;
            preco = null;
        }
   }

        /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public AdmsServico getServicoPesquisa()
    {
        if(servicoPesquisa == null)
        {
            servicoPesquisa = getInstanciaServico();
        }
        return servicoPesquisa;
    }


    public void setServicoPesquisa(AdmsServico serVicoPesquisa)
    {
        this.servicoPesquisa = serVicoPesquisa;
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public AdmsCategoriaServico getPreco()
    {
        if(preco == null)
        {
            preco = new AdmsCategoriaServico();
        }
        return preco;
    }


    public void setPreco(AdmsCategoriaServico preco)
    {
        this.preco = preco;
    }     
     
         /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
     public void limparDeServicoParaBaixo()
     {
         if(servicoLista != null)
            servicoLista.clear();
         if(servicoPesquisa == null)
         {
             getServicoPesquisa();
         }
         servicoPesquisa.setPkIdServico(null);
         if(listaPrecos != null)
            listaPrecos.clear();
         if(preco != null)
            preco.setPkIdCategoriaServico(null);
         
         //valor = null;
     }
     

     
     public void limparDeServicoParaBaixoComOsAgendamentos()
     {
         if(servicoLista != null)
            servicoLista.clear();
         if(servicoPesquisa == null)
         {
             getServicoPesquisa();
         }
         servicoPesquisa.setPkIdServico(null);
         if(listaPrecos != null)
            listaPrecos.clear();
         if(preco != null)
            preco.setPkIdCategoriaServico(null);
         //valor = null;
         if(agendamentos != null)
             agendamentos.clear();
         if(agendamentosMedicos != null)
             agendamentosMedicos.clear();
     }

    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    
    public void adicionarServicoSolicitado()
    {
        try
        {
            if (servicoPesquisa.getPkIdServico() == null)
            {
                Mensagem.erroMsg("Deve Selecionar pelo menos um servico");
                return;
            }
            //88888888888888888888888888888888888888888888888888888888888888888888888    Armindo
//            else if (admsServicoFacade.find(servicoPesquisa.getPkIdServico()).getNomeServico().equals("Internamento Medicina"))
//            {
//                if (InterCamaListarBean.getInstanciaBean().findAllLivresParaSolicitacoes(admsServicoFacade.find(servicoPesquisa.getPkIdServico()).getNomeServico()).isEmpty())
//                {
//                    Mensagem.erroMsg("Não existem camas disponíveis");
//                    return;
//                }
////                else
////                    NotifyView.getInstanciaBean().send(solicitacao.getFkIdPaciente());
//            }
        }
        catch (Exception ex)
        {
            Mensagem.erroMsg("Deve Selecionar pelo menos um servico");
            return;
        }
//        Integer classificacao = servicoSolicitado
        servicoSolicitado.setFkIdServico(admsServicoFacade.find(servicoPesquisa.getPkIdServico()));
        servicoSolicitado.setFkIdTipoSolicitacao(admsTipoSolicitacaoServicoFacade.find(servicoSolicitado.getFkIdTipoSolicitacao().getPkIdTipoSolicitacao()));
        servicoSolicitado.getFkIdClassificacaoServicoSolicitado().setPkIdClassificacaoServicoSolicitado(servicoSolicitado.getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado());
        servicoSolicitado.setFkIdPrecoCategoriaServico(valor);
        if (verificarSeServicoJaAdicionado(servicoSolicitado))
        {
            return;
        }
//        servicoSolicitado.setFkIdClassificacaoServicoSolicitado(admsClassificacaoServicoSolicitadoFacade.find(servicoSolicitado
//            .getFkIdClassificacaoServicoSolicitado()
//            .getPkIdClassificacaoServicoSolicitado()));
                
        solicitacao.getAdmsServicoSolicitadoList().add(criarEAdicionar());

//        valorTotalDoPreco = valorTotalDoPreco + valor.getValor();
        
//        servicoSolicitado = null;
//        
//        servicoSolicitado = criarInstanciaEAdicionar();
        
        numeroServicosNovosAdicionados++;
    }

    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public boolean verificarSeServicoJaAdicionado(AdmsServicoSolicitado servicoParaAdicionar)
    {
        for (int i = 0; i < solicitacao.getAdmsServicoSolicitadoList().size(); i++)
        {
            if (solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdServico().getPkIdServico() == servicoParaAdicionar.getFkIdServico().getPkIdServico()
                && solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getPkIdCategoriaServico()
                /*.getPkIdValorPrecoServico()*/ == servicoParaAdicionar.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getPkIdCategoriaServico()/*.getPkIdValorPrecoServico()*/)
            {
                Mensagem.erroMsg("Este Serviço Com Este Preço Já Foi Adicionado");
                //inicializaServicoSolicitado();
                return true;
            }
        }
        return false;
    }
    

    
    public AdmsServicoSolicitado criarEAdicionar()
    {
        AdmsServicoSolicitado servicoSolicitadoAdd = new AdmsServicoSolicitado();
        servicoSolicitadoAdd.setFkIdClassificacaoServicoSolicitado(admsClassificacaoServicoSolicitadoFacade.find((servicoSolicitado.getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado())));
        servicoSolicitadoAdd.setFkIdEstadoPagamento(admsEstadoPagamentoFacade.findPagamentoPendente());
        servicoSolicitadoAdd.setFkIdServico(servicoSolicitado.getFkIdServico());
        servicoSolicitadoAdd.setFkIdTipoSolicitacao(servicoSolicitado.getFkIdTipoSolicitacao());
        servicoSolicitadoAdd.setFkIdSolicitacao(solicitacao);
        servicoSolicitadoAdd.setFkIdPrecoCategoriaServico(servicoSolicitado.getFkIdPrecoCategoriaServico());
        
        return servicoSolicitadoAdd;
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void carregarServicos()
    {
        servicoLista.clear();
        servicoLista.addAll(admsServicoFacade.findServico(servicoPesquisa, true));
        if(!servicoLista.isEmpty())
        {
            servicoPesquisa.setPkIdServico(servicoLista.get(0).getPkIdServico());
            carregarPrecosServico(servicoLista.get(0));
        }
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void carregarPrecosServico(AdmsServico servico)
    {
        listaPrecos.clear();
        if(servico.getPkIdServico() != null)
        {
            listaPrecos.addAll(admsCategoriaServicoFacade.findCategoriasServicoAtivos(servico));
        }
    }
    
    
    
    public void removerServicoSolicitadoSeuAgendamentoEDescontarValorDoPreco(AdmsServicoSolicitado servicoSolicitadoRemover)
    {
        int posicao = pegarPosicaoServicoSolicitado(servicoSolicitadoRemover, solicitacao);
        if(posicao == -1) return;
        removerAgendamentoDoServicoCasoExista(servicoSolicitadoRemover);
//        valorTotalDoPreco = valorTotalDoPreco - solicitacao.getAdmsServicoSolicitadoList().get(posicao).getFkIdPrecoCategoriaServico().getValor();
        removerServicoSolicitadoPelaPosicao(posicao, solicitacao);
    }

    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void limparSolicitacao()
    {
        agendamentos = null;
        getAgendamentos();
        agendamentosMedicos = null;
        getAgendamentosMedicos();
        servicoSolicitado = null;
        getServicoSolicitado();
        servicoPesquisa = null;
        getServicoPesquisa();
    }

//    @Override
//    public void definirValorASerPagoPeloTipo(FinEncargoDevido encargo)
//    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
}
