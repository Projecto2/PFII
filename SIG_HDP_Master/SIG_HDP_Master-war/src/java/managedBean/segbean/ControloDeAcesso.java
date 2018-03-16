/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.SegConta;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sessao.SegPerfilHasFuncionalidadeFacade;

/**
 *
 * @author adalberto
 */
public class ControloDeAcesso implements Serializable
{

    @EJB
    private SegPerfilHasFuncionalidadeFacade perfilFuncionalidadeFacade;
    private SegConta contaUtilizador;

    private String[] funcionalidadeSEG =
    {
        "Navegação Segurança","Conta", "Funcionalidades", "Perfis","Privilegios", "Permissao"
    };
    private String[] funcionalidadeAMB =
    {
        "CID10", "Actualizar Tabelas", "Gerir Perfis", "Configurar Perfis", "Hipótese de Diagnóstico",
        "Consultas Externas", "Enfermagem", "Consultas", "Triagem", "Clínica Geral", "Especialidade","","","","","","","","","","","","","","","","","","","",
    };

    private String[] funcionalidadeRH =
    {
        "Ingresso", "Contratos", "Horários","Assiduidade","Férias","Pessoas","Funcionários","Candidatos","Estagiários",
        "Novo Contrato","Renovaçao e Pesquisa de Contratos","Historico de Contratos","Horário Geral de Trabalho","Definir Horário de Funcionario",
        "Remover Horários de Funcionarios","Avaliaçao de Desempenho","Registo de falta de Funcionario","Plano de Férias","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };
    private String[] funcionalidadeFARM =
    {
        "Farmácia","Produtos", "Fornecimentos","Pedidos","Dispensas","Armazenamento","Requisicao",
        "Pendentes","Realizados","Cancelados","Atendimento","Realizadas","Locais","","","","","","","","","","","","","","","","",
    };
    private String[] funcionalidadeADMS =
    {
        "Admissões", "Pacientes","Listagens","Pacientes","Solicitações","Agendamentos","Solicitações","Agendamentos","",
    };
    private String[] funcionalidadeINTER =
    {
        "Enfermagem", "Internamento Medicina","Internamento Pediatrico","Internamento CNT","Pedidos para o Internamento",
        "Pacientes","Salas","Camas","Relatórios","Imprimir Documentos","Farmácia","Internados","Movimento Doentes",
        "Arquivados","Medicação Pacientes","Medicamentos","Solicitação","Movimentos","Consulta ao Stock Geral",
        "Consulta ao Stock Do Internamento","Enfermarias","Configurações","Tabelas Auxiliares","Estados Cama Internamento",
        "Tipos de Doença Internamento","Tipos de Notificações","Tipos de Alta","Tipos de Saída","Pulso Unidade","Parametros Vitais","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };
    private String[] funcionalidadeDIAG =
    {
        "Laboratório", "Imagiologia","Análises Clínicas","Hemoterápia","Gestão de Exames","Exames de Rotina","Exames de Urgência/Emergência",
        "Farmácia","Solicitações de Exames","Resultados de Exames","Solicitações de Materiais","Movimentos",
        "Stock","Candidatos à Dador","Banco de Sangue","Tipagem à Pacientes","Transfusões","Solicitações de Tipagem","Resultados de Tipagem",
        "Solicitações de Componentes Sanguíneos","Solicitações de Componentes Sanguíneos Aprovadas","Histórico de Entrada e Saída","Gestão de Exames","Radiografia",
        "Ecografia","Tabelas Auxiliares","Categorias de Exames","Subcategorias de Exames","Tipos de Amostra",
        "Grupos Sanguíneos","Tipos Componentes Sanguíneos","","","","","","","","","","","","","","","","","",
    };
    private String[] funcionalidadeSUPI =
    {
        "Enfermeiros", "Escalas","Avaliação de Estagiário","Formação de Enfermeiros","Listar Enfermeiros",
        "Escala de Supervisores Pac","Escala de Supervisores","Escala de Enfermeiros","Escala de Estagiários",
        "Escala de Médicos","Pontuações","Critérios","Avaliações","Formadores","Registar Formação","Listar Formações",
        "Adicionar Enfermeiro","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };
    private String[] funcionalidadeFIN =
    {
        "Tesouraria","Pacientes", "Listagens", "Encargos Devidos","Pagamentos", "Pagamentos","","","","","","","","","","","","","","","","","","",
    };
    private String[] funcionalidadeGRL =
    {
        "Localização","Pessoas", "Ainda não sei", "Outros","Países", "Províncias","Municípios",
        "Distritos","Comunas","Religião","Área Interna","Centro Hospitalar","Convênio","Especialidade",
        "Fornecedor","Instituição","Marca (Laboratório)","","","","","","","","","","",
        "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };

    public ControloDeAcesso()
    {
    }

    public void preRenderView()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    /*verificar injeccoes via URL consoante as permissoes das regras nos submodulo*/
    public void injeccaoViaURL(String permissao)
    {
        //boolean temPermissao = regraPerfilFacade.temAcessoRegraPermissaoUtilizador(contaUtilizador.getFksegperf().getPksegperf(), permissao, regra);
        boolean temPermissao = perfilFuncionalidadeFacade.temAcessoPermissaoUtilizador(contaUtilizador.getFkIdPerfil().getPkIdPerfil(), permissao);
        if (temPermissao)
        {
            System.out.println("tem regra");
        }
        else
        {
            System.out.println("nao regra");
            redirectedForPage("../loginAcessoNegadoSeg.xhtml");

        }
    }

    /*controlar permissões forçadas via URL*/
    public void injeccaoPermissaViaURL() throws IOException
    {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAdress = request.getHeader("X-FORWARDED-FOR");
        System.out.println("ip inject url:" + ipAdress);
        if (ipAdress == null)
        {
            ipAdress = request.getRemoteAddr();
        }

        if (contaUtilizador.getPkIdConta() != null)
        {
            //gravar o registo de logs na base de dados
            /*Seglogacess logAcesso = new Seglogacess();
             logAcesso.setEventoseglogacess("Hacker");
             logAcesso.setRiscoseglogacess("Alta");
             logAcesso.setOperadorseglogacess(username);
             logAcesso.setTipousuarioseglogacess(contaUtilizador.getFksegtipoutil().getDescsegtipoutil());
             logAcesso.setDataregseglogacess(new Date());
             logAcesso.setIpseglogacess(getIpAdressClient());
             logAcesso.setResultseglogacess("Sucesso");
             logAcesso.setFksegcontutil(contaUtilizador);
             logAcesso.setDetalhesseglogacess("Forcar Permissao via URL negado, Nome do utilizador: " + contaUtilizador.getFksegtipoutil().getDescsegtipoutil() + ": " + contaUtilizador.getFkfuncionario().getFkpessoa().getNome() + " " + contaUtilizador.getFkfuncionario().getFkpessoa().getSobrenome());

             logAcessoFacade.create(logAcesso);
             */
        }
    }

    public void redirectedForPage(String page)
    {
        try
        {

            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        }
        catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(SegLoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SegConta getContaUtilizador()
    {
        return contaUtilizador;
    }

    public void setContaUtilizador(SegConta contaUtilizador)
    {
        this.contaUtilizador = contaUtilizador;
    }

    public String[] getFuncionalidadeSEG()
    {
        return funcionalidadeSEG;
    }

    public void setFuncionalidadeSEG(String[] funcionalidadeSEG)
    {
        this.funcionalidadeSEG = funcionalidadeSEG;
    }

    public String[] getFuncionalidadeAMB()
    {
        return funcionalidadeAMB;
    }

    public void setFuncionalidadeAMB(String[] funcionalidadeAMB)
    {
        this.funcionalidadeAMB = funcionalidadeAMB;
    }

    public String[] getFuncionalidadeRH()
    {
        return funcionalidadeRH;
    }

    public void setFuncionalidadeRH(String[] funcionalidadeRH)
    {
        this.funcionalidadeRH = funcionalidadeRH;
    }

    public String[] getFuncionalidadeFARM()
    {
        return funcionalidadeFARM;
    }

    public void setFuncionalidadeFARM(String[] funcionalidadeFARM)
    {
        this.funcionalidadeFARM = funcionalidadeFARM;
    }

    public String[] getFuncionalidadeADMS()
    {
        return funcionalidadeADMS;
    }

    public void setFuncionalidadeADMS(String[] funcionalidadeADMS)
    {
        this.funcionalidadeADMS = funcionalidadeADMS;
    }

    public String[] getFuncionalidadeINTER()
    {
        return funcionalidadeINTER;
    }

    public void setFuncionalidadeINTER(String[] funcionalidadeINTER)
    {
        this.funcionalidadeINTER = funcionalidadeINTER;
    }

    public String[] getFuncionalidadeDIAG()
    {
        return funcionalidadeDIAG;
    }

    public void setFuncionalidadeDIAG(String[] funcionalidadeDIAG)
    {
        this.funcionalidadeDIAG = funcionalidadeDIAG;
    }

    public String[] getFuncionalidadeSUPI()
    {
        return funcionalidadeSUPI;
    }

    public void setFuncionalidadeSUPI(String[] funcionalidadeSUPI)
    {
        this.funcionalidadeSUPI = funcionalidadeSUPI;
    }

    public String[] getFuncionalidadeFIN()
    {
        return funcionalidadeFIN;
    }

    public void setFuncionalidadeFIN(String[] funcionalidadeFIN)
    {
        this.funcionalidadeFIN = funcionalidadeFIN;
    }

    public String[] getFuncionalidadeGRL()
    {
        return funcionalidadeGRL;
    }

    public void setFuncionalidadeGRL(String[] funcionalidadeGRL)
    {
        this.funcionalidadeGRL = funcionalidadeGRL;
    }
    

}
