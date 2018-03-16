/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.FarmLoteProduto;
import entidade.FarmProduto;
import entidade.InterHoraMedicacao;
import entidade.InterMedicacao;
import entidade.InterMedicacaoHasFarmProduto;
import entidade.InterRegistoInternamento;
import entidade.RhFuncionario;
import java.io.Serializable;
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
import sessao.FarmProdutoFacade;
import sessao.InterMedicacaoFacade;
import sessao.InterMedicacaoHasFarmProdutoFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterMedicacaoNovoBean implements Serializable
{

    @EJB
    private FarmProdutoFacade farmProdutoFacade;

    @EJB
    private InterMedicacaoHasFarmProdutoFacade interMedicacaoHasFarmProdutoFacade;

    @EJB
    private InterMedicacaoFacade interMedicacaoFacade;

    @Resource
    private UserTransaction userTransaction;

    private final Calendar dataCorrente = Calendar.getInstance();

    private String dose, unidadeMedida;

    private InterMedicacaoHasFarmProduto medicacaoProdutoOral;

    private boolean salvou = false;

    private InterMedicacao medicacao;

    private int fkIdViaAdministracao, fkIdFormaFarmaceutica, sos;

    /**
     * Creates a new instance of InterMedicacaoOralNovoBean
     */
    public InterMedicacaoNovoBean()
    {
        dose = ""+ 1;
    }

    public static InterMedicacaoNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterMedicacaoNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interMedicacaoOralNovoBean");
    }

    public InterMedicacao getInstancia()
    {
        InterMedicacao md = new InterMedicacao();
        md.setFkIdRegistoInternamento(new InterRegistoInternamento());
        md.setFkIdFuncionario(new RhFuncionario());

        return md;
    }

    public InterMedicacao getMedicacao()
    {
        if (medicacao == null)
        {
            medicacao = getInstancia();
        }
        return medicacao;
    }

    public void setMedicacao(InterMedicacao medicacao)
    {
        this.medicacao = medicacao;
    }

    public InterMedicacaoHasFarmProduto getInstanciaMedicacaoProduto()
    {
        InterMedicacaoHasFarmProduto mdP = new InterMedicacaoHasFarmProduto();
        mdP.setFkIdProduto(new FarmProduto());
        mdP.setFkIdMedicacao(new InterMedicacao());
        mdP.setFkIdHoraMedicacao(new InterHoraMedicacao());

        return mdP;
    }

    public int getFkIdViaAdministracao()
    {
        return fkIdViaAdministracao;
    }

    public void setFkIdViaAdministracao(int fkIdViaAdministracao)
    {
        this.fkIdViaAdministracao = fkIdViaAdministracao;
    }

    public int getFkIdFormaFarmaceutica()
    {
        return fkIdFormaFarmaceutica;
    }

    public int getSos()
    {
        return sos;
    }

    public void setSos(int sos)
    {
        this.sos = sos;
    }

    public void setFkIdFormaFarmaceutica(int fkIdFormaFarmaceutica)
    {
        this.fkIdFormaFarmaceutica = fkIdFormaFarmaceutica;
    }

    public InterMedicacaoHasFarmProduto getMedicacaoProdutoOral()
    {
        if (medicacaoProdutoOral == null)
        {
            medicacaoProdutoOral = getInstanciaMedicacaoProduto();
        }
        return medicacaoProdutoOral;
    }

    public void setMedicacaoProdutoOral(InterMedicacaoHasFarmProduto medicacaoProdutoOral)
    {
        this.medicacaoProdutoOral = medicacaoProdutoOral;
    }

    public boolean isSalvou()
    {
        return salvou;
    }

    public void setSalvou(boolean salvou)
    {
        this.salvou = salvou;
    }

    public String getDose()
    {
        return dose;
    }

    public void setDose(String dose)
    {
        this.dose = dose;
    }

    public String getUnidadeMedida()
    {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida)
    {
        this.unidadeMedida = unidadeMedida;
    }

    public void salvar()
    {
        if (medicacaoProdutoOral.getFkIdProduto().getPkIdProduto() == null)
        {
            Mensagem.warnMsg("O medicamento é obrigatório!");
        }
        else if (medicacaoProdutoOral.getFkIdHoraMedicacao().getPkIdHoraMedicacao() == null)
        {
            Mensagem.warnMsg("A hora é obrigatória!");
        }
        else if (dose.equals(""))
        {
            Mensagem.warnMsg("A quantidade é obrigatória!");
        }
        else
        {
            try
            {
                userTransaction.begin();

                if (!salvou)
                {
                    InterRegistoInternamento registo = InterControloDiarioBean.getInstanciaBean().getRegistoInternamento();

                    getMedicacao().getFkIdRegistoInternamento().setPkIdRegistoInternamento(registo.getPkIdRegistoInternamento());
                    getMedicacao().setDataHora(dataCorrente.getTime());
                    getMedicacao().getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());

                    interMedicacaoFacade.create(getMedicacao());

                    salvou = true;
                }

                List<InterMedicacao> listaMd = interMedicacaoFacade.findAll();

                medicacaoProdutoOral.getFkIdMedicacao().setPkIdMedicacao(listaMd.get(listaMd.size() - 1).getPkIdMedicacao());
                medicacaoProdutoOral.setDose(dose);
                medicacaoProdutoOral.setSOS(getSOS());

                interMedicacaoHasFarmProdutoFacade.create(medicacaoProdutoOral);

//            InterMedicacaoListarBean.getInstanciaBean().pesquisarPorViaAdms(1);
                userTransaction.commit();
                Mensagem.sucessoMsg("Medicamento adicionado com sucesso.");

                limparCampos();
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

    private boolean getSOS()
    {
        if (sos == 1)
        {
            return true;
        }
        return false;
    }

    public void limparCampos()
    {
        medicacao = null;
        medicacaoProdutoOral = null;
        dose = ""+ 1;
        unidadeMedida = null;
    }

    /*De Farmácia*/
    public List<FarmProduto> lerTodosMedicamentos()
    {
        return farmProdutoFacade.findAll();
    }

    public List<FarmProduto> lerTodosMedicamentosPorViaAdmissao()
    {
        int categoria;

        if (InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel1().getPkIdCategoriaMedicamento() == null)
        {
            categoria = 0;
        }

        else if (InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel2().getPkIdCategoriaMedicamento() == null)
        {
            categoria = InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel1().getPkIdCategoriaMedicamento();
        }

        else if (InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel3().getPkIdCategoriaMedicamento() == null)
        {
            categoria = InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel2().getPkIdCategoriaMedicamento();
        }

        else if (InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel4().getPkIdCategoriaMedicamento() == null)
        {
            categoria = InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel3().getPkIdCategoriaMedicamento();
        }
        else
        {
            categoria = InterFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel4().getPkIdCategoriaMedicamento();
        }

        List<FarmProduto> lista = interMedicacaoHasFarmProdutoFacade.produtosPorViaAdmissao(fkIdViaAdministracao, fkIdFormaFarmaceutica, categoria);
        List<FarmProduto> listaAux = new ArrayList();

        for (int i = 0; i < lista.size(); i++)
        {
            if (InterMedicacaoListarBean.getInstanciaBean().pesquisarPorViaAdms(lista.get(i).getPkIdProduto()).isEmpty())
            {
                listaAux.add(lista.get(i));
            }
        }

        return listaAux;
    }

    public FarmProduto lerMedicamentoPorNome(String nomeMedicamento)
    {
        return interMedicacaoFacade.pesquisarMedicamentoPorNome(nomeMedicamento);
    }
}
