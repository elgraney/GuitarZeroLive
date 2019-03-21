package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
*   Server
*
* @author Jordan assisted by Joe
*/
public class Server {
    public final static int PORT = 8888;
    public final static int BUFFER_SIZE = 1024;
    static String bundlesDir = "src\\com\\RD\\Server\\Bundles\\";
    static String coverArtDir = "src\\com\\RD\\Server\\ServerCoverArts\\";

    public static void main( String[] argv ) throws FileNotFoundException {
        /**
         * Unizips all the image files so that all images are available for store,
         * then when a connection is successful wth a client, starts a worker thread to send the files required for download
         */
        unzipCoverArts(bundlesDir, coverArtDir);

        try {
            final ServerSocket ssck = new ServerSocket( PORT );

            while ( true ) {
                final Socket sck = ssck.accept();
                final Worker wkr = new Worker( sck );
                new Thread(wkr).start();
            }
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }

    public static void unzipCoverArts(String zipFilePath,String destDir) throws FileNotFoundException {
        /**
         * Unzips contents of zipFilePath and places it into destDir.
         */
        try {
            File zip_dir = new File(zipFilePath);
            File dest_dir = new File(destDir);
            File[] zips = zip_dir.listFiles();
            System.out.println(zips);

            for (File zip : zip_dir.listFiles()) {
                byte[] buff = new byte[BUFFER_SIZE];
                FileInputStream fis = new FileInputStream(zip);
                ZipInputStream zis = new ZipInputStream(fis);
                ZipEntry zipEntry = zis.getNextEntry();
                while(zipEntry != null){


                    String fileName = zipEntry.getName();
                    if(fileName.endsWith(".png")){
                        File newFile = new File(dest_dir + File.separator + fileName);
                        System.out.println(fileName + "     Unzipping to  ...." + newFile);


                        new File(newFile.getParent()).mkdirs();
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int length;
                        while ((length = zis.read(buff))>0){
                            fos.write(buff, 0, length);
                        }
                        fos.close();
                    }
                    zis.closeEntry();
                    zipEntry = zis.getNextEntry();
                }

            }
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}

