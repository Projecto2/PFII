/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author gemix
 */
public class FileManagerGeral
{
    public static HSSFWorkbook createHSSFWorkbook(String filename) throws FileNotFoundException, IOException
    {
        InputStream ExcelFileToRead = new FileInputStream(filename);
        //System.err.println("1: AmbCidCapitulosBean.carregarCapitulosTabela()");
        return new HSSFWorkbook(ExcelFileToRead);
    }

    public static Date lerDataVersaoTabela(String versao, String caminhoFicheiro)
    {
        String parametros[] = versao.split("=");
        if (parametros.length != 2 || parametros[0].equalsIgnoreCase(Constantes.VERSAO) == false)
        {
            util.Mensagem.warnMsg("O Fichero '" + caminhoFicheiro + "' Tem Data de Versão Mal Definida. Fale Com o Administrador do Sistema");
//            System.exit(1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("pt", "PT"));
        Date dataXLSFile = null;
        try
        {
            dataXLSFile = sdf.parse(parametros[1]);
        }
        catch (ParseException ex)
        {
            util.Mensagem.warnMsg("A Data da Versão do Fichero '" + caminhoFicheiro + "' Tem Formato Impróprio. Fale Com o Administrador do Sistema");
//            System.exit(1);
        }
        return dataXLSFile;
    }

    public static Date lerVersaoTabela(HSSFRow row, String caminhoFicheiro)
    {
        Iterator cells = row.cellIterator();
        HSSFCell cell = (HSSFCell) cells.next();
        Date dataXLSFile = lerDataVersaoTabela(cell.getStringCellValue().trim(), caminhoFicheiro.trim());
        return dataXLSFile;
    }
}
