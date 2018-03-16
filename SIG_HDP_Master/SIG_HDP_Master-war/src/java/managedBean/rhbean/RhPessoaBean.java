/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.GrlComuna;
import entidade.GrlContacto;
import entidade.GrlDistrito;
import entidade.GrlDocumentoIdentificacao;
import entidade.GrlEndereco;
import entidade.GrlEstadoCivil;
import entidade.GrlFicheiroAnexado;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlPessoa;
import entidade.GrlReligiao;
import entidade.GrlSexo;
import entidade.GrlTipoDocumentoIdentificacao;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlContactoFacade;
import sessao.GrlDocumentoIdentificacaoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.GrlMunicipioFacade;
import sessao.GrlPessoaFacade;
import util.Constantes;
import util.ItensAjaxBean;
import util.Mensagem;
import util.UploadFicheiro;

/**
 *
 * @author Ornela F. Boaventura
 * @author Garcia Paulo
 */
@ManagedBean
@SessionScoped
public class RhPessoaBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private GrlContactoFacade contactoFacade;
    @EJB
    private GrlMunicipioFacade municipioFacade;
    @EJB
    private GrlEnderecoFacade enderecoFacade;
    @EJB
    private GrlDocumentoIdentificacaoFacade documentoIdentificacaoFacade;
    @EJB
    private GrlPessoaFacade pessoaFacade;

    /**
     * Entidade pessoa
     */
    private List<GrlPessoa> pessoasPesquisadasList;
    private GrlPessoa pessoa, pessoaPesquisa, pessoaVisualizar;

    private Part fotoCarregada;

    private int tipoDocumento;

    private String numeroDocumento;

    public RhPessoaBean ()
    {
    }

    public static RhPessoaBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhPessoaBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhPessoaBean");
    }

    public static GrlPessoa getInstancia ()
    {
        GrlFicheiroAnexado foto = new GrlFicheiroAnexado();
        foto.setFicheiro(Constantes.FOTO_DEFAULT);

        GrlPessoa pes = new GrlPessoa();
        pes.setFkIdFoto(foto);
        pes.setFkIdEstadoCivil(new GrlEstadoCivil());
        pes.setFkIdEndereco(new GrlEndereco());
        pes.getFkIdEndereco().setFkIdComuna(new GrlComuna());
        pes.getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
        pes.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
//      pes.setFkIdConjugePessoa(new GrlConjugePessoa());
        pes.setFkIdContacto(new GrlContacto());
        pes.setFkIdSexo(new GrlSexo());
        pes.setFkIdReligiao(new GrlReligiao());
        pes.setFkIdNacionalidade(new GrlPais());
        pes.setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());

        return pes;
    }

    public GrlPessoa getPessoa ()
    {
        if (pessoa == null)
        {
            pessoa = getInstancia();
            pessoa.setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());
        }
        checkComunaDistritoReligiaoFoto(pessoa);

        return pessoa;
    }

    public void setPessoa (GrlPessoa pessoa)
    {

        if (pessoa != null)
        {
            pessoa.setGrlDocumentoIdentificacaoList(documentoIdentificacaoFacade.pesquisaPorPessoa(pessoa.getPkIdPessoa()));

            checkComunaDistritoReligiaoFoto(pessoa);

            //Fazendo set de algumas propriedades da ItensAjaxBean para aparecerem selecionados nas combobox da dialog editar
            Integer idMunicipio = pessoa.getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio();

            GrlMunicipio mun = municipioFacade.find(idMunicipio);

            ItensAjaxBean itensAjaxBean = ItensAjaxBean.getInstanciaBean();

            itensAjaxBean.setPais(mun.getFkIdProvincia().getFkIdPais().getPkIdPais());
            itensAjaxBean.setProvincia(mun.getFkIdProvincia().getPkIdProvincia());
            itensAjaxBean.setMunicipio(mun.getPkIdMunicipio());
        }

        this.pessoa = pessoa;
    }

    private void checkComunaDistritoReligiaoFoto (GrlPessoa p)
    {
        if (p.getFkIdFoto() == null)
        {
            p.setFkIdFoto(getInstancia().getFkIdFoto());
        }
        if (p.getFkIdReligiao() == null)
        {
            p.setFkIdReligiao(new GrlReligiao());
        }
        if (p.getFkIdEndereco().getFkIdComuna() == null)
        {
            p.getFkIdEndereco().setFkIdComuna(new GrlComuna());
        }
        if (p.getFkIdEndereco().getFkIdDistrito() == null)
        {
            p.getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
        }
    }

    public GrlPessoa getPessoaPesquisa ()
    {
        if (pessoaPesquisa == null)
        {
            pessoaPesquisa = getInstancia();
            pessoaPesquisa.setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());
            pessoaPesquisa.getGrlDocumentoIdentificacaoList().add(0, new GrlDocumentoIdentificacao());
            pessoaPesquisa.getGrlDocumentoIdentificacaoList().get(0).setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());
        }
        return pessoaPesquisa;
    }

    public void setPessoaPesquisa (GrlPessoa pessoaPesquisa)
    {
        this.pessoaPesquisa = pessoaPesquisa;
    }

    public GrlPessoa getPessoaVisualizar ()
    {
        return pessoaVisualizar;
    }

    public void setPessoaVisualizar (GrlPessoa pessoaVisualizar)
    {
        this.pessoaVisualizar = pessoaVisualizar;
    }

    public List<GrlPessoa> getPessoasPesquisadasList ()
    {
        return pessoasPesquisadasList;
    }

    public void setPessoasPesquisadasList (List<GrlPessoa> pessoasPesquisadasList)
    {
        this.pessoasPesquisadasList = pessoasPesquisadasList;
    }

    public Part getFotoCarregada ()
    {
        return fotoCarregada;
    }

    public void setFotoCarregada (Part fotoCarregada)
    {
        this.fotoCarregada = fotoCarregada;
    }

    public String apresentarFoto ()
    {
        if (getPessoa().getFkIdFoto() == null)
        {
            return null;
        }

        String foto = getPessoa().getFkIdFoto().getFicheiro();
        return (Constantes.PASTA_FOTO + foto).trim();
    }

    public String apresentarFotoPessoaVisualizar ()
    {
        if (getPessoaVisualizar() == null || getPessoaVisualizar().getFkIdFoto() == null)
        {
            return null;
        }

        String foto = getPessoaVisualizar().getFkIdFoto().getFicheiro();
        return (Constantes.PASTA_FOTO + foto).trim();
    }

    public String getPastaFoto ()
    {
        return Constantes.PASTA_FOTO;
    }

    public int getTipoDocumento ()
    {
        return tipoDocumento;
    }

    public void setTipoDocumento (int tipoDocumento)
    {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento ()
    {
        return numeroDocumento;
    }

    public void setNumeroDocumento (String numeroDocumento)
    {
        this.numeroDocumento = numeroDocumento;
    }

    public void uploadFoto ()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();
            String novoNome = upload.gravar(fotoCarregada, Constantes.DESTINO_FOTO, "FOTO");
            removerFoto(getPessoa().getFkIdFoto(), false);
            getPessoa().getFkIdFoto().setFicheiro(novoNome);
            fotoCarregada = null;
            System.out.println("Foto carregada com sucesso !");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Mensagem.erroMsg(e.getMessage());
        }
    }

    /**
     * Remove uma determinada foto(ficheiro) que foi carregada no servidor
     *
     * @param foto Entidade que contém o nome do ficheiro (foto)
     * @param apresentarMensagem flag booleana que indica se o resultado da
     * operação será apresentado
     */
    public void removerFoto (GrlFicheiroAnexado foto, boolean apresentarMensagem)
    {
        boolean b = false;

        if (!foto.getFicheiro().equals(Constantes.FOTO_DEFAULT))
        {
            b = UploadFicheiro.getInstance().apagarFicheiro(new java.io.File(Constantes.DESTINO_FOTO + foto.getFicheiro()));
        }

        if (apresentarMensagem)
        {
            if (b)
            {
                foto.setFicheiro(Constantes.FOTO_DEFAULT);
                Mensagem.sucessoMsg("Foto removida com sucesso !");
            }
            else
            {
                Mensagem.erroMsg("Não foi possível remover a foto !");
            }
        }
    }

    public String gravarPessoa ()
    {
        if (pessoa == null)
        {
            throw new NullPointerException("Pessoa NULL");
        }
        else if (pessoa.getPkIdPessoa() == null)
        {
            return create();
        }
        else
        {
            return edit();
        }
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();

            ficheiroAnexadoFacade.create(pessoa.getFkIdFoto());
            contactoFacade.create(pessoa.getFkIdContacto());

            if (pessoa.getFkIdEndereco().getFkIdComuna().getPkIdComuna() == null)
            {
                pessoa.getFkIdEndereco().setFkIdComuna(null);
            }
            if (pessoa.getFkIdEndereco().getFkIdDistrito().getPkIdDistrito() == null)
            {
                pessoa.getFkIdEndereco().setFkIdDistrito(null);
            }
            enderecoFacade.create(pessoa.getFkIdEndereco());

            if (pessoa.getFkIdReligiao().getPkIdReligiao() == null)
            {
                pessoa.setFkIdReligiao(null);
            }
            pessoaFacade.create(pessoa);

            userTransaction.commit();

            Mensagem.sucessoMsg("Pessoa gravada com sucesso ! " + pessoa.getNome() + " " + pessoa.getNomeDoMeio() + " " + pessoa.getSobreNome());

//            pessoa = null;
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return "novaPessoaEditarGrl.xhtml";
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            //Se a pessoa não existe
            if (pessoa.getPkIdPessoa() == null)
            {
                throw new NullPointerException("Id pessoa null");
            }

            Integer idNacionalidade = pessoa.getFkIdNacionalidade().getPkIdPais();
            Integer idReligiao = pessoa.getFkIdReligiao().getPkIdReligiao();
            Integer idSexo = pessoa.getFkIdSexo().getPkIdSexo();
            Integer idComuna = pessoa.getFkIdEndereco().getFkIdComuna().getPkIdComuna();
            Integer idDistrito = pessoa.getFkIdEndereco().getFkIdDistrito().getPkIdDistrito();
            Integer idMunicipio = pessoa.getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio();

            pessoa.setFkIdNacionalidade(new GrlPais(idNacionalidade));
            pessoa.setFkIdReligiao(new GrlReligiao(idReligiao));
            pessoa.setFkIdSexo(new GrlSexo(idSexo));
            pessoa.getFkIdEndereco().setFkIdComuna(new GrlComuna(idComuna));
            pessoa.getFkIdEndereco().setFkIdDistrito(new GrlDistrito(idDistrito));
            pessoa.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(idMunicipio));

            if (idComuna == null)
            {
                pessoa.getFkIdEndereco().setFkIdComuna(null);
            }
            if (idDistrito == null)
            {
                pessoa.getFkIdEndereco().setFkIdDistrito(null);
            }

            if (idReligiao == null)
            {
                pessoa.setFkIdReligiao(null);
            }

            ficheiroAnexadoFacade.edit(pessoa.getFkIdFoto());
            contactoFacade.edit(pessoa.getFkIdContacto());
            enderecoFacade.edit(pessoa.getFkIdEndereco());

            //Coloca null todos os campos que podem originar excepções
            pessoa.setRhCandidato(null);
            pessoa.setRhEstagiario(null);
            pessoa.setRhFuncionario(null);
            pessoa.setAdmsPacienteList(null);
            pessoa.setAdmsResponsavelPacienteList(null);
            pessoa.setDiagCandidatoDadorList(null);
            pessoa.setGrlDocumentoIdentificacaoList(null);
            pessoa.setSupiFormadorAuxList(null);

            pessoaFacade.edit(pessoa);

            Mensagem.sucessoMsg("Pessoa editada com sucesso ! " + pessoa.getNome() + " " + pessoa.getNomeDoMeio() + " " + pessoa.getSobreNome());

            userTransaction.commit();

            return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    public void limpar ()
    {
        pessoa = null;
    }

    public void pesquisarPessoasTodoTipo ()
    {
        pessoasPesquisadasList = pessoaFacade.findPessoa(pessoaPesquisa, true, true, true, true, true);
        if (pessoasPesquisadasList == null || pessoasPesquisadasList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + pessoasPesquisadasList.size() + ")");
        }

    }

    public String voltar ()
    {
        limparPesquisa();
        pessoa = getInstancia();
        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    public String limparPesquisa ()
    {
        pessoasPesquisadasList = null;
        pessoaPesquisa = pessoaVisualizar = null;

        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    /**
     * @author Ornela F. Boaventura
     *
     * Criado para atender algumas necessidades de pesquisa de pessoas em
     * recursos humanos
     *
     * @return
     */
    public String limparPesquisaRH ()
    {
        pessoasPesquisadasList = null;
        pessoaPesquisa = pessoaVisualizar = null;

        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    /**
     * @author Ornela F. Boaventura
     * @param pessoa
     * @return
     */
    public boolean isFuncionario (GrlPessoa pessoa)
    {
        return pessoaFacade.isFuncionario(pessoa);
    }

    /**
     * @author Ornela F. Boaventura
     * @param pessoa
     * @return
     */
    public boolean isCandidato (GrlPessoa pessoa)
    {
        return pessoaFacade.isCandidato(pessoa);
    }

    /**
     * @author Ornela F. Boaventura
     * @param pessoa
     * @return
     */
    public boolean isEstagiario (GrlPessoa pessoa)
    {
        return pessoaFacade.isEstagiario(pessoa);
    }

    public String getDocumentoIdentificacao (GrlPessoa pessoa)
    {
        if (pessoa == null || pessoa.getGrlDocumentoIdentificacaoList().isEmpty())
        {
            return "";
        }
        return "" + pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getDescricao() + ": "
               + "" + pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento();
    }

}
