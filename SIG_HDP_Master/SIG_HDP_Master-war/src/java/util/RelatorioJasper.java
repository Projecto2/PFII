/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Armindo Binje,
 * @author Elizangela Gaspar
 * @author Mauro Clemente
 * @author Ornela F. Boaventura
 *
 */
public class RelatorioJasper
{

    public static void exportPDF (String nomeRelatorio, HashMap<String, Object> parametrosMap, List colecaoDados)
    {
        try
        {
            if (parametrosMap == null)
            {
                parametrosMap = new HashMap<>();
            }
            if (colecaoDados == null)
            {
                throw new IllegalArgumentException("Nenhuma coleção de dados para imprimir");
            }
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(colecaoDados);

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            String path = externalContext.getRealPath("WEB-INF/relatorios/" + nomeRelatorio);

            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametrosMap, beanCollectionDataSource);

            HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();

            //Utilizado para baixar o relatório sem ser pré-visualizado (Por isso foi inutilizado)
            //httpServletResponse.addHeader("Content-disposition", "attachment; filename=relatório.pdf");
            
            
            JasperExportManager.exportReportToPdfStream(jasperPrint, httpServletResponse.getOutputStream());
            
//            JasperExportManager.exportReportToXmlStream(jasperPrint, httpServletResponse.getOutputStream());

            FacesContext.getCurrentInstance().responseComplete();
//            System.out.println("Imprimiu PDF");
        }
        catch (JRException | IOException | IllegalArgumentException ex)
        {
            Mensagem.erroMsg(ex.toString());
            ex.printStackTrace();
        }

    }
    
    
    
    
       //Feito por Elisangela Faspar
    public static void exportPDFSemLista (String nomeRelatorio, HashMap<String, Object> parametrosMap)
    {
        try
        {
            if (parametrosMap == null)
            {
                parametrosMap = new HashMap<>();
            }
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String path = externalContext.getRealPath("WEB-INF/relatorios/" + nomeRelatorio);
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametrosMap);

            HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();

            JasperExportManager.exportReportToPdfStream(jasperPrint, httpServletResponse.getOutputStream());

            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (JRException | IOException | IllegalArgumentException ex)
        {
            Mensagem.erroMsg(ex.toString());
            ex.printStackTrace();
        }
    }
    
    //Feito por Elisangela Faspar
    public static void exportPDFSemListaAutoBackup (String nomeRelatorio, HashMap<String, Object> parametrosMap)
    {
       System.out.println("exportPDFSemListaAutoBackup");
        try
        {
            if (parametrosMap == null)
            {
                parametrosMap = new HashMap<>();
            }
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String path = externalContext.getRealPath("WEB-INF/relatorios/" + nomeRelatorio);
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametrosMap);

            HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();

            httpServletResponse.addHeader("Content-disposition", 
                    "attachment; filename=" + nomeRelatorio + util.UploadFicheiro.gerarDataHoraActual() + ".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, httpServletResponse.getOutputStream());
            System.out.println("depois de exportReportToPdfStream");
            
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (JRException | IOException | IllegalArgumentException ex)
        {
            Mensagem.erroMsg(ex.toString());
            ex.printStackTrace();
        }
    }
}
