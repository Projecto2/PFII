/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import entidade.SupiPontuacao;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.SupiPontuacaoFacade;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiPontuacaoTeste implements Serializable{
    @EJB
    private SupiPontuacaoFacade supiPontuacaoFacade;
    

    /**
     * Creates a new instance of SupiPontuacaoTeste
     */
    SupiPontuacao pontuacao1;
     Mensagem mensagem;
    public SupiPontuacaoTeste() {
        pontuacao1 = new SupiPontuacao();
      mensagem = new Mensagem();
    }

    public SupiPontuacao getPontuacao1() {
        return pontuacao1;
    }

    public void setPontuacao1(SupiPontuacao pontuacao1) {
        this.pontuacao1 = pontuacao1;
    }
     public void salvar()  
   {
       if(pontuacao1!= null){
           supiPontuacaoFacade.create(pontuacao1);
           mensagem.addMessage("Cadastrado", null);
       }
   
   } 
    public void limpar() {
        pontuacao1 = new SupiPontuacao();
    } 
    
}
