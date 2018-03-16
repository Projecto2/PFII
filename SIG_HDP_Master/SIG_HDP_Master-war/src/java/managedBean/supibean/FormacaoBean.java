/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import entidade.GrlComuna;
import entidade.GrlContacto;
import entidade.GrlDistrito;
import entidade.GrlEndereco;
import entidade.GrlEstadoCivil;
import entidade.GrlMunicipio;
import entidade.GrlPessoa;
import entidade.GrlSexo;
import entidade.RhFuncionario;
import entidade.RhProfissao;
import entidade.SupiAreaFormacao;
import entidade.SupiCategoriaFormador;
import entidade.SupiFormacao;
import entidade.SupiFormacaoFormando;
import entidade.SupiFormadorAux;
import entidade.SupiInstituicaoProveniencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessao.GrlContactoFacade;
import sessao.GrlDistritoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlPessoaFacade;
import sessao.RhProfissaoFacade;
import sessao.SupiAreaFormacaoFacade;
import sessao.SupiFormacaoFacade;
import sessao.SupiFormacaoFormandoFacade;
import sessao.SupiFormadorAuxFacade;

/**
 *
 * @author helga
 */
@ManagedBean
@ViewScoped
public class FormacaoBean implements Serializable{
    @EJB
    private GrlContactoFacade grlContactoFacade;
    @EJB
    private GrlEnderecoFacade grlEnderecoFacade;
    @EJB
    private GrlDistritoFacade grlDistritoFacade;
    @EJB
    private RhProfissaoFacade rhProfissaoFacade;
    @EJB
    private SupiAreaFormacaoFacade supiAreaFormacaoFacade;
    @EJB
    private GrlPessoaFacade grlPessoaFacade;
    
    
    
    @EJB
    private SupiFormacaoFormandoFacade supiFormacaoFormandoFacade;
    @EJB
    private SupiFormadorAuxFacade supiFormadorAuxFacade;
    @EJB
    private SupiFormacaoFacade supiFormacaoFacade;
    
    private SupiFormacao supiFormacao = new SupiFormacao();
    private SupiFormadorAux supiFormadorAux = new SupiFormadorAux();
    private SupiFormacaoFormando supiFormacaoFormando = new SupiFormacaoFormando();
    private RhFuncionario rhFuncionario = new RhFuncionario();
    private GrlPessoa grlPessoa = new GrlPessoa();
    private RhProfissao rhProfissao = new RhProfissao();
    private SupiAreaFormacao areaFormacao = new SupiAreaFormacao();
    private GrlContacto grlContacto = new GrlContacto();
    private GrlEndereco grlEndereco = new GrlEndereco();
    
    private int idformando;
    private int idfuncionario;
    private int idmunicipio;
    private int idmunicipio2;
     private int iddistrito;
     private int idcomuna;
    private int idinstituicao;
    private int idareaformacao;
    private int idprofissao;
    private int idsexo;
    private int idestadocivil;
    private List<SupiFormacaoFormando> listaFormandos;
    private SupiCategoriaFormador supiCategoriaFormador;
    private boolean btnentidade;
    private boolean btnformador;
    private boolean btnDadosF;
    private boolean btnInstituicao;
    private int idSupiFormadorAux;
    private int idTipoEntidade;
    
    

    public int getIdareaformacao() {
        return idareaformacao;
    }

    public void setIdareaformacao(int idareaformacao) {
        this.idareaformacao = idareaformacao;
    }

    public int getIdprofissao() {
        return idprofissao;
    }

    public List<SupiFormacao> listarFormacoesFormadorInterno(){
    
    
    return supiFormacaoFacade.listarFormacoesFormadorInterno();
    }
    
     public List<SupiFormacao> listarFormacoesFormadorExternoFisico(){
    
    
    return supiFormacaoFacade.listarFormacoesFormadorExternoFisico();
    }
     
     
      public List<SupiFormacao> listarFormacoesFormadorJuridico(){
    
    
    return supiFormacaoFacade.listarFormacoesFormadorExternoJuridico();
    }
      
      
        public List<SupiFormacaoFormando> listadeFormandos(int id){
    
    
    return supiFormacaoFacade.listarFormandos(id);
    }
    
    
    
    
    public void setIdprofissao(int idprofissao) {
        this.idprofissao = idprofissao;
    }

    
    public FormacaoBean() {
        
        listaFormandos= new ArrayList<>();
    }
    
    public void gravar(){
        
        if(btnformador){
        
        supiFormadorAux.setFkIdFuncionario(new RhFuncionario(idfuncionario));
        supiFormadorAuxFacade.create(supiFormadorAux);
        supiFormacao.setFkIdFormadorAux(supiFormadorAux);
       supiFormacao.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(idmunicipio));
        supiFormacaoFacade.create(supiFormacao);
        
        for (SupiFormacaoFormando sff : listaFormandos) {
            
            supiFormacaoFormando = new SupiFormacaoFormando();
            supiFormacaoFormando.setFkIdFormacao(supiFormacao);
            supiFormacaoFormando.setFkIdFuncionario(sff.getFkIdFuncionario());
            supiFormacaoFormandoFacade.create(supiFormacaoFormando);
            
            
        }
        
        }
        
        else if (btnInstituicao){
            
        supiFormadorAux.setFkIdInstituicaoProveniencia(new SupiInstituicaoProveniencia(idinstituicao));
        supiFormadorAuxFacade.create(supiFormadorAux);
        supiFormacao.setFkIdFormadorAux(supiFormadorAux);
        supiFormacao.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(idmunicipio));
        supiFormacaoFacade.create(supiFormacao);
             for (SupiFormacaoFormando sff : listaFormandos) {
            
            supiFormacaoFormando = new SupiFormacaoFormando();
            supiFormacaoFormando.setFkIdFormacao(supiFormacao);
            supiFormacaoFormando.setFkIdFuncionario(sff.getFkIdFuncionario());
            supiFormacaoFormandoFacade.create(supiFormacaoFormando);
            
            
        }   
                
                
                }  else if(btnDadosF){
                    
//         grlPessoa = new GrlPessoa();
//         grlContacto =new GrlContacto();
//         grlEndereco = new GrlEndereco();
         
         grlEndereco.setFkIdComuna(new GrlComuna(idcomuna));
         grlEndereco.setFkIdDistrito(new GrlDistrito(iddistrito));
         grlEndereco.setFkIdMunicipio(new GrlMunicipio(idmunicipio2));
         grlEnderecoFacade.create(grlEndereco);
         
         grlContactoFacade.create(grlContacto);
         
                    
        grlPessoa.setFkIdSexo(new GrlSexo(idsexo));
        grlPessoa.setFkIdEstadoCivil(new GrlEstadoCivil(idestadocivil));
        grlPessoa.setFkIdContacto(grlContacto);
        grlPessoa.setFkIdEndereco(grlEndereco);
        
        grlPessoaFacade.create(grlPessoa);
        
        
        supiFormadorAux.setFkIdPessoa(grlPessoa);
        supiFormadorAux.setFkIdProfissao(new RhProfissao(idprofissao));
        supiFormadorAux.setFkIdAreaFormacao(new SupiAreaFormacao(idareaformacao));
        
        supiFormadorAuxFacade.create(supiFormadorAux);
        supiFormacao.setFkIdFormadorAux(supiFormadorAux);
        supiFormacao.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(idmunicipio));
        supiFormacaoFacade.create(supiFormacao);
             for (SupiFormacaoFormando sff : listaFormandos) {
            
            supiFormacaoFormando = new SupiFormacaoFormando();
            supiFormacaoFormando.setFkIdFormacao(supiFormacao);
            supiFormacaoFormando.setFkIdFuncionario(sff.getFkIdFuncionario());
            supiFormacaoFormandoFacade.create(supiFormacaoFormando);
                
                
                
                }
        
        
        }
       
        
        
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
    
    
    
    
    
    public SupiFormacao getSupiFormacao() {
        return supiFormacao;
    }

    public void setSupiFormacao(SupiFormacao supiFormacao) {
        this.supiFormacao = supiFormacao;
    }

    public SupiFormadorAux getSupiFormadorAux() {
        return supiFormadorAux;
    }

    public void setSupiFormadorAux(SupiFormadorAux supiFormadorAux) {
        this.supiFormadorAux = supiFormadorAux;
    }

    public int getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(int idfuncionario) {
        this.idfuncionario = idfuncionario;
    }

    public int getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(int idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public SupiFormacaoFormando getSupiFormacaoFormando() {
        return supiFormacaoFormando;
    }

    public void setSupiFormacaoFormando(SupiFormacaoFormando supiFormacaoFormando) {
        this.supiFormacaoFormando = supiFormacaoFormando;
    }

    public List<SupiFormacaoFormando> getListaFormandos() {
        if(listaFormandos==null)
            listaFormandos=new ArrayList<>();
        return listaFormandos;
    }

    public void setListaFormandos(List<SupiFormacaoFormando> listaFormandos) {
        this.listaFormandos = listaFormandos;
    }
    
    public void adicionarFormandosAlista(RhFuncionario funcionario){
        
        FacesContext context = FacesContext.getCurrentInstance();
    int posicaoEncontrada=-1;
    
    for(int pos=0;pos<listaFormandos.size();pos++){
      SupiFormacaoFormando listatemp= listaFormandos.get(pos);
      if(listatemp.getFkIdFuncionario().equals(funcionario))
          posicaoEncontrada=pos;
    }
    if(posicaoEncontrada<0){
        SupiFormacaoFormando formacaoFormando = new SupiFormacaoFormando();
        formacaoFormando.setFkIdFuncionario(funcionario);
        listaFormandos.add(formacaoFormando);
    }
    else{
     context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Funcionario já Adicionado!",""));
    
    }
    
    }
    
    public void removerFormandos(SupiFormacaoFormando formacaoFormando){
        
    int posicaoEncontrada=-1;
      for(int pos=0;pos<listaFormandos.size();pos++){
          SupiFormacaoFormando listatemp= listaFormandos.get(pos);
          if(listatemp.getFkIdFuncionario().equals(formacaoFormando.getFkIdFuncionario()))
              posicaoEncontrada=pos;
      }
      if(posicaoEncontrada > -1)
      {
            listaFormandos.remove(posicaoEncontrada);
      }
    
    }
    
    public RhFuncionario getRhFuncionario() {
        return rhFuncionario;
    }

    public void setRhFuncionario(RhFuncionario rhFuncionario) {
        this.rhFuncionario = rhFuncionario;
    }

    public int getIdformando() {
        return idformando;
    }

    public void setIdformando(int idformando) {
        this.idformando = idformando;
    }

    public SupiCategoriaFormador getSupiCategoriaFormador() {
        return supiCategoriaFormador;
    }

    public void setSupiCategoriaFormador(SupiCategoriaFormador supiCategoriaFormador) {
        this.supiCategoriaFormador = supiCategoriaFormador;
    }

    public boolean isBtnentidade() {
        return btnentidade;
    }

    public void setBtnentidade(boolean btnentidade) {
        this.btnentidade = btnentidade;
    }

    public boolean isBtnformador() {
        return btnformador;
    }

    public void setBtnformador(boolean btnformador) {
        this.btnformador = btnformador;
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

    public int getIdSupiFormadorAux() {
        return idSupiFormadorAux;
    }

    public void setIdSupiFormadorAux(int idSupiFormadorAux) {
        this.idSupiFormadorAux = idSupiFormadorAux;
    }

    public int getIdTipoEntidade() {
        return idTipoEntidade;
    }

    public void setIdTipoEntidade(int idTipoEntidade) {
        this.idTipoEntidade = idTipoEntidade;
    }

    public int getIdinstituicao() {
        return idinstituicao;
    }

    public void setIdinstituicao(int idinstituicao) {
        this.idinstituicao = idinstituicao;
    }

    public GrlPessoa getGrlPessoa() {
        return grlPessoa;
    }

    public void setGrlPessoa(GrlPessoa grlPessoa) {
        this.grlPessoa = grlPessoa;
    }

    public RhProfissao getRhProfissao() {
        return rhProfissao;
    }

    public void setRhProfissao(RhProfissao rhProfissao) {
        this.rhProfissao = rhProfissao;
    }

    public SupiAreaFormacao getAreaFormacao() {
        return areaFormacao;
    }

    public void setAreaFormacao(SupiAreaFormacao areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public int getIdsexo() {
        return idsexo;
    }

    public void setIdsexo(int idsexo) {
        this.idsexo = idsexo;
    }

    public int getIdestadocivil() {
        return idestadocivil;
    }

    public void setIdestadocivil(int idestadocivil) {
        this.idestadocivil = idestadocivil;
    }

    public GrlContacto getGrlContacto() {
        return grlContacto;
    }

    public void setGrlContacto(GrlContacto grlContacto) {
        this.grlContacto = grlContacto;
    }

    public GrlEndereco getGrlEndereco() {
        return grlEndereco;
    }

    public void setGrlEndereco(GrlEndereco grlEndereco) {
        this.grlEndereco = grlEndereco;
    }

    public int getIdmunicipio2() {
        return idmunicipio2;
    }

    public void setIdmunicipio2(int idmunicipio2) {
        this.idmunicipio2 = idmunicipio2;
    }

    public int getIddistrito() {
        return iddistrito;
    }

    public void setIddistrito(int iddistrito) {
        this.iddistrito = iddistrito;
    }

    public int getIdcomuna() {
        return idcomuna;
    }

    public void setIdcomuna(int idcomuna) {
        this.idcomuna = idcomuna;
    }
    
    
    
    
    
}
