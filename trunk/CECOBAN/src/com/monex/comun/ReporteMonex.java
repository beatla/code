package com.monex.comun;

import java.io.*;
import java.net.*;

public class ReporteMonex {

	// public static final String sReporteador =
	// "http://monxrep.monex.com.mx:7001/ReportesMonexNT/ReportesDinamicos.jsp?";
	public static final String sReporteador = "http://172.21.7.195:8082/ReportesMonexNT/ReportesDinamicos.jsp?";
	public static final String sRutaRepositorio = "Y:\\Salida\\";

	public static void lanzadorWeb(String sUrl) {
		try {
			System.out.println("Lanzador web");
			System.out.println(sUrl);
			Runtime rt = Runtime.getRuntime();
			System.out.println("ejecuta URL");
			rt.exec("rundll32 url.dll,FileProtocolHandler " + sUrl);
			System.out.println("listo!!!");
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public String guardaURLenArchivo(String sUrl, String sRuta, String sArchivo) {
		FileOutputStream fos = null;
		InputStream is = null;
		String sExt = "";
		URL url = null;
		int iNextByte = 0;
		String sMensaje = "";

		try {

			long startTime = System.currentTimeMillis();

			sExt = sUrl.substring(sUrl.indexOf("PDF=") + 4,
					sUrl.indexOf("PDF=") + 5);

			if (sExt.equals("S")) {
				sExt = "PDF";
			} else if (sExt.equals("X")) {
				sExt = "XLS";
			} else if (sExt.equals("T")) {
				sExt = "TXT";
			}
			url = new URL(sUrl);
			is = url.openStream();

			long middleTime = System.currentTimeMillis();

			/*
			 * fos = new FileOutputStream(sRuta + sArchivo + "." + sExt); while
			 * ( (iNextByte = is.read()) != -1) { fos.write(iNextByte); }
			 */

			// readWriteArray(is, sRuta + sArchivo + "." + sExt);

			readWriteBuffer(is, sRuta + sArchivo + "." + sExt);

			long endTime = System.currentTimeMillis();

			System.out.println("Tiempo transcurrido " + (endTime - startTime)
					+ " milli seconds");

			System.out.println("Tiempo transcurrido lectura "
					+ (middleTime - startTime) + " milli seconds");

			System.out.println("Tiempo transcurrido escritura "
					+ (endTime - middleTime) + " milli seconds");

			sMensaje = "Archivo generado en \n" + sRuta + sArchivo + "." + sExt;
		} catch (FileNotFoundException e) {
			sMensaje = "Error en reporte. No se encontro la ruta " + sRuta;
			e.printStackTrace();
		} catch (MalformedURLException e) {
			sMensaje = "Error en reporte. URL mal formada [" + sUrl + "]";
			e.printStackTrace();
		} catch (IOException e) {
			sMensaje = "Error en reporte. " + e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			sMensaje = "Error en reporte. " + e.getMessage();
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					sMensaje = "Error en reporte. " + e.getMessage();
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					sMensaje = "Error en reporte. " + e.getMessage();
					e.printStackTrace();
				}
			}
		}
		return sMensaje;
	}

	@SuppressWarnings("unused")
	public static void readWriteArray(InputStream in, String fileTo)
			throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(fileTo);
			int availableLength = in.available();
			System.out.println("Tamaño del stream");
			System.out.println(availableLength);
			byte[] totalBytes = new byte[availableLength];
			int bytedata = in.read(totalBytes);
			out.write(totalBytes);
			out.flush();
			System.out.println("Tamaño del archivo escrito");
			System.out.println(totalBytes);
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}

	public static void readWriteBuffer(InputStream in, String fileTo)
			throws IOException {
		InputStream inBuffer = null;
		OutputStream outBuffer = null;
		try {
			inBuffer = new BufferedInputStream(in, 4096);
			OutputStream out = new FileOutputStream(fileTo);
			outBuffer = new BufferedOutputStream(out, 4096);
			while (true) {
				int bytedata = inBuffer.read();
				if (bytedata == -1)
					break;
				outBuffer.write(bytedata);
			}
		} finally {
			if (inBuffer != null)
				inBuffer.close();
			if (outBuffer != null)
				outBuffer.close();
		}
	}

}

/*
 * 
 * --** Reads a file storing intermediate data into a list. Fast method.
 * 
 * @param file the file to be read
 * 
 * @return a file data-- public byte[] read2list(String file) throws Exception {
 * InputStream in = null; byte[] buf = null; // output buffer int bufLen =
 * 20000*1024; try{ in = new BufferedInputStream(new FileInputStream(file)); buf
 * = new byte[bufLen]; byte[] tmp = null; int len = 0; List data = new
 * ArrayList(24); // keeps peaces of data while((len = in.read(buf,0,bufLen)) !=
 * -1){ tmp = new byte[len]; System.arraycopy(buf,0,tmp,0,len); // still need to
 * do copy data.add(tmp); } -* This part os optional. This method could return a
 * List data for further processing, etc.- len = 0; if (data.size() == 1) return
 * (byte[]) data.get(0); for (int i=0;i<data.size();i++) len += ((byte[])
 * data.get(i)).length; buf = new byte[len]; // final output buffer len = 0; for
 * (int i=0;i<data.size();i++){ // fill with data tmp = (byte[]) data.get(i);
 * System.arraycopy(tmp,0,buf,len,tmp.length); len += tmp.length; } }finally{ if
 * (in != null) try{ in.close();}catch (Exception e){} } return buf; }
 */

/*
 * 
 * package com.performance.io;
 * 
 * // This class shows the different bench marks using default behavior,
 * buffered streams and custom buffering
 * 
 * import java.io.*;
 * 
 * public class IOTest {
 * 
 * public static void main(String[] args){
 * 
 * IOTest io = new IOTest();
 * 
 * try{
 * 
 * long startTime = System.currentTimeMillis();
 * 
 * io.readWrite("c:/temp/Bookmarks.html","c:/temp/Bookmarks1.html");
 * 
 * long endTime = System.currentTimeMillis();
 * 
 * System.out.println(
 * "Time taken for reading and writing using default behaviour : "
 * 
 * + (endTime - startTime) + " milli seconds" );
 * 
 * 
 * 
 * long startTime1 = System.currentTimeMillis();
 * 
 * io.readWriteBuffer("c:/temp/Bookmarks.html","c:/temp/Bookmarks2.html");
 * 
 * long endTime1 = System.currentTimeMillis();
 * 
 * System.out.println("Time taken for reading and writing using buffered streams : "
 * 
 * + (endTime1 - startTime1) + " milli seconds" );
 * 
 * 
 * 
 * long startTime2 = System.currentTimeMillis();
 * 
 * io.readWriteArray("c:/temp/Bookmarks.html","c:/temp/Bookmarks3.html");
 * 
 * long endTime2 = System.currentTimeMillis();
 * 
 * System.out.println("Time taken for reading and writing using custom buffering : "
 * 
 * + (endTime2 - startTime2) + " milli seconds" );
 * 
 * }catch(IOException e){ e.printStackTrace();}
 * 
 * }
 * 
 * public static void readWrite(String fileFrom, String fileTo) throws
 * IOException{
 * 
 * InputStream in = null;
 * 
 * OutputStream out = null;
 * 
 * try{
 * 
 * in = new FileInputStream(fileFrom);
 * 
 * out = new FileOutputStream(fileTo);
 * 
 * while(true){
 * 
 * int bytedata = in.read();
 * 
 * if(bytedata == -1)
 * 
 * break;
 * 
 * out.write(bytedata);
 * 
 * }
 * 
 * }
 * 
 * finally{
 * 
 * if(in != null)
 * 
 * in.close();
 * 
 * if(out !=null)
 * 
 * out.close();
 * 
 * }
 * 
 * }
 * 
 * public static void readWriteBuffer(String fileFrom, String fileTo) throws
 * IOException{
 * 
 * InputStream inBuffer = null;
 * 
 * OutputStream outBuffer = null;
 * 
 * try{
 * 
 * InputStream in = new FileInputStream(fileFrom);
 * 
 * inBuffer = new BufferedInputStream(in);
 * 
 * OutputStream out = new FileOutputStream(fileTo);
 * 
 * outBuffer = new BufferedOutputStream(out);
 * 
 * while(true){
 * 
 * int bytedata = inBuffer.read();
 * 
 * if(bytedata == -1)
 * 
 * break;
 * 
 * out.write(bytedata);
 * 
 * }
 * 
 * }
 * 
 * finally{
 * 
 * if(inBuffer != null)
 * 
 * inBuffer.close();
 * 
 * if(outBuffer !=null)
 * 
 * outBuffer.close();
 * 
 * }
 * 
 * }
 * 
 * public static void readWriteArray(String fileFrom, String fileTo) throws
 * IOException{ InputStream in = null; OutputStream out = null; try{ in = new
 * FileInputStream(fileFrom); out = new FileOutputStream(fileTo); int
 * availableLength = in.available(); byte[] totalBytes = new
 * byte[availableLength]; int bytedata = in.read(totalBytes);
 * out.write(totalBytes); } finally{ if(in != null) in.close(); if(out !=null)
 * out.close(); } } }
 */

