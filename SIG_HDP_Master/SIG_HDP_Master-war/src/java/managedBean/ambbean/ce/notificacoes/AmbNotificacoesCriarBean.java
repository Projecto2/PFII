/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.notificacoes;

import entidade.AdmsAgendamento;
import entidade.AmbConsultorioAtendimento;
import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHipoteseHasDoenca;
import entidade.AmbEstadoNotificacao;
import entidade.AmbNotificacoes;
import entidade.AmbTriagem;
import entidade.DiagExameRealizado;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.ambbean.ce.consultas.AmbConsultaListarBean;
import managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoListarBean;
import managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoListarBean;
import managedBean.ambbean.ce.receitas.AmbPrescricaoMedicaListarBean;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAdmsAgendamento;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import managedBean.ambbean.ce.triagem.AmbTriagemListarBean;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbEstadoNotificacaoFacade;
import sessao.AmbNotificacoesFacade;
import sessao.AmbTriagemFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbNotificacoesCriarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade;    
    @EJB
    private AmbEstadoNotificacaoFacade ambEstadoNotificacaoFacade;
    @EJB
    private AmbNotificacoesFacade ambNotificacoesFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;

    private AmbEstadoNotificacao ambEstadoNotificacao;
    private AmbNotificacoes ambNotificacoes;
    private AmbTriagem ambTriagem;

    /**
     * Creates a new instance of AmbNotificacoesCriarBean
     */
    public AmbNotificacoesCriarBean()
    {
    }

    public static AmbNotificacoesCriarBean getInstanciaBean()
    {
        return (AmbNotificacoesCriarBean) GeradorCodigo.getInstanciaBean("ambNotificacoesCriarBean");
    }

    public static AmbTriagemCriarBean getInstanciaAmbTriagemBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemCriarBean");
    }

    public static AmbNotificacoes getInstanciaAmbNotificacoes()
    {
        AmbNotificacoes ambNotificacoes = new AmbNotificacoes();

        ambNotificacoes.setFkIdAgendamento(getInstanciaAdmsAgendamento());
        ambNotificacoes.setFkIdEstado(new AmbEstadoNotificacao());

        return ambNotificacoes;
    }

    public AmbEstadoNotificacao getAmbEstadoNotificacao()
    {
        if (ambEstadoNotificacao == null)
        {
            ambEstadoNotificacao = new AmbEstadoNotificacao();
        }
        return ambEstadoNotificacao;
    }

    public void setAmbEstadoNotificacao(AmbEstadoNotificacao ambEstadoNotificacao)
    {
        this.ambEstadoNotificacao = ambEstadoNotificacao;
    }

    public AmbNotificacoes getAmbNotificacoes()
    {
        if (ambNotificacoes == null)
        {
            ambNotificacoes = getInstanciaAmbNotificacoes();
        }
        return ambNotificacoes;
    }

    public void setAmbNotificacoes(AmbNotificacoes ambNotificacoes)
    {
        this.ambNotificacoes = ambNotificacoes;
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

    public String concatenaStrings
    (String str1, String str2, String str3, String str4, String str5,
     String str6, String str7, String str8, String str9, String str10)
    {
        StringBuilder sb = new StringBuilder();
        
        if (!str10.equals(Defs.STRING_VAZIA))
        {
            sb.append(str1);
            sb.append(str2);
            sb.append(str3);
            sb.append(str4);
            sb.append(str5);
            sb.append(str6);
            sb.append(str7);
            sb.append(str8);
            sb.append(str9);
            sb.append(str10);
        } else
        {
            sb.append(str1);
            sb.append(str2);
            sb.append(str3);
            sb.append(str4);
            sb.append(str5);
            sb.append(str6);
            sb.append(str7);
            sb.append(str8);
            sb.append(str9);
        }
        
        return sb.toString();
    }    
    
    public void criarAmbNotificacaoConsulta()
    {
        try
        {
            this.userTransaction.begin();

            ambNotificacoes = getInstanciaAmbNotificacoes();

            if (AmbConsultaListarBean.getInstanciaBean().pesquisarPacientesEncaminhadosConsulta() != null)
            {
                for (AmbConsultorioAtendimento aca : AmbConsultaListarBean.getInstanciaBean().pesquisarPacientesEncaminhadosConsulta())
                {
                    if (aca != null)
                    {
                        for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
                        {
                             if (an != null)
                             {
                                    ambNotificacoes.setData(new Date());
                                    
                                    ambNotificacoes.setDescricao
                                    (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                          aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                          aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_CONSULTADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 1 : " + ambNotificacoes.getDescricao()); 
                                    ambNotificacoes.setFkIdAgendamento(aca.getFkIdTriagem().getFkIdAgendamento());

                                    for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                                        if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_CONSULTADO))
                                           ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
                                
                                    ambNotificacoesFacade.create(ambNotificacoes);
                             }
                            
                        }
                    }
                    
                    if (AmbConsultaListarBean.getInstanciaBean().findAll().isEmpty())
                    {
                        ambNotificacoes.setData(new Date());
                        
                        ambNotificacoes.setDescricao
                        (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                          aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                          aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_CONSULTADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 2 : " + ambNotificacoes.getDescricao());                        
                        ambNotificacoes.setFkIdAgendamento(aca.getFkIdTriagem().getFkIdAgendamento());
                        
                        for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                            if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_CONSULTADO))
                               ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));

                        ambNotificacoesFacade.create(ambNotificacoes);
                    }                   
                }
            }
            
            this.userTransaction.commit();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }     
    
    public void criarAmbNotificacaoDiagnostico()
    {
        try
        {
            this.userTransaction.begin();

            ambNotificacoes = getInstanciaAmbNotificacoes();

            if (AmbDiagnosticoListarBean.getInstanciaBean().findConsultasExame() != null)
            {
                for (DiagExameRealizado der : AmbDiagnosticoListarBean.getInstanciaBean().findConsultasExame())
                {
                    for (AdmsAgendamento aa : der.getFkIdServicoSolicitado().getAdmsAgendamentoList())
                    {
                        for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
                        {
                            for (AmbDiagnostico ad: ambDiagnosticoFacade.findAll())
                            {
                                if (ad != null)
                                {
                                    ambNotificacoes.setData(new Date());
                                    
                                    ambNotificacoes.setDescricao
                                    (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA,
                                                      ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                                      ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_DIAGNOSTICADO, Defs.PONTO));
                                    
                                    ambNotificacoes.setFkIdAgendamento(ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento());
                                
                                    for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                                         if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_DIAGNOSTICADO))
                                             ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
                                
                                    ambNotificacoesFacade.create(ambNotificacoes);
                                }
                            }
                        }
                    
                        if (ambDiagnosticoFacade.findAll().isEmpty())
                        {
                            ambNotificacoes.setData(new Date());
                        
                            ambNotificacoes.setDescricao
                            (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA,
                                              aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                              aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_DIAGNOSTICADO, Defs.PONTO));
                        
                            ambNotificacoes.setFkIdAgendamento(aa);
                            
                            for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                                if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_DIAGNOSTICADO))
                                    ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));

                            ambNotificacoesFacade.create(ambNotificacoes);
                        }
                    }
                }
            }
            
            this.userTransaction.commit();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }     
    
    public void criarAmbNotificacaoEncaminhamento()
    {
        try
        {
            this.userTransaction.begin();

            ambNotificacoes = getInstanciaAmbNotificacoes();

            if (AmbConsultorioAtendimentoListarBean.getInstanciaBean().listarDadosTriagens() != null)
            {
                for (AmbTriagem at : AmbConsultorioAtendimentoListarBean.getInstanciaBean().listarDadosTriagens())
                {
                    if (at != null)
                    {
                        for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
                        {
                             if (an != null)
                             {
                                 ambNotificacoes.setData(new Date());
                                    
                                 ambNotificacoes.setDescricao
                                 (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                                   at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                                   at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_ENCAMINHADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 1 : " + ambNotificacoes.getDescricao()); 
                                 ambNotificacoes.setFkIdAgendamento(at.getFkIdAgendamento());

                                 for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                                      if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_ENCAMINHADO))
                                          ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
                                
                                 ambNotificacoesFacade.create(ambNotificacoes);
                             }
                            
                        }
                    }
                    
                    if (AmbConsultorioAtendimentoListarBean.getInstanciaBean().findAll().isEmpty())
                    {
                        ambNotificacoes.setData(new Date());
                        
                        ambNotificacoes.setDescricao
                        (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                          at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                          at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_ENCAMINHADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 2 : " + ambNotificacoes.getDescricao());                        
                        ambNotificacoes.setFkIdAgendamento(at.getFkIdAgendamento());
                        
                        for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                            if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_ENCAMINHADO))
                               ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));

                        ambNotificacoesFacade.create(ambNotificacoes);
                    }                   
                }
            }
            
            this.userTransaction.commit();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    public void criarAmbNotificacaoPrimeira()
    {
        try
        {
             for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
             {
System.err.print("Tamanho do Vector: " + ambNotificacoesFacade.findAll().size());
System.err.print("Descrição        : " + ambNotificacoesFacade.find(Long.parseLong("" + an.getPkIdNotificacoes())).getDescricao());
System.err.print("Data             : " + ambNotificacoesFacade.find(Long.parseLong("" + an.getPkIdNotificacoes())).getData());//Long.parseLong("" + 81)
             }
////            this.userTransaction.begin();
//
//            ambNotificacoes = getInstanciaAmbNotificacoes();
//
//            if (AmbTriagemListarBean.getInstanciaBean().findSolicitacoes() != null)
//            {
//                for (AdmsAgendamento aa : AmbTriagemListarBean.getInstanciaBean().findSolicitacoes())
//                {
//                    if (ambNotificacoesFacade.findAll() != null)
//                    {
//                        for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
//                        {
//                            if (an != null)
//                            {
////System.out.print("ambNotificacoesBean.criarAmbNotificacaoPrimeira(): Teste 1 : " + an.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso());                                 
////System.out.print("ambNotificacoesBean.criarAmbNotificacaoPrimeira(): Teste 2 : " + aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso());                                 
//                                ambNotificacoes.setData(new Date());
//                                
//                                ambNotificacoes.setDescricao
//                                (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA,
//                                                  aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
//                                                  aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_PRIMEIRA_NOTIFICACAO, Defs.PONTO, Defs.STRING_VAZIA));
//                                
////System.err.print("Descrição 1: " + ambNotificacoes.getDescricao());   
//System.err.print("Descrição 2: " + ambNotificacoesFacade.findAll().get(ambNotificacoesFacade.findAll().size() - 1).getDescricao()); 
//                                
////                                if (!ambNotificacoes.getDescricao().equals(ambNotificacoesFacade.findAll().get(ambNotificacoesFacade.findAll().size()).getDescricao()))
//                                    ambNotificacoes.setFkIdAgendamento(aa);
//                                
//                                for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
//                                    if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_EM_ESPERA))
////                                        if (!ambNotificacoes.getDescricao().equals(an.getDescricao()))
//                                            ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
//                                
////                                if (!ambNotificacoes.getDescricao().equals(an.getDescricao()))
////                                    ambNotificacoesFacade.create(ambNotificacoes);
//                            }
//                        }
//                    }
//                    
//                    if (ambNotificacoesFacade.findAll().isEmpty())
//                    {
//System.err.print("Estava Vazia ! ! !");
//                        ambNotificacoes.setData(new Date());
//                        
//                        ambNotificacoes.setDescricao
//                        (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA,
//                                          aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
//                                          aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_PRIMEIRA_NOTIFICACAO, Defs.PONTO, Defs.STRING_VAZIA));
//                       
//                        ambNotificacoes.setFkIdAgendamento(aa);
//                        
//                        for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
//                            if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_EM_ESPERA))
//                               ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
//
////                        ambNotificacoesFacade.create(ambNotificacoes);
//                    }                   
//                }
//            }
//            
////            this.userTransaction.commit();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    public void criarAmbNotificacaoReceitaConsulta()
    {
        try
        {
            this.userTransaction.begin();

            ambNotificacoes = getInstanciaAmbNotificacoes();

            if (AmbConsultaListarBean.getInstanciaBean().pesquisarConsultas() != null)
            {
                for (AmbDiagnosticoHipoteseHasDoenca adhhd : AmbConsultaListarBean.getInstanciaBean().pesquisarConsultas())
                {
                    if (adhhd != null)
                    {
                        for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
                        {
                             if (an != null)
                             {
                                 ambNotificacoes.setData(new Date());
                                    
                                 ambNotificacoes.setDescricao
                                 (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                                   adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                                   adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_RECEITADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 1 : " + ambNotificacoes.getDescricao()); 
                                 ambNotificacoes.setFkIdAgendamento(adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento());

                                 for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                                      if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_RECEITADO))
                                          ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
                                
                                 ambNotificacoesFacade.create(ambNotificacoes);
                             }
                            
                        }
                    }
                    
                    if (AmbPrescricaoMedicaListarBean.getInstanciaBean().findAll().isEmpty())
                    {
                        ambNotificacoes.setData(new Date());
                        
                        ambNotificacoes.setDescricao
                        (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                          adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                          adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_RECEITADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 2 : " + ambNotificacoes.getDescricao());                        
                        ambNotificacoes.setFkIdAgendamento(adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento());
                        
                        for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                            if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_RECEITADO))
                               ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));

                        ambNotificacoesFacade.create(ambNotificacoes);
                    }                   
                }
            }
            
            this.userTransaction.commit();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }    
    
    public void criarAmbNotificacaoReconsulta()
    {
        try
        {
            this.userTransaction.begin();

            ambNotificacoes = getInstanciaAmbNotificacoes();

            if (AmbConsultaListarBean.getInstanciaBean().pesquisarPacientesEncaminhadosConsulta() != null)
            {
                for (AmbConsultorioAtendimento aca : AmbConsultaListarBean.getInstanciaBean().pesquisarPacientesEncaminhadosConsulta())
                {
                    if (aca != null)
                    {
                        for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
                        {
                             if (an != null)
                             {
                                 ambNotificacoes.setData(new Date());
                                    
                                 ambNotificacoes.setDescricao
                                 (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                                   aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                                   aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_RECONSULTADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 1 : " + ambNotificacoes.getDescricao()); 
                                 ambNotificacoes.setFkIdAgendamento(aca.getFkIdTriagem().getFkIdAgendamento());

                                 for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                                      if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_RECONSULTADO))
                                          ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
                                
                                 ambNotificacoesFacade.create(ambNotificacoes);
                             }
                            
                        }
                    }
                    
                    if (AmbConsultaListarBean.getInstanciaBean().findAll().isEmpty())
                    {
                        ambNotificacoes.setData(new Date());
                        
                        ambNotificacoes.setDescricao
                        (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA, 
                                          aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                          aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.FIM_ECRDR, Defs.ESTADO_NOTIFICACAO_RECONSULTADO, Defs.PONTO));
//System.out.print("ambNotificacoesBean.criarAmbNotificacaoEncaminhamento(): Teste 2 : " + ambNotificacoes.getDescricao());                        
                        ambNotificacoes.setFkIdAgendamento(aca.getFkIdTriagem().getFkIdAgendamento());
                        
                        for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                            if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_RECONSULTADO))
                               ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));

                        ambNotificacoesFacade.create(ambNotificacoes);
                    }                   
                }
            }
            
            this.userTransaction.commit();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }     
    
    public void criarAmbNotificacaoTriagem()
    {
        try
        {
            this.userTransaction.begin();

            ambNotificacoes = getInstanciaAmbNotificacoes();

            if (AmbTriagemListarBean.getInstanciaBean().findSolicitacoes() != null)
            {
                for (AdmsAgendamento aa : AmbTriagemListarBean.getInstanciaBean().findSolicitacoes())
                {
                    for (AmbNotificacoes an : ambNotificacoesFacade.findAll())
                    {
                        for (AmbTriagem at: ambTriagemFacade.findAll())
                        {
                            if (at != null)
                            {
                                if (an != null)
                                {
                                    ambNotificacoes.setData(new Date());
                                    
                                    ambNotificacoes.setDescricao
                                    (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA,
                                          at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                          at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.CONCLUSAO_TRIAGEM, Defs.ESTADO_NOTIFICACAO_TRIAGEM, Defs.PONTO));
                                    
                                    ambNotificacoes.setFkIdAgendamento(at.getFkIdAgendamento());
                                
                                    for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                                        if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_TRIAGEM))
                                           ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));
                                
                                    ambNotificacoesFacade.create(ambNotificacoes);
                                }
                            }
                        }
                    }
                    
                    if (AmbConsultorioAtendimentoListarBean.getInstanciaBean().listarDadosTriagens().isEmpty())
                    {
                        ambNotificacoes.setData(new Date());
                        
                        ambNotificacoes.setDescricao
                        (concatenaStrings(Defs.INICIO_NOTIFICACAO, Defs.STRING_VAZIA, aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome(), Defs.STRING_VAZIA,
                                          aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio(), Defs.STRING_VAZIA, 
                                          aa.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome(), Defs.CONCLUSAO_TRIAGEM, Defs.ESTADO_NOTIFICACAO_TRIAGEM, Defs.PONTO));
                        
                        ambNotificacoes.setFkIdAgendamento(aa);
                        
                        for (int i = 0; i < ambEstadoNotificacaoFacade.findAll().size(); i++)
                            if (ambEstadoNotificacaoFacade.findAll().get(i).getDescricao().equals(Defs.ESTADO_NOTIFICACAO_TRIAGEM))
                               ambNotificacoes.setFkIdEstado(ambEstadoNotificacaoFacade.findAll().get(i));

                        ambNotificacoesFacade.create(ambNotificacoes);
                    }                   
                }
            }
            
            this.userTransaction.commit();

        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }    
}
