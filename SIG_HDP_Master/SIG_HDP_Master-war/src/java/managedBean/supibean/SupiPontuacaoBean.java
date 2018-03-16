/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import entidade.SupiPontuacao;
//import static entidade.SupiPontuacao_.pkIdPontuacao;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.SupiPontuacaoFacade;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiPontuacaoBean {
    @EJB
    private SupiPontuacaoFacade supiPontuacaoFacade;
    

    /**
     * Creates a new instance of SupiPontuacaoBean
     */
     SupiPontuacao pontuacao1, pontuacaoPesquisa, pontuacaoVisualizar;
     Mensagem mensagem;
     private boolean pesquisar = false;
    public SupiPontuacaoBean() {
        pontuacao1 = new SupiPontuacao();
        mensagem = new Mensagem();
    }

    public SupiPontuacao getPontuacaoPesquisa() {
        return pontuacaoPesquisa;
    }

    public void setPontuacaoPesquisa(SupiPontuacao pontuacaoPesquisa) {
        this.pontuacaoPesquisa = pontuacaoPesquisa;
    }

    public SupiPontuacao getPontuacaoVisualizar() {
        return pontuacaoVisualizar;
    }

    public void setPontuacaoVisualizar(SupiPontuacao pontuacaoVisualizar) {
        this.pontuacaoVisualizar = pontuacaoVisualizar;
    }

    public boolean isPesquisar() {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar) {
        this.pesquisar = pesquisar;
    }
    
    public SupiPontuacao getPontuacao1() {
        return pontuacao1;
    }

    public void setPontuacao1(SupiPontuacao pontuacao1) {
        this.pontuacao1 = pontuacao1;
    }
     
     public void limpar() {
        pontuacao1 = new SupiPontuacao();
    }
     public String salvar() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//      System.out.println("ID"+pontuacao1.getPkIdPontuacao());
//      
//        if (pontuacao1 != null) {
// 
//            if (verificarPontuacao(pontuacao1.getValorInicial(), pontuacao1.getValorFinal())) {
//                mensagem.addMessage("Pontução Já Existente!", null);
//                limpar();
//            } else {
//                
//                supiPontuacaoFacade.create(pontuacao1);
//                limpar();
//                mensagem.addMessage("Dados Registados com sucesso", null);
//                System.out.println("Pontuação:  pontuação  Registada Com Sucesso !! ");
//            }
//        }
        return "listarPontuacao";

    }
     public String limparPesquisa ()
     {
          return "listarPontuacao.xhtml?faces-redirect=true";
     } 
     public void eliminar(int idPontuacao) {

        FacesContext fc = FacesContext.getCurrentInstance();

//        try {
//
//            pontuacao1 = supiPontuacaoFacade.find(idPontuacao);
//
//            if (pontuacao1.getSupiCriterioAvaliacaoList()!= null) {
//
//                supiPontuacaoFacade.remove(pontuacao1);
//
//                
//                mensagem.addMessage("Dados Removidos com sucesso!", null);
//
//                pontuacao1 = new SupiPontuacao();
//
//
//            } else {
//                fc.addMessage(null, new FacesMessage("Erro ao Eliminar " + ""));
//                
//
//            }
//
//        } catch (Exception ex) {
//            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
//        }
    }
     public boolean verificarPontuacao(double valorMin, double valorMax) {
        List<SupiPontuacao> listaEstagiario = supiPontuacaoFacade.findAll();

//        for (SupiPontuacao est : listaEstagiario) {
//            if (est.getValorInicial() == valorMin && est.getValorFinal() == valorMax) {
//                return true;
//            }
//        }

        return false;

    }
      public void editar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        try {
            if (pontuacao1 != null) {
                supiPontuacaoFacade.edit(pontuacao1);
                
                mensagem.addMessage("Dados Actualizados com sucesso", null);
            }
        } catch (Exception e) {
            e.getMessage();
            
        }

    }
      public void visualizarDados(Integer idPontuacao) {
        pontuacao1 = supiPontuacaoFacade.find(idPontuacao);
    }
      public String prepararEditar(Integer idPontuacao) {
         
        pontuacao1 = supiPontuacaoFacade.find(idPontuacao);
        return "editarPontuacao";
    }
      public List<SupiPontuacao> listarTodas() {
        return supiPontuacaoFacade.findAll();
    }
    
}
