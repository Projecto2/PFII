/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.GrlEndereco;
import entidade.GrlPais;
import entidade.GrlPessoa;
import entidade.RhFuncionario;
import entidade.RhProfissao;
import entidade.SupiEscala;
//import entidade.SupiFuncionarioHasEscala;
//import entidade.SupiFuncionarioSupervisionaEscala;
import entidade.SupiSeccao;

import entidade.SupiTurno;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import sessao.GrlPessoaFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhProfissaoFacade;
import sessao.SupiSeccaoFacade;
import sessao.SupiTurnoFacade;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;
import sessao.SupiEscalaFacade;
import util.GeradorCodigo;
//import sessao.SupiFuncionarioHasEscalaFacade;
//import sessao.SupiFuncionarioSupervisionaEscalaFacade;

import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiEnfermeiroSupiBean implements Serializable {
    
    
    @EJB
    private SupiEscalaFacade supiEscalaFacade;
    @Resource
    private UserTransaction userTransaction;
    @EJB
    private SupiSeccaoFacade supiSeccaoFacade;
    @EJB
    private RhProfissaoFacade rhProfissaoFacade;
    
    @EJB
    private GrlPessoaFacade grlPessoaFacade;
    
    @EJB
    private SupiTurnoFacade supiTurnoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
//    private SupiFuncionarioHasEscalaFacade supiFuncionarioHasEscalaFacade;
//    @EJB
//    private SupiFuncionarioSupervisionaEscalaFacade supervisionaEscalaFacade;
//    
    private GrlPessoa pessoa;
    private SupiEscala escala, eliminarEscala,escalaP, escala1;
    
    private GrlEndereco endereco;
    
    private GrlPais pais;
    RhFuncionario rhFuncionario;
    SupiSeccao supiSeccao;
    
    RhProfissao rhProfissao;
    
   
    private SupiTurno turno;
    private int idFuncionario;
    private int idEnfermeiro;
    private String licenca;
    private int idSeccao;
    private int cargaHorariaMensal;
    private String nomeCompleto;
    private List<Object> enfermeirosSeleccionados, supervisoresSeleccionados;
    int turnoSeleccionado;
    
    private boolean pesquisar = false;
    private List<RhFuncionario> listaEnfermeiros;
    private List<RhFuncionario> listaSupervisores;
    private List<SupiEscala> escalaPesquisar;
    
 

    /**
     * Creates a new instance of EnfermeiroSupiBean
     */
    public SupiEnfermeiroSupiBean() {
        turno = new SupiTurno();
        
        supiSeccao = new SupiSeccao();
        rhProfissao = new RhProfissao();
        rhFuncionario = new RhFuncionario();
        
    }
    public static SupiEnfermeiroSupiBean getInstanciaBean()
    {
        return (SupiEnfermeiroSupiBean) GeradorCodigo.getInstanciaBean("supiEnfermeiroSupiBean");
    }
    
    public SupiEscala getInstancia()
   {
       
       //SupiEscala escala1 = new SupiEscala();
       escala1.setFkIdSeccao(new SupiSeccao());
       
     
      
//      RhFuncionario funcionario = new RhFuncionario();
//      funcionario.setFkIdPessoa(new PessoaBean().getInstanciaPessoa());
//      funcionario.setFkIdAnexoGuiaTransferencia(new GrlFicheiroAnexado());
//      funcionario.setFkIdEstadoFuncionario(new RhEstadoFuncionario());
//      funcionario.setFkIdCargo(new RhCargo());
//      funcionario.setFkIdTipoFuncionario(new RhTipoFuncionario());
//      funcionario.setFkIdCategoria(new RhCategoriaProfissao());
//      funcionario.setFkIdCentroHospitalar(new GrlCentroHospitalar());

      return escala1;
   }
    
   public void changeTurno (ValueChangeEvent eve)
   {
      int turno = Integer.parseInt(eve.getNewValue().toString());
      
        //Faz com que apareça o horário do turno selecionado      
       try {
          // escala.getFkIdTurno().setCargaHoraria(supiTurnoFacade.find(turno).getCargaHoraria());
       } catch (Exception ex) {
           System.out.println(ex);
       }
        System.out.println("Trocou o turno - "+ turno);
      
   }
   
   
      public SupiTurno getTurno() {
        return turno;
    }
    
    public void setTurno(SupiTurno turno) {
        this.turno = turno;
    }

    public SupiEscala getEscala1() {
        if (escala1 == null)
         escala1 = getInstancia();
        return escala1;
    }

    public void setEscala1(SupiEscala escala1) {
        this.escala1 = escala1;
    }

    

   
    
    
   
   public List<Object> getEnfermeirosSeleccionados() {
        return enfermeirosSeleccionados;
    }
    
    public void setEnfermeirosSeleccionados(List<Object> enfermeirosSeleccionados) {
        this.enfermeirosSeleccionados = enfermeirosSeleccionados;
    }
    
    public List<Object> getSupervisoresSeleccionados() {
        return supervisoresSeleccionados;
    }
    
    public void setSupervisoresSeleccionados(List<Object> supervisoresSeleccionados) {
        this.supervisoresSeleccionados = supervisoresSeleccionados;
    }
    
    public int getIdFuncionario() {
        return idFuncionario;
    }
    
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
    public int getIdEnfermeiro() {
        return idEnfermeiro;
    }
    
    public int getIdSeccao() {
        return idSeccao;
    }
    
    public void setIdSeccao(int idSeccao) {
        this.idSeccao = idSeccao;
    }
    
    public int getCargaHorariaMensal() {
        return cargaHorariaMensal;
    }
    
    public void setCargaHorariaMensal(int cargaHorariaMensal) {
        this.cargaHorariaMensal = cargaHorariaMensal;
    }
    
    public void setIdEnfermeiro(int idEnfermeiro) {
        this.idEnfermeiro = idEnfermeiro;
    }
    
    public String getLicenca() {
        if (licenca == null) {
            licenca = new String();
        }
        return licenca;
    }
    
    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public SupiEscala getEliminarEscala() {
         if (eliminarEscala == null) {
            eliminarEscala = new SupiEscala();
           // eliminarEscala.setFkIdTurno(new SupiTurno());
            eliminarEscala.setFkIdSeccao(new SupiSeccao());
        }
        return eliminarEscala;
    }

    public void setEliminarEscala(SupiEscala eliminarEscala) {
        this.eliminarEscala = eliminarEscala;
    }

    public SupiEscala getEscalaP() {
        if (escalaP == null)
      {
         escalaP = getInstancia();
         escalaP.setFkIdSeccao(new SupiSeccao());
        // escalaP.setFkIdTurno(new SupiTurno());
      }
        return escalaP;
    }

    public void setEscalaP(SupiEscala escalaP) {
        this.escalaP = escalaP;
    }
    
    
    
    public SupiEscala getEscala() {
        if (escala == null) {
            escala = new SupiEscala();
            escala.setFkIdSeccao(new SupiSeccao());
        }
        return escala;
    }
    
    public void setEscala(SupiEscala escala) {
        this.escala = escala;
    }
    
    public SupiSeccao getSupiSeccao() {
        if (supiSeccao == null) {
            supiSeccao = new SupiSeccao();
        }
        return supiSeccao;
    }
    
    public void setSupiSeccao(SupiSeccao supiSeccao) {
        this.supiSeccao = supiSeccao;
    }
    
    public RhFuncionario getRhFuncionario() {
        if (rhFuncionario == null) {
            rhFuncionario = new RhFuncionario();
            pessoa = new GrlPessoa();
            rhFuncionario.setFkIdPessoa(pessoa);
        }
        
        return rhFuncionario;
    }
    
    public void setRhFuncionario(RhFuncionario rhFuncionario) {
        this.rhFuncionario = rhFuncionario;
    }
    
    public RhProfissao getRhProfissao() {
        if (rhProfissao == null) {
            rhProfissao = new RhProfissao();
        }
        return rhProfissao;
    }
    
    public void setRhProfissao(RhProfissao rhProfissao) {
        this.rhProfissao = rhProfissao;
    }
    
    public String getNomeCompleto() {
        return nomeCompleto;
    }
    
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    
    public void setPesquisar(boolean pesquisar) {
        this.pesquisar = pesquisar;
    }
    
    public boolean getPesquisar() {
        return this.pesquisar;
    }

    public List<SupiEscala> getEscalaPesquisar() {
        return escalaPesquisar;
    }

    public void setEscalaPesquisar(List<SupiEscala> escalaPesquisar) {
        this.escalaPesquisar = escalaPesquisar;
    }
    
    public void pesquisarEscala ()
    {
     // escalaPesquisar = supiEscalaFacade.findEscala(escalaP);
      escalaPesquisar = new ArrayList<>();
      if (escalaPesquisar.isEmpty())
        Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
      
    }
    
    public List<RhFuncionario> listarEnfermeiros() {
        if (listaEnfermeiros == null) {
            listaEnfermeiros = rhFuncionarioFacade.listaDosEnfermeiros();
        }
        return listaEnfermeiros;
    }
    
    public List<RhFuncionario> listarSupervisores() {
        if (listaSupervisores == null) {
            listaSupervisores = rhFuncionarioFacade.listaDosEnfermeiros();
        }
        return listaSupervisores;
    }

    
    
//    public void create() {
//        supiEnfermeiroFacade.create(enfer);
//    }
    public List<GrlPessoa> listarPessoa() {
        return grlPessoaFacade.findAll();
    }
    
    public List<SupiTurno> listarTurno() {
        return supiTurnoFacade.findAll();
    }
    
    public List<SupiSeccao> listarSeccao() {
        return supiSeccaoFacade.findAll();
    }
    
    public List<RhProfissao> listarProffissao() {
        return rhProfissaoFacade.findAll();
    }
    
    public List<RhFuncionario> listarFuncionario() {
         //escala.getSupiFuncionarioHasEscalaList().get(0).getFkIdEnfermeiro().getFkIdPessoa().getNomeCompleto();        
        return rhFuncionarioFacade.findAll();
    }
    
    /*<p:dataTable id="tabela" var="escala" value="#{supiEnfermeiroSupiBean.listarFuncionarioHasEscala()}" sortMode="multiple"  paginator="true" rows="3"
   selectionMode="single"  selection="#{supiEnfermeiroSupiBean.enfermeirosSeleccionados}" rowKey="#{escala.fkIdEscala.pkIdEscala}">*/

    public List<SupiEscala> listarEscala() {
        return supiEscalaFacade.findAll();
    }
    
    
    
    public String limparPesquisa() {
        return "listarEscala.xhtml?faces-redirect=true";
    }
    
    public void listenerDataEscala(SelectEvent eve) {
        try {
            listaEnfermeiros = rhFuncionarioFacade.listaDosEnfermeiros();
            listaSupervisores = rhFuncionarioFacade.listaDosEnfermeiros();
            
            Date data = escala.getDataEscala();
            
            //Pegando os enfermeiros e supervisores por data            
//            List<RhFuncionario> enfermEscalados = supiEscalaFacade.listarEnfermeirosEscaladosPorData(data);
//            List<RhFuncionario> supervisoresEscalados = supiEscalaFacade.listarSupervisoresEscaladosPorData(data);
//            
            //Se os enfermeiros ja estiverem escalados para esta data entao sao removidos
            //da listagem e nao serao escalados neste dia
//            for (RhFuncionario enferm : enfermEscalados) {
//                if (listaEnfermeiros.contains(enferm))
//                    listaEnfermeiros.remove(enferm);
//                if (listaSupervisores.contains(enferm))
//                    listaSupervisores.remove(enferm);
//            }

            //Se os supervisores ja estiverem escalados para esta data entao sao removidos
            //da listagem e nao serao escalados neste dia
//            for (RhFuncionario superv : supervisoresEscalados) {
//                if (listaSupervisores.contains(superv))
//                    listaSupervisores.remove(superv);
//                if (listaEnfermeiros.contains(superv))
//                    listaEnfermeiros.remove(superv);
//            }
            
            enfermeirosSeleccionados = null;
            supervisoresSeleccionados = null;
        } catch (Exception ex) {
            ex.printStackTrace();;
            Mensagem.erroMsg(ex.getMessage());
        }
        
        System.out.println("entrou gubjyybbbybgybygy");
    }
    
    public void create() {
        
        try {
            
            if (enfermeirosSeleccionados == null || enfermeirosSeleccionados.isEmpty()) {
                throw new Exception("A escala tem que ter pelo menos um Enfermeiro");
            }
            
            if (supervisoresSeleccionados == null || supervisoresSeleccionados.isEmpty()) {
                throw new Exception("A escala tem que ter pelo menos um supervisor");
            }
            for (Object o : enfermeirosSeleccionados) {
                
                if (supervisoresSeleccionados.contains(o)) {
                    throw new Exception("Um Enfermeiro não pode ser escalado como Supervisor na mesma escala");
                }
                
            }

            /* enfer.setFkIdEscalaEnfermeiro(new SupiEscalaEnfermeiro(1));
             System.out.println("OK4"+"\n");*/
            userTransaction.begin();
            supiEscalaFacade.create(escala);
//            for (Object o : enfermeirosSeleccionados) {
//                SupiFuncionarioHasEscala funcHas = new SupiFuncionarioHasEscala();
//                
//                funcHas.setFkIdEscala(escala);
//                funcHas.setFkIdEnfermeiro(rhFuncionarioFacade.find(Integer.parseInt("" + o)));
//                supiFuncionarioHasEscalaFacade.create(funcHas);
//                
//            }
//            for (Object o : supervisoresSeleccionados) {
//                
//                SupiFuncionarioSupervisionaEscala supervisor = new SupiFuncionarioSupervisionaEscala();
//                
//                supervisor.setFkIdEscala(escala);
//                supervisor.setFkIdFuncionario(rhFuncionarioFacade.find(Integer.parseInt("" + o)));
//                supervisionaEscalaFacade.create(supervisor);
//                
//            }

            //supiEnfermeiroFacade.create(enfer);
            userTransaction.commit();
            Mensagem.sucessoMsg("Escala gravada com sucesso");
            escala = null;
            supervisoresSeleccionados = null;
            enfermeirosSeleccionados = null;
            
        } catch (Exception excepcao) {
            Mensagem.erroMsg(excepcao.getMessage());
            Mensagem.erroMsg("Erro! Verifique os dados e tente novamente");
            excepcao.printStackTrace();
            
            try {
                
                userTransaction.rollback();
                
            } catch (Exception ex) {
                Mensagem.erroMsg("Erro no Rollback ");
                
            }
            
        }
    }

    /*public void edit() {
     try {
     userTransaction.begin();
     if (escala.getPkIdEscala()== null) {
     throw new NullPointerException("PK -> NULL");
     }
     int func = escala.getFkIdFuncionario().getPkIdFuncionario();
     enfer.setFkIdFuncionario(new RhFuncionario(func));

     int turno = enfer.getFkIdTurno().getPkIdTurno();
     enfer.setFkIdTurno(new SupiTurno(turno));

     int escala = enfer.getFkIdEscalaEnfermeiro().getPkIdEscalaEnfermeiro();
     enfer.setFkIdEscalaEnfermeiro(new SupiEscalaEnfermeiro(escala));

     // supiEnfermeiroFacade.edit(enfer);
     userTransaction.commit();
     Mensagem.sucessoMsg("Escala editada com sucesso! ");
     } catch (Exception e) {
     try {
     userTransaction.rollback();
     Mensagem.erroMsg(e.toString());
     } catch (IllegalStateException | SecurityException | SystemException ex) {
     Mensagem.erroMsg("Rollback: " + ex.toString());
     }
     }

     enfer = null;

     }*/
    public ArrayList<SelectItem> selectMeses ()
     {
          ArrayList<SelectItem> itens = new ArrayList<>();

          itens.add(new SelectItem(1, "Janeiro"));
          itens.add(new SelectItem(2, "Fevereiro"));
          itens.add(new SelectItem(3, "Março"));
          itens.add(new SelectItem(4, "Abril"));
          itens.add(new SelectItem(5, "Maio"));
          itens.add(new SelectItem(6, "Junho"));
          itens.add(new SelectItem(7, "Julho"));
          itens.add(new SelectItem(8, "Agosto"));
          itens.add(new SelectItem(9, "Setembro"));
          itens.add(new SelectItem(10, "Outubro"));
          itens.add(new SelectItem(11, "Novembro"));
          itens.add(new SelectItem(12, "Dezembro"));

          return itens;
     }
    
    public void eliminarEnfermeiro(int pkIdEnfermeiro) {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        
        try {

            //enfer = supiEnfermeiroFacade.find(pkIdEnfermeiro);
            if (eliminarEscala != null) {

                // supiEnfermeiroFacade.remove(enfer);
                fc.addMessage(null, new FacesMessage("Dados Removidos com sucesso!"));
                
                eliminarEscala = new SupiEscala();
                
            } else {
                fc.addMessage(null, new FacesMessage("Erro ao Eliminar " + ""));
                
            }
            
        } catch (Exception ex) {
            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
        }
        
    }

//    public void salvarEnfermeiro() {
//        enfer.setFkIdFuncionario(new RhFuncionario(idFuncionario));
//        enfer.setNumeroLicenca(licenca);
//        enfer.setFkIdTurno(new SupiTurno(idTurno));
//        enfer.setFkIdEscalaEnfermeiro(new SupiEscalaEnfermeiro(idEscala));
//        
//
//       
//        supiEnfermeiroFacade.create(enfer);
//      
//
//        System.out.println("escala salva com sucesso");
//    }
}
