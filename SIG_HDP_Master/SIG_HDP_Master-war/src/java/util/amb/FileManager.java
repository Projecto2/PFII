/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.amb;

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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author aires
 */
public class FileManager
{
    /*
     abre um ficheiro excel de extensao xls
     */

    public static HSSFWorkbook openXLS_File(String filename) throws FileNotFoundException, IOException
    {
        InputStream ExcelFileToRead = new FileInputStream(filename);
        //System.err.println("1: AmbCidCapitulosBean.carregarCapitulosTabela()");
        return new HSSFWorkbook(ExcelFileToRead);
    }

    public static HSSFSheet getSheetFromXLS_File(String filename, String sheetName) throws FileNotFoundException, IOException
    {
        HSSFWorkbook hSSFWorkbook = openXLS_File(filename);
        return hSSFWorkbook.getSheet(sheetName);
    }
    
    public static HSSFSheet getSheetFromXLS_File(String filename, int sheetIndex) throws FileNotFoundException, IOException
    {
        HSSFWorkbook hSSFWorkbook = openXLS_File(filename);
        return hSSFWorkbook.getSheetAt(sheetIndex);
    }
    
    public static HSSFSheet getSheetFromXLS_File(String filename) throws FileNotFoundException, IOException
    {
        HSSFWorkbook hSSFWorkbook = openXLS_File(filename);
        return hSSFWorkbook.getSheetAt(0);
    }
    
    public static Date lerDataVersaoTabela(String versionAttribute, String filename, String sheetname)
    {
        String msg = null;
        String parametros[] = versionAttribute.split("=");
        if (parametros.length != 2 || parametros[0].equalsIgnoreCase(util.amb.Defs.FILE_CID_VERSAO) == false)
        {
            msg = (sheetname != null) ? "A tabela existente na folha '" + sheetname + "' do fichero '" + filename + "' tem data de versao mal definida. Fale com o administrador do sistema" :
                                                "A tabela existente no fichero '" + filename + "' tem data de versao mal definida. Fale com o administrador do sistema";
            util.Mensagem.warnMsg(msg);
            System.exit(1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("pt", "PT"));
        Date dataSheet = null;
        try
        {
            dataSheet = sdf.parse(parametros[1]);
        }
        catch (ParseException ex)
        {
            msg = (sheetname != null) ? "A data da tabela existente na folha '" + sheetname + "' do fichero '" + filename + "' tem formato improprio. Fale com o administrador do sistema" :
                                                "A data da tabela existente no fichero '" + filename + "' tem formato improprio. Fale com o administrador do sistema";
            util.Mensagem.warnMsg(msg);
    
            System.exit(1);
        }
        return dataSheet;
    }

    public static Date lerVersaoTabela(HSSFRow row, String filename, String sheetname)
    {
        Iterator cells = row.cellIterator();
        HSSFCell cell = (HSSFCell) cells.next();
        Date dataSheet = lerDataVersaoTabela(cell.getStringCellValue().trim(), filename, sheetname);
        return dataSheet;
    }
}
