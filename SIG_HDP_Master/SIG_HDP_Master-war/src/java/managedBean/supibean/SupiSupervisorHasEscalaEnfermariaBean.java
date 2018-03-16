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
import entidade.InterEnfermaria;
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
import entidade.SupiSupervisorHasEscala;
//import entidade.SupiMedicoHasEscalaHasConsultorio;
import entidade.SupiTipoEscala;
import entidade.SupiTurno;
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
import sessao.InterEnfermariaFacade;
import sessao.RhFuncionarioFacade;
import sessao.SupiAtividadeMedicoFacade;
import sessao.SupiEscalaFacade;
import sessao.SupiEscalaMedicoFacade;
import sessao.SupiLocalConsultaFacade;
import sessao.SupiMedicoHasEscalaFacade;
import sessao.SupiSupervisorHasEscalaFacade;
//import sessao.SupiMedicoHasEscalaHasConsultorioFacade;
import sessao.SupiTurnoFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiSupervisorHasEscalaEnfermariaBean implements Serializable
{
    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;
    @EJB
    private SupiSupervisorHasEscalaFacade supiSupervisorHasEscalaFacade;
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
    private InterEnfermaria interEnfermaria;
    private SupiSupervisorHasEscala supiSupervisorHasEscala;

    private Integer idEscala, diaDoMes, localConsulta;

    /**
     * Creates a new instance of SupiMedicoHasEscalaHasConsultorioBean
     */
    public SupiSupervisorHasEscalaEnfermariaBean()
    {
    }

    public static SupiSupervisorHasEscalaEnfermariaBean getInstanciaBean()
    {
        return (SupiSupervisorHasEscalaEnfermariaBean) GeradorCodigo.getInstanciaBean("supiSupervisorHasEscalaEnfermariaBean");
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
// Já que não podes fazer o milagre da multiplicação do pão, faça então o milagre da sua divisão.
    public static SupiEscala getInstanciaSupiEscala()
    {
        SupiEscala supiEscala = new SupiEscala();
        
        supiEscala.setFkIdSeccao(new SupiSeccao());
        supiEscala.setFkIdTipoEscala(new SupiTipoEscala());
        
        

        return supiEscala;
    }

    public static SupiSupervisorHasEscala getInstancia()
    {
        SupiSupervisorHasEscala supiSupervisorHasEscala = new SupiSupervisorHasEscala();
        supiSupervisorHasEscala.setFkIdEscala(new SupiEscala());
        supiSupervisorHasEscala.setFkIdFuncionario(new RhFuncionario());
        supiSupervisorHasEscala.setFkIdEnfermaria(new InterEnfermaria());
        supiSupervisorHasEscala.setFkIdTurno(new SupiTurno());
        

        return supiSupervisorHasEscala;
    }

    public InterEnfermaria getInterEnfermaria()
    {
        if (interEnfermaria == null)
        {
            interEnfermaria = new InterEnfermaria();
        }
        return interEnfermaria;
    }

    public void setInterEnfermaria(InterEnfermaria interEnfermaria)
    {
        this.interEnfermaria = interEnfermaria;
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

    

    public SupiSupervisorHasEscala getSupiSupervisorHasEscala()
    {
        if (supiSupervisorHasEscala == null)
        {
            supiSupervisorHasEscala = getInstancia();
        }
        return supiSupervisorHasEscala;
    }
    
    public void setSupiSupervisorHasEscala(SupiSupervisorHasEscala supiSupervisorHasEscala)
    {
        this.supiSupervisorHasEscala = supiSupervisorHasEscala;
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

    public List<SupiEscala> findallEscalas()
    {
        return supiEscalaFacade.findAll();

    }

    public List<SupiSupervisorHasEscala> supervisorEscaladosList()
    {
        try
        {
            if (idEscala == null || diaDoMes == null)
            {
                return null;
            }

            System.out.println("Id Escala: " + idEscala + " --- Dia do mês: " + diaDoMes);

            SupiEscala escala = supiEscalaFacade.find(idEscala);

            Date data = new SimpleDateFormat("dd-MM-yyyy").parse(diaDoMes + "-" + escala.getMes() + "-" + escala.getAno());

            List<SupiSupervisorHasEscala> supervisorEscaladosList = supiSupervisorHasEscalaFacade.findMedicosDaEscala(idEscala, data);

            for (SupiSupervisorHasEscala supHasEscala : supervisorEscaladosList)
            {
                
                if (supHasEscala.getFkIdEnfermaria() == null)
                {
                    supHasEscala.setFkIdEnfermaria(new InterEnfermaria());
                }
                
            }

            return supervisorEscaladosList;
            
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

        SupiEscala escala = supiEscalaFacade.find(idEscala);
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
    public void atribuirEnfermaria()
    {
        try
        {
            this.userTransaction.begin();

//            ambConsultorio = new AmbConsultorio();

            if(supiSupervisorHasEscala == null || supiSupervisorHasEscala.getPkIdFuncionarioSupervisionaEscala() == null)
                throw new Exception("Enfermaria e dia de trabalho não selecionados");

            System.err.print("Teste Enfermaria: Gravar: "+supiSupervisorHasEscala.getFkIdEnfermaria().getDescricao());

            if (supiSupervisorHasEscala.getFkIdEnfermaria().getPkIdEnfermaria() == null)
            {
                supiSupervisorHasEscala.setFkIdEnfermaria(null);
            } 
            else
            {
                   supiSupervisorHasEscala.setFkIdEnfermaria(interEnfermariaFacade.find(supiSupervisorHasEscala.getFkIdEnfermaria().getPkIdEnfermaria()));
                //supiMedicoHasEscala.setFkIdConsultorio(ambConsultorioFacade.find(supiMedicoHasEscala.getFkIdConsultorio().getPkIdConsultorio()));
            }

            supiSupervisorHasEscalaFacade.edit(supiSupervisorHasEscala);

            userTransaction.commit();
            Mensagem.sucessoMsg("Enfermaria atribuída com sucesso!");

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

    public List<InterEnfermaria> listarEnfermarias()
    {
        return interEnfermariaFacade.findAll();
    }

    

    public List<SupiSupervisorHasEscala> listarSupervisoresEscalados()
    {
        List<SupiSupervisorHasEscala> resultado = new ArrayList<>();

        for (SupiSupervisorHasEscala supiSupervisorHasEscala : supiSupervisorHasEscalaFacade.findAll())
        {
            if (supiSupervisorHasEscala != null)
            {
                for (SupiEscala supiEscala : supiEscalaFacade.findAll())
                {
                    if (supiEscala.getPkIdEscala() == supiSupervisorHasEscala.getFkIdEscala().getPkIdEscala())
                    {
                        for (RhFuncionario rhFuncionario : rhFuncionarioFacade.findAll())
                        {
                            if (rhFuncionario.getPkIdFuncionario() == supiSupervisorHasEscala.getFkIdFuncionario().getPkIdFuncionario())
                            {
                                resultado.add(supiSupervisorHasEscala);
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

    public void seleccionarDados(SupiSupervisorHasEscala supiSupervisorHasEscala)
    {
        setSupiSupervisorHasEscala(supiSupervisorHasEscala);
    }

    public String fechar()
    {
        return "/faces/supiVisao/supiEscala/atribuicaoDeEnfermariaSupi.xhtml?faces-redirect=true";
    }
}
