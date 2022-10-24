
package org.lodestone.facebooksource;

/**
 *
 * @author JJ
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.openqa.selenium.io.Zip;


public class UnZipFile {
    
    public void UnZipFile(String ZipFileDir)throws FileNotFoundException, IOException {
        //regex to remove file extension     
        String newFileDir = ZipFileDir.replaceAll("\\.\\w+","");
        Zip.unzip(new FileInputStream(ZipFileDir), new File(newFileDir));
//       try {
//			ZipFile zipFile = new ZipFile("TestZipFolder.zip");
//			Enumeration<?> enu = zipFile.entries();
//			while (enu.hasMoreElements()) {
//				ZipEntry zipEntry = (ZipEntry) enu.nextElement();
//
//				String name = zipEntry.getName();
//				long size = zipEntry.getSize();
//				long compressedSize = zipEntry.getCompressedSize();
//				System.out.printf("File name: %-20s | File size: %6d | compressed size: %6d\n", 
//						name, size, compressedSize);
//
//				File file = new File(name);
//				if (name.endsWith("/")) {
//					file.mkdirs();
//					continue;
//				}
//
//				File parent = file.getParentFile();
//				if (parent != null) {
//					parent.mkdirs();
//				}
//
//				InputStream is = zipFile.getInputStream(zipEntry);
//				FileOutputStream fos = new FileOutputStream(file);
//				byte[] bytes = new byte[1024];
//				int length;
//				while ((length = is.read(bytes)) >= 0) {
//					fos.write(bytes, 0, length);
//				}
//				is.close();
//				fos.close();
//
//			}
//			zipFile.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
         

    }
    public static void main(String[] args) throws IOException {
		
	}
   
}
