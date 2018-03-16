/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.GrlDocumentoIdentificacaoFacade;
import sessao.GrlMunicipioFacade;
import sessao.GrlPessoaFacade;
import sessao.GrlTipoDocumentoIdentificacaoFacade;
import util.Constantes;
import util.ItensAjaxBean;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsPessoaBean
{
    @EJB
    private GrlTipoDocumentoIdentificacaoFacade grlTipoDocumentoIdentificacaoFacade;

    FacesContext context = FacesContext.getCurrentInstance();
    @EJB
    private GrlMunicipioFacade municipioFacade;
    @EJB
    private GrlDocumentoIdentificacaoFacade documentoIdentificacaoFacade;
    @EJB
    private GrlPessoaFacade pessoaFacade;

    private boolean pesquisar;

    /**
     * Entidade pessoa
     */
    private GrlPessoa pessoa, pessoaPesquisa, pessoaVisualizar;
    
    private List<GrlPessoa> pessoas;

//    private Part fotoCarregada;

    private String paginaAnterior;

    private int tipoDocumento;

    private String numeroDocumento;
    
    private Date dataNascimentoInicial, dataNascimentoFinal;

    public AdmsPessoaBean ()
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

    public Date getDataNascimentoInicial()
    {
        return dataNascimentoInicial;
    }

    public void setDataNascimentoInicial(Date dataNascimentoInicial)
    {
        this.dataNascimentoInicial = dataNascimentoInicial;
    }

    public Date getDataNascimentoFinal()
    {
        return dataNascimentoFinal;
    }

    public void setDataNascimentoFinal(Date dataNascimentoFinal)
    {
        System.out.println("data "+dataNascimentoFinal);
//        if(dataNascimentoFinal != null)
//        {
//            System.out.println("entrou");
//            dataNascimentoFinal = AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(dataNascimentoFinal);
//            this.dataNascimentoFinal = dataNascimentoFinal;
//        }
        this.dataNascimentoFinal = dataNascimentoFinal;
    }
    
    

    public void adicionarDocumentosIdentificacao () throws Exception
    {
        for (GrlDocumentoIdentificacao doc : pessoa.getGrlDocumentoIdentificacaoList())
        {
            doc.setFkIdPessoa(pessoa);
            documentoIdentificacaoFacade.create(doc);
        }
    }



    public void limpar ()
    {
        pessoa = null;
    }

    
    
    public void pesquisarPessoasNaoPacientes()
    {
        pessoas = pessoaFacade.findPessoaAdmissoes(pessoaPesquisa, 100, dataNascimentoInicial, dataNascimentoFinal, false, true, true, true, true);
        if(pessoas.isEmpty())
            Mensagem.warnMsg("Nenhuma Pessoa Encontrada");
        else Mensagem.sucessoMsg("Tabela Atualizada. "+pessoas.size()+" registos!");
    }

    public List<GrlPessoa> getPessoass()
    {
        if(pessoas == null)
        {
            pessoas = new ArrayList<>();
        }
        return pessoas;
    }

    public void setPessoass(List<GrlPessoa> pessoas)
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
     *
     * Criado para atender algumas necessidades de pesquisa de pessoas em
     * recursos humanos
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


    private ItensAjaxBean obterItensAjaxBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }
    
//    public void adicionarDocumentoDeIdentificacao ()
//    {
//        GrlTipoDocumentoIdentificacao tipoDocumentoObj = grlTipoDocumentoIdentificacaoFacade.find(tipoDocumento);
//        if (validarDocumentoIdentificacao(tipoDocumentoObj))
//        {
//            GrlDocumentoIdentificacao documentoIdentificacao = new GrlDocumentoIdentificacao();
//            documentoIdentificacao.setNumeroDocumento(numeroDocumento);
//            documentoIdentificacao.setFkTipoDocumentoIdentificacao(tipoDocumentoObj);
//            pessoa.getGrlDocumentoIdentificacaoList().add(documentoIdentificacao);
//        }
//    }
//
//    private boolean validarDocumentoIdentificacao (GrlTipoDocumentoIdentificacao tipoDocumentoObj)
//    {
//        if (!numeroDocumento.equals(""))
//        {
//            for (int i = 0; i < pessoa.getGrlDocumentoIdentificacaoList().size(); i++)
//            {
//                if (pessoa.getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()
//                    == tipoDocumentoObj.getPkTipoDocumentoIdentificacao())
//                {
//                    Mensagem.erroMsg("Tipo de documento ja adicionado, so pode ser adicionado uma vez");
//                    return false;
//                }
//            }
//        }
//        else
//        {
//            Mensagem.erroMsg("O numero do documento nao pode ser um texto em branco");
//            return false;
//        }
//        return true;
//    }

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
    
    
    public Date getMomentoActual()
    {
        return new Date();
    }
    
}
