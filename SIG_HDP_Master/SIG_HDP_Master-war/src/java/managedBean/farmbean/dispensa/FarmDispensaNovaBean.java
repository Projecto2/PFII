/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.dispensa;

import entidade.AdmsPaciente;
import entidade.FarmDispensa;
import entidade.FarmDispensaHasLoteProduto;
import entidade.FarmFichaStock;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.GrlDocumentoIdentificacao;
import entidade.GrlTipoDocumentoIdentificacao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.paciente.AdmsPacienteNovoBean;
import managedBean.segbean.SegcontroloSessaoBean;
import sessao.AdmsPacienteFacade;
import sessao.FarmDispensaFacade;
import sessao.FarmDispensaHasLoteProdutoFacade;
import sessao.FarmFichaStockFacade;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmDispensaNovaBean implements Serializable
{

   private static final int MAX = 3;
   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmDispensaFacade dispensaFacade;
   @EJB
   private FarmLocalArmazenamentoFacade localFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteFacade;
   @EJB
   private FarmDispensaHasLoteProdutoFacade dispensaHasLoteProdutoFacade;
   @EJB
   private FarmFichaStockFacade fichaStockFacade;
   @EJB
   private AdmsPacienteFacade admsPacienteFacade;

   private FarmDispensa dispensa;
   private String descricaoProdutoPesquisa = "";
   private List<FarmDispensaHasLoteProduto> itensDispensa;
   private List<FarmLoteProdutoHasLocalArmazenamento> itensDisponiveis;
   private List<FarmDispensaHasLoteProduto> itensDispensados;

   private FarmLoteProduto itemAuxiliar;
   private boolean renderizarTabela = false;

   FacesContext context = FacesContext.getCurrentInstance();
   HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
   HttpSession sessao = request.getSession();

   FarmTurnoDispensaBean farmTurnoDispensaBean = (FarmTurnoDispensaBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmTurnoDispensaBean");
   /**
    * Creates a new instance of FarmDispensaNovaBean
    */
   public FarmDispensaNovaBean()
   {

   }

   public void pesquisarLotesProduto()
   {
      itensDisponiveis = new ArrayList<>();
      itensDisponiveis = loteFacade.findProdutosNoLocal(dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento(), descricaoProdutoPesquisa);
      if (itensDisponiveis.isEmpty())
         Mensagem.warnMsg("Nenhum Item Corresponde à pesquisa.");

      eliminarDuplicados();
   }

   public FarmDispensa getInstanciaDispensa()
   {
      new SegcontroloSessaoBean().validarSessao();
      dispensa = new FarmDispensa();
      
      dispensa.setFkIdPaciente(AdmsPacienteNovoBean.getInstanciaBean().getInstanciaPaciente()); 
      dispensa.getFkIdPaciente().getFkIdPessoa().getGrlDocumentoIdentificacaoList().add(0, new GrlDocumentoIdentificacao());
      dispensa.getFkIdPaciente().getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());

      dispensa.setFkIdTurnoDispensa(farmTurnoDispensaBean.getTurnoActual());      
      SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");
      dispensa.setFkIdFuncionario(sessaoActual.getFkIdFuncionario());
      return dispensa;
   }

   public void pesquisarProdutos()
   {
      setItensDisponiveis(loteFacade.findProdutosNoLocal(dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento(), descricaoProdutoPesquisa));
   }

   public List<FarmLoteProdutoHasLocalArmazenamento> complete(String query)
   {
      List<FarmLoteProdutoHasLocalArmazenamento> todosLotes = loteFacade.findProdutosNoLocal(dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento(), "");
      List<FarmLoteProdutoHasLocalArmazenamento> lotesFiltrados = new ArrayList<>();

      for (int i = 0; i < todosLotes.size(); i++)
      {
         FarmLoteProdutoHasLocalArmazenamento item = todosLotes.get(i);
         if (lotesFiltrados.size() < MAX && (item.getFkIdLoteProduto().getFkIdProduto().getDescricao().toLowerCase().startsWith(query) || item.getFkIdLoteProduto().getFkIdProduto().getDescricao().startsWith(query)))
         {
            lotesFiltrados.add(item);
         }
      }
      return lotesFiltrados;
   }

   public String criar()
   {
      try
      {
         userTransaction.begin();

         if (itensDispensa.isEmpty())
         {
            Mensagem.warnMsg("Não seleccionou nenhum item para dispensa. Por favor, verifique.");
            throw new Exception();
         }
         
         System.out.println("pesquisando paciente com np: "+dispensa.getFkIdPaciente().getNumeroPaciente());
         List<AdmsPaciente> listaPacientes = admsPacienteFacade.findPaciente(dispensa.getFkIdPaciente(), 1, null, null);
         
         if(!listaPacientes.isEmpty())
         {
            dispensa.setFkIdPaciente(listaPacientes.get(0));
            System.out.println("encontrou: "+dispensa.getFkIdPaciente());  
         }
         else
         {
            Mensagem.warnMsg("Não seleccionou nenhum item para dispensa. Por favor, verifique.");
            throw new Exception();
         }
         
         dispensa.setDataHora(new Date());
         dispensaFacade.create(dispensa);

         for (FarmDispensaHasLoteProduto item : itensDispensa)
         {
            FarmLoteProdutoHasLocalArmazenamento loteArmazenado = loteFacade.findLoteProdutoNoLocal(dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento(), item.getFkIdLoteProduto());
            int qtdRestante = loteArmazenado.getQuantidadeStock() - item.getQuantidade();
            if (qtdRestante >= 0)
            {
               loteArmazenado.setQuantidadeStock(qtdRestante);
               loteFacade.edit(loteArmazenado);
               item.setFkIdDispensa(dispensa);
               dispensaHasLoteProdutoFacade.create(item);

               FarmFichaStock fichaParaOrigem = new FarmFichaStock();

               fichaParaOrigem.setDataMovimento(new Date());
               AdmsPaciente paciente = admsPacienteFacade.find(dispensa.getFkIdPaciente().getPkIdPaciente());

               fichaParaOrigem.setOrigemOuDestino("Dispensa ao Utente com nº de Processo "
                       + paciente.getNumeroPaciente());
               fichaParaOrigem.setFkIdLoteProduto(loteArmazenado.getFkIdLoteProduto());
               fichaParaOrigem.setFkIdLocalArmazenamento(dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento());
               fichaParaOrigem.setEntradas(0);
               fichaParaOrigem.setSaidas(item.getQuantidade());
               fichaParaOrigem.setQuantidadeRestante(qtdRestante);
               fichaParaOrigem.setFkIdFuncionario(dispensa.getFkIdFuncionario());
               fichaStockFacade.create(fichaParaOrigem);
            }
            else
            {
               Mensagem.warnMsg("A quantidade de " + item.getFkIdLoteProduto().getFkIdProduto().getDescricao() + " que deseja retirar não está disponível no stock de " + dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento().getDescricao() + ".");
               throw new Exception();
            }
         }
         userTransaction.commit();

         Mensagem.sucessoMsg("Dispensa efectuada com sucesso!");
         limparCampos();
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao processar a Dispensa. Tente novamente.");
         try
         {
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }
      return null;
   }

   public String definirTurno()
   {
      FarmTurnoDispensaBean.getInstanciaBean().abrirTurno();
      System.out.println("difinindo turno...");
      dispensa = getInstanciaDispensa();
      System.out.println("instanciou dispensa");
      dispensa.setFkIdTurnoDispensa(FarmTurnoDispensaBean.getInstanciaBean().getTurnoActual());
      System.out.println("FarmTurnoDispensaBean.getInstanciaBean().getTurnoActual(): " + FarmTurnoDispensaBean.getInstanciaBean().getTurnoActual().getFkIdLocalDeAtendimento());
      
      List<FarmLocalArmazenamento> locais = localFacade.findAll();
      for (FarmLocalArmazenamento aux2 : locais)
      {
         System.out.println("verificando: "+aux2.getDescricao() + " " + aux2.getPkIdLocalArmazenamento());
         if (aux2.getPkIdLocalArmazenamento() == dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento().getPkIdLocalArmazenamento())
            dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento().setDescricao(aux2.getDescricao());
      }
      
      System.out.println("local de armazenamento: "+dispensa.getFkIdTurnoDispensa().getFkIdLocalDeAtendimento().getDescricao());
      return "/faces/farmVisao/farmDispensa/dispensaNovaFarm.xhtml?faces-redirect=true";
   }

   public void adicionar()
   {
      FarmDispensaHasLoteProduto item = new FarmDispensaHasLoteProduto();
      item.setFkIdLoteProduto(itemAuxiliar);
      item.setFkIdDispensa(new FarmDispensa());
      item.setQuantidade(1);
      itensDispensa.add(item);
      eliminarDuplicados();
      itemAuxiliar = new FarmLoteProduto();
   }

   public void eliminarDuplicados()
   {
      for (FarmDispensaHasLoteProduto aux : itensDispensa)
      {
         FarmLoteProdutoHasLocalArmazenamento aux2 = eliminarAux(aux.getFkIdLoteProduto());

         if (aux2 != null && aux2.getFkIdLoteProduto().getNumeroLote().equals(aux.getFkIdLoteProduto().getNumeroLote()))
         {
            itensDisponiveis.remove(aux2);

         }
      }
   }

   public FarmLoteProdutoHasLocalArmazenamento eliminarAux(FarmLoteProduto lote)
   {
      for (FarmLoteProdutoHasLocalArmazenamento aux : itensDisponiveis)
      {
         if (aux.getFkIdLoteProduto() == lote)
            return aux;
      }

      return null;
   }

   public void remover(FarmDispensaHasLoteProduto item)
   {
      itensDispensa.remove(item);
      pesquisarLotesProduto();
      eliminarDuplicados();
   }

   public void limparCampos()
   {
      dispensa.setFkIdPaciente(new AdmsPaciente());
      dispensa.setPrescricaoMedica(null);
      itensDispensa = new ArrayList<>();
      itensDisponiveis = new ArrayList<>();
      descricaoProdutoPesquisa = "";
   }

   /**
    * @return the dataHoje
    */
   public Date getDataHoje()
   {
      return new Date();
   }

   /**
    * @return the dispensa
    */
   public FarmDispensa getDispensa()
   {
      if (dispensa == null)
         dispensa = getInstanciaDispensa();
      return dispensa;
   }

   /**
    * @param dispensa the dispensa to set
    */
   public void setDispensa(FarmDispensa dispensa)
   {
      this.dispensa = dispensa;
   }

   /**
    * @return the itensDispensa
    */
   public List<FarmDispensaHasLoteProduto> getItensDispensa()
   {
      if (itensDispensa == null)
         itensDispensa = new ArrayList<>();
      return itensDispensa;
   }

   /**
    * @param itensDispensa the itensDispensa to set
    */
   public void setItensDispensa(List<FarmDispensaHasLoteProduto> itensDispensa)
   {
      this.itensDispensa = itensDispensa;
   }

   /**
    * @return the descricaoProdutoPesquisa
    */
   public String getDescricaoProdutoPesquisa()
   {
      return descricaoProdutoPesquisa;
   }

   /**
    * @param descricaoProdutoPesquisa the descricaoProdutoPesquisa to set
    */
   public void setDescricaoProdutoPesquisa(String descricaoProdutoPesquisa)
   {
      this.descricaoProdutoPesquisa = descricaoProdutoPesquisa;
   }

   /**
    * @return the itensDisponiveis
    */
   public List<FarmLoteProdutoHasLocalArmazenamento> getItensDisponiveis()
   {
      return itensDisponiveis;
   }

   /**
    * @param itensDisponiveis the itensDisponiveis to set
    */
   public void setItensDisponiveis(List<FarmLoteProdutoHasLocalArmazenamento> itensDisponiveis)
   {
      this.itensDisponiveis = itensDisponiveis;
   }

   /**
    * @return the itemAuxiliar
    */
   public FarmLoteProduto getItemAuxiliar()
   {
      return itemAuxiliar;
   }

   /**
    * @param itemAuxiliar the itemAuxiliar to set
    */
   public void setItemAuxiliar(FarmLoteProduto itemAuxiliar)
   {
      this.itemAuxiliar = itemAuxiliar;
   }

   /**
    * @return the itensDispensados
    */
   public List<FarmDispensaHasLoteProduto> getItensDispensados()
   {
      if (itensDispensados == null)
         itensDispensados = new ArrayList<>();
      return itensDispensados;
   }

   /**
    * @param itensDispensados the itensDispensados to set
    */
   public void setItensDispensados(List<FarmDispensaHasLoteProduto> itensDispensados)
   {
      this.itensDispensados = itensDispensados;
   }

   /**
    * @return the renderizarTabela
    */
   public boolean isRenderizarTabela()
   {
      return renderizarTabela;
   }

   /**
    * @param renderizarTabela the renderizarTabela to set
    */
   public void setRenderizarTabela(boolean renderizarTabela)
   {
      this.renderizarTabela = renderizarTabela;
   }
}
