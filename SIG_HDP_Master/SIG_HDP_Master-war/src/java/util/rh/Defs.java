/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.rh;

import managedBean.segbean.SegcontroloSessaoBean;

/**
 *
 * @author Ornela F. Boaventura
 */
public class Defs
{
    public static final String FILE_ESTADO_CANDIDATO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_estado_candidato.xls";
    public static final String FILE_ESTADO_CONTRATO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_estado_contrato.xls";
    public static final String FILE_ESTADO_ESTAGIARIO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_estado_estagiario.xls";
    public static final String FILE_ESTADO_FERIAS_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_estado_ferias.xls";
    public static final String FILE_ESTADO_FUNCIONARIO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_estado_funcionario.xls";
    
    public static final String FILE_NIVEL_ACADEMICO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_nivel_academico.xls";
    public static final String FILE_PERIODO_AULAS_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_periodo_aulas.xls";
    
    public static final String FILE_TIPO_CONTRATO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_tipo_contrato.xls";
    public static final String FILE_TIPO_ESTAGIO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_tipo_estagio.xls";
    public static final String FILE_TIPO_FALTA_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_tipo_falta.xls";
    public static final String FILE_TIPO_FUNCIONARIO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_tipo_funcionario.xls";

    public static final String FILE_DEPARTAMENTO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_departamento.xls";
    public static final String FILE_SECCAO_TRABALHO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_seccao_trabalho.xls";
    
    public static final String FILE_TIPO_DE_HORARIO_TRABALHO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_tipo_de_horario_trabalho.xls";
    
    public static final String FILE_PROFISSAO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_profissao.xls";
    public static final String FILE_CATEGORIA_PROFISSIONAL_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_categoria_profissional.xls";
    public static final String FILE_FUNCAO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_funcao.xls";
    public static final String FILE_SUBSIDIO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_subsidio.xls";
    public static final String FILE_FORMA_PAGAMENTO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_forma_pagamento.xls";
    
    public static final String FILE_CRITERIO_DE_AVALIACAO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_criterio_de_avaliacao.xls";
    public static final String FILE_CLASSIFICACAO_DO_CRITERIO_RH = "/home/HDP_FILES/Ficheiros_excel/rh_ficheiros_excel/rh_classificacao_do_criterio.xls";
    
    
    
    
        /**
     * @author Ornela F. Boaventura 
     * 
     * Endereço ftp da pasta onde são armazenados os anexos dos funcionários cadastrados
     */
    public static final String PASTA_ANEXO_FUNCIONARIO = "ftp://divina:divina@"+SegcontroloSessaoBean.getInstanciaBean().getIpMaquinaServidor()+"/Upload/anexoFuncionario/";

    /**
     * @author Ornela F. Boaventura 
     * 
     * Endereço ftp da pasta onde são armazenados os anexos dos candidatos cadastrados.
     */
    public static final String PASTA_ANEXO_CANDIDATO = "ftp://divina:divina@"+SegcontroloSessaoBean.getInstanciaBean().getIpMaquinaServidor()+"/Upload/anexoCandidato/";

    /**
     * @author Ornela F. Boaventura 
     * 
     * Endereço ftp da pasta onde são armazenados os anexos dos estagiários cadastrados.
     */
    public static final String PASTA_ANEXO_ESTAGIARIO = "ftp://divina:divina@"+SegcontroloSessaoBean.getInstanciaBean().getIpMaquinaServidor()+"/Upload/anexoEstagiario/";

    /**
     * @author Ornela F. Boaventura 
     * 
     * Endereço ftp da pasta onde são armazenados os anexos dos contratos realizados.
     */
    public static final String PASTA_ANEXO_CONTRATO = "ftp://divina:divina@"+SegcontroloSessaoBean.getInstanciaBean().getIpMaquinaServidor()+"/Upload/anexoContrato/";


    /**
     * @author Ornela F. Boaventura 
     * 
     * Localização da pasta onde são feitos os uploads de anexos dos funcionários cadastrados.
     */
    public static final String DESTINO_ANEXO_FUNCIONARIO = "/home/HDP_FILES/Upload/anexoFuncionario/";

    /**
     * @author Ornela F. Boaventura 
     * 
     * Localização da pasta onde são feitos os uploads de anexos dos candidatos cadastrados.
     */
    public static final String DESTINO_ANEXO_CANDIDATO = "/home/HDP_FILES/Upload/anexoCandidato/";

    /**
     * @author Ornela F. Boaventura 
     * 
     * Localização da pasta onde são feitos os uploads de anexos dos estagiários cadastrados.
     */
    public static final String DESTINO_ANEXO_ESTAGIARIO = "/home/HDP_FILES/Upload/anexoEstagiario/";

    /**
     * @author Ornela F. Boaventura 
     * 
     * Localização da pasta onde são feitos os uploads de anexos dos contratos realizados.
     */
    public static final String DESTINO_ANEXO_CONTRATO = "/home/HDP_FILES/Upload/anexoContrato/";


    public static final String RH_ACTIVO = "Activo";
    public static final String RH_ADMINISTRATIVA = "Administrativa";
    public static final String RH_APROVADO = "Aprovado";
    public static final String RH_CLINICA = "Clínica";
    public static final String RH_CANCELADO = "Cancelado";
    public static final String RH_CONTRATADO = "Contratado";
    public static final String RH_DEMITIDO = "Demitido";
    public static final String RH_DETERMINADO = "Determinado";
    public static final String RH_ESTAGIO_FINALIZADO = "Estágio Finalizado";
    public static final String RH_FUNCIONARIO_CONTRATADO = "F. Contratado";
    public static final String RH_INACTIVO = "Inactivo";
    public static final String RH_INDETERMINADO = "Indeterminado";
    public static final String RH_LICENCA_LIMITADA = "Licença Limitada";
    public static final String RH_MARCADO = "Marcado";
    public static final String RH_REFORMADO = "Reformado";
    public static final String RH_TERMINADO = "Terminado";

    //Para avaliação de desempenho
    /**
     * @author Ornela F. Boaventura 
     * 
     * Texto que descreve a classificação na avaliação de desempenho de um funcionário como BOM
     */
    public static final String RH_BOM = "BOM";
    
    /**
     * @author Ornela F. Boaventura 
     * 
     * Texto que descreve a classificação na avaliação de desempenho de um funcionário como MAU
     */
    public static final String RH_MAU = "MAU";
    
    /**
     * @author Ornela F. Boaventura 
     * 
     * Texto que descreve a classificação na avaliação de desempenho de um funcionário como MUITO BOM
     */
    public static final String RH_MUITO_BOM = "MUITO BOM";
    
    /**
     * @author Ornela F. Boaventura 
     * 
     * Texto que descreve a classificação na avaliação de desempenho de um funcionário como REGULAR
     */
    public static final String RH_REGULAR = "REGULAR";

    //Para faltas
    public static final String RH_FALTA_JUSTIFICADA = "Justificada";
    public static final String RH_FALTA_NAO_JUSTIFICADA = "Não Justificada";
    
}
