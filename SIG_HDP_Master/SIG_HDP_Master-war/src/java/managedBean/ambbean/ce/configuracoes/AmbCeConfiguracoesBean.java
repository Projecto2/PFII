/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.configuracoes;

import entidade.AmbCeConfiguracoes;
import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import entidade.RhProfissao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.ambbean.ce.consultas.AmbConsultaCriarBean;
import managedBean.ambbean.ce.consultas.AmbConsultaListarBean;
import managedBean.segbean.SegLoginBean;
import sessao.AmbCeConfiguracoesFacade;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;
import sessao.SegContaFacade;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbCeConfiguracoesBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;    
    
    @EJB
    private AmbCeConfiguracoesFacade ambCeConfiguracoesFacade;
    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;
    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;
    @EJB
    private RhProfissaoFacade rhProfissaoFacade; 
    @EJB
    private SegContaFacade segContaFacade;
    
    private AmbCeConfiguracoes ambCeConfiguracoes;
    
    private GrlCentroHospitalar grlCentroHospitalar;
    private GrlEspecialidade especialidadePreferencial;
    
    private SegConta segConta;
    
    private boolean chpSOM
                  , chpSOMRendered
                  , especialidadeSOM;
    
    private int idConta
              , idCentroHospitalar
              , idEspecialidade;
    
    /**
     * Creates a new instance of AmbCeConfiguracoesBean
     */
    public AmbCeConfiguracoesBean()
    {
//        teste();
//        chpSOM = true;
//        chpSOMRendered = true;
//        idCentroHospitalar = getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar();
    }

    public void init()
    {
//        chpSOM = true;
        chpSOMRendered = true;
//        especialidadeSOM = true;
//        idCentroHospitalar = getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar();
        //System.err.println("0: AmbCidConfiguracoesBean.inicializar()");
        
//        initSegConta();
//        initEspecialidadePreferencial();
    }    
   
    public String initNomeModulo()
    {
        return util.amb.Defs.NOME_MODULO;
    }    
    
    public static AmbCeConfiguracoesBean getInstanciaBean()
    {
        return (AmbCeConfiguracoesBean) GeradorCodigo.getInstanciaBean("ambCeConfiguracoesBean");
    }    
    
    public static AmbConsultaCriarBean getInstanciaAmbConsultaCriarBean()
    {
        return (AmbConsultaCriarBean) GeradorCodigo.getInstanciaBean("ambConsultaCriarBean");
    }  
            
    public static AmbConsultaListarBean getInstanciaAmbConsultaListarBean()
    {
        return (AmbConsultaListarBean) GeradorCodigo.getInstanciaBean("ambConsultaListarBean");
    }

    public AmbCeConfiguracoes getAmbCeConfiguracoes()
    {
        if (ambCeConfiguracoes == null)
        {
            ambCeConfiguracoes = new AmbCeConfiguracoes();
        }
        return ambCeConfiguracoes;
    }

    public void setAmbCeConfiguracoes(AmbCeConfiguracoes ambCeConfiguracoes)
    {
        this.ambCeConfiguracoes = ambCeConfiguracoes;
    }

    public GrlCentroHospitalar getGrlCentroHospitalar()
    {
        if (grlCentroHospitalar == null)
        {
            grlCentroHospitalar = new GrlCentroHospitalar();
        }
        return grlCentroHospitalar;
    }

    public void setGrlCentroHospitalar(GrlCentroHospitalar grlCentroHospitalar)
    {
        this.grlCentroHospitalar = grlCentroHospitalar;
    }
    
    public GrlEspecialidade getEspecialidadePreferencial()
    {
        return especialidadePreferencial;
    }

    public void setEspecialidadePreferencial(GrlEspecialidade especialidadePreferencial)
    {
        this.especialidadePreferencial = especialidadePreferencial;
    }    
    
    public SegConta getSegConta()
    {
        if (segConta == null)
        {
            segConta = obterContaDaCorrenteSessao();
        }
        return segConta;
    }

    public void setSegConta(SegConta segConta)
    {
        this.segConta = segConta;
    }
    
    public boolean isChpSOM()
    {
//        List<GrlCentroHospitalar> listCentroHospitalar = ambCeConfiguracoesFacade.findAllOrderBYPkId();
//        chpSOM = (listCentroHospitalar.size() > 1);
//                prioridadeDoencaSOMrendered = (listAmbCidDoencasPrioridades.size() > 1);
////System.err.println("0: AmbCidHipoteseDiagnosticoAbstract.isPrioridadeDoencaSOMrendered()\tidDoencasPrioridadesPreferencial: " + this.idDoencasPrioridadesPreferencial);
        return chpSOM;
    }

    public void setChpSOM(boolean chpSOM)
    {
        this.chpSOM = chpSOM;
    }        
    
//   public void definirLocalOrigemPedido(String localString)
//   {
//      getPedido();
//      System.out.println("local a definir: " + localString);
//      FarmLocalArmazenamento local = getInstanciaLocal();
//      local.setDescricao(localString);
//
//      if (!locaisArmazenamentoFacade.findLocalArmazenamento(local).isEmpty())
//         pedido.setFkLocalOrigemPedido(locaisArmazenamentoFacade.findLocalArmazenamento(local).get(0));
//
//      System.out.println("origem do pedido definida como: " + pedido.getFkLocalOrigemPedido());
//   }    
    
   public List<GrlCentroHospitalar> teste()
   {
//      FarmLocalArmazenamento local findAllByCentroContaRendered= getInstanciaLocal();
//      local.setDescricao(localString);
//System.err.print("ambCeConfiguracoes.teste()\nCentro de Proveniência: " + findAllByCentroConta(this.ambCeConfiguracoesFacade.findAllByIdConta(idConta).getIdCentroHospitalarProveniencia(), this.ambCeConfiguracoesFacade.findAllByIdConta(idConta).getIdConta()).toString());
//       System.err.print("ambCeConfiguracoes.teste()\nCentro de Proveniência: " + ambCeConfiguracoesFacade.findAll().toString());
//System.err.print("ambCeConfiguracoes.teste()\nCentro de Proveniência: " + findAllByCentroContaRendered().getCodigoCentro());
//System.err.print("1:ambCeConfiguracoes.teste()\nCentro de Proveniência: " + findAllByCentroConta(findAllByCentroContaRendered().getPkIdCentro(), obterContaDaCorrenteSessao().getPkIdConta()).toString());       
//System.err.print("2:ambCeConfiguracoes.teste()\nCentro de Proveniência: " + findAllByCentroConta(findAllByCentroContaRendered().getPkIdCentro(), obterContaDaCorrenteSessao().getPkIdConta()).get(1));
      if (!findAllByCentroConta(findAllByCentroContaRendered().getPkIdCentro(), obterContaDaCorrenteSessao().getPkIdConta()).isEmpty())
          findAllByCentroConta(findAllByCentroContaRendered().getPkIdCentro(), obterContaDaCorrenteSessao().getPkIdConta());

      return findAllByCentroConta(findAllByCentroContaRendered().getPkIdCentro(), obterContaDaCorrenteSessao().getPkIdConta());
   }     
    
    public GrlCentroHospitalar findAllByCentroContaRendered(/*int idCentro, int idConta*/)
    {
        GrlCentroHospitalar resultado = new GrlCentroHospitalar();
        
        for (GrlCentroHospitalar gch : grlCentroHospitalarFacade.findAll())
        {
//             if (idCentro >= 0)
//             {
                for (AmbCeConfiguracoes acc : ambCeConfiguracoesFacade.findAll())
                {
                     for (SegConta sc : segContaFacade.findAll())
                     {   
//                          if (gch.getPkIdCentro() == acc.getIdCentroHospitalarProveniencia())
//                          {
                              if (sc.getPkIdConta().equals(acc.getIdConta()))
                              {
                                  resultado = gch;
//System.err.print("ambCeConfiguracoes.findAllByCentroConta()\nCentro de Proveniência: " + gch.getFkIdInstituicao().getDescricao());
//System.err.print("ambCeConfiguracoes.findAllByCentroConta()\nConta de Utilizador: "    + sc.getFkIdFuncionario().getFkIdPessoa().getNome());
                              }
//                          }
                    }
                }
//             }
//             else 
//             {
//                 if (gch.getPkIdCentro() == 1)
//                     resultado = gch;
//             }
             
        }
        return resultado;
    }     
    
    public List<GrlCentroHospitalar> findAllByCentroConta(int idCentro, int idConta)
    {
        List<GrlCentroHospitalar> resultado = new ArrayList<>();
        
        for (GrlCentroHospitalar gch : grlCentroHospitalarFacade.findAll())
        {
             if (!ambCeConfiguracoesFacade.findAll().isEmpty())
             {
                 for (AmbCeConfiguracoes acc : ambCeConfiguracoesFacade.findAll())
                 {
                      for (SegConta sc : segContaFacade.findAll())
                      {   
//                           if (gch.getPkIdCentro() == acc.getIdCentroHospitalarProveniencia())
//                           {
//                               if (sc.getPkIdConta().equals(acc.getIdConta()))
//                               {
                                   if (gch.getPkIdCentro() == idCentro)
                                   {
                                       if (sc.getPkIdConta().equals(idConta))
                                       {
                                           resultado.add(gch);
//System.err.print("ambCeConfiguracoes.findAllByCentroConta()\nCentro de Proveniência: " + gch.getFkIdInstituicao().getDescricao());
//System.err.print("ambCeConfiguracoes.findAllByCentroConta()\nConta de Utilizador: "    + sc.getFkIdFuncionario().getFkIdPessoa().getNome());
                                        }
                                   }
                                   else
                                   {
                                       if (gch.getPkIdCentro() != idCentro)
                                           if (!sc.getPkIdConta().equals(idConta))
                                               if (!resultado.contains(gch))
                                                   resultado.add(gch);
                                   }
//                                }
//                           }
//                                   else
//                                   {
//                                       if (gch.getPkIdCentro() != idCentro)
////                                           if (gch.getPkIdCentro() != acc.getIdCentroHospitalarProveniencia())
//                                               if (!sc.getPkIdConta().equals(idConta))
////                                                   if (!sc.getPkIdConta().equals(acc.getIdConta()))
//                                                       if (!resultado.contains(gch))
//                                                           resultado.add(gch);
//                                   }
                     }
                 }
             }
             else
             {
                 resultado.add(gch);
             }
        }
        return resultado;
    }    
    
    public boolean isChpSOMRendered()
    {
//        findAllByCentroConta(getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar(), obterContaDaCorrenteSessao().getPkIdConta())
        List<GrlCentroHospitalar> listCentroHospitalar = findAllByCentroConta(getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar(), obterContaDaCorrenteSessao().getPkIdConta());//findAllOrderBYCentro
        chpSOMRendered = (listCentroHospitalar.size() > 1); 
        
        return chpSOMRendered;
    }

    public void setChpSOMRendered(boolean chpSOMRendered)
    {
        this.chpSOMRendered = chpSOMRendered;
    }
    
    public boolean isEspecialidadeSOM()
    {
        List<GrlEspecialidade> listEspecialidade = ambCeConfiguracoesFacade.findAllByIdEspecialidade(getInstanciaAmbConsultaListarBean().getCodigoEspecialidade());
        especialidadeSOM = (listEspecialidade.size() > 1);        
        
        return especialidadeSOM;
    }

    public void setEspecialidadeSOM(boolean especialidadeSOM)
    {
        this.especialidadeSOM = especialidadeSOM;
    }

    public int getIdCentroHospitalar()
    {
        return idCentroHospitalar;
    }   
    
    public void setIdCentroHospitalar(int idCentroHospitalar)
    {
        this.idCentroHospitalar = idCentroHospitalar;
    }

    public int getIdEspecialidade()
    {
        return idEspecialidade;
    }

    public void setIdEspecialidade(int idEspecialidade)
    {
        this.idEspecialidade = idEspecialidade;
    }    
    
    public List<GrlCentroHospitalar> findAllCentros()
    {
        return grlCentroHospitalarFacade.findAll();
    }   
    
    public GrlEspecialidade obterEspecialidadePreferencial()
    {
        this.idEspecialidade = ambCeConfiguracoes.getIdEspecialidade();
        
        if (idEspecialidade == 0)
        {
            return null;
        }
        return this.grlEspecialidadeFacade.find(idEspecialidade);
    }    
    
    public void initEspecialidadePreferencial()
    {
        //System.err.println("0: AmbCeConfiguracoesBean.initEspecialidadePreferencial()\tidConta: " + this.idConta);
        especialidadePreferencial = obterEspecialidadePreferencial();
        
        if (especialidadePreferencial != null)
        {
            this.idEspecialidade = this.especialidadePreferencial.getPkIdEspecialidade();
            //System.err.println("1: AmbCeConfiguracoesBean.initEspecialidadePreferencial()\tidConta: " + this.idConta);
            return;
        }

        RhProfissao rhProfissao = this.rhProfissaoFacade.findByDescricao("Médico");
        this.especialidadePreferencial = new GrlEspecialidade();
        especialidadePreferencial.setFkIdProfissao(rhProfissao);
        //System.err.println("2: AmbCeConfiguracoesBean.initEspecialidadePreferencial()\tidConta: " + this.idConta);
    }         
    
    public boolean createRegister(AmbCeConfiguracoes reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCeConfiguracoesFacade.create(reg);
            this.userTransaction.commit();
            //System.err.println("0: AmbCidConfiguracoesBean.createRegister()");
            return true;
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        //System.err.println("1: AmbCidConfiguracoesBean.createRegister()");
        return false;
    }    
    
    public boolean editRegister(AmbCeConfiguracoes reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCeConfiguracoesFacade.edit(reg);
            this.userTransaction.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }    

    public void createUpdateSegContaOnConfiguracoes()
    {
//System.err.println("0: AmbCeConfiguracoesBean.createUpdateSegContaOnConfiguracoes():idConta: " + idConta);

        if (ambCeConfiguracoesFacade.findAllByIdConta(idConta) != null)
            this.ambCeConfiguracoes = this.ambCeConfiguracoesFacade.findAllByIdConta(idConta);
        else
            this.ambCeConfiguracoes = new AmbCeConfiguracoes();
            
//        if (ambCeConfiguracoes != null)
//        {
//System.err.println("1: AmbCeConfiguracoesBean.createUpdateSegContaOnConfiguracoes()");            
//            return;
//        }
        
//        this.ambCeConfiguracoes = new AmbCeConfiguracoes();
        
        ambCeConfiguracoes.setIdConta(idConta);
        ambCeConfiguracoes.setIdEspecialidade(idEspecialidade);
        ambCeConfiguracoes.setIdCentroHospitalarProveniencia(getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar());
//System.err.println("2: AmbCeConfiguracoesBean.createUpdateSegContaOnConfiguracoes():Cod. Centro: " + ambCeConfiguracoes.getIdCentroHospitalarProveniencia());        
//        ambCeConfiguracoes.setIdCentroHospitalarProveniencia(findAllByCentroContaRendered(getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar(), obterContaDaCorrenteSessao().getPkIdConta()).getPkIdCentro());
        
        if (ambCeConfiguracoesFacade.findAll().isEmpty())
        {
//System.err.println("3: AmbCeConfiguracoesBean.createUpdateSegContaOnConfiguracoes():Cod. Centro: " + getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar());                                     
            this.createRegister(ambCeConfiguracoes);
        }
        else
        {
//System.err.println("4: AmbCeConfiguracoesBean.createUpdateSegContaOnConfiguracoes()|Cod. Centro: " + getInstanciaAmbConsultaListarBean().getCodigoCentroHospitalar());                         
            this.editRegister(ambCeConfiguracoes);
        }
        
       
    }   
    
    public SegConta obterContaDaCorrenteSessao()
    {
        return SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
    }     
    
    public SegConta initSegConta()
    {
//        segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
//        this.idConta = segConta.getPkIdConta();
        this.idConta = obterContaDaCorrenteSessao().getPkIdConta();
        createUpdateSegContaOnConfiguracoes();
        return segConta;
    }    
}