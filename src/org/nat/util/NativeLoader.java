package org.nat.util;

import java.io.File;
import java.io.FilenameFilter;

public class NativeLoader {
    public static void loadLibrary(String libraryName) {
        String fileName=getFileName(libraryName);
        File directory=new File("./");
        File[] possibleBin=getSubFile(directory,"bin");

        if(possibleBin.length<1) {
            throw new UnsatisfiedLinkError("no bin/ found");
        }

        File bin=possibleBin[0];

        File[] possibleLibrary = getSubFile(bin,fileName);

        if(possibleLibrary.length<1) {
            throw new UnsatisfiedLinkError("no "+fileName+" found, unsupported os or architecture");
        }

        System.load(possibleLibrary[0].getAbsolutePath());
    }

    public static String getFileName(String libName) {
        String os=System.getProperty("os.name");
        String architecture=System.getProperty("os.arch");
        int arch=32;
        if(architecture.contains("x86")) {
            arch=32;
        } else if(architecture.contains("64")) {
            arch=64;
        }
        if(os.contains("Windows") && arch==32) {
            return libName+"32.dll";
        } else if(os.contains("Windows") && arch==64) {
            return libName+".dll";
        }
        return null;
    }

    public static File[] getSubFile(File file,String theName) {
        FilenameFilter fnf=new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                return theName.equals(name);
            }
        };
        return file.listFiles(fnf);
    }
}
