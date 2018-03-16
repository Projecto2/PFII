/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhContrato;
import entidade.RhEstagiario;
import entidade.RhFerias;
import entidade.RhNotificacao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhFuncionarioFacade;
import util.Mensagem;
import sessao.RhContratoFacade;
import sessao.RhEstadoContratoFacade;
import sessao.RhEstadoEstagiarioFacade;
import sessao.RhEstadoFeriasFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhEstagiarioFacade;
import sessao.RhFeriasFacade;
import sessao.RhNotificacaoFacade;
import util.rh.MetodosGerais;
import util.rh.RhNotificacaoAssunto;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhNotificacaoAtualizacaoAutomaticaBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;
    @EJB
    private RhEstadoEstagiarioFacade estadoEstagiarioFacade;
    @EJB
    private RhEstadoContratoFacade estadoContratoFacade;
    @EJB
    private RhEstadoFeriasFacade estadoFeriasFacade;

    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhEstagiarioFacade estagiarioFacade;
    @EJB
    private RhContratoFacade contratoFacade;
    @EJB
    private RhFeriasFacade feriasFacade;

    @EJB
    private RhNotificacaoFacade notificacaoFacade;

    /**
     * Creates a new instance of funcionarioBean
     */
    public RhNotificacaoAtualizacaoAutomaticaBean ()
    {
    }

    public void verificarNotificacao ()
    {

        try
        {
            notificarInicioEstagios();
            notificarTerminoEstagios();
            notificarInicioContratos();
            notificarAproximacaoTerminoContratos();
            notificarTerminoContratos();
            notificarInicioFerias();
            notificarTerminoFerias();
        }
        catch (Exception e)
        {
            Mensagem.erroMsg("Ocorreu um erro ao verificar notificações");
            e.printStackTrace();
        }
    }

    private void notificarInicioEstagios ()
    {

        List<RhEstagiario> estagiarios = estagiarioFacade.procurarAprovadosComInicioAntesDe(new Date());

        if (!estagiarios.isEmpty())
        {

            for (RhEstagiario est : estagiarios)
            {
                String textoString = "O estágio de " + est.getFkIdPessoa().getNome() + " " + est.getFkIdPessoa().getNomeDoMeio() + " " + est.getFkIdPessoa().getSobreNome()
                                     + ", portador(a) do Bilhete de Identidade Nº " + est.getNumeroBi()
                                     + ", teve início no dia " + new SimpleDateFormat("dd-MM-yyyy").format(est.getDataInicioEstagio());

                RhNotificacao ntf = construirNotificacao(RhNotificacaoAssunto.INICIO_DE_ESTAGIO, textoString, est.getDataInicioEstagio(), est.getPkIdEstagiario());

                if (!notificacaoFacade.findNotificacao(ntf.getAssunto(), ntf.getDataDeEvento(), ntf.getIdDaEntidadeDoEvento()).isEmpty())
                {
                    return;
                }

                est.setFkIdEstadoEstagiario(estadoEstagiarioFacade.pesquisaPorDescricao(util.rh.Defs.RH_ACTIVO).get(0));
                guardarNotificacao(est, ntf);
            }

        }

    }

    private void notificarTerminoEstagios ()
    {
        List<RhEstagiario> estagiarios = estagiarioFacade.procurarActivosComTerminoAntesDe(new Date());

        if (!estagiarios.isEmpty())
        {

            for (RhEstagiario est : estagiarios)
            {
                String textoString = "O estágio de " + est.getFkIdPessoa().getNome() + " " + est.getFkIdPessoa().getNomeDoMeio() + " " + est.getFkIdPessoa().getSobreNome()
                                     + ", portador(a) do Bilhete de Identidade Nº " + est.getNumeroBi()
                                     + ", terminou no dia " + new SimpleDateFormat("dd-MM-yyyy").format(est.getDataInicioEstagio());

                RhNotificacao ntf = construirNotificacao(RhNotificacaoAssunto.FIM_DE_ESTAGIO, textoString, est.getDataInicioEstagio(), est.getPkIdEstagiario());

                if (!notificacaoFacade.findNotificacao(ntf.getAssunto(), ntf.getDataDeEvento(), ntf.getIdDaEntidadeDoEvento()).isEmpty())
                {
                    return;
                }

                est.setFkIdEstadoEstagiario(estadoEstagiarioFacade.pesquisaPorDescricao(util.rh.Defs.RH_ESTAGIO_FINALIZADO).get(0));
                guardarNotificacao(est, ntf);
            }

        }

    }

    private void notificarInicioContratos ()
    {
        List<RhContrato> contratos = contratoFacade.procurarActivosComInicioAntesDe(new Date());

        if (!contratos.isEmpty())
        {

            for (RhContrato contr : contratos)
            {
                String textoString = "O contrato do(a) funcionário(a) " + contr.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + contr.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + " " + contr.getFkIdFuncionario().getFkIdPessoa().getSobreNome()
                                     + ", portador(a) do cartão de identidade Nº " + contr.getFkIdFuncionario().getNumeroCartao()
                                     + ", teve início no dia " + new SimpleDateFormat("dd-MM-yyyy").format(contr.getDataInicio());

                RhNotificacao ntf = construirNotificacao(RhNotificacaoAssunto.INICIO_DE_CONTRATO, textoString, contr.getDataInicio(), contr.getPkIdContrato());

                if (!notificacaoFacade.findNotificacao(ntf.getAssunto(), ntf.getDataDeEvento(), ntf.getIdDaEntidadeDoEvento()).isEmpty())
                {
                    return;
                }

                contr.getFkIdFuncionario().setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(util.rh.Defs.RH_ACTIVO).get(0));
                guardarNotificacao(contr, ntf);
            }

        }

    }

    private void notificarAproximacaoTerminoContratos ()
    {
        List<RhContrato> contratos = contratoFacade.procurarActivosComTerminoEntre(new Date(), MetodosGerais.calcularProximaData(new Date(), 15));

        if (!contratos.isEmpty())
        {

            for (RhContrato contr : contratos)
            {
                String textoString = "O contrato do(a) funcionário(a) " + contr.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + contr.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + " " + contr.getFkIdFuncionario().getFkIdPessoa().getSobreNome()
                                     + ", portador(a) do cartão de identidade Nº " + contr.getFkIdFuncionario().getNumeroCartao()
                                     + ", irá terminar no dia " + new SimpleDateFormat("dd-MM-yyyy").format(contr.getDataTermino());

                RhNotificacao ntf = construirNotificacao(RhNotificacaoAssunto.APROXIMACAO_DE_FIM_DE_CONTRATO, textoString, contr.getDataTermino(), contr.getPkIdContrato());

                if (!notificacaoFacade.findNotificacao(ntf.getAssunto(), ntf.getDataDeEvento(), ntf.getIdDaEntidadeDoEvento()).isEmpty())
                {
                    return;
                }

                guardarNotificacao(contr.getFkIdFuncionario(), ntf);
            }

        }
    }

    private void notificarTerminoContratos ()
    {
        List<RhContrato> contratos = contratoFacade.procurarActivosComTerminoAntesDe(new Date());

        if (!contratos.isEmpty())
        {

            for (RhContrato contr : contratos)
            {
                String textoString = "O contrato do(a) funcionário(a) " + contr.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + contr.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + " " + contr.getFkIdFuncionario().getFkIdPessoa().getSobreNome()
                                     + ", portador(a) do cartão de identidade Nº " + contr.getFkIdFuncionario().getNumeroCartao()
                                     + ", terminou no dia " + new SimpleDateFormat("dd-MM-yyyy").format(contr.getDataTermino());

                RhNotificacao ntf = construirNotificacao(RhNotificacaoAssunto.FIM_DE_CONTRATO, textoString, contr.getDataTermino(), contr.getPkIdContrato());

                if (!notificacaoFacade.findNotificacao(ntf.getAssunto(), ntf.getDataDeEvento(), ntf.getIdDaEntidadeDoEvento()).isEmpty())
                {
                    return;
                }

                contr.setFkIdEstadoContrato(estadoContratoFacade.pesquisaPorDescricao(util.rh.Defs.RH_TERMINADO).get(0));
                contr.getFkIdFuncionario().setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(util.rh.Defs.RH_INACTIVO).get(0));
                guardarNotificacao(contr.getFkIdFuncionario(), ntf);
            }

        }
    }

    private void notificarInicioFerias ()
    {
        List<RhFerias> feriasList = feriasFacade.procurarMarcadasComInicioAntesDe(new Date());

        if (!feriasList.isEmpty())
        {

            for (RhFerias fer : feriasList)
            {
                String textoString = "As férias do(a) funcionário(a)" + fer.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + fer.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + " " + fer.getFkIdFuncionario().getFkIdPessoa().getSobreNome()
                                     + ", portador(a) do cartão de identidade Nº " + fer.getFkIdFuncionario().getNumeroCartao()
                                     + ", tiveram início no dia " + new SimpleDateFormat("dd-MM-yyyy").format(fer.getDataInicio());

                RhNotificacao ntf = construirNotificacao(RhNotificacaoAssunto.INICIO_DE_FERIAS, textoString, fer.getDataInicio(), fer.getPkIdFerias());

                if (!notificacaoFacade.findNotificacao(ntf.getAssunto(), ntf.getDataDeEvento(), ntf.getIdDaEntidadeDoEvento()).isEmpty())
                {
                    return;
                }

                fer.setFkIdEstadoFerias(estadoFeriasFacade.pesquisaPorDescricao(util.rh.Defs.RH_ACTIVO).get(0));
                fer.getFkIdFuncionario().setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(util.rh.Defs.RH_LICENCA_LIMITADA).get(0));
                guardarNotificacao(fer, ntf);
            }

        }

    }

    private void notificarTerminoFerias ()
    {
        List<RhFerias> feriasList = feriasFacade.procurarActivasComTerminoAntesDe(MetodosGerais.getDataDeHojeSemHora());

        if (!feriasList.isEmpty())
        {

            for (RhFerias fer : feriasList)
            {
                String textoString = "As férias do(a) funcionário(a)" + fer.getFkIdFuncionario().getFkIdPessoa().getNome() + " " + fer.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + " " + fer.getFkIdFuncionario().getFkIdPessoa().getSobreNome()
                                     + ", portador(a) do cartão de identidade Nº " + fer.getFkIdFuncionario().getNumeroCartao()
                                     + ", terminaram no dia " + new SimpleDateFormat("dd-MM-yyyy").format(fer.getDataTermino());

                RhNotificacao ntf = construirNotificacao(RhNotificacaoAssunto.FIM_DE_FERIAS, textoString, fer.getDataTermino(), fer.getPkIdFerias());

                if (!notificacaoFacade.findNotificacao(ntf.getAssunto(), ntf.getDataDeEvento(), ntf.getIdDaEntidadeDoEvento()).isEmpty())
                {
                    return;
                }

                fer.setFkIdEstadoFerias(estadoFeriasFacade.pesquisaPorDescricao(util.rh.Defs.RH_TERMINADO).get(0));
                fer.getFkIdFuncionario().setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(util.rh.Defs.RH_ACTIVO).get(0));
                guardarNotificacao(fer, ntf);
            }

        }
    }

    private RhNotificacao construirNotificacao (String assunto, String descricaotexto, Date dataDeEvento, int idDaEntidadeDoEvento)
    {
        RhNotificacao notificacao = new RhNotificacao();

        notificacao.setDataDeNotificacao(new Date());
        notificacao.setAssunto(assunto);
        notificacao.setDescricao(descricaotexto);
        notificacao.setDataDeEvento(dataDeEvento);
        notificacao.setIdDaEntidadeDoEvento(idDaEntidadeDoEvento);

        return notificacao;
    }

    private void guardarNotificacao (Object entidade, RhNotificacao notificacao)
    {
        try
        {
            userTransaction.begin();

            if (entidade instanceof RhEstagiario)
            {
                estagiarioFacade.edit((RhEstagiario) entidade);
            }

            else if (entidade instanceof RhContrato)
            {
                funcionarioFacade.edit(((RhContrato) entidade).getFkIdFuncionario());
                contratoFacade.edit((RhContrato) entidade);
            }

            else if (entidade instanceof RhFerias)
            {
                funcionarioFacade.edit(((RhFerias) entidade).getFkIdFuncionario());
                feriasFacade.edit((RhFerias) entidade);
            }

            notificacaoFacade.create(notificacao);

            userTransaction.commit();
            Mensagem.sucessoMsg("Nova Notificação");
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro ao guardar notificação");
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

}
