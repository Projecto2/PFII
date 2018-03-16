/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.admsbean;

import entidade.AdmsPaciente;
import entidade.AdmsPrimieroCarregamentoPacientes;
import entidade.GrlEstadoCivil;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlSexo;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.paciente.AdmsPacienteNovoBean;
import sessao.AdmsPacienteFacade;
import sessao.AdmsPrimieroCarregamentoPacientesFacade;
import sessao.GrlContactoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlPessoaFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsCarregamentoInicialPacientesBean implements Serializable
{
    @EJB
    private AdmsPrimieroCarregamentoPacientesFacade admsPrimieroCarregamentoPacientesFacade1;
    @EJB
    private GrlContactoFacade grlContactoFacade;
    @EJB
    private GrlEnderecoFacade grlEnderecoFacade;

    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    @EJB
    private GrlPessoaFacade grlPessoaFacade;
    @EJB
    private AdmsPrimieroCarregamentoPacientesFacade admsPrimieroCarregamentoPacientesFacade;
    
    

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of AdmsCarregamentoInicialPacientes
     */
    public AdmsCarregamentoInicialPacientesBean()
    {
    }
    
    
    public static AdmsCarregamentoInicialPacientesBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsCarregamentoInicialPacientesBean admsCarregamentoInicialPacientesBean = 
            (AdmsCarregamentoInicialPacientesBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsCarregamentoInicialPacientesBean");
        
        return admsCarregamentoInicialPacientesBean;
    }
    

    public void carregarPacientesCasoAindaNaoTenhamSidoCarregados()
    {
        if (!admsPrimieroCarregamentoPacientesFacade.jaExistiuCarregamento())
        {

            try
            {
                
                for (int i = 0; i < 70000; i++)
                {
                    
                    AdmsPaciente pacienteParaGravar = new AdmsPaciente();
                    pacienteParaGravar = AdmsPacienteNovoBean.getInstanciaBean().getInstanciaPaciente();
                    pacienteParaGravar.getFkIdPessoa().setNome("Paciente");
                    pacienteParaGravar.getFkIdPessoa().setNomeDoMeio("Paciente");
                    pacienteParaGravar.getFkIdPessoa().setSobreNome("Paciente");
                    pacienteParaGravar.getFkIdPessoa().setNomeMae("Paciente Mãe");
                    pacienteParaGravar.getFkIdPessoa().setNomePai("Paciente Pai");
                    pacienteParaGravar.getFkIdPessoa().setDataCadastro(new Date());
                    pacienteParaGravar.getFkIdPessoa().setDataNascimento(new Date());
                    pacienteParaGravar.getFkIdPessoa().setFkIdEstadoCivil(new GrlEstadoCivil(1));
                    pacienteParaGravar.getFkIdPessoa().setFkIdNacionalidade(new GrlPais(1));
                    pacienteParaGravar.getFkIdPessoa().setFkIdReligiao(null);
                    pacienteParaGravar.getFkIdPessoa().setFkIdSexo(new GrlSexo(1));
//                    pacienteParaGravar.getFkIdPessoa().getFkIdContacto().setTelefone1("000000000");
                    pacienteParaGravar.getFkIdPessoa().getFkIdContacto().setTelefone1("000000000");
//                    pacienteParaGravar.getFkIdPessoa().setFkIdEndereco(new GrlEndereco());
                    pacienteParaGravar.getFkIdPessoa().getFkIdEndereco().setFkIdDistrito(null);
                    pacienteParaGravar.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(null);
                    pacienteParaGravar.getFkIdPessoa().getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(1));
                    pacienteParaGravar.getFkIdPessoa().setFkIdGrupoSanguineo(null);
                    pacienteParaGravar.getFkIdPessoa().setFkIdFoto(null);

                    userTransaction.begin();
                        grlContactoFacade.create(pacienteParaGravar.getFkIdPessoa().getFkIdContacto()); 
                        grlEnderecoFacade.create(pacienteParaGravar.getFkIdPessoa().getFkIdEndereco());
                        grlPessoaFacade.create(pacienteParaGravar.getFkIdPessoa());
                    userTransaction.commit();

                    userTransaction.begin();
                        pacienteParaGravar.setNumeroPaciente("P" + pacienteParaGravar.getFkIdPessoa().getPkIdPessoa());
                        admsPacienteFacade.create(pacienteParaGravar);
                    userTransaction.commit();
                }
                userTransaction.begin();
                    AdmsPrimieroCarregamentoPacientes primeiroCarregamento = new AdmsPrimieroCarregamentoPacientes();
                    primeiroCarregamento.setData(new Date());
                    admsPrimieroCarregamentoPacientesFacade.create(primeiroCarregamento);
                userTransaction.commit();
                
                Mensagem.sucessoMsg("Carregamento Inicial Feito Com Sucesso");

            }
            catch (Exception ex)
            {
                try
                {
                    userTransaction.rollback();
                    Mensagem.erroMsg(ex.toString());
                }
                catch (IllegalStateException | SecurityException | SystemException excep)
                {
                    Mensagem.erroMsg("Rollback: " + ex.toString());
                }
            }

        }
    }

}
