/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterControloParametrosVitais;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.InterControloParametrosVitaisFacade;
import util.Constantes;
import util.Data;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterControloParametrosVitaisListarBean implements Serializable
{

    @EJB
    private InterControloParametrosVitaisFacade interControloParametrosVitaisFacade;

    private List<InterControloParametrosVitais> listaParametrosVitais;

    private final Calendar dataCorrente = Calendar.getInstance();

    private Date dataRegisto1, dataRegisto2;

    private String parametro, nomeEnfermeiro;

    /**
     * Creates a new instance of InterControloParametrosVitaisListarBean
     */
    public InterControloParametrosVitaisListarBean()
    {
    }

    public static InterControloParametrosVitaisListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterControloParametrosVitaisListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interControloParametrosVitaisListarBean");
    }

    public List<InterControloParametrosVitais> getListaParametrosVitais()
    {
        return listaParametrosVitais;
    }

    public void setListaParametrosVitais(List<InterControloParametrosVitais> listaParametrosVitais)
    {
        this.listaParametrosVitais = listaParametrosVitais;
    }

    public Date getDataRegisto1()
    {
        return dataRegisto1;
    }

    public void setDataRegisto1(Date dataRegisto1)
    {
        this.dataRegisto1 = dataRegisto1;
    }

    public Date getDataRegisto2()
    {
        return dataRegisto2;
    }

    public void setDataRegisto2(Date dataRegisto2)
    {
        this.dataRegisto2 = dataRegisto2;
    }

    public String getParametro()
    {
        return parametro;
    }

    public void setParametro(String parametro)
    {
        this.parametro = parametro;
    }

    public String getNomeEnfermeiro()
    {
        return nomeEnfermeiro;
    }

    public void setNomeEnfermeiro(String nomeEnfermeiro)
    {
        this.nomeEnfermeiro = nomeEnfermeiro;
    }

    public float pesquisarPorParametro(String parametro)
    {
        float valor = 0;

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametro, dataCorrente.getTime(), dataCorrente.get(Calendar.HOUR_OF_DAY), null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            valor = listaParametrosVitais.get(0).getValor();
        }
        return valor;
    }

    public String findByDescricaoParametro(String parametro, Long pkIdRegisoInternamento)
    {
        String valor = "";

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(pkIdRegisoInternamento, parametro, null, 0, null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            valor += listaParametrosVitais.get(listaParametrosVitais.size() - 1).getValor();
        }
        return valor;
    }

    public InterControloParametrosVitais findByParametro(String parametro, Long pkIdRegistoInternamento)
    {
        InterControloParametrosVitais controlo = null;

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(pkIdRegistoInternamento, parametro, dataCorrente.getTime(), dataCorrente.get(Calendar.HOUR_OF_DAY), null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            controlo = listaParametrosVitais.get(0);
        }
        return controlo;
    }

    public String findPulsoUnidade(Long pkIdRegistoInternamento)
    {
        InterControloParametrosVitais controlo = null;

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(pkIdRegistoInternamento, "Pulso", null, 0, null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            controlo = listaParametrosVitais.get(listaParametrosVitais.size() - 1);
            return controlo.getFkIdPulsoUnidade().getDescricao();
        }
        return "";
    }

    public String findTensaoArterial()
    {
        String ta = null;

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), "Tensão Arterial", dataCorrente.getTime(), dataCorrente.get(Calendar.HOUR_OF_DAY), null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            ta = listaParametrosVitais.get(0).getTaValor1() + " / " + listaParametrosVitais.get(0).getTaValor2();
        }
        return ta;
    }

    public String findUltimaTensaoArterialById(Long pkIdRegistoInternamento)
    {
        String ta = "";

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(pkIdRegistoInternamento, "Tensão Arterial", null, 0, null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            ta = listaParametrosVitais.get(listaParametrosVitais.size() - 1).getTaValor1() + " / " + listaParametrosVitais.get(listaParametrosVitais.size() - 1).getTaValor2();
        }
        return ta;
    }

    public Date pesquisarDataPorParametro(String parametro)
    {
        Date valor = null;

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametro, dataCorrente.getTime(), dataCorrente.get(Calendar.HOUR_OF_DAY), null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            valor = listaParametrosVitais.get(0).getDataRegistadaNoPaciente();
        }
        return valor;
    }

    public boolean jaFoiRegistado(String parametro)
    {
        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametro, dataCorrente.getTime(), dataCorrente.get(Calendar.HOUR_OF_DAY), null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            return true;
        }
        return false;
    }

    public void pesquisar()
    {
        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), parametro, null, 0, dataRegisto1, dataRegisto2, nomeEnfermeiro);

        if (listaParametrosVitais.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaParametrosVitais.size() + " registo(s) retornado(s).");
        }
    }

    public String definirCorTa(Long pkIdRegisoInternamento)
    {
        float ta1;
        float ta2;
        String cor = Constantes.NORMAL;

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(pkIdRegisoInternamento, "Tensão Arterial", null, 0, null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {
            ta1 = listaParametrosVitais.get(listaParametrosVitais.size() - 1).getTaValor1();
            ta2 = listaParametrosVitais.get(listaParametrosVitais.size() - 1).getTaValor2();

            if (ta1 <= 60 || ta1 >= 150 || ta2 <= 50 || ta2 >= 100)
            {
                cor = Constantes.GRAVE;
            }
            else if ((ta1 > 60 && ta1 <= 80) || (ta1 >= 130 && ta1 < 150) || (ta2 > 50 && ta2 <= 65) || (ta2 >= 80 && ta2 < 100))
            {
                cor = Constantes.CUIDADOS;
            }
        }

        return cor;
    }

    public String definirCorByParametro(String parametro, Long pkIdRegisoInternamento)
    {
        float valor;
        String cor = Constantes.NORMAL;

        listaParametrosVitais = interControloParametrosVitaisFacade.pesquisarParametro(pkIdRegisoInternamento, parametro, null, 0, null, null, null);

        if (!listaParametrosVitais.isEmpty())
        {

            valor = listaParametrosVitais.get(listaParametrosVitais.size() - 1).getValor();

            if (parametro.equals("Temperatura"))
            {
                if (valor < 34 || valor > 38)
                {
                    cor = Constantes.GRAVE;
                }
                else if (valor == 34 || valor == 38)
                {
                    cor = Constantes.CUIDADOS;
                }
            }
            else if (parametro.equals("Pulso"))
            {
                if (valor < 50 || valor > 130)
                {
                    cor = Constantes.GRAVE;
                }
                else if ((valor >= 50 && valor < 60) || (valor > 100 && valor <= 130))
                {
                    cor = Constantes.CUIDADOS;
                }
            }
            else if (parametro.equals("Saturação"))
            {
                if (valor < 80)
                {
                    cor = Constantes.GRAVE;
                }
                else if (valor >= 80 && valor <= 90)
                {
                    cor = Constantes.CUIDADOS;
                }
            }
            else if (parametro.equals("Frequência Respiratória"))
            {
                if (valor < 8 || valor > 30)
                {
                    cor = Constantes.GRAVE;
                }
                else if ((valor >= 8 && valor < 12) || (valor > 20 && valor <= 30))
                {
                    cor = Constantes.CUIDADOS;
                }
            }
            else if (parametro.equals("Diuresi"))
            {
                if (valor < 500 || valor > 4000)
                {
                    cor = Constantes.GRAVE;
                }
                else if ((valor >= 500 && valor < 1000) || (valor > 2500 && valor <= 4000))
                {
                    cor = Constantes.CUIDADOS;
                }
            }
            else if (parametro.equals("Glicemia"))
            {
                if (valor < 60 || valor > 200)
                {
                    cor = Constantes.GRAVE;
                }
                else if ((valor >= 60 && valor < 80) || (valor > 120 && valor <= 200))
                {
                    cor = Constantes.CUIDADOS;
                }
            }
        }
        return cor;
    }

    public InterControloParametrosVitaisFacade getInterControloParametrosVitaisFacade()
    {
        return interControloParametrosVitaisFacade;
    }

    public void setInterControloParametrosVitaisFacade(InterControloParametrosVitaisFacade interControloParametrosVitaisFacade)
    {
        this.interControloParametrosVitaisFacade = interControloParametrosVitaisFacade;
    }
}
