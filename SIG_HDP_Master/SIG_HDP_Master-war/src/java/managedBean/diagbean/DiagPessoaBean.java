/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

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
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.DiagCandidatoDadorFacade;
import sessao.GrlContactoFacade;
import sessao.GrlDocumentoIdentificacaoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlFicheiroAnexadoFacade;
import sessao.GrlPessoaFacade;
import sessao.GrlTipoDocumentoIdentificacaoFacade;
import util.Constantes;
import util.Mensagem;
import util.UploadFicheiro;
import util.diag.Defs;

/**
 *
 * @author Ornela F. Boaventura
 * @author Garcia Paulo
 */
@ManagedBean
@SessionScoped
public class DiagPessoaBean implements Serializable
{

    FacesContext context = FacesContext.getCurrentInstance();

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagCandidatoDadorFacade diagCandidatoDadorFacade;
    @EJB
    private GrlTipoDocumentoIdentificacaoFacade grlTipoDocumentoIdentificacaoFacade;

    @EJB
    private GrlFicheiroAnexadoFacade ficheiroAnexadoFacade;
    @EJB
    private GrlContactoFacade contactoFacade;
    @EJB
    private GrlEnderecoFacade enderecoFacade;
    @EJB
    private GrlDocumentoIdentificacaoFacade documentoIdentificacaoFacade;

    @EJB
    private GrlPessoaFacade pessoaFacade;

    private boolean pesquisar;

    private GrlPessoa grlPessoa, grlPessoaPesquisa, grlPessoaVisualizar;

    private Part fotoCarregada;

    private String paginaAnterior;

    private int tipoDocumento;

    private String numeroDocumento;

    List<GrlPessoa> itens;
    
    private int numeroRegistos = 10;
    
    public static DiagPessoaBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagPessoaBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagPessoaBean");
    }

    public static GrlPessoa getInstancia()
    {
        GrlFicheiroAnexado foto = new GrlFicheiroAnexado(1);
        foto.setFicheiro(Constantes.FOTO_DEFAULT);
        
        GrlPessoa grlPessoa = new GrlPessoa();

        grlPessoa.setFkIdFoto(foto);
        grlPessoa.setFkIdEstadoCivil(new GrlEstadoCivil());
        grlPessoa.setFkIdEndereco(new GrlEndereco());
        grlPessoa.getFkIdEndereco().setFkIdComuna(new GrlComuna());
        grlPessoa.getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
        grlPessoa.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
//          pes.setFkIdDocumentoIdentificacao(new GrlDocumentoIdentificacao());
//          pes.getFkIdDocumentoIdentificacao().setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao());
//      pes.setFkIdConjugePessoa(new GrlConjugePessoa());
        grlPessoa.setFkIdContacto(new GrlContacto());
        grlPessoa.setFkIdSexo(new GrlSexo());
        grlPessoa.getFkIdSexo().setPkIdSexo(Defs.SEXO_DEFAULT_ID);
        grlPessoa.setFkIdReligiao(new GrlReligiao());
        grlPessoa.setFkIdNacionalidade(new GrlPais());
        grlPessoa.getFkIdNacionalidade().setPkIdPais(Defs.NACIONALIDADE_DEFAULT_ID);
        grlPessoa.setFkIdGrupoSanguineo(new DiagGrupoSanguineo());
        
        return grlPessoa;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }

    public List<GrlPessoa> getItens()
    {
        return itens;
    }

    public void setItens(List<GrlPessoa> itens)
    {
        this.itens = itens;
    }
    
    public GrlPessoa getGrlPessoa()
    {
        if (grlPessoa == null)
        {
            grlPessoa = getInstancia();
        }
        return grlPessoa;
    }

    public void setGrlPessoa(GrlPessoa grlPessoa)
    {
        this.grlPessoa = grlPessoa;
    }

    public GrlPessoa getGrlPessoaPesquisa()
    {
        if (grlPessoaPesquisa == null)
        {
            grlPessoaPesquisa = getInstancia();
        }
        return grlPessoaPesquisa;
    }

    public void setGrlPessoaPesquisa(GrlPessoa grlPessoaPesquisa)
    {
        this.grlPessoaPesquisa = grlPessoaPesquisa;
    }

    public GrlPessoa getGrlPessoaVisualizar()
    {
        return grlPessoaVisualizar;
    }

    public void setGrlPessoaVisualizar(GrlPessoa grlPessoaVisualizar)
    {
        this.grlPessoaVisualizar = grlPessoaVisualizar;
    }

    public String getPaginaAnterior()
    {
        return paginaAnterior;
    }

    public boolean getPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public Part getFotoCarregada()
    {
        return fotoCarregada;
    }

    public void setFotoCarregada(Part fotoCarregada)
    {
        this.fotoCarregada = fotoCarregada;
    }

    public String apresentarFoto()
    {
        String foto = getGrlPessoa().getFkIdFoto().getFicheiro();
        return (Constantes.PASTA_FOTO + foto).trim();
    }

    public String previsualizarFoto()
    {
        String foto = getGrlPessoa().getFkIdFoto().getFicheiro();
        return (Constantes.PASTA_FOTO + foto + "?faces-redirect=true").trim();
    }

    public String getPastaFoto()
    {
        return Constantes.PASTA_FOTO;
    }

    public int getTipoDocumento()
    {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento)
    {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento()
    {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento)
    {
        this.numeroDocumento = numeroDocumento;
    }

    public void uploadFoto()
    {
        try
        {
            UploadFicheiro upload = UploadFicheiro.getInstance();
            String novoNome = upload.gravar(fotoCarregada, Constantes.DESTINO_FOTO, "FOTO");
            removerFoto(getGrlPessoa().getFkIdFoto(), false);
            getGrlPessoa().getFkIdFoto().setFicheiro(novoNome);
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
    public void removerFoto(GrlFicheiroAnexado foto, boolean apresentarMensagem)
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

    public String create()
    {
        try
        {
            userTransaction.begin();

            ficheiroAnexadoFacade.create(grlPessoa.getFkIdFoto());
//               documentoIdentificacaoFacade.create(grlPessoa.getFkIdDocumentoIdentificacao());
            contactoFacade.create(grlPessoa.getFkIdContacto());
            enderecoFacade.create(grlPessoa.getFkIdEndereco());
            if (grlPessoa.getFkIdReligiao().getPkIdReligiao() == null)
            {
                grlPessoa.setFkIdReligiao(null);
            }
            pessoaFacade.create(grlPessoa);
            adicionarDocumentosIdentificacao();
            userTransaction.commit();

            Mensagem.sucessoMsg("Pessoa gravada com sucesso!");
            Mensagem.sucessoMsg("O seu número é: " + grlPessoa.getPkIdPessoa());

            grlPessoa = null;
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

        //return "novaPessoaEditarGrl.xhtml?faces-redirect=true";
        return "novaPessoaEditarGrl.xhtml?faces-redirect=true";
    }

    public void adicionarDocumentosIdentificacao() throws Exception
    {
        for (GrlDocumentoIdentificacao doc : grlPessoa.getGrlDocumentoIdentificacaoList())
        {
            doc.setFkIdPessoa(grlPessoa);
            documentoIdentificacaoFacade.create(doc);
        }
    }

    public String edit()
    {
        try
        {
            userTransaction.begin();
            //Se a grlPessoa já existe
            if (grlPessoa.getPkIdPessoa() == null)
            {
                throw new NullPointerException("Id pessoa null");
            }

            int idNacionalidade = grlPessoa.getFkIdNacionalidade().getPkIdPais();
            int idSexo = grlPessoa.getFkIdSexo().getPkIdSexo();
            int idComuna = grlPessoa.getFkIdEndereco().getFkIdComuna().getPkIdComuna();
//               int idTipoDocIdent = grlPessoa.getFkIdDocumentoIdentificacao().getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao();

            ficheiroAnexadoFacade.edit(grlPessoa.getFkIdFoto());
            contactoFacade.edit(grlPessoa.getFkIdContacto());
            grlPessoa.setFkIdNacionalidade(new GrlPais(idNacionalidade));
            grlPessoa.setFkIdSexo(new GrlSexo(idSexo));
//               grlPessoa.getFkIdDocumentoIdentificacao().setFkTipoDocumentoIdentificacao(new GrlTipoDocumentoIdentificacao(idTipoDocIdent));
//               documentoIdentificacaoFacade.edit(grlPessoa.getFkIdDocumentoIdentificacao());
            grlPessoa.getFkIdEndereco().setFkIdComuna(new GrlComuna(idComuna));
            enderecoFacade.edit(grlPessoa.getFkIdEndereco());
            pessoaFacade.edit(grlPessoa);

            Mensagem.sucessoMsg("Pessoa editada com sucesso!");

            userTransaction.commit();
            limparPesquisa();

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

    public void limpar()
    {
        grlPessoa = null;
    }

    public String novaPessoa()
    {
        return "pessoaNovaEditarGrl.xhtml?faces-redirect=true";
    }

    public void pesquisarPessoas()
    {
        itens = diagCandidatoDadorFacade.findPessoaNaoCandidatos(grlPessoaPesquisa, numeroRegistos);
        
        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }           
    }

    public String voltar()
    {
        limparPesquisa();
        grlPessoa = getInstancia();
        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        grlPessoaPesquisa = grlPessoaVisualizar = null;

        return "pessoaPesquisarGrl.xhtml?faces-redirect=true";
    }

    /**
     * Este método retorna o texto do botão de cadastro da página de pesquisa de
 grlPessoas
     *
     * @return
     */
    public String textoBotaoCadastro()
    {
        if (this.paginaAnterior != null)
        {
            switch (this.paginaAnterior)
            {
                case "PACIENTE":
                    return "Cadastrar Como Paciente";
                case "FUNCIONARIO":
                    return "Cadastrar Como Funcionário";
                case "CANDIDATO":
                    return "Cadastrar Como Candidato";
                case "ESTAGIARIO":
                    return "Cadastrar Como Estagiário";
                case "CANDIDATO_DADOR":
                    return "Cadastrar Como Candidato Dador";
            }
        }

        return "<----->";
    }

    /**
     * Este método retorna a URL de qualquer página que tenha relação com a
     * página anterior a de pesquisa de pessoas mas já carrega os dados da
     * pessoa passada como paramêtro
     *
     * @param pes
     * @return
     */
//     public String selecionarPessoa (GrlPessoa pes)
//     {
//          limparPesquisa();
//
//          if ("FUNCIONARIO".equals(this.paginaAnterior))
//          {
//               RhFuncionarioBean funcionarioBean = getFuncionarioBean();
//               funcionarioBean.getFuncionario().setFkIdPessoa(pes);
//               funcionarioBean.setFuncionario(funcionarioBean.getFuncionario());
//
//               return "funcionarioNovoRh.xhtml?faces-redirect=true";
//          }
//          else if ("CANDIDATO".equals(this.paginaAnterior))
//          {
//               RhCandidatoBean candidatoBean = getCandidatoBean();
//               candidatoBean.getCandidato().setFkIdPessoa(pes);
//               candidatoBean.setCandidato(candidatoBean.getCandidato());
//
//               return "candidatoNovoRh.xhtml?faces-redirect=true";
//          }
//          else if ("ESTAGIARIO".equals(this.paginaAnterior))
//          {
//               RhEstagiarioBean estagiarioBean = getEstagiarioBean();
//               estagiarioBean.getEstagiario().setFkIdPessoa(pes);
//               estagiarioBean.setEstagiario(estagiarioBean.getEstagiario());
//
//               return "estagiarioNovoRh.xhtml?faces-redirect=true";
//          }
//
//          return limparPesquisa();
//     }
    /**
     * Como a página de pesquisa de grlPessoas é usada antes do cadastro de
 qualquer entidade que seja grlPessoa(Ex: Funcionário, Paciente, Etc), então
 antes desta ser apresentada ela é preparada de acordo a página anterior
 que a chamou. Por exemplo, se a página anterior tiver sido a de pesquisa
 de funcionários, então qualquer grlPessoa que for selecionada será
 cadastrada como funcionário
     *
     * @param paginaAnterior
     * @return
     */
    public String pepararPesquisaPessoa(String paginaAnterior)
    {
        this.paginaAnterior = paginaAnterior;

        return limparPesquisa();
    }

    public List<GrlPessoa> pesquisarPessoasNaoPacientes()
    {
        if (pesquisar)
        {
            List<GrlPessoa> pp;
            pp = pessoaFacade.findPessoa(grlPessoaPesquisa, false, true, true, true, true);
            return pp;
        }

        return null;
    }

//     private RhFuncionarioBean getFuncionarioBean ()
//     {
//          return (RhFuncionarioBean) FacesContext.getCurrentInstance()
//                  .getELContext().getELResolver()
//                  .getValue(FacesContext.getCurrentInstance().getELContext(), null, "rhFuncionarioBean");
//     }
//
//     private RhCandidatoBean getCandidatoBean ()
//     {
//          return (RhCandidatoBean) FacesContext.getCurrentInstance()
//                  .getELContext().getELResolver()
//                  .getValue(FacesContext.getCurrentInstance().getELContext(), null, "rhCandidatoBean");
//     }
//
//     private RhEstagiarioBean getEstagiarioBean ()
//     {
//          return (RhEstagiarioBean) FacesContext.getCurrentInstance()
//                  .getELContext().getELResolver()
//                  .getValue(FacesContext.getCurrentInstance().getELContext(), null, "rhEstagiarioBean");
//     }
    public void adicionarDocumentoDeIdentificacao()
    {
        GrlTipoDocumentoIdentificacao tipoDocumentoObj = grlTipoDocumentoIdentificacaoFacade.find(tipoDocumento);
        if (validarDocumentoIdentificacao(tipoDocumentoObj))
        {
            GrlDocumentoIdentificacao documentoIdentificacao = new GrlDocumentoIdentificacao();
            documentoIdentificacao.setNumeroDocumento(numeroDocumento);
            documentoIdentificacao.setFkTipoDocumentoIdentificacao(tipoDocumentoObj);
            grlPessoa.getGrlDocumentoIdentificacaoList().add(documentoIdentificacao);
        }
    }

    private boolean validarDocumentoIdentificacao(GrlTipoDocumentoIdentificacao tipoDocumentoObj)
    {
        if (!numeroDocumento.equals(""))
        {
            for (int i = 0; i < grlPessoa.getGrlDocumentoIdentificacaoList().size(); i++)
            {
                if (grlPessoa.getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()
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

    public void removerDocumentoIdentificacao(GrlDocumentoIdentificacao documento)
    {
        for (int i = 0; i < grlPessoa.getGrlDocumentoIdentificacaoList().size(); i++)
        {
            if (grlPessoa.getGrlDocumentoIdentificacaoList().get(i).getNumeroDocumento().equalsIgnoreCase(documento.getNumeroDocumento())
                    && grlPessoa.getGrlDocumentoIdentificacaoList().get(i).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()
                    == documento.getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao())
            {
                grlPessoa.getGrlDocumentoIdentificacaoList().remove(i);
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
    
}
