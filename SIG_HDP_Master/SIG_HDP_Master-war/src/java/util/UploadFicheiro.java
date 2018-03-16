/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Part;

/**
 *
 * @author Ornela F. Boaventura
 */
public class UploadFicheiro
{

   private static final UploadFicheiro INSTANCE = new UploadFicheiro();

   private UploadFicheiro ()
   {
   }

   public static UploadFicheiro getInstance ()
   {
      return INSTANCE;
   }
   
   /**
    * Realiza o carregamento de um ficheiro submetido, e guarda num diretório
    * indicado
    *
    * @param part Objecto que contém o ficheiro submetido
    * @param dirDestino Diretório destino para guardar o ficheiro
    * @param novoNome Novo nome com que será gravado o ficheiro
    * @return
    * @throws IOException
    */
   public String gravar (Part part, String dirDestino, String novoNome) throws IOException
   {
      String fileName = part.getSubmittedFileName();

      File fileSalvarDir = new File(dirDestino);
      //O diretório destino é criado caso ainda não exista
      if (!fileSalvarDir.exists())
      {
         fileSalvarDir.mkdir();
      }

      InputStream input = part.getInputStream();

      if (novoNome != null && ! novoNome.trim().isEmpty())
      {
         //Pegando o índice do ultimo ponto na String fileName
         //depois do ponto está a extensão (Ex: .png ou .jpg)
         int index = fileName.lastIndexOf(".");
         fileName = (novoNome + "_" + gerarDataHoraActual() + fileName.substring(index)).trim();
      }

      Files.copy(input, new File(fileSalvarDir, fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);

      return fileName;
   }

   /**
    * Apaga um ficheiro 
    *
    * @param file Ficheiro que se pretenda apagar
    * @return
    */
   public boolean apagarFicheiro (File file)
   {
      return file.isFile() && file.delete();
   }

   /**
    * Retorna a data e hora actual em String
    * @return
    */
   public static String gerarDataHoraActual ()
   {
      Date d = Calendar.getInstance().getTime();

      String dataString = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss_SSS").format(d);

      return dataString;
   }

}
