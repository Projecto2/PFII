/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.AdmsPaciente;
import entidade.InterParametroVital;
import entidade.InterRegistoInternamentoHasParametroVital;
import entidade.SegConta;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.InterNotificacaoFacade;
import sessao.InterRegistoInternamentoHasParametroVitalFacade;
import util.Constantes;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRegistoInternamentoParametroVitalListarBean
{

    @EJB
    private InterNotificacaoFacade interNotificacaoFacade;

    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private InterRegistoInternamentoHasParametroVitalFacade interRegistoInternamentoHasParametroVitalFacade;

    private List<InterRegistoInternamentoHasParametroVital> listaRegistoInternamentoHasParametroVital;

    private final Calendar dataCorrente = Calendar.getInstance();

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

    /**
     * Creates a new instance of
     * InterRegistoInternamentoParametroVitalListarBean
     */
    public InterRegistoInternamentoParametroVitalListarBean()
    {
    }

    public static InterRegistoInternamentoParametroVitalListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterRegistoInternamentoParametroVitalListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interRegistoInternamentoParametroVitalListarBean");
    }

    public List<InterRegistoInternamentoHasParametroVital> getListaRegistoInternamentoHasParametroVital()
    {
        return listaRegistoInternamentoHasParametroVital;
    }

    public void setListaRegistoInternamentoHasParametroVital(List<InterRegistoInternamentoHasParametroVital> listaRegistoInternamentoHasParametroVital)
    {
        this.listaRegistoInternamentoHasParametroVital = listaRegistoInternamentoHasParametroVital;
    }

    public List<InterRegistoInternamentoHasParametroVital> pesquisarTodos(Long pkIdRegisto, int parametro)
    {
        List<InterRegistoInternamentoHasParametroVital> listaAux = interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegisto, parametro);

        return listaAux;
    }

    public List<InterRegistoInternamentoHasParametroVital> findAllOutrosParametros(Long pkIdRegisto)
    {
        List<InterRegistoInternamentoHasParametroVital> listaAux = interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegisto, 0);
        List<InterRegistoInternamentoHasParametroVital> lista = new ArrayList();

        for (int i = 0; i < listaAux.size(); i++)
        {
            if (listaAux.get(i).getFkIdParametroVital().getPkIdParametroVital() > 8)
            {
                lista.add(listaAux.get(i));
            }
        }

        return lista;
    }

    public List<InterRegistoInternamentoHasParametroVital> findAllOutrosParametrosHorarios(Long pkIdRegisto)
    {
        List<InterRegistoInternamentoHasParametroVital> listaAux = interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegisto, 0);
        List<InterRegistoInternamentoHasParametroVital> lista = new ArrayList();

        for (int i = 0; i < listaAux.size(); i++)
        {
            if (listaAux.get(i).getFkIdParametroVital().getPkIdParametroVital() > 8 || !horarioPadrao(listaAux.get(i).getFkIdHoraMedicacao().getDescricao()))
            {
                lista.add(listaAux.get(i));
            }
        }

        return lista;
    }

    public List<InterRegistoInternamentoHasParametroVital> pesquisar(Long pkIdRegisto, int parametro, int opcao)
    {   
        SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        
        tipoServico = segConta.getFkIdFuncionario().getFkIdSeccaoTrabalho().getDescricao();
        
        List<InterRegistoInternamentoHasParametroVital> listaAux = interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegisto, parametro);

        List<InterRegistoInternamentoHasParametroVital> lista = new ArrayList();

        String assunto;
        
        for (int i = 0; i < listaAux.size(); i++)
        {
            assunto = "";
            
            if (listaAux.get(i).getFkIdParametroVital().getPkIdParametroVital() <= 8 || !horarioPadrao(listaAux.get(i).getFkIdHoraMedicacao().getDescricao()))
            {
                if (horario6_6h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || horario8_8h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || horario12_12h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)))
                {
                    if (opcao == 1)
                    {
                        lista.add(listaAux.get(i));
                    }
                    else
                    {
                        try
                        {
                            userTransaction.begin();

                            if (InterControloParametrosVitaisListarBean.getInstanciaBean().definirCorTa(listaAux.get(i).getFkIdRegistoInternamento().getPkIdRegistoInternamento()).equals(Constantes.CUIDADOS) || InterControloParametrosVitaisListarBean.getInstanciaBean().definirCorTa(listaAux.get(i).getFkIdRegistoInternamento().getPkIdRegistoInternamento()).equals(Constantes.GRAVE))
                                assunto =  "A Tensão Arterial está no estado " + getDescricaoEstadoByCor(InterControloParametrosVitaisListarBean.getInstanciaBean().definirCorTa(listaAux.get(i).getFkIdRegistoInternamento().getPkIdRegistoInternamento()));
                            else if (InterControloParametrosVitaisListarBean.getInstanciaBean().definirCorByParametro(listaAux.get(i).getFkIdParametroVital().getDescricao(), listaAux.get(i).getFkIdRegistoInternamento().getPkIdRegistoInternamento()).equals(Constantes.CUIDADOS) || InterControloParametrosVitaisListarBean.getInstanciaBean().definirCorByParametro(listaAux.get(i).getFkIdParametroVital().getDescricao(), listaAux.get(i).getFkIdRegistoInternamento().getPkIdRegistoInternamento()).equals(Constantes.GRAVE))
                                assunto =  "O parametro "+listaAux.get(i).getFkIdParametroVital().getDescricao()+" está no estado " + getDescricaoEstadoByCor(InterControloParametrosVitaisListarBean.getInstanciaBean().definirCorByParametro(listaAux.get(i).getFkIdParametroVital().getDescricao(), listaAux.get(i).getFkIdRegistoInternamento().getPkIdRegistoInternamento()));
                          
                            if (interNotificacaoFacade.findBy(tipoServico, 2, assunto, listaAux.get(i).getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), listaAux.get(i).getFkIdParametroVital().getPkIdParametroVital(), 0).isEmpty() || interNotificacaoFacade.findBy(tipoServico, 2, null, listaAux.get(i).getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), listaAux.get(i).getFkIdParametroVital().getPkIdParametroVital(), 0) == null)
                            {
                                    InterNotificacaoBean.getInstanciaBean().getInterNotificacao().setDescricao(Constantes.NOTIFICACAO_PARAMETROS_DESCRICAO );
                                    InterNotificacaoBean.getInstanciaBean().getInterNotificacao().setDataDeNotificacao(dataCorrente.getTime());
                                    InterNotificacaoBean.getInstanciaBean().getInterNotificacao().setFkIdPaciente(new AdmsPaciente(listaAux.get(i).getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()));
                                    InterNotificacaoBean.getInstanciaBean().getInterNotificacao().getFkIdTipoNotificacao().setPkIdTipoNotificacao(2);
                                    InterNotificacaoBean.getInstanciaBean().getInterNotificacao().setFkIdParametroVital(new InterParametroVital(listaAux.get(i).getFkIdParametroVital().getPkIdParametroVital()));
                                    InterNotificacaoBean.getInstanciaBean().getInterNotificacao().getFkIdEnfermaria().setPkIdEnfermaria(InterEnfermariaListarBean.getInstanciaBean().getInterEnfermariaFacade().getEnfermariaFuncionario(tipoServico).getPkIdEnfermaria());
                                    
                                    if (assunto.equals(""))                                    
                                        InterNotificacaoBean.getInstanciaBean().getInterNotificacao().setAssunto("Precisa-se medir o parâmetro "+listaAux.get(i).getFkIdParametroVital().getDescricao());
                                    else
                                        InterNotificacaoBean.getInstanciaBean().getInterNotificacao().setAssunto(assunto);

                                    interNotificacaoFacade.create(InterNotificacaoBean.getInstanciaBean().getInterNotificacao());

                                    InterNotificacaoBean.getInstanciaBean().limparCampos();
                            }

                            userTransaction.commit();
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
                }
            }
        }

        return lista;
    }

    public boolean horario6_6h(String horario, int hora)
    {
        if (horario.equals("6/6h") && ((hora >= 6 && hora <= 6 + Constantes.HORASATIVAS) || (hora >= 12 && hora <= 12 + Constantes.HORASATIVAS) || (hora >= 18 && hora <= 18 + Constantes.HORASATIVAS) || (hora >= 0 && hora <= 0 + Constantes.HORASATIVAS)))
        {
            return true;
        }
        return false;
    }

    public boolean horario8_8h(String horario, int hora)
    {
        if (horario.equals("8/8h") && ((hora >= 6 && hora <= 6 + Constantes.HORASATIVAS) || (hora >= 14 && hora <= 14 + Constantes.HORASATIVAS) || (hora >= 22 && hora <= 22 + Constantes.HORASATIVAS)))
        {
            return true;
        }
        return false;
    }

    public boolean horario12_12h(String horario, int hora)
    {
        if (horario.equals("12/12h") && ((hora >= 6 && hora <= 6 + Constantes.HORASATIVAS) || (hora >= 18 && hora <= 18 + Constantes.HORASATIVAS)))
        {
            return true;
        }
        return false;
    }

    public boolean horarioPadrao(String horario)
    {
        if (horario.equals("6/6h") || horario.equals("8/8h") || horario.equals("12/12h"))
        {
            return true;
        }
        return false;
    }

    public String getParametro(Long pkIdRegisto, int parametro)
    {
        List<InterRegistoInternamentoHasParametroVital> listaAux = interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegisto, parametro);

        if (listaAux.isEmpty())
        {
            return "";
        }
        return listaAux.get(0).getFkIdHoraMedicacao().getDescricao();
    }

    public boolean findOutrosParametrosVitais(Long pkIdRegisto)
    {
        if (!InterParametroVitalListarBean.getInstanciaBean().findAllParametrosByIdRegisto(pkIdRegisto).isEmpty())
        {
            List<InterParametroVital> listaParametros = InterParametroVitalListarBean.getInstanciaBean().findAllParametrosByIdRegisto(pkIdRegisto);

            for (int i = 0; i < listaParametros.size(); i++)
            {
                if (listaParametros.get(i).getPkIdParametroVital() > 8)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public String horas(int parametro)
    {
        String horario = null;

        List<InterRegistoInternamentoHasParametroVital> listaAux = interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametro);

        if (listaAux.isEmpty())
        {
            return "";
        }
        else if (listaAux.get(0).getFkIdHoraMedicacao().getDescricao().equals("6/6h"))
        {
            horario = "6h 12h 18h 24h";
        }
        else if (listaAux.get(0).getFkIdHoraMedicacao().getDescricao().equals("8/8h"))
        {
            horario = "6h 14h 22h";
        }
        else if (listaAux.get(0).getFkIdHoraMedicacao().getDescricao().equals("12/12h"))
        {
            horario = "6h 18h";
        }
        return horario;
    }

    public String horas(int parametro, Long pkIdRegistoInternamento)
    {
        String horario = null;

        List<InterRegistoInternamentoHasParametroVital> listaAux = interRegistoInternamentoHasParametroVitalFacade.pesquisarRegisto(tipoServico, pkIdRegistoInternamento, parametro);

        if (listaAux.isEmpty())
        {
            return "";
        }
        else if (listaAux.get(0).getFkIdHoraMedicacao().getDescricao().equals("6/6h"))
        {
            horario = "6h 12h 18h 24h";
        }
        else if (listaAux.get(0).getFkIdHoraMedicacao().getDescricao().equals("8/8h"))
        {
            horario = "6h 14h 22h";
        }
        else if (listaAux.get(0).getFkIdHoraMedicacao().getDescricao().equals("12/12h"))
        {
            horario = "6h 18h";
        }
        return horario;
    }
    
    private String getDescricaoEstadoByCor(String cor)
    {
        if (cor.equals(Constantes.GRAVE))
        {
            return "Grave";
        }
        return "Cuidados";
    }
}
