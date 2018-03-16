/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.configuracoes;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmConfiguracoesBean implements Serializable
{
   //LOCAIS DE ARMAZENAMENTO PARA ORIGEM DE PEDIDOS
   public static final int ORIGEM_PEDIDO_MODULO_AMBULATORIO = 12;
   public static final int ORIGEM_PEDIDO_MODULO_DIAGNOSTICO = 11;
   public static final int ORIGEM_PEDIDO_MODULO_FARMACIA = 7;
   public static final int ORIGEM_PEDIDO_MODULO_INTERNAMENTO_MED = 1;
   public static final int ORIGEM_PEDIDO_MODULO_INTERNAMENTO_PED = 2;
   
   //LOCAIS DE ARMAZENAMENTO PARA DESTINO DE PEDIDOS
   public static final int DESTINO_PEDIDO_MODULO_AMBULATORIO = 3;
   public static final int DESTINO_PEDIDO_MODULO_DIAGNOSTICO = 3;
   public static final int DESTINO_PEDIDO_MODULO_FARMACIA = 3;
   public static final int DESTINO_PEDIDO_MODULO_INTERNAMENTO_MED = 3;
   public static final int DESTINO_PEDIDO_MODULO_INTERNAMENTO_PED = 3;
   
   //INSTITUICAO PARA DOACAO
   public static final int DESTINO_DOACAO = 13;//HOSPITAL SANATORIO
   
   //INSTITUICAO PARA CADASTRO DE LOCAIS
   public static final int INSTITUICAO_CADASTRO_LOCAL = 1;//HDP
   public static final int TIPO_CADASTRO_LOCAL = 4;//AREA EXTERNA
   
   //TABELAS AUXILIARES DE PRODUTO
   public static final int TIPO_PRODUTO = 1;//MEDICAMENTO
   public static final int VIA_ADMINISTRACAO = 15;//VIA ORAL
   public static final int UNIDADE_MEDIDA = 23;//MILIGRAMA
   public static final int TIPO_UNIDADE_MEDIDA = 4;//MASSA OU PESO
   public static final int FORMA_FARMACEUTICA = 6;//COMPRIMIDO
   public static final int CATEGORIA_MEDICAMENTO = 44;//Amebicidas e antigiardiases

   //FORNECIMENTO 
   public static final int TIPO_FORNECIMENTO = 3;//DPSL
   public static final int FORNECEDOR = 1;//DIRECAO PROVINCIAL DE MEDICAMENTOS ESSENCIAIS

   //DISPENSA
   public static final int LOCAL_ATENDIMENTO_UTENTE = 7;//FARMACIA EXTERNA
   
   //GESTAO DE LOTES
   public static final int MARCA_LABORATORIO = 141;//EDOL
   

   /**
    * Creates a new instance of FarmConfiguracoesBean
    */
   public FarmConfiguracoesBean()
   {
   }
   
   public static FarmConfiguracoesBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmConfiguracoesBean farmConfiguracoesBean
              = (FarmConfiguracoesBean) context.getELContext().getELResolver().getValue(context.getELContext(),
                      null, "farmConfiguracoesBean");

      return farmConfiguracoesBean;
   }
   
}
