/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;

/*********************************************
 *AUTOR: Elisângela Maria Pedro Gaspar
 *DATA: 2017-03-24 16:55
/*********************************************/
/**
 *
 * @author elisangela
 */
public class LinkedDeArrays
{
   private String descricao;
   private ArrayList<LinkedDeArrays> filhos;

   /**
    * @return the filhos
    */
   public ArrayList<LinkedDeArrays> getFilhos()
   {
      return filhos;
   }

   /**
    * @param filhos the filhos to set
    */
   public void setFilhos(ArrayList<LinkedDeArrays> filhos)
   {
      this.filhos = filhos;
   }

   /**
    * @return the descricao
    */
   public String getDescricao()
   {
      return descricao;
   }

   /**
    * @param descricao the descricao to set
    */
   public void setDescricao(String descricao)
   {
      this.descricao = descricao;
   }
}
