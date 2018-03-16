/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.SupiAreaFormacao;
import entidade.SupiSeccao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.SupiAreaFormacaoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiAreaFormacaoSupi implements Serializable {

    @Resource
    private UserTransaction userTransaction;
    @EJB
    private SupiAreaFormacaoFacade supiAreaFormacaoFacade;

    private SupiAreaFormacao supiAreaFormacao;

    /**
     * Creates a new instance of SupiAreaFormacao
     */
    public SupiAreaFormacaoSupi() {
    }
    
    public static SupiAreaFormacaoSupi getInstanciaBean()
    {
        return (SupiAreaFormacaoSupi) GeradorCodigo.getInstanciaBean("supiAreaFormacaoSupi");
    }
    
    public static SupiAreaFormacao getInstancia () {
      SupiAreaFormacao  areaDeformacao = new SupiAreaFormacao();
        return areaDeformacao;
    }

    public SupiAreaFormacao getSupiAreaFormacao() {
        if (supiAreaFormacao == null) {
            supiAreaFormacao = new SupiAreaFormacao();
        }
        return supiAreaFormacao;
    }

    public void setSupiAreaFormacao(SupiAreaFormacao supiAreaFormacao) {
        this.supiAreaFormacao = supiAreaFormacao;
    }

    public String create() {
        try {
            userTransaction.begin();
            supiAreaFormacaoFacade.create(supiAreaFormacao);
            userTransaction.commit();
            Mensagem.sucessoMsg("Area de Formação guardada com sucesso!");
        } catch (Exception e) {
            try {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        supiAreaFormacao = null;

        return null;
    }

    public String edit() {
        try {
            userTransaction.begin();
            if (supiAreaFormacao.getPkIdAreaFormacao() == null) {
                throw new NullPointerException("PK -> NULL");
            }
            supiAreaFormacaoFacade.edit(supiAreaFormacao);
            userTransaction.commit();
            Mensagem.sucessoMsg("Area de Formação editada com sucesso!");
        } catch (Exception e) {
            try {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        supiAreaFormacao = null;

        return null;
    }

    public void eliminar(int idAreaFormacao) {

        FacesContext fc = FacesContext.getCurrentInstance();

        try {

            supiAreaFormacao = supiAreaFormacaoFacade.find(idAreaFormacao);

            if (supiAreaFormacao.getSupiFormadorList() != null) {

                supiAreaFormacaoFacade.remove(supiAreaFormacao);

                fc.addMessage(null, new FacesMessage("Area de Formação Removida com sucesso!"));

                supiAreaFormacao = new SupiAreaFormacao();

            } else {
                fc.addMessage(null, new FacesMessage("Erro ao Eliminar a area de formação " + ""));

            }

        } catch (Exception ex) {
            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
        }
    }

    public List<SupiAreaFormacao> findall() {
        return supiAreaFormacaoFacade.findAll();
    }

}
