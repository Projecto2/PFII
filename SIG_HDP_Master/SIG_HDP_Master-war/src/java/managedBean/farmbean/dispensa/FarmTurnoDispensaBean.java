/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.dispensa;

import entidade.AdmsPaciente;
import entidade.FarmDispensa;
import entidade.FarmDispensaHasLoteProduto;
import entidade.FarmLocalArmazenamento;
import entidade.FarmTurno;
import entidade.FarmTurnoDispensa;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedBean.farmbean.configuracoes.FarmConfiguracoesBean;
import managedBean.segbean.SegLoginBean;
import managedBean.segbean.SegcontroloSessaoBean;
import sessao.FarmDispensaFacade;
import sessao.FarmDispensaHasLoteProdutoFacade;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmTurnoDispensaFacade;
import sessao.FarmTurnoFacade;
import util.Constantes;
import util.Mensagem;
import util.RelatorioJasper;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTurnoDispensaBean implements Serializable
{

   @EJB
   private FarmDispensaFacade dispensaFacade;
   @EJB
   private FarmDispensaHasLoteProdutoFacade dispensaHasLoteProdutoFacade;
   @EJB
   private FarmTurnoDispensaFacade turnoDispensaFacade;
   @EJB
   private FarmTurnoFacade turnoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade localArmazenamentoFacade;
   private FarmTurnoDispensa turnoAnterior, turnoActual;
   private ConexaoPostgresSQL conexaoPostgresSQL;
   private List<FarmDispensaHasLoteProduto> listaItensDispensadosPesquisados;
   private List<FarmTurnoDispensa> listaTurnoDispensa;
   /**
    * Creates a new instance of FarmTurnoDispensaBean
    */
   public FarmTurnoDispensaBean()
   {
   }

   /*
    Método que valida os turnos
    SE O TURNO DO DIA ANTERIOR FOI FECHADO
      SE EXISTE TURNO ABERTO HOJE
         REDIRECCIONAR PARA REALIZACAO DE DISPENSAS
      SE NAO REDIRECCIONAR PARA ABRIR TURNO
    SE NAO REDIRECCIONAR PARA FECHAR TURNO ATRASADO
   
    */
   
   public static FarmTurnoDispensaBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTurnoDispensaBean farmTurnoDispensaBean
              = (FarmTurnoDispensaBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTurnoDispensaBean");

      return farmTurnoDispensaBean;
   }
   
   public String validarTurno()
   {
      System.out.println("validando turno...");
      setTurnoAnterior(turnoDispensaFacade.findUltimoTurnoAberto(new FarmTurnoDispensa()));
      if (getTurnoAnterior().getDataHoraFecho() != null)
      {
         if (getTurnoActual().getPkIdTurnoDispensa() != null)
         {
            return redireccionarParaNovaDispensa();
         }
         else
         {
            return redireccionarParaAbrirTurno(getTurnoActual());
         }
      }
      else
      {
         if(getTurnoAnterior().getPkIdTurnoDispensa() == null)
            return redireccionarParaAbrirTurno(getTurnoActual());
            
         if (new SimpleDateFormat("dd-MM-yyyy").format(getTurnoAnterior().getDataAbertura()).
                 equals(new SimpleDateFormat("dd-MM-yyyy").format(new Date())) 
                 && getTurnoAnterior().getFkIdTurno().getDescricao().equals(getPeriodoDoDia()))
         {
            turnoActual = turnoAnterior;
            return redireccionarParaNovaDispensa();
         }

         return redireccionarParaFecharTurno(turnoAnterior);
      }
   }

   public String redireccionarParaFecharTurno(FarmTurnoDispensa turno_a_fechar)
   {
      if (turno_a_fechar.getDataHoraFecho() != null)
      {
         return "/faces/farmVisao/farmDispensa/dispensaFecharTurnoAtrasadoFarm.xhtml?faces-redirect=true";
      }

      return "/faces/farmVisao/farmDispensa/dispensaFecharTurnoAtrasadoFarm.xhtml?faces-redirect=true";
   }

   public String redireccionarParaAbrirTurno(FarmTurnoDispensa turno_a_abrir)
   {
      return "/faces/farmVisao/farmDispensa/dispensaAbrirTurnoFarm.xhtml?faces-redirect=true";
   }

   public String redireccionarParaNovaDispensa()
   {
      return "/faces/farmVisao/farmDispensa/dispensaNovaFarm.xhtml?faces-redirect=true";
   }

   public void abrirTurno()
   {
      if (turnoActual == null)
         turnoActual = getInstanciaTurno();
      
      turnoActual.setDataAbertura(new Date());
      definirPeriodoDoDia(turnoActual);
      System.out.println("turno actual> "+turnoActual);
      
      
      turnoActual.setFkIdFuncionarioQueAbriu(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
      turnoActual.setFkIdFuncionarioQueFechou(null);
      turnoDispensaFacade.create(turnoActual);
      System.out.println("criou turno...");
   }

   public void fecharTurno(FarmTurnoDispensa turno_a_fechar)
   {
      FacesContext context = FacesContext.getCurrentInstance();
      HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
      HttpSession sessao = request.getSession();
      SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");
      turno_a_fechar.setFkIdFuncionarioQueFechou(sessaoActual.getFkIdFuncionario());
      
      turno_a_fechar.setDataHoraFecho(new Date());
      turnoDispensaFacade.edit(turno_a_fechar);
     
      Mensagem.sucessoMsg("O Fecho do Turno foi feito com sucesso.");
      turno_a_fechar = null;
   }
   
   public void definirPeriodoDoDia(FarmTurnoDispensa turno)
   {
      List<FarmTurno> turnos = turnoFacade.findAll();

      if (turnos.size() >= 3)//se tiver pelo menos 3 turnos
      {
         FarmTurno turnoAux = new FarmTurno();
         turnoAux.setDescricao(getPeriodoDoDia());
//         turno.setFkIdLocalDeAtendimento(new FarmLocalArmazenamento(7));
         turno.setFkIdTurno(turnoFacade.findTurno(turnoAux).get(0));
      }
      else
      {
         Mensagem.warnMsg("A aplicação não conseguiu encontrar turnos suficientes para a abertura do período...");
      }
   }
   
   public String getPeriodoDoDia()
   {
         if (Integer.parseInt(new SimpleDateFormat("HH").format(new Date())) < 12)
         {
            return Constantes.FARM_TURNO_MANHA;
         }
         else if (Integer.parseInt(new SimpleDateFormat("HH").format(new Date())) < 18)
         {
            return Constantes.FARM_TURNO_TARDE;
         }
         else
         {
            return Constantes.FARM_TURNO_NOITE;
         }
   }

   public FarmTurnoDispensa getInstanciaTurno()
   {
      FarmTurnoDispensa turnoDispensaAux = new FarmTurnoDispensa();
      turnoDispensaAux.setFkIdLocalDeAtendimento(new FarmLocalArmazenamento(FarmConfiguracoesBean.LOCAL_ATENDIMENTO_UTENTE));
      turnoDispensaAux.setFkIdTurno(new FarmTurno());
      definirPeriodoDoDia(turnoDispensaAux);
      turnoDispensaAux.setFkIdFuncionarioQueFechou(new RhFuncionario());
      return turnoDispensaAux;
   }

   public FarmDispensa getInstanciaDispensa()
   {
      new SegcontroloSessaoBean().validarSessao();
      FarmDispensa dispensa = new FarmDispensa();

      dispensa.setFkIdPaciente(new AdmsPaciente());
      dispensa.setFkIdTurnoDispensa(new FarmTurnoDispensa());
      dispensa.setFkIdFuncionario(new RhFuncionario());
      return dispensa;
   }

   public List<FarmDispensaHasLoteProduto> getItensDispensadosNoTurnoActual()
   {
      List<FarmDispensaHasLoteProduto> itensDispensados = new ArrayList<>();
      FarmDispensa dispensaAux = getInstanciaDispensa();
      dispensaAux.setFkIdTurnoDispensa(turnoActual);
      for (FarmDispensa disp : dispensaFacade.findDispensa(dispensaAux, null, null))
      {
         List<FarmDispensaHasLoteProduto> lotesDispensados = dispensaHasLoteProdutoFacade.findItensDispensa(disp);
         for (FarmDispensaHasLoteProduto item : lotesDispensados)
         {
            itensDispensados.add(item);
         }
      }

      return itensDispensados;
   }

   public List<FarmDispensaHasLoteProduto> getItensDispensadosNoTurnoAnterior()
   {
      List<FarmDispensaHasLoteProduto> itensDispensados = new ArrayList<>();
      FarmDispensa dispensaAux = getInstanciaDispensa();
//      setTurnoAnterior(turnoDispensaFacade.findUltimoTurnoAberto(new FarmTurnoDispensa()));
      dispensaAux.setFkIdTurnoDispensa(turnoAnterior);
      
      for (FarmDispensa disp : dispensaFacade.findDispensa(dispensaAux, null, null))
      {
         List<FarmDispensaHasLoteProduto> lotesDispensados = dispensaHasLoteProdutoFacade.findItensDispensa(disp);
         for (FarmDispensaHasLoteProduto item : lotesDispensados)
         {
            itensDispensados.add(item);
         }
      }

      return itensDispensados;
   }
      
   public void pesquisarItensDispensadosNoTurno(FarmTurnoDispensa turnoDispensa)
   {
      listaItensDispensadosPesquisados = dispensaHasLoteProdutoFacade.findItensDispensadosNoTurno(turnoDispensa);
      if (listaItensDispensadosPesquisados.isEmpty())
         Mensagem.warnMsg("Nenhuma dispensa realizada neste turno.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. "
                 + listaItensDispensadosPesquisados.size() + " registo(s) retornado(s).");
   }

   public void pesquisarTurnos()
   {
      listaTurnoDispensa = turnoDispensaFacade.findTurnosFechadosOrderByIdDesc();
      if (listaTurnoDispensa.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. "
                 + listaTurnoDispensa.size() + " registo(s) retornado(s).");
   }
   /**
    * @return the turnoAnterior
    */
   public FarmTurnoDispensa getTurnoAnterior()
   {
      if (turnoAnterior == null)
         turnoAnterior = getInstanciaTurno();
      return turnoAnterior;
   }

   /**
    * @param turnoAnterior the turnoAnterior to set
    */
   public void setTurnoAnterior(FarmTurnoDispensa turnoAnterior)
   {
      this.turnoAnterior = turnoAnterior;
   }

   /**
    * @return the turnoActual
    */
   public FarmTurnoDispensa getTurnoActual()
   {
      if (turnoActual == null)
         turnoActual = getInstanciaTurno();
      return turnoActual;
   }

   public List<FarmDispensaHasLoteProduto> getListaItensDispensadosPesquisados()
   {
      if(listaItensDispensadosPesquisados == null)
         listaItensDispensadosPesquisados = new ArrayList<>();
      
      return listaItensDispensadosPesquisados;
   }

   public void setListaItensDispensadosPesquisados(List<FarmDispensaHasLoteProduto> listaItensDispensadosPesquisados)
   {
      this.listaItensDispensadosPesquisados = listaItensDispensadosPesquisados;
   }

   public List<FarmTurnoDispensa> getListaTurnoDispensa()
   {
      if(listaTurnoDispensa == null)
         listaTurnoDispensa = new ArrayList<>();
      return listaTurnoDispensa;
   }

   public void setListaTurnoDispensa(List<FarmTurnoDispensa> listaTurnoDispensa)
   {
      this.listaTurnoDispensa = listaTurnoDispensa;
   }

   
   
   /**
    * @param turnoActual the turnoActual to set
    */
   public void setTurnoActual(FarmTurnoDispensa turnoActual)
   {
      this.turnoActual = turnoActual;
   }

   public void imprimirRelatorio(FarmTurnoDispensa turno_a_fechar)
   {
      conexaoPostgresSQL = new ConexaoPostgresSQL();
      Connection conn = conexaoPostgresSQL.getConnection();
      
      HashMap<String, Object> parametrosMap = new HashMap<>();
      parametrosMap.put("descricaoTurno", turno_a_fechar.getFkIdTurno().getDescricao());
      parametrosMap.put("descricaoLocal", localArmazenamentoFacade.find(turno_a_fechar.getFkIdLocalDeAtendimento().getPkIdLocalArmazenamento()).getDescricao());
      parametrosMap.put("idTurnoDispensa", turno_a_fechar.getPkIdTurnoDispensa());
      parametrosMap.put("dataHoraAbertura", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(turno_a_fechar.getDataAbertura()));
      parametrosMap.put("dataHoraFecho",  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(turno_a_fechar.getDataHoraFecho()));
      parametrosMap.put("funcionarioQueAbriu", turno_a_fechar.getFkIdFuncionarioQueAbriu().getFkIdPessoa().getNome() 
              + " " + turno_a_fechar.getFkIdFuncionarioQueAbriu().getFkIdPessoa().getNomeDoMeio() 
              + " " +turno_a_fechar.getFkIdFuncionarioQueAbriu().getFkIdPessoa().getSobreNome());
      
      parametrosMap.put("funcionarioQueFechou", turno_a_fechar.getFkIdFuncionarioQueFechou().getFkIdPessoa().getNome() 
              + " " + turno_a_fechar.getFkIdFuncionarioQueFechou().getFkIdPessoa().getNomeDoMeio() 
              + " " +turno_a_fechar.getFkIdFuncionarioQueFechou().getFkIdPessoa().getSobreNome());
      
      parametrosMap.put("REPORT_CONNECTION", conn);
      
      RelatorioJasper.exportPDFSemListaAutoBackup("farm/fechoDoTurno.jasper", parametrosMap);
      Mensagem.sucessoMsg("O Relatório foi gerado com sucesso.");
   }
   
   public boolean turnoFechado(FarmTurnoDispensa turno)
   {
      return turno.getDataHoraFecho() != null;
   }
}
