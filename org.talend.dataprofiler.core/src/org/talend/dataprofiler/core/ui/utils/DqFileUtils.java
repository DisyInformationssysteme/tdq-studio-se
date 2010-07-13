// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IPath;

/**
 * DOC bZhou class global comment. Detailled comment
 */
public final class DqFileUtils {

    /**
     * DOC bZhou FileUtils constructor comment.
     */
    private DqFileUtils() {

    }

    /**
     * DOC bZhou Comment method "getFile".
     * 
     * @param parent
     * @param fileName
     * @return
     */
    public static File getFile(File parent, String fileName) {
        List<File> allFiles = new ArrayList<File>();
        searchAllFile(allFiles, parent, true);

        for (File file : allFiles) {
            if (file.isFile() && StringUtils.equals(fileName, file.getName())) {
                return file;
            }
        }

        return null;
    }

    /**
     * DOC bZhou Comment method "existFile".
     * 
     * @param parent
     * @param fileName
     * @return
     */
    public static boolean existFile(File parent, String fileName) {
        File file = getFile(parent, fileName);
        return file != null && file.exists();
    }

    /**
     * DOC bZhou Comment method "existFile".
     * 
     * @param parent
     * @param targetFile
     * @return
     */
    public static boolean existFile(File parent, File targetFile) {
        return existFile(parent, targetFile.getName());
    }

    /**
     * DOC bZhou Comment method "existFile".
     * 
     * @param parent
     * @param targetPath
     * @return
     */
    public static boolean existFile(File parent, IPath targetPath) {
        return existFile(parent, targetPath.toFile());
    }

    /**
     * DOC bZhou Comment method "searchAllFile".
     * 
     * @param result
     * @param parent
     * @param recursive
     */
    public static void searchAllFile(List<File> result, File parent, boolean recursive) {
        File[] files = parent.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && recursive) {
                    searchAllFile(result, file, recursive);
                } else {
                    result.add(file);
                }
            }
        } else {
            result.add(parent);
        }
    }
}
