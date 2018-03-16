/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmUpdatesFacade extends AbstractFacade<FarmUpdates>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmUpdatesFacade()
   {
      super(FarmUpdates.class);
   }

   public FarmUpdates obterRegistoUpdates()
   {
      List<FarmUpdates> list = this.findAll();
      if (list == null || list.isEmpty())
      {
         return null;
      }
      return list.get(0);
   }

   public Date dataFarmaco()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getFarmaco();
   }

   public Date dataFormaFarmaceutica()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getFormaFarmaceutica();
   }

   public Date dataEfeitoSecundario()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getEfeitoSecundario();
   }

   public Date dataViaAdministracao()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getViaAdministracao();
   }

   public Date dataOutroComponente()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getOutroComponente();
   }

   public Date dataEstadoPedido()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getEstadoPedido();
   }

   public Date dataEstado()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getEstado();
   }

   public Date dataCategoriaDeMedicamento()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getCategoriaMedicamento();
   }

   public Date dataUnidadeDeMedida()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getUnidadeMedida();
   }
   

   public Date dataTipoUnidadeDeMedida()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getTipoUnidadeMedida();
   }
   
   public Date dataTipoNotificacao()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getTipoNotificacao();
   }
   
   public Date dataTipoProduto()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getTipoProduto();
   }
   
   public Date dataTipoQuantidade()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getTipoQuantidade();
   }
   
   public Date dataTurno()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getTurno();
   }
   
   public Date dataLocalArmazenamento()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getLocalArmazenamento();
   }
   
   public Date dataAviso()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getAviso();
   }
   
   public Date dataContraIndicacao()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getContraIndicacao();
   }
   
   public Date dataIndicacao()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getIndicacao();
   }
   
   public Date dataObservacao()
   {
      FarmUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getObservacao();
   }
   
   public void escreverDataActualizacaoFarmacoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setFarmaco(new Date());
         this.create(reg);
      }
      else
      {
         reg.setFarmaco(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoFormaFarmaceuticaTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setFormaFarmaceutica(new Date());
         this.create(reg);
      }
      else
      {
         reg.setFormaFarmaceutica(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoEfeitoSecundarioTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setEfeitoSecundario(new Date());
         this.create(reg);
      }
      else
      {
         reg.setEfeitoSecundario(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoViaAdministracaoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setViaAdministracao(new Date());
         this.create(reg);
      }
      else
      {
         reg.setViaAdministracao(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoOutroComponenteTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setOutroComponente(new Date());
         this.create(reg);
      }
      else
      {
         reg.setOutroComponente(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoEstadoDePedidoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setEstadoPedido(new Date());
         this.create(reg);
      }
      else
      {
         reg.setEstadoPedido(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoEstadoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setEstado(new Date());
         this.create(reg);
      }
      else
      {
         reg.setEstado(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoCategoriaDeMedicamentoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setCategoriaMedicamento(new Date());
         this.create(reg);
      }
      else
      {
         reg.setCategoriaMedicamento(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoUnidadeDeMedidaTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setUnidadeMedida(new Date());
         this.create(reg);
      }
      else
      {
         reg.setUnidadeMedida(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoTipoUnidadeDeMedidaTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setTipoUnidadeMedida(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTipoUnidadeMedida(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoTipoNotificacaoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setTipoNotificacao(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTipoNotificacao(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoTipoLocalArmazenamentoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setTipoLocalArmazenamento(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTipoLocalArmazenamento(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoTipoFornecimentoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setTipoFornecimento(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTipoFornecimento(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoTipoProdutoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setTipoProduto(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTipoProduto(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoTipoQuantidadeTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setTipoQuantidade(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTipoQuantidade(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoTurnoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setTurno(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTurno(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoLocalArmazenamentoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setLocalArmazenamento(new Date());
         this.create(reg);
      }
      else
      {
         reg.setLocalArmazenamento(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoAvisoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setAviso(new Date());
         this.create(reg);
      }
      else
      {
         reg.setAviso(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoContraIndicacaoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setContraIndicacao(new Date());
         this.create(reg);
      }
      else
      {
         reg.setContraIndicacao(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoIndicacaoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setIndicacao(new Date());
         this.create(reg);
      }
      else
      {
         reg.setIndicacao(new Date());
         this.edit(reg);
      }
   }
   
   public void escreverDataActualizacaoObservacaoTabela()
   {
      FarmUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new FarmUpdates();
         reg.setObservacao(new Date());
         this.create(reg);
      }
      else
      {
         reg.setObservacao(new Date());
         this.edit(reg);
      }
   }
}


