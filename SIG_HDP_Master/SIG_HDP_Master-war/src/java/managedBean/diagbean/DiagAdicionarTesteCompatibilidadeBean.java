/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsPaciente;
import entidade.DiagBolsaSangue;
import entidade.DiagCandidatoDador;
import entidade.DiagNumeroDoacao;
import entidade.DiagResultadoTesteCompatibilidade;
import entidade.DiagTesteCompatibilidade;
import entidade.DiagTipagemDador;
import entidade.DiagTipagemDoente;
import entidade.DiagTipoDoacao;
import entidade.GrlCentroHospitalar;
import entidade.GrlComuna;
import entidade.GrlContacto;
import entidade.GrlDistrito;
import entidade.GrlEndereco;
import entidade.GrlEstadoCivil;
import entidade.GrlFicheiroAnexado;
import entidade.GrlInstituicao;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlPessoa;
import entidade.GrlReligiao;
import entidade.GrlSexo;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.DiagBolsaSangueFacade;
import sessao.DiagCandidatoDadorFacade;
import sessao.DiagResultadoTesteCompatibilidadeFacade;
import sessao.DiagTesteCompatibilidadeFacade;
import sessao.DiagTipagemDadorFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagAdicionarTesteCompatibilidadeBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagBolsaSangueFacade diagBolsaSangueFacade;

    @EJB
    private DiagTesteCompatibilidadeFacade diagTesteCompatibilidadeFacade;
    @EJB
    private DiagResultadoTesteCompatibilidadeFacade diagResultadoTesteCompatibilidadeFacade;

    @EJB
    private DiagTipagemDadorFacade diagTipagemDadorFacade;

    @EJB
    private DiagCandidatoDadorFacade diagCandidatoDadorFacade;

    private DiagCandidatoDador diagCandidatoDadorPesquisar, diagCandidatoDadorVisualizar, diagCandidatoDadorAdicionarTeste;

    private DiagTipagemDador diagTipagemDadorVisualizar;

    private DiagTipagemDoente diagTipagemDoente;

    private DiagTesteCompatibilidade diagTesteCompatibilidade;

    private DiagResultadoTesteCompatibilidade diagResultadoTesteCompatibilidade;

    private AdmsPaciente admsPaciente;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private boolean pesquisar;

    private boolean eCompativel = false;

    private String stringResultadoCompatibilidade = "";

    public static DiagAdicionarTesteCompatibilidadeBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagAdicionarTesteCompatibilidadeBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagAdicionarTesteCompatibilidadeBean");
    }
    
    public DiagTesteCompatibilidade getDiagTesteCompatibilidade()
    {
        if (diagTesteCompatibilidade == null)
        {
            diagTesteCompatibilidade = new DiagTesteCompatibilidade();
            diagTesteCompatibilidade.setFkIdBolsaSangue(new DiagBolsaSangue());
            diagTesteCompatibilidade.setFkIdPaciente(new AdmsPaciente());
            diagTesteCompatibilidade.setFkIdResultadoTesteCompatibilidade(new DiagResultadoTesteCompatibilidade());
        }
        return diagTesteCompatibilidade;
    }

    public void setDiagTesteCompatibilidade(DiagTesteCompatibilidade diagTesteCompatibilidade)
    {
        this.diagTesteCompatibilidade = diagTesteCompatibilidade;
    }

    public AdmsPaciente getAdmsPaciente()
    {
        if (admsPaciente == null)
        {
            admsPaciente = getInstanciaPaciente();
        }
        return admsPaciente;
    }

    public void setAdmsPaciente(AdmsPaciente admsPaciente)
    {
        this.admsPaciente = admsPaciente;
    }

    public AdmsPaciente getInstanciaPaciente()
    {
        GrlFicheiroAnexado foto = new GrlFicheiroAnexado();
        foto.setFicheiro(Constantes.FOTO_DEFAULT);

        AdmsPaciente pacient = new AdmsPaciente();

        pacient.setFkIdPessoa(new GrlPessoa());
        pacient.getFkIdPessoa().setFkIdFoto(foto);
        pacient.getFkIdPessoa().setFkIdEstadoCivil(new GrlEstadoCivil());
        pacient.getFkIdPessoa().setFkIdEndereco(new GrlEndereco());
        pacient.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(new GrlComuna());
//        pacient.getFkIdPessoa().setFkIdDocumentoIdentificacao(new GrlDocumentoIdentificacao());
//        pacient.getFkIdPessoa().getFkIdDocumentoIdentificacao().setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());
//      cand.getFkIdPessoa().setFkIdConjugePessoa(new GrlConjugePessoa());
        pacient.getFkIdPessoa().setFkIdContacto(new GrlContacto());
        pacient.getFkIdPessoa().setFkIdSexo(new GrlSexo());
        pacient.getFkIdPessoa().setFkIdReligiao(new GrlReligiao());
        pacient.getFkIdPessoa().setFkIdNacionalidade(new GrlPais());

        return pacient;
    }

    public DiagCandidatoDador getDiagCandidatoDadorPesquisar()
    {
        if (diagCandidatoDadorPesquisar == null)
        {
            diagCandidatoDadorPesquisar = getInstanciaCandidatoDador();
        }
        return diagCandidatoDadorPesquisar;
    }

    public void setDiagCandidatoDadorPesquisar(DiagCandidatoDador diagCandidatoDadorPesquisar)
    {
        this.diagCandidatoDadorPesquisar = diagCandidatoDadorPesquisar;
    }

    public DiagCandidatoDador getDiagCandidatoDadorVisualizar()
    {
        if (diagCandidatoDadorVisualizar == null)
        {
            diagCandidatoDadorVisualizar = getInstanciaCandidatoDador();
        }
        return diagCandidatoDadorVisualizar;
    }

    public void setDiagCandidatoDadorVisualizar(DiagCandidatoDador diagCandidatoDadorVisualizarAux)
    {
        if (diagCandidatoDadorVisualizarAux != null)
        {
            diagTipagemDadorVisualizar = diagTipagemDadorFacade.findTipagemDador(diagCandidatoDadorVisualizarAux);
        }
        this.diagCandidatoDadorVisualizar = diagCandidatoDadorVisualizarAux;
    }

    public DiagCandidatoDador getDiagCandidatoDadorAdicionarTeste()
    {
        if (diagCandidatoDadorAdicionarTeste == null)
        {
            diagCandidatoDadorAdicionarTeste = getInstanciaCandidatoDador();
        }
        return diagCandidatoDadorAdicionarTeste;
    }

    public void setDiagCandidatoDadorAdicionarTeste(DiagCandidatoDador diagCandidatoDadorAdicionarTeste)
    {
        this.diagCandidatoDadorAdicionarTeste = diagCandidatoDadorAdicionarTeste;
    }

    public DiagCandidatoDador getInstanciaCandidatoDador()
    {

        DiagCandidatoDador cand = new DiagCandidatoDador();
//        GrlDocumentoIdentificacao docID = new GrlDocumentoIdentificacao();
//        docID.setFkTipoDocumentoIdentificacao( new GrlTipoDocumentoIdentificacao() );

        cand.setFkIdPessoa(new GrlPessoa());
        cand.getFkIdPessoa().setFkIdEstadoCivil(new GrlEstadoCivil());
        cand.getFkIdPessoa().setFkIdEndereco(new GrlEndereco());
        cand.getFkIdPessoa().getFkIdEndereco().setFkIdComuna(new GrlComuna());
        cand.getFkIdPessoa().getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
        cand.getFkIdPessoa().getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
//        cand.getFkIdPessoa().getGrlDocumentoIdentificacaoList().set(0, docID);
//      cand.getFkIdPessoa().setFkIdConjugePessoa(new GrlConjugePessoa());
        cand.getFkIdPessoa().setFkIdContacto(new GrlContacto());
        cand.getFkIdPessoa().setFkIdSexo(new GrlSexo());
        cand.getFkIdPessoa().setFkIdReligiao(new GrlReligiao());
        cand.getFkIdPessoa().setFkIdNacionalidade(new GrlPais());

        cand.setDataUltimaDadiva(null);
        cand.setFkIdInstituicaoUltimaDadiva(new GrlInstituicao());
        cand.setFkIdNumeroDoacao(new DiagNumeroDoacao());
        cand.setFkIdTipoDoacao(new DiagTipoDoacao());

        return cand;
    }

    public DiagTipagemDador getDiagTipagemDadorVisualizar()
    {
        return diagTipagemDadorVisualizar;
    }

    public void setDiagTipagemDadorVisualizar(DiagTipagemDador diagTipagemDadorVisualizar)
    {
        this.diagTipagemDadorVisualizar = diagTipagemDadorVisualizar;
    }

    public String getStringResultadoCompatibilidade()
    {
        return stringResultadoCompatibilidade;
    }

    public void setStringResultadoCompatibilidade(String stringResultadoCompatibilidade)
    {
        this.stringResultadoCompatibilidade = stringResultadoCompatibilidade;
    }

    public List<DiagResultadoTesteCompatibilidade> getListaResultadosTesteCompatibilidade()
    {
        return diagResultadoTesteCompatibilidadeFacade.findAll();
    }

    public boolean isTemMensagemPendente()
    {
        return temMensagemPendente;
    }

    public void setTemMensagemPendente(boolean temMensagemPendente)
    {
        this.temMensagemPendente = temMensagemPendente;
    }

    public String getMensagemPendente()
    {
        return mensagemPendente;
    }

    public void setMensagemPendente(String mensagemPendente)
    {
        this.mensagemPendente = mensagemPendente;
    }

    public String getTipoMensagemPendente()
    {
        return tipoMensagemPendente;
    }

    public void setTipoMensagemPendente(String tipoMensagemPendente)
    {
        this.tipoMensagemPendente = tipoMensagemPendente;
    }

    public void getMensagem()
    {
        if (tipoMensagemPendente == "Sucesso")
        {
            Mensagem.sucessoMsg(mensagemPendente);

            temMensagemPendente = false;
        }
        else
        {
            Mensagem.erroMsg(mensagemPendente);

            temMensagemPendente = false;
        }
    }

    public boolean getPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagCandidatoDador> pesquisarCandidatos()
    {
        if (pesquisar)
        {
            List<DiagCandidatoDador> pp;
            pp = diagCandidatoDadorFacade.findCandidatoTesteCompatibilidade(diagCandidatoDadorPesquisar.getFkIdPessoa(), admsPaciente);
            if (pp.isEmpty() || pp == null)
            {
                System.out.println("pesquisou");
                Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
            }

            return pp;
        }

        return null;
    }

    public void selecionarCandidatoAdicionarTeste(DiagCandidatoDador diagCandidatoDadorAux)
    {
        diagCandidatoDadorAdicionarTeste = diagCandidatoDadorAux;

        System.out.println("dador adicionar teste: " + diagCandidatoDadorAdicionarTeste.getFkIdPessoa().getNome());

        if (eCompativel(diagTipagemDoente, diagTipagemDadorFacade.findTipagemDador(diagCandidatoDadorAdicionarTeste)))
        {
            stringResultadoCompatibilidade = "Compatível";
        }
        else
        {
            stringResultadoCompatibilidade = "Incompatível";
        }

    }

    public boolean eCompativel(DiagTipagemDoente tipagemDoente, DiagTipagemDador tipagemDador)
    {
        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("A Rh+"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("A Rh+")
                    || tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }
        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("A Rh-"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("A Rh-")
                    || tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }
        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("B Rh+"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("B Rh+")
                    || tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }
        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("B Rh-"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("B Rh-")
                    || tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }
        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("AB Rh+"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("A Rh+")
                    || tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }
        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("AB Rh-"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("A Rh-")
                    || tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }

        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh+"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh+")
                    || tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }

        if (tipagemDoente.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
        {
            if (tipagemDador.getConclusao().getDescricaoGrupoSanguineo().equals("O Rh-"))
            {
                return true;
            }
        }
        return false;
    }

    public String registarTesteCompatibilidade()
    {
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagTesteCompatibilidade.setData(new Date(data.getTimeInMillis()));
            diagTesteCompatibilidade.setFkIdPaciente(admsPaciente);
            diagTesteCompatibilidade.setFkIdBolsaSangue(diagBolsaSangueFacade.findBolsasDeSangueDador(diagCandidatoDadorAdicionarTeste.getFkIdPessoa()).get(0));

            eCompativel = eCompativel(diagTipagemDoente, diagTipagemDadorFacade.findTipagemDador(diagCandidatoDadorAdicionarTeste));

            diagResultadoTesteCompatibilidade = DiagResultadoTesteCompatibilidadeBean.getInstancia();

            if (eCompativel)
            {
                diagResultadoTesteCompatibilidade = diagResultadoTesteCompatibilidadeFacade.find(1);

                stringResultadoCompatibilidade = "Compatível";
            }
            else
            {
                diagResultadoTesteCompatibilidade = diagResultadoTesteCompatibilidadeFacade.find(2);

                stringResultadoCompatibilidade = "Incompatível";
            }

            diagTesteCompatibilidade.setFkIdResultadoTesteCompatibilidade(diagResultadoTesteCompatibilidade);

            diagTesteCompatibilidadeFacade.create(diagTesteCompatibilidade);

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Teste de compatibilidade salvo com sucesso!";

            diagTesteCompatibilidade = null;
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }

        }

        return "adicionarTesteCompatibilidade.xhtml?faces-redirect=true";
    }

    public String redirecionarAdicionarTesteCompatibilidade(AdmsPaciente pacienteAux, DiagTipagemDoente tipagemPacienteAux)
    {
        admsPaciente = pacienteAux;

        diagTipagemDoente = new DiagTipagemDoente();
        diagTipagemDoente = tipagemPacienteAux;

        return "adicionarTesteCompatibilidade.xhtml?faces-redirect=true";
    }

    public String voltarParaVisualizacaoTestesCompatibilidade()
    {
        admsPaciente = null;

        return "visualizarTestesCompatibilidade.xhtml?faces-redirect=true";
    }

    public String limparPesquisaCandidado()
    {

        pesquisar = false;

        diagCandidatoDadorPesquisar = null;

        return "adicionarTesteCompatibilidade.xhtml?faces-redirect=true";
    }
}
