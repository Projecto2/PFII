/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.tabelasAuxiliares;

import entidade.AdmsPaciente;
import entidade.FarmAviso;
import entidade.FarmCategoriaMedicamento;
import entidade.FarmContraIndicacao;
import entidade.FarmEfeitoSecundario;
import entidade.FarmEstadoPedido;
import entidade.FarmFarmaco;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmIndicacao;
import entidade.FarmLocalArmazenamento;
import entidade.FarmObservacao;
import entidade.FarmOutroComponente;
import entidade.FarmTipoFornecimento;
import entidade.FarmTipoLocalArmazenamento;
import entidade.FarmTipoProduto;
import entidade.FarmTipoQuantidade;
import entidade.FarmTipoUnidadeMedida;
import entidade.FarmTurno;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.GrlDocumentoIdentificacao;
import entidade.GrlFornecedor;
import entidade.GrlMarcaProduto;
import entidade.GrlTipoDocumentoIdentificacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import managedBean.grlbean.GrlFornecedorNovoBean;
import managedBean.grlbean.GrlPessoaBean;
import sessao.AdmsPacienteFacade;
import sessao.FarmAvisoFacade;
import sessao.FarmCategoriaMedicamentoFacade;
import sessao.FarmContraIndicacaoFacade;
import sessao.FarmEfeitoSecundarioFacade;
import sessao.FarmEstadoPedidoFacade;
import sessao.FarmFarmacoFacade;
import sessao.FarmFormaFarmaceuticaFacade;
import sessao.FarmIndicacaoFacade;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmObservacaoFacade;
import sessao.FarmOutroComponenteFacade;
import sessao.FarmTipoFornecimentoFacade;
import sessao.FarmTipoLocalArmazenamentoFacade;
import sessao.FarmTipoProdutoFacade;
import sessao.FarmTipoQuantidadeFacade;
import sessao.FarmTipoUnidadeMedidaFacade;
import sessao.FarmTurnoFacade;
import sessao.FarmUnidadeMedidaFacade;
import sessao.FarmViaAdministracaoFacade;
import sessao.GrlFornecedorFacade;
import sessao.GrlMarcaProdutoFacade;
import util.Constantes;

/**
 *
 * @author elisangela
 */
@ManagedBean
@RequestScoped
public class FarmListasUteisBean implements Serializable
{

   @EJB
   private FarmTurnoFacade turnoFacade;
   @EJB
   private FarmTipoProdutoFacade tipoProdutoFacade;
   @EJB
   private FarmFormaFarmaceuticaFacade formaFarmaceuticaFacade;
   @EJB
   private FarmViaAdministracaoFacade viaAdminFacade;
   @EJB
   private GrlMarcaProdutoFacade marcaFacade;
   @EJB
   private GrlFornecedorFacade fornecedorFacade;
   @EJB
   private FarmTipoFornecimentoFacade tipoFornecimentoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade locaisArmazenamentoFacade;
   @EJB
   private FarmTipoLocalArmazenamentoFacade tipoLocaisArmazenamentoFacade;
   @EJB
   private FarmUnidadeMedidaFacade unidadeMedidaFacade;
   @EJB
   private FarmEfeitoSecundarioFacade efeitoSecundraioFacade;
   @EJB
   private FarmFarmacoFacade farmacoFacade;
   @EJB
   private FarmOutroComponenteFacade outroComponenteFacade;
   @EJB
   private FarmTipoQuantidadeFacade tipoQuantidadeFacade;
   @EJB
   private FarmTipoUnidadeMedidaFacade tipoUnidadeMedidaFacade;
   @EJB
   private AdmsPacienteFacade pacienteFacade;
   @EJB
   private FarmCategoriaMedicamentoFacade categoriaMedicamentoFacade;
   @EJB
   private FarmAvisoFacade avisoFacade;
   @EJB
   private FarmContraIndicacaoFacade contraIndicacaoFacade;
   @EJB
   private FarmIndicacaoFacade indicacaoFacade;
   @EJB
   private FarmObservacaoFacade observacaoFacade;
   @EJB
   private FarmEstadoPedidoFacade estadoPedidoFacade;
   private AdmsPaciente paciente;

   /**
    * Creates a new instance of FarmListasUteisBean
    */
   public FarmListasUteisBean()
   {
   }

   public ArrayList<SelectItem> getTodosTiposLocalArmazenamento()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmTipoLocalArmazenamento e : tipoLocaisArmazenamentoFacade.findAll())
      {
         itens.add(new SelectItem(e.getPkIdTipoLocalArmazenamento(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodosTiposQuantidade()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmTipoQuantidade e : tipoQuantidadeFacade.findAll())
      {
         itens.add(new SelectItem(e.getPkIdTipoQuantidade(), e.getDescricao()));
      }
      return itens;
   }
   
   public ArrayList<SelectItem> getTodosTiposUnidadeMedida()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmTipoUnidadeMedida e : tipoUnidadeMedidaFacade.findAll())
      {
         itens.add(new SelectItem(e.getPkIdTipoUnidadeMedida(), e.getDescricao()));
      }
      
      return itens;
   }

   public ArrayList<SelectItem> getTodosTiposDeProduto()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmTipoProduto e : tipoProdutoFacade.findAll())
      {
         itens.add(new SelectItem(e.getPkIdTipoProduto(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodosTurnosDispensa()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmTurno e : turnoFacade.findTurno(new FarmTurno()))
      {
         itens.add(new SelectItem(e.getPkIdTurno(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodasViasAdmin()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmViaAdministracao e : viaAdminFacade.findViaAdministracao(new FarmViaAdministracao()))
      {
         itens.add(new SelectItem(e.getPkIdViaAdministracao(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodasMarcas()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();

      for (GrlMarcaProduto e : marcaFacade.findMarcaProduto(new GrlMarcaProduto()))
      {
         itens.add(new SelectItem(e.getPkIdMarca(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodasFormasFarmaceuticas()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmFormaFarmaceutica e : formaFarmaceuticaFacade.findFormaFarmaceutica(new FarmFormaFarmaceutica()))
      {
         itens.add(new SelectItem(e.getPkIdFormaFarmaceutica(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodosTipoFornecimento()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmTipoFornecimento e : tipoFornecimentoFacade.findAll())
      {
         itens.add(new SelectItem(e.getPkIdTipoFornecimento(), e.getDescricaoTipoFornecimento()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodasUnidadeesMedida()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmUnidadeMedida e : unidadeMedidaFacade.findUnidadeMedida(FarmUnidadeDeMedidaBean.getInstanciaBean().getInstanciaUnidade()))
      {
         itens.add(new SelectItem(e.getPkIdUnidadeMedida(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodosFornecedores()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();

      for (GrlFornecedor e : fornecedorFacade.findFornecedor(GrlFornecedorNovoBean.getInstanciaBean().getInstanciaFornecedor()))
      {
         itens.add(new SelectItem(e.getPkIdFornecedor(), e.getFkIdInstituicao().getDescricao()));
      }
      return itens;
   }

   public List<FarmEfeitoSecundario> getTodosEfeitosSecundarios()
   {
      return efeitoSecundraioFacade.findEfeitoSecundario(new FarmEfeitoSecundario());
   }

   public ArrayList<SelectItem> getTodosEstadosDePedido()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmEstadoPedido e : estadoPedidoFacade.findEstadoPedido(new FarmEstadoPedido()))
      {
         itens.add(new SelectItem(e.getPkIdEstadoPedido(), e.getDescricao()));
      }
      return itens;
   }

   public List<FarmFarmaco> getTodosFarmacos()
   {
      return farmacoFacade.findFarmaco(new FarmFarmaco());
   }

   public List<FarmOutroComponente> getTodosOutrosComponentes()
   {
      return outroComponenteFacade.findOutroComponente(new FarmOutroComponente());
   }

   public List<FarmAviso> getTodosAvisos()
   {
      return avisoFacade.findAviso(new FarmAviso());
   }

   public List<FarmObservacao> getTodasObservacoes()
   {
      return observacaoFacade.findObservacao(new FarmObservacao());
   }

   public List<FarmIndicacao> getTodasIndicacoes()
   {
      return indicacaoFacade.findIndicacao(new FarmIndicacao());
   }

   public List<FarmContraIndicacao> getTodasContraIndicacoes()
   {
      return contraIndicacaoFacade.findIndicacao(new FarmContraIndicacao());
   }

   public ArrayList<SelectItem> getLocaisArmazenamentoArmazem()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_ARMAZEM));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getAbreviatura()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getLocaisArmazenamento()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getLocaisArmazenamentoFarmacia()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_FARMACIA));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }
      return itens;
   }

   public AdmsPaciente getInstanciaPaciente()
   {
      AdmsPaciente pacienteInstancia = new AdmsPaciente(null, "");
      pacienteInstancia.setFkIdPessoa(new GrlPessoaBean().getInstanciaPessoa());
      return pacienteInstancia;
   }

   public ArrayList<SelectItem> getTodosPacientes()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();
      if (paciente == null)
      {
         paciente = getInstanciaPaciente();
         paciente.getFkIdPessoa().setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());
         paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().add(0, new GrlDocumentoIdentificacao());
         paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());
      }
      for (AdmsPaciente e : pacienteFacade.findPaciente(paciente, null, null, null))
      {
         itens.add(new SelectItem(e.getPkIdPaciente(), e.getFkIdPessoa().getNome() + " " + e.getFkIdPessoa().getSobreNome() + " - " + e.getNumeroPaciente()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getTodasCategorias()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmCategoriaMedicamento aux = new FarmCategoriaMedicamento();
      aux.setFkIdCategoriaSuper(new FarmCategoriaMedicamento());
      for (FarmCategoriaMedicamento e : categoriaMedicamentoFacade.findCategoriaOrderByDescricao(aux))
      {
         itens.add(new SelectItem(e.getPkIdCategoriaMedicamento(), e.getCapitulo() + " - " + e.getDescricao()));
      }
      return itens;
   }
   
   public ArrayList<SelectItem> getTodasCategoriasOrderByCapitulo()
   {
      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmCategoriaMedicamento aux = new FarmCategoriaMedicamento();
      aux.setFkIdCategoriaSuper(new FarmCategoriaMedicamento());
      for (FarmCategoriaMedicamento e : categoriaMedicamentoFacade.findCategoriaOrderByCapitulo(aux))
      {
         itens.add(new SelectItem(e.getPkIdCategoriaMedicamento(), e.getCapitulo() + " - " + e.getDescricao()));
      }
      return itens;
   }
}
