/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterMedicacao;
import entidade.InterMedicacaoHasFarmProduto;
import entidade.InterRealizarMedicacao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.InterMedicacaoHasFarmProdutoFacade;
import sessao.InterRealizarMedicacaoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterMedicacaoListarBean implements Serializable
{

    @EJB
    private InterMedicacaoHasFarmProdutoFacade interMedicacaoHasFarmProdutoFacade;

    @EJB
    private InterRealizarMedicacaoFacade interRealizarMedicacaoFacade;

    @Resource
    private UserTransaction userTransaction;

    private String nomeEnfermeiro, nomeDoMeioEnfermeiro, sobreNomeEnfermeiro;
    private String medicamentoPesq, viaPesq, horarioPesq;

    private Date dataRegistoPesq, dataRegistoPesq1,dataRegistoPesq2;

    private List<InterMedicacao> listaMedicacao;

    private List<InterMedicacaoHasFarmProduto> listaMedicacaoProduto, listaMedicacaoProdutoOral;
    private List<InterMedicacaoHasFarmProduto> listaMedicacaoProdutoEndovenoso, listaMedicacaoProdutoIM;
    private List<InterMedicacaoHasFarmProduto> listaMedicacaoProdutoInalacao, listaOutrosMedicamentos;

    private List<InterRealizarMedicacao> listaMedicacaoRealizada;

    private SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

    private final Calendar dataCorrente = Calendar.getInstance();
    
    private int formaFarmaceuticaPesq, opcaoMedicacaoPesq; 

    /**
     * Creates a new instance of InterMedicacaoListarBean
     */
    public InterMedicacaoListarBean()
    {
    }

//    public void init()
//    {
//        inicializar();
//    }
//
//    public void inicializar()
//    {
//        listaMedicacaoProdutoOral = listaMedicacaoProdutoEndovenoso = listaMedicacaoProdutoIM = listaMedicacaoProdutoInalacao = new ArrayList();
//    }
    public static InterMedicacaoListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterMedicacaoListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interMedicacaoListarBean");
    }

    public String getNomeEnfermeiro()
    {
        return nomeEnfermeiro;
    }

    public void setNomeEnfermeiro(String nomeEnfermeiro)
    {
        this.nomeEnfermeiro = nomeEnfermeiro;
    }

    public String getNomeDoMeioEnfermeiro()
    {
        return nomeDoMeioEnfermeiro;
    }

    public void setNomeDoMeioEnfermeiro(String nomeDoMeioEnfermeiro)
    {
        this.nomeDoMeioEnfermeiro = nomeDoMeioEnfermeiro;
    }

    public String getSobreNomeEnfermeiro()
    {
        return sobreNomeEnfermeiro;
    }

    public void setSobreNomeEnfermeiro(String sobreNomeEnfermeiro)
    {
        this.sobreNomeEnfermeiro = sobreNomeEnfermeiro;
    }

    public String getMedicamentoPesq()
    {
        return medicamentoPesq;
    }

    public void setMedicamentoPesq(String medicamentoPesq)
    {
        this.medicamentoPesq = medicamentoPesq;
    }

    public String getViaPesq()
    {
        return viaPesq;
    }

    public void setViaPesq(String viaPesq)
    {
        this.viaPesq = viaPesq;
    }

    public Date getDataRegistoPesq1()
    {
        return dataRegistoPesq1;
    }

    public void setDataRegistoPesq1(Date dataRegistoPesq1)
    {
        this.dataRegistoPesq1 = dataRegistoPesq1;
    }

    public Date getDataRegistoPesq2()
    {
        return dataRegistoPesq2;
    }

    public void setDataRegistoPesq2(Date dataRegistoPesq2)
    {
        this.dataRegistoPesq2 = dataRegistoPesq2;
    }

    public int getFormaFarmaceuticaPesq()
    {
        return formaFarmaceuticaPesq;
    }

    public void setFormaFarmaceuticaPesq(int formaFarmaceuticaPesq)
    {
        this.formaFarmaceuticaPesq = formaFarmaceuticaPesq;
    }

    public String getHorarioPesq()
    {
        return horarioPesq;
    }

    public void setHorarioPesq(String horarioPesq)
    {
        this.horarioPesq = horarioPesq;
    }

    public Date getDataRegistoPesq()
    {
        return dataRegistoPesq;
    }

    public void setDataRegistoPesq(Date dataRegistoPesq)
    {
        this.dataRegistoPesq = dataRegistoPesq;
    }

    public int getOpcaoMedicacaoPesq()
    {
        return opcaoMedicacaoPesq;
    }

    public void setOpcaoMedicacaoPesq(int opcaoMedicacaoPesq)
    {
        this.opcaoMedicacaoPesq = opcaoMedicacaoPesq;
    }

    public List<InterMedicacao> getListaMedicacao()
    {
        return listaMedicacao;
    }

    public void setListaMedicacao(List<InterMedicacao> listaMedicacao)
    {
        this.listaMedicacao = listaMedicacao;
    }

    public List<InterMedicacaoHasFarmProduto> getListaOutrosMedicamentos()
    {
        return listaOutrosMedicamentos;
    }

    public void setListaOutrosMedicamentos(List<InterMedicacaoHasFarmProduto> listaOutrosMedicamentos)
    {
        this.listaOutrosMedicamentos = listaOutrosMedicamentos;
    }

    public List<InterMedicacaoHasFarmProduto> getListaMedicacaoProduto()
    {
        return listaMedicacaoProduto;
    }

    public void setListaMedicacaoProduto(List<InterMedicacaoHasFarmProduto> listaMedicacaoProduto)
    {
        this.listaMedicacaoProduto = listaMedicacaoProduto;
    }

    public List<InterMedicacaoHasFarmProduto> getListaMedicacaoProdutoOral()
    {
        if (listaMedicacaoProdutoOral == null)
        {
            return new ArrayList();
        }
        return listaMedicacaoProdutoOral;
    }

    public void setListaMedicacaoProdutoOral(List<InterMedicacaoHasFarmProduto> listaMedicacaoProdutoOral)
    {
        this.listaMedicacaoProdutoOral = listaMedicacaoProdutoOral;
    }

    public List<InterMedicacaoHasFarmProduto> getListaMedicacaoProdutoEndovenoso()
    {
        if (listaMedicacaoProdutoEndovenoso == null)
        {
            return new ArrayList();
        }
        return listaMedicacaoProdutoEndovenoso;
    }

    public void setListaMedicacaoProdutoEndovenoso(List<InterMedicacaoHasFarmProduto> listaMedicacaoProdutoEndovenoso)
    {
        this.listaMedicacaoProdutoEndovenoso = listaMedicacaoProdutoEndovenoso;
    }

    public List<InterMedicacaoHasFarmProduto> getListaMedicacaoProdutoIM()
    {
        if (listaMedicacaoProdutoIM == null)
        {
            return new ArrayList();
        }
        return listaMedicacaoProdutoIM;
    }

    public void setListaMedicacaoProdutoIM(List<InterMedicacaoHasFarmProduto> listaMedicacaoProdutoIM)
    {
        this.listaMedicacaoProdutoIM = listaMedicacaoProdutoIM;
    }

    public List<InterRealizarMedicacao> getListaMedicacaoRealizada()
    {
        return listaMedicacaoRealizada;
    }

    public void setListaMedicacaoRealizada(List<InterRealizarMedicacao> listaMedicacaoRealizada)
    {
        this.listaMedicacaoRealizada = listaMedicacaoRealizada;
    }

    public List<InterMedicacaoHasFarmProduto> getListaMedicacaoProdutoInalacao()
    {
        if (listaMedicacaoProdutoInalacao == null)
        {
            return new ArrayList();
        }
        return listaMedicacaoProdutoInalacao;
    }

    public void setListaMedicacaoProdutoInalacao(List<InterMedicacaoHasFarmProduto> listaMedicacaoProdutoInalacao)
    {
        this.listaMedicacaoProdutoInalacao = listaMedicacaoProdutoInalacao;
    }

    public void pesquisar(int pk_id_medicacao)
    {
        listaMedicacaoProduto = interMedicacaoHasFarmProdutoFacade.pesquisarRegisto(tipoServico, nomeEnfermeiro, nomeDoMeioEnfermeiro, sobreNomeEnfermeiro, dataRegistoPesq, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), 0, 0, 0, null);

        if (listaMedicacaoProduto.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaMedicacaoProduto.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarMedicacaoRealizada()
    {
        listaMedicacaoRealizada = interRealizarMedicacaoFacade.pesquisarMedicacaoRealizada(tipoServico, medicamentoPesq, viaPesq, horarioPesq, nomeEnfermeiro, dataRegistoPesq1, dataRegistoPesq2, formaFarmaceuticaPesq, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), opcaoMedicacaoPesq);

        if (listaMedicacaoRealizada.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaMedicacaoRealizada.size() + " registo(s) retornado(s).");
        }
    }

    public List<InterMedicacaoHasFarmProduto> pesquisarPorViaAdms(int pkIdMedicamento)
    {
        List<InterMedicacaoHasFarmProduto> listaAux = interMedicacaoHasFarmProdutoFacade.pesquisarRegisto(tipoServico, nomeEnfermeiro, nomeDoMeioEnfermeiro, sobreNomeEnfermeiro, dataRegistoPesq, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), 0, 0, pkIdMedicamento, null);
        
        return listaAux;
    }

    public void eliminarMedicamento(InterMedicacaoHasFarmProduto protudo)
    {
        try
        {
            userTransaction.begin();

            interMedicacaoHasFarmProdutoFacade.remove(protudo);

            userTransaction.commit();
            Mensagem.sucessoMsg("Medicamento eliminado com sucesso.");
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

    public String voltar()
    {
        return "/faces/interVisao/interInternamento/internamentoListar/medicacaoListarInter.xhtml?faces-redirect=true";
    }

    public String registarMedicacao()
    {
        List<InterMedicacaoHasFarmProduto> listaAux = interMedicacaoHasFarmProdutoFacade.pesquisarRegisto(tipoServico, null, null, null, null, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), 0, 0, 0, null);
        
        listaMedicacaoProdutoOral = new ArrayList();
        listaOutrosMedicamentos = new ArrayList();
        
        for (int i = 0; i < listaAux.size(); i++)
        {
            if (!InterRealizarMedicacaoListarBean.getInstanciaBean().descricaoOpcaoMedicacao(listaAux.get(i).getPkIdInterMedicacaoHasFarmProduto(), InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), 0, null).equals("xxx---stop"))
            {
                    if (InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario6_6h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario8_8h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario12_12h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)))                
                        listaMedicacaoProdutoOral.add(listaAux.get(i));
            }
        }
        
        for (int i = 0; i < listaAux.size(); i++)
        {
            if (!InterRealizarMedicacaoListarBean.getInstanciaBean().descricaoOpcaoMedicacao(listaAux.get(i).getPkIdInterMedicacaoHasFarmProduto(), InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento(), 0, null).equals("xxx---stop"))
            {
                    if (!InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horarioPadrao(listaAux.get(i).getFkIdHoraMedicacao().getDescricao()))                
                        listaOutrosMedicamentos.add(listaAux.get(i));
            }
        }

        return "/faces/interVisao/interInternamento/internamentoCadastrar/realizarMedicacaoInter.xhtml?faces-redirect=true";
    }

    public String horario()
    {
        int hora = dataCorrente.get(Calendar.HOUR_OF_DAY);

        if (hora == 6 || hora == 12 || hora == 18 || hora == 0)
        {
            return "6/6h";
        }
        else if (hora == 6 || hora == 14 || hora == 22)
        {
            return "8/8h";
        }
        else if (hora == 6 || hora == 18)
        {
            return "12/12h";
        }
        else
        {
            return "MF";
        }
    }
    
    public String horarioPadrao()
    {
        int hora = dataCorrente.get(Calendar.HOUR_OF_DAY);

        if (hora <= 6+Constantes.HORASATIVAS || hora <= 12+Constantes.HORASATIVAS || hora <= 18+Constantes.HORASATIVAS || hora <= 0+Constantes.HORASATIVAS)
        {
            return "6/6h";
        }
        else if (hora <= 6+Constantes.HORASATIVAS || hora <= 14+Constantes.HORASATIVAS || hora <= 22+Constantes.HORASATIVAS)
        {
            return "8/8h";
        }
        else if (hora <= 6+Constantes.HORASATIVAS || hora <= 18+Constantes.HORASATIVAS)
        {
            return "12/12h";
        }
        else
        {
            return "MF";
        }
    }

    public boolean verificarHorario()
    {
        if (horarioPadrao().equals("MF"))
        {
            return true;
        }
        return false;
    }

    public boolean verificarMedicamentos()
    {
        if (listaMedicacaoProdutoOral.isEmpty())
        {
            return false;
        }
        return true;
    }
    
    public String findSOS(boolean sos)
    {
        if (sos)
        {
            return "Sim";
        }
        return "Não";
    }

    public InterMedicacaoHasFarmProdutoFacade getInterMedicacaoHasFarmProdutoFacade()
    {
        return interMedicacaoHasFarmProdutoFacade;
    }

    public void setInterMedicacaoHasFarmProdutoFacade(InterMedicacaoHasFarmProdutoFacade interMedicacaoHasFarmProdutoFacade)
    {
        this.interMedicacaoHasFarmProdutoFacade = interMedicacaoHasFarmProdutoFacade;
    }
    
    
}
