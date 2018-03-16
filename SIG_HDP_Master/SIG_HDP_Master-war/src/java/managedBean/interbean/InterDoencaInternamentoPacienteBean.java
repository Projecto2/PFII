/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.AmbCidSubcategorias;
import entidade.InterDoencaInternamentoPaciente;
import entidade.InterRegistoInternamento;
import entidade.InterTipoDoencaInternamento;
import entidade.RhFuncionario;
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
import org.primefaces.model.DualListModel;
import sessao.AmbCidSubcategoriasFacade;
import sessao.InterDoencaInternamentoPacienteFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterDoencaInternamentoPacienteBean implements Serializable
{

    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;

    @EJB
    private InterDoencaInternamentoPacienteFacade interDoencaInternamentoPacienteFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterDoencaInternamentoPaciente doencaPaciente;

    private String doencaPesq, nomeFuncionario, sobreNomeFuncionario;

    private int funcionario, tipoDoenca;

    private InterRegistoInternamento registoInternamento;

    private Date dataRegisto1, dataRegisto2;

    private final Calendar dataCorrente = Calendar.getInstance();

    private List<InterDoencaInternamentoPaciente> listaDoencasPaciente;

    private SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();

    private String doenca;
    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

    /**
     * Creates a new instance of DoencaInternamentoPacienteBean
     */
    public InterDoencaInternamentoPacienteBean()
    {

    }

    public static InterDoencaInternamentoPacienteBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterDoencaInternamentoPacienteBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interDoencaInternamentoPacienteBean");
    }

    public InterDoencaInternamentoPaciente getInstancia()
    {
        InterDoencaInternamentoPaciente d = new InterDoencaInternamentoPaciente();
        d.setFkIdRegistoInternamento(new InterRegistoInternamento());
        d.setFkIdFuncionario(new RhFuncionario());
        d.setFkIdTipoDoencaIntenamento(new InterTipoDoencaInternamento());
        d.setFkIdCidSubcategorias(new AmbCidSubcategorias());
        d.setFkIdResultadoDoenca(null);

        return d;
    }

    public InterRegistoInternamento getRegistoInternamento()
    {
        return registoInternamento;
    }

    public void setRegistoInternamento(InterRegistoInternamento registoInternamento)
    {
        this.registoInternamento = registoInternamento;
    }

    public InterDoencaInternamentoPaciente getDoencaPaciente()
    {
        if (doencaPaciente == null)
        {
            doencaPaciente = getInstancia();
        }
        return doencaPaciente;
    }

    public void setDoencaPaciente(InterDoencaInternamentoPaciente doencaPaciente)
    {
        this.doencaPaciente = doencaPaciente;
    }

    public String getDoencaPesq()
    {
        return doencaPesq;
    }

    public void setDoencaPesq(String doencaPesq)
    {
        this.doencaPesq = doencaPesq;
    }

    public int getFuncionario()
    {
        return funcionario;
    }

    public void setFuncionario(int funcionario)
    {
        this.funcionario = funcionario;
    }

    public int getTipoDoenca()
    {
        return tipoDoenca;
    }

    public void setTipoDoenca(int tipoDoenca)
    {
        this.tipoDoenca = tipoDoenca;
    }

    public List<InterDoencaInternamentoPaciente> getListaDoencasPaciente()
    {
        return listaDoencasPaciente;
    }

    public void setListaDoencasPaciente(List<InterDoencaInternamentoPaciente> listaDoencasPaciente)
    {
        this.listaDoencasPaciente = listaDoencasPaciente;
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

    public String getNomeFuncionario()
    {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario)
    {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getSobreNomeFuncionario()
    {
        return sobreNomeFuncionario;
    }

    public void setSobreNomeFuncionario(String sobreNomeFuncionario)
    {
        this.sobreNomeFuncionario = sobreNomeFuncionario;
    }

    public String getDoenca()
    {
        return doenca;
    }

    public void setDoenca(String doenca)
    {
        this.doenca = doenca;
    }

    public InterDoencaInternamentoPacienteFacade getInterDoencaInternamentoPacienteFacade()
    {
        return interDoencaInternamentoPacienteFacade;
    }

    public void setInterDoencaInternamentoPacienteFacade(InterDoencaInternamentoPacienteFacade interDoencaInternamentoPacienteFacade)
    {
        this.interDoencaInternamentoPacienteFacade = interDoencaInternamentoPacienteFacade;
    }

    public void salvarDoencaCoexistente(Long pkIdRegistoInternamento)
    {
        List<String> listaPkIdSubcategoriasDasDoencasSeleccionadas = InterCidHipoteseDiagnosticoBean.getInstanciaBean().getListaPkIdSubcategoriasDasDoencasSeleccionadas();

        if (listaPkIdSubcategoriasDasDoencasSeleccionadas != null)
        {
            try
            {
                for (int i = 0; i < listaPkIdSubcategoriasDasDoencasSeleccionadas.size(); i++)
                {

                    getDoencaPaciente().getFkIdRegistoInternamento().setPkIdRegistoInternamento(pkIdRegistoInternamento);
                    getDoencaPaciente().getFkIdFuncionario().setPkIdFuncionario(segConta.getFkIdFuncionario().getPkIdFuncionario());
                    getDoencaPaciente().setDataDiagnostico(dataCorrente.getTime());
                    getDoencaPaciente().setDataRegisto(dataCorrente.getTime());
                    getDoencaPaciente().getFkIdCidSubcategorias().setPkIdSubcategorias(listaPkIdSubcategoriasDasDoencasSeleccionadas.get(i));

                    getDoencaPaciente().getFkIdTipoDoencaIntenamento().setPkIdTipoDoencaInternamento(1);

                    if (interDoencaInternamentoPacienteFacade.pesquisarRegisto(tipoServico, null, null, null, null, pkIdRegistoInternamento, null, getDoencaPaciente().getFkIdTipoDoencaIntenamento().getPkIdTipoDoencaInternamento(), listaPkIdSubcategoriasDasDoencasSeleccionadas.get(i)).isEmpty())
                    {
                        interDoencaInternamentoPacienteFacade.create(getDoencaPaciente());
                    }

                    limparCampos();

                }

                InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().getDualList().getTarget().clear();

                InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().setDualList(new DualListModel(InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().getDualList().getSource(), InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().getDualList().getTarget()));

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

    public void salvarDoencaIntercorrente()
    {
        List<String> listaPkIdSubcategoriasDasDoencasSeleccionadas = InterCidHipoteseDiagnosticoBean.getInstanciaBean().getListaPkIdSubcategoriasDasDoencasSeleccionadas();

        if (listaPkIdSubcategoriasDasDoencasSeleccionadas == null)
        {
            Mensagem.warnMsg("Por favor seleccione uma ou mais doenças!");
        }
        else
        {
            try
            {
                userTransaction.begin();

                for (int i = 0; i < listaPkIdSubcategoriasDasDoencasSeleccionadas.size(); i++)
                {

                    getDoencaPaciente().getFkIdRegistoInternamento().setPkIdRegistoInternamento(registoInternamento.getPkIdRegistoInternamento());
                    getDoencaPaciente().getFkIdFuncionario().setPkIdFuncionario(segConta.getFkIdFuncionario().getPkIdFuncionario());
                    getDoencaPaciente().setDataDiagnostico(dataCorrente.getTime());
                    getDoencaPaciente().setDataRegisto(dataCorrente.getTime());
                    getDoencaPaciente().getFkIdCidSubcategorias().setPkIdSubcategorias(listaPkIdSubcategoriasDasDoencasSeleccionadas.get(i));

                    getDoencaPaciente().getFkIdTipoDoencaIntenamento().setPkIdTipoDoencaInternamento(2);

                    if (interDoencaInternamentoPacienteFacade.pesquisarRegisto(tipoServico, null, null, null, null, registoInternamento.getPkIdRegistoInternamento(), null, getDoencaPaciente().getFkIdTipoDoencaIntenamento().getPkIdTipoDoencaInternamento(), listaPkIdSubcategoriasDasDoencasSeleccionadas.get(i)).isEmpty())
                    {
                        interDoencaInternamentoPacienteFacade.create(getDoencaPaciente());
                    }

                    limparCampos();
                }

                InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().getDualList().getTarget().clear();

                InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().setDualList(new DualListModel(InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().getDualList().getSource(), InterCidHipoteseDiagnosticoBean.getInstanciaBean().getDoencasPickList().getDualList().getTarget()));

                userTransaction.commit();

                Mensagem.sucessoMsg("Doença(s) Intercorrentes registada(s) com sucesso!");

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

    public void salvarDoencaPrincipal(Long pkIdRegistoInternamento, String doencaPrincipal)
    {

        try
        {
            getDoencaPaciente().getFkIdRegistoInternamento().setPkIdRegistoInternamento(pkIdRegistoInternamento);
            getDoencaPaciente().getFkIdFuncionario().setPkIdFuncionario(segConta.getFkIdFuncionario().getPkIdFuncionario());
            getDoencaPaciente().setDataDiagnostico(dataCorrente.getTime());
            getDoencaPaciente().setDataRegisto(dataCorrente.getTime());
            getDoencaPaciente().getFkIdCidSubcategorias().setPkIdSubcategorias(ambCidSubcategoriasFacade.getAmbCidSubcategoriasFromNomeDoenca(doencaPrincipal).getPkIdSubcategorias());

            getDoencaPaciente().getFkIdTipoDoencaIntenamento().setPkIdTipoDoencaInternamento(3);

            interDoencaInternamentoPacienteFacade.create(getDoencaPaciente());

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

    public void limparCampos()
    {
        doencaPaciente = null;
        doenca = null;
    }

    public String pagina(InterRegistoInternamento registoInternamamento)
    {
        listaDoencasPaciente = new ArrayList();

        this.registoInternamento = registoInternamamento;

        return "/faces/interVisao/interInternamento/internamentoListar/doencaInternamentoPacienteListarInter.xhtml?faces-redirect=true";
    }

    public void pesquisar(Long pkIdRegistoInternamento, int opcao)
    {
        listaDoencasPaciente = interDoencaInternamentoPacienteFacade.pesquisarRegisto(tipoServico, nomeFuncionario, sobreNomeFuncionario, dataRegisto1, dataRegisto2, pkIdRegistoInternamento, doencaPesq, tipoDoenca, null);

        if (opcao == 1)
        {
            if (listaDoencasPaciente.isEmpty())
            {
                Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
            }
            else
            {
                Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaDoencasPaciente.size() + " registo(s) retornado(s).");
            }
        }
    }

    public List<InterDoencaInternamentoPaciente> findAllDoencasPaciente(Long pkIdRegistoInternamento)
    {
        List<InterDoencaInternamentoPaciente> lista = interDoencaInternamentoPacienteFacade.pesquisarRegisto(tipoServico, null, null, null, null, pkIdRegistoInternamento, null, 0, null);
        
        return lista;
    }
    
    public String titulo()
    {
//        if (opcao == 1)
//        {
//            return "Registar Doenças Coexistentes";
//        }
        return "Registar Doenças Intercorrentes";
    }
}
