/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.GrlEndereco;
import entidade.GrlMunicipio;
import entidade.GrlProvincia;
import entidade.RhFuncionario;
import entidade.RhProfissao;
import entidade.RhSeccaoTrabalho;
import entidade.SupiAreaFormacao;
import entidade.SupiCategoriaFormador;
import entidade.SupiEstado;
import entidade.SupiFormacao;
import entidade.SupiFormacaoFuncionario;
import entidade.SupiFormacaoFuncionarioPk;
import entidade.SupiFormador;
import entidade.SupiFormadorAux;
import entidade.SupiInstituicaoProveniencia;
import entidade.SupiOpcao;
import entidade.SupiTipoFormador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import managedBean.grlbean.GrlPessoaBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.GrlEnderecoFacade;
import sessao.GrlMunicipioFacade;
import sessao.GrlPessoaFacade;
import sessao.GrlProvinciaFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhProfissaoFacade;
import sessao.RhSeccaoTrabalhoFacade;
import sessao.SupiAreaFormacaoFacade;
import sessao.SupiCategoriaFormadorFacade;
import sessao.SupiEstadoFacade;
import sessao.SupiFormacaoFacade;
import sessao.SupiFormacaoFuncionarioFacade;
import sessao.SupiFormacaoFuncionarioPkFacade;
import sessao.SupiFormadorAuxFacade;
import sessao.SupiFormadorFacade;
import sessao.SupiInstituicaoProvenienciaFacade;
import sessao.SupiOpcaoFacade;
import sessao.SupiSeccaoFacade;
import sessao.SupiTipoFormadorFacade;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiFormadorBean implements Serializable
{
    @EJB
    private SupiInstituicaoProvenienciaFacade supiInstituicaoProvenienciaFacade;

    @EJB
    private GrlPessoaFacade grlPessoaFacade;
    
    @EJB
    private RhProfissaoFacade rhProfissaoFacade;
    @EJB
    private SupiCategoriaFormadorFacade supiCategoriaFormadorFacade;
    @EJB
    private SupiFormadorAuxFacade supiFormadorAuxFacade;
    
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    
    @EJB
    private SupiFormadorFacade supiFormadorFacade;
    @EJB
    private SupiTipoFormadorFacade supiTipoFormadorFacade;
    @EJB
    private SupiAreaFormacaoFacade supiAreaFormacaoFacade;
    
     @EJB
    private GrlProvinciaFacade grlProvinciaFacade;
    @EJB
    private GrlEnderecoFacade grlEnderecoFacade;
    @EJB
    private SupiFormacaoFuncionarioPkFacade supiFormacaoFuncionarioPkFacade;
    @EJB
    private SupiOpcaoFacade supiOpcaoFacade;
    @EJB
    private RhSeccaoTrabalhoFacade rhSeccaoTrabalhoFacade;
    
    @EJB
    private SupiFormacaoFacade supiFormacaoFacade;
    @EJB
    private SupiEstadoFacade supiEstadoFacade;
    @EJB
    private SupiFormacaoFuncionarioFacade supiFormacaoFuncionarioFacade;
    @EJB
    private SupiSeccaoFacade supiSeccaoFacade;

    private GrlProvincia grlProvincia;
    private GrlEndereco grlEndereco;
    private RhFuncionario rhFuncionario;
    private GrlMunicipio grlMunicipio;
    private SupiFormadorAux supiFormador;
    private SupiFormacao supiFormacao;

    public SupiFormacao getSupiFormacao() {
        return supiFormacao;
    }

    public void setSupiFormacao(SupiFormacao supiFormacao) {
        this.supiFormacao = supiFormacao;
    }
            
            
      private SupiFormacao pesquisaFormacao;
    private List<SupiFormacao> listaFormacao;
    private SupiEstado supiEstado;
    private SupiFormacaoFuncionario supiFormacaoFuncionario;
    private RhSeccaoTrabalho rhSeccaoTrabalho;
    private SupiOpcao supiOpcao;
    private SupiFormacaoFuncionarioPk supiFormacaoFuncionarioPk;

   
             
    /**
     * Creates a new instance of SupiFormadorBeanrhProfissao
     */
    SupiFormador formador1, pesquisaFormador;
    private List<SupiFormador> listaFormadores;
    SupiTipoFormador opcao1;
    SupiAreaFormacao areaDeFormacao1;
    private boolean eDoHDP1;
    Mensagem mensagem;
   
    private SupiFormadorAux supiFormadorAux;
    private SupiCategoriaFormador supiCategoriaFormador;
    private boolean btnentidade;
    private boolean btnformador;
    private boolean btnDadosF;
    private boolean btnInstituicao;
    private RhProfissao rhProfissao;
   
    private SupiInstituicaoProveniencia supiInstituicaoProveniencia;
   private int idSupiFormadorAux;
   private int idTipoEntidade;

    public int getIdTipoEntidade() {
        return idTipoEntidade;
    }

    public void setIdTipoEntidade(int idTipoEntidade) {
        this.idTipoEntidade = idTipoEntidade;
    }

    public boolean isBtnDadosF() {
        return btnDadosF;
    }

    public void setBtnDadosF(boolean btnDadosF) {
        this.btnDadosF = btnDadosF;
    }

    public boolean isBtnInstituicao() {
        return btnInstituicao;
    }

    public void setBtnInstituicao(boolean btnInstituicao) {
        this.btnInstituicao = btnInstituicao;
    }
    
     public SupiFormadorAux getInstancia()
    {
        
        SupiFormadorAux formAux = new SupiFormadorAux();
        formAux.setFkIdAreaFormacao(new SupiAreaFormacao());
        //formAux.setFkIdCategoriaFormador(new SupiCategoriaFormador());
        formAux.setFkIdInstituicaoProveniencia(new SupiInstituicaoProveniencia());
        formAux.setFkIdPessoa(new GrlPessoaBean().getInstanciaPessoa());
        formAux.setFkIdProfissao(new RhProfissao());
        
        return formAux;
    }
    
    public void gravar(){
      //  supiFormacao.setFkIdFormador(new SupiFormadorAux(idSupiFormadorAux));
        
       System.out.println(this.supiFormacao.getDescricaoFormacao());
    
      //supiFormacaoFacade.create(this.supiFormacao);
    
    
    }
    
    
    
    public void tipodeformador(){
        
        if(idSupiFormadorAux==2){
      
        btnentidade=true;
        btnformador=false;
        
        }else if(idSupiFormadorAux==1){
        btnentidade= false;
        btnformador= true;
        btnDadosF =false;
        btnInstituicao=false;
        }
    
    }
        
        
        public void tipoentidade(){
        
        if(idTipoEntidade==2){
      
        btnDadosF =false;
        btnformador=false;
        btnInstituicao=true;
        
        
        }else if(idTipoEntidade==1){
       
        btnformador= false;
        btnDadosF =true;
        btnInstituicao=false;
        }
    
    }
    
     
    public SupiCategoriaFormador getSupiCategoriaFormador() {
        return supiCategoriaFormador;
    }

    public void setSupiCategoriaFormador(SupiCategoriaFormador supiCategoriaFormador) {
        this.supiCategoriaFormador = supiCategoriaFormador;
    }

    public RhProfissao getRhProfissao() {
        return rhProfissao;
    }

    public void setRhProfissao(RhProfissao rhProfissao) {
        this.rhProfissao = rhProfissao;
    }

    public SupiInstituicaoProveniencia getSupiInstituicaoProveniencia() {
        return supiInstituicaoProveniencia;
    }

    public void setSupiInstituicaoProveniencia(SupiInstituicaoProveniencia supiInstituicaoProveniencia) {
        this.supiInstituicaoProveniencia = supiInstituicaoProveniencia;
    }
    
    
    
    public SupiFormadorBean()
    {
        areaDeFormacao1 = new SupiAreaFormacao();
        opcao1 = new SupiTipoFormador();
        eDoHDP1 = true;
        mensagem = new Mensagem();
    }
    
    public SupiFormador getInstanciaFormador()
    {
        SupiFormador form = new SupiFormador();
        form.setFkIdAreaFormacao(new SupiAreaFormacao());
        form.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());
        form.setFkIdTipoFormador(new SupiTipoFormador());
        
        return form;
    }

    
    public SupiFormador getPesquisaFormador()
    {
        if (pesquisaFormador == null)
        {
            pesquisaFormador = getInstanciaFormador();
        }
        return pesquisaFormador;
    }
    
    public void setPesquisaFormador(SupiFormador pesquisaFormador)
    {
        this.pesquisaFormador = pesquisaFormador;
    }
    
    public SupiFormadorAux getSupiFormadorAux()
    {
        if (supiFormadorAux == null)
        {
            supiFormadorAux = getInstancia();
        }
        return supiFormadorAux;
    }
    
    public void setSupiFormadorAux(SupiFormadorAux supiFormadorAux)
    {
        this.supiFormadorAux = supiFormadorAux;
    }
    
    public ArrayList<SelectItem> getTodasAreasFormacao()
    {
        
        ArrayList<SelectItem> itens = new ArrayList<>();
        
        for (SupiAreaFormacao e : supiAreaFormacaoFacade.findAll())
        {
            itens.add(new SelectItem(e.getPkIdAreaFormacao(), e.getDescricao()));
        }
        return itens;
    }
    
    public List<SupiFormador> getListaFormadores()
    {
        return listaFormadores;
    }
    
    public void setListaFormadores(List<SupiFormador> listaFormadores)
    {
        this.listaFormadores = listaFormadores;
    }
    
    public SupiFormador getFormador1()
    {
        if (formador1 == null)
        {
            formador1 = new SupiFormador();
            formador1.setFkIdAreaFormacao(new SupiAreaFormacao());
            formador1.setFkIdTipoFormador(new SupiTipoFormador());
        }
        return formador1;
    }
    
    public void setFormador1(SupiFormador formador1)
    {
        this.formador1 = formador1;
    }
    
    public SupiTipoFormador getOpcao1()
    {
        return opcao1;
    }
    
    public void setOpcao1(SupiTipoFormador opcao1)
    {
        this.opcao1 = opcao1;
    }
    
    public RhFuncionario getRhFuncionario()
    {
        if (rhFuncionario == null)
        {
            rhFuncionario = new RhFuncionario();
        }
        return rhFuncionario;
    }
    
    public void setRhFuncionario(RhFuncionario rhFuncionario)
    {
        this.rhFuncionario = rhFuncionario;
    }
    
    public SupiAreaFormacao getAreaDeFormacao1()
    {
        return areaDeFormacao1;
    }
    
    public void setAreaDeFormacao1(SupiAreaFormacao areaDeFormacao1)
    {
        this.areaDeFormacao1 = areaDeFormacao1;
    }
    
    public boolean isEDoHDP1()
    {
        return eDoHDP1;
    }
    
    public void setEDoHDP1(boolean eDoHDP1)
    {
        this.eDoHDP1 = eDoHDP1;
    }
    
    public void limpar()
    {
        formador1 = null;
        areaDeFormacao1 = new SupiAreaFormacao();
        opcao1 = new SupiTipoFormador();
    }
    
    public List<RhFuncionario> listarFuncionario()
    {
        
        return rhFuncionarioFacade.findAll();
    }
    
    public List<SupiTipoFormador> listarTipoFormador()
    {
        
        return supiTipoFormadorFacade.findAll();
    }
    
    public List<SupiCategoriaFormador> listarCategoriaFormador()
    {
        
        return supiCategoriaFormadorFacade.findAll();
    }
    
    public List<SupiAreaFormacao> listarAreaFormacao()
    {
        
        return supiAreaFormacaoFacade.findAll();
    }
    
    public List<RhProfissao> listarProfissao()
    {
        return rhProfissaoFacade.findAll();
    }
    public List<SupiInstituicaoProveniencia> listarInstituicao()
    {
        
        return supiInstituicaoProvenienciaFacade.findAll();
    }

//    public boolean verificarFormador(String nome) {
//        List<SupiFormador> listaEstagiario = supiFormadorFacade.findAll();
//        for (SupiFormador est : listaEstagiario) {
//            if (est.getNome().equals(nome)) {
//                return true;
//            }
//        }
//
//        return false;
//
//    }
    public void pesquisarFormador()
    {
        listaFormadores = supiFormadorFacade.findFormador(pesquisaFormador);
        
        if (listaFormadores.isEmpty())
        {
            Mensagem.erroMsg("Nenhum Formador encontrado para esta pesquisa");
        }
    }
    
    public void limparPesquisas()
    {
        setPesquisaFormador(getInstanciaFormador());
        listaFormadores = new ArrayList<>();
    }
    
    public void eliminar()
    {
        try
        {
            supiFormadorFacade.remove(pesquisaFormador);
            pesquisaFormador = getInstanciaFormador();
            pesquisarFormador();
            Mensagem.sucessoMsg("O Formador foi eliminado com sucesso!");
        } catch (Exception ex)
        {
            Mensagem.warnMsg("O Formador não pode ser eliminado, porque está a ser utilizado.");
        }
        
    }
    
    public String salvar()
    {
        formador1.setFkIdFuncionario(rhFuncionario);
        if (formador1 != null)
        {
//            if (verificarFormador(formador1.getNome())) {
//                mensagem.addMessage("Formador Já Existente!", null);
//                limpar();
//                return "cadastroFormador";
//            } else {
            formador1.setDataCadastro(new Date());
//                SupiTipoFormador op = supiTipoFormadorFacade.find(opcao1.getPkIdTipoFormador());

//                if (op.getPkIdTipoFormador() == 2) {
//                    // area = sUAreaDeFormacaoFacade.find(areaDeFormacao.getIdAreaDeFormacao());
//                    formador1.setFkIdAreaFormacao(supiAreaFormacaoFacade.find(areaDeFormacao1.getPkIdAreaFormacao()));
//
//                }
//                formador1.setFkIdTipoFormador(op);
//                //System.out.println("Nome de form: " + formador1.getNome());
//                System.out.println("Nome: " + formador1.getFkIdFuncionario().getFkIdPessoa().getNome());
//                System.out.println("Sobrenome: " + formador1.getFkIdFuncionario().getFkIdPessoa().getSobreNome());
//                System.out.println("Data Nascimento: " + formador1.getDataNascimento());
//                System.out.println("area de form: " + formador1.getFkIdAreaFormacao().getPkIdAreaFormacao());
//                System.out.println("tipo de form: " + formador1.getFkIdTipoFormador().getPkIdTipoFormador());
            supiFormadorFacade.create(formador1);
            mensagem.addMessage("Dados Registados com sucesso", null);
            
            limpar();
        }

        //}
        return "listarFormador";
    }
    
    public List<SupiTipoFormador> listarOpcao()
    {
        return supiTipoFormadorFacade.findAll();
    }
    
    public void editar()
    {
        if (formador1 != null)
        {
            
            formador1.setDataCadastro(new Date());
            SupiTipoFormador op = supiTipoFormadorFacade.find(opcao1.getPkIdTipoFormador());
            
            if (op.getPkIdTipoFormador() == 2)
            {
                // area = sUAreaDeFormacaoFacade.find(areaDeFormacao.getIdAreaDeFormacao());
                formador1.setFkIdAreaFormacao(supiAreaFormacaoFacade.find(areaDeFormacao1.getPkIdAreaFormacao()));
            }
            
            formador1.setFkIdTipoFormador(op);
            supiFormadorFacade.edit(formador1);
            mensagem.addMessage("Dados Actualizados com Sucesso!", null);
            
        }
    }

    /**
     * ***********************
     * ELIMINAR DADOS *** ***********************
     */
    public void eliminar(int idFormador)
    {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        
        try
        {
            
            formador1 = supiFormadorFacade.find(idFormador);
            
            if (formador1 != null)
            {
                
                supiFormadorFacade.remove(formador1);
                
                fc.addMessage(null, new FacesMessage("Dados Removidos com sucesso!"));
                
                formador1 = new SupiFormador();
                
            } else
            {
                fc.addMessage(null, new FacesMessage("Erro ao Eliminar " + ""));
                
            }
            
        } catch (Exception ex)
        {
            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
        }
        
    }

    //LIstar Todas
    public List<SupiFormador> listarTodos()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try
        {
            return supiFormadorFacade.findAll();
        } catch (Exception e)
        {
            
        }
        return null;
    }
    
    public boolean retornaEstadoFormador()
    {
        
        int idEstadoFormador = getFormador1().getFkIdTipoFormador().getPkIdTipoFormador();
        
        if (idEstadoFormador == 1)
        {
            eDoHDP1 = true;
        } else
        {
            eDoHDP1 = false;
        }
        
        return eDoHDP1;
        
    }
    
    public String prepararEditar(Integer idFormador)
    {
        formador1 = supiFormadorFacade.find(idFormador);
        return "editarFormador";
    }

    

    public boolean isBtnentidade() {
        return btnentidade;
    }

    public void setBtnentidade(boolean btnentidade) {
        this.btnentidade = btnentidade;
    }

    public int getIdSupiFormadorAux() {
        return idSupiFormadorAux;
    }

    public void setIdSupiFormadorAux(int idSupiFormadorAux) {
        this.idSupiFormadorAux = idSupiFormadorAux;
    }

    public boolean isBtnformador() {
        return btnformador;
    }

    public void setBtnformador(boolean btnformador) {
        this.btnformador = btnformador;
    }

   
    

    

    
    
}
