/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.GrlUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlUpdatesFacade extends AbstractFacade<GrlUpdates>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public GrlUpdatesFacade()
    {
        super(GrlUpdates.class);
    }

    public GrlUpdates obterRegistoUpdates()
    {
        List<GrlUpdates> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    public Date dataPais()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getPais();
    }

    public Date dataProvincia()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getProvincia();
    }

    public Date dataMunicipio()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getMunicipio();
    }

    public Date dataDistrito()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getDistrito();
    }

    public Date dataComuna()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getComuna();
    }

    public Date dataTipoDocumentoIdentificacao()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoDocumentoIdentificacao();
    }

    public Date dataAreaInterna()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getAreaInterna();
    }

    public Date dataMarcaProduto()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getMarcaProduto();
    }
//    
//    public Date dataSubcategoriasTabela()
//    {
//        AmbCidUpdates reg = obterRegistoUpdates();
//        return reg == null ? null : reg.getSubcategorias();
//    }
//    

    public Date dataCentroHospitalar()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getCentroHospitalar();
    }

    public Date dataDiaSemana()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getDiaSemana();
    }

    public Date dataEspecialidade()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEspecialidade();
    }

    public Date dataEstadoCivil()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoCivil();
    }

    public Date dataSexo()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSexo();
    }

    public Date dataReligiao ()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getReligiao();
    }
    
    public Date dataGrauParentesco()
    {
        GrlUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getGrauParentesco();
    }
    
    public void escreverDataActualizacaoCentroHospitalarTabela()
    {
        GrlUpdates reg = obterRegistoUpdates();

        if (reg == null)
        {
            reg = new GrlUpdates();
            reg.setCentroHospitalar(new Date());
            this.create(reg);
        }
        else
        {
            reg.setCentroHospitalar(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoDiaSemanaTabela()
    {
        GrlUpdates reg = obterRegistoUpdates();

        if (reg == null)
        {
            reg = new GrlUpdates();
            reg.setDiaSemana(new Date());
            this.create(reg);
        }
        else
        {
            reg.setDiaSemana(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoEspecialidadeTabela()
    {
        GrlUpdates reg = obterRegistoUpdates();

        if (reg == null)
        {
            reg = new GrlUpdates();
            reg.setEspecialidade(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEspecialidade(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoEstadoCivilTabela()
    {
        GrlUpdates reg = obterRegistoUpdates();

        if (reg == null)
        {
            reg = new GrlUpdates();
            reg.setEstadoCivil(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoCivil(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoSexoTabela()
    {
        GrlUpdates reg = obterRegistoUpdates();

        if (reg == null)
        {
            reg = new GrlUpdates();
            reg.setSexo(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSexo(new Date());
            this.edit(reg);
        }
    }

    
   public void escreverDataActualizacaoPaisTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setPais(new Date());
         this.create(reg);
      }
      else
      {
         reg.setPais(new Date());
         this.edit(reg);
      }
   }

   public void escreverDataActualizacaoProvinciaTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setProvincia(new Date());
         this.create(reg);
      }
      else
      {
         reg.setProvincia(new Date());
         this.edit(reg);
      }
   }

   public void escreverDataActualizacaoMunicipioTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setMunicipio(new Date());
         this.create(reg);
      }
      else
      {
         reg.setMunicipio(new Date());
         this.edit(reg);
      }
   }

   public void escreverDataActualizacaoDistritoTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setDistrito(new Date());
         this.create(reg);
      }
      else
      {
         reg.setDistrito(new Date());
         this.edit(reg);
      }
   }

   public void escreverDataActualizacaoComunaTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setComuna(new Date());
         this.create(reg);
      }
      else
      {
         reg.setComuna(new Date());
         this.edit(reg);
      }
   }

   public void escreverDataActualizacaoTipoDocumentoIdentificacaoTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setTipoDocumentoIdentificacao(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTipoDocumentoIdentificacao(new Date());
         this.edit(reg);
      }
   }

   public void escreverDataActualizacaoAreaInternaTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setAreaInterna(new Date());
         this.create(reg);
      }
      else
      {
         reg.setAreaInterna(new Date());
         this.edit(reg);
      }
   }

   public void escreverDataActualizacaoMarcaProdutoTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setMarcaProduto(new Date());
         this.create(reg);
      }
      else
      {
         reg.setMarcaProduto(new Date());
         this.edit(reg);
      }
   }

    public void escreverDataActualizacaoReligiaoTabela ()
    {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setReligiao(new Date());
         this.create(reg);
      }
      else
      {
         reg.setReligiao(new Date());
         this.edit(reg);
      }
    }
    
    
   public void escreverDataActualizacaoGrauParentescoTabela()
   {
      GrlUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new GrlUpdates();
         reg.setGrauParentesco(new Date());
         this.create(reg);
      }
      else
      {
         reg.setGrauParentesco(new Date());
         this.edit(reg);
      }
   }


}
