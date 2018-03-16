/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import entidade.SupiAreaFormacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.SupiAreaFormacaoFacade;
import util.GeradorCodigo;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiAreaFormacaoBean implements Serializable{
    @EJB
    private SupiAreaFormacaoFacade supiAreaFormacaoFacade;
    
    SupiAreaFormacao areaDeformacao;

    /**
     * Creates a new instance of SupiAreaFormacao
     */
    public SupiAreaFormacaoBean() {
        //areaDeformacao = new SupiAreaFormacao();
    }
    
     public static SupiAreaFormacaoBean getInstanciaBean()
    {
        return (SupiAreaFormacaoBean) GeradorCodigo.getInstanciaBean("supiAreaFormacaoBean");
    }
    
    
    public static SupiAreaFormacao getInstancia () {
      SupiAreaFormacao  areaDeformacao = new SupiAreaFormacao();
        return areaDeformacao;
    }
    
    public void limpar()
    {
        areaDeformacao = new SupiAreaFormacao();
    }

    public SupiAreaFormacao getAreaDeformacao() {
        return areaDeformacao;
    }

    public void setAreaDeformacao(SupiAreaFormacao areaDeformacao) {
        this.areaDeformacao = areaDeformacao;
    }
    
      //LIstar Todas
   
  public List<SupiAreaFormacao> listarTodos()
    {
         return supiAreaFormacaoFacade.findAll(); 
 
    }
  
  public String prepararEditar(Integer area) {
        areaDeformacao = supiAreaFormacaoFacade.find(area);
        return "editarAreaDeFormacao";
    }
  
   /**
     * **********************************
     * Vizualizar DADOS DA AVALIACAO**
     ***********************************
     */
    public void visualizarDados(Integer area) {
        areaDeformacao = supiAreaFormacaoFacade.find(area);
    }
    
    public String salvar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

         
        supiAreaFormacaoFacade.create(areaDeformacao);
        limpar();
        facesContext.addMessage(null, new FacesMessage("Dados Registados com sucesso"));
        return "areaDeFormacao";
    }
    
    public void editar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        supiAreaFormacaoFacade.edit(areaDeformacao);
        //limpar();
        facesContext.addMessage(null, new FacesMessage("Dados Actualizados com Sucesso!"));

    }
    
     /**
     * ***********************
     * ELIMINAR DADOS *** ***********************
     */
    public void eliminar(int idAvaliacao) {

        FacesContext fc = FacesContext.getCurrentInstance();

        try {

            areaDeformacao = supiAreaFormacaoFacade.find(idAvaliacao);

            if (areaDeformacao.getSupiFormadorList().isEmpty()) {

                supiAreaFormacaoFacade.remove(areaDeformacao);

                fc.addMessage(null, new FacesMessage("Dados Removidos com sucesso!"));

                areaDeformacao = new SupiAreaFormacao();


            } else {
                fc.addMessage(null, new FacesMessage("Área de Formação não pode ser Removida" + ""));

            }

        } catch (Exception ex) {
            fc.addMessage(null, new FacesMessage("Erro:" + "" + ex.getMessage()));
        }


    }
    // vai faltar o método imprimir relatório e imprimir cada um.
    
    
}
