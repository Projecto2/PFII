/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.DiagGrupoSanguineo;
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
import sessao.GrlComunaFacade;
import sessao.GrlContactoFacade;
import sessao.GrlDocumentoIdentificacaoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.GrlMunicipioFacade;
import sessao.GrlPessoaFacade;
import sessao.GrlTipoDocumentoIdentificacaoFacade;
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
public class GrlPessoaBean implements Serializable
{

    @EJB
    private GrlTipoDocumentoIdentificacaoFacade grlTipoDocumentoIdentificacaoFacade;

    FacesContext context = FacesContext.getCurrentInstance();

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private GrlContactoFacade contactoFacade;
    @EJB
    private GrlMunicipioFacade municipioFacade;
    @EJB
    private GrlComunaFacade comunaFacade;
    @EJB
    private GrlEnderecoFacade enderecoFacade;
    @EJB
    private GrlDocumentoIdentificacaoFacade documentoIdentificacaoFacade;
    @EJB
    private GrlPessoaFacade pessoaFacade;

    private boolean pesquisar;

    /**
     * Entidade pessoa
     */
    private GrlPessoa pessoa,

    /**
     * Entidade pessoa
     */
    pessoaPesquisa,

    /**
     * Entidade pessoa
     */
    pessoaVisualizar;
    
    private List<GrlPessoa> pessoas;

    private Part fotoCarregada;

    private String paginaAnterior;

    private int tipoDocumento;

    private String numeroDocumento;

    public GrlPessoaBean ()
    {
    }

    public GrlPessoa getPessoa ()
    {
        if (pessoa == null)
        {
            pessoa = getInstanciaPessoa();
            pessoa.setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());
        }
        return pessoa;
    }

    public void setPessoa (GrlPessoa pessoa)
    {

        if (pessoa != null)
        {
            pessoa.setGrlDocumentoIdentificacaoList(documentoIdentificacaoFacade.pesquisaPorPessoa(pessoa.getPkIdPessoa()));

            if (pessoa.getFkIdFoto() == null)
            {
                pessoa.setFkIdFoto(getInstanciaPessoa().getFkIdFoto());
            }
            if (pessoa.getFkIdReligiao() == null)
            {
                pessoa.setFkIdReligiao(new GrlReligiao());
            }
            if (pessoa.getFkIdEndereco().getFkIdComuna() == null)
            {
                pessoa.getFkIdEndereco().setFkIdComuna(new GrlComuna());
            }
            if (pessoa.getFkIdEndereco().getFkIdDistrito() == null)
            {
                pessoa.getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
            }

            //Fazendo set de algumas propriedades da ItensAjaxBean para aparecerem selecionados nas combobox da dialog editar
            Integer idMunicipio = pessoa.getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio();

            GrlMunicipio mun = municipioFacade.find(idMunicipio);

            ItensAjaxBean itensAjaxBean = obterItensAjaxBean();

            itensAjaxBean.setPais(mun.getFkIdProvincia().getFkIdPais().getPkIdPais());
            itensAjaxBean.setProvincia(mun.getFkIdProvincia().getPkIdProvincia());
            itensAjaxBean.setMunicipio(mun.getPkIdMunicipio());
        }

        this.pessoa = pessoa;
    }

    public GrlPessoa getPessoaPesquisa ()
    {
        if (pessoaPesquisa == null)
        {
            pessoaPesquisa = getInstanciaPessoa();
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

    public String getPaginaAnterior ()
    {
        return paginaAnterior;
    }

    public boolean getPesquisar ()
    {
        return pesquisar;
    }

    public void setPesquisar (boolean pesquisar)
    {
        this.pesquisar = pesquisar;
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

    public String previsualizarFoto ()
    {
        String foto = getPessoa().getFkIdFoto().getFicheiro();
        return (Constantes.PASTA_FOTO + foto + "?faces-redirect=true").trim();
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

    public GrlPessoa getInstanciaPessoa ()
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
        pes.setFkIdGrupoSanguineo(new DiagGrupoSanguineo());
        pes.setFkIdReligiao(new GrlReligiao());
        pes.setFkIdNacionalidade(new GrlPais());
        pes.setFkIdGrupoSanguineo(new DiagGrupoSanguineo());
        pes.setGrlDocumentoIdentificacaoList(new ArrayList<GrlDocumentoIdentificacao>());

        return pes;
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
            adicionarDocumentosIdentificacao();

            userTransaction.commit();

            Mensagem.sucessoMsg("Pessoa gravada com sucesso!");
            Mensagem.sucessoMsg("O seu número é: " + pessoa.getPkIdPessoa());

//            pessoa = null;
            numeroDocumento = "";
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

    public void adicionarDocumentosIdentificacao () throws Exception
    {
        for (GrlDocumentoIdentificacao doc : pessoa.getGrlDocumentoIdentificacaoList())
        {
            doc.setFkIdPessoa(pessoa);
            documentoIdentificacaoFacade.create(doc);
        }
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            //Se a pessoa já existe
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
            pessoaFacade.edit(pessoa);
            editarDocumentosIdentificacao();

            Mensagem.sucessoMsg("Pessoa editada com sucesso!");

            userTransaction.commit();

            return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
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

        return null;
    }

    public void editarDocumentosIdentificacao () throws Exception
    {

        for (GrlDocumentoIdentificacao doc : documentoIdentificacaoFacade.pesquisaPorPessoa(pessoa.getPkIdPessoa()))
        {
            documentoIdentificacaoFacade.remove(doc);
        }

        for (GrlDocumentoIdentificacao doc : pessoa.getGrlDocumentoIdentificacaoList())
        {
            doc.setPkIdDocumento(null);
            doc.setFkIdPessoa(pessoa);
            documentoIdentificacaoFacade.create(doc);
        }
    }

    public void limpar ()
    {
        pessoa = null;
    }

    public List<GrlPessoa> pesquisarPessoas ()
    {
        if (pesquisar)
        {
            List<GrlPessoa> pp = new ArrayList<>();
//            pp = pessoaFacade.findPessoa(pessoaPesquisa, flagPaciente, flagFuncionario,
//                                         flagCandidato, flagEstagiario, flagDador);
            if (pp.isEmpty() || pp == null)
            {
                Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
            }

            return pp;
        }

        return null;
    }

    public List<GrlPessoa> pesquisarPessoasTodoTipo ()
    {
        if (pesquisar)
        {
            List<GrlPessoa> pp;
            pp = pessoaFacade.findPessoa(pessoaPesquisa, true, true, true, true, true);
            if (pp.isEmpty() || pp == null)
            {
                Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
            }

            return pp;
        }

        return null;
    }

//    public List<GrlPessoa> pesquisarPessoasNaoPacientes ()
//    {
//        if (pesquisar)
//        {
//            List<GrlPessoa> pp;
//            pp = pessoaFacade.findPessoa(pessoaPesquisa, false, true, true, true, true);
//            return pp;
//        }
//
//        return null;
//    }
    
    
    public void pesquisarPessoasNaoPacientes()
    {
        pessoas = pessoaFacade.findPessoa(pessoaPesquisa, false, true, true, true, true);
        if(pessoas.isEmpty())
            Mensagem.warnMsg("Nenhuma Pessoa Encontrada");
    }

    public List<GrlPessoa> getPessoas()
    {
        if(pessoas == null)
        {
            pessoas = new ArrayList<>();
        }
        return pessoas;
    }

    public void setPessoas(List<GrlPessoa> pessoas)
    {
        this.pessoas = pessoas;
    }
    

    public String voltar ()
    {
        limparPesquisa();
        pessoa = getInstanciaPessoa();
        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    public String limparPesquisa ()
    {
        pesquisar = false;
        pessoaPesquisa = pessoaVisualizar = null;

        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    /**
     * @author Ornela F. Boaventura

 Criado para atender algumas necessidades de pesquisa de pessoas em
 recursos humanos
     *
     * @return
     */
    public String limparPesquisaRH ()
    {
        pesquisar = false;
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

    public void adicionarDocumentoDeIdentificacao ()
    {
        GrlTipoDocumentoIdentificacao tipoDocumentoObj = grlTipoDocumentoIdentificacaoFacade.find(tipoDocumento);
        if (validarDocumentoIdentificacao(tipoDocumentoObj))
        {
            GrlDocumentoIdentificacao documentoIdentificacao = new GrlDocumentoIdentificacao();
            documentoIdentificacao.setNumeroDocumento(numeroDocumento);
            documentoIdentificacao.setFkTipoDocumentoIdentificacao(tipoDocumentoObj);
            pessoa.getGrlDocumentoIdentificacaoList().add(documentoIdentificacao);
        }
    }

    private boolean validarDocumentoIdentificacao (GrlTipoDocumentoIdentificacao tipoDocumentoObj)
    {
        if (!numeroDocumento.equals(""))
        {
            for (int i = 0; i < pessoa.getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if (pessoa.getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()
                    == tipoDocumentoObj.getPkTipoDocumentoIdentificacao())
                {
                    Mensagem.erroMsg("Tipo de documento ja adicionado, so pode ser adicionado uma vez");
                    return false;
                }
            }
        }
        else
        {
            Mensagem.erroMsg("O numero do documento nao pode ser um texto em branco");
            return false;
        }
        return true;
    }

    public void removerDocumentoIdentificacao (GrlDocumentoIdentificacao documento)
    {
        for (int i = 0; i < pessoa.getGrlDocumentoIdentificacaoList().size(); i++)
        {
            if (pessoa.getGrlDocumentoIdentificacaoList().get(i).getNumeroDocumento().equalsIgnoreCase(documento.getNumeroDocumento())
                && pessoa.getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()
                   == documento.getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao())
            {
                pessoa.getGrlDocumentoIdentificacaoList().remove(i);
                break;
            }
        }
        //pessoa.getGrlDocumentoIdentificacaoList().remove(documento);
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

    private ItensAjaxBean obterItensAjaxBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }

}
