/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.consultas;

import entidade.AdmsAgendamento;
import entidade.AmbAderencia;
import entidade.AmbCidSubcategorias;
import entidade.AmbClassificacaoDor;
import entidade.AmbColoracao;
import entidade.AmbConfirmacao;
import entidade.AmbConsulta;
import entidade.AmbConsultaHasColoracao;
import entidade.AmbConsultaHasCor;
import entidade.AmbConsultaHasEspessura;
import entidade.AmbConsultaHasImpressoesGerais;
import entidade.AmbConsultaHasTextura;
import entidade.AmbConsultaHasTurgorPele;
import entidade.AmbConsultaHasTurgorTecido;
import entidade.AmbConsultorioAtendimento;
import entidade.AmbCor;
import entidade.AmbDiagnosticoHipotese;
import entidade.AmbDiagnosticoHipoteseHasDoenca;
import entidade.AmbEspessura;
import entidade.AmbEstadoHidratacao;
import entidade.AmbImpressoesGerais;
import entidade.AmbObservacoesMedicas;
import entidade.AmbSinal;
import entidade.AmbTextura;
import entidade.AmbTriagem;
import entidade.AmbTriagemHasSinal;
import entidade.AmbTurgor;
import entidade.GrlEspecialidade;
import entidade.GrlTamanho;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.ambbean.ce.configuracoes.AmbCeConfiguracoesBean;
import managedBean.ambbean.ce.diagnosticos.AmbHipoteseDiagnosticoBean;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsulta;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultaHasColoracao;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultaHasCor;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultaHasEspessura;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultaHasImpressoesGerais;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultaHasTextura;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultaHasTurgorPele;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultaHasTurgorTecido;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbDiagnosticoHipotese;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbDiagnosticoHipoteseHasDoenca;
import static managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoCriarBean.getInstanciaAmbConsultorioAtendimento;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import sessao.AdmsAgendamentoFacade;
import sessao.AmbAderenciaFacade;
import sessao.AmbCeConfiguracoesFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.AmbClassificacaoDorFacade;
import sessao.AmbColoracaoFacade;
import sessao.AmbConfirmacaoFacade;
import sessao.AmbConsultaFacade;
import sessao.AmbConsultaHasColoracaoFacade;
import sessao.AmbConsultaHasCorFacade;
import sessao.AmbConsultaHasEspessuraFacade;
import sessao.AmbConsultaHasImpressoesGeraisFacade;
import sessao.AmbConsultaHasTexturaFacade;
import sessao.AmbConsultaHasTurgorPeleFacade;
import sessao.AmbConsultaHasTurgorTecidoFacade;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbCorFacade;
import sessao.AmbDiagnosticoHipoteseHasDoencaFacade;
import sessao.AmbEspessuraFacade;
import sessao.AmbEstadoHidratacaoFacade;
import sessao.AmbImpressoesGeraisFacade;
import sessao.AmbObservacoesMedicasFacade;
import sessao.AmbSinalFacade;
import sessao.AmbTexturaFacade;
import sessao.AmbTriagemFacade;
import sessao.AmbTriagemHasSinalFacade;
import sessao.AmbTurgorFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.GrlTamanhoFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultaListarBean implements Serializable
{
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AmbAderenciaFacade ambAderenciaFacade; 
    @EJB
    private AmbCeConfiguracoesFacade ambCeConfiguracoesFacade;    
    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;
    @EJB
    private AmbClassificacaoDorFacade ambClassificacaoDorFacade;
    @EJB
    private AmbColoracaoFacade ambColoracaoFacade;
    @EJB
    private AmbCorFacade ambCorFacade;
    @EJB
    private AmbConfirmacaoFacade ambConfirmacaoFacade;    
    @EJB
    private AmbConsultaFacade ambConsultaFacade;
    @EJB
    private AmbConsultaHasColoracaoFacade ambConsultaHasColoracaoFacade;
    @EJB
    private AmbConsultaHasCorFacade ambConsultaHasCorFacade;
    @EJB
    private AmbConsultaHasEspessuraFacade ambConsultaHasEspessuraFacade;    
    @EJB
    private AmbConsultaHasImpressoesGeraisFacade ambConsultaHasImpressoesGeraisFacade;    
    @EJB
    private AmbConsultaHasTexturaFacade ambConsultaHasTexturaFacade;
    @EJB
    private AmbConsultaHasTurgorPeleFacade ambConsultaHasTurgorPeleFacade; 
    @EJB
    private AmbConsultaHasTurgorTecidoFacade ambConsultaHasTurgorTecidoFacade;    
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade; 
    @EJB
    private AmbDiagnosticoHipoteseHasDoencaFacade ambDiagnosticoHipoteseHasDoencaFacade;    
    @EJB
    private AmbEspessuraFacade ambEspessuraFacade;
    @EJB
    private AmbEstadoHidratacaoFacade ambEstadoHidratacaoFacade;    
    @EJB
    private AmbImpressoesGeraisFacade ambImpressoesGeraisFacade;
    @EJB
    private AmbObservacoesMedicasFacade ambObservacoesMedicasFacade;    
    @EJB
    private AmbSinalFacade ambSinalFacade;
    @EJB
    private AmbTexturaFacade ambTexturaFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private AmbTriagemHasSinalFacade ambTriagemHasSinalFacade;
    @EJB
    private AmbTurgorFacade ambTurgorFacade;
    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;
    @EJB
    private GrlTamanhoFacade grlTamanhoFacade;    

    private AmbCidSubcategorias ambCidSubcategorias;
    private AmbConsulta ambConsulta
                      , acAux;
    private AmbConsultaHasColoracao ambConsultaHasColoracao;
    private AmbConsultaHasCor ambConsultaHasCor;
    private AmbConsultaHasEspessura ambConsultaHasEspessura;
    private AmbConsultaHasImpressoesGerais ambConsultaHasImpressoesGerais;
    private AmbConsultaHasTextura ambConsultaHasTextura;
    private AmbConsultaHasTurgorPele ambConsultaHasTurgorPele;
    private AmbConsultaHasTurgorTecido ambConsultaHasTurgorTecido;
    private AmbConsultorioAtendimento ambConsultorioAtendimento;
    private AmbDiagnosticoHipotese adhAux;
    private AmbDiagnosticoHipoteseHasDoenca ambDiagnosticoHipoteseHasDoenca;
    private AmbTriagem ambTriagem;
    private Date dataInicio
               , dataFinal;

    private int codigoCentroHospitalar 
              , codigoCentroHospitalarAux
              , codigoConfirmacao
              , codigoEspecialidade;
    
    private int[] listaCorPele
                , listaEspessuraTecido
                , listaImpressoes
                , listaMucosasColoracao
                , listaTexturaPele
                , listaTurgorPele
                , listaTurgorTecido;

    private long codigoEncaminhamento
               , codigoTriagem;

    /**
     * Creates a new instance of AmbConsultaListarBean
     */
    public AmbConsultaListarBean()
    {
    }
    
    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbConsultaListarBean getInstanciaBean()
    {
        return (AmbConsultaListarBean) GeradorCodigo.getInstanciaBean("ambConsultaListarBean");
    }
    
    public static AmbCeConfiguracoesBean getInstanciaAmbCeConfiguracoesBean()
    {
        return (AmbCeConfiguracoesBean) GeradorCodigo.getInstanciaBean("ambCeConfiguracoesBean");
    }    
    
    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/

    /******************Início dos métodos setters e getters********************/
    
    public AmbConsulta getACAux()
    {
        if (acAux == null)
        {
            acAux = getInstanciaAmbConsulta();
        }
        return acAux;
    }

    public void setACAux(AmbConsulta ambConsulta)
    {
        this.acAux = ambConsulta;
    }  
    
    public AmbConsulta getAmbConsulta()
    {
        if (ambConsulta == null)
        {
            ambConsulta = getInstanciaAmbConsulta();
        }
        return ambConsulta;
    }

    public void setAmbConsulta(AmbConsulta ambConsulta)
    {
        this.ambConsulta = ambConsulta;
    }     
    
    public AmbConsultaHasColoracao getAmbConsultaHasColoracao()
    {
        if (ambConsultaHasColoracao == null)
        {
            ambConsultaHasColoracao = getInstanciaAmbConsultaHasColoracao();
        }
        return ambConsultaHasColoracao;
    }

    public void setAmbConsultaHasColoracao(AmbConsultaHasColoracao ambConsultaHasColoracao)
    {
        this.ambConsultaHasColoracao = ambConsultaHasColoracao;
    }

    public AmbConsultaHasCor getAmbConsultaHasCor()
    {
        if (ambConsultaHasCor == null)
        {
            ambConsultaHasCor = getInstanciaAmbConsultaHasCor();
        }
        return ambConsultaHasCor;
    }

    public void setAmbConsultaHasCor(AmbConsultaHasCor ambConsultaHasCor)
    {
        this.ambConsultaHasCor = ambConsultaHasCor;
    }

    public AmbDiagnosticoHipotese getAdhAux()
    {
        if (adhAux == null)
        {
            adhAux = getInstanciaAmbDiagnosticoHipotese();
        }
        return adhAux;
    }

    public void setAdhAux(AmbDiagnosticoHipotese adhAux)
    {
        this.adhAux = adhAux;
    }

    public AmbDiagnosticoHipoteseHasDoenca getAmbDiagnosticoHipoteseHasDoenca()
    {
        if (ambDiagnosticoHipoteseHasDoenca == null)
        {
            ambDiagnosticoHipoteseHasDoenca = getInstanciaAmbDiagnosticoHipoteseHasDoenca();
        }
        return ambDiagnosticoHipoteseHasDoenca;
    }

    public void setAmbDiagnosticoHipoteseHasDoenca(AmbDiagnosticoHipoteseHasDoenca ambDiagnosticoHipoteseHasDoenca)
    {
        this.ambDiagnosticoHipoteseHasDoenca = ambDiagnosticoHipoteseHasDoenca;
    }   
    
    public AmbConsultaHasEspessura getAmbConsultaHasEspessura()
    {
        if (ambConsultaHasEspessura == null)
        {
            ambConsultaHasEspessura = getInstanciaAmbConsultaHasEspessura();
        }
        return ambConsultaHasEspessura;
    }

    public void setAmbConsultaHasEspessura(AmbConsultaHasEspessura ambConsultaHasEspessura)
    {
        this.ambConsultaHasEspessura = ambConsultaHasEspessura;
    }

    public AmbConsultaHasImpressoesGerais getAmbConsultaHasImpressoesGerais()
    {
        if (ambConsultaHasImpressoesGerais == null)
        {
            ambConsultaHasImpressoesGerais = getInstanciaAmbConsultaHasImpressoesGerais();
        }
        return ambConsultaHasImpressoesGerais;
    }

    public void setAmbConsultaHasImpressoesGerais(AmbConsultaHasImpressoesGerais ambConsultaHasImpressoesGerais)
    {
        this.ambConsultaHasImpressoesGerais = ambConsultaHasImpressoesGerais;
    }

    public AmbConsultaHasTextura getAmbConsultaHasTextura()
    {
        if (ambConsultaHasTextura == null)
        {
            ambConsultaHasTextura = getInstanciaAmbConsultaHasTextura();
        }
        return ambConsultaHasTextura;
    }

    public void setAmbConsultaHasTextura(AmbConsultaHasTextura ambConsultaHasTextura)
    {
        this.ambConsultaHasTextura = ambConsultaHasTextura;
    }

    public AmbConsultaHasTurgorPele getAmbConsultaHasTurgorPele()
    {
        if (ambConsultaHasTurgorPele == null)
        {
            ambConsultaHasTurgorPele = getInstanciaAmbConsultaHasTurgorPele();
        }
        return ambConsultaHasTurgorPele;
    }

    public void setAmbConsultaHasTurgorPele(AmbConsultaHasTurgorPele ambConsultaHasTurgorPele)
    {
        this.ambConsultaHasTurgorPele = ambConsultaHasTurgorPele;
    }

    public AmbConsultaHasTurgorTecido getAmbConsultaHasTurgorTecido()
    {
        if (ambConsultaHasTurgorTecido == null)
        {
            ambConsultaHasTurgorTecido = getInstanciaAmbConsultaHasTurgorTecido();
        }
        return ambConsultaHasTurgorTecido;
    }

    public void setAmbConsultaHasTurgorTecido(AmbConsultaHasTurgorTecido ambConsultaHasTurgorTecido)
    {
        this.ambConsultaHasTurgorTecido = ambConsultaHasTurgorTecido;
    }

    public AmbConsultorioAtendimento getAmbConsultorioAtendimento()
    {
        if (this.ambConsultorioAtendimento == null)
        {
            this.ambConsultorioAtendimento = getInstanciaAmbConsultorioAtendimento();
        }
        return ambConsultorioAtendimento;
    }

    public void setAmbConsultorioAtendimento(AmbConsultorioAtendimento ambConsultorioAtendimento)
    {
        this.ambConsultorioAtendimento = ambConsultorioAtendimento;
    }    
    
    public AmbTriagem getAmbTriagem()
    {
        if (ambTriagem == null)
        {
            ambTriagem = getInstanciaAmbTriagem();
        }
        return ambTriagem;
    }

    public void setAmbTriagem(AmbTriagem ambTriagem)
    {
        this.ambTriagem = ambTriagem;
    }

    public Date getDataInicio()
    {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal()
    {
        if (dataFinal == null)
        {
            dataFinal = new Date();
        }
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal)
    {
        this.dataFinal = dataFinal;
    }    

    public int getCodigoCentroHospitalar()
    {
//        if (codigoCentroHospitalar < 0 || codigoCentroHospitalar > 5)
//        {
//        codigoCentroHospitalar
//            codigoCentroHospitalarAux = getInstanciaAmbCeConfiguracoesBean().findAllByCentroContaRendered(/*codigoCentroHospitalar, */getInstanciaAmbCeConfiguracoesBean().getSegConta().getPkIdConta()).getPkIdCentro();
//            codigoCentroHospitalar    = ; 
//        }
        return codigoCentroHospitalar;
    }

    public void setCodigoCentroHospitalar(int codigoCentroHospitalar)
    {
        this.codigoCentroHospitalar = codigoCentroHospitalar;
    }

    public int getCodigoCentroHospitalarAux()
    {
//        codigoCentroHospitalarAux = getInstanciaAmbCeConfiguracoesBean().findAllByCentroContaRendered(/*codigoCentroHospitalar, */getInstanciaAmbCeConfiguracoesBean().getSegConta().getPkIdConta()).getPkIdCentro();
        return codigoCentroHospitalarAux;
    }

    public void setCodigoCentroHospitalarAux(int codigoCentroHospitalarAux)
    {
        this.codigoCentroHospitalarAux = codigoCentroHospitalarAux;
    }
    
    public int getCodigoConfirmacao()
    {
        return codigoConfirmacao;
    }

    public void setCodigoConfirmacao(int codigoConfirmacao)
    {
        this.codigoConfirmacao = codigoConfirmacao;
    }    
    
    public int getCodigoEspecialidade()
    {
        return codigoEspecialidade;
    }

    public void setCodigoEspecialidade(int codigoEspecialidade)
    {
        this.codigoEspecialidade = codigoEspecialidade;
    } 
    
    public int[] getListaCorPele()
    {
        return listaCorPele;
    }

    public void setListaCorPele(int[] listaCorPele)
    {
        this.listaCorPele = listaCorPele;
    }

    public int[] getListaEspessuraTecido()
    {
        return listaEspessuraTecido;
    }

    public void setListaEspessuraTecido(int[] listaEspessuraTecido)
    {
        this.listaEspessuraTecido = listaEspessuraTecido;
    }

    public int[] getListaImpressoes()
    {
        return listaImpressoes;
    }

    public void setListaImpressoes(int[] listaImpressoes)
    {
        this.listaImpressoes = listaImpressoes;
    }

    public int[] getListaMucosasColoracao()
    {
        return listaMucosasColoracao;
    }

    public void setListaMucosasColoracao(int[] listaMucosasColoracao)
    {
        this.listaMucosasColoracao = listaMucosasColoracao;
    }

    public int[] getListaTexturaPele()
    {
        return listaTexturaPele;
    }

    public void setListaTexturaPele(int[] listaTexturaPele)
    {
        this.listaTexturaPele = listaTexturaPele;
    }

    public int[] getListaTurgorPele()
    {
        return listaTurgorPele;
    }

    public void setListaTurgorPele(int[] listaTurgorPele)
    {
        this.listaTurgorPele = listaTurgorPele;
    }  

    public int[] getListaTurgorTecido()
    {
        return listaTurgorTecido;
    }

    public void setListaTurgorTecido(int[] listaTurgorTecido)
    {
        this.listaTurgorTecido = listaTurgorTecido;
    }    
    
    public AmbEspessuraFacade getAmbEspessuraFacade()
    {
        return ambEspessuraFacade;
    }

    public void setAmbEspessuraFacade(AmbEspessuraFacade ambEspessuraFacade)
    {
        this.ambEspessuraFacade = ambEspessuraFacade;
    }

    public long getCodigoEncaminhamento()
    {
        return codigoEncaminhamento;
    }

    public void setCodigoEncaminhamento(long codigoEncaminhamento)
    {
        this.codigoEncaminhamento = codigoEncaminhamento;
    }
    
    public long getCodigoTriagem()
    {
        return codigoTriagem;
    }

    public void setCodigoTriagem(long codigoTriagem)
    {
        this.codigoTriagem = codigoTriagem;
    }

    /*********************Fim dos métodos setters e getters********************/
    
    /*********************Início dos métodos para pesquisas********************/

    public List<AmbConfirmacao> listarConfirmacoes()
    {
        return ambConfirmacaoFacade.findAll();
    }    
    
    public List<GrlEspecialidade> listarEspecialidades()
    {
        return grlEspecialidadeFacade.findAll();
    }

    public AmbConsultorioAtendimento retornaEncaminhamentos()
    {
        for (AmbConsultorioAtendimento aca : ambConsultorioAtendimentoFacade.findAll())
        {
            for (AmbTriagem at : ambTriagemFacade.findAll())
            {
                if (aca.getFkIdTriagem().getPkIdTriagem().equals(at.getPkIdTriagem()))
                {
                    if (aca.getPkIdConsultorioAtendimento() == codigoEncaminhamento)
                    {
                        ambConsultorioAtendimento = aca;
                        ambConsultorioAtendimento.setPkIdConsultorioAtendimento(codigoEncaminhamento);
                    }
                }
            }
        }
        return ambConsultorioAtendimento;
    }
    
    public AmbTriagem retornaAmbTriagem()
    {
        for (AmbTriagem at : ambTriagemFacade.findAll())
        {
            for (AdmsAgendamento aa : admsAgendamentoFacade.findAll())
            {
                if (aa.getPkIdAgendamento() == at.getFkIdAgendamento().getPkIdAgendamento())
                {
                    if (at.getPkIdTriagem() == codigoTriagem)
                    {
                        ambTriagem = at;
                        ambTriagem.setPkIdTriagem(codigoTriagem);
                    }
                }
            }
        }
        return ambTriagem;
    }

    public List<AmbConsultaHasColoracao> retornaColoracao(int coloracao)
    {
        ambConsultaHasColoracao = getInstanciaAmbConsultaHasColoracao();

        List<AmbConsultaHasColoracao> resultado = new ArrayList();

        for (AmbColoracao ambColoracao : ambColoracaoFacade.findAll())
        {
            if (ambColoracao.getPkIdColoracao() == coloracao)
            {
                ambConsultaHasColoracao.setFkIdColoracao(ambColoracao);
                resultado.add(ambConsultaHasColoracao);
            }
        }
        return resultado;
    }

    public List<AmbConsultaHasColoracao> devolverColoracao()
    {
        List<AmbConsultaHasColoracao> resultadoColoracao = new ArrayList();
        int c = 0;

        if (listaMucosasColoracao != null)
        {
            for (int i = 0; i < listaMucosasColoracao.length; i++)
            {
                for (AmbConsultaHasColoracao acc : retornaColoracao(listaMucosasColoracao[i]))
                {
                    if (acc.getFkIdColoracao().getPkIdColoracao() == c)
                    {
                        ambConsultaHasColoracao = acc;
                    }
                    resultadoColoracao.add(acc);
                }

                if (i < listaMucosasColoracao.length - 1)
                {
                    resultadoColoracao.get(i).getFkIdColoracao().getDescricao();
                }
            }
        }
        return resultadoColoracao;
    }

    public List<AmbConsultaHasCor> retornaCor(int cor)
    {
        ambConsultaHasCor = getInstanciaAmbConsultaHasCor();

        List<AmbConsultaHasCor> resultado = new ArrayList();

        for (AmbCor ambCor : ambCorFacade.findAll())
        {
            if (ambCor.getPkIdCor() == cor)
            {
                ambConsultaHasCor.setFkIdCor(ambCor);
                resultado.add(ambConsultaHasCor);
            }
        }
        return resultado;
    }

    public List<AmbConsultaHasCor> devolverCor()
    {
        List<AmbConsultaHasCor> resultadoCor = new ArrayList();
        int c = 0;

        if (listaCorPele != null)
        {
            for (int i = 0; i < listaCorPele.length; i++)
            {
                for (AmbConsultaHasCor ac : retornaCor(listaCorPele[i]))
                {
                    if (ac.getFkIdCor().getPkIdCor() == c)
                    {
                        ambConsultaHasCor = ac;
                    }
                    resultadoCor.add(ac);
                }

                if (i < listaCorPele.length - 1)
                {
                    resultadoCor.get(i).getFkIdCor().getDescricao();
                }
            }
        }
        return resultadoCor;
    }

    public List<AmbDiagnosticoHipoteseHasDoenca> retornaDoencas(String doenca)
    {
        ambDiagnosticoHipoteseHasDoenca = getInstanciaAmbDiagnosticoHipoteseHasDoenca();

        List<AmbDiagnosticoHipoteseHasDoenca> resultado = new ArrayList();

        if (!ambCidSubcategoriasFacade.findAll().isEmpty())
        {
            for (AmbCidSubcategorias acs : ambCidSubcategoriasFacade.findAll())
            {
                if (acs.getPkIdSubcategorias().equals(doenca))
                {
                    ambDiagnosticoHipoteseHasDoenca.setFkIdSubcategorias(acs);
                    resultado.add(ambDiagnosticoHipoteseHasDoenca);
                }
            }
        }
        return resultado;
    }

    public List<AmbDiagnosticoHipoteseHasDoenca> devolverDoencas()
    {
        List<AmbDiagnosticoHipoteseHasDoenca> resultado = new ArrayList();

        String cd = null;

        List<String> listaPkIdSubcategoriasDasDoencasSeleccionadas = AmbHipoteseDiagnosticoBean.getInstanciaBean().getListaPkIdSubcategoriasDasDoencasSeleccionadas();

        if (listaPkIdSubcategoriasDasDoencasSeleccionadas != null)
        {
            for (int i = 0; i < listaPkIdSubcategoriasDasDoencasSeleccionadas.size(); i++)
            {
                for (AmbDiagnosticoHipoteseHasDoenca adhd : retornaDoencas(listaPkIdSubcategoriasDasDoencasSeleccionadas.get(i)))
                {
                    if (adhd.getFkIdSubcategorias().getPkIdSubcategorias().equals(cd))
                    {
                        ambDiagnosticoHipoteseHasDoenca = adhd;
                    }
                    resultado.add(adhd);
                }

                if (i < listaPkIdSubcategoriasDasDoencasSeleccionadas.size() - 1)
                {
                    resultado.get(i).getFkIdSubcategorias().getNome();
                }
            }
        }
        return resultado;
    }     

    public List<AmbConsultaHasEspessura> retornaEspessura(int espessura)
    {
        ambConsultaHasEspessura = getInstanciaAmbConsultaHasEspessura();

        List<AmbConsultaHasEspessura> resultado = new ArrayList();

        for (AmbEspessura ambEspessura : ambEspessuraFacade.findAll())
        {
            if (ambEspessura.getPkIdEspessura() == espessura)
            {
                ambConsultaHasEspessura.setFkIdEspessura(ambEspessura);
                resultado.add(ambConsultaHasEspessura);
            }
        }
        return resultado;
    }

    public List<AmbConsultaHasEspessura> devolverEspessura()
    {
        List<AmbConsultaHasEspessura> resultadoEspessura = new ArrayList();
        int c = 0;

        if (listaEspessuraTecido != null)
        {
            for (int i = 0; i < listaEspessuraTecido.length; i++)
            {
                for (AmbConsultaHasEspessura ace : retornaEspessura(listaEspessuraTecido[i]))
                {
                    if (ace.getFkIdEspessura().getPkIdEspessura() == c)
                    {
                        ambConsultaHasEspessura = ace;
                    }
                    resultadoEspessura.add(ace);
                }

                if (i < listaEspessuraTecido.length - 1)
                {
                    resultadoEspessura.get(i).getFkIdEspessura().getDescricao();
                }
            }
        }
        return resultadoEspessura;
    }

    public List<AmbConsultaHasImpressoesGerais> retornaImpressoesGerais(int impressaoGeral)
    {
        ambConsultaHasImpressoesGerais = getInstanciaAmbConsultaHasImpressoesGerais();

        List<AmbConsultaHasImpressoesGerais> resultado = new ArrayList();

        for (AmbImpressoesGerais impressoesGerais : ambImpressoesGeraisFacade.findAll())
        {
            if (impressoesGerais.getPkIdImpressoesGerais() == impressaoGeral)
            {
                ambConsultaHasImpressoesGerais.setFkIdImpressoesGerais(impressoesGerais);
                resultado.add(ambConsultaHasImpressoesGerais);
            }
        }
        return resultado;
    }

    public List<AmbConsultaHasImpressoesGerais> devolverImpressoesGerais()
    {
        List<AmbConsultaHasImpressoesGerais> resultadoImpressoes = new ArrayList();
        int ig = 0;

        if (listaImpressoes != null)
        {
            for (int i = 0; i < listaImpressoes.length; i++)
            {
                for (AmbConsultaHasImpressoesGerais acig : retornaImpressoesGerais(listaImpressoes[i]))
                {
                    if (acig.getFkIdImpressoesGerais().getPkIdImpressoesGerais() == ig)
                    {
                        ambConsultaHasImpressoesGerais = acig;
                    }
                    resultadoImpressoes.add(acig);
                }

                if (i < listaImpressoes.length - 1)
                {
                    resultadoImpressoes.get(i).getFkIdImpressoesGerais().getDescricao();
                }
            }
        }
        return resultadoImpressoes;
    }

    public List<AmbConsultaHasTextura> retornaTextura(int textura)
    {
        ambConsultaHasTextura = getInstanciaAmbConsultaHasTextura();

        List<AmbConsultaHasTextura> resultado = new ArrayList();

        for (AmbTextura ambTextura : ambTexturaFacade.findAll())
        {
            if (ambTextura.getPkIdTextura() == textura)
            {
                ambConsultaHasTextura.setFkIdTextura(ambTextura);
                resultado.add(ambConsultaHasTextura);
            }
        }
        return resultado;
    }

    public List<AmbConsultaHasTextura> devolverTextura()
    {
        List<AmbConsultaHasTextura> resultadoTextura = new ArrayList();
        int ct = 0;

        if (listaTexturaPele != null)
        {
            for (int i = 0; i < listaTexturaPele.length; i++)
            {
                for (AmbConsultaHasTextura act : retornaTextura(listaTexturaPele[i]))
                {
                    if (act.getFkIdTextura().getPkIdTextura() == ct)
                    {
                        ambConsultaHasTextura = act;
                    }
                    resultadoTextura.add(act);
                }

                if (i < listaTexturaPele.length - 1)
                {
                    resultadoTextura.get(i).getFkIdTextura().getDescricao();
                }
            }
        }
        return resultadoTextura;
    }

    public List<AmbConsultaHasTurgorPele> retornaTurgorPele(int turgor)
    {
        ambConsultaHasTurgorPele = getInstanciaAmbConsultaHasTurgorPele();

        List<AmbConsultaHasTurgorPele> resultado = new ArrayList();

        for (AmbTurgor ambTurgorPele : ambTurgorFacade.findAll())
        {
            if (ambTurgorPele.getPkIdTurgor() == turgor)
            {
                ambConsultaHasTurgorPele.setFkIdTurgor(ambTurgorPele);
                resultado.add(ambConsultaHasTurgorPele);
            }
        }
        return resultado;
    }

    public List<AmbConsultaHasTurgorPele> devolverTurgorPele()
    {
        List<AmbConsultaHasTurgorPele> resultadoTurgor = new ArrayList();
        int ct = 0;

        if (listaTurgorPele != null)
        {
            for (int i = 0; i < listaTurgorPele.length; i++)
            {
                for (AmbConsultaHasTurgorPele act : retornaTurgorPele(listaTurgorPele[i]))
                {
                    if (act.getFkIdTurgor().getPkIdTurgor() == ct)
                    {
                        ambConsultaHasTurgorPele = act;
                    }
                    resultadoTurgor.add(act);
                }

                if (i < listaTurgorPele.length - 1)
                {
                    resultadoTurgor.get(i).getFkIdTurgor().getDescricao();
                }
            }
        }
        return resultadoTurgor;
    }

    public List<AmbConsultaHasTurgorTecido> retornaTurgorTecido(int turgor)
    {
        ambConsultaHasTurgorTecido = getInstanciaAmbConsultaHasTurgorTecido();

        List<AmbConsultaHasTurgorTecido> resultado = new ArrayList();

        for (AmbTurgor ambTurgorTecido : ambTurgorFacade.findAll())
        {
            if (ambTurgorTecido.getPkIdTurgor() == turgor)
            {
                ambConsultaHasTurgorTecido.setFkIdTurgor(ambTurgorTecido);
                resultado.add(ambConsultaHasTurgorTecido);
            }
        }
        return resultado;
    }

    public List<AmbConsultaHasTurgorTecido> devolverTurgorTecido()
    {
        List<AmbConsultaHasTurgorTecido> resultadoTurgorTecido = new ArrayList();
        int ct = 0;

        if (listaTurgorTecido != null)
        {
            for (int i = 0; i < listaTurgorTecido.length; i++)
            {
                for (AmbConsultaHasTurgorTecido actt : retornaTurgorTecido(listaTurgorTecido[i]))
                {
                    if (actt.getFkIdTurgor().getPkIdTurgor() == ct)
                    {
                        ambConsultaHasTurgorTecido = actt;
                    }
                    resultadoTurgorTecido.add(actt);
                }

                if (i < listaTurgorTecido.length - 1)
                {
                    resultadoTurgorTecido.get(i).getFkIdTurgor().getDescricao();
                }
            }
        }
        return resultadoTurgorTecido;
    }

    public List<AmbConsultorioAtendimento> pesquisarPacientesEncaminhadosConsulta()
    {
        return ambConsultaFacade.findPacientesEncaminhadosConsulta();
    }

    public List<AmbConsultorioAtendimento> pesquisarPacientesEncaminhadosReconsulta()
    {
        return ambConsultaFacade.findPacientesEncaminhadosReconsulta();
    }

    public void atualizarDadosPacientesTriagem()
    {
        for (AmbTriagem at : ambTriagemFacade.findAll())
        {
            if (at.getPkIdTriagem() == codigoTriagem)
            {
                ambTriagem = at;
                ambTriagem.setPkIdTriagem(codigoTriagem);
            }
        }
    }

    public List<AmbDiagnosticoHipoteseHasDoenca> pesquisaDePacienteConsultado(String np)
    {
        List<AmbDiagnosticoHipoteseHasDoenca> resultado = new ArrayList<>();

        for (AmbDiagnosticoHipoteseHasDoenca adhhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
            if (adhhd != null)
                if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                    if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdTipoSolicitacaoServico().getDescricaoTipoSolicitacaoServico().equals(Defs.TIPO_SOLICITACAO_PRIMEIRA))
                        resultado.add(adhhd);
        return resultado;
    }       
    
    public List<AmbDiagnosticoHipoteseHasDoenca> pesquisaDePacienteReconsultado(String np)
    {
        List<AmbDiagnosticoHipoteseHasDoenca> resultado = new ArrayList<>();

        for (AmbDiagnosticoHipoteseHasDoenca adhhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
            if (adhhd != null)
                if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                    if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdTipoSolicitacaoServico().getDescricaoTipoSolicitacaoServico().equals(Defs.TIPO_SOLICITACAO_RETORNO))
                        resultado.add(adhhd);
        return resultado;
    }    
    
    public List<AmbDiagnosticoHipoteseHasDoenca> findPacientesConsultados()
    {
        return ambConsultaFacade.findPacientesConsultados();
    }    
    
    public List<AmbDiagnosticoHipoteseHasDoenca> pesquisarConsultas()
    {
        return ambConsultaFacade.findConsulta(ambDiagnosticoHipoteseHasDoenca, dataInicio, dataFinal);
    }
    
    public List<AmbDiagnosticoHipoteseHasDoenca> pesquisarReconsultas()
    {
        return ambConsultaFacade.findReconsulta(ambDiagnosticoHipoteseHasDoenca, dataInicio, dataFinal);
    }    
    
    public List<AmbConsulta> findAll() 
    {
        return ambConsultaFacade.findAll();
    }
    
    public List<AmbAderencia> listarAderencia() 
    {
        return ambAderenciaFacade.findAll();
    }
    
    public List<AmbClassificacaoDor> listarClassificacaoDor() 
    {
        return ambClassificacaoDorFacade.findAll();
    }
    
    public List<AmbColoracao> listarColoracao() 
    {
        return ambColoracaoFacade.findAll();
    }
    
    public List<AmbCor> listarCor() 
    {
        return ambCorFacade.findAll();
    }
    
    public List<AmbEspessura> listarEspessura() 
    {
        return ambEspessuraFacade.findAll();
    }
    
    public List<AmbCidSubcategorias> listarDoencas() 
    {
        return ambCidSubcategoriasFacade.findAll();
    }
    
    public List<AmbEstadoHidratacao> listarEstadoHidratacao() 
    {
        return ambEstadoHidratacaoFacade.findAll();
    }

    public List<AmbImpressoesGerais> listarImpressoesGerais() 
    {
        return ambImpressoesGeraisFacade.findAll();
    }
    
    public List<AmbObservacoesMedicas> listarObservacoesMedicas() 
    {
        return ambObservacoesMedicasFacade.findAll();
    }
    
    public List<AmbTextura> listarTextura() 
    {
        return ambTexturaFacade.findAll();
    }
    
    public List<AmbTurgor> listarTurgor() 
    {
        return ambTurgorFacade.findAll();
    }
    
    public List<GrlTamanho> listarTamanho() 
    {
        return grlTamanhoFacade.findAll();
    }    
    
    public String dataSistema()
    {
        return DataUtils.dataTimeAgoraFull();
    }
    
    public AmbConsulta seleccionarDetalhesConsulta(AmbConsulta ac)
    {
        acAux = ac;
//System.err.print("ambConsultaListarBean.seleccionarDetalhesConsulta(): " + acAux.toString());
        return acAux;
    }

    public AmbDiagnosticoHipotese seleccionarDetalhesAmbDiagnosticoHipotese(AmbDiagnosticoHipotese adh)
    {
        adhAux = adh;
        return adhAux;
    }
  
    public List<AmbConsultaHasImpressoesGerais> devolveImpressoesGerais(AmbConsulta ac) 
    {
        List<AmbConsultaHasImpressoesGerais> resultado = new ArrayList<>();
        
        if (!ambConsultaHasImpressoesGeraisFacade.findAll().isEmpty())
        {
            for (AmbConsultaHasImpressoesGerais achig : ambConsultaHasImpressoesGeraisFacade.findAll())
            {
                 if (ac != null)
                 {
                     if (achig != null)
                     {
//System.err.print("Valor: " + ac.toString());
                         if (ac.equals(achig.getFkIdConsulta()))
                         {
                             resultado.add(achig);
                         }
                     }
                 }
            }
        }
        return resultado;
    }    
    
    public StringBuilder listarImpressoesGerais(AmbConsulta ac) 
    {
        StringBuilder resultado = new StringBuilder();
        
        if (!devolveImpressoesGerais(ac).isEmpty())
            for (int i = 0; i < devolveImpressoesGerais(ac).size(); i++)
                 resultado.append(devolveImpressoesGerais(ac).get(i).getFkIdImpressoesGerais().getDescricao()).append(". ");
        
        return resultado;
    }    
    
    public List<AmbConsultaHasColoracao> devolveColoracao(AmbConsulta ac) 
    {
        List<AmbConsultaHasColoracao> resultado = new ArrayList<>();
        
        if (!ambConsultaHasColoracaoFacade.findAll().isEmpty())
            for (AmbConsultaHasColoracao achc : ambConsultaHasColoracaoFacade.findAll())
                 if (ac != null)
                     if (achc != null)
                         if (ac.equals(achc.getFkIdConsulta()))
                             resultado.add(achc);
        return resultado;
    }    
    
    public StringBuilder listarColoracao(AmbConsulta ac) 
    {
        StringBuilder resultado = new StringBuilder();
        
        if (!devolveColoracao(ac).isEmpty())
            for (int i = 0; i < devolveColoracao(ac).size(); i++)
                 resultado.append(devolveColoracao(ac).get(i).getFkIdColoracao().getDescricao()).append(". ");
        
        return resultado;
    }    
    
    public List<AmbConsultaHasCor> devolveCor(AmbConsulta ac) 
    {
        List<AmbConsultaHasCor> resultado = new ArrayList<>();
        
        if (!ambConsultaHasCorFacade.findAll().isEmpty())
            for (AmbConsultaHasCor achc : ambConsultaHasCorFacade.findAll())
                 if (ac != null)
                     if (achc != null)
                         if (ac.equals(achc.getFkIdConsulta()))
                             resultado.add(achc);
        return resultado;
    }    
    
    public StringBuilder listarCor(AmbConsulta ac) 
    {
        StringBuilder resultado = new StringBuilder();
        
        if (!devolveCor(ac).isEmpty())
            for (int i = 0; i < devolveCor(ac).size(); i++)
                 resultado.append(devolveCor(ac).get(i).getFkIdCor().getDescricao()).append(". ");
        
        return resultado;
    }    
    
    public List<AmbConsultaHasTextura> devolveTextura(AmbConsulta ac) 
    {
        List<AmbConsultaHasTextura> resultado = new ArrayList<>();
        
        if (!ambConsultaHasTexturaFacade.findAll().isEmpty())
            for (AmbConsultaHasTextura acht : ambConsultaHasTexturaFacade.findAll())
                 if (ac != null)
                     if (acht != null)
                         if (ac.equals(acht.getFkIdConsulta()))
                             resultado.add(acht);
        return resultado;
    }     
    
    public StringBuilder listarTextura(AmbConsulta ac) 
    {
        StringBuilder resultado = new StringBuilder();
        
        if (!devolveTextura(ac).isEmpty())
            for (int i = 0; i < devolveTextura(ac).size(); i++)
                 resultado.append(devolveTextura(ac).get(i).getFkIdTextura().getDescricao()).append(". ");
        
        return resultado;
    }     
    
    public List<AmbConsultaHasTurgorPele> devolveTurgorPele(AmbConsulta ac) 
    {
        List<AmbConsultaHasTurgorPele> resultado = new ArrayList<>();
        
        if (!ambConsultaHasTurgorPeleFacade.findAll().isEmpty())
            for (AmbConsultaHasTurgorPele achtp : ambConsultaHasTurgorPeleFacade.findAll())
                 if (ac != null)
                     if (achtp != null)
                         if (ac.equals(achtp.getFkIdConsulta()))
                             resultado.add(achtp);
        return resultado;
    }    
    
    public StringBuilder listarTurgorPele(AmbConsulta ac) 
    {
        StringBuilder resultado = new StringBuilder();
        
        if (!devolveTurgorPele(ac).isEmpty())
            for (int i = 0; i < devolveTurgorPele(ac).size(); i++)
                 resultado.append(devolveTurgorPele(ac).get(i).getFkIdTurgor().getDescricao()).append(". ");
        
        return resultado;
    }    
    
    public List<AmbConsultaHasEspessura> devolveEspessura(AmbConsulta ac) 
    {
        List<AmbConsultaHasEspessura> resultado = new ArrayList<>();
        
        if (!ambConsultaHasEspessuraFacade.findAll().isEmpty())
            for (AmbConsultaHasEspessura ache : ambConsultaHasEspessuraFacade.findAll())
                 if (ac != null)
                     if (ache != null)
                         if (ac.equals(ache.getFkIdConsulta()))
                             resultado.add(ache);
        return resultado;
    }    
  
    public StringBuilder listarEspessura(AmbConsulta ac) 
    {
        StringBuilder resultado = new StringBuilder();
        
        if (!devolveEspessura(ac).isEmpty())
            for (int i = 0; i < devolveEspessura(ac).size(); i++)
                 resultado.append(devolveEspessura(ac).get(i).getFkIdEspessura().getDescricao()).append(". ");
        
        return resultado;
    }    
    
    public List<AmbConsultaHasTurgorTecido> devolveTurgorTecido(AmbConsulta ac) 
    {
        List<AmbConsultaHasTurgorTecido> resultado = new ArrayList<>();
        
        if (!ambConsultaHasTurgorTecidoFacade.findAll().isEmpty())
            for (AmbConsultaHasTurgorTecido achtt : ambConsultaHasTurgorTecidoFacade.findAll())
                 if (ac != null)
                     if (achtt != null)
                         if (ac.equals(achtt.getFkIdConsulta()))
                             resultado.add(achtt);
        return resultado;
    }    
    
    public StringBuilder listarTurgorTecido(AmbConsulta ac) 
    {
        StringBuilder resultado = new StringBuilder();
        
        if (!devolveTurgorTecido(ac).isEmpty())
            for (int i = 0; i < devolveTurgorTecido(ac).size(); i++)
                 resultado.append(devolveTurgorTecido(ac).get(i).getFkIdTurgor().getDescricao()).append(". ");
        
        return resultado;
    }    
    
    /*********************Fim dos métodos para pesquisas***********************/
   
    
    public boolean verificaConsulta()
    {
        if (retornaEncaminhamentos() != null)
            return !retornaEncaminhamentos().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente()
                   .getFkIdPessoa().getFkIdSexo().getDescricao().equals(Defs.FEMININO);
        else
            return true;
    }
    
    public boolean verificaReconsulta()
    {
        if (retornaEncaminhamentos() != null)
            return !retornaEncaminhamentos().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente()
                   .getFkIdPessoa().getFkIdSexo().getDescricao().equals(Defs.FEMININO);
        else
            return true;
    }    
}
