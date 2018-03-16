/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.AmbConsultorio;
import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.GrlPessoa;
import entidade.RhCategoriaProfissional;
import entidade.RhEstadoFuncionario;
import entidade.RhFuncao;
import entidade.RhFuncionario;
import entidade.RhHorarioGeralDeTrabalho;
import entidade.RhNivelAcademico;
import entidade.RhProfissao;
import entidade.RhSeccaoTrabalho;
import entidade.RhTipoDeHorarioTrabalho;
import entidade.RhTipoFuncionario;
import entidade.SegConta;
import entidade.SupiAtividadeMedico;
import entidade.SupiEscala;
import entidade.SupiEscalaMedico;
import entidade.SupiLocalConsulta;
import entidade.SupiSeccao;
import entidade.SupiMedicoHasEscala;
//import entidade.SupiMedicoHasEscalaHasConsultorio;
import entidade.SupiTipoEscala;
import entidade.SupiTurnoMedico;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.AmbConsultorioFacade;
import sessao.RhFuncionarioFacade;
import sessao.SupiAtividadeMedicoFacade;
import sessao.SupiEscalaFacade;
import sessao.SupiEscalaMedicoFacade;
import sessao.SupiLocalConsultaFacade;
import sessao.SupiMedicoHasEscalaFacade;
//import sessao.SupiMedicoHasEscalaHasConsultorioFacade;
import sessao.SupiTurnoFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class SupiMedicoHasEscalaHasConsultorioBean implements Serializable
{
    @EJB
    private SupiEscalaMedicoFacade supiEscalaMedicoFacade;
    @EJB
    private SupiMedicoHasEscalaFacade supiMedicoHasEscalaFacade;

    @EJB
    private SupiLocalConsultaFacade supiLocalConsultaFacade;

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbConsultorioFacade ambConsultorioFacade;
    @EJB
    private SupiEscalaFacade supiEscalaFacade;
    @EJB
    private SupiAtividadeMedicoFacade supiAtividadeMedicoFacade;
   
    @EJB
    private SupiTurnoFacade supiTurnoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    

    private AmbConsultorio ambConsultorio;
    private RhFuncionario rhFuncionario;
    private SupiMedicoHasEscala supiMedicoHasEscala;

    private Integer idEscala, diaDoMes, localConsulta;

    /**
     * Creates a new instance of SupiMedicoHasEscalaHasConsultorioBean
     */
    public SupiMedicoHasEscalaHasConsultorioBean()
    {
    }

    public static SupiMedicoHasEscalaHasConsultorioBean getInstanciaBean()
    {
        return (SupiMedicoHasEscalaHasConsultorioBean) GeradorCodigo.getInstanciaBean("supiMedicoHasEscalaHasConsultorioBean");
    }

    public static RhFuncionario getInstanciaRhFuncionario()
    {
        RhFuncionario rhFuncionario = new RhFuncionario();
        rhFuncionario.setFkIdAnexoGuiaTransferencia(new GrlFicheiroAnexado());
        rhFuncionario.setFkIdCategoria(new RhCategoriaProfissional());
        rhFuncionario.setFkIdCentroHospitalar(new GrlCentroHospitalar());
        rhFuncionario.setFkIdEspecialidade1(new GrlEspecialidade());
        rhFuncionario.setFkIdEspecialidade2(new GrlEspecialidade());
        rhFuncionario.setFkIdEstadoFuncionario(new RhEstadoFuncionario());
        rhFuncionario.setFkIdFuncao(new RhFuncao());
        rhFuncionario.setFkIdHorarioGeralDeTrabalho(new RhHorarioGeralDeTrabalho());
        rhFuncionario.setFkIdNivelAcademico(new RhNivelAcademico());
        rhFuncionario.setFkIdPessoa(new GrlPessoa());
        rhFuncionario.setFkIdProfissao(new RhProfissao());
        rhFuncionario.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        rhFuncionario.setFkIdTipoDeHorarioTrabalho(new RhTipoDeHorarioTrabalho());
        rhFuncionario.setFkIdTipoFuncionario(new RhTipoFuncionario());

        return rhFuncionario;
    }

    public static SupiEscalaMedico getInstanciaSupiEscala()
    {
        SupiEscalaMedico supiEscala = new SupiEscalaMedico();

        supiEscala.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        

        return supiEscala;
    }

    public static SupiMedicoHasEscala getInstancia()
    {
        SupiMedicoHasEscala supiMedicoHasEscala = new SupiMedicoHasEscala();

        supiMedicoHasEscala.setFkIdEscalaMedico(new SupiEscalaMedico());
        supiMedicoHasEscala.setFkIdMedico(new RhFuncionario());
        supiMedicoHasEscala.setFkIdConsultorio(new AmbConsultorio());
        supiMedicoHasEscala.setFkIdTurnoMedico(new SupiTurnoMedico());
        supiMedicoHasEscala.setFkIdAtividadeMedico(new SupiAtividadeMedico());
        supiMedicoHasEscala.setFkIdLocalConsulta(new SupiLocalConsulta());

        return supiMedicoHasEscala;
    }

    public AmbConsultorio getAmbConsultorio()
    {
        if (ambConsultorio == null)
        {
            ambConsultorio = new AmbConsultorio();
        }
        return ambConsultorio;
    }

    public void setAmbConsultorio(AmbConsultorio ambConsultorio)
    {
        this.ambConsultorio = ambConsultorio;
    }
    
    public RhFuncionario getRhFuncionario()
    {
        if (rhFuncionario == null)
        {
            rhFuncionario = getInstanciaRhFuncionario();
        }
        return rhFuncionario;
    }

    public void setRhFuncionario(RhFuncionario rhFuncionario)
    {
        this.rhFuncionario = rhFuncionario;
    }

    public Integer getLocalConsulta()
    {
        return localConsulta;
    }

    public void setLocalConsulta(Integer localConsulta)
    {
        this.localConsulta = localConsulta;
    }

    public SupiMedicoHasEscala getSupiMedicoHasEscala()
    {
        if (supiMedicoHasEscala == null)
        {
            supiMedicoHasEscala = getInstancia();
        }
        return supiMedicoHasEscala;
    }

    public Integer getIdEscala()
    {
        return idEscala;
    }

    public void setIdEscala(Integer idEscala)
    {
        this.idEscala = idEscala;
    }

    public Integer getDiaDoMes()
    {
        return diaDoMes;
    }

    public void setDiaDoMes(Integer diaDoMes)
    {
        this.diaDoMes = diaDoMes;
    }

    public void setSupiMedicoHasEscala(SupiMedicoHasEscala supiMedicoHasEscala)
    {
        this.supiMedicoHasEscala = supiMedicoHasEscala;
    }

//    public SupiMedicoHasEscalaHasConsultorio getSupiMedicoHasEscalaHasConsultorio()
//    {
//        if (supiMedicoHasEscalaHasConsultorio == null)
//        {
//            supiMedicoHasEscalaHasConsultorio = getInstanciaSupiMedicoHasEscalaHasConsultorio();
//        }
//        return supiMedicoHasEscalaHasConsultorio;
//    }
//
//    public void setSupiMedicoHasEscalaHasConsultorio(SupiMedicoHasEscalaHasConsultorio supiMedicoHasEscalaHasConsultorio)
//    {
//        this.supiMedicoHasEscalaHasConsultorio = supiMedicoHasEscalaHasConsultorio;
//    }
//

    public List<SupiEscalaMedico> findallEscalas()
    {
        return supiEscalaMedicoFacade.findAll();

    }

    public List<SupiMedicoHasEscala> medicosEscaladosList()
    {
        try
        {
            if (idEscala == null || diaDoMes == null)
            {
                return null;
            }

            System.out.println("Id Escala: " + idEscala + " --- Dia do mês: " + diaDoMes);

            SupiEscalaMedico escala = supiEscalaMedicoFacade.find(idEscala);

            Date data = new SimpleDateFormat("dd-MM-yyyy").parse(diaDoMes + "-" + escala.getMes() + "-" + escala.getAno());

            List<SupiMedicoHasEscala> medicosEscaladosList = supiMedicoHasEscalaFacade.findMedicosDaEscala(idEscala, data);

            for (SupiMedicoHasEscala medHasEscala : medicosEscaladosList)
            {
                
                if (medHasEscala.getFkIdConsultorio() == null)
                {
                    medHasEscala.setFkIdConsultorio(new AmbConsultorio());
                }
                if (medHasEscala.getFkIdAtividadeMedico() == null)
                {
                    medHasEscala.setFkIdAtividadeMedico(new SupiAtividadeMedico());
                }
                if (medHasEscala.getFkIdLocalConsulta() == null)
                {
                    medHasEscala.setFkIdLocalConsulta(new SupiLocalConsulta());
                }
            }

            return medicosEscaladosList;
            
        } catch (ParseException ex)
        {
            System.out.println("Ex: " + ex);
        }

        return null;

    }

    public List<SelectItem> diasDoMesSeleccionado()
    {
        System.out.println("Impressão id" + idEscala);
        if (idEscala == null)
        {
            diaDoMes = null;
            return null;
        }

        SupiEscalaMedico escala = supiEscalaMedicoFacade.find(idEscala);
        ArrayList<SelectItem> dias = new ArrayList<>();

        System.out.println("Escala " + escala);

        for (int i = 1; i <= util.rh.MetodosGerais.numeroDiasDoMes(escala.getAno(), escala.getMes()); i++)
        {
            dias.add(new SelectItem(i, "" + i));
        }
        return dias;

    }

    public static SegLoginBean obterSeLoginBean()
    {
        return (SegLoginBean) GeradorCodigo.getInstanciaBean("segLoginBean");
    }

    public SegConta segContaObterSegLoginBean()
    {
        SegConta segConta = new SegConta();
        segConta = obterSeLoginBean().obterContaDaCorrenteSessao();

        return segConta;
    }

    public RhFuncionario obterFuncionario()
    {
        return segContaObterSegLoginBean().getFkIdFuncionario();
    }

//    public List<SupiMedicoHasEscala> retornaConsultorio(int medico)
//    {
//        supiMedicoHasEscala = new SupiMedicoHasEscala();
//
//        List<SupiMedicoHasEscala> resultado = new ArrayList();
//
//        for (SupiMedicoHasEscala medicosupi : supiMedicoHasEscalaFacade.findAll())
//        {
//            if (medicosupi.getPkIdMedicoEscala() == medico)
//            {
//                supiMedicoHasEscala.setFkIdMedicoHasEscala(medicosupi);
//                resultado.add(supiMedicoHasEscala);
//            }
//        }
//        return resultado;
//    }
//
//    public List<SupiMedicoHasEscala> devolverConsultorio()
//    {
//        List<SupiMedicoHasEscala> resultadoSinais = new ArrayList();
//        int codigoConsultorio = 0;
//        for (int i = 0; i < listaDeMedicos.length; i++)
//        {
//            for (SupiMedicoHasEscala escalaConsultorio : retornaConsultorio(listaDeMedicos[i]))
//            {
//                if (escalaConsultorio.getPkIdMedicoEscala() == codigoConsultorio)
//                {
//                    supiMedicoHasEscala = escalaConsultorio;
//                }
//                resultadoSinais.add(escalaConsultorio);
//            }
//
//            if (i < listaDeMedicos.length - 1)
//            {
//                resultadoSinais.get(i).getFkIdMedico().getFkIdPessoa().getNome();
//            }
//        }
//        return resultadoSinais;
//    }
//
    public void atribuirConsultorio()
    {
        try
        {
            this.userTransaction.begin();

//            ambConsultorio = new AmbConsultorio();

            if(supiMedicoHasEscala == null || supiMedicoHasEscala.getPkIdMedicoEscala() == null)
                throw new Exception("Médico e dia de trabalho não selecionados");

            System.err.print("Teste consultório: Gravar: "+supiMedicoHasEscala.getFkIdConsultorio().getNome());

            if (supiMedicoHasEscala.getFkIdConsultorio().getPkIdConsultorio() == null)
            {
                supiMedicoHasEscala.setFkIdConsultorio(null);
            } 
            else
            {
                supiMedicoHasEscala.setFkIdConsultorio(ambConsultorioFacade.find(supiMedicoHasEscala.getFkIdConsultorio().getPkIdConsultorio()));
            }

            if (supiMedicoHasEscala.getFkIdAtividadeMedico().getPkIdAtividadeMedico()== null)
            {
                supiMedicoHasEscala.setFkIdAtividadeMedico(null);
            } 
            else
            {
                supiMedicoHasEscala.setFkIdAtividadeMedico(supiAtividadeMedicoFacade.find(supiMedicoHasEscala.getFkIdAtividadeMedico().getPkIdAtividadeMedico()));
            }

            if (supiMedicoHasEscala.getFkIdLocalConsulta().getPkIdLocalConsulta()== null)
            {
                supiMedicoHasEscala.setFkIdLocalConsulta(null);
            } 
            else
            {
                supiMedicoHasEscala.setFkIdLocalConsulta(supiLocalConsultaFacade.find(supiMedicoHasEscala.getFkIdLocalConsulta().getPkIdLocalConsulta()));
            }
            
            supiMedicoHasEscalaFacade.edit(supiMedicoHasEscala);

            userTransaction.commit();
            Mensagem.sucessoMsg("Consultório atribuído com sucesso!");

        } catch (Exception e)
        {
            Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    public List<AmbConsultorio> listarConsultorios()
    {
        return ambConsultorioFacade.findAll();
    }

    public List<SupiLocalConsulta> listarLocal()
    {
        return supiLocalConsultaFacade.findAll();
    }

    public List<SupiMedicoHasEscala> listarMedicosEscalados()
    {
        List<SupiMedicoHasEscala> resultado = new ArrayList<>();

        for (SupiMedicoHasEscala supiMedicoHasEscala : supiMedicoHasEscalaFacade.findAll())
        {
            if (supiMedicoHasEscala != null)
            {
                for (SupiEscala supiEscala : supiEscalaFacade.findAll())
                {
                    if (supiEscala.getPkIdEscala() == supiMedicoHasEscala.getFkIdEscalaMedico().getPkIdEscalaMedico())
                    {
                        for (RhFuncionario rhFuncionario : rhFuncionarioFacade.findAll())
                        {
                            if (rhFuncionario.getPkIdFuncionario() == supiMedicoHasEscala.getFkIdMedico().getPkIdFuncionario())
                            {
                                resultado.add(supiMedicoHasEscala);
                            }
                        }
                    }
                }
            }
        }
        return resultado;
    }

    ///-------------------------------------------------------------------------
    public String dateToString(Date data)
    {
        return DataUtils.dateToString(data);
    }

    public void seleccionarDados(SupiMedicoHasEscala medicoHasEscala)
    {
        setSupiMedicoHasEscala(medicoHasEscala);
    }

    public String fechar()
    {
        return "/faces/supiVisao/supiEscala/atribuicaoDeConsultoriosEnfermariaSupi.xhtml?faces-redirect=true";
    }
}
