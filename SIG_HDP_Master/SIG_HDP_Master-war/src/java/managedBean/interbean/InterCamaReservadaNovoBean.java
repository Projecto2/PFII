/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.AdmsPaciente;
import entidade.InterCamaInternamento;
import entidade.InterCamaReservada;
import entidade.InterEstadoCama;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.InterCamaReservadaFacade;
import util.Constantes;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterCamaReservadaNovoBean implements Serializable
{

    @EJB
    private InterCamaReservadaFacade interCamaReservadaFacade;

    @Resource
    private UserTransaction userTransaction;

    private final Calendar dataCorrente = Calendar.getInstance();

    private InterCamaReservada interCamaReservada;

    /**
     * Creates a new instance of InterCamaReservadaNovoBean
     */
    public InterCamaReservadaNovoBean()
    {
    }

    public static InterCamaReservadaNovoBean getInstanciaBean()
    {
        return (InterCamaReservadaNovoBean) GeradorCodigo.getInstanciaBean("interCamaReservadaNovoBean");
    }

    public InterCamaReservada getInstancia()
    {
        InterCamaReservada c = new InterCamaReservada();
        c.setFkIdCamaInternamento(new InterCamaInternamento());
        c.setFkIdPaciente(new AdmsPaciente());

        return c;
    }

    public InterCamaReservada getInterCamaReservada()
    {
        if (interCamaReservada == null)
        {
            interCamaReservada = getInstancia();
        }
        return interCamaReservada;
    }

    public void setInterCamaReservada(InterCamaReservada interCamaReservada)
    {
        this.interCamaReservada = interCamaReservada;
    }

    public void reservarCama(int pkIdCama)
    {
        try
        {
            userTransaction.begin();

            InterCamaInternamento camaReserva = InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().find(pkIdCama);
            camaReserva.setFkIdEstadoCama(new InterEstadoCama(2));
            InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().edit(camaReserva);

            getInterCamaReservada().setFkIdCamaInternamento(camaReserva);
            getInterCamaReservada().setDataReserva(dataCorrente.getTime());
            getInterCamaReservada().setFkIdPaciente(InterSolicitacoesInterBean.getInstanciaBean().getServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente());
            getInterCamaReservada().setTempoMaximoReservado(Constantes.TEMPO_MAXIMO_CAMAS_RESERVA_EM_MIN);

            interCamaReservadaFacade.create(getInterCamaReservada());

            userTransaction.commit();
            Mensagem.sucessoMsg("Cama reservada com sucesso");
//            InterSolicitacoesInterBean.getInstanciaBean().findAllSolicitacoesAgendadasInter();
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                System.out.println(e.toString());

            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }
    }

    public InterCamaReservada findCamaReservadaByIdPaciente(Long pkIdPaciente)
    {
        if (!interCamaReservadaFacade.findCamaReservadaByPacienteId(pkIdPaciente).isEmpty())
        {
            return interCamaReservadaFacade.findCamaReservadaByPacienteId(pkIdPaciente).get(0);
        }
        return null;
    }

    public String findCamaReservada(Long pkIdPaciente)
    {
        if (!interCamaReservadaFacade.findCamaReservadaByPacienteId(pkIdPaciente).isEmpty())
        {
            InterCamaReservada camaReservada = interCamaReservadaFacade.findCamaReservadaByPacienteId(pkIdPaciente).get(0);

            if (findDiferencaHoras(camaReservada.getDataReserva()) > Constantes.TEMPO_MAXIMO_CAMAS_RESERVA_EM_MIN)
            {
                InterCamaInternamento camaReserva = InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().find(camaReservada.getFkIdCamaInternamento().getPkIdCamaInternamento());
                camaReserva.setFkIdEstadoCama(new InterEstadoCama(3));
                InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().edit(camaReserva);
                
                interCamaReservadaFacade.remove(camaReservada);
                
                return "black";
            }
            return Constantes.CAMARESERVADA;
        }
        return "black";
    }

    public int findDiferencaHoras(Date dataCamaReservada)
    {
        Calendar tempo = Calendar.getInstance();
        long dt1 = tempo.getTime().getTime();
        long dt2 = dataCamaReservada.getTime();

        long diferenca = dt1 - dt2;

        int dias = (int) (diferenca / (24 * 60 * 60 * 1000));
        int horas = (int) ((diferenca - (24 * dias * 60 * 60 * 1000)) / (1000 * 60 * 60));
        int minutos = (int) (diferenca - (24 * dias * 60 * 60 * 1000) - (1000 * 60 * 60 * horas)) / (1000 * 60);

//        System.out.println("Minutos : " + minutos);

        return minutos;
    }

//    public boolean removerCamaReservadaPeloTempo(Long pkIdPaciente)
//    {
//        if (!interCamaReservadaFacade.findCamaReservadaByPacienteId(pkIdPaciente).isEmpty())
//        {
//            InterCamaReservada camaReservada = interCamaReservadaFacade.findCamaReservadaByPacienteId(pkIdPaciente).get(0);
//
//            if (findDiferencaHoras(camaReservada.getDataReserva()) > Constantes.TEMPO_MAXIMO_CAMAS_RESERVA_EM_MIN)
//            {
//                interCamaReservadaFacade.remove(camaReservada);
//            }
//            return true;
//        }
//        return false;
//    }
}
