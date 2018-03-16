/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.amb;

/**
 *
 * @author aires
 */
public class Defs
{
    public static final String TOKENS_SEPARATORS = " <>,.;:|\\|!$%&/()=?»«/*-+'";
    
    public static final String AMB_CID_PERFIL_NOVO = "./ambCid/perfilNovoAmb.xhtml";
    public static final String CID_10 = "CID-10";
        
    public static String FILE_CID10_TABLES_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/cid/cid10_excel_files.xls";
    public static String SHEET_CAPITULOS_AMB = "capitulos";
    public static String SHEET_AGRUPAMENTOS_AMB = "agrupamentos";
    public static String SHEET_CATEGORIAS_AMB = "categorias";
    public static String SHEET_SUBCATEGORIAS_AMB = "subcategorias";
    
    public static String SHEET_DOENCAS_PRIORIDADES_AMB = "doencas_prioridades";
    public static String SHEET_PERFIL_TIPOS_AMB = "perfil_tipos";
    
    public static String FILE_PERFIL_TIPOS_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/cid/cid10_perfil_tipos.xls";
    public static String FILE_DOENCAS_PRIORIDADES_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/cid/cid10_doencas_prioridades.xls";
    
    public static String FILE_CID_VERSAO = "versao";
    
    public static String CAPITULOS_URL = "./ambVisao/ambCid/capitulosAmb.xhtml";
    public static String AGRUPAMENTOS_URL = "./ambVisao/ambCid/agrupamentosAmb.xhtml";
    public static String CATEGORIAS_URL = "./ambVisao/ambCid/categoriasAmb.xhtml";
    public static String SUBCATEGORIAS_URL = "./ambVisao/ambCid/subcategoriasAmb.xhtml";
  
    public static String PERFIL_TIPO_PUBLICO = "Publico";
    public static String PERFIL_TIPO_PRIVADO = "Privado";
    
    public static String PROFISSAO_MEDICO = "Médico";
    
    public static int DOENCAS_PRIORIDADE_MINIMA = 3;
    public static int DOENCAS_PRIORIDADE_MAXIMA = 1;
    
    /**
     *
     * @author: Ivandro dos Santos Colombo
     * 
     */
    
/****************************************************************************************************************************************************************/
/**/                                                                                                                                                          /**/                                            
/**/ /*Referentes aos ficheiros Excel*/                                                                                                                       /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ public static final String FILE_CE_VERSAO = "versao";                                                                                                    /**/                                            
/**/                                                                                                                                                          /**/
/**/ public static final String FILE_ADERENCIA_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_aderencia.xls";                               /**/                                      
/**/ public static final String FILE_CLASSIFICACAO_DOR_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_classificacao_dor.xls";               /**/ 
/**/ public static final String FILE_COLORACAO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_coloracao.xls";                               /**/
/**/ public static final String FILE_CONSULTORIO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_consultorio.xls";                           /**/           
/**/ public static final String FILE_CONFIRMACAO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_confirmacao.xls";                           /**/           
/**/ public static final String FILE_COR_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_cor.xls";                                           /**/
/**/ public static final String FILE_ESPESSURA_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_espessura.xls";                               /**/
/**/ public static final String FILE_ESTADO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_estado.xls";                                     /**/
/**/ public static final String FILE_ESTADO_HIDRATACAO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_estado_hidratacao.xls";               /**/
/**/ public static final String FILE_ESTADO_NOTIFICACAO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_estado_notificacao.xls";             /**/
/**/ public static final String FILE_ESTADO_PAGAMENTO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_estado_pagamento.xls";                 /**/
/**/ public static final String FILE_FREQUENCIA_CARDIACA_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_frequencia_cardiaca.xls";           /**/
/**/ public static final String FILE_FREQUENCIA_RESPIRATORIA_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_frequencia_respiratoria.xls";   /**/
/**/ public static final String FILE_INTERVALO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_intervalo.xls";                               /**/
/**/ public static final String FILE_IMPRESSOES_GERAIS_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_impressoes_gerais.xls";               /**/
/**/ public static final String FILE_SINAL_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_sinal.xls";                                       /**/
/**/ public static final String FILE_OBSERVACOES_MEDICAS_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_observacoes_medicas.xls";           /**/
/**/ public static final String FILE_TEMPO_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_tempo.xls";                                       /**/
/**/ public static final String FILE_TENSAO_ARTERIAL_MAXIMA_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_tensao_arterial_maxima.xls";     /**/
/**/ public static final String FILE_TENSAO_ARTERIAL_MINIMA_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_tensao_arterial_minima.xls";     /**/
/**/ public static final String FILE_TEXTURA_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_textura.xls";                                   /**/
/**/ public static final String FILE_TURGOR_AMB = "/home/HDP_FILES/Ficheiros_excel/amb_ficheiros_excel/ce/ce_turgor.xls";                                     /**/
/**/                                                                                                                                                          /**/                                            
/**/ /*Referentes às unidades e valores das mesmas*/                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ public static final String UNIDADE_FREQUENCIA_CARDIACA_PULSO        = "bpm";                                                                             /**/                                            
/**/ public static final String UNIDADE_FREQUENCIA_RESPIRATORIA          = "rpm";                                                                             /**/                                            
/**/ public static final String UNIDADE_GLICEMIA                         = "mg/dl";                                                                           /**/                                            
/**/ public static final String UNIDADE_TENSAO_ARTERIAL                  = "mmHg";                                                                            /**/                                            
/**/                                                                                                                                                          /**/
/**/ public static final String VALOR_FREQUENCIA_CARDIACA_PULSO          = "60";                                                                              /**/                                            
/**/ public static final String VALOR_FREQUENCIA_RESPIRATRIA             = "18";                                                                              /**/                                            
/**/ public static final String VALOR_GLICEMIA                           = "70";                                                                              /**/                                            
/**/ public static final String VALOR_TENSAO_ARTERIAL_MAXIMA             = "120";                                                                             /**/                                            
/**/ public static final String VALOR_TENSAO_ARTERIAL_MINIMA             = "80";                                                                              /**/                                            
/**/ public static final double VALOR_TEMPERATURA                        = 35.0;                                                                              /**/                                            
/**/ public static final String VALOR_PULSO                              = "75";                                                                              /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ /*Referentes aos tipos de solicitação, centro e estado e outros*/                                                                                        /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ public static final String TIPO_SOLICITACAO_PRIMEIRA                = "Primeira Vez";                                                                    /**/                                            
/**/ public static final String TIPO_SOLICITACAO_RETORNO                 = "Retorno";                                                                         /**/                                            
/**/ public static final String CENTRO_HDP                               = "HDP";                                                                             /**/                                            
/**/ public static final String ESTADO_ESPERA                            = "Em espera";                                                                       /**/                                            
/**/ public static final String ESTADO_ATENDIDO                          = "Atendido";                                                                        /**/                                            
/**/ public static final String DESCRICAO_TABELA_BUSCA                   = "amb_consulta";                                                                    /**/                                            
/**/ public static final int    LIMITE                                   = 100;                                                                               /**/                                            
/**/                                                                                                                                                          /**/ 
/**/ /*Referentes às notificações*/                                                                                                                           /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ public static final String ESTADO_NOTIFICACAO_EM_ESPERA             = "Em espera";                                                                       /**/                                            
/**/ public static final String ESTADO_NOTIFICACAO_TRIAGEM               = "Triagem";                                                                         /**/                                            
/**/ public static final String ESTADO_NOTIFICACAO_ENCAMINHADO           = "Encaminhado (a)";                                                                 /**/                                            
/**/ public static final String ESTADO_NOTIFICACAO_CONSULTADO            = "Consultado (a)";                                                                  /**/                                            
/**/ public static final String ESTADO_NOTIFICACAO_RECONSULTADO          = "Reconsultado (a)";                                                                /**/                                            
/**/ public static final String ESTADO_NOTIFICACAO_DIAGNOSTICADO         = "Diagnosticado (a)";                                                               /**/                                            
/**/ public static final String ESTADO_NOTIFICACAO_RECEITADO             = "Receitado (a)";                                                                   /**/                                            
/**/                                                                                                                                                          /**/
/**/ public static final String CONCLUSAO_TRIAGEM                        = " concluiu a ";                                                                    /**/
/**/ public static final String INICIO_NOTIFICACAO                       = "O (A) Paciente";                                                                  /**/                                            
/**/ public static final String FIM_ECRDR                                = " foi ";                                                                           /**/
/**/ public static final String FIM_PRIMEIRA_NOTIFICACAO                 = " tem de ser atendido (a)";                                                        /**/
/**/ public static final String PONTO                                    = ".";                                                                               /**/                                            
/**/ public static final String STRING_VAZIA                             = " ";                                                                               /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ /*Referentes às observações médicas*/                                                                                                                    /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ public static final String ALTA                                     = "Alta (Medicado)";                                                                 /**/                                            
/**/ public static final String SOLICITACAO_OUTROS_SERVICOS              = "Solicitação outros Serviços";                                                     /**/ 
/**/ public static final String NOME_MODULO                              = "Ambulatório";                                                                     /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ /*Referentes ao sexo*/                                                                                                                                   /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ public static final String FEMININO                                 = "Feminino";                                                                        /**/                                            
/**/ public static final String MASCULINO                                = "Masculino";                                                                       /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ /*Referentes ao valor Nenhum amb_sinal*/                                                                                                                 /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/ public static final String VALOR_PADRAO                             = "Nenhum";                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/**/                                                                                                                                                          /**/                                            
/****************************************************************************************************************************************************************/
}
